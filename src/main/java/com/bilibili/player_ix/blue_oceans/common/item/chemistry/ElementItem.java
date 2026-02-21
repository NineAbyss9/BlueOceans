
package com.bilibili.player_ix.blue_oceans.common.item.chemistry;

import com.bilibili.player_ix.blue_oceans.api.item.ILockedItem;
import com.bilibili.player_ix.blue_oceans.common.chemistry.IElement;
import com.bilibili.player_ix.blue_oceans.common.chemistry.Element;
import com.github.NineAbyss9.ix_api.api.item.BaseItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ElementItem
extends BaseItem
implements IElement, ILockedItem {
    protected final Element element;
    public ElementItem(Properties properties, Element pElement) {
        super(properties);
        this.element = pElement;
    }

    public ElementItem(Element pElement) {
        this(new Properties().stacksTo(64), pElement);
    }

    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel,
                                List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(this.description());
    }

    public Element getElement() {
        return this.element;
    }
}
