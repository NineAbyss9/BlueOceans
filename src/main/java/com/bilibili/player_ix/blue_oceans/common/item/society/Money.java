
package com.bilibili.player_ix.blue_oceans.common.item.society;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class Money
extends Item {
    public static final String FACE_VALUE = "FaceValue";
    public Money(Properties pP) {
        super(pP);
    }

    public Money() {
        super(new Properties());
    }

    public static ItemStack withFaceValue(float pValue) {
        //TODO
        ItemStack stack = new ItemStack(Item.byId(0));
        setFaceValue(stack, pValue);
        return stack;
    }

    public static float getFaceValue(CompoundTag pTag) {
        return pTag.getFloat(FACE_VALUE);
    }

    public static void setFaceValue(CompoundTag pTag, float pValue) {
        pTag.putFloat(FACE_VALUE, pValue);
    }

    public static void setFaceValue(ItemStack pStack, float pValue) {
        setFaceValue(pStack.getOrCreateTag(), pValue);
    }
}
