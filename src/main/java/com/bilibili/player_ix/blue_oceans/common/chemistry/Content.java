
package com.bilibili.player_ix.blue_oceans.common.chemistry;

import com.bilibili.player_ix.blue_oceans.common.blocks.chemistry.AbstractContainer;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.world.level.block.Block;
import org.nine_abyss.util.Nothing;
import org.nine_abyss.util.function.ConditionalConsumer;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class Content {
    public final int id;
    public final Consumer<Block> consumer;
    private static final Int2ObjectMap<Content> MAP;
    public static final Content EMPTY;
    public static final Content WATER;
    public static final Content LAVA;
    public Content(int pId, Consumer<Block> pConsumer) {
        this.id  = pId;
        this.consumer = pConsumer;
    }

    public static Content register(int pId, Consumer<Block> blockConsumer) {
        Content content = new Content(pId, blockConsumer);
        MAP.put(pId, content);
        return content;
    }

    public static Content of(int pId) {
        return MAP.getOrDefault(pId, EMPTY);
    }

    public static void ifContainer(Block block, Consumer<AbstractContainer> c) {
        if (block instanceof AbstractContainer container) {
            c.accept(container);
            //return true;
        }
        //return false;
    }

    public static boolean water(AbstractContainer container) {
        return container.holder().stream().anyMatch(Predicate.isEqual(WATER));
    }

    static {
        MAP = new Int2ObjectOpenHashMap<>();
        EMPTY = register(0, block -> {
        });
        WATER = register(1, block -> ifContainer(block,
                ConditionalConsumer.empty()));
        LAVA = register(2, block -> ifContainer(block, container -> {
            if (water(container)) {
                if (container.fill(EMPTY)) {
                    Nothing.getInstance().noting();
                }
            }
        }));
    }
}
