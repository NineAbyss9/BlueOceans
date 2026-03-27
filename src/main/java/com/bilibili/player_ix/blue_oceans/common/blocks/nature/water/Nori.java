
package com.bilibili.player_ix.blue_oceans.common.blocks.nature.water;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

//饼菜
public class Nori
extends AquaticPlant
{
    public Nori(Properties pProperties)
    {
        super(pProperties);
    }

    public boolean isShearable(ItemStack item, Level level, BlockPos pos)
    {
        return true;
    }

    public List<ItemStack> onSheared(@Nullable Player player, ItemStack item, Level level, BlockPos pos, int fortune)
    {
        List<ItemStack> result = new ArrayList<>();
        for (int i = 0;i < 3 + fortune;i++) result.add(this.asItem().getDefaultInstance());
        return result;
    }
}
