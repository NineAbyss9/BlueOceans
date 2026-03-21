
package com.bilibili.player_ix.blue_oceans.init.data;

public interface ITextureProvider
{
    default String getAddress()
    {
        return "";
    }

    default String getLoc()
    {
        return "";
    }
}
