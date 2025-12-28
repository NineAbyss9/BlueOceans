
package org.nine_abyss.bug;

/**The class {@linkplain Bug} and its subclasses are a form of {@linkplain Throwable}.
 * <p>
 *They can give you some information so you should not catch them.*/
public class Bug
extends Throwable {
    public Bug() {
        super();
    }

    public Bug(String message) {
        super(message);
    }
}
