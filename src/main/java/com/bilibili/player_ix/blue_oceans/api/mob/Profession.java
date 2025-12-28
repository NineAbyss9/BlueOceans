
package com.bilibili.player_ix.blue_oceans.api.mob;

import com.bilibili.player_ix.blue_oceans.init.BlueOceansItems;
import com.github.player_ix.ix_api.util.UnmodifiableSet;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.PoiTypeTags;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.nine_abyss.annotation.doc.Message;

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
    @Nullable
    private final VillagerProfession villagerProfession;
    public final String name;
    public final Predicate<Holder<PoiType>> heldJobSite;
    public final Predicate<Holder<PoiType>> acquirableJobSite;
    public final Set<Item> requestedItems;
    public final Set<Block> secondaryPoi;
    @Nullable
    public final SoundEvent workSound;
    public Profession(@Nullable VillagerProfession pProfession, String pName, Predicate<Holder<PoiType>> pHeldJobSite,
                      Predicate<Holder<PoiType>> pAcquirableJobSite, Set<Item> pRequestedItems, Set<Block> pSecondaryPoi,
                      @Nullable SoundEvent pWorkSound) {
        this.villagerProfession = pProfession;
        this.name = pName;
        this.heldJobSite = pHeldJobSite;
        this.acquirableJobSite = pAcquirableJobSite;
        this.requestedItems = pRequestedItems;
        this.secondaryPoi = pSecondaryPoi;
        this.workSound = pWorkSound;
    }

    public Profession(@Nonnull @Message("Must be nonnull") VillagerProfession pProfession) {
        this(pProfession, pProfession.name(), pProfession.heldJobSite(), pProfession.acquirableJobSite(),
                pProfession.requestedItems(), pProfession.secondaryPoi(), pProfession.workSound());
    }

    @Nullable
    public VillagerProfession villagerProfession() {
        return villagerProfession;
    }

    static {
        EMPTY = new Profession(VillagerProfession.NONE);
        ARMORER = new Profession(VillagerProfession.ARMORER);
        FARMER = new Profession(VillagerProfession.FARMER);
        FISHMAN = new Profession(VillagerProfession.FISHERMAN);
        SOLIDER = new Profession(null, "Solider", site->site.is(PoiTypeTags.VILLAGE),
                aqSite->true, UnmodifiableSet.of(BlueOceansItems.SNIPER_RIFLE.get()), UnmodifiableSet.of(),
                SoundEvents.VILLAGER_WORK_BUTCHER);
    }
}
