
package com.bilibili.player_ix.blue_oceans.common.item.util.axe;

import com.bilibili.player_ix.blue_oceans.api.item.BoTier;
import com.bilibili.player_ix.blue_oceans.common.chemistry.IElement;
import com.bilibili.player_ix.blue_oceans.common.chemistry.Element;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Tier;

public class ElementAxe
extends AxeItem
implements IElement {
    protected final Element element;
    public ElementAxe(Element pElement, Tier pTier, float pAttackDamageModifier, float pAttackSpeedModifier,
                      Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
        this.element = pElement;
    }

    public ElementAxe(Element pElement, int pLevel, int pEv, String pName,  int pAttackDamageModifier,
                          float pAttackSpeedModifier) {
        this(pElement, BoTier.get(pElement, pLevel, pEv, pName), pAttackDamageModifier, pAttackSpeedModifier,
                new Properties());
    }

    public Element getElement() {
        return element;
    }
}
