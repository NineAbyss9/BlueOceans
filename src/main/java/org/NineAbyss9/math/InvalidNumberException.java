
package org.NineAbyss9.math;

public class InvalidNumberException
extends RuntimeException {
    @java.io.Serial
    private static final long serialVersionUID = 1412497683426082729L;
    public InvalidNumberException() {
        super("Invalid number!");
    }

    public InvalidNumberException(String message) {
        super(message);
    }
}
