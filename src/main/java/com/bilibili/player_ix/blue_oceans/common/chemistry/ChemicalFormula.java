
package com.bilibili.player_ix.blue_oceans.common.chemistry;

import net.minecraft.network.chat.Component;

import java.util.Objects;

public class ChemicalFormula
{
    private final String name;
    private final double value;
    private final Element first;
    public ChemicalFormula(String name, double value)
    {
        this.name = name;
        this.value = value;
        //TODO: correct this
        this.first = Element.fromElementSymbol(this.name);
    }

    public Component description()
    {
        return Component.translatable("bo.cf." + name);
    }

    public Element first()
    {
        return first;
    }

    public String name()
    {
        return name;
    }

    public double value()
    {
        return value;
    }

    public boolean equals(Object obj)
    {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var other = (ChemicalFormula)obj;
        return Objects.equals(this.name, other.name) &&
                Double.doubleToLongBits(this.value) == Double.doubleToLongBits(other.value);
    }

    public int hashCode()
    {
        return Objects.hash(name, value);
    }

    public String toString()
    {
        return "ChemicalFormula[" +
                "name=" + name + ", " +
                "value=" + value + ']';
    }
}
