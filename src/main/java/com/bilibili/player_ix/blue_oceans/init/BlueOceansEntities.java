
package com.bilibili.player_ix.blue_oceans.init;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.common.entities.Paramecium;
import com.bilibili.player_ix.blue_oceans.common.entities.animal.Bear;
import com.bilibili.player_ix.blue_oceans.common.entities.projectile.BulletProjectile;
import com.bilibili.player_ix.blue_oceans.common.entities.projectile.WaterTrap;
import com.bilibili.player_ix.blue_oceans.common.entities.dumplings.DumplingMonster;
import com.bilibili.player_ix.blue_oceans.common.entities.illagers.red_plum_illager.Freak;
import com.bilibili.player_ix.blue_oceans.common.entities.projectile.Venom;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.*;
import com.bilibili.player_ix.blue_oceans.common.entities.illagers.red_plum_illager.Dictator;
import com.bilibili.player_ix.blue_oceans.common.entities.illagers.NaturalEnvoy;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.red_plum_girl.RedPlumGirl;
import com.bilibili.player_ix.blue_oceans.common.entities.traffic.Bike;
import com.bilibili.player_ix.blue_oceans.common.entities.undeads.Death;
import com.bilibili.player_ix.blue_oceans.common.entities.villagers.BaseVillager;
import com.bilibili.player_ix.blue_oceans.common.entities.villagers.HuntingVillager;
import com.bilibili.player_ix.blue_oceans.common.entities.villagers.VillageChief;
import com.bilibili.player_ix.blue_oceans.common.entities.villagers.VillagerBiologist;
import com.bilibili.player_ix.blue_oceans.config.BlueOceansConfig;
import org.nine_abyss.annotation.PFMAreNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@PFMAreNonnullByDefault
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlueOceansEntities {
    public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, BlueOceans.MOD_ID);
    public static final RegistryObject<EntityType<BaseVillager>> BASE_VILLAGER = registryObject("base_villager", EntityType.Builder.of(BaseVillager::new, MobCategory.MISC), 0.6F, 1.95F);
    public static final RegistryObject<EntityType<Bear>> BEAR = registryObject("bear", EntityType.Builder.of(Bear::new, MobCategory.CREATURE).clientTrackingRange(10), 1.4F, 1.4F);
    public static final RegistryObject<EntityType<BulletProjectile>> BULLET = registryObject("bullet", EntityType.Builder.<BulletProjectile>of(BulletProjectile::new, MobCategory.MISC).fireImmune(), 0.4F, .25F);
    public static final RegistryObject<EntityType<Death>> DEATH = registryObject("death", EntityType.Builder.<Death>of(Death::new, MobCategory.MONSTER).setCustomClientFactory(Death::new), 0.6f, 1.6f);
    public static final RegistryObject<EntityType<Dictator>> DICTATOR = registryObject("dictator", EntityType.Builder.<Dictator>of(Dictator::new, MobCategory.MONSTER).setCustomClientFactory(Dictator::new), 0.6f, 1.95f);
    public static final RegistryObject<EntityType<DumplingMonster>> DUMPLING_MONSTER = registryObject("dumpling_monster", EntityType.Builder.<DumplingMonster>of(DumplingMonster::new, MobCategory.MONSTER).setCustomClientFactory(DumplingMonster::new), 0.6f,1f);
    public static final RegistryObject<EntityType<Freak>> FREAK = registryObject("freak", EntityType.Builder.<Freak>of(Freak::new, MobCategory.MONSTER).setCustomClientFactory(Freak::new), 0.6f, 1.95f);
    public static final RegistryObject<EntityType<HeartOfHorror>> HEART_OF_HORROR = registryObject("heart_of_horror", EntityType.Builder.<HeartOfHorror>of(HeartOfHorror::new, MobCategory.MONSTER).setCustomClientFactory(HeartOfHorror::new), 3, 2);
    public static final RegistryObject<EntityType<HuntingVillager>> HUNTING_VILLAGER = registryObject("hunting_villager", EntityType.Builder.<HuntingVillager>of(HuntingVillager::new, MobCategory.MISC), 0.6f, 1.95f);
    public static final RegistryObject<EntityType<NaturalEnvoy>> NATURAL_ENVOY = registryObject("natural_envoy", EntityType.Builder.<NaturalEnvoy>of(NaturalEnvoy::new, MobCategory.MONSTER).setCustomClientFactory(NaturalEnvoy::new), 0.6f, 1.95f);
    public static final RegistryObject<EntityType<NeoPlum>> NEO_PLUM = registryObject("neo_plum", EntityType.Builder.of(NeoPlum::new, MobCategory.MONSTER), 0.5f, 0.5f);
    public static final RegistryObject<EntityType<PlumFactory>> PLUM_FACTORY = registryObject("plum_factory", EntityType.Builder.of(PlumFactory::new, MobCategory.MONSTER), 1.25F, .9F);
    public static final RegistryObject<EntityType<RedPlumCow>> RED_PLUMS_COW = register("red_plums_cow",
            EntityType.Builder.<RedPlumCow>of(RedPlumCow::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(RedPlumCow::new).sized(0.9f, 1.4f));
    public static final RegistryObject<EntityType<RedPlumGirl>> RED_PLUM_GIRL = register("red_plum_girl",EntityType.Builder.<RedPlumGirl>of(RedPlumGirl::new, MobCategory.MISC).clientTrackingRange(1024).setCustomClientFactory(RedPlumGirl::new).fireImmune().sized(0.6f, 1.95f));
    public static final RegistryObject<EntityType<RedPlumSlayer>> RED_PLUM_SLAYER = registryObject("red_plum_slayer", EntityType.Builder.of(RedPlumSlayer::new, MobCategory.MONSTER), 0.6F, 1.95F);
    public static final RegistryObject<EntityType<RedPlumSpider>> RED_PLUM_SPIDER = registryObject("red_plum_spider", EntityType.Builder.of(RedPlumSpider::new, MobCategory.MONSTER), 1.5F, 1.0F);
    public static final RegistryObject<EntityType<RedPlumWorm>> RED_PLUM_WORM = registryObject("red_plum_worm", EntityType.Builder.<RedPlumWorm>of(RedPlumWorm::new, MobCategory.MONSTER).setCustomClientFactory(RedPlumWorm::new),  0.4f, 0.3f);
    public static final RegistryObject<EntityType<Venom>> VENOM = register("venom", EntityType.Builder.<Venom>of(Venom::new, MobCategory.MISC).fireImmune().sized(0.6f, 0.8f).clientTrackingRange(6));
    public static final RegistryObject<EntityType<VillagerBiologist>> VILLAGER_BIOLOGIST = register("villager_biologist", EntityType.Builder.of(VillagerBiologist::new, MobCategory.MISC).sized(0.6f, 1.95f).clientTrackingRange(8));
    public static final RegistryObject<EntityType<WaterTrap>> WATER_TRAP = BlueOceansEntities.register("water_trap", EntityType.Builder.<WaterTrap>of(WaterTrap::new, MobCategory.MISC).clientTrackingRange(8).sized(0.6f, 0.8f));
    public static final RegistryObject<EntityType<RedPlumSkeleton>> RED_PLUM_SKELETON = registryObject("red_plum_skeleton", EntityType.Builder.of(RedPlumSkeleton::new, MobCategory.MONSTER), 0.6f, 1.95f);
    public static final RegistryObject<EntityType<RedPlumHuman>> RED_PLUM_HUMAN = registryObject("red_plum_human", EntityType.Builder.of(RedPlumHuman::new, MobCategory.MONSTER), 0.6f, 1.95F);
    public static final RegistryObject<EntityType<Paramecium>> PARAMECIUM = registryObject("paramecium", EntityType.Builder.of(Paramecium::new, MobCategory.MISC), 2f, 1f);
    public static final RegistryObject<EntityType<VillageChief>> VILLAGER_CHIEF = registryObject("villager_chief", EntityType.Builder.of(VillageChief::new, MobCategory.MISC), 0.6F, 1.95F);
    public static final RegistryObject<EntityType<Bike>> BIKE = registryObject("bike", EntityType.Builder.of(Bike::new, MobCategory.CREATURE), 1.25F, 0.9F);

    private static <T extends Entity> RegistryObject<EntityType<T>> register(String registryName,
                                                                             EntityType.Builder<T> entityTypeBuilder) {
        return REGISTRY.register(registryName, () -> entityTypeBuilder.build(registryName));
    }

    private static <T extends Entity> RegistryObject<EntityType<T>> registryObject(String name,
                                                                                   EntityType.Builder<T> builder, float size, float size1) {
        return REGISTRY.register(name, ()->builder.sized(size, size1).build(name));
    }

    public static void registerSpawns(SpawnPlacementRegisterEvent event) {
        event.register(NEO_PLUM.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types
                .MOTION_BLOCKING_NO_LEAVES, (pEntityType, pServerLevel,
                                             pSpawnType, pPos, pRandom) ->
                checkAPIMonsterSpawnRules(pEntityType, pServerLevel, pSpawnType, pPos, pRandom) &&
                        BlueOceansConfig.Common.SPAWN_NEO_PLUM.get(), SpawnPlacementRegisterEvent.Operation.AND);
    }

    public static boolean checkAPIMonsterSpawnRules(EntityType<? extends Mob> type, ServerLevelAccessor accessor,
                                                    MobSpawnType spawnType, BlockPos pos, RandomSource source) {
        return Mob.checkMobSpawnRules(type, accessor, spawnType, pos, source) && accessor.getDifficulty()
                != Difficulty.PEACEFUL && Monster.isDarkEnoughToSpawn(accessor, pos, source);
    }

    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(BASE_VILLAGER.get(), BaseVillager.createBaseVillagerAttributes().build());
        event.put(BEAR.get(), Bear.createAttributes().build());
        event.put(BIKE.get(), Bike.createAttributes());
        event.put(DEATH.get(), Death.createAttributes().build());
        event.put(DICTATOR.get(), Dictator.createAttributes().build());
        event.put(DUMPLING_MONSTER.get(), DumplingMonster.createAttributes().build());
        event.put(FREAK.get(), Freak.createAttributes().build());
        event.put(HEART_OF_HORROR.get(), HeartOfHorror.createAttributes().build());
        event.put(HUNTING_VILLAGER.get(), HuntingVillager.createAttributes().build());
        event.put(NATURAL_ENVOY.get(), NaturalEnvoy.createAttributes().build());
        event.put(NEO_PLUM.get(), NeoPlum.createAttributes().build());
        event.put(PLUM_FACTORY.get(), PlumFactory.createAttributes());
        event.put(RED_PLUMS_COW.get(), RedPlumCow.createAttributes().build());
        event.put(RED_PLUM_GIRL.get(), RedPlumGirl.createAttributes().build());
        event.put(RED_PLUM_HUMAN.get(), RedPlumHuman.createAttributes().build());
        event.put(RED_PLUM_SKELETON.get(), RedPlumSkeleton.createAttributes().build());
        event.put(RED_PLUM_SLAYER.get(), RedPlumSlayer.createAttributes().build());
        event.put(RED_PLUM_SPIDER.get(), RedPlumSpider.createAttributes().build());
        event.put(RED_PLUM_WORM.get(), RedPlumWorm.createAttributes().build());
        event.put(VILLAGER_BIOLOGIST.get(), VillagerBiologist.createAttributes().build());
        event.put(VILLAGER_CHIEF.get(), VillageChief.ATTRIBUTE_SUPPLIER);
        event.put(PARAMECIUM.get(), Paramecium.createAttributes().build());
    }
}
