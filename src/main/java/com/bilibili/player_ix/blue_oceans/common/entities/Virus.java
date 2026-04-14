
package com.bilibili.player_ix.blue_oceans.common.entities;

import com.bilibili.player_ix.blue_oceans.common.item.biology.organ.*;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansMobEffects;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;

public class Virus
{
    private VirusType virusType;
    public Virus() {
        super();
    }

    protected void readAdditionalSaveData(CompoundTag pCompound)
    {
        this.setVirusType(pCompound.getInt("VirusType"));
    }

    protected void addAdditionalSaveData(CompoundTag pCompound)
    {
        pCompound.putInt("VirusType", this.getVirusType());
    }

    public int getVirusType()
    {
        return virusType.id;
    }

    public VirusType getVirusTypeEnum()
    {
        return virusType;
    }

    public void setVirusType(int type)
    {
        this.virusType = VirusType.byId(type);
    }

    public void setVirusType(VirusType type)
    {
        this.virusType = type;
    }

    public enum VirusType {
        ///天花
        Smallpox(0, 1.0f, MobEffects.WITHER, 40),
        ///流感
        Influenza(1, 0.8f, MobEffects.MOVEMENT_SLOWDOWN, 50),
        ///埃博拉
        Ebola(2, 1.5f, MobEffects.POISON, 30),
        ///新冠
        COVID19(3, 1.2f, MobEffects.WEAKNESS, 45),
        ///狂犬病
        Rabies(4, 1.3f, MobEffects.CONFUSION, 35),
        ///艾滋病
        HIV(5, 0.5f, MobEffects.WEAKNESS, 60),
        ///寨卡
        Zika(6, 0.7f, MobEffects.MOVEMENT_SLOWDOWN, 55),
        ///登革热
        Dengue(7, 1.1f, MobEffects.POISON, 40),
        ///马尔堡
        Marburg(8, 1.4f, MobEffects.WITHER, 32),
        ///尼帕
        Nipah(9, 1.6f, MobEffects.CONFUSION, 28),
        ///汉坦病毒
        Hantavirus(10, 1.2f, MobEffects.POISON, 42),
        ///拉沙热
        Lassa(11, 1.0f, MobEffects.WEAKNESS, 48),
        ///裂谷热
        RiftValleyFever(12, 1.3f, MobEffects.WITHER, 38),
        ///猴痘
        Monkeypox(13, 0.9f, MobEffects.WEAKNESS, 52),
        ///黄热病
        YellowFever(14, 1.1f, MobEffects.POISON, 44),
        ///基孔肯雅热
        Chikungunya(15, 0.8f, MobEffects.MOVEMENT_SLOWDOWN, 50),
        ///亨德拉病毒
        Hendra(16, 1.5f, MobEffects.CONFUSION, 30),
        ///严重急性呼吸综合征
        SARS(17, 1.2f, MobEffects.WEAKNESS, 40),
        RED_PLUM(18, 1.7F, BlueOceansMobEffects.PLUM_INFECTION.get(), 20);
        private final int id;
        private final float damageMultiplier;
        private final MobEffect associatedEffect;
        private final int organDamageInterval;
        private VirusType(int id, float damageMultiplier, MobEffect associatedEffect, int organDamageInterval) {
            this.id = id;
            this.damageMultiplier = damageMultiplier;
            this.associatedEffect = associatedEffect;
            this.organDamageInterval = organDamageInterval;
        }

        public int getId()
        {
            return this.id;
        }

        public float getDamageMultiplier()
        {
            return this.damageMultiplier;
        }

        public MobEffect getAssociatedEffect()
        {
            return this.associatedEffect;
        }

        public int getOrganDamageInterval()
        {
            return this.organDamageInterval;
        }

        public Class<? extends Organ> getTargetOrgan() {
            return switch (this) {
                case Smallpox, Monkeypox -> Heart.class;
                case Influenza, SARS, Hendra, COVID19 -> Lung.class;
                case Ebola, Dengue, YellowFever, RiftValleyFever, Lassa, Hantavirus, Marburg -> Kidney.class;
                case Rabies, Zika, Chikungunya, Nipah -> Brain.class;
                default -> Organ.class;
            };
        }

        public static VirusType byId(int id) {
            for (VirusType type : values()) {
                if (type.getId() == id) {
                    return type;
                }
            }
            return Smallpox;
        }
    }
}
