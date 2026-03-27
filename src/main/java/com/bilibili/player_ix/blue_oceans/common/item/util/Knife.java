
package com.bilibili.player_ix.blue_oceans.common.item.util;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;

public class Knife
extends TieredItem
{
    public Knife(Tier pTier, Properties pProperties)
    {
        super(pTier, pProperties);
    }

    /*public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack)
    {
        return ImmutableMultimap.<Attribute, AttributeModifier>builder().put(Attributes.ATTACK_DAMAGE,
                this.getTier().getAttackDamageBonus()).build();
    }

    private static AttributeModifier ATTACK_D_M = new AttributeModifier();*/
}
