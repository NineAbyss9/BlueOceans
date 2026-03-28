
package com.bilibili.player_ix.blue_oceans.common.chemistry;

import com.bilibili.player_ix.blue_oceans.common.blocks.chemistry.AbstractContainer;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.FluidState;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Content
{
    private static int currentId = -1;
    public final int id;
    public final String name;
    //public final Consumer<Block> consumer;
    private static final Int2ObjectMap<Content> MAP;
    public static final Content EMPTY;
    public static final Content WATER;
    public static final Content LAVA;
    public Content(int pId, String pSt)
    {
        id  = pId;
        name = pSt;
    }

    public boolean isEmpty() {
        return this.id == 0;
    }

    public Component description() {
        return Component.translatable("content." + name);
    }

    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (!(obj instanceof Content content))
            return false;
        return id == content.id && name.equals(content.name);
    }

    public int hashCode() {
        return Objects.hash(id, name//, consumer
        );
    }

    public static Content register(String pName)
    {
        currentId++;
        Content content = new Content(currentId, pName);
        MAP.put(currentId, content);
        return content;
    }

    public static Content of(int pId)
    {
        Content content;
        return (content = MAP.get(pId))
                == null ? Content.EMPTY : content;
    }

    public static Content init(ChemicalFormula cf)
    {
        return register(cf.name());
    }

    public static Content get(FluidState pState)
    {
        if (pState.isEmpty())
            return Content.EMPTY;
        else if (pState.is(FluidTags.WATER))
            return Content.WATER;
        else
            return Content.LAVA;
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
        EMPTY = register("Empty"//, FunctionCollector.accept()
        );
        WATER = register("Water"//, block -> ifContainer(block,
         //       FunctionCollector.accept())
    );
        LAVA = register("Lava"/*, block -> ifContainer(block, container -> {
            if (water(container)) {
                if (container.fill(EMPTY)) {
                    Nothing.getInstance().noting();
                }
            }
        })*/);
    }
}
