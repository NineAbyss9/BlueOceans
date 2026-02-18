
package com.bilibili.player_ix.blue_oceans.client.gui.menu;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;

public class SurgeryMenu
extends AbstractContainerMenu {
    protected final CraftingContainer craftSlots = new TransientCraftingContainer(this, 4, 4);
    protected final ResultContainer resultSlots = new ResultContainer();
    protected final ContainerLevelAccess access;
    protected final Player player;
    public SurgeryMenu(int pContainerId, Inventory pInventory, ContainerLevelAccess pAccess) {
        super(//TODO:Replace this
                MenuType.CRAFTING,
                pContainerId);
        this.access = pAccess;
        this.player = pInventory.player;
        this.addSlot(new ResultSlot(pInventory.player, this.craftSlots, this.resultSlots, 0,
                //TODO:Replace this
                124, 35));
        for (int i = 0;i < 3;++i) {
            for (int j = 0;j < 3;++j) {
                this.addSlot(new Slot(this.craftSlots, j + i * 3, 30 + j * 18, 17 + i * 18));
            }
        }
        for (int k = 0;k < 3;++k) {
            for (int i1 = 0;i1 < 9;++i1) {
                this.addSlot(new Slot(pInventory, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
            }
        }
        for (int l = 0;l < 9;++l) {
            this.addSlot(new Slot(pInventory, l, 8 + l * 18, 142));
        }
    }

    public SurgeryMenu(int pContainerId, Inventory pInventory) {
        this(pContainerId, pInventory, ContainerLevelAccess.NULL);
    }

    public void removed(Player pPlayer) {
        super.removed(pPlayer);
        this.access.execute((pLevel, pos) -> {
            this.clearContainer(pPlayer, this.craftSlots);
        });
    }

    public int getSize() {
        return 17;
    }

    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (pIndex == 0) {
                this.access.execute((p_39378_, p_39379_) -> {
                    itemstack1.getItem().onCraftedBy(itemstack1, p_39378_, pPlayer);
                });
                if (!this.moveItemStackTo(itemstack1, 10, 46, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(itemstack1, itemstack);
            } else if (pIndex >= 10 && pIndex < 46) {
                if (!this.moveItemStackTo(itemstack1, 1, 10, false)) {
                    if (pIndex < 37) {
                        if (!this.moveItemStackTo(itemstack1, 37, 46, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (!this.moveItemStackTo(itemstack1, 10, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.moveItemStackTo(itemstack1, 10, 46, false)) {
                return ItemStack.EMPTY;
            }
            if (itemstack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(pPlayer, itemstack1);
            if (pIndex == 0) {
                pPlayer.drop(itemstack1, false);
            }
        }
        return itemstack;
    }

    public boolean stillValid(Player pPlayer) {
        return stillValid(this.access, pPlayer,
                //TODO: Replace this
                null);
    }
}
