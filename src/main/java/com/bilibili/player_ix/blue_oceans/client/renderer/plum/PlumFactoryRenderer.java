
package com.bilibili.player_ix.blue_oceans.client.renderer.plum;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.client.model.plum.PlumFactoryModel;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.PlumFactory;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class PlumFactoryRenderer<E extends PlumFactory>
extends MobRenderer<E, PlumFactoryModel<E>> {
    public PlumFactoryRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new PlumFactoryModel<>(
                pContext.bakeLayer(PlumFactoryModel.LAYER_LOCATION)), 1.25F);
    }

    public ResourceLocation getTextureLocation(E pEntity) {
        return BlueOceans.redPlum("plum_factory");
    }
}
