
package com.bilibili.player_ix.blue_oceans.client.renderer.block;

import com.bilibili.player_ix.blue_oceans.common.blocks.be.CorpseEntity;
//import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.Entity;
import org.NineAbyss9.util.IXUtil;
import org.joml.Quaternionf;

public class CorpseRenderer implements BlockEntityRenderer<CorpseEntity>
{
    private final BlockEntityRendererProvider.Context context;
    public CorpseRenderer(BlockEntityRendererProvider.Context pContext)
    {
        this.context = pContext;
    }

    public void render(CorpseEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer,
                       int pPackedLight, int pPackedOverlay)
    {
        //if (pBlockEntity.getEntity() == null) return;
        //RenderSystem.setShaderColor(0.5f, 0.5f, 0.5f, 1.0f);
        pPoseStack.pushPose();
        EntityRenderer<? extends Entity> renderer = this.context.getEntityRenderer().getRenderer(pBlockEntity.getEntity());
        if (renderer instanceof LivingEntityRenderer<?, ?> livingEntityRenderer && livingEntityRenderer.getModel()
                instanceof HeadedModel headedModel) {
            headedModel.getHead().render(pPoseStack, pBuffer.getBuffer(RenderType.entityCutoutNoCull(renderer
                    .getTextureLocation(IXUtil.c.convert(pBlockEntity.getEntity())))).color(255, 255, 255, 255),
                    pPackedLight, pPackedOverlay, 0.5f, 0.5f, 0.5f, 1f);
            pPoseStack.translate(0.5f, 0.5f, 0.5f);
            pPoseStack.mulPose(new Quaternionf(0f, 90f, 0f, 0f));
        }
        pPoseStack.popPose();
    }
}
