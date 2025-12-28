
package com.bilibili.player_ix.blue_oceans.common.blocks.chemistry;

import com.bilibili.player_ix.blue_oceans.common.blocks.OreBlock;
import com.bilibili.player_ix.blue_oceans.common.chemistry.Element;
import com.bilibili.player_ix.blue_oceans.common.chemistry.IElement;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.SoundType;

public class AbstractElementOre
extends OreBlock
implements IElement {
    protected final Element element;
    public AbstractElementOre(float pDestroyTime, float pExplosionResistance,
                              SoundType pSoundType, DyeColor pColor, Element pElement) {
        this(Properties.of().strength(pDestroyTime, pExplosionResistance)
                .requiresCorrectToolForDrops().sound(pSoundType).mapColor(pColor), pElement);
    }

    public AbstractElementOre(Properties pProperties, IntProvider xpRange, Element pElement) {
        super(pProperties, xpRange);
        this.element = pElement;
    }

    public AbstractElementOre(Properties pProperties, Element pElement) {
        super(pProperties);
        this.element = pElement;
    }

    public Element getElement() {
        return element;
    }
}
