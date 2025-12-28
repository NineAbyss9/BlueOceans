
package com.github.player_ix.ix_api.api.mobs;

public interface IFlagMob {
    int getFlag();

    void setFlag(int flag);

    default int getAttackTick() {
        return 0;
    }

    default void setAttackTick(int attackTick) {
    }

    default boolean isFlag(int flag) {
        return this.getFlag() == flag;
    }

    default void resetFlag() {
        this.setFlag(0);
    }

    default void resetState() {
        this.resetAttackTick();
        this.resetFlag();
    }

    default void plusAttackTick() {
        this.setAttackTick(this.getAttackTick() + 1);
    }

    default boolean attackTickEquals(int pAttackTick) {
        return this.getAttackTick() == pAttackTick;
    }

    default boolean attackTick(int pAttackTick) {
        return this.getAttackTick() >= pAttackTick;
    }

    default void resetAttackTick() {
        this.setAttackTick(0);
    }
}
