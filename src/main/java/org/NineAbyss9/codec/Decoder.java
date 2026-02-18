
package org.NineAbyss9.codec;

import org.NineAbyss9.code.Code;

public interface Decoder {
    /**Decode a {@linkplain Code}*/
    void decode(Code code);

    /**Decode a prase of {@linkplain String}*/
    void decode(String code);
}
