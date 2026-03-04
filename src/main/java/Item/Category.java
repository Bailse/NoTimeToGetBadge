package Item;

import java.lang.annotation.Annotation;

/**
 * this class Category
 */
public enum Category implements jdk.jfr.Category {
    /**
     * EDUCATION CLASS
     */
    EDUCATION,
    /**
     * HEALTH CLASS
     */
    HEALTH,
    /**
     * VEHICLE CLASS
     */
    VEHICLE;

    /**
     *  return new value
     *
     */

    @Override
    public String[] value() {
        return new String[0];
    }
    /**
     *  return null from interface class
     *
     */
    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
