
package com.bilibili.player_ix.blue_oceans.common.entities.ai.behavior.villager;

import com.bilibili.player_ix.blue_oceans.api.mob.Profession;
import com.bilibili.player_ix.blue_oceans.common.entities.ai.behavior.Behavior;
import com.bilibili.player_ix.blue_oceans.common.entities.villagers.BaseVillager;
import com.github.player_ix.ix_api.util.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import org.nine_abyss.util.IXUtil;
import org.nine_abyss.util.IXUtilUser;

public class MineBehavior<E extends BaseVillager>
extends Behavior
implements IXUtilUser {
    private final E mob;
    protected int destroyBlockProgress;
    public MineBehavior(E pMob) {
        mob = pMob;
    }

    public boolean canUse() {
        return this.mob.getProfession() == Profession.MINER;
    }

    public boolean canContinueToUse() {
        return this.mob.targetPosOptional().isPresent() && this.isPickaxeReady();
    }

    public boolean isPickaxeReady() {
        return this.mob.isHolding(stack -> stack.is(ItemTags.PICKAXES));
    }

    public boolean canReach(BlockPos pPos) {
        return this.mob.distanceToSqr(pPos.getX() + 0.5, pPos.getY(), pPos.getZ() + 0.5) <
                this.mob.getAttributeValue(ForgeMod.BLOCK_REACH.get());
    }

    public void tick() {
        @SuppressWarnings("all")
        BlockPos pos = this.mob.targetPosOptional().get();
        if (canReach(pos)) {
            this.mob.getNavigation().stop();
            mob.level().destroyBlockProgress(0, pos, 0);
        } else {
            Vec3 truePos = BlockUtil.getTruePos(pos);
            this.mob.getNavigation().moveTo(truePos.x, truePos.y, truePos.z, 1.0);
        }
    }

    protected <T> T convert() {
        return IXUtil.c.convert(mob);
    }
}
