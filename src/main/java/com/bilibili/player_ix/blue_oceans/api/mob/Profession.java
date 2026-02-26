
package com.bilibili.player_ix.blue_oceans.api.mob;

import com.bilibili.player_ix.blue_oceans.init.BlueOceansItems;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.PoiTypeTags;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import org.NineAbyss9.util.lister.ImmutableSubLister;
import org.NineAbyss9.util.lister.Lister;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;
import java.util.function.Predicate;

public class Profession {
    public static final Profession EMPTY;
    public static final Profession ARMORER;
    public static final Profession FARMER;
    public static final Profession FISHMAN;
    public static final Profession SOLIDER;
    public static final Profession BIOLOGIST;
    public static final Profession MINER;
    @Nullable
    private final VillagerProfession villagerProfession;
    public final String name;
    public final Predicate<Holder<PoiType>> heldJobSite;
    public final Predicate<Holder<PoiType>> acquirableJobSite;
    public final Lister<Item> requestedItems;
    public final Lister<Block> secondaryPoi;
    @Nullable
    public final SoundEvent workSound;
    public Profession(@Nullable VillagerProfession pProfession, String pName, Predicate<Holder<PoiType>> pHeldJobSite,
                      Predicate<Holder<PoiType>> pAcquirableJobSite, Set<Item> pRequestedItems, Set<Block> pSecondaryPoi,
                      @Nullable SoundEvent pWorkSound) {
        this.villagerProfession = pProfession;
        this.name = pName;
        this.heldJobSite = pHeldJobSite;
        this.acquirableJobSite = pAcquirableJobSite;
        this.requestedItems = ImmutableSubLister.copyOf(pRequestedItems);
        this.secondaryPoi = ImmutableSubLister.copyOf(pSecondaryPoi);
        this.workSound = pWorkSound;
    }

    public Profession(@Nullable VillagerProfession pProfession, String pName, Predicate<Holder<PoiType>> pHeldJobSite,
                      Predicate<Holder<PoiType>> pAcquirableJobSite, Lister<Item> pRequestedItems, Lister<Block> pSecondaryPoi,
                      @Nullable SoundEvent pWorkSound) {
        this.villagerProfession = pProfession;
        this.name = pName;
        this.heldJobSite = pHeldJobSite;
        this.acquirableJobSite = pAcquirableJobSite;
        this.requestedItems = pRequestedItems;
        this.secondaryPoi = pSecondaryPoi;
        this.workSound = pWorkSound;
    }

    public Profession(@Nonnull VillagerProfession pProfession) {
        this(pProfession, pProfession.name(), pProfession.heldJobSite(), pProfession.acquirableJobSite(),
                pProfession.requestedItems(), pProfession.secondaryPoi(), pProfession.workSound());
    }

    @Nullable
    public VillagerProfession villagerProfession() {
        return villagerProfession;
    }

    public static Builder builder() {
        return new Builder();
    }

    static {
        EMPTY = new Profession(VillagerProfession.NONE);
        ARMORER = new Profession(VillagerProfession.ARMORER);
        FARMER = new Profession(VillagerProfession.FARMER);
        FISHMAN = builder().profession(VillagerProfession.FISHERMAN)
                .requestedItems(ImmutableSubLister.of(Items.FISHING_ROD)).build();
        SOLIDER = new Profession(null, "Solider", site->site.is(PoiTypeTags.VILLAGE),
                aqSite->true, Set.of(BlueOceansItems.SNIPER_RIFLE.get()), Set.of(),
                SoundEvents.VILLAGER_WORK_BUTCHER);
        BIOLOGIST = new Profession(null, "Biologist", site->site
                .is(PoiTypeTags.VILLAGE), aqSite -> aqSite.is(PoiTypes.MEETING),
                Set.of(BlueOceansItems.ECHO_POTION.get(), Items.POTION), Set.of(), SoundEvents.BREWING_STAND_BREW);
        MINER = new Profession(null, "Miner", aq->true, aqq->true,
                Set.of(), Set.of(), SoundEvents.STONE_BREAK);
    }

    public static class Builder {
        private VillagerProfession profession;
        private String name;
        private Predicate<Holder<PoiType>> heldJobSite;
        private Predicate<Holder<PoiType>> acquirableJobSite;
        private Lister<Item> requestedItems;
        private Lister<Block> secondaryPoi;
        private SoundEvent workSound;
        public Builder profession(@Nullable VillagerProfession pProfession) {
            this.profession = pProfession;
            if (pProfession != null) {
                this.name = pProfession.name();
                this.heldJobSite = pProfession.heldJobSite();
                this.acquirableJobSite = pProfession.acquirableJobSite();
                this.requestedItems = ImmutableSubLister.copyOf(pProfession.requestedItems());
                this.secondaryPoi = ImmutableSubLister.copyOf(pProfession.secondaryPoi());
                this.workSound = pProfession.workSound();
            }
            return this;
        }

        public Builder name(String pName) {
            this.name = pName;
            return this;
        }

        public Builder held(Predicate<Holder<PoiType>> pHeld) {
            this.heldJobSite = pHeld;
            return this;
        }

        public Builder acquirable(Predicate<Holder<PoiType>> pAcquirable) {
            this.acquirableJobSite = pAcquirable;
            return this;
        }

        public Builder requestedItems(Lister<Item> pItems) {
            this.requestedItems = pItems;
            return this;
        }

        public Builder secondaryPos(Lister<Block> pPoi) {
            this.secondaryPoi = pPoi;
            return this;
        }

        public Builder workSound(SoundEvent pSound) {
            this.workSound = pSound;
            return this;
        }

        public Profession build() {
            return new Profession(profession, name, heldJobSite, acquirableJobSite, requestedItems, secondaryPoi, workSound);
        }
    }
}
