package az.content;

import arc.graphics.Color;
import arc.struct.Seq;
import mindustry.type.Item;

public class AZItems {
    public static Item
    fors, lepera, ferbium, //TODO rename ferbuim
    serrid, arside, superdenseAlloy,
    seonium, medulla, zectral, arcanite,
    palerite, darnar;

    public static final Seq<Item> aurionaItems = new Seq<>();
    public static void load() {
        fors = new Item("fors", Color.valueOf("f3b2c1")) {{
        cost = 0.75f;
        hardness = 1;
        alwaysUnlocked = true;
        }};

        lepera = new Item("lepera", Color.valueOf("87d7bf")) {{
            cost = 0.3f;
        }};

        serrid = new Item("serrid", Color.valueOf("42373a")) {{
            cost = 1f;
        }};

        arside = new Item("arside", Color.valueOf("d9fa96")) {{
            cost = 0.6f;
        }};

        ferbium = new Item("ferbium", Color.valueOf("847bb1")) {{
            cost = 1f;
            hardness = 2;
        }};

        superdenseAlloy = new Item("superdense-alloy", Color.valueOf("313442")) {{
            cost = 1f;
        }};

        seonium = new Item("seonium", Color.valueOf("5e3143")) {{
            cost = 1f;
        }};

        medulla = new Item("medulla", Color.valueOf("4f2e6c")) {{
//            frames = 4;
            cost = 1f;
        }};

        zectral = new Item("zectral", Color.valueOf("54686a")) {{
            cost = 1f;
        }};

        arcanite = new Item("arcanite"){{
            cost =4f;
        }};

        palerite = new Item("palerite") {{
            cost = 2f;
        }};
        aurionaItems.addAll(
                fors, arside, lepera, serrid, ferbium, superdenseAlloy, seonium, medulla, zectral
        );
    }
}
