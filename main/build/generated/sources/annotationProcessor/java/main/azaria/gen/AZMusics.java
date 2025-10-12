package azaria.gen;

import arc.audio.Music;
import mindustry.Vars;

public final class AZMusics {
    private AZMusics() {
        throw new AssertionError();
    }

    protected static Music load(String name) {
        return Vars.tree.loadMusic(name);
    }

    public static void load() {
        if(Vars.headless) return;
    }
}
