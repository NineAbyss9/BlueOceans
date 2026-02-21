
package com.bilibili.player_ix.blue_oceans.client.renderer.plum;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.client.model.BoCreeperModel;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.RedPlumCreeper;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class RPCreeperRenderer<E extends RedPlumCreeper>
extends MobRenderer<E, BoCreeperModel<E>> {
    public RPCreeperRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new BoCreeperModel<>(pContext.bakeLayer(ModelLayers.CREEPER)), 0.5F);
    }

    protected void scale(E pLivingEntity, PoseStack pPoseStack, float pPartialTickTime) {
        float f = pLivingEntity.getSwelling(pPartialTickTime);
        float f1 = 1.0F + Mth.sin(f * 100.0F) * f * 0.01F;
        f = Mth.clamp(f, 0.0F, 1.0F);
        f *= f;
        f *= f;
        float f2 = (1.0F + f * 0.4F) * f1;
        float f3 = (1.0F + f * 0.1F) / f1;
        pPoseStack.scale(f2, f3, f2);
    }

    protected float getWhiteOverlayProgress(E pLivingEntity, float pPartialTicks) {
        float f = pLivingEntity.getSwelling(pPartialTicks);
        return (int)(f * 10.0F) % 2 == 0 ? 0.0F : Mth.clamp(f, 0.5F, 1.0F);
    }

    public ResourceLocation getTextureLocation(E pEntity) {
        return BlueOceans.redPlum("creeper");
    }
}
