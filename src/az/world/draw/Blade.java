package az.world.draw;

import arc.Core;
import arc.graphics.g2d.TextureRegion;
import mindustry.io.JsonIO;

public class Blade {
    public final String name;
    public TextureRegion bladeRegion, blurRegion, bladeOutlineRegion, shadeRegion;
    /**
     * Rotor offsets from the unit
     */
    public float x = 0f, y = 0f;
    /**
     * Rotor Size Scaling
     */
    public float bladeSizeScl = 1, shadeSizeScl = 1;
    /**
     * Blade base movement speed
     */
    public float bladeMoveSpeed = 12;
    /**
     * Minimum Movement Speed for blade, the blade speed won't go below this value, even when dying
     */
    public float minimumBladeMoveSpeed = 0f;
    /**
     * On what bladeLayer is the Blade drawn at
     */
    public float bladeLayer = 0.1f;
    /**
     * Multiplier for blurs alpha
     */
    public float bladeBlurAlphaMultiplier = 0.9f;
    /**
     * Duplicates the initial blade and moves it on the opposite dirrection
     */
    public boolean doubleBlade = false;

    public boolean dounbleBlur = true;

    public Blade(String name) {
        this.name = name;
    }

    public static class BladeMount {
        public final Blade blade;
        public float bladeRotation;
        public float bladeBlurRotation;
        public long seed;

        public BladeMount(Blade blade) {
            this.blade = blade;
        }
    }

    public void load() {
        bladeRegion = Core.atlas.find(name);
        blurRegion = Core.atlas.find(name + "-blur");
        bladeOutlineRegion = Core.atlas.find(name + "-outline");
        shadeRegion = Core.atlas.find(name + "-shade");}

    // For mirroring
    public Blade copy() {
        return JsonIO.copy(this, new Blade(name));
    }
}
