
package com.github.NineAbyss9.ix_api.util;

import com.github.NineAbyss9.ix_api.api.annotation.ClientOnly;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.*;
import org.NineAbyss9.annotation.PAMAreNonnullByDefault;
import com.github.NineAbyss9.ix_api.api.annotation.ServerOnly;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.NineAbyss9.math.AbyssMath;
import org.NineAbyss9.math.MathSupport;

@PAMAreNonnullByDefault
public record ParticleUtil(Entity entity) {
    private Level level() {
        return this.entity.level();
    }

    @ClientOnly
    public void addParticle(ParticleOptions pParticleData, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
        if (this.level().isClientSide) {
            this.level().addParticle(pParticleData, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
        }
    }

    @ClientOnly
    public void addParticle(ParticleOptions options, double pXS, double pYS, double pZS) {
        this.addParticle(options, entity.getX(), entity.getY(), entity.getZ(), pXS, pYS, pZS);
    }

    @ClientOnly
    public static void addParticle(Level level, ParticleOptions options, BlockPos pos, double xs, double ys, double zs) {
        level.addParticle(options, pos.getX(), pos.getY(), pos.getZ(), xs, ys, zs);
    }

    @ClientOnly
    public void randomAddParticle(ParticleOptions options, double xScale, double zScale, double pXS, double pYS, double pZS) {
        this.addParticle(options, this.entity.getRandomX(xScale), this.entity.getRandomY(), this.entity.getRandomZ(zScale),
                pXS, pYS, pZS);
    }

    @ClientOnly
    public void randomAddParticle(ParticleOptions options, double pXS, double pYS, double pZS) {
        this.randomAddParticle(options, 0.8,0.8,
                pXS, pYS, pZS);
    }

    @ClientOnly
    public void randomAddParticle(ParticleOptions options) {
        this.randomAddParticle(options, 0, 0, 0);
    }

    @ServerOnly
    public static void addParticleAroundSelf(Entity entity, ParticleOptions options, int count) {
        if (!entity.level().isClientSide()) {
            ServerLevel level = (ServerLevel)entity.level();
            double x = level.getRandom().nextGaussian() * 0.05;
            level.sendParticles(options, entity.getX(), entity.getRandomY(), entity.getZ(), count,
                    1.5, 2, 1.5, x);
        }
    }

    @ClientOnly
    public static void addFlatParticle(ParticleOptions options, Entity entity, double x, double z) {
        if (entity.level().isClientSide()) {
            double rx = entity.getRandomX(x);
            double y = entity.getRandomY();
            double rz = entity.getRandomZ(z);
            entity.level().addParticle(options, rx, y, rz, 0, 0, 0);
        }
    }

    @ClientOnly
    public static void addRedStoneParticle(Entity entity, double x, double y, double z, double xS, double yS, double zS) {
        if (entity.level().isClientSide()) {
            entity.level().addParticle(DustParticleOptions.REDSTONE, x, y, z, xS, yS, zS);
        }
    }

    @ServerOnly
    public static void sendParticles(ServerLevel level, ParticleOptions particle, Vec3 position, int count, double dx,
                                     double dy, double dz, double speed) {
        level.sendParticles(particle, position.x(), position.y(), position.z(), count, dx, dy, dz, speed);
    }

    public void sendParticles(ParticleOptions options, int count, double dx, double dy, double dz, double speed) {
        sendParticles((ServerLevel)this.entity.level(), options, this.entity.position(), count, dx, dy, dz, speed);
    }

    @ServerOnly
    public static void explode(ServerLevel level, Vec3 position) {
        level.sendParticles(ParticleTypes.EXPLOSION_EMITTER, position.x(), position.y(), position.z(), 1,
                0, 0, 0, 0);
    }

    @ClientOnly
    public static void addParticle(Level level, ParticleOptions options, Vec3 pos, double dx, double dy, double dz) {
        if (level.isClientSide) {
            level.addParticle(options, pos.x(), pos.y(), pos.z(), dx, dy, dz);
        }
    }

    @ClientOnly
    public static void addParticle(Level level, ParticleOptions options, Vec3 pos, float[] floats) {
        if (level.isClientSide) {
            level.addParticle(options, pos.x(), pos.y(), pos.z(), floats[0], floats[1], floats[2]);
        }
    }

    @ServerOnly
    public static void serverAddParticle(ServerLevel serverLevel, ParticleOptions options, Vec3 pos) {
        serverLevel.sendParticles(options, pos.x, pos.y, pos.z,
                1, 0, 0, 0, 0);
    }

    @ServerOnly
    public static void spawnAnim(Entity entity, ParticleOptions options) {
        spawnAnim(entity, options, 20);
    }

    public static void spawnAnim(Entity entity, ParticleOptions options, int count) {
        AABB aabb = entity.getBoundingBox();
        sendParticles((ServerLevel)entity.level(), options, entity.position(), count,
                aabb.getXsize() - 0.2, aabb.getYsize(), aabb.getZsize() - 0.2,
                MathSupport.random.nextGaussian() * 0.02);
    }

    @ClientOnly
    public static void spawnAnim(ParticleOptions options, Level level, Entity pos) {
        for (int i = 0;i < 20;i++) {
            level.addParticle(options, pos.getRandomX(1), pos.getRandomY(), pos.getRandomZ(1),
                    0, 0, 0);
        }
    }

    @ServerOnly
    public static void spawnAnim(Entity entity) {
        spawnAnim(entity, ParticleTypes.POOF);
    }

    public static void addBlockParticle(Level pLevel, BlockPos pPos, double pX, double pY, double pZ, double xz, double yz,
                                        double zz) {
        pLevel.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, pLevel.getBlockState(pPos)), pX, pY, pZ, xz, yz, zz);
    }

    public static void addBlockParticle(Level pLevel, BlockPos pPos, double pX, double pY, double pZ) {
        addBlockParticle(pLevel, pPos, pX, pY, pZ, AbyssMath.random(0.3), 0.2, AbyssMath.random(0.3));
    }

    public static void addBlockParticle(Level pLevel, BlockPos pPos) {
        addBlockParticle(pLevel, pPos, pPos.getX() + 0.5, pPos.getY(), pPos.getZ() + 0.5);
    }

    public static ParticleOptions getItemParticleOption(ItemStack stackIn) {
        return new ItemParticleOption(ParticleTypes.ITEM, stackIn);
    }
}
