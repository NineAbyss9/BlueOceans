
package com.bilibili.player_ix.blue_oceans.client.renderer;

import com.bilibili.player_ix.blue_oceans.client.model.HuntingVillagerModel;
import com.bilibili.player_ix.blue_oceans.common.entities.villagers.VillagerBiologist;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CrossedArmsItemLayer;
import net.minecraft.resources.ResourceLocation;

public class VillagerBiologistRenderer<V extends VillagerBiologist>
extends MobRenderer<V, HuntingVillagerModel<V>> {
    public VillagerBiologistRenderer(EntityRendererProvider.Context p_174304_) {
        super(p_174304_, new HuntingVillagerModel<>(p_174304_.bakeLayer(HuntingVillagerModel.LOCATION)), 0.5f);
        this.addLayer(new CrossedArmsItemLayer<>(this, p_174304_.getItemInHandRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(V v) {
        return new ResourceLocation("blue_oceans:textures/entities/villagers/biologist.png");
    }
}
