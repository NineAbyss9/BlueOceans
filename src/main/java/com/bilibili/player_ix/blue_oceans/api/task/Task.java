
package com.bilibili.player_ix.blue_oceans.api.task;

import javax.annotation.Nullable;

public enum Task {
    EMPTY(0),
    PURSUIT_AND_APPREHENSION(1),
    MEET(2),
    PLANT_FLAG(3),
    FIND_HELP(4),
    FARM(5),
    FOLLOW(6);
    public final int id;
    public final ItemHolder item;
    Task(int pId, @Nullable ItemHolder pHolder) {
        id = pId;
        item = pHolder;
    }

    Task(int pId) {
        this(pId, null);
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
