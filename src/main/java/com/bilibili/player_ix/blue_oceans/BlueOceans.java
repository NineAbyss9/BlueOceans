
package com.bilibili.player_ix.blue_oceans;

//import com.bilibili.player_ix.blue_oceans.api.crafting.BoRecipes;
import com.bilibili.player_ix.blue_oceans.config.BlueOceansConfig;
import com.bilibili.player_ix.blue_oceans.init.*;
import com.bilibili.player_ix.blue_oceans.network.BoNetwork;
import com.github.player_ix.ix_api.api.ModOfNineAbyss;
import com.github.player_ix.ix_api.util.ResourceLocations;
import com.mojang.logging.LogUtils;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.nine_abyss.NineAbyssBase;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.LinkedHashSet;
import java.util.Set;

/**The main class of BlueOceans Mod.*/
@ParametersAreNonnullByDefault
@Mod("blue_oceans")
public class BlueOceans implements ModOfNineAbyss {
    /**The id of the mod.*/
    public static final String MOD_ID = "blue_oceans";
    public static final Logger LOGGER = LogUtils.getLogger();
    /**The agent of the mod.*/
    public static BoAgent agent;
    public static Set<Comparable<?>> configs = new LinkedHashSet<>();

    /*Constructor
    @SuppressWarnings("removal")*/
    public BlueOceans(FMLJavaModLoadingContext context) {
        agent = DistExecutor.unsafeRunForDist(() -> ClientAgent::new, () -> ServerAgent::new);
        IEventBus bus = context.getModEventBus();
        bus.addListener(this::commonSetup);
        bus.addListener(BlueOceansEntities::registerAttributes);
        bus.addListener(BlueOceansEntities::registerSpawns);
        BoTags.register();
        context.registerConfig(ModConfig.Type.COMMON, BlueOceansConfig.COMMON_SPEC);
        //BoRecipes.RECIPES.register(bus);
        BlueOceansEntities.REGISTRY.register(bus);
        BlueOceansBlockEntities.BLOCK_ENTITIES.register(bus);
        BlueOceansBlocks.BLOCKS.register(bus);
        BlueOceansMobEffects.register(bus);
        BlueOceansParticleTypes.REGISTRY.register(bus);
        BlueOceansItems.ITEMS.register(bus);
        BlueOceansSounds.SOUNDS.register(bus);
        BlueOceansTabs.TABS.register(bus);
        MinecraftForge.EVENT_BUS.register(this);
        BlueOceansHooks.onHandleConfigValue();
    }

    public String getVersion() {
        return "1.0.9a";
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        NineAbyssBase.setup();
        BoNetwork.register();
    }

    @Nonnull
    public static ResourceLocation location(String s) {
        return ResourceLocations.fromNamespaceAndPath(MOD_ID, s);
    }

    public static ModelLayerLocation modelLocation(String s) {
        return new ModelLayerLocation(location(s), "main");
    }

    public static ResourceLocation gui(String s) {
        return location("textures/gui/" + s + ".png");
    }

    public static String armor(String s) {
        return "blue_oceans:textures/models/armor/" + s + "armor.png";
    }

    public static String leggings(String s) {
        return "blue_oceans:textures/models/armor/" + s + "armor_layer.png";
    }

    @Nonnull
    public static ResourceLocation entity(String path) {
        return location("textures/entities/" + path + ".png");
    }

    public static ResourceLocation villager(String path) {
        return entity("villagers/" + path);
    }

    @Nonnull
    public static ResourceLocation animal(String path) {
        return entity("animal/" + path);
    }

    @Nonnull
    public static ResourceLocation redPlum(String path) {
        return entity("red_plum_mobs/" + path);
    }

    @Nonnull
    public static ResourceLocation entityWithCheck(String path) {
        return location("textures/entities/" + path.replace(".png", "")
                .replace("blue_oceans:", "") + ".png");
    }
}
