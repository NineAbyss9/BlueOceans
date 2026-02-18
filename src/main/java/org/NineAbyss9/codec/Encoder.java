
package org.NineAbyss9.codec;

import org.NineAbyss9.code.Code;

public interface Encoder {
    /**Encode a {@linkplain Code}*/
    void encode(Code code);

    /**Encode a prase of {@linkplain String}*/
    void encode(String code);
}
