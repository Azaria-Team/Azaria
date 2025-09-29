package az.content;

import mindustry.Vars;
import mindustry.game.Schematic;
import mindustry.game.Schematics;

import java.io.IOException;

public class AZLoadouts {
    public static Schematic
            basicLegion;

    public static void load(){
       // basicLegion = Schematics.readBase64("bXNjaAF4nGNgZmBmZmDJS8xNZeBOSizOTPZJTc/Mz2PgTkktTi7KLCgBcRgY2HISk1JzihmYomMZGfgzCnJ0k/OLUnVzIIoZGBhBiJGBGQBPIhPX");
        basicLegion = loadSchem("az-coreLegion");
    }

        private static Schematic loadSchem(String name) {
            Schematic s;
            try {
                s = Schematics.read(Vars.tree.get("schematics/" + name + ".msch"));
            } catch (IOException e) {
                s = null;
                e.printStackTrace();
            }
            return s;
        }

        private static Schematic loadBase64(String b64) {
            return Schematics.readBase64(b64);
        }
}
