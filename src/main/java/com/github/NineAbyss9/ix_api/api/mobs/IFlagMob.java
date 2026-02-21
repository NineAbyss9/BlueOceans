
package com.github.NineAbyss9.ix_api.api.mobs;

public interface IFlagMob {
    int getFlag();

    void setFlag(int flag);

    default int getAniTick() {
        return 0;
    }

    default void setAniTick(int aniTick) {
    }

    default boolean isFlag(int flag) {
        return this.getFlag() == flag;
    }

    default void resetFlag() {
        this.setFlag(0);
    }

    default void resetState() {
        this.resetAniTick();
        this.resetFlag();
    }

    default void increaseAniTick() {
        this.setAniTick(this.getAniTick() + 1);
    }

    default boolean aniTickEquals(int pAttackTick) {
        return this.getAniTick() == pAttackTick;
    }

    default boolean aniTick(int pAttackTick) {
        return this.getAniTick() >= pAttackTick;
    }

    default void resetAniTick() {
        this.setAniTick(0);
    }
}
