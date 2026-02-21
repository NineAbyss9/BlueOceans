
package com.bilibili.player_ix.blue_oceans.common.item.food;

import com.github.NineAbyss9.ix_api.util.Maths;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class Coffee
extends FoodItem {
    public static final String JAVA = "Java";
    public Coffee() {
        super(new Properties());
    }

    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.DRINK;
    }

    public int getUseDuration(ItemStack pStack) {
        return 20;
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        return ItemUtils.startUsingInstantly(pLevel, pPlayer, pUsedHand);
    }

    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        if (isJava(pStack)) {
            pLivingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, Maths.minuteToTick(5),
                    4));
        }
        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }

    public static boolean isJava(ItemStack stack) {
        return stack.getDisplayName().getString().equals(JAVA);
    }
}
