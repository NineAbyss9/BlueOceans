
package com.bilibili.player_ix.blue_oceans.api.mob;

import com.bilibili.player_ix.blue_oceans.api.task.Task;

public interface IAcceptTask {
    Task getTask();

    default int getTaskId() {
        return this.getTask().id;
    }

    void setTask(int pTask);

    default void setTask(Task pTask) {
        this.setTask(pTask.id);
    }

    default void resetTask() {
        this.setTask(Task.EMPTY);
    }
}
