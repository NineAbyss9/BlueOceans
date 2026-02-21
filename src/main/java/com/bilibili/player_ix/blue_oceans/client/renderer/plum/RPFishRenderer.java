
package com.bilibili.player_ix.blue_oceans.client.renderer.plum;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.RedPlumFish;
import net.minecraft.client.model.CodModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class RPFishRenderer<E extends RedPlumFish>
extends MobRenderer<E, CodModel<E>> {
    public RPFishRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new CodModel<>(pContext.bakeLayer(ModelLayers.COD)), 0.3F);
    }

    public ResourceLocation getTextureLocation(E pEntity) {
        return BlueOceans.redPlum("cod");
    }
}
