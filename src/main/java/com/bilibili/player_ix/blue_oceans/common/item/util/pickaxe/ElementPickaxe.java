
package com.bilibili.player_ix.blue_oceans.common.item.util.pickaxe;

import com.bilibili.player_ix.blue_oceans.api.item.BoTier;
import com.bilibili.player_ix.blue_oceans.common.chemistry.IElement;
import com.bilibili.player_ix.blue_oceans.common.chemistry.Element;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;

public class ElementPickaxe
extends PickaxeItem
implements IElement {
    protected final Element element;
    public ElementPickaxe(Element pElement, Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier,
                          Properties properties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, properties);
        this.element = pElement;
    }

    public ElementPickaxe(Element pElement, int pLevel, int pEv, String pName,  int pAttackDamageModifier,
                          float pAttackSpeedModifier) {
        this(pElement, BoTier.get(pElement, pLevel, pEv, pName), pAttackDamageModifier, pAttackSpeedModifier,
                new Properties().stacksTo(1));
    }

    public Element getElement() {
        return element;
    }
}
