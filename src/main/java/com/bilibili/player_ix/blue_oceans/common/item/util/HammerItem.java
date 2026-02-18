
package com.bilibili.player_ix.blue_oceans.common.item.util;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.NineAbyss9.util.Option;

public class HammerItem
extends DiggerItem {
    public HammerItem(float pAttackDamageModifier, float pAttackSpeedModifier, Tier pTier, Properties pProperties) {
        super(pAttackDamageModifier, pAttackSpeedModifier, pTier, BlockTags.MINEABLE_WITH_PICKAXE, pProperties);
    }

    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        BlockPos pos = pContext.getClickedPos();
        BlockState state = level.getBlockState(pos);
        ItemStack stack = pContext.getItemInHand();
        if (!state.isAir() && state.getBlock().defaultDestroyTime() < 8.0F && isCorrectToolForDrops(stack, state)) {
            level.destroyBlock(pos, true, pContext.getPlayer());
            Option.ofNullable(pContext.getPlayer()).ifPresent(player -> stack.hurtAndBreak(
                    1, player, a -> a.broadcastBreakEvent(pContext.getHand())));
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.useOn(pContext);
    }
}
