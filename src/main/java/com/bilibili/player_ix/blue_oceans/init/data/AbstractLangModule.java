
package com.bilibili.player_ix.blue_oceans.init.data;

import com.ibm.icu.impl.Pair;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractLangModule
{
    private boolean collected = false;
    private final List<Pair<String, String>> collection = new ArrayList<>();
    List<Pair<String, String>> collect(String locale)
    {
        if (!collected) {
            collected = true;
            _collect(locale);
        }
        return collection;
    }

    abstract protected void _collect(String locale);

    protected void add(String key, String value)
    {
        collection.add(Pair.of(key, value));
    }
}
