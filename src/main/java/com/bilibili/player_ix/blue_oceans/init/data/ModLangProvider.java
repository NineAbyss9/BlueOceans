
package com.bilibili.player_ix.blue_oceans.init.data;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.List;
import java.util.function.Supplier;

public class ModLangProvider
extends LanguageProvider
{
    public ModLangProvider(PackOutput output, String modId, String locale, List<Supplier<AbstractLangModule>> modules)
    {
        super(output, modId, locale);
        this.locale = locale;
        this.modules = modules.stream().map(Supplier::get).toList();
    }

    private final String locale;
    private final List<AbstractLangModule> modules;

    protected void addTranslations()
    {
        modules.stream().flatMap(m -> m.collect(locale).stream())
                .forEach(pair -> add(pair.first, pair.second));
    }
}
