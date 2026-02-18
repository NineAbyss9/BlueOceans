
package com.github.player_ix.ix_api.util;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;
import org.NineAbyss9.annotation.PFMAreNonnullByDefault;
import org.NineAbyss9.annotation.doc.Message;

@PFMAreNonnullByDefault
public final class Vec9 extends Vec3 {
    public Vec9(double pX, double pY, double pZ) {
        super(pX, pY, pZ);
    }

    public Vec9(Vector3f vector3f) {
        super(vector3f);
    }

    public Vec9 add(double pX, double pZ) {
        return new Vec9(this.x + pX, this.y, this.z + pZ);
    }

    public MutableVec3 mutable() {
        return new MutableVec3(x, y, z);
    }

    public static CompoundTag createVec3Tag(Vec3 vec3, String main) {
        CompoundTag tag = new CompoundTag();
        tag.putDouble(main + "X", vec3.x);
        tag.putDouble(main + "Y", vec3.y);
        tag.putDouble(main + "Z", vec3.z);
        return tag;
    }

    public static void createVec9Tag(CompoundTag pTag, Vec3 pVector, String main) {
        pTag.put(main, createVec3Tag(pVector, main));
    }

    public static Vec9 readVec3Tag(CompoundTag pTag, String string) {
        CompoundTag tag = pTag.getCompound(string);
        return new Vec9(tag.getDouble(string + "X"), tag.getDouble(string + "Y"),
                tag.getDouble(string + "Z"));
    }

    public static Vec3 moveToVec(Entity target, Entity entity, double speed) {
        double xDPower = target.getX() - entity.getX();
        double yDPower = target.getY() - entity.getY();
        double zDPower = target.getZ() - entity.getZ();
        double d = Math.sqrt(xDPower * xDPower + yDPower * yDPower + zDPower * zDPower);
        double xPower = -(xDPower / d * 5.0 * speed);
        double yPower = -(yDPower / d * 5.0 * speed);
        double zPower = -(zDPower / d * 5.0 * speed);
        return new Vec9(xPower, yPower, zPower);
    }

    public static Vec9 of() {
        return new Vec9(0.0, 0.0, 0.0);
    }

    public static Vec9 of(double x, double y, double z) {
        return new Vec9(x, y, z);
    }

    @Message("Already added.")
    public static Vec9 of(BlockPos pos) {
        return new Vec9(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
    }

    public static Vec9 of(MutableVec3 vec3) {
        return new Vec9(vec3.x(), vec3.y(), vec3.z());
    }

    public static Vec9 of(Vec3 pVector) {
        return new Vec9(pVector.x, pVector.y, pVector.z);
    }
}
