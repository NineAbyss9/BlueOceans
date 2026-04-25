
package com.bilibili.player_ix.blue_oceans.client;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.client.model.*;
import com.bilibili.player_ix.blue_oceans.client.model.animal.ParameciumModel;
import com.bilibili.player_ix.blue_oceans.client.model.animal.land.EarthwormModel;
import com.bilibili.player_ix.blue_oceans.client.model.animal.ocean.JellyfishModel;
import com.bilibili.player_ix.blue_oceans.client.model.animal.water.ShrimpModel;
import com.bilibili.player_ix.blue_oceans.client.model.deprecated.DeathModel;
import com.bilibili.player_ix.blue_oceans.client.model.deprecated.DumplingMonsterModel;
import com.bilibili.player_ix.blue_oceans.client.model.item.GasMaskModel;
import com.bilibili.player_ix.blue_oceans.client.model.plum.*;
import com.bilibili.player_ix.blue_oceans.client.model.projectile.BulletModel;
import com.bilibili.player_ix.blue_oceans.client.model.projectile.VenomModel;
import com.bilibili.player_ix.blue_oceans.client.model.villager.HattedVillagerModel;
import com.bilibili.player_ix.blue_oceans.client.model.villager.HuntingVillagerArmorModel;
import com.bilibili.player_ix.blue_oceans.client.model.villager.HuntingVillagerModel;
import com.bilibili.player_ix.blue_oceans.client.particles.BloodParticle;
import com.bilibili.player_ix.blue_oceans.client.particles.Impart;
import com.bilibili.player_ix.blue_oceans.client.particles.RedPlumSpell;
import com.bilibili.player_ix.blue_oceans.client.particles.SparkParticle;
import com.bilibili.player_ix.blue_oceans.client.renderer.*;
import com.bilibili.player_ix.blue_oceans.client.renderer.animal.DuckRenderer;
import com.bilibili.player_ix.blue_oceans.client.renderer.animal.ParameciumRenderer;
import com.bilibili.player_ix.blue_oceans.client.renderer.animal.land.BearRenderer;
import com.bilibili.player_ix.blue_oceans.client.renderer.animal.land.EarthwormRenderer;
import com.bilibili.player_ix.blue_oceans.client.renderer.animal.ocean.JellyfishRenderer;
import com.bilibili.player_ix.blue_oceans.client.renderer.animal.water.ShrimpRenderer;
import com.bilibili.player_ix.blue_oceans.client.renderer.block.CorpseRenderer;
import com.bilibili.player_ix.blue_oceans.client.renderer.block.WoodenSupportRenderer;
import com.bilibili.player_ix.blue_oceans.client.renderer.deprecated.DeathRenderer;
import com.bilibili.player_ix.blue_oceans.client.renderer.deprecated.WaterTrapRenderer;
import com.bilibili.player_ix.blue_oceans.client.renderer.illager.NaturalEnvoyRenderer;
import com.bilibili.player_ix.blue_oceans.client.renderer.plum.*;
import com.bilibili.player_ix.blue_oceans.client.renderer.projectile.BulletRenderer;
import com.bilibili.player_ix.blue_oceans.client.renderer.projectile.EchoPotionRenderer;
import com.bilibili.player_ix.blue_oceans.client.renderer.projectile.SeedEntityRenderer;
import com.bilibili.player_ix.blue_oceans.client.renderer.projectile.VenomRenderer;
import com.bilibili.player_ix.blue_oceans.client.renderer.villager.*;
import com.bilibili.player_ix.blue_oceans.common.item.food.Coffee;
import com.bilibili.player_ix.blue_oceans.init.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.GrassColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
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
        event.registerSpriteSet(BlueOceansParticleTypes.BLOOD.get(), BloodParticle.Provider::new);
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
        event.registerLayerDefinition(EarthwormModel.LAYER_LOCATION, EarthwormModel::createBodyLayer);
        event.registerLayerDefinition(FreakModel.LAYER_LOCATION, FreakModel::createBodyLayer);
        event.registerLayerDefinition(HattedVillagerModel.LOCATION, HattedVillagerModel::createBodyLayer);
        event.registerLayerDefinition(HeartOfHorrorModel.LAYER_LOCATION, HeartOfHorrorModel::createBodyLayer);
        event.registerLayerDefinition(HuntingVillagerArmorModel.LOCATION, HuntingVillagerArmorModel::createInnerArmorLayer);
        event.registerLayerDefinition(HuntingVillagerArmorModel.LOC, HuntingVillagerArmorModel::createOuterArmorLayer);
        event.registerLayerDefinition(HuntingVillagerModel.LOCATION, HuntingVillagerModel::createBodyLayer);
        event.registerLayerDefinition(JellyfishModel.LAYER_LOCATION, JellyfishModel::createBodyLayer);
        event.registerLayerDefinition(PlumBuilderModel.LAYER_LOCATION, PlumBuilderModel::createBodyLayer);
        event.registerLayerDefinition(PlumFactoryModel.LAYER_LOCATION, PlumFactoryModel::createBodyLayer);
        event.registerLayerDefinition(PlumHolderModel.LAYER_LOCATION, PlumHolderModel::createBodyLayer);
        event.registerLayerDefinition(PlumSpreaderModel.LAYER_LOCATION, PlumSpreaderModel::createBodyLayer);
        event.registerLayerDefinition(NeoFighterModel.LAYER_LOCATION, NeoFighterModel::createBodyLayer);
        event.registerLayerDefinition(NeoPlumModel.NEO_PLUM, NeoPlumModel::createBodyLayer);
        event.registerLayerDefinition(ParameciumModel.LAYER_LOCATION, ParameciumModel::createBodyLayer);
        event.registerLayerDefinition(RedPlumDemonModel.LAYER_LOCATION, RedPlumDemonModel::createBodyLayer);
        event.registerLayerDefinition(RedPlumGirlModel.LAYER_LOCATION, RedPlumGirlModel::createBodyLayer);
        event.registerLayerDefinition(RedPlumIllagerModel.LAYER_LOCATION, RedPlumIllagerModel::createBodyLayer);
        event.registerLayerDefinition(RedPlumSlayerModel.LAYER_LOCATION, RedPlumSlayerModel::createBodyLayer);
        event.registerLayerDefinition(ShrimpModel.LAYER_LOCATION, ShrimpModel::createBodyLayer);
        event.registerLayerDefinition(VenomModel.LAYER_LOCATION, VenomModel::createBodyLayer);
        registerItemLayerDef(event);
    }

    public static void registerItemLayerDef(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(GasMaskModel.LAYER_LOCATION, GasMaskModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(BlueOceansEntities.BASE_VILLAGER.get(), BaseVillagerRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.BEAR.get(), BearRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.BIKE.get(), BikeRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.BULLET.get(), BulletRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.DEATH.get(), DeathRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.DICTATOR.get(), DictatorRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.DUCK.get(), DuckRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.EARTHWORM.get(), EarthwormRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.ECHO_POTION.get(), EchoPotionRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.FARMER.get(), FarmerRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.FREAK.get(), FreakRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.HEART_OF_HORROR.get(), HeartOfHorrorRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.HUNTING_VILLAGER.get(), HuntingVillagerRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.JELLYFISH.get(), JellyfishRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.NATURAL_ENVOY.get(), NaturalEnvoyRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.NEO_FIGHTER.get(), NeoFighterRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.NEO_PLUM.get(), NeoPlumRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.PARAMECIUM.get(), ParameciumRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.RED_PLUM_CREEPER.get(), RPCreeperRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.PLUM_BUILDER.get(), PlumBuilderRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.PLUM_FACTORY.get(), PlumFactoryRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.PLUM_HOLDER.get(), PlumRenderer.PlumHolderRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.PLUM_SPREADER.get(), PlumSpreaderRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.RED_DEMON.get(), RedDemonRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.RED_PLUMS_COW.get(), RedPlumsCowRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.RED_PLUM_FISH.get(), RPFishRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.RED_PLUM_GIRL.get(), RedPlumGirlRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.RED_PLUM_HUMAN.get(), RedPlumHumanRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.RED_PLUM_SKELETON.get(), RedPlumSkeletonRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.RED_PLUM_SLAYER.get(), RedPlumSlayerRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.RED_PLUM_SPIDER.get(), RedPlumSpiderRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.RED_PLUM_VILLAGER.get(), RPVillagerRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.RED_PLUM_WORM.get(), RedPlumWormRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.RESEARCHER.get(), ResearcherRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.SEED.get(), SeedEntityRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.SHRIMP.get(), ShrimpRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.VENOM.get(), VenomRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.VILLAGER_BIOLOGIST.get(), VillagerBiologistRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.VILLAGER_CHIEF.get(), VillagerChiefRenderer::new);
        event.registerEntityRenderer(BlueOceansEntities.WATER_TRAP.get(), WaterTrapRenderer::new);
        registerBlockEntityRenderers(event);
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
        registerItemStates();
        injectItemModels(Minecraft.getInstance().getItemRenderer());
        injectItemTextures(Minecraft.getInstance());
    }

    public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(BlueOceansBlockEntities.CORPSE.get(), CorpseRenderer::new);
        event.registerBlockEntityRenderer(BlueOceansBlockEntities.WOODEN_SUPPORT.get(), WoodenSupportRenderer::new);
    }

    public static void registerItemStates() {
        ItemProperties.register(BlueOceansItems.COFFEE.get(), new ResourceLocation("java"),
                (pStack, pLevel, pEntity, pSeed) -> Coffee.isJava(pStack) ? 1.0F : 0.0F);
    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event)
    {
        event.register((itemStack, i) -> {
            return GrassColor.getDefaultColor();
        });
    }

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event)
    {
        event.register((pState, pLevel, pPos, pTintIndex) -> {
            if (pLevel == null || pPos == null) {
                return GrassColor.getDefaultColor();
            }
            return BiomeColors.getAverageGrassColor(pLevel, pPos);
        }, BlueOceansBlocks.WEED.get());
    }

    private static void injectItemModels(ItemRenderer pRenderer) {
        ItemModelShaper shaper = pRenderer.getItemModelShaper();
        shaper.register(BlueOceansItems.GAS_MASK.get(), makeModelLocation("gasmask"));
    }

    private static void injectItemTextures(Minecraft pMinecraft) {
        TextureManager manager = pMinecraft.textureManager;
        var gasmask = BlueOceans.item("biology/gas_mask");
        manager.register(gasmask, new SimpleTexture(gasmask));
    }

    public static ModelResourceLocation makeModelLocation(String name) {
        return new ModelResourceLocation(BlueOceans.location(name), "main");
    }
}
