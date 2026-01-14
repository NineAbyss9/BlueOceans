
package com.bilibili.player_ix.blue_oceans.client.gui.menu;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.ItemCombinerMenuSlotDefinition;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.nine_abyss.util.function.FunctionCollector;

public class ForgeMenu
extends ItemCombinerMenu {
    private int poundCount;
    public ForgeMenu(int pContainerId, Inventory pPlayerInventory,
                     ContainerLevelAccess pAccess) {
        super(null, pContainerId, pPlayerInventory, pAccess);
    }

    public static ForgeMenu register(int pContainerId, Inventory inventory) {
        return new ForgeMenu(pContainerId, inventory, ContainerLevelAccess.NULL);
    }

    protected boolean mayPickup(Player pPlayer, boolean pHasStack) {
        return false;
    }

    protected void onTake(Player pPlayer, ItemStack pStack) {

    }

    protected boolean isValidBlock(BlockState pState) {
        return false;
    }

    public void createResult() {

    }

    public int getPoundCount() {
        return this.poundCount;
    }

    public void increasePoundCount() {
        poundCount++;
    }

    protected ItemCombinerMenuSlotDefinition createInputSlotDefinitions() {
        return ItemCombinerMenuSlotDefinition.create().withSlot(0, 26, 45, FunctionCollector.alwaysTrue())
                .withSlot(1, 75, 45, FunctionCollector.alwaysTrue())
                .withResultSlot(2, 133, 45).build();
    }
}
