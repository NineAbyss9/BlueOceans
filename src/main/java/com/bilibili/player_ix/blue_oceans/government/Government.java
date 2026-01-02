
package com.bilibili.player_ix.blue_oceans.government;

import com.bilibili.player_ix.blue_oceans.government.effect.Effect;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Government {
    private Country country;
    private Party mainParty;
    private final List<Party> allParties;
    private final Set<Effect> effects;
    private final List<LivingEntity> followers;
    private final StabilityHandler stability;
    private String name;
    public static final Government EMPTY;
    public static final Government EVIL_FACTION;
    public Government(Country pCountry, Ideology pIdeology, LivingEntity pLeader, String pName) {
        this.country = pCountry;
        this.mainParty = new Party(pIdeology, pLeader);
        this.allParties = new ArrayList<>();
        this.allParties.add(this.mainParty);
        this.effects = new HashSet<>();
        this.followers = new ArrayList<>();
        this.followers.add(pLeader);
        this.stability = new StabilityHandler(40F);
        this.name = pName;
    }

    public Government(Ideology pIdeology, LivingEntity pLeader, String pName) {
        this(Country.VILLAGER, pIdeology, pLeader, pName);
    }

    public void tick() {
        this.tickEffects();
    }

    private void tickEffects() {
        if (!effects.isEmpty()) {
            effects.removeIf(Effect::shouldBeRemoved);
            effects.forEach(effect -> effect.tick(this, this.followers));
        }
    }

    public LivingEntity getLeader() {
        return this.getMainParty().leader();
    }

    public Party getMainParty() {
        return mainParty;
    }

    public void setMainParty(Party pMainParty) {
        this.mainParty = pMainParty;
    }

    public List<Party> getAllParties() {
        return allParties;
    }

    public void addEffect(Effect pEffect) {
        this.effects.add(pEffect);
    }

    public void removeEffect(Effect pEffect) {
        this.effects.remove(pEffect);
    }

    public void removeAllEffects() {
        this.effects.clear();
    }

    public float getStability() {
        return this.stability.getStability();
    }

    public void setStability(float pStability) {
        this.stability.setStability(pStability);
    }

    public String getName() {
        return name;
    }

    public void setName(String pName) {
        this.name = pName;
    }

    public boolean isAuthoritarian() {
        return false;
    }

    public Country getCountry() {
        return this.country;
    }

    public void setCountry(Country pG_s_C_C) {
        this.country = pG_s_C_C;
    }

    public List<LivingEntity> followers() {
        return followers;
    }

    public ListTag getAdditionalSaveData() {
        ListTag listTag = new ListTag();
        String name = this.getName();
        CompoundTag tag = new CompoundTag();
        tag.putString("Name", name);
        float vStability = this.getStability();
        tag.putFloat("Stability", vStability);
        tag.putInt("FollowersSize", this.followers.size());
        listTag.add(tag);
        return listTag;
    }

    public static Government empty() {
        return EMPTY;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Government other))
            return false;
        else {
            if (this == obj)
                return true;
            return this.getName().equals(other.name);
        }
    }

    /*public static boolean canHurt(LivingEntity pEntity, LivingEntity pSource) {
        if (Government.EVIL_FACTION.followers.contains(pEntity) && )
        return
    }*/

    static {
        EMPTY = new Government(Ideology.Anarchism, null, "EMPTY");
        EVIL_FACTION = new Government(Country.EMPIRE_OF_EVIL, Ideology.Authoritarian, null,
                "EvilFaction");
    }
}
