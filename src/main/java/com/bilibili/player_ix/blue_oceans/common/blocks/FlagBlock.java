
package com.bilibili.player_ix.blue_oceans.common.blocks;

import com.bilibili.player_ix.blue_oceans.common.item.FlagItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class FlagBlock
extends Block {
    private static final IntegerProperty DATA_TYPE;
    private final FlagItem.Type type;
    public FlagBlock(FlagItem.Type pType) {
        super(Properties.of().instabreak().noCollission());
        this.registerDefaultState(this.defaultBlockState().setValue(DATA_TYPE, 1));
        this.type = pType;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(DATA_TYPE);
    }

    public FlagItem.Type getType() {
        return type;
    }

    static {
        DATA_TYPE = IntegerProperty.create("type", 1, 2);
    }
}
