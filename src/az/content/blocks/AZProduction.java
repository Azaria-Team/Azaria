package az.content.blocks;

import az.content.AZItems;
import az.graphics.AZPal;
import az.world.draw.DrawCrasideSmelt;
import az.world.draw.MultiDrawFlame;
import mindustry.content.Fx;
import mindustry.gen.Sounds;
import mindustry.graphics.Layer;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.draw.DrawDefault;
import mindustry.world.draw.DrawFlame;
import mindustry.world.draw.DrawMulti;
import mindustry.world.draw.DrawRegion;

import static mindustry.type.ItemStack.with;

public class AZProduction {
    public static Block
    crasideBrewer, heavyDutyCrucible;

    public static void load() {

        //TODO
        crasideBrewer = new GenericCrafter("craside-brewer") {{
            requirements(Category.crafting, with(AZItems.fors, 60, AZItems.lepera, 35));
            outputItem = new ItemStack(AZItems.arside, 2);
            consumeItems(with(AZItems.lepera, 1, AZItems.serrid, 1));
            craftTime = 110f;
            //ambientSound = Sounds.hum;
            ambientSoundVolume = 0.07f;
            size = 3;
            craftEffect = Fx.none;
            consumePower(1f);
            researchCostMultiplier = 0.2f;
            squareSprite = false;
            ambientSound = Sounds.smelter;
            drawer = new DrawMulti(
                    new DrawRegion("-bottom"){{
                        layer = Layer.block;
                    }},
                    new DrawRegion(){{
                        layer = Layer.block + 0.1f;
                    }},
                    new DrawRegion("-top"){{
                        layer = Layer.block + 0.2f;
                    }},
                    new DrawCrasideSmelt(){{
                    }}
            );
        }};

        heavyDutyCrucible = new GenericCrafter("heavy-duty-crucible") {{
            requirements(Category.crafting, with(AZItems.fors, 200, AZItems.lepera, 150, AZItems.arside, 100));
            outputItem = new ItemStack(AZItems.superdenseAlloy, 4);
            consumeItems(with(AZItems.fors, 2, AZItems.serrid, 3, AZItems.ferbium, 1));
            craftTime = 160f;
            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.1f;
            size = 4;
            // craftEffect = AZFx.crasideBrewerSmoke;
            consumePower(1f);
            drawer = new DrawMulti(
                    new DrawDefault(),
                    new MultiDrawFlame(){{
                        flamePoints(
                                new FlamePoint(63.5f/128f,33.4f/128f),
                                new FlamePoint(63.5f/128f,93.5f/128f),
                                new FlamePoint(43.5f/128f,83.5f/128f),
                                new FlamePoint(43.5f/128f,43.5f/128f),
                                new FlamePoint(83.5f/128f,83.5f/128f),
                                new FlamePoint(83.5f/128f,43.5f/128f),
                                new FlamePoint(33.5f/128f,63.5f/128f),
                                new FlamePoint(93.5f/128f,63.5f/128f)

                        );
                        flameRadius = 1.7f;
                        flameRadiusIn = 0.5f;
                        flameRadiusScl = 3f;
                        flameRadiusMag = 0.5f;
                        flameRadiusInMag = 0.5f;
                        flameColor = AZPal.superdenseBullet;
                    }}
            );
            // researchCostMultiplier = 0.2f;
            squareSprite = false;
        }};
    }
}
