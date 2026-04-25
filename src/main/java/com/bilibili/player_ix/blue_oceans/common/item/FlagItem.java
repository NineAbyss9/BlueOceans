
package com.bilibili.player_ix.blue_oceans.common.item;

import com.bilibili.player_ix.blue_oceans.init.BlueOceansBlocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Rarity;

public class FlagItem extends BlockItem {
    protected final Type type;
    public FlagItem(Type pType, Rarity pRarity) {
        super(BlueOceansBlocks.FLAG_BLOCK.get(), new Properties().stacksTo(64).rarity(pRarity));
        this.type = pType;
    }

    public FlagItem() {
        this(Type.EVIL_FACTION, Rarity.UNCOMMON);
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        EVIL_FACTION(1),
        PLAYER(2);
        public final int id;
        Type(int pId) {
            this.id =pId;
        }
    }
}
