
package com.bilibili.player_ix.blue_oceans.client.renderer;

import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.RedPlumWorm;
import net.minecraft.client.model.SilverfishModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class RedPlumWormRenderer<T extends RedPlumWorm> extends MobRenderer<T, SilverfishModel<T>> {
    private final ResourceLocation WORM = new ResourceLocation("blue_oceans:textures/entities/red_plum_mobs/red_plum_worm.png");

    public RedPlumWormRenderer(EntityRendererProvider.Context p_174304_) {
        super(p_174304_, new SilverfishModel<>(p_174304_.bakeLayer(ModelLayers.SILVERFISH)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(T t) {
        return WORM;
    }
}
