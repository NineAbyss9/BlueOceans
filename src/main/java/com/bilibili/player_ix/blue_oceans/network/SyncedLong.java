
package com.bilibili.player_ix.blue_oceans.network;

import sun.misc.Unsafe;

public class SyncedLong
{
    private long value;
    public SyncedLong(long pValue)
    {
        value = pValue;
        update();
    }

    public long getValue()
    {
        return value;
    }

    public void increase()
    {
        value++;
        update();
    }

    public void setValue(long l)
    {
        value = l;
        update();
    }

    public void update()
    {
        //var unsafe = Unsafe.getUnsafe();
        //unsafe.putLong(unsafe.getAddress(this.value), this.value);
    }
}
