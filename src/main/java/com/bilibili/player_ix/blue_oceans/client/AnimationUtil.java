
package com.bilibili.player_ix.blue_oceans.client;

import com.bilibili.player_ix.blue_oceans.util.MathUtils;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;

public final class AnimationUtil extends AnimationUtils {
    public AnimationUtil() {
    }

    public static void hasItemAni(ModelPart part) {
        part.xRot = part.xRot * 0.5F - MathUtils.PI;
        part.yRot = 0;
    }

    public static void bowAndArrow(ModelPart leftArm, ModelPart rightArm, ModelPart head) {
        rightArm.yRot = -0.1F + head.yRot;
        leftArm.yRot = 0.1F + head.yRot + 0.4F;
        rightArm.xRot = -1.5707964F + head.xRot;
        leftArm.xRot = -1.5707964F + head.xRot;
    }

    public static void brush(ModelPart rightArm) {
        rightArm.xRot = rightArm.xRot * 0.5F - 0.62831855F;
        rightArm.yRot = 0.0F;
    }

    public static void castingSpell(ModelPart leftArm, ModelPart rightArm, float tick) {
        rightArm.xRot = Mth.cos(tick * 0.6662F) * 0.25F;
        leftArm.xRot = Mth.cos(tick * 0.6662F) * 0.25F;
        rightArm.zRot = 2.3561945F;
        leftArm.zRot = -2.3561945F;
        rightArm.yRot = MathUtils.PI;
        leftArm.yRot = MathUtils.PI;
    }

    public static void cultistCastingSpell(ModelPart leftArm, ModelPart rightArm, ModelPart head) {
        rightArm.xRot = Mth.cos(0.6662F) * 0.25F;
        leftArm.xRot = Mth.cos(0.6662F) * 0.25F;
        rightArm.zRot = 2.3561945F;
        leftArm.zRot = -2.3561945F;
        rightArm.yRot = MathUtils.PI;
        leftArm.yRot = MathUtils.PI;
        head.xRot = MathUtils.piDiving(5);
    }
}
