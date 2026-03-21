
package com.bilibili.player_ix.blue_oceans.client.model;

import com.github.NineAbyss9.ix_api.api.mobs.ICreeper;
import com.github.NineAbyss9.ix_api.util.Maths;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;

public class BoCreeperModel<E extends Mob & ICreeper>
extends HierarchicalModel<E>
implements HeadedModel
{
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart rightHindLeg;
    private final ModelPart leftHindLeg;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftFrontLeg;

    public BoCreeperModel(ModelPart pRoot)
    {
        this.root = pRoot;
        this.head = pRoot.getChild("head");
        this.leftHindLeg = pRoot.getChild("right_hind_leg");
        this.rightHindLeg = pRoot.getChild("left_hind_leg");
        this.leftFrontLeg = pRoot.getChild("right_front_leg");
        this.rightFrontLeg = pRoot.getChild("left_front_leg");
    }

    public ModelPart getHead()
    {
        return head;
    }

    public ModelPart root()
    {
        return root;
    }

    public void setupAnim(E pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw,
                          float pHeadPitch)
    {
        this.head.yRot = pNetHeadYaw * (Maths.PI / 180F);
        this.head.xRot = pHeadPitch * (Maths.PI / 180F);
        this.rightHindLeg.xRot = Mth.cos(pLimbSwing * 0.6662F) * 1.4F * pLimbSwingAmount;
        this.leftHindLeg.xRot = Mth.cos(pLimbSwing * 0.6662F + Maths.PI) * 1.4F * pLimbSwingAmount;
        this.rightFrontLeg.xRot = Mth.cos(pLimbSwing * 0.6662F + Maths.PI) * 1.4F * pLimbSwingAmount;
        this.leftFrontLeg.xRot = Mth.cos(pLimbSwing * 0.6662F) * 1.4F * pLimbSwingAmount;
    }
}
