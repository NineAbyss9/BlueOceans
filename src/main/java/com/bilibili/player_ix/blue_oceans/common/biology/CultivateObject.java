
package com.bilibili.player_ix.blue_oceans.common.biology;

import net.minecraft.network.FriendlyByteBuf;

import java.util.*;

public class CultivateObject {
    public static Map<String, CultivateObject> FINDER;
    public static final CultivateObject EMPTY;
    /**
     * 青霉菌
     */
    public static final CultivateObject Penicillium;
    public static final CultivateObject RED_PLUM_CELL = null;
    private final String name;
    private final long growTime;
    private final int maxGrow;
    private final Type type;
    public CultivateObject(String pName, long pGrowTime, int pMaxGrow, Type pType) {
        this.name = pName;
        this.growTime = pGrowTime;
        this.maxGrow = pMaxGrow;
        this.type = pType;
        FINDER.put(pName, this);
    }

    public boolean isEmpty() {
        return type == Type.EMPTY;
    }

    public void writeToNetwork(FriendlyByteBuf buffer) {
        buffer.writeUtf(name);
    }

    public boolean isBacteria() {
        return this.type.isBacteria();
    }

    public boolean isFungi() {
        return this.type.isFungi();
    }

    public String name() {
        return name;
    }

    public long growTime() {
        return growTime;
    }

    public int maxGrow() {
        return maxGrow;
    }

    public Type type() {
        return type;
    }

    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (CultivateObject)obj;
        return Objects.equals(this.name, that.name) &&
                this.growTime == that.growTime &&
                this.maxGrow == that.maxGrow &&
                Objects.equals(this.type, that.type);
    }

    public int hashCode() {
        return Objects.hash(name, growTime, maxGrow, type);
    }

    public String toString() {
        return "CultivateObject[" +
                "name:" + name + ", " +
                "growTime:" + growTime + ", " +
                "maxGrow:" + maxGrow + ", " +
                "type:" + type + ']';
    }

    public static CultivateObject get(String pName) {
        if (!FINDER.containsKey(pName))
            return EMPTY;
        return FINDER.get(pName);
    }

    static {
        FINDER = new TreeMap<>();
        EMPTY = new CultivateObject("Empty", Long.MAX_VALUE, Integer.MAX_VALUE, Type.EMPTY);
        Penicillium = new CultivateObject("Penicillium", 400L, 4, Type.Fungi);
    }

    public enum Type {
        EMPTY,
        Bacteria,
        Cell,
        Fungi;

        public boolean isBacteria() {
            return this == Bacteria;
        }

        public boolean isCell() {
            return this == Cell;
        }

        public boolean isFungi() {
            return this == Fungi;
        }
    }
}
