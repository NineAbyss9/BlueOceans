
package com.github.player_ix.ix_api.api.mobs;

import com.github.player_ix.ix_api.util.Maths;
import com.github.player_ix.ix_api.util.ParticleUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.common.ForgeHooks;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Random;
import java.util.function.Predicate;

public abstract class ApiPathfinderMob
extends PathfinderMob {
    protected static final EntityDataAccessor<Boolean> DATA_BABY;
    protected final MobData mobData = new MobData(this);
    protected Random randomUtil = new Random();
    protected ParticleUtil particleUtil = new ParticleUtil(this);
    protected ApiPathfinderMob(EntityType<? extends ApiPathfinderMob> type, Level level) {
        super(type, level);
        this.populateDefaultItems();
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_BABY, false);
    }

    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            this.clientTick();
        }
    }

    public void aiStep() {
        this.updateSwingTime();
        if (this instanceof Enemy) {
            this.updateNoActionTime();
        }
        super.aiStep();
        if (this.level().isClientSide) {
            this.clientAiStep();
        }
    }

    public void clientTick() {
    }

    protected void clientAiStep() {
    }

    @SuppressWarnings("deprecation")
    public boolean canBreatheUnderwater() {
        return super.canBreatheUnderwater();
    }

    public SoundSource getSoundSource() {
        return this instanceof Enemy ? SoundSource.HOSTILE : SoundSource.NEUTRAL;
    }

    public boolean hurt(DamageSource pSource, float pAmount) {
        if (Float.isNaN(pAmount)) {
            return false;
        }
        return super.hurt(pSource, pAmount);
    }

    public void setMainHandItem(Item pItem) {
        this.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(pItem));
    }

    public void setOffHandItem(Item pItem) {
        this.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(pItem));
    }

    public void handleEntityEvent(byte p_21375_) {
        if (p_21375_ == 60) {
            this.spawnAnim();
        } else
            super.handleEntityEvent(p_21375_);
    }

    public void addOrRemoveAttributeModifier(Attribute pA, AttributeModifier pM, boolean add) {
        AttributeInstance attribute = this.getAttribute(pA);
        Objects.requireNonNull(attribute);
        if (add)
            attribute.addTransientModifier(pM);
        else
            attribute.removeModifier(pM);
    }

    public boolean isServerSide() {
        return !this.level().isClientSide;
    }

    public void sendSystemMessage(Component pMessage) {
        if (this.level() instanceof ServerLevel level) {
            level.getServer().getPlayerList().broadcastSystemMessage(pMessage, false);
        } else {
            Minecraft.getInstance().gui.getChat().addMessage(pMessage);
        }
    }

    public void setPos(double pX, double pY, double pZ) {
        if (Double.isNaN(pX) || Double.isNaN(pY) || Double.isNaN(pZ)) {
            return;
        }
        super.setPos(pX, pY, pZ);
    }

    public double x() {
        return this.getX();
    }

    public double y() {
        return this.getY();
    }

    public double z() {
        return this.getZ();
    }

    public ServerLevel getServerLevel() {
        return (ServerLevel)this.level();
    }

    public ClientLevel getClientLevel() {
        return (ClientLevel)this.level();
    }

    public void setBaby(boolean pBaby) {
        this.entityData.set(DATA_BABY, pBaby);
    }

    protected void updateNoActionTime() {
        float f = this.getLightLevelDependentMagicValue();
        if (f > 0.5F) {
            this.noActionTime += 2;
        }
    }

    @SuppressWarnings("deprecation")
    public boolean isPushedByFluid() {
        return super.isPushedByFluid();
    }

    @Nullable
    public SoundEvent getStepSound() {
        return null;
    }

    public static AttributeSupplier.Builder createPathAttributes() {
        return ApiPathfinderMob.createMobAttributes().add(Attributes.ATTACK_DAMAGE);
    }

    protected void populateDefaultItems() {
    }

    public Random getRandomUtil() {
        return this.randomUtil;
    }

    public ParticleUtil getParticleUtil() {
        return particleUtil;
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (DATA_BABY.equals(pKey)) {
            this.refreshDimensions();
        }
        super.onSyncedDataUpdated(pKey);
    }

    @SuppressWarnings("deprecation")
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty,
                                        MobSpawnType pReason, @Nullable SpawnGroupData pData, @Nullable CompoundTag pTag) {
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pData, pTag);
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty,
                                        MobSpawnType pReason) {
        return this.finalizeSpawn(pLevel, pDifficulty, pReason, null, null);
    }

    public boolean isMoving() {
        return this.walkAnimation.isMoving();
    }

    public boolean isImmobile() {
        return super.isImmobile();
    }

    @SuppressWarnings("deprecation")
    public static boolean hasChunkAt( Entity entity, BlockPos pos) {
        return entity.level().hasChunkAt(pos);
    }

    @SuppressWarnings("deprecation")
    public boolean hasChunkAt(int i, int j) {
        return this.level().hasChunkAt(i, j);
    }

    @SuppressWarnings("deprecation")
    public float getLightLevelDependentMagicValue() {
        return super.getLightLevelDependentMagicValue();
    }

    public boolean isFood(ItemStack stack) {
        return false;
    }

    public boolean shouldDropExperience() {
        return true;
    }

    protected boolean shouldDropLoot() {
        return true;
    }

    public boolean doHurtTarget(Entity pEntity) {
        float f = (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
        float f1 = (float)this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
        if (pEntity instanceof LivingEntity entity) {
            f += EnchantmentHelper.getDamageBonus(this.getMainHandItem(), entity.getMobType());
            f1 += (float)EnchantmentHelper.getKnockbackBonus(this);
        }
        int i = EnchantmentHelper.getFireAspect(this);
        if (i > 0) {
            pEntity.setSecondsOnFire(i * 4);
        }
        boolean flag = pEntity.hurt(this.damageSources().mobAttack(this), f);
        if (flag) {
            if (f1 > 0.0F && pEntity instanceof LivingEntity entity) {
                entity.knockback(f1 * 0.5F, Mth.sin(this.getYRot()
                        * Maths.PI_DIVIDING_180), -Mth.cos(this.getYRot() * Maths.PI_DIVIDING_180));
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
            }
            if (pEntity instanceof Player player) {
                this.maybeDisableShield(player, this.getMainHandItem(), player.isUsingItem() ? player.getUseItem()
                        : ItemStack.EMPTY);
            }
            this.doEnchantDamageEffects(this, pEntity);
            this.setLastHurtMob(pEntity);
        }
        return flag;
    }

    public void maybeDisableShield(Player pPlayer, ItemStack pMobItemStack, ItemStack pPlayerItemStack) {
        if (!pMobItemStack.isEmpty() && !pPlayerItemStack.isEmpty() && pMobItemStack.getItem() instanceof
                AxeItem && pPlayerItemStack.is(Items.SHIELD)) {
            float f = 0.25F + (float)EnchantmentHelper.getBlockEfficiency(this) * 0.05F;
            if (this.random.nextFloat() < f) {
                pPlayer.getCooldowns().addCooldown(Items.SHIELD, 100);
                this.level().broadcastEntityEvent(pPlayer, (byte)30);
            }
        }
    }

    public ItemStack getProjectile(ItemStack p_33038_) {
        if (p_33038_.getItem() instanceof ProjectileWeaponItem) {
            Predicate<ItemStack> predicate = ((ProjectileWeaponItem)p_33038_.getItem()).getSupportedHeldProjectiles();
            ItemStack itemstack = ProjectileWeaponItem.getHeldProjectile(this, predicate);
            return ForgeHooks.getProjectile(this, p_33038_, itemstack.isEmpty() ? new ItemStack(Items.ARROW) : itemstack);
        } else {
            return ForgeHooks.getProjectile(this, p_33038_, ItemStack.EMPTY);
        }
    }

    static {
        DATA_BABY = SynchedEntityData.defineId(ApiPathfinderMob.class, EntityDataSerializers.BOOLEAN);
    }
}
