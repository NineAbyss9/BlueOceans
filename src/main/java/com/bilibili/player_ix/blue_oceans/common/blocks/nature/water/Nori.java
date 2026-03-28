
package com.bilibili.player_ix.blue_oceans.common.blocks.nature.water;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

//饼菜
public class Nori
extends AquaticPlant
{
    public Nori(Properties pProperties)
    {
        super(pProperties, (player, item, level, pos, fortune) -> {
            List<ItemStack> result = new ArrayList<ItemStack>();
            for (int i = 0;i < 2 + fortune;i++) result.add(new ItemStack(Items.AIR));
            return result;
        });
    }

    public boolean isShearable(ItemStack item, Level level, BlockPos pos)
    {
        return true;
    }
}
