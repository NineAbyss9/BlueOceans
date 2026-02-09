
package com.bilibili.player_ix.blue_oceans.client.renderer;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.client.model.BoCreeperModel;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.RedPlumCreeper;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class RPCreeperRenderer<E extends RedPlumCreeper>
extends MobRenderer<E, BoCreeperModel<E>> {
    public RPCreeperRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new BoCreeperModel<>(pContext.bakeLayer(ModelLayers.CREEPER)), 0.5F);
    }

    public ResourceLocation getTextureLocation(E pEntity) {
        return BlueOceans.redPlum("creeper");
    }
}
