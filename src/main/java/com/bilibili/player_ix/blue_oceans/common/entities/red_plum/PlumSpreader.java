
package com.bilibili.player_ix.blue_oceans.common.entities.red_plum;

import com.bilibili.player_ix.blue_oceans.init.BlueOceansBlocks;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansEntities;
import com.bilibili.player_ix.blue_oceans.init.BoTags;
import com.bilibili.player_ix.blue_oceans.util.RedPlumUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class PlumSpreader
extends RedPlumMonster
implements IPlumSpreader {
    public PlumSpreader(EntityType<? extends PlumSpreader> type, Level level) {
        super(type, level);
    }

    public void aiStep() {
        super.aiStep();
        if (this.getRandomUtil().nextInt(10) == 0
            && checkConditions(this)) {
            spreadPlum(this);
        }
    }

    protected int nextConvertUpNeeds() {
        return 5;
    }

    public void setInfectLevelPlus() {
        super.setInfectLevelPlus();
        if (this.shouldLevelUp()) {
            var entity = this.getNextLevelConvert().create(this.level());
            if (entity != null) {
                entity.moveTo(this.position());
                this.level().addFreshEntity(entity);
                this.discard();
            }
        }
    }

    protected EntityType<? extends AbstractRedPlumMob> getNextLevelConvert() {
        return BlueOceansEntities.PLUM_FACTORY.get();
    }

    public static boolean checkConditions(Entity pEntity) {
        return !pEntity.level().getBlockState(pEntity.blockPosition().below()).is(BoTags.RED_PLUM_BLOCKS)
                && RedPlumUtil.canSpreadPlum(pEntity.level()) && pEntity.fallDistance < 1F;
    }

    public static void spreadPlum(Entity pEntity) {
        pEntity.level().setBlock(pEntity.blockPosition().below(),
                BlueOceansBlocks.RED_PLUM_BLOCK.get().defaultBlockState(), 0);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createPathAttributes().add(Attributes.ATTACK_DAMAGE, 1)
                .add(Attributes.MAX_HEALTH, 30).add(Attributes.FOLLOW_RANGE, 64)
                .add(Attributes.MOVEMENT_SPEED, 0.3);
    }
}
