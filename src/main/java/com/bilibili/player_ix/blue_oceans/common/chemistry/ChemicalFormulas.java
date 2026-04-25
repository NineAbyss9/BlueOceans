
package com.bilibili.player_ix.blue_oceans.common.chemistry;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class ChemicalFormulas
{
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final Map<String, ChemicalFormula> MAP;
    public static final ChemicalFormula CO2;
    public static final ChemicalFormula CO;
    public static final ChemicalFormula O2;
    public static final ChemicalFormula WATER;
    public static final ChemicalFormula H2O2;
    public static final ChemicalFormula CH4;
    public static final ChemicalFormula H2;
    public static final ChemicalFormula NACL;
    public static final ChemicalFormula MnO2;
    public static final ChemicalFormula HCl;
    public static final ChemicalFormula SO2;

    public static final ChemicalFormula HF;
    public static final ChemicalFormula H2S;
    public static final ChemicalFormula WF6;

    public static ChemicalFormula get(String name) {
        if (!MAP.containsKey(name)) {
            var cf = of(name);
            Content.init(cf);
            LOGGER.debug("Found uninitialized {} \"{}\", registered.", ChemicalFormula.class.getSimpleName(),
                    name);
            return cf;
        }
        return MAP.get(name);
    }

    public static ChemicalFormula of(String name, double value)
    {
        return MAP.putIfAbsent(name, new ChemicalFormula(name, value));
    }

    public static ChemicalFormula of(String name)
    {
        return of(name, 0);
    }

    static {
        MAP = new HashMap<>();
        NACL = of("NaCl");
        H2 = of("H₂");
        CH4 = of("CH₄");
        H2O2 = of("H₂O₂");
        WATER = of("H₂O");
        CO = of("CO");
        CO2 = of("CO₂");
        O2 = of("O₂");
        MnO2 = of("MnO₂");
        HCl = of("HCl");
        SO2 = of("SO₂");

        HF = of("HF");
        H2S = of("H₂S");
        WF6 = of("WF₆");
    }
}
