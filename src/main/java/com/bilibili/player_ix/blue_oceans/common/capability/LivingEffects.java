
package com.bilibili.player_ix.blue_oceans.common.capability;

import java.util.LinkedHashMap;
import java.util.Map;

public class LivingEffects {
    public static final Map<String, LivingEffect> REGISTRY;
    public static final LivingEffect ILL;

    public static LivingEffect register(String pName, LivingEffect pEffect) {
        return REGISTRY.put(pName, pEffect);
    }

    static {
        REGISTRY = new LinkedHashMap<>();
        ILL = register("ill", new LivingEffect());
    }
}
