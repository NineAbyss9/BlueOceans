
package com.bilibili.player_ix.blue_oceans.common.item.util;

import com.bilibili.player_ix.blue_oceans.api.item.BoTier;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

//电锯
public class Chainsaw
extends DiggerItem {
    public static final String STARTED_TAG = "Started";
    public static final String USE_TIME_TAG = "UseTime";
    public Chainsaw(float pAttackDamageModifier, float pAttackSpeedModifier, Tier pTier, Properties pProperties) {
        super(pAttackDamageModifier, pAttackSpeedModifier, pTier, BlockTags.MINEABLE_WITH_AXE, pProperties);
    }

    public Chainsaw() {
        this(6.0F, -3.4F, BoTier.IRON, new Properties());
    }

    public InteractionResult useOn(UseOnContext pContext) {
        ItemStack stack = pContext.getItemInHand();
        if (isStarted(stack)) {
            BlockPos pos = pContext.getClickedPos();
            Level level = pContext.getLevel();
            BlockState state = level.getBlockState(pos);
            if (this.isCorrectToolForDrops(stack, state)) {
                level.destroyBlock(pos, true, pContext.getPlayer());
            }
        }
        return super.useOn(pContext);
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);
        if (isStarted(stack))
            if (getAndPlusUseTime(stack) >= this.getUseDuration(stack)) {
                setStarted(stack, false);
                setUseTime(stack, 0);
            }
        else {
            if (getAndPlusUseTime(stack) >= this.getUseDuration(stack)) {
                setStarted(stack, true);
                setUseTime(stack, 0);
            }
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    public int getUseDuration(ItemStack pStack) {
        return isStarted(pStack) ? 21 : 61;
    }

    public static boolean isStarted(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return tag != null && tag.getBoolean(STARTED_TAG);
    }

    public static void setStarted(ItemStack stack, boolean flag) {
        stack.getOrCreateTag().putBoolean(STARTED_TAG, flag);
    }

    public static int getUseTime(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return tag != null ? tag.getInt(USE_TIME_TAG) : 0;
    }

    public static void setUseTime(ItemStack stack, int useTime) {
        stack.getOrCreateTag().putInt(USE_TIME_TAG, useTime);
    }

    public static int getAndPlusUseTime(ItemStack stack) {
        setUseTime(stack, getUseTime(stack) + 1);
        //Waiting...


        return getUseTime(stack);
    }
}
