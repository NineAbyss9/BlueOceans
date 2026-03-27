
package com.bilibili.player_ix.blue_oceans.common.item.biology;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

//Zoom
public class Loupe
extends Item
{
    public Loupe(Properties pProperties)
    {
        super(pProperties);
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand)
    {
        pLevel.playLocalSound(pPlayer.blockPosition(), SoundEvents.ELDER_GUARDIAN_CURSE,
                SoundSource.BLOCKS, 1f, 1f, false);
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
