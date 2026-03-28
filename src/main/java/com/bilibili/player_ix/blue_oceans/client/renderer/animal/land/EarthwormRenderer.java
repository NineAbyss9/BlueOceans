
package com.bilibili.player_ix.blue_oceans.client.renderer.animal.land;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.client.model.animal.land.EarthwormModel;
import com.bilibili.player_ix.blue_oceans.common.entities.animal.farm.Earthworm;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class EarthwormRenderer<E extends Earthworm>
extends MobRenderer<E, EarthwormModel<E>> {
    public EarthwormRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new EarthwormModel<>(pContext.bakeLayer(EarthwormModel.LAYER_LOCATION)),
                0.3F);
    }

    private static ResourceLocation LOC = BlueOceans.land("earthworm");

    public ResourceLocation getTextureLocation(E pEntity) {
        return LOC;
    }
}
