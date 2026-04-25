
package com.bilibili.player_ix.blue_oceans.world;

import com.bilibili.player_ix.blue_oceans.common.blocks.chemistry.gas.GasEnum;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class ExplosionData
{
    public final float power;
    public final boolean fire;
    public GasEnum gasEnum = null;
    public BlockPos pos = null;
    public ExplosionData(float powerIn, boolean fireIn)
    {
        this.power = powerIn;
        this.fire = fireIn;
    }

    public ExplosionData gas(GasEnum gasEnumIn)
    {
        this.gasEnum = gasEnumIn;
        return this;
    }

    public boolean shouldSpawnGas()
    {
        return this.gasEnum != null;
    }

    public void spawnGas(Level pLevel) {

    }
}
