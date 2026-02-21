
package com.bilibili.player_ix.blue_oceans.client.renderer.projectile;

import com.bilibili.player_ix.blue_oceans.common.entities.projectile.plum.EchoPotion;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class EchoPotionRenderer<E extends EchoPotion>
extends ThrownItemRenderer<E> {
    public EchoPotionRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }
}
