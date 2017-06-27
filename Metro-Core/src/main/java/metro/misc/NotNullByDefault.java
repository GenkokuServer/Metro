package metro.misc;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(value = CLASS)
@Target(value = { PACKAGE, TYPE, METHOD, CONSTRUCTOR, FIELD, LOCAL_VARIABLE })
public @interface NotNullByDefault {}