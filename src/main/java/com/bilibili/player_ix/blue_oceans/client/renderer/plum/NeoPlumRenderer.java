
package com.bilibili.player_ix.blue_oceans.client.renderer.plum;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.client.model.plum.NeoPlumModel;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.NeoPlum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class NeoPlumRenderer<N extends NeoPlum>
extends MobRenderer<N, NeoPlumModel<N>> {
    public NeoPlumRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new NeoPlumModel<>(pContext.bakeLayer(NeoPlumModel.NEO_PLUM)), 0.5F);
    }

    protected boolean isShaking(N pEntity) {
        return pEntity.isConverting();
    }

    public ResourceLocation getTextureLocation(N entity) {
        return BlueOceans.entityWithCheck("red_plum_mobs/neo_plum.png");
    }
}
