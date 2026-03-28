
package com.bilibili.player_ix.blue_oceans.client.renderer.villager;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.client.model.villager.HuntingVillagerModel;
import com.bilibili.player_ix.blue_oceans.common.entities.villagers.Researcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class ResearcherRenderer<E extends Researcher>
extends AbstractHuntingVillagerRenderer<E> {
    public ResearcherRenderer(EntityRendererProvider.Context context) {
        super(context, new HuntingVillagerModel<>(context.bakeLayer(HuntingVillagerModel.LOCATION)), 0.5F);
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
    }

    private static ResourceLocation LOC = BlueOceans.villager("researcher");

    public ResourceLocation getTextureLocation(E pEntity) {
        return LOC;
    }
}
