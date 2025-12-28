
package com.github.player_ix.ix_api.init;

import com.github.player_ix.ix_api.IXApi;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ApiItems {
    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS,
            IXApi.MOD_ID);
    public static final RegistryObject<Item> TESTER_SPAWN_EGG;

    private static RegistryObject<Item> spawnEgg(String name, Supplier<? extends EntityType<? extends Mob>>
            supplier, int g, int b) {
        return REGISTER.register(name + "_spawn_egg", ()->new ForgeSpawnEggItem(supplier, g, b, properties()));
    }

    private static Item.Properties properties() {
        return new Item.Properties();
    }

    static {
        TESTER_SPAWN_EGG = spawnEgg("damage_tester", ApiEntities.DAMAGE_TESTER,
                0, 0);
    }
}
