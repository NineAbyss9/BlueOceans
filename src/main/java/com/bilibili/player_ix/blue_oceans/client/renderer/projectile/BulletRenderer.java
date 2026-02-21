
package com.bilibili.player_ix.blue_oceans.client.renderer.projectile;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.client.model.BulletModel;
import com.bilibili.player_ix.blue_oceans.common.entities.projectile.BulletProjectile;
import com.mojang.blaze3d.vertex.PoseStack;
//import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class BulletRenderer<T extends BulletProjectile>
extends EntityRenderer<T> {
    private final BulletModel<T> bulletModel;
    public BulletRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.bulletModel = new BulletModel<>(pContext.bakeLayer(BulletModel.LAYER_LOCATION));
    }

    public void render(T pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack,
                       MultiBufferSource pBuffer, int pPackedLight) {
        pPoseStack.pushPose();
        float f = Mth.lerp(pPartialTick, pEntity.xRotO, pEntity.getXRot());
        float f1 = Mth.rotLerp(pPartialTick, pEntity.yRotO, pEntity.getYRot());
        pPoseStack.translate(0.0F, -1.F, 0.0F);
        //pPoseStack.mulPose(Axis.YP.rotationDegrees(180.0F - pPartialTick));
        this.bulletModel.setupAnim(pEntity, pEntityYaw, 0, 0, f, f1);
        this.bulletModel.renderToBuffer(pPoseStack, pBuffer.getBuffer(this.bulletModel.renderType(this
                        .getTextureLocation(pEntity))), pPackedLight,
                0, 1.0F, 1.0F, 1.0F, 1.0F);
        pPoseStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);
    }

    public ResourceLocation getTextureLocation(T pEntity) {
        return BlueOceans.entity("projectile/bullet");
    }
}
