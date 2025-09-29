package az.content.blocks;

import az.content.AZAttribute;
import az.content.AZFx;
import az.content.AZItems;
import az.content.AZLiquids;
import az.graphics.AZPal;
import az.world.blocks.power.LightningPowerNode;
import az.world.blocks.power.ThermalEvaporator;
import mindustry.gen.Sounds;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.power.ConsumeGenerator;
import mindustry.world.draw.*;

import static mindustry.type.ItemStack.with;

public class AZPower {
    public static Block
    plasmaNode, plasmaNodeLarge, plasmaDistributor, plasmaDistributorLarge, oxyliteTurbine, thermalEvaporator;
    public static void load() {
        //nodes
        plasmaNode = new LightningPowerNode("plasma-node", 0) {{
            requirements(Category.power, with(AZItems.fors, 3, AZItems.lepera, 5));
            researchCost = with(AZItems.fors, 25);

            consumePowerBuffered(5000f);
            lightningRange = 15 * 8f;
            thresholdPerTile = 0.0001f / 8f;
        }};

        plasmaDistributor = new LightningPowerNode("plasma-distributor", 12) {{
            requirements(Category.power, with(AZItems.fors, 25, AZItems.lepera, 35));
            researchCost = with(AZItems.fors, 40);
            size = 2;
            consumePowerBuffered(4000f);
            lightningRange = 5 * 8f;
            laserRange = 10;
            thresholdPerTile = 0.0001f / 8f;
            //buildCostMultiplier = 8f;
        }};

        plasmaNodeLarge = new LightningPowerNode("plasma-node-large", 0) {{
            requirements(Category.power, with(AZItems.lepera, 20, AZItems.arside, 20));
            consumePowerBuffered(15000f);
            size = 2;
            lightningRange = 25 * 8f;
            thresholdPerTile = 0.0001f / 8f;
        }};

        plasmaDistributorLarge = new LightningPowerNode("plasma-distributor-large", 24) {{
            requirements(Category.power, with(AZItems.lepera, 20, AZItems.arside, 20));
            consumePowerBuffered(13000f);
            size = 3;
            lightningRange = 15 * 8f;
            laserRange = 20;
            thresholdPerTile = 0.0001f / 8f;
        }};

        //generators
        thermalEvaporator = new ThermalEvaporator("thermal-evaporator") {{
            requirements(Category.power, with(AZItems.fors, 40));
            researchCost = with(AZItems.fors, 65, AZItems.lepera, 30);
            health = 80;
            powerProduction = 0.15f;
            displayEfficiency = true;
            size = 2;
            floating = true;
            placeableLiquid = true;
            ambientSound = Sounds.hum;
            ambientSoundVolume = 0.06f;
            attribute = AZAttribute.mainlheatattr;
            generateEffect = AZFx.smokeEvaporatorBig;
            effectChance = 0.2f;
            drawer = new DrawMulti(
                    new DrawRegion(),
                    new DrawGlowRegion() {{
                        alpha = 0.7f;
                        color = AZPal.lightningNodeColor;
                        glowIntensity = 0.4f;
                        glowScale = 11f;
                    }}
            );
        }};

        oxyliteTurbine = new ConsumeGenerator("oxylite-turbine"){{
            requirements(Category.power, with(AZItems.fors, 100, AZItems.lepera, 35, AZItems.arside, 55));
            powerProduction = 2.5f;
            itemDuration = 130f;
            consumeLiquid(AZLiquids.oxyliteLiq, 5f / 60f);
            hasLiquids = true;
            size = 3;
            squareSprite = false;
            generateEffect = AZFx.smokeEvaporatorSmall;
            researchCostMultiplier = 0.5f;
            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.1f;

            consumeItems(with(AZItems.lepera, 2f));

            drawer = new DrawMulti(

                    new DrawRegion("-bottom"),
                    new DrawLiquidTile(AZLiquids.oxyliteLiq){{
                        padLeft = 2; padRight = 2; padTop = 2; padBottom = 2;
                    }},
                    new DrawDefault(),
                    new DrawBlurSpin("-rotor", 10f)
            );
        }};
    }
}
