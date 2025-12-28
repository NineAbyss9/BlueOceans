
package com.bilibili.player_ix.blue_oceans.client.model;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

public class VenomModel<T extends Entity> extends HierarchicalModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(BlueOceans.location("venom"), "main");
    private final ModelPart root;

    public VenomModel(ModelPart part) {
        this.root = part;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("root", CubeListBuilder.create().texOffs(-16, -8).addBox(-8.0F, 0.0F, -8.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(meshdefinition, 16, 16);
    }

    public void setupAnim(T t, float v, float v1, float v2, float v3, float v4) {
        this.root.xRot = t.getXRot();
    }

    public ModelPart root() {
        return this.root;
    }
}
