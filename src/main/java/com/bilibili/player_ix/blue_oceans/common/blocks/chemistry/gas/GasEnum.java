
package com.bilibili.player_ix.blue_oceans.common.blocks.chemistry.gas;

import com.bilibili.player_ix.blue_oceans.common.chemistry.*;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.Level;
import org.NineAbyss9.util.pair.Pair;

import java.util.Collections;
import java.util.List;

public enum GasEnum
implements ICheFor
{
    HYDROGEN(Element.H, 0.1f,0.7f, Collections.singletonList(new MobEffectInstance(MobEffects.DIG_SLOWDOWN,
            25)),true, Pair.of(Level.ExplosionInteraction.NONE, 0F),16767483),
    HELIUM(Element.He,0.15f, 0.5f, Collections.singletonList(new MobEffectInstance(MobEffects.JUMP,25)),true, Pair.of(Level.ExplosionInteraction.NONE, 0F),16750204),
    NITROGEN(Element.Ni, 0.97f,0.19f,Collections.singletonList(new MobEffectInstance(MobEffects.CONFUSION,25)),true,Pair.of(Level.ExplosionInteraction.NONE, 0F),14459799),
    OXYGEN(Element.O, 1.1f,0.18f,Collections.emptyList(),false,Pair.of(Level.ExplosionInteraction.NONE, 0F),8585197),
    FLUORINE(Element.F, 1.6f,0.16f,Collections.singletonList(new MobEffectInstance(MobEffects.POISON,25)),true,Pair.of(Level.ExplosionInteraction.NONE, 0F),15983239),
    NEON(Element.Ne, 0.9f,0.23f,Collections.singletonList(new MobEffectInstance(MobEffects.NIGHT_VISION,25)),true,Pair.of(Level.ExplosionInteraction.NONE, 0F),16763457),
    CHLORINE(Element.Cl, 2.5f,0.12f,Collections.singletonList(new MobEffectInstance(MobEffects.BLINDNESS,25)),true,Pair.of(Level.ExplosionInteraction.NONE, 0F),15925122),
    ARGON(Element.Ar, 1.4f,0.16f,Collections.singletonList(new MobEffectInstance(MobEffects.DIG_SPEED,25)),true,Pair.of(Level.ExplosionInteraction.NONE, 0F),12354047),
    KRYPTON(Element.Kr, 2.8f,0.11f,Collections.singletonList(new MobEffectInstance(MobEffects.GLOWING,25)),true,Pair.of(Level.ExplosionInteraction.NONE, 0F),12568788),
    XENON(Element.Xe, 4.5f,0.09f,Collections.singletonList(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,25)),true,Pair.of(Level.ExplosionInteraction.NONE, 0F),8299263),
    RADON(Element.Ra, 7.5f,0.07f, List.of(new MobEffectInstance(MobEffects.WITHER, 25)),true,Pair.of(Level.ExplosionInteraction.NONE, 0F),16743053),
    OGANESSON(Element.Og, 10f,0.06f,Collections.singletonList(new MobEffectInstance(MobEffects.SLOW_FALLING,25)),true,Pair.of(Level.ExplosionInteraction.NONE, 0F),10328228),
    AMMONIA(Element.Am, 0.73f,0.24f,Collections.singletonList(new MobEffectInstance(MobEffects.CONFUSION,25)),true,Pair.of(Level.ExplosionInteraction.NONE, 0F),14459799),
    CARBON_DIOXIDE(ChemicalFormulas.CO2, 2f,0.15f, Collections.singletonList(new MobEffectInstance(MobEffects.DIG_SLOWDOWN,25)),true,Pair.of(Level.ExplosionInteraction.NONE, 0F),3684408),
    HYDROGEN_CHLORIDE(ChemicalFormulas.HCl, 1.5f,0.17f, Collections.emptyList(),true,Pair.of(Level.ExplosionInteraction.NONE, 0F),10345635),
    //TODO correct these:
    HYDROGEN_FLUORIDE(Element.Am, 1.15f,0.22f, Collections.emptyList(), true, Pair.of(Level.ExplosionInteraction.NONE, 0F),10345635),
    HYDROGEN_SULFIDE(Element.Am, 1.35f,0.17f, Collections.emptyList(), true,Pair.of(Level.ExplosionInteraction.NONE, 0F),13676874),
    SULFUR_DIOXIDE(Element.Og, 2.9f,0.13f, Collections.emptyList(), true, Pair.of(Level.ExplosionInteraction.NONE, 0F),6118148),
    TUNGSTEN_HEXAFLUORIDE(Element.He, 2.4f,0.13f, Collections.emptyList(), true, Pair.of(Level.ExplosionInteraction.NONE, 0F),11048836);
    private final ChemicalFormula element;
    private final float density;
    private final float dissipationRate;
    private final List<MobEffectInstance> effects;
    private final boolean suffocation;
    private final Pair<Level.ExplosionInteraction,Float> flammability;
    private final int color;
    GasEnum(ChemicalFormula elementIn, float density, float dissipationRate, List<MobEffectInstance> effects, boolean suffocation,
            Pair<Level.ExplosionInteraction,Float> flammability, int color)
    {
        this.element = elementIn;
        this.density = density;
        this.dissipationRate = dissipationRate;
        this.effects = effects;
        this.suffocation = suffocation;
        this.flammability = flammability;
        this.color = color;
    }

    GasEnum(Element elementIn, float density, float dissipationRate, List<MobEffectInstance> effects, boolean suffocation,
            Pair<Level.ExplosionInteraction,Float> flammability, int color)
    {
        this(elementIn.chemicalFormula(), density, dissipationRate, effects, suffocation, flammability, color);
    }

    public boolean isSuffocating() {
        return this.suffocation;
    }

    public float getDensity() {
        return density;
    }

    public float getDissipationRate() {
        return dissipationRate;
    }

    public Pair<Level.ExplosionInteraction, Float> getFlammability() {
        return flammability;
    }

    public List<MobEffectInstance> getEffects() {
        return effects;
    }

    public int getColor() {
        return color;
    }

    public ChemicalFormula chemicalFormula()
    {
        return element;
    }
}
