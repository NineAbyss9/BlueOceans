
package com.bilibili.player_ix.blue_oceans.common.item.util;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.state.BlockState;

@SuppressWarnings("deprecation")
public class IntegratedTool
extends DiggerItem {
    public IntegratedTool(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pAttackDamageModifier, pAttackSpeedModifier, pTier, BlockTags.MINEABLE_WITH_PICKAXE, pProperties);
    }

    public float getDestroySpeed(ItemStack pStack, BlockState pState) {
        return this.speed;
    }

    public boolean isCorrectToolForDrops(BlockState pBlock) {
        int i = this.getTier().getLevel();
        if (i < 3 && pBlock.is(BlockTags.NEEDS_DIAMOND_TOOL))
            return false;
        else if (i < 2 && pBlock.is(BlockTags.NEEDS_IRON_TOOL))
            return false;
        else
            return (i >= 1 || !pBlock.is(BlockTags.NEEDS_STONE_TOOL));
    }
}
