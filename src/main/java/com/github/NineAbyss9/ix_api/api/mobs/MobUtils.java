
package com.github.NineAbyss9.ix_api.api.mobs;

import org.NineAbyss9.annotation.PAMAreNonnullByDefault;
import com.github.NineAbyss9.ix_api.util.ObjectUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.Team;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

@PAMAreNonnullByDefault
public record MobUtils(Entity entity) {
    public static final Predicate<LivingEntity> avoidEntityPredicate_RangedEnemy;

    public void floatOnLava() {
        floatOnLava(this.entity);
    }

    public static void floatOnLava(Entity entity) {
        floatFluid(entity, 1);
    }

    public static void floatFluid(Entity entity, int i) {
        if (i == 0) {
            if (entity.isInWater()) {
                entity.getDeltaMovement().scale(0.5).add(0, 0.05, 0);
            }
        }
        if (i == 1) {
            if (entity.isInLava()) {
                entity.getDeltaMovement().scale(0.5).add(0, 0.1, 0);
            }
        }
    }

    @SuppressWarnings("deprecation")
    public static <T extends Mob> void registerSpawn(EntityType<T> p_21755_, SpawnPlacements.Type p_21756_,
                                                     Heightmap.Types p_21757_, SpawnPlacements.SpawnPredicate<T> p_21758_) {
        SpawnPlacements.register(p_21755_, p_21756_, p_21757_, p_21758_);
    }

    public static boolean illagerSpawnPredicate(EntityType<? extends AbstractIllager> entityType, ServerLevelAccessor
            world, MobSpawnType reason, BlockPos blockPos, RandomSource random) {
        return Mob.checkMobSpawnRules(entityType, world, reason, blockPos, random) && blockPos.asLong() > 60;
    }

    public static <T extends Mob> SpawnPlacements.SpawnPredicate<T> monsterSpawnPredicate() {
        return (entityType, world, reason, pos, random) ->
                (world.getDifficulty() != Difficulty.PEACEFUL && Monster.isDarkEnoughToSpawn(world, pos, random)
                        && Mob.checkMobSpawnRules(entityType, world, reason, pos, random));
    }

    public boolean isEasy() {
        return isEasy(this.entity);
    }

    public static boolean isEasy(Entity entity) {
        return entity.level().getDifficulty().equals(Difficulty.EASY);
    }

    public boolean isNormal() {
        return isNormal(this.entity);
    }

    public static boolean isNormal(Entity entity) {
        return entity.level().getDifficulty().equals(Difficulty.NORMAL);
    }

    public boolean isHard() {
        return isHard(this.entity);
    }

    public static boolean isHard(Entity entity) {
        return entity.level().getDifficulty().equals(Difficulty.HARD);
    }

    public static void healLiving(LivingEntity living, float amount) {
        if (living.isAlive()) {
            if (amount > 0) {
                float v = amount;
                v = Math.min(living.getMaxHealth() - living.getHealth(), v);
                living.setHealth(living.getHealth() + v);
            }
        }
    }

    public static boolean isDead(LivingEntity living) {
        return living.getHealth() <= 0;
    }

    public static boolean isHalfHealth(LivingEntity living) {
        return living.getHealth() <= living.getMaxHealth() / 2;
    }

    @Nullable
    public static Entity hurtSource(DamageSource source) {
        return source.getDirectEntity() == null ? source.getEntity() : source.getDirectEntity();
    }

    public static boolean areAllies(Entity entity, Entity entity1) {
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

    public static boolean ownableCanHurt(LivingEntity living, @Nullable Entity entity) {
        if (entity instanceof Ownable own) {
            if (own.isHostile()) {
                return living instanceof AbstractVillager || living instanceof AbstractGolem || living instanceof Player
                        || living instanceof ApiVillager;
            } else {
                return living instanceof Enemy;
            }
        } else {
            return MobUtils.canHurt(living, entity);
        }
    }

    public static boolean canHurt(LivingEntity entity, @Nullable Entity sourceMob) {
        if (sourceMob == entity) {
            return false;
        }
        if (sourceMob instanceof Ownable ownableMob) {
            if (ownableMob.getOwner() == entity) {
                return false;
            }
            if (entity instanceof Ownable ownable && ObjectUtil.nonnullEquals(ownable.getOwner(), ownableMob.getOwner())) {
                return false;
            }
        }
        if (entity instanceof Ownable ownableMob) {
            if (sourceMob != null && ownableMob.getOwner() == sourceMob) {
                return false;
            }
            if (sourceMob instanceof Ownable ownable && ObjectUtil.nonnullEquals(ownableMob.getOwner(), ownable.getOwner())) {
                return false;
            }
        }
        if (sourceMob instanceof TraceableEntity ownableMob) {
            if (ownableMob.getOwner() == entity) {
                return false;
            }
            if (entity instanceof TraceableEntity ownable && ObjectUtil.nonnullEquals(ownable.getOwner(), ownableMob.getOwner())) {
                return false;
            }
        }
        if (entity instanceof TraceableEntity ownableMob) {
            if (sourceMob != null && ownableMob.getOwner() == sourceMob) {
                return false;
            }
            if (sourceMob instanceof TraceableEntity ownable && ObjectUtil.nonnullEquals(ownableMob.getOwner(),
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

    public static void push(double x, double y, double z, Entity entity, double xSpeed, double ySpeed, double zSpeed) {
        List<LivingEntity> list = entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(x, y, z));
        for (LivingEntity lie : list) {
            if (MobUtils.canHurt(lie, entity)) {
                lie.push(xSpeed, ySpeed, zSpeed);
            }
        }
    }

    public static boolean isAlive(Entity e) {
        if (e instanceof LivingEntity living) {
            return living.getHealth() > 0 && !living.isRemoved();
        } else {
            return e.isAlive();
        }
    }

    public static boolean rangeHurt(double x, double y, double z, Entity mob, DamageSource type, float damage, boolean flag) {
        List<LivingEntity> list = mob.level().getEntitiesOfClass(LivingEntity.class, mob.getBoundingBox().inflate(x, y, z));
        for (LivingEntity lie : list) {
            if (flag) {
                return lie.hurt(type, damage);
            } else {
                if (mob instanceof LivingEntity living) {
                    if (MobUtils.canHurt(lie, living)) {
                        return lie.hurt(type, damage);
                    }
                } else {
                    if (MobUtils.canHurt(lie, mob)) {
                        return lie.hurt(type, damage);
                    }
                }
            }
        }
        return false;
    }

    public static void rangeHurt(double x, double y, double z, Entity entity, DamageSource source,
                                 float damage, Predicate<LivingEntity> predicate) {
        List<LivingEntity> list = entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(x, y, z),
                predicate);
        for (LivingEntity lie : list) {
            if (predicate.test(lie)) {
                lie.hurt(source, damage);
            }
        }
    }

    public static void setAttributeValue(@Nullable AttributeInstance instance, double d) {
        if (instance != null) {
            instance.setBaseValue(d);
        }
    }

    public static boolean rangeHurt(double x, double y, double z, Entity mob, DamageSource type, float damage) {
        return MobUtils.rangeHurt(x, y, z, mob, type, damage, false);
    }

    public static void rangeHurtAndFire(double x, double y, double z, Entity mob, DamageSource type,
                                        float damage, int onFireTime) {
        List<LivingEntity> list = mob.level().getEntitiesOfClass(LivingEntity.class, mob.getBoundingBox().inflate(x, y, z));
        for (LivingEntity lie : list) {
            if (mob instanceof LivingEntity living) {
                if (MobUtils.canHurt(lie, living)) {
                    lie.hurt(type, damage);
                    if (!lie.fireImmune()) {
                        lie.setSecondsOnFire(onFireTime);
                    }
                }
            } else {
                if (MobUtils.canHurt(lie, mob)) {
                    lie.hurt(type, damage);
                    if (!lie.fireImmune()) {
                        lie.setSecondsOnFire(onFireTime);
                    }
                }
            }
        }
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

    public static void sweepAttack(LivingEntity attacker, Entity target, DamageSource damageSource, float damage){
        sweepAttack(attacker, target, damageSource, damage, 1.0D);
    }

    public static void sweepAttack(LivingEntity attacker, Entity target, DamageSource damageSource,
                                   float damage, double radius){
        for(LivingEntity livingentity : attacker.level().getEntitiesOfClass(LivingEntity.class,
                target.getBoundingBox().inflate(radius, 0.25D, radius))) {
            if (livingentity != attacker && livingentity != target && !attacker.isAlliedTo(livingentity) &&
                    (!(livingentity instanceof ArmorStand) || !((ArmorStand)livingentity).isMarker()) &&
                    attacker.canAttack(livingentity)) {
                livingentity.knockback(0.4F, Mth.sin(attacker.getYRot() *
                        ((float)Math.PI / 180F)), -Mth.cos(attacker.getYRot() * ((float)Math.PI / 180F)));
                livingentity.hurt(damageSource, damage);
            }
        }
    }

    public static void burnInTheSun(boolean flag, Mob mob, int onFireTime) {
        if (MobUtils.isSunBurnTick(mob) && flag) {
            ItemStack itemstack = mob.getItemBySlot(EquipmentSlot.HEAD);
            if (itemstack.isEmpty()) {
                mob.setSecondsOnFire(onFireTime);
            } else {
                if (itemstack.isDamageableItem()) {
                    itemstack.setDamageValue(itemstack.getDamageValue() + mob.getRandom().nextInt(2));
                    if (itemstack.getDamageValue() >= itemstack.getMaxDamage()) {
                        mob.broadcastBreakEvent(EquipmentSlot.HEAD);
                        mob.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
                    }
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    public static boolean isSunBurnTick(Mob mob) {
        if (mob.level().isDay() && !mob.level().isClientSide) {
            float f = mob.getLightLevelDependentMagicValue();
            BlockPos blockpos = BlockPos.containing(mob.getX(), mob.getEyeY(), mob.getZ());
            boolean flag = mob.isInWaterRainOrBubble() || mob.isInPowderSnow || mob.wasInPowderSnow;
            return f > 0.5F && mob.getRandom().nextFloat() * 30.0F < (f - 0.4F) * 2.0F && !flag
                    && mob.level().canSeeSky(blockpos);
        }
        return false;
    }

    public static void moveToGround(Entity entity) {
        HitResult rayTrace = rayTrace(entity);
        if (rayTrace.getType() == HitResult.Type.BLOCK) {
            BlockHitResult hitResult = (BlockHitResult) rayTrace;
            if (hitResult.getDirection() == Direction.UP) {
                BlockState hitBlock = entity.level().getBlockState(hitResult.getBlockPos());
                if (hitBlock.getBlock() instanceof SlabBlock && hitBlock.getValue(BlockStateProperties.SLAB_TYPE)
                        == SlabType.BOTTOM) {
                    entity.setPos(entity.getX(), hitResult.getBlockPos().getY() + 1.0625F - 0.5f, entity.getZ());
                } else {
                    entity.setPos(entity.getX(), hitResult.getBlockPos().getY() + 1.0625F, entity.getZ());
                }
            }
        }
    }

    public static boolean isHurt(LivingEntity pEntity) {
        return pEntity.getHealth() > 0.0F && pEntity.getHealth() < pEntity.getMaxHealth();
    }

    public static HitResult rayTrace(Entity entity) {
        Vec3 startPos = new Vec3(entity.getX(), entity.getY(), entity.getZ());
        Vec3 endPos = new Vec3(entity.getX(), entity.level().getMinBuildHeight(), entity.getZ());
        return entity.level().clip(new ClipContext(startPos, endPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE,
                entity));
    }

    public static BlockHitResult rayTrace(Entity mob, double distance, boolean fluids) {
        return (BlockHitResult) mob.pick(distance, 1.0F, fluids);
    }

    public static boolean hasLineOfSight(Entity looker, Entity target) {
        if (looker.level() != target.level()) {
            return false;
        } else {
            if (looker instanceof LivingEntity living) {
                return living.hasLineOfSight(target);
            }
            Vec3 vec3 = new Vec3(looker.getX(), looker.getEyeY(), looker.getZ());
            Vec3 vec31 = new Vec3(target.getX(), target.getEyeY(), target.getZ());
            if (vec31.distanceTo(vec3) > 128.0D) {
                return false;
            } else {
                return looker.level().clip(new ClipContext(vec3, vec31, ClipContext.Block.COLLIDER,
                        ClipContext.Fluid.NONE, looker)).getType() == HitResult.Type.MISS;
            }
        }
    }

    public static void moveToLookAt(Mob pMob, double pSpeed) {
        Vec3 vector = pMob.getLookAngle();
        pMob.setDeltaMovement(vector.x * pSpeed, pMob.getDeltaMovement().y, vector.z * pSpeed);
    }

    public static class HostileNearestAttackableTargetGoal
            extends NearestAttackableTargetGoal<LivingEntity> {
        public HostileNearestAttackableTargetGoal(Mob p_26060_, boolean p_26062_, Predicate<LivingEntity> predicate) {
            super(p_26060_, LivingEntity.class, p_26062_, predicate);
        }

        public HostileNearestAttackableTargetGoal(Mob p, boolean b) {
            this(p, b, lie -> lie instanceof AbstractVillager || lie instanceof AbstractGolem || lie instanceof Player
                    || lie instanceof ApiVillager);
        }
    }

    static {
        avoidEntityPredicate_RangedEnemy = lie -> (lie instanceof ApiVillager || lie instanceof AbstractGolem);
    }
}
