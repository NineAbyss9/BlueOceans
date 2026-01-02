
package org.nine_abyss.annotation;

import java.lang.annotation.*;

/**Use this {@linkplain java.lang.annotation.Annotation} to stop warn {@code unchecked}*/
@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.CONSTRUCTOR,
        ElementType.TYPE, ElementType.TYPE_PARAMETER, ElementType.LOCAL_VARIABLE,
        ElementType.METHOD, ElementType.PARAMETER, ElementType.PACKAGE, ElementType.TYPE_USE,
        ElementType.RECORD_COMPONENT})
@Retention(RetentionPolicy.SOURCE)
@SuppressWarnings("unused")
public @interface NotCheckUnused {
}
