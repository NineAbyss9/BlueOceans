
package com.bilibili.player_ix.blue_oceans.government;

//import com.bilibili.player_ix.blue_oceans.BlueOceans;

public class Country {
    private Government government;
    public static final Country PLAYER;
    public static final Country EMPIRE_OF_EVIL;
    public static final Country VILLAGER;
    public Country(Government pGovernment) {
        this.government = pGovernment;
    }

    public Government government() {
        return government;
    }

    public void setGovernment(Government pC_s_G_G) {
        this.government = pC_s_G_G;
    }

    public int followerSize() {
        return this.government().followers().size();
    }

    static {
        PLAYER = new Country(Government.PLAYER);
        EMPIRE_OF_EVIL = new Country(Government.EVIL_FACTION);
        VILLAGER = new Country(new Government(Ideology.Democracy, null, "Villager"));
    }
}
