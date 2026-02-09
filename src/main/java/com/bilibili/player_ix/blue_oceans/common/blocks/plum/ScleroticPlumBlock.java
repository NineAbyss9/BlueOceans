
package com.bilibili.player_ix.blue_oceans.common.blocks.plum;

import com.bilibili.player_ix.blue_oceans.common.blocks.RedPlumBlock;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.AbstractRedPlumMob;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.NeoFighter;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.NeoPlum;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.RedPlumMonster;
import com.bilibili.player_ix.blue_oceans.config.BoCommonConfig;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.phys.AABB;

public class ScleroticPlumBlock
extends RedPlumBlock {
    public ScleroticPlumBlock() {
        super(Properties.of().strength(3.0F, 10.0F)
                .mapColor(DyeColor.RED).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.SCULK)
                .randomTicks());
    }

    protected void spawnPlum(ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (BoCommonConfig.SPAWN_NEO_PLUM.get() && pRandom.nextFloat() < 0.05F
                && NaturalSpawner.isSpawnPositionOk(SpawnPlacements.Type.ON_GROUND,
                pLevel, pPos.above(), BlueOceansEntities.NEO_FIGHTER.get())
                && pLevel.getEntitiesOfClass(RedPlumMonster.class, new AABB(pPos).inflate(8)).size() < 8) {
            AbstractRedPlumMob plum = NeoFighter.create(pPos, pLevel);
            if (plum != null) {
                NeoPlum.addParticleAroundPlum(plum);
            }
        }
    }
}
