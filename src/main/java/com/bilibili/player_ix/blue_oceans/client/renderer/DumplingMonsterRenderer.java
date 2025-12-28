
package com.bilibili.player_ix.blue_oceans.client.renderer;

import com.bilibili.player_ix.blue_oceans.client.model.DumplingMonsterModel;
import com.bilibili.player_ix.blue_oceans.common.entities.dumplings.DumplingMonster;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class DumplingMonsterRenderer<T extends DumplingMonster> extends MobRenderer<T, DumplingMonsterModel<T>> {
    private static final ResourceLocation JIAO_ZI = new ResourceLocation("blue_oceans:textures/entities/dumplings/dumpling_monster.png");

    public DumplingMonsterRenderer(EntityRendererProvider.Context context) {
        super(context, new DumplingMonsterModel<>(context.bakeLayer(DumplingMonsterModel.LAYER_LOCATION)), 0.5f);
    }

    public ResourceLocation getTextureLocation(T t) {
        return JIAO_ZI;
    }
}
