
package org.nine_abyss.code;

import org.nine_abyss.math.AbyssMath;

public class HashCode
implements Code {
    private final int base;
    private int hashCode;
    public HashCode() {
        base = 0;
    }

    public HashCode(int pBase) {
        this.base = pBase;
    }

    public HashCode run() {
        this.hashCode = AbyssMath.random.nextInt() * 31;
        return this;
    }

    public String read() {
        return String.valueOf(hashCode());
    }

    public void write(String code) {
    }

    public int strangeHashCode() {
        return new Object().hashCode();
    }

    public int hashCode() {
        return hashCode;
    }
}
