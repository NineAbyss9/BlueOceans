
package com.bilibili.player_ix.blue_oceans.init.data;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(modid = BlueOceans.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BoDataGenHelper
{
    public static final Set<RegistryObject<Block>> BLOCK_REGISTRIES;
    public static final Set<RegistryObject<Item>> ITEM_REGISTRIES;

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        var generator = event.getGenerator();
        var packOutput = generator.getPackOutput();
        var lookupProvider = event.getLookupProvider();
        var existingFileHelper = event.getExistingFileHelper();
        var isServer = event.includeServer();
        var isClient = event.includeClient();

        generator.addProvider(isClient, new ModBlockStateProvider(packOutput, BlueOceans.MOD_ID,
                existingFileHelper));
        generator.addProvider(isClient, new ModItemModelProvider(packOutput, BlueOceans.MOD_ID, existingFileHelper));

        //var languages = List.of("en_us", "zh_cn");
        //languages.forEach(l -> generator.addProvider(isClient, new ModLangProvider(packOutput, BlueOceans.MOD_ID, l,
        //        BoLang.LANG)));
    }

    static {
        BLOCK_REGISTRIES = new HashSet<>();
        ITEM_REGISTRIES = new HashSet<>();
    }
}
