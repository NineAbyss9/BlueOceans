
package com.bilibili.player_ix.blue_oceans.common.item.util.sword;

import com.bilibili.player_ix.blue_oceans.api.item.BoTier;
import com.bilibili.player_ix.blue_oceans.common.chemistry.IElement;
import com.bilibili.player_ix.blue_oceans.common.chemistry.Element;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class ElementSword
extends SwordItem
implements IElement {
    protected final Element element;
    public ElementSword(Element pElement, Tier pTier, int pAttackDamage, float pAttackSpeed, Properties pProperties) {
        super(pTier, pAttackDamage, pAttackSpeed, pProperties);
        element = pElement;
    }

    public ElementSword(Element pElement, int pLevel, int pEv, String pName, int pAttackDamageModifier) {
        this(pElement, BoTier.get(pElement, pLevel, pEv, pName), pAttackDamageModifier, -2.4F,
                new Properties());
    }

    public Element getElement() {
        return element;
    }
}
