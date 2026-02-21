
package com.bilibili.player_ix.blue_oceans.compat.noixapi;

import com.bilibili.player_ix.blue_oceans.compat.ICompatable;
import com.github.NineAbyss9.ix_api.IXApi;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class NoIXApiCompat
implements ICompatable {
    public NoIXApiCompat() {
    }

    public static boolean isApiLoaded() {
        return IXApi.isNoIXApiLoaded();
    }

    public void setup(FMLCommonSetupEvent event) {
    }

    public static MobEffect getApiEffect(String name) {
        return ICompatable.getEffect(IXApi.NOIXMODAPI, name);
    }

    public static EntityType<?> getApiEntity(String name) {
        return ICompatable.getEntityType(IXApi.NOIXMODAPI, name);
    }
}
