
package org.NineAbyss9.annotation;

import javax.annotation.Nonnull;
import javax.annotation.meta.When;
import java.lang.annotation.Documented;

@Documented
@Nonnull(
        when = When.NEVER
)
public @interface AlwaysNull {
}
