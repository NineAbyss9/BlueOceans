
package com.bilibili.player_ix.blue_oceans.util;

import org.nine_abyss.annotation.PAMAreNonnullByDefault;
import com.github.player_ix.ix_api.api.mobs.Ownable;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.Team;

import javax.annotation.Nullable;

/**Copy from No.IXApi*/
@PAMAreNonnullByDefault
public record MobUtil(Entity pEntity)
{
    public static boolean areAllies(Entity entity, Entity entity1)
    {
        if (entity instanceof Mob mob) {
            if (entity1 instanceof LivingEntity livingEntity) {
                if (mob.getTarget() == livingEntity) {
                    return false;
                }
            }
        }
        if (entity1 instanceof Mob mob) {
            if (entity instanceof LivingEntity livingEntity) {
                if (mob.getTarget() == livingEntity) {
                    return false;
                }
            }
        }
        return entity.isAlliedTo(entity1) || entity1.isAlliedTo(entity) || entity == entity1 ||
                entity.getTeam() == entity1.getTeam();
    }

    public static boolean ownableCanHurt(LivingEntity living, @Nullable Entity entity)
    {
        if (entity instanceof Ownable own) {
            if (own.isHostile()) {
                return living instanceof AbstractVillager || living instanceof AbstractGolem || living instanceof Player;
            } else {
                return living instanceof Enemy;
            }
        } else {
            return MobUtil.canHurt(living, entity);
        }
    }

    public static void moveToGround(Entity entity) {
        BlockHitResult hitResult = rayTrace(entity);
        if (hitResult.getType() == HitResult.Type.BLOCK
                && hitResult.getDirection() == Direction.UP) {
            BlockState hitBlock = entity.level().getBlockState(hitResult.getBlockPos());
            if (hitBlock.getBlock() instanceof SlabBlock && hitBlock.getValue(BlockStateProperties.SLAB_TYPE)
                    == SlabType.BOTTOM) {
                entity.setPos(entity.getX(), hitResult.getBlockPos().getY() + 1.0625F - 0.5f, entity.getZ());
            } else {
                entity.setPos(entity.getX(), hitResult.getBlockPos().getY() + 1.0625F, entity.getZ());
            }
        }
    }

    public static BlockHitResult rayTrace(Entity entity) {
        Vec3 startPos = new Vec3(entity.getX(), entity.getY(), entity.getZ());
        Vec3 endPos = new Vec3(entity.getX(), entity.level().getMinBuildHeight(), entity.getZ());
        return entity.level().clip(new ClipContext(startPos, endPos, ClipContext.Block.COLLIDER, ClipContext
                .Fluid.NONE, entity));
    }

    public static double ground(Entity entity) {
        Vec3 startPos = new Vec3(entity.getX(), entity.getY(), entity.getZ());
        Vec3 endPos = new Vec3(entity.getX(), entity.level().getMinBuildHeight(), entity.getZ());
        return entity.level().clip(new ClipContext(startPos, endPos, ClipContext.Block.COLLIDER, ClipContext
                .Fluid.NONE, entity)).getBlockPos().getY();
    }

    public static boolean canHurt(LivingEntity entity, @Nullable Entity sourceMob) {
        if (sourceMob == entity) {
            return false;
        }
        if (sourceMob instanceof Ownable ownableMob) {
            if (ownableMob.getOwner() == entity) {
                return false;
            }
            if (entity instanceof Ownable ownable &&
                    ObjUtil.nonnullEquals(ownable.getOwner(), ownableMob.getOwner())) {
                return false;
            }
        }
        if (entity instanceof Ownable ownableMob) {
            if (sourceMob != null && ownableMob.getOwner() == sourceMob) {
                return false;
            }
            if (sourceMob instanceof Ownable ownable && ObjUtil.nonnullEquals(ownableMob.getOwner(),
                    ownable.getOwner())) {
                return false;
            }
        }
        if (sourceMob instanceof TraceableEntity ownableMob) {
            if (ownableMob.getOwner() == entity) {
                return false;
            }
            if (entity instanceof TraceableEntity ownable && ObjUtil.nonnullEquals(ownable.getOwner(),
                    ownableMob.getOwner())) {
                return false;
            }
        }
        if (entity instanceof TraceableEntity ownableMob) {
            if (sourceMob != null && ownableMob.getOwner() == sourceMob) {
                return false;
            }
            if (sourceMob instanceof TraceableEntity ownable && ObjUtil.nonnullEquals(ownableMob.getOwner(),
                    ownable.getOwner())) {
                return false;
            }
        }
        if (entity instanceof Player player && player.isCreative()) {
            return false;
        }
        if (sourceMob != null) {
            Team team = sourceMob.getTeam();
            Team ea = entity.getTeam();
            if (ea != null && ea.equals(team)) {
                return false;
            }
        }
        return entity.isAlive() && !entity.isInvulnerable();
    }

    public static AABB getRange(Mob mob, double dv, double x, double y, double z, double x1, double y1, double z1) {
        float bodyYawRad = mob.yBodyRot * Mth.DEG_TO_RAD;
        double dx = -Mth.sin(bodyYawRad) * dv;
        double dz = Mth.cos(bodyYawRad) * dv;
        Vec3 center = new Vec3(mob.getX() + dx, mob.getY() + mob.getBbHeight() * 0.5f,
                mob.getZ() + dz);
        return new AABB(center.x - x / 2, center.y - y / 2, center.z - z / 2,
                center.x + x1 / 2, center.y + y1 / 2, center.z + z1 / 2);
    }
}
