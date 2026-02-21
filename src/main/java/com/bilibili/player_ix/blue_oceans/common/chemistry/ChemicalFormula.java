
package com.bilibili.player_ix.blue_oceans.common.chemistry;

import net.minecraft.network.chat.Component;

public record ChemicalFormula(String name, double value) {
    public Component description() {
        return Component.translatable("bo.cf." + name);
    }
}
