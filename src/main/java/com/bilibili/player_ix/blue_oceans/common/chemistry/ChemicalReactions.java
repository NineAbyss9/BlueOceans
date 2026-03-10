
package com.bilibili.player_ix.blue_oceans.common.chemistry;

import org.NineAbyss9.util.lister.SubLister;

public class ChemicalReactions {
    public static final ChemicalReaction IRON_WITH_OXYGEN;

    static {
        IRON_WITH_OXYGEN = new ChemicalReaction(SubLister.of(ChemicalFormulas.O2), SubLister.of(), Conditions.EMPTY);
    }
}
