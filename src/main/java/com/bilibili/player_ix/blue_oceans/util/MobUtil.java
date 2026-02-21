
package com.bilibili.player_ix.blue_oceans.util;

import com.github.NineAbyss9.ix_api.api.mobs.IShieldUser;
import net.minecraft.world.damagesource.DamageSource;
import org.NineAbyss9.annotation.PAMAreNonnullByDefault;
import com.github.NineAbyss9.ix_api.api.mobs.Ownable;
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
import org.NineAbyss9.math.AbyssMath;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

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

    public static void disableShield(double x, double y, double z, Entity mob) {
        List<Entity> players = mob.level().getEntitiesOfClass(Entity.class, mob.getBoundingBox().inflate(x, y, z));
        if (!players.isEmpty()) {
            for (Entity entityIn : players) {
                if (entityIn instanceof Player player) {
                    player.disableShield(true);
                } else if (entityIn instanceof IShieldUser user) {
                    user.disableShield(false);
                }
            }
        }
    }

    public static void disableShield(Entity pEntity, int pTicks) {
        if (pEntity instanceof Player player && player.isBlocking()) {
            if (!player.level().isClientSide) {
                player.getCooldowns().addCooldown(player.getUseItem().getItem(), pTicks);
                player.stopUsingItem();
                player.level().broadcastEntityEvent(player, AbyssMath.toByte(30));
            }
        } else if (pEntity instanceof IShieldUser user) {
            user.disableShield(false);
        }
    }

    /**From here, the code comes from
     *<a href="https://github.com/Polarice3/Goety-2/blob/1.20/src/main/java/com/Polarice3/Goety/utils/MobUtil.java">...</a>*/
    public static List<LivingEntity> getEntityLivingBaseNearby(LivingEntity livingEntity, double distanceX, double distanceY, double distanceZ, double radius) {
        return getEntitiesNearby(livingEntity, LivingEntity.class, distanceX, distanceY, distanceZ, radius);
    }

    public static  <T extends Entity> List<T> getEntitiesNearby(LivingEntity livingEntity, Class<T> entityClass, double dX, double dY, double dZ, double r) {
        return livingEntity.level().getEntitiesOfClass(entityClass, livingEntity.getBoundingBox().inflate(dX, dY, dZ), e -> e != livingEntity && livingEntity.distanceTo(e) <= r + e.getBbWidth() / 2.0F && e.getY() <= livingEntity.getY() + dY);
    }

    public static void areaAttack(LivingEntity attacker, float range, float height, float arc, DamageSource source, float damage) {
        areaAttack(attacker, range, height, arc, damage, 0, 0, source, true);
    }

    public static void areaAttack(LivingEntity attacker, float range, float height, float arc, float damage, float hpDamage, int shieldBreak, DamageSource damageSource, boolean knockback) {
        areaAttack(attacker, range, height, arc, damage, hpDamage, shieldBreak, damageSource, knockback, null);
    }

    public static void areaAttack(LivingEntity attacker, float range, float height, float arc, float damage, float hpDamage,
                                  int shieldBreak, DamageSource source, boolean knockback, @Nullable Consumer<LivingEntity> attackEffect) {
        List<LivingEntity> entitiesHit = getEntityLivingBaseNearby(attacker, range, height, range, range);
        if (!attacker.level().isClientSide) {
            for (LivingEntity entityHit : entitiesHit) {
                float entityRelativeAngle = getRelativeAngle(attacker, entityHit);
                float entityHitDistance = (float)Math.sqrt((entityHit.getZ() - attacker.getZ()) *
                        (entityHit.getZ() - attacker.getZ()) + (entityHit.getX() - attacker.getX()) * (entityHit.getX() - attacker.getX()));
                if (entityHitDistance <= range && (entityRelativeAngle <= arc / 2 && entityRelativeAngle >= -arc / 2)
                        || (entityRelativeAngle >= 360 - arc / 2 || entityRelativeAngle <= -360 + arc / 2)) {
                    if (canHurt(entityHit, attacker)) {
                        boolean flag = entityHit.hurt(source, damage + (entityHit.getMaxHealth() * hpDamage));
                        if (entityHit.isDamageSourceBlocked(source) && shieldBreak > 0) {
                            disableShield(entityHit, shieldBreak);
                        }
                        if (flag) {
                            double d0 = entityHit.getX() - attacker.getX();
                            double d1 = entityHit.getZ() - attacker.getZ();
                            double d2 = Math.max(d0 * d0 + d1 * d1, 0.001D);
                            if (knockback) {
                                entityHit.push(d0 / d2 * 2.0D, 0.18D, d1 / d2 * 2.0D);
                            }
                            if (attackEffect != null) {
                                attackEffect.accept(entityHit);
                            }
                        }
                    }
                }
            }
        }
    }

    public static float getRelativeAngle(LivingEntity attacker, LivingEntity entityHit) {
        float entityHitAngle = (float)((Math.atan2(entityHit.getZ() - attacker.getZ(), entityHit.getX() - attacker.getX()) * (180 / Math.PI) - 90) % 360);
        float entityAttackingAngle = attacker.yBodyRot % 360;
        if (entityHitAngle < 0) {
            entityHitAngle += 360;
        }
        if (entityAttackingAngle < 0) {
            entityAttackingAngle += 360;
        }
        return entityHitAngle - entityAttackingAngle;
    }

    //To here
}
