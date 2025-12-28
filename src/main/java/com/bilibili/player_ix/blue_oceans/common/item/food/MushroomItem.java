
package com.bilibili.player_ix.blue_oceans.common.item.food;

//import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
//import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
//import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
//import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
//import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

@SuppressWarnings("all")
public class MushroomItem
extends BlockItem {
    public MushroomItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    public MushroomItem(Block pBlock, Properties pProperties, int n, float s) {
        this(pBlock, pProperties.food(new FoodProperties.Builder().nutrition(n).saturationMod(s).build()));
    }

    public MushroomItem(Block pBlock, Properties pProperties, int n, float s, int pToxicLevel) {
        this(pBlock, pProperties.food(new FoodProperties.Builder().nutrition(n).saturationMod(s).effect(
                ()-> new MobEffectInstance(MobEffects.POISON, 30, pToxicLevel),
                pToxicLevel * 0.05F).build()));
    }

    public InteractionResult useOn(UseOnContext pContext) {
        InteractionResult interactionresult = this.place(new BlockPlaceContext(pContext));
        if (interactionresult.consumesAction()) {
            return interactionresult;
        } else {
            if (this.isEdible()) {
                InteractionResult interactionresult1 = this.use(pContext.getLevel(), pContext.getPlayer(),
                        pContext.getHand()).getResult();
                return interactionresult1 == InteractionResult.CONSUME ? InteractionResult.CONSUME_PARTIAL :
                        interactionresult1;
            }
            return InteractionResult.PASS;
        }
    }

    /*public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        if (itemstack.isEdible()) {
            if (pPlayer.canEat(itemstack.getFoodProperties(pPlayer).canAlwaysEat())) {
                pPlayer.startUsingItem(pUsedHand);
                return InteractionResultHolder.consume(itemstack);
            } else {
                return InteractionResultHolder.fail(itemstack);
            }
        } else {
            return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));
        }
    }*/
}
