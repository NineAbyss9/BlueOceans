
package com.bilibili.player_ix.blue_oceans.common.entities.villagers;

import com.github.NineAbyss9.ix_api.api.mobs.IFlagMob;
import com.github.NineAbyss9.ix_api.api.mobs.IShieldUser;
import com.github.NineAbyss9.ix_api.api.mobs.ai.goal.ShieldUserMeleeAttackGoal;
import com.github.NineAbyss9.ix_api.api.mobs.ai.goal.UseShieldGoal;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;

public class Researcher
extends BaseVillager
implements IShieldUser, IFlagMob {
    protected static final EntityDataAccessor<Integer> DATA_FLAGS;
    public final ItemCooldowns cooldown;
    public Researcher(EntityType<? extends Researcher> pType, Level level) {
        super(pType, level);
        this.cooldown = new ItemCooldowns();
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_FLAGS, 0);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new ShieldUserMeleeAttackGoal<>(this, 1));
        this.goalSelector.addGoal(1, new UseShieldGoal<>(this));
        super.registerGoals();
    }

    protected void addAttackBehaviors() {
    }

    protected void populateDefaultItems() {
        this.setMainHandItem(Items.IRON_SWORD);
        this.setOffHandItem(Items.SHIELD);
    }

    public boolean isUsingShield() {
        return this.isFlag(2);
    }

    public void setUsingShield(boolean using) {
        this.setFlag(using ? 2 : 0);
    }

    public boolean isShieldOnCooldown() {
        return this.cooldown.isOnCooldown(Items.SHIELD);
    }

    public void disableShield(boolean pBecauseOfAxe) {
        float f = 0.25F + (float)EnchantmentHelper.getBlockEfficiency(this) * 0.05F;
        if (pBecauseOfAxe) {
            f += 0.75F;
        }
        if (this.random.nextFloat() < f) {
            this.cooldown.addCooldown(this.getUseItem().getItem(), 100);
            this.stopUsingItem();
            this.level().broadcastEntityEvent(this, (byte)30);
        }
    }

    public int getFlag() {
        return this.entityData.get(DATA_FLAGS);
    }

    public void setFlag(int flag) {
        this.entityData.set(DATA_FLAGS, flag);
    }

    static {
        DATA_FLAGS = SynchedEntityData.defineId(Researcher.class, EntityDataSerializers.INT);
    }
}
