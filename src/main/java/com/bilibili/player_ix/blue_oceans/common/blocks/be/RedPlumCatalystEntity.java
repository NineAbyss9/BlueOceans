
package com.bilibili.player_ix.blue_oceans.common.blocks.be;

import com.bilibili.player_ix.blue_oceans.common.blocks.RedPlumCatalyst;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.phys.Vec3;

public class RedPlumCatalystEntity
extends BlockEntity
implements GameEventListener.Holder<RedPlumCatalystEntity.Listener> {
    private final Listener listener;
    public RedPlumCatalystEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlueOceansBlockEntities.RED_PLUM_CATALYST.get(), pPos, pBlockState);
        this.listener = new Listener(new BlockPositionSource(pPos));
    }

    public Listener getListener() {
        return listener;
    }

    public static class Listener implements GameEventListener {
        private final PositionSource source;
        public Listener(PositionSource pSource) {
            this.source = pSource;
        }

        public PositionSource getListenerSource() {
            return source;
        }

        public int getListenerRadius() {
            return 8;
        }

        public DeliveryMode getDeliveryMode() {
            return DeliveryMode.BY_DISTANCE;
        }

        public boolean handleGameEvent(ServerLevel pLevel, GameEvent pGameEvent, GameEvent.Context pContext,
                                       Vec3 pPos) {
            if (pGameEvent.equals(GameEvent.ENTITY_DIE)) {
                Entity entity = pContext.sourceEntity();
                if (entity instanceof LivingEntity livingEntity) {
                    if (!livingEntity.wasExperienceConsumed()) {
                        int experience = livingEntity.getExperienceReward();
                        if (livingEntity.shouldDropExperience() && experience > 0) {
                            RedPlumCatalyst.spreadPlum(pLevel, BlockPos.containing(pPos).below());
                        }
                        livingEntity.skipDropExperience();
                        RedPlumCatalyst.spreadPlum(pLevel, BlockPos.containing(pPos));
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
