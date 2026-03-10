
package com.bilibili.player_ix.blue_oceans.common.chemistry;

public class ChemicalFormulas {
    public static final ChemicalFormula CO2;
    public static final ChemicalFormula CO;
    public static final ChemicalFormula O2;
    public static final ChemicalFormula WATER;
    public static final ChemicalFormula H2O2;
    public static final ChemicalFormula CH4;
    public static final ChemicalFormula H2;
    public static final ChemicalFormula NACL;

    public static ChemicalFormula of(String name, double value) {
        return new ChemicalFormula(name, value);
    }

    public static ChemicalFormula of(String name) {return of(name, 0);}

    static {
        NACL = of("NaCl");
        H2 = of("H₂");
        CH4 = of("CH₄");
        H2O2 = of("H₂O₂");
        WATER = of("H₂O");
        CO = of("CO");
        CO2 = of("CO₂");
        O2 = of("O₂");
    }
}
