package Item;

import java.lang.annotation.Annotation;

public enum Category implements jdk.jfr.Category {

        EDUCATION, HEALTH, VEHICLE, MONEY;

    @Override
    public String[] value() {
        return new String[0];
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
