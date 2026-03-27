
package com.bilibili.player_ix.blue_oceans.common.blocks.nature.land;

import com.bilibili.player_ix.blue_oceans.common.blocks.nature.PlantBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;

@SuppressWarnings("deprecation")
public class ThornBlock
extends PlantBlock
{
    public ThornBlock(Properties pProperties)
    {
        super(pProperties);
    }

    public ThornBlock()
    {
        this(Properties.of().noCollission().speedFactor(0.8f).strength(2f, 3f)
                .mapColor(MapColor.COLOR_GREEN));
    }

    public void thorn(BlockState pState, Level pLevel, BlockPos pPos, LivingEntity pEntity)
    {
        pEntity.hurt(pLevel.damageSources().cactus(), 1.5f);
        if (pLevel.getDifficulty() == Difficulty.HARD) {
            pEntity.addEffect(new MobEffectInstance(MobEffects.POISON, 200));
        }
    }

    public void playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer)
    {
        if (pPlayer.getMainHandItem().isEmpty())
            this.thorn(pState, pLevel, pPos, pPlayer);
        super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
    }

    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity)
    {
        if (pEntity instanceof LivingEntity entity && (entity.getItemBySlot(EquipmentSlot.LEGS)
                .isEmpty() || entity.getItemBySlot(EquipmentSlot.FEET).isEmpty())) {
            this.thorn(pState, pLevel, pPos, entity);
        }
    }
}
