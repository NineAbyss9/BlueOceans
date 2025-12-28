
package com.bilibili.player_ix.blue_oceans.common.blocks;

import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.NeoPlum;
import com.bilibili.player_ix.blue_oceans.config.BlueOceansConfig;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansBlocks;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansParticleTypes;
import com.github.player_ix.ix_api.util.ParticleUtil;
import com.github.player_ix.ix_api.util.Vec9;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class RedPlumBlock extends BaseEntityBlock implements IPlumBlock {
    public RedPlumBlock(Properties pProperties) {
        super(pProperties);
    }

    public RedPlumBlock() {
        this(Properties.of().strength(1F, 40F).mapColor(DyeColor.RED)
                .instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.SCULK)
                .randomTicks());
    }

    @Nullable
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return null;
    }

    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @SuppressWarnings("deprecation")
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        this.spawnPlum(pLevel, pPos, pRandom);
        if (pRandom.nextInt(10) == 0) {
            ParticleUtil.serverAddParticle(pLevel, BlueOceansParticleTypes.RED_SPELL.get(), Vec9.of(pPos.above()));
        }
        if (this.getLevel() == 1 && pRandom.nextInt(25) == 0) {
            pLevel.setBlock(pPos, BlueOceansBlocks.RED_PLUM_CATALYST.get().defaultBlockState(), 0);
        }
    }

    protected void spawnPlum(ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pRandom.nextInt(20) == 0 && BlueOceansConfig.Common.SPAWN_NEO_PLUM.get()) {
            BlockPos pos = pPos.above();
            var plum = NeoPlum.create(pos, pLevel);
            if (plum != null) {
                NeoPlum.addParticleAroundPlum(plum);
            }
        }
    }
}
