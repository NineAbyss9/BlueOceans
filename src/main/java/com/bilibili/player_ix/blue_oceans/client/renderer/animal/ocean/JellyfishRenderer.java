
package com.bilibili.player_ix.blue_oceans.client.renderer.animal.ocean;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.client.model.JellyfishModel;
import com.bilibili.player_ix.blue_oceans.common.entities.animal.ocean.Jellyfish;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class JellyfishRenderer<E extends Jellyfish>
extends MobRenderer<E, JellyfishModel<E>> {
    public JellyfishRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new JellyfishModel<>(pContext.bakeLayer(JellyfishModel.LAYER_LOCATION)),
                0.5F);
    }

    protected void setupRotations(E pEntityLiving, PoseStack pPoseStack, float pAgeInTicks, float pRotationYaw,
                                  float pPartialTicks) {
        float f = Mth.lerp(pPartialTicks, pEntityLiving.xBodyRotO, pEntityLiving.xBodyRot);
        float f1 = Mth.lerp(pPartialTicks, pEntityLiving.zBodyRotO, pEntityLiving.zBodyRot);
        //pPoseStack.translate(0.0F, 0.5F, 0.0F);
        pPoseStack.mulPose(Axis.YP.rotationDegrees(180.0F - pRotationYaw));
        pPoseStack.mulPose(Axis.XP.rotationDegrees(f));
        pPoseStack.mulPose(Axis.YP.rotationDegrees(f1));
        //pPoseStack.translate(0.0F, -1.2F, 0.0F);
    }

    protected float getBob(E pLivingBase, float pPartialTicks) {
        return Mth.lerp(pPartialTicks, pLivingBase.oldTentacleAngle, pLivingBase.tentacleAngle);
    }

    public static ResourceLocation jellyfish(String path) {
        return BlueOceans.ocean("jellyfish/" + path);
    }

    public ResourceLocation getTextureLocation(E pEntity) {
        return jellyfish("cubozoa");
    }
}
