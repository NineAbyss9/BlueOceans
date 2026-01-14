
package com.bilibili.player_ix.blue_oceans.common.item.food;

import com.github.player_ix.ix_api.util.Maths;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class Coffee
extends FoodItem {
    public static final String JAVA = "Java";
    public Coffee(int pNutrition, float pSaturation, MobEffectInstance... effects) {
        super(pNutrition, pSaturation, 1.0F, effects);
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
