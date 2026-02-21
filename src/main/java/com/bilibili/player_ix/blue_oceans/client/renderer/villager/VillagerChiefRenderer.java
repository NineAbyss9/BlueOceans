
package com.bilibili.player_ix.blue_oceans.client.renderer.villager;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.common.entities.villagers.VillageChief;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class VillagerChiefRenderer<T extends VillageChief>
extends BaseVillagerRenderer<T> {
    public VillagerChiefRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    public ResourceLocation getTextureLocation(T pEntity) {
        return BlueOceans.entity("villagers/villager_chief");
    }
}
