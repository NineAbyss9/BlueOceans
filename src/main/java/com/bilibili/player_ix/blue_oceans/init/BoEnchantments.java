
package com.bilibili.player_ix.blue_oceans.init;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.common.enchantment.Harvest;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BoEnchantments {
    public static final DeferredRegister<Enchantment> REGISTER
            = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, BlueOceans.MOD_ID);
    public static final RegistryObject<Enchantment> HARVEST;

    static {
        HARVEST = REGISTER.register("harvest", Harvest::new);
    }
}
