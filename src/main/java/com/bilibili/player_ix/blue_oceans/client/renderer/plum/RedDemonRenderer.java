
package com.bilibili.player_ix.blue_oceans.client.renderer.plum;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.client.model.plum.RedPlumDemonModel;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.RedDemon;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class RedDemonRenderer<E extends RedDemon>
extends MobRenderer<E, RedPlumDemonModel<E>> {
    public RedDemonRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new RedPlumDemonModel<>(pContext.bakeLayer(RedPlumDemonModel.LAYER_LOCATION)), 0.7F);
    }

    protected void scale(E pLivingEntity, PoseStack pPoseStack, float pPartialTickTime) {
        pPoseStack.scale(1.5F, 1.5F, 1.5F);
    }

    public ResourceLocation getTextureLocation(E pEntity) {
        return BlueOceans.redPlum("demon");
    }
}
