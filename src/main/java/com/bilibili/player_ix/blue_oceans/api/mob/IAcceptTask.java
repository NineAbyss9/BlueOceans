
package com.bilibili.player_ix.blue_oceans.api.mob;

import com.bilibili.player_ix.blue_oceans.api.task.Task;

public interface IAcceptTask {
    /**Gets the current task.*/
    Task getTask();

    default boolean hasTask(Task pTask) {
        return this.getTask() == pTask;
    }

    default boolean hasTask(int pTask) {
        return this.getTaskId() == pTask;
    }

    /**@see Task#getId()*/
    default int getTaskId() {
        return this.getTask().id;
    }

    /**Sets the current task.*/
    void setTask(int pTask);

    /**@see #setTask(Task)*/
    default void setTask(Task pTask) {
        this.setTask(pTask.id);
    }

    /**Resets the current task to {@linkplain Task#EMPTY}*/
    default void resetTask() {
        this.setTask(Task.EMPTY);
    }

    default boolean isIdle() {
        return this.getTask().equals(Task.EMPTY);
    }
}
