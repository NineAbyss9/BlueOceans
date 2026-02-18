
package com.bilibili.player_ix.blue_oceans.common.entities.projectile;

import com.bilibili.player_ix.blue_oceans.common.entities.villagers.Fishman;
import com.github.player_ix.ix_api.util.Maths;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.NineAbyss9.util.ValueHolder;

import javax.annotation.Nullable;

public class ModFishingHook
extends Projectile {
    private boolean biting;
    private int outOfWaterTime;
    private int life;
    private int nibble;
    private int timeUntilLured;
    private int timeUntilHooked;
    private float fishAngle;
    private boolean openWater = true;
    private FishHookState currentState;
    private final RandomSource synchronizedRandom = RandomSource.create();
    private final int luck;
    private final int lureSpeed;
    private static final EntityDataAccessor<Integer> DATA_HOOKED_ID;
    private static final EntityDataAccessor<Boolean> DATA_BITING;
    public FishingHook hook = null;
    private ModFishingHook(EntityType<? extends Projectile> pEntityType, Level pLevel, int pLuck, int pLureSpeed) {
        super(pEntityType, pLevel);
        this.noCulling = true;
        this.luck = pLuck;
        this.lureSpeed = pLureSpeed;
    }

    public ModFishingHook(EntityType<? extends ModFishingHook> type, Level pLevel) {
        this(type, pLevel, 0, 0);
    }

    protected void defineSynchedData() {
        this.entityData.define(DATA_HOOKED_ID, 0);
        this.entityData.define(DATA_BITING, false);
    }

    public void tick() {
        this.synchronizedRandom.setSeed(this.getUUID().getLeastSignificantBits() ^ this.level().getGameTime());
        super.tick();
        if (this.getOwner() == null)
            discard();
        else if (this.level().isClientSide || !shouldStopFishing((LivingEntity)this.getOwner())) {
            if (this.onGround()) {
                ++this.life;
                if (this.life >= 1200) {
                    this.discard();
                    return;
                }
            } else {
                this.life = 0;
            }
            float f = 0.0F;
            BlockPos blockpos = this.blockPosition();
            FluidState fluidstate = this.level().getFluidState(blockpos);
            if (fluidstate.is(FluidTags.WATER)) {
                f = fluidstate.getHeight(this.level(), blockpos);
            }
            boolean flag = f > 0.0F;
            if (this.currentState.isFlying()) {
                Vec3 vec3 = this.getDeltaMovement();
                double d0 = this.getY() + vec3.y - blockpos.getY() - f;
                if (Math.abs(d0) < 0.01D) {
                    d0 += Math.signum(d0) * 0.1D;
                }
                this.setDeltaMovement(vec3.x * 0.9D, vec3.y - d0 * this.random.nextDouble() * 0.2D, vec3.z * 0.9D);
                if (this.nibble <= 0 && this.timeUntilHooked <= 0) {
                    this.openWater = true;
                } else {
                    this.openWater = this.openWater && this.outOfWaterTime < 10 && this.calculateOpenWater(blockpos);
                }
                if (flag) {
                    this.outOfWaterTime = Math.max(0, this.outOfWaterTime - 1);
                    if (this.biting) {
                        this.setDeltaMovement(this.getDeltaMovement().add(0, -.1 * this.synchronizedRandom.nextFloat() *
                                this.synchronizedRandom.nextFloat(), 0));
                    }
                    if (!this.level().isClientSide) {
                        this.catchingFish(blockpos);
                    }
                } else {
                    this.outOfWaterTime = Math.min(10, this.outOfWaterTime + 1);
                }
            }
            if (!fluidstate.is(FluidTags.WATER)) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.03D, 0.0D));
            }
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.updateRotation();
            if (this.currentState.isFlying() && (this.onGround() || this.horizontalCollision))
                this.setDeltaMovement(Vec3.ZERO);
            this.setDeltaMovement(this.getDeltaMovement().scale(.92));
            this.reapplyPosition();
        }
    }

    private boolean shouldStopFishing(LivingEntity pPlayer) {
        ItemStack itemstack = pPlayer.getMainHandItem();
        ItemStack itemstack1 = pPlayer.getOffhandItem();
        boolean flag = itemstack.canPerformAction(net.minecraftforge.common.ToolActions.FISHING_ROD_CAST);
        boolean flag1 = itemstack1.canPerformAction(net.minecraftforge.common.ToolActions.FISHING_ROD_CAST);
        if (!pPlayer.isRemoved() && pPlayer.isAlive() && (flag || flag1) && !(this.distanceToSqr(pPlayer) > 1024.0D)) {
            return false;
        } else {
            this.discard();
            return true;
        }
    }

    private void catchingFish(BlockPos pPos) {
        ServerLevel serverlevel = (ServerLevel)this.level();
        int i = 1;
        BlockPos blockpos = pPos.above();
        if (this.random.nextFloat() < 0.25F && this.level().isRainingAt(blockpos)) {
            ++i;
        }
        if (this.random.nextFloat() < 0.5F && !this.level().canSeeSky(blockpos)) {
            --i;
        }
        if (this.nibble > 0) {
            --this.nibble;
            if (this.nibble <= 0) {
                this.timeUntilLured = 0;
                this.timeUntilHooked = 0;
                this.getEntityData().set(DATA_BITING, false);
            }
        } else if (this.timeUntilHooked > 0) {
            this.timeUntilHooked -= i;
            if (this.timeUntilHooked > 0) {
                this.fishAngle += (float)this.random.triangle(0.0D, 9.188D);
                float f = this.fishAngle * ((float)Math.PI / 180F);
                float f1 = Mth.sin(f);
                float f2 = Mth.cos(f);
                double d0 = this.getX() + (double)(f1 * (float)this.timeUntilHooked * 0.1F);
                double d1 = (float)Mth.floor(this.getY()) + 1.0F;
                double d2 = this.getZ() + (double)(f2 * (float)this.timeUntilHooked * 0.1F);
                BlockState blockstate = serverlevel.getBlockState(BlockPos.containing(d0, d1 - 1.0D, d2));
                if (blockstate.is(Blocks.WATER)) {
                    if (this.random.nextFloat() < 0.15F) {
                        serverlevel.sendParticles(ParticleTypes.BUBBLE, d0, d1 - .1, d2, 1, f1, .1, f2, 0);
                    }
                    float f3 = f1 * 0.04F;
                    float f4 = f2 * 0.04F;
                    serverlevel.sendParticles(ParticleTypes.FISHING, d0, d1, d2, 0, f4, 0.01D, -f3, 1.0D);
                    serverlevel.sendParticles(ParticleTypes.FISHING, d0, d1, d2, 0, -f4, 0.01D, f3, 1.0D);
                }
            } else {
                this.playSound(SoundEvents.FISHING_BOBBER_SPLASH, 0.25F, 1.0F + (this.random.nextFloat() -
                        this.random.nextFloat()) * 0.4F);
                double d3 = this.getY() + 0.5D;
                serverlevel.sendParticles(ParticleTypes.BUBBLE, this.getX(), d3, this.getZ(), (int)(1.0F + this.getBbWidth() * 20.0F), this.getBbWidth(),
                        0.0D, this.getBbWidth(), 0.2F);
                serverlevel.sendParticles(ParticleTypes.FISHING, this.getX(), d3, this.getZ(), (int)(1.0F + this.getBbWidth() * 20.0F), this.getBbWidth(),
                        0.0D, this.getBbWidth(), 0.2F);
                this.nibble = Mth.nextInt(this.random, 20, 40);
                this.getEntityData().set(DATA_BITING, true);
            }
        } else if (this.timeUntilLured > 0) {
            this.timeUntilLured -= i;
            float f5 = 0.15F;
            if (this.timeUntilLured < 20) {
                f5 += (float)(20 - this.timeUntilLured) * 0.05F;
            } else if (this.timeUntilLured < 40) {
                f5 += (float)(40 - this.timeUntilLured) * 0.02F;
            } else if (this.timeUntilLured < 60) {
                f5 += (float)(60 - this.timeUntilLured) * 0.01F;
            }
            if (this.random.nextFloat() < f5) {
                float f6 = Mth.nextFloat(this.random, 0.0F, 360.0F) * Maths.PI_DIVIDING_180;
                float f7 = Mth.nextFloat(this.random, 25.0F, 60.0F);
                double d4 = this.getX() + (double)(Mth.sin(f6) * f7) * 0.1D;
                double d5 = (float)Mth.floor(this.getY()) + 1.0F;
                double d6 = this.getZ() + (double)(Mth.cos(f6) * f7) * 0.1D;
                BlockState blockstate1 = serverlevel.getBlockState(BlockPos.containing(d4, d5 - 1.0D, d6));
                if (blockstate1.is(Blocks.WATER)) {
                    serverlevel.sendParticles(ParticleTypes.SPLASH, d4, d5, d6, 2 + this.random.nextInt(2), .1,
                            0, .1, 0);
                }
            }
            if (this.timeUntilLured <= 0) {
                this.fishAngle = Mth.nextFloat(this.random, 0.0F, 360.0F);
                this.timeUntilHooked = Mth.nextInt(this.random, 20, 80);
            }
        } else {
            this.timeUntilLured = Mth.nextInt(this.random, 100, 600);
            this.timeUntilLured -= this.lureSpeed * 20 * 5;
        }
    }

    private boolean calculateOpenWater(BlockPos pPos) {
        OpenWaterType fishinghook$openwatertype = OpenWaterType.INVALID;
        for (int i = -1; i <= 2; ++i) {
            OpenWaterType area = this.getOpenWaterTypeForArea(pPos.offset(-2, i, -2), pPos.offset(2, i, 2));
            switch (area) {
                case INVALID:
                    return false;
                case ABOVE_WATER:
                    if (fishinghook$openwatertype == OpenWaterType.INVALID) {
                        return false;
                    }
                    break;
                case INSIDE_WATER:
                    if (fishinghook$openwatertype == OpenWaterType.ABOVE_WATER) {
                        return false;
                    }
            }
            fishinghook$openwatertype = area;
        }
        return true;
    }

    private OpenWaterType getOpenWaterTypeForArea(BlockPos pFirstPos, BlockPos pSecondPos) {
        return BlockPos.betweenClosedStream(pFirstPos, pSecondPos).map(this::getOpenWaterTypeForBlock)
                .reduce((type, type1) -> type == type1 ? type : OpenWaterType.INVALID)
                .orElse(OpenWaterType.INVALID);
    }

    private OpenWaterType getOpenWaterTypeForBlock(BlockPos p_37164_) {
        BlockState blockstate = this.level().getBlockState(p_37164_);
        if (!blockstate.isAir() && !blockstate.is(Blocks.LILY_PAD)) {
            FluidState fluidstate = blockstate.getFluidState();
            return fluidstate.is(FluidTags.WATER) && fluidstate.isSource() && blockstate.getCollisionShape(this.level(), p_37164_).isEmpty() ?
                    OpenWaterType.INSIDE_WATER : OpenWaterType.INVALID;
        } else {
            return OpenWaterType.ABOVE_WATER;
        }
    }

    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);
        this.setDeltaMovement(this.getDeltaMovement().normalize().scale(pResult.distanceTo(this)));
    }

    public boolean shouldRenderAtSqrDistance(double pDistance) {
        return pDistance < 4096.0D;
    }

    public void lerpTo(double pX, double pY, double pZ, float pYRot, float pXRot, int pLerpSteps, boolean pTeleport) {
    }

    protected MovementEmission getMovementEmission() {
        return MovementEmission.NONE;
    }

    public void remove(RemovalReason pReason) {
        this.updateOwnerInfo(null);
        super.remove(pReason);
    }

    public void setOwner(@Nullable Entity pOwner) {
        super.setOwner(pOwner);
        this.updateOwnerInfo(this);
    }

    public void onClientRemoval() {
        this.updateOwnerInfo(null);
    }

    private void updateOwnerInfo(@Nullable ModFishingHook hook) {
        Entity owner = this.getOwner();
        if (owner instanceof Fishman player) {
            player.fishing = hook;
        }
    }

    public boolean canChangeDimensions() {
        return false;
    }

    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this,
                (ValueHolder.nullToOther(this.getOwner(), this).getId()));
    }

    public void recreateFromPacket(ClientboundAddEntityPacket pPacket) {
        super.recreateFromPacket(pPacket);
        if (this.getOwner() == null) {
            this.kill();
        }
    }

    public enum FishHookState {
        FLYING,
        BOBBING;

        public boolean isFlying() {
            return this.equals(FLYING);
        }
    }

    public enum OpenWaterType {
        ABOVE_WATER,
        INSIDE_WATER,
        INVALID
    }

    static {
        DATA_HOOKED_ID = SynchedEntityData.defineId(ModFishingHook.class, EntityDataSerializers.INT);
        DATA_BITING = SynchedEntityData.defineId(ModFishingHook.class, EntityDataSerializers.BOOLEAN);
    }
}
