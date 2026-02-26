
package com.bilibili.player_ix.blue_oceans.common.entities.animal.flying;

import com.bilibili.player_ix.blue_oceans.api.mob.CompletelyPerverseState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class Butterfly
extends AbstractFlyingAnimal
implements CompletelyPerverseState.Interface {
    private CompletelyPerverseState state;
    public Butterfly(EntityType<? extends Butterfly> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.state = CompletelyPerverseState.LARVA;
    }

    public void setPerState(CompletelyPerverseState pState) {
        this.state = pState;
    }

    public CompletelyPerverseState getPerState() {
        return state;
    }
}
