
package com.bilibili.player_ix.blue_oceans.client;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.client.model.*;
import com.bilibili.player_ix.blue_oceans.client.particles.Impart;
import com.bilibili.player_ix.blue_oceans.client.particles.RedPlumSpell;
import com.bilibili.player_ix.blue_oceans.client.particles.SparkParticle;
import com.bilibili.player_ix.blue_oceans.client.renderer.*;
import com.bilibili.player_ix.blue_oceans.client.renderer.block.WoodenSupportRenderer;
import com.bilibili.player_ix.blue_oceans.client.renderer.plum.PlumBuilderRenderer;
import com.bilibili.player_ix.blue_oceans.common.item.food.Coffee;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansBlockEntities;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansEntities;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansItems;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansParticleTypes;
import com.github.player_ix.ix_api.api.renderer.BaseEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterItemDecorationsEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientInitEvents {
    public static final ResourceLocation LOCKED = BlueOceans.location("textures/gui/lock.png");
    private ClientInitEvents() {
    }

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(BlueOceansParticleTypes.BIG_RED_PLUM_INSTANT_SPELL.get(),
            RedPlumSpell.BigInstantSpellProvider::new);
        event.registerSpriteSet(BlueOceansParticleTypes.BO_SPELL.get(), RedPlumSpell.BOSpellProvider::new);
        event.registerSpriteSet(BlueOceansParticleTypes.IMPART.get(), Impart.Provider::new);
        event.registerSpriteSet(BlueOceansParticleTypes.RED_PLUM_SPELL.get(),
                RedPlumSpell.SpellProvider::new);
        event.registerSpriteSet(BlueOceansParticleTypes.RED_PLUM_INSTANT_SPELL.get(),
                RedPlumSpell.InstantProvider::new);
        event.registerSpriteSet(BlueOceansParticleTypes.RED_SPELL.get(), RedPlumSpell.RedSpell::new);
        event.registerSpriteSet(BlueOceansParticleTypes.SPARK.get(), SparkParticle.SparkParticleProvider::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(AbstractSkeletonModel.LOCATION, AbstractSkeletonModel::createBodyLayer);
        event.registerLayerDefinition(BikeModel.LAYER_LOCATION, BikeModel::createBodyLayer);
        event.registerLayerDefinition(BulletModel.LAYER_LOCATION, BulletModel::createBodyLayer);
        event.registerLayerDefinition(DeathModel.DEATH, DeathModel::createBodyLayer);
        event.registerLayerDefinition(DumplingMonsterModel.LAYER_LOCATION, DumplingMonsterModel::createBodyLayer);
        event.registerLayerDefinition(FreakagerModel.LAYER_LOCATION, FreakagerModel::createBodyLayer);
        event.registerLayerDefinition(HattedVillagerModel.LOCATION, HattedVillagerModel::createBodyLayer);
        event.registerLayerDefinition(HeartOfHorrorModel.LAYER_LOCATION, HeartOfHorrorModel::createBodyLayer);
        event.registerLayerDefinition(HuntingVillagerArmorModel.LOCATION, HuntingVillagerArmorModel::createInnerArmorLayer);
        event.registerLayerDefinition(HuntingVillagerArmorModel.LOC, HuntingVillagerArmorModel::createOuterArmorLayer);
        event.registerLayerDefinition(HuntingVillagerModel.LOCATION, HuntingVillagerModel::createBodyLayer);
        event.registerLayerDefinition(PlumBuilderModel.LAYER_LOCATION, PlumBuilderModel::createBodyLayer);
        event.registerLayerDefinition(PlumFactoryModel.LAYER_LOCATION, PlumFactoryModel::createBodyLayer);
        event.registerLayerDefinition(PlumSpreaderModel.LAYER_LOCATION, PlumSpreaderModel::createBodyLayer);
        event.registerLayerDefinition(NeoFighterModel.LAYER_LOCATION, NeoFighterModel::createBodyLayer);
        event.registerLayerDefinition(NeoPlumModel.NEO_PLUM, NeoPlumModel::createBodyLayer);
        event.registerLayerDefinition(ParameciumModel.LAYER_LOCATION, ParameciumModel::createBodyLayer);
        event.registerLayerDefinition(RedPlumGirlModel.LAYER_LOCATION, RedPlumGirlModel::createBodyLayer);
        event.registerLayerDefinition(RedPlumIllagerModel.LAYER_LOCATION, RedPlumIllagerModel::createBodyLayer);
        event.registerLayerDefinition(RedPlumSlayerModel.LAYER_LOCATION, RedPlumSlayerModel::createBodyLayer);
        event.registerLayerDefinition(VenomModel.LAYER_LOCATION, VenomModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(BlueOceansEntities.BASE_VILLAGER.get(), BaseVillagerRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.BEAR.get(), BearRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.BIKE.get(), BikeRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.BULLET.get(), BulletRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.CHLORINE.get(), BaseEntityRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.DEATH.get(), DeathRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.DICTATOR.get(), DictatorRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.DUMPLING_MONSTER.get(), DumplingMonsterRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.ECHO_POTION.get(), EchoPotionRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.FARMER.get(), FarmerRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.FREAK.get(), FreakerRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.HEART_OF_HORROR.get(), HeartOfHorrorRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.HUNTING_VILLAGER.get(), HuntingVillagerRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.NATURAL_ENVOY.get(), NaturalEnvoyRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.NEO_FIGHTER.get(), NeoFighterRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.NEO_PLUM.get(), NeoPlumRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.PARAMECIUM.get(), ParameciumRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.RED_PLUM_CREEPER.get(), RPCreeperRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.PLUM_BUILDER.get(), PlumBuilderRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.PLUM_FACTORY.get(), PlumFactoryRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.PLUM_SPREADER.get(), PlumSpreaderRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.RED_PLUMS_COW.get(), RedPlumsCowRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.RED_PLUM_GIRL.get(), RedPlumGirlRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.RED_PLUM_HUMAN.get(), RedPlumHumanRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.RED_PLUM_SKELETON.get(), RedPlumSkeletonRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.RED_PLUM_SLAYER.get(), RedPlumSlayerRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.RED_PLUM_SPIDER.get(), RedPlumSpiderRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.RED_PLUM_WORM.get(), RedPlumWormRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.VENOM.get(), VenomRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.VILLAGER_BIOLOGIST.get(), VillagerBiologistRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.VILLAGER_CHIEF.get(), VillagerChiefRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.WATER_TRAP.get(), WaterTrapRenderer::new);
    }

    @SubscribeEvent
    public static void registerItemDecorations(RegisterItemDecorationsEvent event) {
        event.register(BlueOceansItems.STEEL_AXE.get(),
                (guiGraphics, font, stack, xOffset, yOffset) -> {
                    guiGraphics.blit(LOCKED, xOffset, yOffset, 8, 8, 8,
                            8, 8, 8);
                    return true;
                });
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        BlockEntityRenderers.register(BlueOceansBlockEntities.WOODEN_SUPPORT.get(), WoodenSupportRenderer::new);
        registerItemStates();
    }

    public static void registerItemStates() {
        ItemProperties.register(BlueOceansItems.COFFEE.get(), new ResourceLocation("java"),
                (pStack, pLevel, pEntity, pSeed) -> Coffee.isJava(pStack) ? 1.0F : 0.0F);
    }
}
