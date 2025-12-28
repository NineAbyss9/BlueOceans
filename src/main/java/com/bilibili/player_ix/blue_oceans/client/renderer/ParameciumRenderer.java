
package com.bilibili.player_ix.blue_oceans.client.renderer;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.client.model.ParameciumModel;
import com.bilibili.player_ix.blue_oceans.common.entities.Paramecium;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ParameciumRenderer<T extends Paramecium>
extends MobRenderer<T, ParameciumModel<T>> {
    public ParameciumRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new ParameciumModel<>(pContext.bakeLayer(
                ParameciumModel.LAYER_LOCATION)), 0.5f);
    }

    protected void scale(T pLivingEntity, PoseStack pPoseStack, float pPartialTickTime) {
        float f = 2F;
        pPoseStack.scale(f, f, f);
    }

    public ResourceLocation getTextureLocation(T t) {
        return BlueOceans.location("textures/entities/paramecium.png");
    }
}
