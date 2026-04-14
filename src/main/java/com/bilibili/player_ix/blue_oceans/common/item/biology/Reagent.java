
package com.bilibili.player_ix.blue_oceans.common.item.biology;

import com.bilibili.player_ix.blue_oceans.common.chemistry.Content;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

import javax.annotation.Nullable;
import java.util.List;

public class Reagent extends Item
{
    public static final String CONTENT_TAG = "Content";
    public Reagent(Properties pProperties)
    {
        super(pProperties);
    }

    public Reagent()
    {
        this(new Properties());
    }

    @SuppressWarnings("all")
    public InteractionResult useOn(UseOnContext pContext)
    {
        Level level = pContext.getLevel();
        Player player = pContext.getPlayer();
        BlockPos pos = pContext.getClickedPos();
        BlockState state = level.getBlockState(pos);
        FluidState fluidState = state.getFluidState();
        if (state.isAir() || player == null || fluidState == null)
            return InteractionResult.PASS;
        else {
            ItemStack stack = player.getItemInHand(pContext.getHand());
            setContent(stack.getOrCreateTag(), Content.get(state.getFluidState()).id);
            return InteractionResult.SUCCESS;
        }
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand)
    {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);
        var result = BlueOceansHooks.onUseContent(pPlayer, getContent(stack.getOrCreateTag()));
        if (result.getBool()) {

            return InteractionResultHolder.sidedSuccess(stack, pLevel.isClientSide);
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    public void appendHoverText(ItemStack pStack, Level pLevel, List<Component> pTooltipComponents,
                                TooltipFlag pIsAdvanced)
    {
        Content content = getContent(pStack.getTag());
        pTooltipComponents.add(content.description());
    }

    public static Content getContent(@Nullable CompoundTag pTag)
    {
        if (pTag == null || !pTag.contains(CONTENT_TAG))
            return Content.EMPTY;
        return Content.of(pTag.getInt(CONTENT_TAG));
    }

    public static void setContent(CompoundTag pTag, int value)
    {
        pTag.putInt(CONTENT_TAG, value);
    }
}
