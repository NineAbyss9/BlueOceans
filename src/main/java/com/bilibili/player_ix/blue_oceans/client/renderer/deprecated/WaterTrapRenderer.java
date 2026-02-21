
package com.bilibili.player_ix.blue_oceans.client.renderer.deprecated;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.common.entities.projectile.WaterTrap;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class WaterTrapRenderer
extends EntityRenderer<WaterTrap> {
    private static final ResourceLocation WATER_TRAP = BlueOceans.location("textures/entities/entity_null.png");

    public WaterTrapRenderer(EntityRendererProvider.Context aSuper) {
        super(aSuper);
    }
    
    public ResourceLocation getTextureLocation(WaterTrap entity) {
		return WATER_TRAP;
	}   
}
