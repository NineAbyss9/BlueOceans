
package com.bilibili.player_ix.blue_oceans.init.data;

public class ModBlockLangModule
extends AbstractLangModule
{
    protected void _collect(String locale) {
        BoLang.LANG_MAP.forEach((id, nameLang) -> {
            var key = "block." + id.toLanguageKey();
            var name = nameLang.get(locale);
            add(key, name);
        });
    }
}
