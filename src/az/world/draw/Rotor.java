package az.world.draw;

import arc.*;
import arc.graphics.g2d.*;
import mindustry.io.JsonIO;

/**
 * Defines a rotor type.
 * @author younggam
 * @author GlennFolker
 */
public class Rotor{
    public final String name;

    public TextureRegion bladeRegion, rotorGlowRegion, rotorShadeRegion, topRegion;

    public float x;
    public float y;

    /** Rotor Size Scaling */
    public float rotorSizeScl = 1, rotorTopSizeScl = 1;
    /** Rotor base rotation speed */
    public float rotorSpeed = 12;
    /** Minimum Rotation Speed for rotor, the rotor speed wont go below this value, even when dying */
    public float minimumRotorSpeed = 0f;
    /** On what rotorLayer is the Rotor drawn at */
    public float rotorLayer = 0.5f;
    /** How fast does the blur region rotates, multiplied by default rotatespeed */
    public float rotorBlurSpeedMultiplier = 0.25f;
    /** Multiplier for rotor blurs alpha */
    public float rotorGlowSpeedMultiplier = 0.17f;
    public float rotorBlurAlphaMultiplier = 0.9f;
    /** Whenever to draw the rotor top sprite */
    public float rotorGlowAlphaMultiplier = 1f;
    public boolean drawRotorTop = false;

    public float rotOffset = 0f;
    public float speed = 29f;

    public int bladeCount = 4;
    public boolean mirror = false;

    public boolean rotorNotRadial = true;

    public boolean rotorRadial = false;

    public boolean drawGlow = false;


    public Rotor(String name){
        this.name = name;
    }

    public void load(){
        bladeRegion = Core.atlas.find(name);
        //bladeOutlineRegion = Core.atlas.find(name + "-rotor-outline");
        rotorShadeRegion = Core.atlas.find(name + "-shade");
        rotorGlowRegion = Core.atlas.find(name + "-glow");
        topRegion = Core.atlas.find(name + "-top");
    }

    public Rotor copy(){
        return JsonIO.copy(this, new Rotor(name));
    }

    /** Rotor entities that are mounted in units or other stuff. */
    public static class RotorMount{
        public final Rotor rotor;
        public float rotorRot;
        public float rotorShadeRot;

        public RotorMount(Rotor rotor){
            this.rotor = rotor;
        }
    }
}
