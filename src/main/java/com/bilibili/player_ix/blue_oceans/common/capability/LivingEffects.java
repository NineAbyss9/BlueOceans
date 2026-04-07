
package com.bilibili.player_ix.blue_oceans.common.capability;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Registry of custom living-health effects (parallel to mob effects; includes disease / immune). */
public class LivingEffects
{
    private static final Map<String, LivingEffect> BY_KEY = new LinkedHashMap<>();
    private static final List<LivingEffect> BY_ID = new ArrayList<>();
    private static final Map<LivingEffect, String> KEY_OF = new LinkedHashMap<>();
    public static final LivingEffect ILL;
    public static final LivingEffect VIRAL_INVASION;
    public static final LivingEffect IMMUNE_RESPONSE;

    private LivingEffects()
    {
        throw new AssertionError();
    }

    public static LivingEffect register(String pKey, LivingEffect pEffect)
    {
        if (BY_KEY.containsKey(pKey))
        {
            throw new IllegalStateException("Duplicate LivingEffect key: " + pKey);
        }
        int id = BY_ID.size();
        pEffect.bindRegistryId(id, pKey);
        BY_KEY.put(pKey, pEffect);
        BY_ID.add(pEffect);
        KEY_OF.put(pEffect, pKey);
        return pEffect;
    }

    public static String getKey(LivingEffect pEffect)
    {
        return KEY_OF.getOrDefault(pEffect, "");
    }

    public static LivingEffect byKey(String pKey)
    {
        return BY_KEY.get(pKey);
    }

    public static LivingEffect byId(int pId)
    {
        if (pId < 0 || pId >= BY_ID.size())
        {
            return null;
        }
        return BY_ID.get(pId);
    }

    public static Map<String, LivingEffect> registryView()
    {
        return Collections.unmodifiableMap(BY_KEY);
    }

    static
    {
        ILL = register("ill", new IllLivingEffect());
        VIRAL_INVASION = register("viral_invasion", new ViralInvasionLivingEffect());
        IMMUNE_RESPONSE = register("immune_response", new ImmuneResponseLivingEffect());
    }
}
