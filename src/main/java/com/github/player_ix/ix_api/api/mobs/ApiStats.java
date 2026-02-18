
package com.github.player_ix.ix_api.api.mobs;

import com.github.player_ix.ix_api.IXApi;
import org.NineAbyss9.annotation.Unused;
import com.github.player_ix.ix_api.util.ResourceLocations;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.StatType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Unused
public class ApiStats {
    public static final DeferredRegister<StatType<?>> REGISTER =
            DeferredRegister.create(ForgeRegistries.STAT_TYPES, IXApi.MOD_ID);
    public static final ResourceLocation INTERACT_WITH_ALTAR;

    public static ResourceLocation makeCustomStat(String location, StatFormatter formatter) {
        //REGISTER.register(location, ()->new StatType<>());
        return ResourceLocations.parse(location);
    }

    static {
        INTERACT_WITH_ALTAR = makeCustomStat("interact_with_altar", StatFormatter.DEFAULT);
    }
}
