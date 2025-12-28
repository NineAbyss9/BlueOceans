
package com.bilibili.player_ix.blue_oceans.api.mob;

public class AgeManager {
    private int age;
    public AgeManager() {
    }

    public void plus() {
        ++age;
    }

    public void setAge(int pAge) {
        this.age = pAge;
    }
}
