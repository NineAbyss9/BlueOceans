
package com.github.NineAbyss9.ix_api.api.mobs;

import com.github.NineAbyss9.ix_api.api.APISpells;
import com.github.NineAbyss9.ix_api.util.Vec9;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.world.phys.Vec3;

public class ApiEntityDataSerializers {
    //public static final EntityDataSerializer<UUID> UUID_SERIALIZER;
    public static final EntityDataSerializer<APISpells.APISpell> API_SPELL;
    public static final EntityDataSerializer<Double> DOUBLE;
    public static final EntityDataSerializer<Vec3> VEC3;
    public static final EntityDataSerializer<Vec9> VEC9;
    private ApiEntityDataSerializers() {
    }

    static {
        /*UUID_SERIALIZER = new EntityDataSerializer.ForValueType<>() {
            public void write(FriendlyByteBuf friendlyByteBuf, UUID uuid) {
                friendlyByteBuf.writeUtf(uuid.toString()));
            }

            public UUID read(FriendlyByteBuf friendlyByteBuf) {
                return UUID.fromString(friendlyByteBuf.readUtf());
            }
        };*/
        API_SPELL = new EntityDataSerializer.ForValueType<>() {
            public void write(FriendlyByteBuf friendlyByteBuf, APISpells.APISpell apiSpell) {
                friendlyByteBuf.writeUtf(apiSpell.name());
            }

            public APISpells.APISpell read(FriendlyByteBuf friendlyByteBuf) {
                return APISpells.APISpell.valueOf(friendlyByteBuf.readUtf());
            }
        };
        DOUBLE = EntityDataSerializer.simple(FriendlyByteBuf::writeDouble, FriendlyByteBuf::readDouble);
        VEC3 = new EntityDataSerializer.ForValueType<>() {
            public void write(FriendlyByteBuf pBuffer, Vec3 pValue) {
                pBuffer.writeDouble(pValue.x);
                pBuffer.writeDouble(pValue.y);
                pBuffer.writeDouble(pValue.z);
            }

            public Vec3 read(FriendlyByteBuf pBuffer) {
                return new Vec3(pBuffer.readDouble(), pBuffer.readDouble(), pBuffer.readDouble());
            }
        };
        VEC9 = new EntityDataSerializer.ForValueType<>() {
            public void write(FriendlyByteBuf pBuffer, Vec9 pValue) {
                pBuffer.writeDouble(pValue.x).writeDouble(pValue.y).writeDouble(pValue.z);
            }

            public Vec9 read(FriendlyByteBuf pBuffer) {
                return Vec9.of(pBuffer.readDouble(), pBuffer.readDouble(), pBuffer.readDouble());
            }
        };
        //EntityDataSerializers.registerSerializer(UUID_SERIALIZER);
        EntityDataSerializers.registerSerializer(API_SPELL);
        EntityDataSerializers.registerSerializer(DOUBLE);
        EntityDataSerializers.registerSerializer(VEC3);
        EntityDataSerializers.registerSerializer(VEC9);
    }
}
