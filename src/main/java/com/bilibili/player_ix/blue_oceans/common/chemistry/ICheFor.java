
package com.bilibili.player_ix.blue_oceans.common.chemistry;

public interface ICheFor
extends IElement
{
    ChemicalFormula chemicalFormula();

    default Element getElement()
    {
        return chemicalFormula().first();
    }
}
