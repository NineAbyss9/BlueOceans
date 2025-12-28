
package com.bilibili.player_ix.blue_oceans.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CraftingTableBlock;
import net.minecraft.world.level.block.state.BlockState;

public class ForgeBlock
extends CraftingTableBlock {
    private static final Component FORGING = Component.translatable("gui.blue_oceans.forging");
    public ForgeBlock(Properties pProperties) {
        super(pProperties);
    }

    public MenuProvider getMenuProvider(BlockState pState, Level pLevel, BlockPos pPos) {
        return new SimpleMenuProvider((pContainerId, pPlayerInventory, pPlayer) -> null, FORGING);
    }
}
