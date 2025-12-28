
package com.github.player_ix.ix_api.util;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;
import org.nine_abyss.annotation.PFMAreNonnullByDefault;

@PFMAreNonnullByDefault
public final class Vec9 extends Vec3 {
    public Vec9(double pX, double pY, double pZ) {
        super(pX, pY, pZ);
    }

    public Vec9(Vector3f vector3f) {
        super(vector3f);
    }

    public static CompoundTag createVec3Tag(Vec3 vec3, String main) {
        CompoundTag tag = new CompoundTag();
        tag.putDouble(main + "X", vec3.x());
        tag.putDouble(main + "Y", vec3.y());
        tag.putDouble(main + "Z", vec3.z());
        return tag;
    }

    public static Vec3 readVec3Tag(CompoundTag tag, String string) {
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

    public static Vec9 of(BlockPos pos) {
        return new Vec9(pos.getX(), pos.getY(), pos.getZ());
    }

    public static Vec9 of(MutableVec3 vec3) {
        return new Vec9(vec3.x(), vec3.y(), vec3.z());
    }
}
