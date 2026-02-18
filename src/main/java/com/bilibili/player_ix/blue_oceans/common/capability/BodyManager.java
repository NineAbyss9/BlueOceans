
package com.bilibili.player_ix.blue_oceans.common.capability;

import com.bilibili.player_ix.blue_oceans.common.item.biology.organ.Organ;
import org.NineAbyss9.util.lister.Lister;
import org.NineAbyss9.util.lister.SubLister;

public class BodyManager {
    public Lister<Organ> activeOrgans;
    public BodyManager(LivingHealth pHealth) {
        this.activeOrgans = SubLister.of();
    }

    public void tick() {

    }

}
