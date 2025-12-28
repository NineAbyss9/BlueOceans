
package org.nine_abyss.annotation;

import java.lang.annotation.*;

/**An {@linkplain java.lang.annotation.Annotation} that tip something is unused.*/
@NotCheckUnused
@Documented
@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.CONSTRUCTOR,
        ElementType.TYPE, ElementType.TYPE_PARAMETER, ElementType.LOCAL_VARIABLE,
        ElementType.METHOD, ElementType.PARAMETER, ElementType.PACKAGE, ElementType.TYPE_USE,
        ElementType.RECORD_COMPONENT})
@Retention(RetentionPolicy.SOURCE)
public @interface Unused {
}
