package az.content;

import arc.graphics.Color;
import mindustry.Vars;
import mindustry.type.Liquid;

public class AZLiquids {
    public static Liquid
            oxyliteLiq, spectralia,

            ssaninaBomzha;

    public static void load() {

        oxyliteLiq = new Liquid("oxylite-liq", Color.valueOf("53ad99")) {{
            viscosity = 0.65f;
            heatCapacity = 0.3f;
        }};


        spectralia = new Liquid("spectralia", Color.valueOf("f4c0f4")) {{
            viscosity = 0.65f;
            heatCapacity = 0.3f;
        }};

        ssaninaBomzha = new Liquid("cursedLiquid", Color.valueOf("96d66a")) {{
            temperature = 1.0f;
            viscosity = 0.45f;
            effect = AZStatusEffects.decomposition;
            lightColor = Color.valueOf("b4d997").a(0.4f);
            hidden = true;
        }};
    }
}
