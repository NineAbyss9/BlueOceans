
package com.bilibili.player_ix.blue_oceans.common.item.farming;

import com.bilibili.player_ix.blue_oceans.init.data.ITextureProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.NineAbyss9.math.MathSupport;

public class ChemicalFertilizer
extends Item
implements ITextureProvider
{
    public static final String FERTILITY = "Fertility";
    public ChemicalFertilizer(Properties pProperties) {
        super(pProperties);
    }

    public ChemicalFertilizer() {
        this(new Properties().stacksTo(64));
    }

    public InteractionResult useOn(UseOnContext pContext)
    {
        Level level = pContext.getLevel();
        BlockPos pos = pContext.getClickedPos();
        if (!level.isClientSide) {
            ItemStack stack = pContext.getItemInHand();
            for (int i = 2;i < 6;i++)
            {
                BlockPos pos1 = pos.relative(Direction.values()[i]);
                if (ScytheItem.shouldDrop(level.getBlockState(pos)) == 0) {
                    pos1 = pos.above().relative(Direction.values()[i]);
                }
                BlockState state = level.getBlockState(pos1);
                if (state.getBlock() instanceof CropBlock cropBlock
                    && getFertility(stack) > MathSupport.random.nextFloat()) {
                    ((ServerLevel)level).sendParticles(ParticleTypes.HAPPY_VILLAGER,
                            (double)pos1.getX(), (double)pos1.getY(), (double)pos1.getZ(), 5, 0.2D,
                            0.2D, 0.2D, 0.01D);
                    cropBlock.performBonemeal((ServerLevel)level, level.random, pos1, state);
                }
            }
            return InteractionResult.CONSUME;
        }
        return InteractionResult.SUCCESS;
    }

    public String getLoc()
    {
        return "chemical_fertilizer";
    }

    public String getAddress()
    {
        return "util/cf";
    }

    public ItemStack getDefaultInstance()
    {
        return setFertility(super.getDefaultInstance(), 0.2F);
    }

    public static float getFertility(ItemStack pStack) {
        if (pStack.getTag() == null || !pStack.getTag().contains(FERTILITY))
            setFertility(pStack, 0.2f);
        return pStack.getOrCreateTag().getFloat(FERTILITY);
    }

    public static ItemStack setFertility(ItemStack pStack, float pFertility) {
        pStack.getOrCreateTag().putFloat(FERTILITY, pFertility);
        return pStack;
    }
}
