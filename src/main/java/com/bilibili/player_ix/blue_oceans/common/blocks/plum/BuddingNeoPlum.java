
package com.bilibili.player_ix.blue_oceans.common.blocks.plum;

//import com.bilibili.player_ix.blue_oceans.common.blocks.BoBlockProperties;
import com.bilibili.player_ix.blue_oceans.common.blocks.RedPlumVein;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class BuddingNeoPlum
extends RedPlumVein {
    //public static final IntegerProperty GROW_AGE = BoBlockProperties.GROW_AGE;
    public BuddingNeoPlum(Properties pProperties) {
        super(pProperties);
        //this.registerDefaultState(this.defaultBlockState().setValue(GROW_AGE, 600));
    }

    public boolean isNeoPlum() {
        return true;
    }

    public boolean grow(RandomSource pRandom) {
        return pRandom.nextInt(5) == 0;
    }

    @Nullable
    public BlockState getGrowState(BlockState pState, ServerLevel pLevel, BlockPos pPos) {
        MultifaceBlock block = (MultifaceBlock)BlueOceansBlocks.BUDDING_NEO_PLUM.get();
        return block.getStateForPlacement(pState, pLevel, pPos, Direction.DOWN);
    }
}
