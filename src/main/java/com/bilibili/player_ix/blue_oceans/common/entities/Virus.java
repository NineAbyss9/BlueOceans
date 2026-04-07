package com.bilibili.player_ix.blue_oceans.common.entities;

import com.bilibili.player_ix.blue_oceans.common.item.biology.organ.Brain;
import com.bilibili.player_ix.blue_oceans.common.item.biology.organ.Heart;
import com.bilibili.player_ix.blue_oceans.common.item.biology.organ.Kidney;
import com.bilibili.player_ix.blue_oceans.common.item.biology.organ.Lung;
import com.bilibili.player_ix.blue_oceans.common.item.biology.organ.Organ;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class Virus
extends Entity
{
    private static final EntityDataAccessor<Integer> DATA_TYPE;
    public Virus(EntityType<?> pEntityType, Level pLevel)
    {
        super(pEntityType, pLevel);
    }

    protected void defineSynchedData()
    {
        this.entityData.define(DATA_TYPE, 0);
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
        return this.entityData.get(DATA_TYPE);
    }

    public VirusType getVirusTypeEnum()
    {
        int typeId = this.getVirusType();
        for (VirusType type : VirusType.values())
        {
            if (type.getId() == typeId)
            {
                return type;
            }
        }
        return VirusType.Smallpox;
    }

    public void setVirusType(int type)
    {
        this.entityData.set(DATA_TYPE, type);
    }

    public void setVirusType(VirusType type)
    {
        this.setVirusType(type.getId());
    }

    static {
        DATA_TYPE = SynchedEntityData.defineId(Virus.class, EntityDataSerializers.INT);
    }

    public enum VirusType {
        Smallpox(0, 1.0f, MobEffects.WITHER, 40),//天花
        Influenza(1, 0.8f, MobEffects.MOVEMENT_SLOWDOWN, 50),//流感
        Ebola(2, 1.5f, MobEffects.POISON, 30),//埃博拉
        COVID19(3, 1.2f, MobEffects.WEAKNESS, 45),//新冠
        Rabies(4, 1.3f, MobEffects.CONFUSION, 35),//狂犬病
        HIV(5, 0.5f, MobEffects.WEAKNESS, 60),//艾滋病
        Zika(6, 0.7f, MobEffects.MOVEMENT_SLOWDOWN, 55),//寨卡
        Dengue(7, 1.1f, MobEffects.POISON, 40),//登革热
        Marburg(8, 1.4f, MobEffects.WITHER, 32),//马尔堡
        Nipah(9, 1.6f, MobEffects.CONFUSION, 28),//尼帕
        Hantavirus(10, 1.2f, MobEffects.POISON, 42),//汉坦病毒
        Lassa(11, 1.0f, MobEffects.WEAKNESS, 48),//拉沙热
        RiftValleyFever(12, 1.3f, MobEffects.WITHER, 38),//裂谷热
        Monkeypox(13, 0.9f, MobEffects.WEAKNESS, 52),//猴痘
        YellowFever(14, 1.1f, MobEffects.POISON, 44),//黄热病
        Chikungunya(15, 0.8f, MobEffects.MOVEMENT_SLOWDOWN, 50),//基孔肯雅热
        Hendra(16, 1.5f, MobEffects.CONFUSION, 30),//亨德拉病毒
        SARS(17, 1.2f, MobEffects.WEAKNESS, 40);//严重急性呼吸综合征

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
