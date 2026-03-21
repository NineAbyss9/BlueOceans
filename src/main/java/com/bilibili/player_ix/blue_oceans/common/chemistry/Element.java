
package com.bilibili.player_ix.blue_oceans.common.chemistry;

import com.google.common.collect.Maps;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class Element {
    private static final Map<Integer, List<Object>> ELEMENT_MAP;
    private static int staticId = 0;
    public static final Element H;
    public static final Element He;
    public static final Element Li;
    public static final Element Be;
    public static final Element B;
    public static final Element C;
    public static final Element N;
    public static final Element O;
    public static final Element F;
    public static final Element Ne;
    public static final Element Na;
    public static final Element Mg;
    public static final Element Al;
    public static final Element Si;
    public static final Element P;
    public static final Element S;
    public static final Element Cl;
    public static final Element Ar;
    public static final Element K;
    public static final Element Ca;
    public static final Element Sc;
    public static final Element Ti;
    public static final Element V;
    public static final Element Cr;
    public static final Element Mn;
    public static final Element Fe;
    public static final Element Co;
    public static final Element Ni;
    public static final Element Cu;
    public static final Element Zn;
    public static final Element Ga;
    public static final Element Ge;
    public static final Element As;
    public static final Element Se;
    public static final Element Br;
    public static final Element Kr;
    public static final Element Rb;
    public static final Element Sr;
    public static final Element Y;
    public static final Element Zr;
    public static final Element Nb;
    public static final Element Mo;
    public static final Element Tc;
    public static final Element Ru;
    public static final Element Rh;
    public static final Element Pd;
    public static final Element Ag;
    public static final Element Cd;
    public static final Element In;
    public static final Element Sn;
    public static final Element Sb;
    public static final Element Te;
    public static final Element I;
    public static final Element Xe;
    public static final Element Cs;
    public static final Element Ba;
    public static final Element La;
    public static final Element Ce;
    public static final Element Pr;
    public static final Element Nd;
    public static final Element Pm;
    public static final Element Sm;
    public static final Element Eu;
    public static final Element Gd;
    public static final Element Tb;
    public static final Element Dy;
    public static final Element Ho;
    public static final Element Er;
    public static final Element Tm;
    public static final Element Yb;
    public static final Element Lu;
    public static final Element Hf;
    public static final Element Ta;
    public static final Element W;
    public static final Element Re;
    public static final Element Os;
    public static final Element Ir;
    public static final Element Pt;
    public static final Element Au;
    public static final Element Hg;
    public static final Element Tl;
    public static final Element Pb;
    public static final Element Bi;
    public static final Element Po;
    public static final Element At;
    public static final Element Rn;
    public static final Element Fr;
    public static final Element Ra;
    public static final Element Ac;
    public static final Element Th;
    public static final Element Pa;
    public static final Element U;
    public static final Element Np;
    public static final Element Pu;
    public static final Element Am;
    public static final Element Cm;
    public static final Element Bk;
    public static final Element Cf;
    public static final Element Es;
    public static final Element Fm;
    public static final Element Md;
    public static final Element No;
    public static final Element Lr;
    public static final Element Rf;
    public static final Element Db;
    public static final Element Sg;
    public static final Element Bh;
    public static final Element Hs;
    public static final Element Mt;
    public static final Element Ds;
    public static final Element Rg;
    public static final Element Cn;
    public static final Element Nh;
    public static final Element Fl;
    public static final Element Mc;
    public static final Element Lv;
    public static final Element Ts;
    public static final Element Og;
    public final int atomicNumber;
    public final float relativeAtomicMass;
    private final String elementSymbol;

    private Element(int pAtomicNumber, float pRelativeAtomicMass, String pElementSymbol) {
        this.atomicNumber = pAtomicNumber;
        this.relativeAtomicMass = pRelativeAtomicMass;
        this.elementSymbol = pElementSymbol;
        if (pAtomicNumber > 118 || pAtomicNumber < 1) {
            throw new NoSuchElementException();
        }
    }

    public static Element fromAtomicNumber(int pAtomicNumber) {
        if (ELEMENT_MAP.containsKey(pAtomicNumber))
            return (Element)ELEMENT_MAP.get(pAtomicNumber).get(0);
        else
            throw new NoSuchElementException(pAtomicNumber);
    }

    public static Element fromRelativeAtomicMass(float pRelativeAtomicMass) {
        Optional<List<Object>> optional = ELEMENT_MAP.values().stream().filter(list->
                Float.compare((float)(list.get(1)), pRelativeAtomicMass) == 0).findFirst();
        if (optional.isPresent())
            return (Element)optional.get().get(0);
        else
            throw new NoSuchElementException(pRelativeAtomicMass);
    }

    public static Element fromElementSymbol(String pElementSymbol) {
        Optional<List<Object>> optional = ELEMENT_MAP.values().stream().filter(list->
                Objects.equals(list.get(2), pElementSymbol)).findFirst();
        if (optional.isPresent())
            return (Element)optional.get().get(0);
        else
            throw new NoSuchElementException();
    }

    @Nonnull
    private static Element register(double pRelativeAtomicMass, String pElementSymbol) {
        float vF = (float)pRelativeAtomicMass;
        staticId++;
        Element element = new Element(staticId, vF, pElementSymbol);
        ELEMENT_MAP.put(staticId, List.of(element, vF, pElementSymbol));
        return element;
    }

    @Nonnull
    private static Element register(String pElementSymbol, double pRelativeAtomicMass) {
        return register(pRelativeAtomicMass, pElementSymbol);
    }

    public ChemicalFormula chemicalFormula() {
        return ChemicalFormulas.get(this.elementSymbol());
    }

    /**{@linkplain java.math.RoundingMode#HALF_EVEN}*/
    public float getRelativeAtomicMass() {
        return (int)this.relativeAtomicMass;
    }

    public String elementSymbol() {
        return this.elementSymbol;
    }

    public boolean equals(Object pOther) {
        if (this == pOther)
            return true;
        if (pOther instanceof Element element)
            return atomicNumber == element.atomicNumber && Float.compare(relativeAtomicMass,
                    element.relativeAtomicMass) == 0 && Objects.equals(elementSymbol, element.elementSymbol);
        return false;
    }

    public int hashCode() {
        return Objects.hash(atomicNumber, relativeAtomicMass, elementSymbol);
    }

    static {
        ELEMENT_MAP = Maps.newTreeMap();
        H = register(1.008, "H");
        He = register(4.0026, "He");
        Li = register(6.94, "Li");
        Be = register(9.0122, "Be");
        B = register(10.81, "B");
        C = register(12.011, "C");
        N = register(14.007, "N");
        O = register(15.999, "O");
        F = register(18.998, "F");
        Ne = register(20.180, "Ne");
        Na = register(22.990, "Na");
        Mg = register(24.305, "Mg");
        Al = register(26.982, "Al");
        Si = register(28.085, "Si");
        P = register(30.974, "P");
        S = register(32.06, "S");
        Cl = register(35.45, "Cl");
        Ar = register(39.948, "Ar");
        K = register(39.098, "K");
        Ca = register(40.078, "Ca");
        Sc = register(44.956, "Sc");
        Ti = register(47.867, "Ti");
        V = register(50.942, "V");
        Cr = register(51.996, "Cr");
        Mn = register(54.983, "Mn");
        Fe = register(55.845, "Fe");
        Co = register(58.933, "Co");
        Ni = register(58.693, "Ni");
        Cu = register(63.546, "Cu");
        Zn = register(65.38, "Zn");
        Ga = register(69.723, "Ga");
        Ge = register(72.630, "Ge");
        As = register(74.922, "As");
        Se = register(78.971, "Se");
        Br = register(79.904, "Br");
        Kr = register(83.798, "Kr");
        Rb = register(85.468, "Rb");
        Sr = register(87.62, "Sr");
        Y = register(88.906, "Y");
        Zr = register(91.224, "Zr");
        Nb = register(92.906, "Nb");
        Mo = register(95.95, "Mo");
        Tc = register(98, "Tc");
        Ru = register(101.07, "Ru");
        Rh = register(102.91, "Rh");
        Pd = register(106.42, "Pd");
        Ag = register(107.87, "Ag");
        Cd = register(112.41, "Cd");
        In = register(114.82, "In");
        Sn = register(118.71, "Sn");
        Sb = register(121.76, "Sb");
        Te = register(127.60, "Te");
        I = register(126.90, "I");
        Xe = register(131.29, "Xe");
        Cs = register(132.91, "Cs");
        Ba = register("Ba", 137.33);
        La = register("La", 138.91);
        Ce = register("Ce", 140.12);
        Pr = register("Pr", 140.91);
        Nd = register("Nd", 144.24);
        Pm = register("Pm", 145.0);
        Sm = register("Sm", 150.36);
        Eu = register("Eu", 151.96);
        Gd = register("Gd", 157.25);
        Tb = register("Tb", 158.93);
        Dy = register("Dy", 162.50);
        Ho = register("Ho", 164.93);
        Er = register("Er", 167.26);
        Tm = register("Tm", 168.93);
        Yb = register("Yb", 173.05);
        Lu = register("Lu", 174.97);
        Hf = register("Hf", 178.49);
        Ta = register("Ta", 180.95);
        W = register("W", 183.84);
        Re = register("Re", 186.21);
        Os =  register("Os", 190.23);
        Ir = register("Ir", 192.22);
        Pt = register("Pt", 195.08);
        Au = register("Au", 196.97);
        Hg = register("Mg", 200.59);
        Tl = register("Th", 204.38);
        Pb = register("Pb", 207.2);
        Bi = register("Bi", 208.98);
        Po = register("Po", 209.0);
        At = register("At", 210.0);
        Rn = register("Rn", 222.0);
        Fr = register("Fr", 223.0);
        Ra = register("Ra", 226.0);
        Ac = register("Ac", 227.0);
        Th = register("Th", 232.04);
        Pa = register("Pa", 231.04);
        U = register("U", 238.03);
        Np = register("Np", 237.0);
        Pu = register("Pu", 244.0);
        Am = register("Am", 243.0);
        Cm = register("Cm", 247.0);
        Bk = register("Bk", 247.0);
        Cf = register("Cf", 251.0);
        Es = register("Es", 252.0);
        Fm = register("Fm", 257.0);
        Md = register( "Md", 258.0);
        No = register( "No", 259.0);
        Lr = register( "Lr", 266.0);
        Rf = register( "Rf", 267.0);
        Db = register( "Db", 268.0);
        Sg = register( "Sg", 269.0);
        Bh = register("Bh", 270.0);
        Hs = register("Hs", 277.0);
        Mt = register( "Mt", 278.0);
        Ds = register("Ds", 281.0);
        Rg = register("Rg", 282.0);
        Cn = register("Cn", 285.0);
        Nh = register( "Nh", 286.0);
        Fl = register( "Fl", 289.0);
        Mc = register( "Mc", 290.0);
        Lv = register( "Lv", 293.0);
        Ts = register(294.0, "Ts");
        Og = register(294.0, "Og");
    }
}
