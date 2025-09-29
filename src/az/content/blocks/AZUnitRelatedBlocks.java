package az.content.blocks;

import arc.util.Time;
import az.content.AZItems;
import az.content.AZUnits;
import mindustry.type.Category;
import mindustry.type.UnitType;
import mindustry.world.Block;
import mindustry.world.blocks.units.Reconstructor;
import mindustry.world.blocks.units.UnitFactory;

import static mindustry.type.ItemStack.with;

public class AZUnitRelatedBlocks {
    public static Block
            angelsharkFabricator, vectorFabricator, unmakerFabricator,
            angelsharkRefabricator, vectorRefabricator, unmakerRefabricator;

    public static void load() {
        angelsharkFabricator = new UnitFactory("angelshark-fabricator"){{
            requirements(Category.units, with(AZItems.fors, 200, AZItems.lepera, 140, AZItems.arside, 100));
            size = 3;
            configurable = false;
            squareSprite = false;
            plans.add(new UnitPlan(AZUnits.angelshark, 15f * Time.toSeconds, with(AZItems.fors, 40, AZItems.lepera, 30, AZItems.arside, 10)));
            researchCost = with(AZItems.fors, 180, AZItems.lepera, 100, AZItems.arside, 40);
            regionSuffix = "-az";
            fogRadius = 3;
            consumePower(2f);
        }};

        vectorFabricator = new UnitFactory("vector-fabricator"){{
            requirements(Category.units, with(AZItems.fors, 200, AZItems.arside, 100));
            size = 3;
            configurable = false;
            squareSprite = false;
            plans.add(new UnitPlan(AZUnits.vector, 10f * Time.toSeconds, with(AZItems.fors, 50, AZItems.arside, 30)));
            researchCost = with(AZItems.fors, 220, AZItems.arside, 80);
            regionSuffix = "-az";
            fogRadius = 3;
            researchCostMultiplier = 0.2f;
            consumePower(2f);
        }};
        unmakerFabricator = new UnitFactory("unmaker-fabricator") {{
            requirements(Category.units, with(AZItems.fors, 200, AZItems.arside, 100));
            size = 3;
            configurable = false;
            squareSprite = false;
            plans.add(new UnitPlan(AZUnits.unmaker, 10f * Time.toSeconds, with(AZItems.fors, 50, AZItems.arside, 30)));
            researchCost = with(AZItems.fors, 220, AZItems.arside, 80);
            regionSuffix = "-az";
            fogRadius = 3;
            consumePower(2f);
        }};

        vectorFabricator = new Reconstructor("vector-refabricator") {{
            requirements(Category.units, with(AZItems.ferbium, 350));

            size = 3;
            consumePower(2.75f);
            consumeItems(with(AZItems.ferbium, 120, AZItems.ferbium, 50));

            constructTime = 35f * 45f;
            upgrades.addAll(
                    new UnitType[] {AZUnits.vector, AZUnits.zephyr}
            );
        }};

        angelsharkRefabricator = new Reconstructor("angelshark-refabricator") {{
            requirements(Category.units, with(AZItems.ferbium, 350));

            size = 3;
            consumePower(2.75f);
            consumeItems(with(AZItems.ferbium, 120, AZItems.ferbium, 50));

            constructTime = 35f * 45f;
            upgrades.addAll(
                    new UnitType[] {AZUnits.angelshark, AZUnits.glaucus}
            );
        }};

        unmakerRefabricator = new Reconstructor("unmaker-refabricator") {{
            requirements(Category.units, with(AZItems.ferbium, 350));

            size = 3;
            consumePower(2.75f);
            consumeItems(with(AZItems.ferbium, 120, AZItems.ferbium, 50));

            constructTime = 35f * 45f;
            upgrades.addAll(
                    new UnitType[] {AZUnits.unmaker, AZUnits.eliminator}
            );
        }};
    }
}
