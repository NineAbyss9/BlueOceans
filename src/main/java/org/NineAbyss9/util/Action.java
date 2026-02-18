
package org.NineAbyss9.util;

import org.NineAbyss9.util.function.FunctionCollector;

public class Action {
    private final Runnable action;
    private final Runnable action1;
    public Action(Runnable trueAction, Runnable falseAction) {
        this.action = trueAction;
        this.action1 = falseAction;
    }

    public void run(boolean flag) {
        if (flag)
            action.run();
        else
            action1.run();
    }

    public static Action emptyFalse(Runnable trueAction) {
        return new Action(trueAction, FunctionCollector.emptyAction());
    }

    public static Action emptyTrue(Runnable falseAction) {
        return new Action(FunctionCollector.emptyAction(), falseAction);
    }
}
