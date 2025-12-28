
package com.bilibili.player_ix.blue_oceans.api.task;

public class ItemSupplier
implements ItemHolder {
    private final Object obj;
    public ItemSupplier(Object object) {
        this.obj = object;
    }

    public Object getTaskItem() {
        return obj;
    }
}
