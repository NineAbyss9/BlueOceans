
package com.bilibili.player_ix.blue_oceans.init.data;

import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class BoLang
{
    public static final List<Supplier<AbstractLangModule>> LANG = List.of(
            ModBlockLangModule::new,
            ModItemLangModule::new
    );
    public static final Map<ResourceLocation, LangEntry> LANG_MAP;
    //Block
    //public static final Supplier<Block> Agaric;

    public static void put(String locate, String en, String zh) {
        LANG_MAP.put(new ResourceLocation(locate), new LangEntry(en, zh));
    }

    static {
        LANG_MAP = new HashMap<>();
    }
}
