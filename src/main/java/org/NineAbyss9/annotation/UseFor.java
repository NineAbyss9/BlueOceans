
package org.NineAbyss9.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Retention(RetentionPolicy.SOURCE)
public @interface UseFor {
    java.lang.Class<?> getClazz();
}
