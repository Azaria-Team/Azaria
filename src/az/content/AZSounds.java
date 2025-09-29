package az.content;

import arc.Core;
import arc.assets.AssetDescriptor;
import arc.assets.loaders.SoundLoader;
import arc.audio.Sound;
import mindustry.Vars;

public class AZSounds {
    public static Sound placeholder,
            thunder_blast = new Sound();

    protected static Sound loadSound(String fileName) {
        String name = "sounds/" + fileName;
        String path = Vars.tree.get(name + ".ogg").exists() ? name + ".ogg" : name + ".mp3";

        Sound sound = new Sound();

        AssetDescriptor<?> desc = Core.assets.load(path, Sound.class, new SoundLoader.SoundParameter(sound));
        desc.errored = Throwable::printStackTrace;

        return sound;
    }
    public static void load() {
        if (Vars.headless) return;
        thunder_blast = loadSound("thunder_blast");
    }
}
