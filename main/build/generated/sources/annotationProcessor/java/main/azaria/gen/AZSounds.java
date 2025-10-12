package azaria.gen;

import arc.audio.Sound;
import mindustry.Vars;

public final class AZSounds {
    public static Sound thunderBlast = new Sound();

    private AZSounds() {
        throw new AssertionError();
    }

    protected static Sound load(String name) {
        return Vars.tree.loadSound("sounds/" + name);
    }

    public static void load() {
        if(Vars.headless) return;
        thunderBlast = load("thunder_blast");
    }
}
