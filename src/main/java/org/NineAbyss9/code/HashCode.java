
package org.NineAbyss9.code;

import org.NineAbyss9.math.AbyssMath;

public class HashCode
implements Code {
    private final int base;
    private int hashCode;
    public HashCode() {
        base = AbyssMath.random.nextInt();
    }

    public HashCode(int pBase) {
        this.base = pBase;
    }

    public HashCode run() {
        this.hashCode = base * 31;
        return this;
    }

    public String read() {
        return String.valueOf(hashCode());
    }

    public void write(String code) {
        this.hashCode = Integer.valueOf(code);
    }

    public int strangeHashCode() {
        return new Object().hashCode();
    }

    public int hashCode() {
        return hashCode;
    }
}
