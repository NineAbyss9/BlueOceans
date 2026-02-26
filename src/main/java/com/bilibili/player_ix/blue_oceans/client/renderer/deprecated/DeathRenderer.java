
package com.bilibili.player_ix.blue_oceans.client.renderer.deprecated;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.client.model.deprecated.DeathModel;
import com.bilibili.player_ix.blue_oceans.common.entities.undeads.Death;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class DeathRenderer<T extends Death> extends MobRenderer<T, DeathModel<T>> {
    private static final ResourceLocation DEATH = BlueOceans.entity("undeads/death");
    public DeathRenderer(EntityRendererProvider.Context context) {
        super(context, new DeathModel<>(context.bakeLayer(DeathModel.DEATH)), 0.5f);
    }

    public void render(T pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        if (pEntity.shouldRenderShadow()) {
            for (int i = 0;i < pEntity.getClientShadowPos().length;i++) {
                this.renderShadow(pEntity, pPartialTicks, pPoseStack, pBuffer, pPackedLight, pEntity.getClientShadowPos()[i]);
            }
        }
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }

    private void renderShadow(T pEntity, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, Vec3 pos) {
        var renderType = this.getRenderType(pEntity, true, true, false);
        var f = Mth.rotLerp(pPartialTicks, pEntity.yBodyRotO, pEntity.yBodyRot);
        var f7 = this.getBob(pEntity, pPartialTicks);
        pPoseStack.pushPose();
        pPoseStack.translate(pos.x, pos.y + 1, pos.z);
        this.setupRotations(pEntity, pPoseStack, f7, f, pPartialTicks);
        if (renderType != null) {
            this.getModel().renderToBuffer(pPoseStack,
                    pBuffer.getBuffer(renderType), pPackedLight,
                    getOverlayCoords(pEntity, pPartialTicks), 0.0F, 0.0F, 0.0F, 1.0F);
        }
        this.getModel().setupAnim(pEntity, 0, 0, pPartialTicks, 0, 0);
        pPoseStack.popPose();
    }

    public ResourceLocation getTextureLocation(T t) {
        return DEATH;
    }
}
