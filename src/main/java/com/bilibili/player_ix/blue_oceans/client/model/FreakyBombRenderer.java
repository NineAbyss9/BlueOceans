
package com.bilibili.player_ix.blue_oceans.client.model;

import com.bilibili.player_ix.blue_oceans.common.entities.illagers.FreakyBomb;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class FreakyBombRenderer<T extends FreakyBomb> extends EntityRenderer<T> {

    public FreakyBombRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull T t) {
        return new ResourceLocation("blue_oceans:textures/entities/entity_null.png");
    }
}
