
package com.bilibili.player_ix.blue_oceans.common.physic;

public interface Electric {
    /**For cells, this will return the volt value,
     *for conductor, this will return the current volt value.*/
    float getVolt();

    /**Gets the current.*/
    float getA();
}
