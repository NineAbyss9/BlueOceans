
package com.bilibili.player_ix.blue_oceans.common.entities.dumplings;

import com.bilibili.player_ix.blue_oceans.init.BlueOceansEntities;
import com.github.player_ix.ix_api.api.mobs.ai.goal.ApiOwnerTargetGoal;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import org.jetbrains.annotations.Nullable;

public class DumplingMonster extends AbstractDumpling implements Enemy {
    public DumplingMonster(EntityType<? extends AbstractDumpling> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
        this.setMaxUpStep(2);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(0, new ApiOwnerTargetGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1,
                true));
        this.goalSelector.addGoal(3, new FloatGoal(this));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, LivingEntity.class, 10f));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1));
        this.targetSelector.addGoal(4, new HurtByTargetGoal(this, DumplingMonster.class).setAlertOthers());
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class,
                true));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, AbstractVillager.class,
                false));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, AbstractGolem.class,
                false));
    }

    public DumplingMonster(PlayMessages.SpawnEntity spawnEntity, Level world){
        this(BlueOceansEntities.DUMPLING_MONSTER.get(), world);
    }

    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected @Nullable SoundEvent getHurtSound(DamageSource p_21239_) {
        return super.getHurtSound(p_21239_);
    }

    protected @Nullable SoundEvent getDeathSound() {
        return super.getDeathSound();
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = AbstractDumpling.createAttributes();
        builder.add(Attributes.MAX_HEALTH, 16).add(Attributes.ATTACK_KNOCKBACK, 1)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.25)
                .add(Attributes.FOLLOW_RANGE, 100).add(Attributes.ATTACK_DAMAGE, 3)
                .add(Attributes.MOVEMENT_SPEED, 0.3);
        return builder;
    }

    public boolean canAttack(LivingEntity pTarget) {
        if (pTarget instanceof DumplingMonster) {
            return false;
        }
        return super.canAttack(pTarget);
    }
}
