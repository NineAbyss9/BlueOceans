
package org.nine_abyss.codec;

import org.nine_abyss.code.Code;

public interface Decoder {
    /**Decode a {@linkplain Code}*/
    void decode(Code code);

    /**Decode a prase of {@linkplain String}*/
    void decode(String code);
}
