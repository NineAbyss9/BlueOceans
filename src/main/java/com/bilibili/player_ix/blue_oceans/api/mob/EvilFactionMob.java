
package com.bilibili.player_ix.blue_oceans.api.mob;

import com.bilibili.player_ix.blue_oceans.api.task.Task;

public interface EvilFactionMob
extends IAcceptTask {
    default Task getTask() {
        return Task.EMPTY;
    }
}
