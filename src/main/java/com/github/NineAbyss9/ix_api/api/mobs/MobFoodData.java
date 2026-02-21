
package com.github.NineAbyss9.ix_api.api.mobs;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;

import javax.annotation.Nullable;

public class MobFoodData {
    private final Mob mob;
    protected final int maxFoodLevel;
    private int foodLevel = 20;
    private float saturationLevel;
    private float exhaustionLevel;
    private int tickTimer;
    private int lastFoodLevel = 20;
    public MobFoodData(Mob pMob, int pMaxFoodLevel) {
        this.mob = pMob;
        this.maxFoodLevel = pMaxFoodLevel;
    }

    public MobFoodData(Mob pMob) {
        this(pMob, 20);
    }

    public void eat(int pFoodLevelModifier, float pSaturationLevelModifier) {
        this.foodLevel = Math.min(pFoodLevelModifier + this.foodLevel, this.maxFoodLevel);
        this.saturationLevel = Math.min(this.saturationLevel + pFoodLevelModifier *
                pSaturationLevelModifier * 2.0F, this.foodLevel);
    }

    public void eat(LivingEntity pEntity) {
        float maxHealth = pEntity.getMaxHealth();
        int foodLevelModifier = (int)(maxHealth / 4);
        float saturationLevelModifier = maxHealth > 15 ? 1.5F : 1.0F;
        this.eat(foodLevelModifier, saturationLevelModifier);
    }

    public void eat(Item pItem, ItemStack pStack, @Nullable LivingEntity entity) {
        if (pItem.isEdible()) {
            FoodProperties foodproperties = pStack.getFoodProperties(entity);
            if (foodproperties != null) {
                this.eat(foodproperties.getNutrition(), foodproperties.getSaturationModifier());
            }
        }
    }

    public void tick() {
        Difficulty difficulty = mob.level().getDifficulty();
        this.lastFoodLevel = this.foodLevel;
        if (mob.hasEffect(MobEffects.HUNGER)) {
            MobEffectInstance hunger = mob.getEffect(MobEffects.HUNGER);
            assert hunger != null;
            this.addExhaustion(0.005F * (hunger.getAmplifier() + 1));
        }
        if (mob.hasEffect(MobEffects.SATURATION)) {
            MobEffectInstance saturation = mob.getEffect(MobEffects.SATURATION);
            assert saturation != null;
            this.eat(saturation.getAmplifier() + 1, 1.0F);
        }
        if (this.exhaustionLevel > 4.0F) {
            this.exhaustionLevel -= 4.0F;
            if (this.saturationLevel > 0.0F) {
                this.saturationLevel = Math.max(this.saturationLevel - 1.0F, 0.0F);
            } else if (difficulty != Difficulty.PEACEFUL) {
                this.foodLevel = Math.max(this.foodLevel - 1, 0);
            }
        }
        boolean flag = mob.level().getGameRules().getBoolean(GameRules.RULE_NATURAL_REGENERATION);
        if (flag && this.saturationLevel > 0.0F && this.isHurt() && this.foodLevel >= 20) {
            ++this.tickTimer;
            if (this.tickTimer >= 10) {
                float f = Math.min(this.saturationLevel, 6.0F);
                mob.heal(f / 6.0F);
                this.addExhaustion(f);
                this.tickTimer = 0;
            }
        } else if (flag && this.foodLevel >= 18 && this.isHurt()) {
            ++this.tickTimer;
            if (this.tickTimer >= 80) {
                this.mob.heal(1.0F);
                this.addExhaustion(6.0F);
                this.tickTimer = 0;
            }
        } else if (this.foodLevel <= 0) {
            ++this.tickTimer;
            if (this.tickTimer >= 80) {
                this.mob.hurt(mob.damageSources().starve(), 1.0F);
                this.tickTimer = 0;
            }
        } else {
            this.tickTimer = 0;
        }
    }

    public boolean isHurt() {
        return MobUtils.isHurt(this.mob);
    }

    /**
     * Reads the food data for the mob.
     */
    public void readAdditionalSaveData(CompoundTag pCompoundTag) {
        if (pCompoundTag.contains("foodLevel", 99)) {
            this.setFoodLevel(pCompoundTag.getInt("foodLevel"));
            this.tickTimer = pCompoundTag.getInt("foodTickTimer");
            this.setSaturation(pCompoundTag.getFloat("foodSaturationLevel"));
            this.setExhaustion(pCompoundTag.getFloat("foodExhaustionLevel"));
        }
    }

    /**
     * Writes the food data for the mob.
     */
    public void addAdditionalSaveData(CompoundTag pCompoundTag) {
        pCompoundTag.putInt("foodLevel", this.getFoodLevel());
        pCompoundTag.putInt("foodTickTimer", this.tickTimer);
        pCompoundTag.putInt("maxFoodLevel", this.getMaxFoodLevel());
        pCompoundTag.putFloat("foodSaturationLevel", this.getSaturationLevel());
        pCompoundTag.putFloat("foodExhaustionLevel", this.getExhaustionLevel());
    }

    public CompoundTag integration() {
        CompoundTag content = new CompoundTag();
        this.addAdditionalSaveData(content);
        return content;
    }

    public void readIntegration(CompoundTag tag) {
        if (tag.contains("FoodData")) {
            CompoundTag content = tag.getCompound("FoodData");
            this.readAdditionalSaveData(content);
        }
    }

    /**
     * Get the mob's food level.
     */
    public int getFoodLevel() {
        return this.foodLevel;
    }

    public int getLastFoodLevel() {
        return this.lastFoodLevel;
    }

    /**
     * Get whether the mob must eat food.
     */
    public boolean needsFood() {
        return this.foodLevel < this.maxFoodLevel;
    }

    public int getMaxFoodLevel() {
        return this.maxFoodLevel;
    }

    /**
     * Adds input to {@linkplain MobFoodData#exhaustionLevel} to a max of 40.
     */
    public void addExhaustion(float pExhaustion) {
        this.exhaustionLevel = Math.min(this.exhaustionLevel + pExhaustion, 40.0F);
    }

    public float getExhaustionLevel() {
        return this.exhaustionLevel;
    }

    /**
     * Get the mobs food saturation level.
     */
    public float getSaturationLevel() {
        return this.saturationLevel;
    }

    public void setFoodLevel(int pFoodLevel) {
        this.foodLevel = pFoodLevel;
    }

    public void setSaturation(float pSaturationLevel) {
        this.saturationLevel = pSaturationLevel;
    }

    public void setExhaustion(float pExhaustionLevel) {
        this.exhaustionLevel = pExhaustionLevel;
    }
}
