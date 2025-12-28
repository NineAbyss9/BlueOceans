
package com.github.player_ix.ix_api.init;

import com.github.player_ix.ix_api.IXApi;
import com.github.player_ix.ix_api.api.mobs.DamageTester;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ApiEntities {
    public static final DeferredRegister<EntityType<?>> REGISTER = DeferredRegister.create(
            ForgeRegistries.ENTITY_TYPES, IXApi.MOD_ID);
    public static final RegistryObject<EntityType<DamageTester>> DAMAGE_TESTER;

    private static <T extends Entity> RegistryObject<EntityType<T>> register(String name,
                                                                             EntityType.Builder<T> builder) {
        return REGISTER.register(name, ()->builder.build(name));
    }

    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(DAMAGE_TESTER.get(), DamageTester.createAttributes());
    }

    static {
        DAMAGE_TESTER = register("damage_tester", EntityType.Builder.of(DamageTester::new, MobCategory.MISC));
    }
}
