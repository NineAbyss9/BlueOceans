
package com.bilibili.player_ix.blue_oceans.common.entities.villagers;

import com.bilibili.player_ix.blue_oceans.api.mob.IAcceptTask;
import com.bilibili.player_ix.blue_oceans.api.mob.ICitizen;
import com.bilibili.player_ix.blue_oceans.api.mob.IMiner;
import com.bilibili.player_ix.blue_oceans.api.mob.Profession;
import com.bilibili.player_ix.blue_oceans.api.task.Task;
import com.bilibili.player_ix.blue_oceans.common.entities.ai.behavior.Behavior;
import com.bilibili.player_ix.blue_oceans.common.entities.ai.behavior.BehaviorFlag;
import com.bilibili.player_ix.blue_oceans.government.Government;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansEntities;
import com.github.player_ix.ix_api.api.ApiPose;
import com.github.player_ix.ix_api.api.item.ItemStacks;
import com.github.player_ix.ix_api.api.mobs.FoodDataUser;
import com.github.player_ix.ix_api.api.mobs.MobFoodData;
import com.github.player_ix.ix_api.api.mobs.OwnableMob;
import com.github.player_ix.ix_api.util.MobSelector;
import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.PoiTypeTags;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.gossip.GossipContainer;
import net.minecraft.world.entity.ai.gossip.GossipType;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.ai.village.ReputationEventType;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import org.NineAbyss9.util.function.FunctionCollector;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BooleanSupplier;

public class BaseVillager
extends AbstractHuntingVillager
implements ICitizen, IAcceptTask, ReputationEventHandler, FoodDataUser, IMiner {
    private Profession profession = Profession.EMPTY;
    private Government government;
    private final MobFoodData foodData;
    private final GossipContainer gossips = new GossipContainer();
    private static final EntityDataAccessor<Integer> DATA_TASK;
    private static final EntityDataAccessor<Boolean> DATA_IS_AGENT;
    private static final EntityDataAccessor<Optional<BlockPos>> DATA_TARGET_POS;
    public BaseVillager(EntityType<? extends BaseVillager> pType, Level level) {
        super(pType, level);
        this.government = Government.empty();
        this.foodData = this.createFoodData();
        this.setAgent(level.getRandom().nextInt(8) == 0);
        this.setHandItemToDaily();
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 16.0F);
    }

    protected PathNavigation createNavigation(Level pLevel) {
        GroundPathNavigation base = new GroundPathNavigation(this, pLevel);
        base.setCanFloat(true);
        base.setCanOpenDoors(true);
        return base;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_TASK, 0);
        this.entityData.define(DATA_IS_AGENT, false);
        this.entityData.define(DATA_TARGET_POS, Optional.empty());
    }

    public void registerBehaviors() {
        this.addAttackBehaviors();
    }

    protected void addAttackBehaviors() {
        this.behaviorSelector.addBehavior(2, new VillagerAttackBehavior(this, 1.0,
                true));
    }

    protected void registerGoals() {
        OwnableMob.addBehaviorGoals(this, 6, 0.7, 8F, true, false);
        this.addTargetGoal();
    }

    protected void addTargetGoal() {
        this.targetSelector.addGoal(2, new AttackTargetGoal(this));
        this.targetSelector.addGoal(2, new AgentAttackTargetGoal(this));
        this.targetSelector.addGoal(3, new VillagerHurtByTargetGoal(this,
                BaseVillager.class));
    }

    public boolean hasFarmSeeds() {
        return this.getInventory().hasAnyMatching(stack ->
                stack.is(ItemTags.VILLAGER_PLANTABLE_SEEDS));
    }

    public Government getGovernment() {
        return this.government;
    }

    public void setGovernment(Government newGovernment) {
        this.government = newGovernment;
    }

    public void onReputationEventFrom(ReputationEventType pType, Entity pTarget) {
        if (pType == ReputationEventType.ZOMBIE_VILLAGER_CURED) {
            this.gossips.add(pTarget.getUUID(), GossipType.MAJOR_POSITIVE, 20);
            this.gossips.add(pTarget.getUUID(), GossipType.MINOR_POSITIVE, 25);
        } else if (pType == ReputationEventType.TRADE) {
            this.gossips.add(pTarget.getUUID(), GossipType.TRADING, 2);
        } else if (pType == ReputationEventType.VILLAGER_HURT) {
            this.gossips.add(pTarget.getUUID(), GossipType.MINOR_NEGATIVE, 25);
        } else if (pType == ReputationEventType.VILLAGER_KILLED) {
            this.gossips.add(pTarget.getUUID(), GossipType.MAJOR_NEGATIVE, 25);
        }
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        this.addCitizenAdditionalSaveData(pCompound);
        ListTag villagerData = new ListTag();
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("isAgent", this.isAgent());
        tag.putInt("CurrentTask", this.getTaskId());
        villagerData.add(tag);
        pCompound.put("VillagerData", villagerData);
        CompoundTag foodTag = this.foodData.integration();
        pCompound.put("FoodData", foodTag);
        pCompound.put("Inventory", this.getInventory().createTag());
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.foodData.readIntegration(pCompound);
        if (pCompound.get("VillagerData") instanceof ListTag villagerData) {
            CompoundTag tag = (CompoundTag)villagerData.get(0);
            this.setAgent(tag.getBoolean("isAgent"));
            this.setTask(tag.getInt("CurrentTask"));
        }
        if (pCompound.get("Inventory") instanceof ListTag listTag) {
            this.getInventory().fromTag(listTag);
        }
    }

    public ApiPose getArmPose() {
        if (this.isAggressive())
            return ApiPose.ATTACKING;
        return ApiPose.CROSSED;
    }

    public MobFoodData foodData() {
        return foodData;
    }

    public void copyTo(BaseVillager other) {
        other.setPos(this.position());
        other.setAgent(this.isAgent());
        other.setGovernment(this.getGovernment());
        ListTag list = this.getInventory().createTag();
        other.getInventory().fromTag(list);
    }

    public void spawnBaby() {
        if (!level().isClientSide) {
            BaseVillager villager = BlueOceansEntities.BASE_VILLAGER.get().create(level());
            FunctionCollector.accept(villager, pVillager -> {
                pVillager.moveTo(this.position());
                pVillager.setGovernment(this.getGovernment());
                if (!level().addFreshEntity(pVillager))
                    pVillager.discard();
            });
        }
    }

    public Task getTask() {
        return Task.fromId(this.entityData.get(DATA_TASK));
    }

    public boolean isWorking() {
        return this.getTask() == Task.WORK;
    }

    public void setTask(int pTask) {
        this.entityData.set(DATA_TASK, pTask);
    }

    @Nullable
    public BlockPos targetPos() {
        return this.entityData.get(DATA_TARGET_POS).orElse(null);
    }

    public void setTargetPos(@Nullable BlockPos pPos) {
        this.entityData.set(DATA_TARGET_POS, Optional.ofNullable(pPos));
    }

    /**Gets the profession of a {@linkplain BaseVillager}*/
    public Profession getProfession() {
        return profession;
    }

    /**This will only work on {@linkplain BaseVillager}*/
    public void setProfession(Profession pProfession) {
        this.profession = pProfession;
    }

    public boolean wantsToPickUp(ItemStack pStack) {
        Item item = pStack.getItem();
        VillagerProfession villagerProfession = getProfession().villagerProfession();
        if (!this.getInventory().canAddItem(pStack))
            return false;
        return this.getWantedItems().contains(item) || (villagerProfession != null
                && villagerProfession.requestedItems().contains(item)) || this.getTask().item.sameStack(pStack) ||
                this.getProfession().requestedItems.contains(item);
    }

    //Enable the mobGriefing rule!
    protected void pickUpItem(ItemEntity pItemEntity) {
        InventoryCarrier.pickUpItem(this, this, pItemEntity);
    }

    protected ItemStack getAttackItem() {
        return ItemStacks.of(Items.IRON_AXE);
    }

    public boolean canAttackEvenBaby() {
        return false;
    }

    public boolean isAgent() {
        return this.entityData.get(DATA_IS_AGENT);
    }

    public void setAgent(boolean agent) {
        this.entityData.set(DATA_IS_AGENT, agent);
    }

    public static AttributeSupplier.Builder createBaseVillagerAttributes() {
        return createPathAttributes().add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.ATTACK_DAMAGE, 4)
                .add(Attributes.MAX_HEALTH, 20)
                .add(Attributes.FOLLOW_RANGE, 64)
                .add(ForgeMod.BLOCK_REACH.get(), 4);
    }

    static {
        DATA_TASK = SynchedEntityData.defineId(BaseVillager.class, EntityDataSerializers.INT);
        DATA_IS_AGENT = SynchedEntityData.defineId(BaseVillager.class, EntityDataSerializers.BOOLEAN);
        DATA_TARGET_POS = SynchedEntityData.defineId(BaseVillager.class, EntityDataSerializers.OPTIONAL_BLOCK_POS);
    }

    private static class AttackTargetGoal extends NearestAttackableTargetGoal<LivingEntity> {
        public AttackTargetGoal(Mob pMob) {
            super(pMob, LivingEntity.class, true, MobSelector.isEnemy());
        }

        public boolean canUse() {
            if (this.mob instanceof BaseVillager villager
                && villager.isBaby()) {
                return villager.canAttackEvenBaby() && super.canUse();
            }
            return super.canUse();
        }
    }

    protected class AgentAttackTargetGoal extends NearestAttackableTargetGoal<LivingEntity> {
        public AgentAttackTargetGoal(Mob pMob) {
            super(pMob, LivingEntity.class, true, entity -> {
                if (entity instanceof BaseVillager villager) {
                    return !villager.isAgent();
                }
                return entity instanceof AbstractVillager;
            });
        }

        public boolean canUse() {
            if (!BaseVillager.this.isAgent() || BaseVillager.this.isBaby() && !BaseVillager.this.canAttackEvenBaby()) {
                return false;
            }
            if (BaseVillager.this.getRandom().nextFloat() < 0.1F) {
                return false;
            }
            List<AbstractVillager> villagers = BaseVillager.this.level().getEntitiesOfClass(AbstractVillager.class,
                    BaseVillager.this.getBoundingBox().inflate(16));
            List<LivingEntity> entities = BaseVillager.this.level().getEntitiesOfClass(LivingEntity.class,
                    BaseVillager.this.getBoundingBox().inflate(16), e->e!=BaseVillager.this);
            if (villagers.isEmpty() || entities.size() > 3) {
                return false;
            }
            return super.canUse();
        }
    }

    public static class MoveThroughVillageBehavior extends Behavior {
        protected final PathfinderMob mob;
        private final double speedModifier;
        @Nullable
        private Path path;
        private BlockPos poiPos;
        private final boolean onlyAtNight;
        private final List<BlockPos> visited = Lists.newArrayList();
        private final int distanceToPoi;
        private final BooleanSupplier canDealWithDoors;

        public MoveThroughVillageBehavior(PathfinderMob pMob, double pSpeedModifier, boolean pOnlyAtNight,
                                          int pDistanceToPoi, BooleanSupplier pCanDealWithDoors) {
            this.mob = pMob;
            this.speedModifier = pSpeedModifier;
            this.onlyAtNight = pOnlyAtNight;
            this.distanceToPoi = pDistanceToPoi;
            this.canDealWithDoors = pCanDealWithDoors;
            this.setFlags(EnumSet.of(BehaviorFlag.MOVE));
        }

        public boolean canUse() {
            this.updateVisited();
            if (this.onlyAtNight && this.mob.level().isDay()) {
                return false;
            } else {
                if (this.mob.getTarget() != null)
                    return false;
                ServerLevel serverlevel = (ServerLevel)this.mob.level();
                BlockPos blockpos = this.mob.blockPosition();
                if (!serverlevel.isCloseToVillage(blockpos, 6)) {
                    return false;
                } else {
                    Vec3 vec3 = LandRandomPos.getPos(this.mob, 15, 7, (p_217751_) -> {
                        if (!serverlevel.isVillage(p_217751_)) {
                            return Double.NEGATIVE_INFINITY;
                        } else {
                            Optional<BlockPos> optional1 = serverlevel.getPoiManager().find((p_217758_)
                                    -> p_217758_.is(PoiTypeTags.VILLAGE), this::hasNotVisited, p_217751_, 10,
                                    PoiManager.Occupancy.IS_OCCUPIED);
                            return optional1.map((p_217754_) -> -p_217754_.distSqr(blockpos)).orElse(Double
                                    .NEGATIVE_INFINITY);
                        }
                    });
                    if (vec3 == null) {
                        return false;
                    } else {
                        Optional<BlockPos> optional = serverlevel.getPoiManager().find((p_217756_) ->
                                p_217756_.is(PoiTypeTags.VILLAGE), this::hasNotVisited, BlockPos.containing(vec3),
                                10, PoiManager.Occupancy.IS_OCCUPIED);
                        if (optional.isEmpty()) {
                            return false;
                        } else {
                            this.poiPos = optional.get().immutable();
                            GroundPathNavigation groundpathnavigation = (GroundPathNavigation)this.mob.getNavigation();
                            boolean flag = groundpathnavigation.canOpenDoors();
                            groundpathnavigation.setCanOpenDoors(this.canDealWithDoors.getAsBoolean());
                            this.path = groundpathnavigation.createPath(this.poiPos, 0);
                            groundpathnavigation.setCanOpenDoors(flag);
                            if (this.path == null) {
                                Vec3 vec31 = DefaultRandomPos.getPosTowards(this.mob, 10, 7,
                                        Vec3.atBottomCenterOf(this.poiPos), (float)Math.PI / 2F);
                                if (vec31 == null) {
                                    return false;
                                }
                                groundpathnavigation.setCanOpenDoors(this.canDealWithDoors.getAsBoolean());
                                this.path = this.mob.getNavigation().createPath(vec31.x, vec31.y, vec31.z, 0);
                                groundpathnavigation.setCanOpenDoors(flag);
                                if (this.path == null) {
                                    return false;
                                }
                            }
                            for (int i = 0;i < this.path.getNodeCount();++i) {
                                Node node = this.path.getNode(i);
                                BlockPos blockpos1 = new BlockPos(node.x, node.y + 1, node.z);
                                if (DoorBlock.isWoodenDoor(this.mob.level(), blockpos1)) {
                                    this.path = this.mob.getNavigation().createPath(node.x, node.y, node.z, 0);
                                    break;
                                }
                            }
                            return this.path != null;
                        }
                    }
                }
            }
        }

        public boolean canContinueToUse() {
            if (this.mob.getNavigation().isDone()) {
                return false;
            } else {
                return !this.poiPos.closerToCenterThan(this.mob.position(), this.mob.getBbWidth()
                        + (float)this.distanceToPoi);
            }
        }

        public void start() {
            this.mob.getNavigation().moveTo(this.path, this.speedModifier);
        }

        public void stop() {
            if (this.mob.getNavigation().isDone() || this.poiPos.closerToCenterThan(this.mob.position(), this.distanceToPoi)) {
                this.visited.add(this.poiPos);
            }
        }

        private boolean hasNotVisited(BlockPos p_25593_) {
            for (BlockPos blockpos : this.visited) {
                if (Objects.equals(p_25593_, blockpos)) {
                    return false;
                }
            }
            return true;
        }

        private void updateVisited() {
            if (this.visited.size() > 15) {
                this.visited.remove(0);
            }
        }
    }

    public static class VillagerBreedBehavior extends Behavior {
        public VillagerBreedBehavior(BaseVillager pVillager) {

        }
    }
}
