
package com.bilibili.player_ix.blue_oceans.common.entities.red_plum;

import com.bilibili.player_ix.blue_oceans.common.blocks.plum.RedPlumCatalyst;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansParticleTypes;
import com.github.NineAbyss9.ix_api.api.annotation.ServerOnly;
import com.github.NineAbyss9.ix_api.util.Maths;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.NineAbyss9.annotation.doc.Message;
import org.NineAbyss9.math.MathSupport;

import javax.annotation.Nullable;

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
        if (this.firstTick && this.isServerSide()) {
            int i = 0;
            while (i < 5) {
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
        if ((this.spreadCooldown <= 0 || this.getRandomUtil().nextFloat() < 0.06F)
            && PlumSpreader.checkConditions(this)) {
            if (this.isServerSide()) {
                spreadPlum(this.level(), this.blockPosition());
            }
            this.spreadCooldown = Maths.toTick(15);
        }
        if (this.isServerSide() &&
                this.getRandomUtil().nextFloat() < this.getSpawnChance() &&
                this.tickCount % 30 == 0 && checkPlums(level(), getBoundingBox().inflate(12))) {
            summonPlum(this.level(), this.blockPosition().offset(Maths.randomInt(3), 0, Maths.randomInt(3)));
        }
    }

    protected void clientAiStep() {
        this.idle.startIfStopped(this.tickCount);
        if (MathSupport.random.nextBoolean())
            BlueOceansParticleTypes.addRedSpell(this.level(), this.getRandomX(0.8),
                    this.getRandomY(), this.getRandomZ(0.8), 0.2);
    }

    protected void customServerAiStep() {
        super.customServerAiStep();
        if (this.getLevel() >= 6 && this.tickCount % 20 == 0) {
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

    public boolean shouldAttackOtherMobs()
    {
        return false;
    }

    public static boolean checkPlums(Level pLevel, AABB pBound) {
        return checkPlums(pLevel, pBound, 10);
    }

    public static boolean checkPlums(Level pLevel, AABB pBound, int maxSize) {
        return pLevel.getEntitiesOfClass(RedPlumMonster.class, pBound).size() < maxSize;
    }

    public static void summonPlum(Level pLevel, BlockPos position)
    {
        PlumHolder.spawn(pLevel, position, 1);
    }

    private float getSpawnChance() {
        return switch (this.getLevel()) {
            case 3 -> 0.03F;
            case 4 -> 0.07F;
            case 5 -> 0.1F;
            case 6 -> 0.2F;
            default -> 0.02F;
        };
    }

    public int getLevel() {
        return this.getInfectLevel();
    }

    public void setLevel(int pLevel) {
        if (pLevel > 6)
            return;
        this.setInfectLevel(pLevel);
    }

    protected void setInfectLevel(int pLevel) {
        if (pLevel > 6)
            return;
        super.setInfectLevel(pLevel);
    }

    @Nullable
    protected EntityType<? extends AbstractRedPlumMob> getNextLevelConvert() {
        return null;
    }

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
