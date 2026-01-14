
package com.bilibili.player_ix.blue_oceans.common.chemistry;

public class NoSuchElementException
extends RuntimeException {
    @java.io.Serial
    private static final long serialVersionUID = -4352292825681263488L;
    public NoSuchElementException(String message) {
        super(message);
    }

    public NoSuchElementException() {
        super("No such element!");
    }

    public NoSuchElementException(int pAtomicNumber) {
        this(noSuch(String.valueOf(pAtomicNumber)));
    }

    public NoSuchElementException(float pRelativeAtomicMass) {
        this(noSuch(String.valueOf(pRelativeAtomicMass)));
    }

    private static String noSuch(String st) {
        return "No such" + st + "element";
    }

    public synchronized Throwable getCause() {
        return this;
    }
}
