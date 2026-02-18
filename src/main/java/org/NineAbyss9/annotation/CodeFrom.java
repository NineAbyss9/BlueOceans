
package org.NineAbyss9.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface CodeFrom {
    java.lang.String from();
}
