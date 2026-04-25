
package com.bilibili.player_ix.blue_oceans.init.data;

import com.bilibili.player_ix.blue_oceans.init.BoTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class BoTagProvider
extends ItemTagsProvider
{
    public BoTagProvider(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_, CompletableFuture<TagLookup<Block>> p_275322_, String modId, @Nullable ExistingFileHelper existingFileHelper)
    {
        super(p_275343_, p_275729_, p_275322_, modId, existingFileHelper);
    }

    protected void addTags(HolderLookup.Provider provider)
    {

    }
}
