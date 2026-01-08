
package org.nine_abyss.codec;

import org.nine_abyss.code.Code;

public interface Encoder {
    /**Encode a {@linkplain Code}*/
    void encode(Code code);

    /**Encode a prase of {@linkplain String}*/
    void encode(String code);
}
