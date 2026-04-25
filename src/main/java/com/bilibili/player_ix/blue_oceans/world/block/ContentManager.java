
package com.bilibili.player_ix.blue_oceans.world.block;

import com.bilibili.player_ix.blue_oceans.api.misc.ContentHolder;
import com.bilibili.player_ix.blue_oceans.common.chemistry.Content;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.NineAbyss9.value_holder.BooleanValueHolder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class ContentManager
{
    private static final Map<Level, ArrayList<BlockPos>> contentsByLevel
            = new LinkedHashMap<>();
    private static final Map<BlockPos, ContentHolder> blockPosContent
            = new LinkedHashMap<>();
    public static void putPos(Level pLevel, BlockPos pPos) {
        contentsByLevel.computeIfAbsent(pLevel, level -> {
            ArrayList<BlockPos> list = new ArrayList<>();
            list.add(pPos);
            return list;
        });
    }

    public static void putContent(BlockPos pPos, ContentHolder pContent) {
        blockPosContent.putIfAbsent(pPos, pContent);
    }

    public static ContentHolder get(Level pLevel, BlockPos pPos) {
        putContent(pPos, new ContentHolder());
        contentsByLevel.computeIfAbsent(pLevel, level -> {
            ArrayList<BlockPos> list = new ArrayList<>();
            list.add(pPos);
            return list;
        });
        return blockPosContent.get(pPos);
    }

    public static BooleanValueHolder<Content> fill(Level pLevel, BlockPos pPos, Content pContent) {
        putPos(pLevel, pPos);
        putContent(pPos, new ContentHolder());
        var holder = blockPosContent.get(pPos);
        var result = holder.fill(pContent);
        blockPosContent.put(pPos, holder);
        return result;
    }
}
