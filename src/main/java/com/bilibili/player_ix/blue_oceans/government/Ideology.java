
package com.bilibili.player_ix.blue_oceans.government;

public class Ideology {
    public static final Ideology Anarchism;
    public static final Ideology Authoritarian;
    public static final Ideology Democracy;
    public static final Ideology Communism;
    public final float defaultStability;
    public Ideology(float pDefaultStability) {
        this.defaultStability = pDefaultStability;
    }

    public Ideology() {
        this(40F);
    }

    static {
        Anarchism = new Ideology(15F);
        Authoritarian = new Ideology(45F);
        Democracy = new Ideology();
        Communism = new Ideology(59F);
    }
}
