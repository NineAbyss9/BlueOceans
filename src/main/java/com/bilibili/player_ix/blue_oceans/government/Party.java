
package com.bilibili.player_ix.blue_oceans.government;

import net.minecraft.world.entity.LivingEntity;

public class Party {
    private final Ideology ideology;
    private LivingEntity leader;
    private float approvalRating;
    public Party(Ideology pIdeology, LivingEntity pLeader) {
        this.ideology = pIdeology;
        this.setLeader(pLeader);
    }

    public LivingEntity leader() {
        return this.leader;
    }

    public void setLeader(LivingEntity pLeader) {
        this.leader = pLeader;
    }

    public Ideology ideology() {
        return ideology;
    }

    public float approvalRating() {
        return approvalRating;
    }

    public void setApprovalRating(float pApprovalRating) {
        this.approvalRating = pApprovalRating;
    }
}
