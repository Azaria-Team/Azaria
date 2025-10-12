package azaria.gen;

import arc.Core;
import arc.graphics.g2d.TextureRegion;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Generic texture regions
 */
public class Regions {
    public static TextureRegion errorRegion;

    /**
     * Loads the texture regions
     */
    public static void load() {
        errorRegion = Core.atlas.find("azaria-error");
    }

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Outline {
        String color();

        int radius();
    }
}
