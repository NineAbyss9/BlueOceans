
package com.bilibili.player_ix.blue_oceans.common.blocks.home;

import com.bilibili.player_ix.blue_oceans.common.blocks.BoBlockProperties;
import com.github.NineAbyss9.ix_api.util.ParticleUtil;
import com.github.NineAbyss9.ix_api.util.Vec9;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.NineAbyss9.math.AbyssMath;

@SuppressWarnings("deprecation")
public class OilLamp
extends Block
{
    public static final VoxelShape SHAPE;
    protected static final BooleanProperty LIT;
    protected static final IntegerProperty CAPACITY;
    public OilLamp(Properties pProperties)
    {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(LIT, Boolean.FALSE));
    }

    public OilLamp()
    {
        this(Properties.of());
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
        pBuilder.add(LIT);
    }

    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom)
    {
        if (pState.getValue(LIT) && pRandom.nextFloat() < 0.05F) {
            ParticleUtil.addParticle(pLevel, ParticleTypes.FLAME, Vec9.of(pPos).add(0d, 0.3d, 0d),
                    AbyssMath.random(0.3), 0.1, AbyssMath.random(0.3));
        }
    }

    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand,
                                 BlockHitResult pHit)
    {
        ItemStack stack = pPlayer.getItemInHand(pHand);
        if (stack.is(this.asItem())) {
            this.drops.toDebugFileName();
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom)
    {
        if (pLevel.getGameTime() % 1200L == 0L && pState.getValue(LIT)) {
            pState = pState.setValue(CAPACITY, pState.getValue(CAPACITY) - 1);
            if (pState.getValue(CAPACITY) == 0) {
                pState = pState.cycle(LIT);
            }
            pLevel.setBlock(pPos, pState, 3);
        }
        if (pState.getValue(CAPACITY) > 0 && !pState.getValue(LIT)) {
            pLevel.setBlock(pPos, pState.cycle(LIT), 3);
        }
    }

    static {
        SHAPE = box(6, 0, 6, 10, 8, 10);
        LIT = BlockStateProperties.LIT;
        CAPACITY = BoBlockProperties.CAPACITY;
    }
}
