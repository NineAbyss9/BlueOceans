
package com.bilibili.player_ix.blue_oceans.client.renderer;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.client.model.VenomModel;
import com.bilibili.player_ix.blue_oceans.common.entities.projectile.Venom;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class VenomRenderer<T extends Venom> extends EntityRenderer<T> {
    private final VenomModel<T> model;
    public VenomRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
        this.model = new VenomModel<>(p_174008_.bakeLayer(VenomModel.LAYER_LOCATION));
    }

    public void render(T pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        pPoseStack.pushPose();
        float f6 = Mth.lerp(pPartialTick, pEntity.xRotO, pEntity.getXRot());
        this.model.setupAnim(pEntity, pEntityYaw, 0, 0, 0, f6);
        this.model.renderToBuffer(pPoseStack, pBuffer.getBuffer(this.model.renderType(this.getTextureLocation(pEntity))),
                pPackedLight, 0, 1.0F, 1.0F, 1.0F, 1.0F);
        pPoseStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);
    }

    public ResourceLocation getTextureLocation(T t) {
        return BlueOceans.entity("projectile/venom");
    }
}
