
package com.bilibili.player_ix.blue_oceans.client.renderer.plum;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.client.model.NeoFighterModel;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.NeoFighter;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class NeoFighterRenderer<E extends NeoFighter>
extends MobRenderer<E, NeoFighterModel<E>> {
    public NeoFighterRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new NeoFighterModel<>(pContext.bakeLayer(NeoFighterModel.LAYER_LOCATION)), 0.5F);
    }

    public ResourceLocation getTextureLocation(E pEntity) {
        return BlueOceans.redPlum("neo_fighter");
    }
}
