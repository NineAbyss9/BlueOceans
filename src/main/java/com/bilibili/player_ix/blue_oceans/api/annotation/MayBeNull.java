
package com.bilibili.player_ix.blue_oceans.api.annotation;

import javax.annotation.Nonnull;
import javax.annotation.meta.TypeQualifierNickname;
import javax.annotation.meta.When;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Nonnull(when = When.MAYBE)
@Retention(RetentionPolicy.RUNTIME)
@TypeQualifierNickname
public @interface MayBeNull {
}
