
package com.bilibili.player_ix.blue_oceans.client.renderer;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.common.entities.animal.freshwater.Duck;
import net.minecraft.client.model.ChickenModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class DuckRenderer<E extends Duck>
extends MobRenderer<E, ChickenModel<E>> {
    public DuckRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new ChickenModel<>(pContext.bakeLayer(ModelLayers.CHICKEN)), 0.3F);
    }

    protected float getBob(E pLivingBase, float pPartialTick) {
        float f = Mth.lerp(pPartialTick, pLivingBase.oFlap, pLivingBase.flap);
        float f1 = Mth.lerp(pPartialTick, pLivingBase.oFlapSpeed, pLivingBase.flapSpeed);
        return (Mth.sin(f) + 1.0F) * f1;
    }

    public ResourceLocation getTextureLocation(E pEntity) {
        return BlueOceans.animal("duck");
    }
}
