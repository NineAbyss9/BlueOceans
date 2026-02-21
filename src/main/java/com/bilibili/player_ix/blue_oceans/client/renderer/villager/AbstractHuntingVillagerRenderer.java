
package com.bilibili.player_ix.blue_oceans.client.renderer.villager;

import com.bilibili.player_ix.blue_oceans.client.model.HuntingVillagerModel;
import com.bilibili.player_ix.blue_oceans.common.entities.villagers.AbstractHuntingVillager;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;

public abstract class AbstractHuntingVillagerRenderer<T extends AbstractHuntingVillager>
extends MobRenderer<T, HuntingVillagerModel<T>> {
    public AbstractHuntingVillagerRenderer(EntityRendererProvider.Context context, HuntingVillagerModel<T> pModel, float v) {
        super(context, pModel, v);
        this.addLayer(new CustomHeadLayer<>(this, context.getModelSet(), context.getItemInHandRenderer()));
    }

    protected void scale(T pLivingEntity, PoseStack pPoseStack, float pPartialTickTime) {
        float f = 0.975f;
        if (pLivingEntity.isBaby()) {
            f *= 0.5f;
            this.shadowRadius = 0.25f;
        } else {
            this.shadowRadius = 0.5f;
        }
        pPoseStack.scale(f, f, f);
    }
}
