
package com.bilibili.player_ix.blue_oceans.client.renderer.projectile;

import com.bilibili.player_ix.blue_oceans.common.entities.projectile.SeedEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class SeedEntityRenderer<E extends SeedEntity>
extends EntityRenderer<E> {
    protected final ItemRenderer itemRenderer;
    public SeedEntityRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.itemRenderer = pContext.getItemRenderer();
    }

    public void render(E pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack,
                       MultiBufferSource pBuffer, int pPackedLight) {
        ItemStack item = pEntity.getItem();
        BakedModel bakedmodel = this.itemRenderer.getModel(item, pEntity.level(), null, pEntity.getId());
        pPoseStack.mulPose(Axis.XP.rotationDegrees(pEntity.getXRot()));
        this.itemRenderer.render(item, ItemDisplayContext.GROUND,
                false, pPoseStack, pBuffer, pPackedLight, OverlayTexture.NO_OVERLAY, bakedmodel);
        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);
    }

    public ResourceLocation getTextureLocation(E pEntity) {
        return InventoryMenu.BLOCK_ATLAS;
    }
}
