
package com.github.player_ix.ix_api.api.mobs;

import com.github.player_ix.ix_api.api.APISpells;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;

public class ApiEntityDataSerializers {
    //public static final EntityDataSerializer<UUID> UUID_SERIALIZER;
    public static final EntityDataSerializer<APISpells.APISpell> API_SPELL;
    private ApiEntityDataSerializers() {
    }

    static {
        /*UUID_SERIALIZER = new EntityDataSerializer.ForValueType<>() {
            public void write(FriendlyByteBuf friendlyByteBuf, UUID uuid) {
                friendlyByteBuf.writeUtf(Integer.toString(uuid.hashCode()));
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
        //EntityDataSerializers.registerSerializer(UUID_SERIALIZER);
        EntityDataSerializers.registerSerializer(API_SPELL);
    }
}
