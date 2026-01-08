
package org.nine_abyss.codec;

import org.nine_abyss.code.Code;

public interface Codec extends Decoder, Encoder {
    Codec field(String st);

    void encode(Code code);

    void decode(Code code);

    void encode(final String code);

    void decode(String code);
}
