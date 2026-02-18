
package com.bilibili.player_ix.blue_oceans.api.task;

import com.bilibili.player_ix.blue_oceans.init.BlueOceansItems;
import net.minecraft.world.item.ItemStack;

public enum Task {
    EMPTY(0),
    PURSUIT_AND_APPREHENSION(1),
    MEET(2),
    PLANT_FLAG(3, new ItemSupplier(new ItemStack(BlueOceansItems.FLAG.get()))),
    FIND_HELP(4),
    FARM(5),
    FOLLOW(6),
    WORK(7),
    DEFECATION(8),
    WEAVE_WEB(9),
    DIG(10),
    HIDE(11),
    WAKE(12),
    CLEAR_PLUMS(13, BlueOceansItems.ECHO_POTION::get);
    public final int id;
    public final ItemHolder item;
    Task(int pId, ItemHolder pHolder) {
        id = pId;
        item = pHolder;
    }

    Task(int pId) {
        this(pId, () -> null);
    }

    public int getId() {
        return id;
    }

    public static Task fromId(int pId) {
        for (Task task : values()) {
            if (pId != task.id)
                continue;
            return task;
        }
        return EMPTY;
    }
}
