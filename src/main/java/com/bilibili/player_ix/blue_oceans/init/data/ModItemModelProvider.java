
package com.bilibili.player_ix.blue_oceans.init.data;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItemModelProvider
extends ItemModelProvider
{
    public ModItemModelProvider(PackOutput output, String modid, ExistingFileHelper existingFileHelper)
    {
        super(output, modid, existingFileHelper);
    }

    protected void registerModels()
    {
        BoDataGenHelper.ITEM_REGISTRIES.forEach(object -> {
            Item item = object.get();
            if (item instanceof Handed handed) {
                String st;
                if (handed.isUtil())
                    st = "util";
                else
                    st = "weapon";
                withExistingParent(item.toString(), mcLoc("item/handheld"))
                        .texture("layer0", modLoc("item/" + st + handed.getAddress()));
            } else if (item instanceof Food food) {
                String address = food.getAddress();
                basic(food.getLoc(), address.isEmpty() ? "food/" + item : address);
            } else if (item instanceof ITextureProvider provider) {
                basic(provider.getLoc(), provider.getAddress());
            } else if (item instanceof SpawnEggItem) {
                spawnEgg(item);
            } else
                basicItem(item);
        });
    }

    @SuppressWarnings("UnusedReturnValue")
    public ItemModelBuilder basic(String loc, String texPath) {
        return getBuilder(modid + ":item/" + loc)
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", new ResourceLocation(modid, "item/" + texPath));
    }

    @SuppressWarnings("UnusedReturnValue")
    public ItemModelBuilder spawnEgg(Item item)
    {
        return spawnEgg(ForgeRegistries.ITEMS.getKey(item));
    }

    @SuppressWarnings("UnusedReturnValue")
    public ItemModelBuilder spawnEgg(ResourceLocation item)
    {
        return getBuilder(item.toString())
                .parent(new ModelFile.UncheckedModelFile("item/template_spawn_egg"));
    }

    public interface Handed
    extends ITextureProvider
    {
        default boolean isUtil() {
            return true;
        }
    }

    public interface Food
    extends ITextureProvider
    {
    }
}
