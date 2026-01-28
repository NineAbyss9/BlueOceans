
package com.github.player_ix.ix_api.api.mobs;

public interface IFlagMob {
    int getFlag();

    void setFlag(int flag);

    default int getAnimTick() {
        return 0;
    }

    default void setAnimTick(int animTick) {
    }

    default boolean isFlag(int flag) {
        return this.getFlag() == flag;
    }

    default void resetFlag() {
        this.setFlag(0);
    }

    default void resetState() {
        this.resetAnimTick();
        this.resetFlag();
    }

    default void increaseAnimTick() {
        this.setAnimTick(this.getAnimTick() + 1);
    }

    default boolean animTickEquals(int pAttackTick) {
        return this.getAnimTick() == pAttackTick;
    }

    default boolean animTick(int pAttackTick) {
        return this.getAnimTick() >= pAttackTick;
    }

    default void resetAnimTick() {
        this.setAnimTick(0);
    }
}
