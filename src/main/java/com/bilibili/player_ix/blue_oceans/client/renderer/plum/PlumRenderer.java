
package com.bilibili.player_ix.blue_oceans.client.renderer.plum;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.client.model.plum.PlumHolderModel;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.PlumHolder;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class PlumRenderer {
    public static class PlumHolderRenderer<E extends PlumHolder>
    extends MobRenderer<E, PlumHolderModel<E>> {
        public PlumHolderRenderer(EntityRendererProvider.Context pContext) {
            super(pContext, new PlumHolderModel<>(pContext.bakeLayer(PlumHolderModel.LAYER_LOCATION)),
                    0.7f);
        }

        public ResourceLocation getTextureLocation(E pEntity) {
            return BlueOceans.redPlum("plum_factory");
        }
    }
}
