
package org.nine_abyss.util;

public class Action {
    private final Runnable action;
    private final Runnable action1;
    private Action(Runnable trueAction, Runnable falseAction) {
        this.action = trueAction;
        this.action1 = falseAction;
    }

    public void run(boolean flag) {
        if (flag)
            action.run();
        else
            action1.run();
    }
}
