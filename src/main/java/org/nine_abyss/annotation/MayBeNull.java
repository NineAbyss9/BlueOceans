
package org.nine_abyss.annotation;

import javax.annotation.Nonnull;
import javax.annotation.meta.When;

@Nonnull(when = When.MAYBE)
public @interface MayBeNull {
}
