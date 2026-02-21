
package com.github.NineAbyss9.ix_api.util;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;
import org.NineAbyss9.annotation.doc.Message;
import org.NineAbyss9.annotation.doc.Since;

import java.util.Random;

@Since("1.0.0")
public class ApiRandom {
    private static final Random staticRandom = new Random();
    private final Random random;
    public ApiRandom(long seed) {
        this.random = new Random(seed);
    }

    public ApiRandom() {this(System.currentTimeMillis() * Util.getNanos());}

    public static int nextInt(@Message("Value should greater than 0") int i) {
        return staticRandom.nextInt(i);
    }

    public Vector3f randomVec3f() {
        return new Vector3f(this.random.nextFloat(), this.random.nextFloat(), this.random.nextFloat());
    }

    public Vec3 randomPosition() {
        return new Vec3(this.random.nextDouble(), this.random.nextDouble(), this.random.nextDouble());
    }

    public BlockPos randomPos() {
        return BlockPos.containing(this.random.nextDouble(), this.random.nextDouble(), this.random.nextDouble());
    }
}
