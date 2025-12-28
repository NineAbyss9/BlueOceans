
package com.bilibili.player_ix.blue_oceans.client.renderer;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.client.model.AbstractSkeletonModel;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.RedPlumSkeleton;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class RedPlumSkeletonRenderer<T extends RedPlumSkeleton>
extends AbstractSkeletonRenderer<T> {
    public RedPlumSkeletonRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new AbstractSkeletonModel<>(
                pContext.bakeLayer(AbstractSkeletonModel.LOCATION)), 0.5f);
        this.addLayer(new ItemInHandLayer<>(this, pContext.getItemInHandRenderer()));
    }

    public ResourceLocation getTextureLocation(T t) {
        return BlueOceans.location("textures/entities/red_plum_mobs/skeleton.png");
    }
}
