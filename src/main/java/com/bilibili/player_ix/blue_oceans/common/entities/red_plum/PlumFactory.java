
package com.bilibili.player_ix.blue_oceans.common.entities.red_plum;

import com.bilibili.player_ix.blue_oceans.common.blocks.RedPlumCatalyst;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansParticleTypes;
import com.bilibili.player_ix.blue_oceans.util.RedPlumUtil;
import com.github.player_ix.ix_api.api.annotation.ServerOnly;
import com.github.player_ix.ix_api.util.Maths;
import com.github.player_ix.ix_api.util.ParticleUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.nine_abyss.annotation.doc.Message;

import javax.annotation.Nullable;

import java.util.List;

public class PlumFactory
extends RedPlumMonster
implements IPlumSpreader {
    public AnimationState idle = new AnimationState();
    private int spreadCooldown;
    public PlumFactory(EntityType<? extends PlumFactory> type, Level level) {
        super(type, level);
        this.xpReward = XP_REWARD_LARGE;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
    }

    public void baseTick() {
        if (this.firstTick) {
            int i = 0;
            while (i < 5) {
                if (this.isServerSide())
                    spreadPlum(this.level(), this.blockPosition());
                i++;
            }
        }
        super.baseTick();
    }

    public void aiStep() {
        super.aiStep();
        if (this.spreadCooldown > 0)
            --this.spreadCooldown;
        if ((this.spreadCooldown <= 0 || this.getRandomUtil().nextInt(15) == 0)
            && PlumSpreader.checkConditions(this)) {
            if (this.isServerSide()) {
                spreadPlum(this.level(), this.blockPosition());
            }
            this.spreadCooldown = Maths.toTick(15);
        }
        if (!this.level().isClientSide &&
                this.getRandomUtil().nextFloat(1F) < this.getSpawnChance() &&
                this.tickCount % 20 == 0
                && this.level().getEntitiesOfClass(RedPlumMonster.class, this.getBoundingBox().inflate(6),
                e -> e != this).size() < 15) {
            summonPlum(this.level(), this.position().add(Maths.randomInt(3), 0, Maths.randomInt(3)));
        }
    }

    protected void clientAiStep() {
        this.idle.startIfStopped(this.tickCount);
        BlueOceansParticleTypes.addRedSpell(this.level(), this.getRandomX(0.8),
                this.getRandomY(), this.getRandomZ(0.8), 0.2);
    }

    protected void customServerAiStep() {
        super.customServerAiStep();
        if (this.getLevel() == 6 && this.tickCount % 20 == 0) {
            this.heal(1F);
        }
    }

    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("SpreadCooldown", this.spreadCooldown);
    }

    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.spreadCooldown = tag.getInt("SpreadCooldown");
    }

    public static void summonPlum(Level pLevel, Vec3 position) {
        List<EntityType<? extends AbstractRedPlumMob>> list = RedPlumUtil.MAP.get(1);
        if (list != null) {
            AbstractRedPlumMob plumMob = list.get(Maths.random.nextInt(list.size())).create(pLevel);
            if (plumMob != null) {
                plumMob.moveTo(position);
                pLevel.addFreshEntity(plumMob);
                ParticleUtil.spawnAnim(plumMob, BlueOceansParticleTypes.RED_SPELL.get());
            }
        }
    }

    private float getSpawnChance() {
        switch (this.getLevel()) {
            case 3 -> {
                return 0.1F;
            }
            case 4 -> {
                return 0.2F;
            }
            case 5 -> {
                return 0.3F;
            }
            case 6 -> {
                return 0.5F;
            }
            default -> {
                return 0.55F;
            }
        }
    }

    public int getLevel() {
        return this.getInfectLevel();
    }

    public void setLevel(int pLevel) {
        if (pLevel > 6)
            return;
        this.setInfectLevel(pLevel);
    }

    public void setLevelPlus() {
        this.setLevel(this.getLevel() + 1);
    }

    @Nullable
    protected EntityType<? extends AbstractRedPlumMob> getNextLevelConvert() {
        return null;
    }

    /**Like NeoPlums, PlumFactories never level up.*/
    protected int nextConvertUpNeeds() {
        return 0x7fffffff;
    }

    @ServerOnly
    public static void spreadPlum(Level pLevel, @Message("It's already below()") BlockPos base) {
        RedPlumCatalyst.spreadPlum((ServerLevel)pLevel, base.below());
    }

    public static AttributeSupplier createAttributes() {
        return createPathAttributes().add(Attributes.ARMOR, 4)
                .add(Attributes.FOLLOW_RANGE, 52)
                .add(Attributes.MAX_HEALTH, 120).add(Attributes.ATTACK_DAMAGE, 2)
                .add(Attributes.MOVEMENT_SPEED, 0.1).build();
    }
}
