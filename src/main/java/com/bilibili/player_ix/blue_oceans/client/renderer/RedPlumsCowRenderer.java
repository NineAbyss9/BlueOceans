
package com.bilibili.player_ix.blue_oceans.client.renderer;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.RedPlumCow;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class RedPlumsCowRenderer extends MobRenderer<RedPlumCow, CowModel<RedPlumCow>> {
    public RedPlumsCowRenderer(EntityRendererProvider.Context $$0) {
        super($$0, new CowModel<>($$0.bakeLayer(ModelLayers.MOOSHROOM)), 0.7f);
    }

    public ResourceLocation getTextureLocation(RedPlumCow entity) {
        return BlueOceans.redPlum("cow");
    }
}
