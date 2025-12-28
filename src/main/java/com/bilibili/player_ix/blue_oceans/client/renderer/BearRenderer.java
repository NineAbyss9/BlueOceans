
package com.bilibili.player_ix.blue_oceans.client.renderer;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.client.model.BearModel;
import com.bilibili.player_ix.blue_oceans.common.entities.animal.Bear;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class BearRenderer<T extends Bear>
extends MobRenderer<T, BearModel<T>> {
    public BearRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new BearModel<>(pContext.bakeLayer(ModelLayers.POLAR_BEAR)), 0.9F);
    }

    public ResourceLocation getTextureLocation(T pEntity) {
        return BlueOceans.animal("bear/bear");
    }

    protected void scale(T pLivingEntity, PoseStack pPoseStack, float pPartialTickTime) {
        pPoseStack.scale(1.25F, 1.25F, 1.25F);
    }
}
