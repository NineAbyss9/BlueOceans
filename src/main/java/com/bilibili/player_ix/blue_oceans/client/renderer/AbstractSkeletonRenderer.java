
package com.bilibili.player_ix.blue_oceans.client.renderer;

import com.bilibili.player_ix.blue_oceans.client.model.AbstractSkeletonModel;
import com.github.NineAbyss9.ix_api.api.mobs.ApiPoseMob;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.world.entity.Mob;

public abstract class AbstractSkeletonRenderer<T extends Mob & ApiPoseMob>
extends MobRenderer<T, AbstractSkeletonModel<T>> {
    public AbstractSkeletonRenderer(EntityRendererProvider.Context pContext, AbstractSkeletonModel<T> pModel,
                                    float pShadowRadius) {
        super(pContext, pModel, pShadowRadius);
    }
}
