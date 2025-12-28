
package com.bilibili.player_ix.blue_oceans.util;

import com.bilibili.player_ix.blue_oceans.api.potion.PlumDishPotion;
import com.bilibili.player_ix.blue_oceans.api.potion.PlumDishPotions;
import org.nine_abyss.annotation.PAMAreNonnullByDefault;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@PAMAreNonnullByDefault
public class PlumDishUtil {
    public static final Component NO_EFFECT;
    public PlumDishUtil() {
    }

    public static List<MobEffectInstance> getMobEffects(ItemStack stackIn) {
        return getAllEffects(stackIn.getTag());
    }

    public static List<MobEffectInstance> getAllEffects(PlumDishPotion pPotion, Collection<MobEffectInstance> pEffects) {
        List<MobEffectInstance> $$2 = Lists.newArrayList();
        $$2.addAll(pPotion.getEffects());
        $$2.addAll(pEffects);
        return $$2;
    }

    public static List<MobEffectInstance> getAllEffects(@Nullable CompoundTag pTag) {
        List<MobEffectInstance> $$1 = Lists.newArrayList();
        $$1.addAll(getPotion(pTag).getEffects());
        getCustomEffects(pTag, $$1);
        return $$1;
    }

    public static List<MobEffectInstance> getCustomEffects(ItemStack stackIn) {
        return getCustomEffects(stackIn.getTag());
    }

    public static List<MobEffectInstance> getCustomEffects(@Nullable CompoundTag pTag) {
        List<MobEffectInstance> $$1 = Lists.newArrayList();
        getCustomEffects(pTag, $$1);
        return $$1;
    }

    public static void getCustomEffects(@Nullable CompoundTag pTag, List<MobEffectInstance> pEffects) {
        if (pTag != null && pTag.contains("DishPotion", 9)) {
            ListTag $$2 = pTag.getList("DishPotion", 10);
            for(int $$3 = 0; $$3 < $$2.size(); ++$$3) {
                CompoundTag $$4 = $$2.getCompound($$3);
                MobEffectInstance $$5 = MobEffectInstance.load($$4);
                if ($$5 != null) {
                    pEffects.add($$5);
                }
            }
        }
    }

    public static PlumDishPotion getPotion(ItemStack stackIn) {
        return getPotion(stackIn.getTag());
    }

    public static PlumDishPotion getPotion(@Nullable CompoundTag pTag) {
        return pTag == null ? PlumDishPotions.EMPTY : PlumDishPotions.get(pTag.getString("DishPotion"));
    }

    public static ItemStack setPotion(ItemStack stackIn, PlumDishPotion pPotion) {
        ResourceLocation $$2 = new ResourceLocation(pPotion.toString());
        if (pPotion == PlumDishPotions.EMPTY) {
            stackIn.removeTagKey("DishPotion");
        } else {
            stackIn.getOrCreateTag().putString("DishPotion", $$2.toString());
        }
        return stackIn;
    }

    public static void addText(ItemStack stack, List<Component> components) {
        addText(getMobEffects(stack), components);
    }

    public static void addText(List<MobEffectInstance> pEffects, List<Component> pTexts) {
        List<Pair<Attribute, AttributeModifier>> lp = Lists.newArrayList();
        Iterator<?> var4;
        MutableComponent mc;
        MobEffect me;
        if (pEffects.isEmpty()) {
            pTexts.add(NO_EFFECT);
        } else {
            for(var4 = pEffects.iterator(); var4.hasNext(); pTexts.add(mc.withStyle(me.getCategory().getTooltipFormatting()))) {
                MobEffectInstance mi = (MobEffectInstance)var4.next();
                mc = Component.translatable(mi.getDescriptionId());
                me = mi.getEffect();
                Map<Attribute, AttributeModifier> m2 = me.getAttributeModifiers();
                if (!m2.isEmpty()) {
                    for (Map.Entry<Attribute, AttributeModifier> attributeAttributeModifierEntry : m2.entrySet()) {
                        AttributeModifier am = attributeAttributeModifierEntry.getValue();
                        AttributeModifier am1 = new AttributeModifier(am.getName(), me.getAttributeModifierValue(mi
                                .getAmplifier(), am), am.getOperation());
                        lp.add(new Pair<>(attributeAttributeModifierEntry.getKey(), am1));
                    }
                }
                if (mi.getAmplifier() > 0) {
                    mc = Component.translatable("potion.withAmplifier", mc, Component.translatable("potion.potency." + mi.getAmplifier()));
                }
                if (!mi.endsWithin(20)) {
                    mc = Component.translatable("potion.withDuration", mc, MobEffectUtil.formatDuration(mi, 1.0f));
                }
            }
        }
        if (!lp.isEmpty()) {
            pTexts.add(CommonComponents.EMPTY);
            pTexts.add(Component.translatable("potion.whenDrank").withStyle(ChatFormatting.DARK_PURPLE));
            for (Pair<Attribute, AttributeModifier> $$11 : lp) {
                AttributeModifier $$12 = $$11.getSecond();
                double $$13 = $$12.getAmount();
                double $$15;
                if ($$12.getOperation() != AttributeModifier.Operation.MULTIPLY_BASE && $$12.getOperation()
                        != AttributeModifier.Operation.MULTIPLY_TOTAL) {
                    $$15 = $$12.getAmount();
                } else {
                    $$15 = $$12.getAmount() * 100.0;
                }
                if ($$13 > 0.0) {
                    pTexts.add(Component.translatable("attribute.modifier.plus." + $$12.getOperation().toValue(),
                            ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format($$15), Component.translatable(
                                    $$11.getFirst().getDescriptionId())).withStyle(ChatFormatting.BLUE));
                } else if ($$13 < 0.0) {
                    $$15 *= -1.0;
                    pTexts.add(Component.translatable("attribute.modifier.take." + $$12.getOperation().toValue(),
                            ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format($$15), Component.translatable(
                                    $$11.getFirst().getDescriptionId())).withStyle(ChatFormatting.RED));
                }
            }
        }
    }

    static {
        NO_EFFECT = Component.translatable("plum_dish_potion.none").withStyle(ChatFormatting.GRAY);
    }
}
