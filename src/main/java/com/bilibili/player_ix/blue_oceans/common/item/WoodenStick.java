
package com.bilibili.player_ix.blue_oceans.common.item;

import com.bilibili.player_ix.blue_oceans.compat.noixapi.NoIXApiCompat;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansItems;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansMobEffects;
import com.github.NineAbyss9.ix_api.util.Maths;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.NineAbyss9.math.MathSupport;
import org.NineAbyss9.util.ValueHolder;

public class WoodenStick
extends PickaxeItem
{
    public WoodenStick() {
        super(Tiers.WOOD, 6, -3.0F, new Properties());
    }

    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        pTarget.addEffect(new MobEffectInstance(ValueHolder.nullToOther(NoIXApiCompat.getApiEffect("stun"),
                BlueOceansMobEffects.STUN.get()), Maths.toTick(2), 0));
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    public InteractionResult useOn(UseOnContext pContext) {
        BlockPos pos = pContext.getClickedPos();
        Level level = pContext.getLevel();
        BlockState state = level.getBlockState(pos);
        if (state.is(BlockTags.ICE)) {
            boolean drop = false;
            ItemStack stack;
            int i = MathSupport.random.nextInt(4);
            if (i == 1) {
                stack = new ItemStack(BlueOceansItems.ICE_AXE.get());
            } else if (i == 2) {
                stack = new ItemStack(BlueOceansItems.ICE_SWORD.get());
            } else if (i == 3) {
                stack = new ItemStack(BlueOceansItems.ICE_PICKAXE.get());
            } else {
                stack = ItemStack.EMPTY;
                drop = true;
            }
            if (!drop)
                level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), stack));
            level.destroyBlock(pos, drop, pContext.getPlayer());
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.PASS;
    }
}
