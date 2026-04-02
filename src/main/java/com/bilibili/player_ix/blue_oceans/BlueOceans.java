
package com.bilibili.player_ix.blue_oceans;

import com.bilibili.player_ix.blue_oceans.config.BoCommonConfig;
import com.bilibili.player_ix.blue_oceans.init.*;
import com.bilibili.player_ix.blue_oceans.network.BoNetwork;
import com.github.NineAbyss9.ix_api.api.ModOfNineAbyss;
import com.github.NineAbyss9.ix_api.util.ResourceLocations;
import com.mojang.logging.LogUtils;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;

/**The main class of BlueOceans Mod.*/
@Mod("blue_oceans")
public class BlueOceans implements ModOfNineAbyss {
    /**The id of the mod.*/
    public static final String MOD_ID = "blue_oceans";
    public static final Logger LOGGER = LogUtils.getLogger();
    /**The agent of the mod.*/
    public static BoAgent agent;

    /*Constructor*/
    @SuppressWarnings("removal")
    public BlueOceans(FMLJavaModLoadingContext context) {
        agent = DistExecutor.unsafeRunForDist(() -> ClientAgent::new, () -> ServerAgent::new);
        IEventBus bus = context.getModEventBus();
        bus.addListener(this::commonSetup);
        bus.addListener(BlueOceansEntities::registerAttributes);
        bus.addListener(BlueOceansEntities::registerSpawns);
        BoTags.register();
        //BoRecipes.RECIPES.register(bus);
        BoEnchantments.REGISTER.register(bus);
        BlueOceansEntities.REGISTRY.register(bus);
        BlueOceansBlockEntities.BLOCK_ENTITIES.register(bus);
        BlueOceansBlocks.BLOCKS.register(bus);
        BlueOceansMobEffects.register(bus);
        BlueOceansParticleTypes.REGISTRY.register(bus);
        BlueOceansItems.ITEMS.register(bus);
        BlueOceansSounds.SOUNDS.register(bus);
        BlueOceansTabs.TABS.register(bus);
        createFiles(FMLPaths.CONFIGDIR.get().resolve(MOD_ID), MOD_ID);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, BoCommonConfig.SPEC,
                "blue_oceans/blue_oceans-common_config.toml");
        BoCommonConfig.load(BoCommonConfig.SPEC, FMLPaths.CONFIGDIR.get()
                .resolve("blue_oceans/blue_oceans-common_config.toml").toString());
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        //NineAbyssBase.setup();
        BoNetwork.register();
        BoBrews.addBrews();
        BlueOceansHooks.onRegisterPlumDishes();
    }

    public static ResourceLocation location(String s) {
        return ResourceLocations.fromNamespaceAndPath(MOD_ID, s);
    }

    public static ResourceLocation item(String s) {
        return location("textures/item/" + s + ".png");
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

    public static ResourceLocation entity(String path) {
        return location("textures/entities/" + path + ".png");
    }

    public static ResourceLocation villager(String path) {
        return entity("villagers/" + path);
    }

    public static ResourceLocation animal(String path) {
        return entity("animal/" + path);
    }

    public static ResourceLocation land(String path) {
        return animal("land/" + path);
    }

    public static ResourceLocation ocean(String path) {
        return animal("ocean/" + path);
    }

    public static ResourceLocation water(String path) {
        return animal("water/" + path);
    }

    public static ResourceLocation redPlum(String path) {
        return entity("red_plum_mobs/" + path);
    }

    public static ResourceLocation entityWithCheck(String path) {
        return location("textures/entities/" + path.replace(".png", "")
                .replace("blue_oceans:", "") + ".png");
    }

    /**Based on
     *<a href="https://github.com/Polarice3/Goety-2/blob/1.20/src/main/java/com/Polarice3/Goety/Goety.java">link</a>*/
    private static void createFiles(Path dirPath, String dirLabel) {
        if (!Files.isDirectory(dirPath.getParent())) {
            createFiles(dirPath.getParent(), "parent of " + dirLabel);
        }
        if (!Files.isDirectory(dirPath)) {
            LOGGER.debug("Try create file {}......", dirPath);
            try {
                Files.createDirectory(dirPath);
            } catch (IOException e) {
                if (e instanceof FileAlreadyExistsException) {
                    LOGGER.error("Try to create file {}, but the file already exists.", dirPath);
                } else {
                    LOGGER.error("Try to create file {}, but failed.", dirPath);
                }
                net.minecraft.Util.throwAsRuntime(e);
            }
        } else {
            LOGGER.debug("Found {} successful.Label: {}", dirPath, dirLabel);
        }
    }
}
