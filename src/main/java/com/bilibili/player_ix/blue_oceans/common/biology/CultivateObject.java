
package com.bilibili.player_ix.blue_oceans.common.biology;

import net.minecraft.network.FriendlyByteBuf;

import java.util.*;

public class CultivateObject {
    public static Map<String, CultivateObject> FINDER;
    /**
     * 青霉菌
     */
    public static final CultivateObject Penicillium;
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
                "name=" + name + ", " +
                "growTime=" + growTime + ", " +
                "maxGrow=" + maxGrow + ", " +
                "type=" + type + ']';
    }


    static {
        FINDER = new TreeMap<>();
        Penicillium = new CultivateObject("Penicillium", 400L, 4, Type.Bacteria);
    }

    public enum Type {
        Bacteria,
        Fungi;

        public boolean isBacteria() {
            return this == Bacteria;
        }

        public boolean isFungi() {
            return this == Fungi;
        }
    }
}
