
package com.bilibili.player_ix.blue_oceans.client.renderer.plum;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.client.model.plum.PlumSpreaderModel;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.PlumSpreader;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class PlumSpreaderRenderer<E extends PlumSpreader>
extends MobRenderer<E, PlumSpreaderModel<E>> {
    public PlumSpreaderRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new PlumSpreaderModel<>(pContext.bakeLayer(PlumSpreaderModel.LAYER_LOCATION)),
                0.5F);
    }

    private static ResourceLocation LOC = BlueOceans.redPlum("plum_spreader");

    public ResourceLocation getTextureLocation(E pEntity) {
        return LOC;
    }
}
