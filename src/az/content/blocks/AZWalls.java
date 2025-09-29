package az.content.blocks;

import az.content.AZItems;

import az.world.blocks.defense.wall.HealingWall;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.defense.Wall;

import static mindustry.type.ItemStack.with;

public class AZWalls {

    public static Block
            forsWall, forsWallLarge,
            compositeWall,compositeWallLarge,
            superdenseWall, superdenseWallLarge;

    public static void load() {

        //sec1
        forsWall = new Wall("fors-wall") {{
            requirements(Category.defense, with(AZItems.fors, 6));
            researchCost = with(AZItems.fors, 100);

            health = 420;
            armor = 3f;

            buildCostMultiplier = 6f;
        }};

        forsWallLarge = new Wall("fors-wall-large") {{
            requirements (Category.defense, with(AZItems.fors, 24));
            researchCost = with(AZItems.fors, 400);

            health = forsWall.health * 4;
            size = 2;
            armor = 3f;

            buildCostMultiplier = 6f;
        }};

        //later
        compositeWall = new HealingWall("composite-wall") {{
            requirements (Category.defense, with(AZItems.arside, 4, AZItems.lepera, 2));
            health = 800;
            buildCostMultiplier = 6f;

            hasLiquids = true;
            liquidCapacity = 25f;
            healAmount = 0.2f;
            placeableLiquid = true;
        }};

        compositeWallLarge = new HealingWall("composite-wall-large"){{
            requirements (Category.defense, with(AZItems.arside, 20, AZItems.lepera, 4));
            health = compositeWall.health * 4;
            size = 2;
            buildCostMultiplier = 6f;

            hasLiquids = true;
            liquidCapacity = 50f;
            healAmount = 0.8f;
            placeableLiquid = true;
        }};

        superdenseWall = new Wall("superdense-wall") {{
           requirements(Category.defense, with(AZItems.superdenseAlloy, 8));
           health = 1500;
           buildCostMultiplier = 8f;
           armor = 5f;
        }};

        superdenseWallLarge = new Wall("superdense-wall-large") {{
            size = 2;
            requirements(Category.defense, with(AZItems.superdenseAlloy, 32));
            health = superdenseWall.health * 4;
            buildCostMultiplier = 8f;
            armor = 7f;
        }};
    }
}
