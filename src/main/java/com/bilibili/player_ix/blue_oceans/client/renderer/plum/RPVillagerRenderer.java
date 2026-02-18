
package com.bilibili.player_ix.blue_oceans.client.renderer.plum;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.client.model.RedPlumIllagerModel;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.RedPlumVillager;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class RPVillagerRenderer<E extends RedPlumVillager>
extends MobRenderer<E, RedPlumIllagerModel<E>> {
    public RPVillagerRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new RedPlumIllagerModel<>(pContext.bakeLayer(RedPlumIllagerModel.LAYER_LOCATION)),
                0.5F);
        this.addLayer(new ItemInHandLayer<>(this, pContext.getItemInHandRenderer()));
    }

    public ResourceLocation getTextureLocation(E pEntity) {
        return BlueOceans.redPlum("villager");
    }
}
