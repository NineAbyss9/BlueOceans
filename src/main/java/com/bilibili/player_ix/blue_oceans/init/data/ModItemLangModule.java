
package com.bilibili.player_ix.blue_oceans.init.data;

public class ModItemLangModule
extends AbstractLangModule
{
    protected void _collect(String locale) {
        BoLang.LANG_MAP.forEach((id, nameLang) -> {
            var key = "item." + id.toLanguageKey();
            var name = nameLang.get(locale);
            add(key, name);
        });
    }
}
