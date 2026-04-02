
package com.bilibili.player_ix.blue_oceans.world;

import net.minecraft.ChatFormatting;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

import java.util.Objects;

public class Weather
{
    private final Category category;
    private String name;
    public Weather(Category pC, String nameIn)
    {
        this.category = pC;
        this.name = nameIn;
    }

    public Weather(Category pC)
    {
        this.category = pC;
    }

    public Category getCategory()
    {
        return category;
    }

    public void tick(Level pLevel)
    {
        if (pLevel.isClientSide)
        {
            this.clientTick((ClientLevel)pLevel);
        } else
        {
            this.serverTick((ServerLevel)pLevel);
        }
    }

    public void clientTick(ClientLevel pLevel)
    {

    }

    public void serverTick(ServerLevel pLevel)
    {

    }

    public String name()
    {
        return name;
    }

    public void setName(String nameIn)
    {
        //this.name = nameIn;
    }

    public boolean equals(Object o)
    {
        if (!(o instanceof Weather weather)) return false;
        return this.getCategory() == weather.getCategory() && Objects.equals(name, weather.name);
    }

    public int hashCode()
    {
        return Objects.hash(getCategory(), name);
    }

    public String toString()
    {
        return "Weather:" + name + "," + category;
    }

    public enum Category
    {
        HARMFUL(ChatFormatting.RED),
        NATURAL(ChatFormatting.GREEN),
        BENEFICIAL(ChatFormatting.BLUE);
        private final ChatFormatting tooltipFormatting;

        private Category(ChatFormatting pTooltipFormatting) {
            this.tooltipFormatting = pTooltipFormatting;
        }

        public ChatFormatting getTooltipFormatting() {
            return this.tooltipFormatting;
        }
    }
}
