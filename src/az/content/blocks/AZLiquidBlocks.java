package az.content.blocks;

import az.content.AZItems;
import az.graphics.AZPal;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.liquid.*;
import mindustry.world.blocks.production.Pump;

import static mindustry.type.ItemStack.with;

public class AZLiquidBlocks {
    public static Block
            impulsePump, liquidPipe, liquidPipeJunction, liquidCanister, liquidPipeRouter, pipeBridgeConduit,
            advancedPump;
    public static void load() {
        impulsePump = new Pump("impulse-pump"){{
            requirements(Category.liquid, with(AZItems.fors, 90, AZItems.arside, 70, AZItems.ferbium, 30));

            squareSprite = false;
            pumpAmount = 10f / 60f;
            liquidCapacity = 140f;
            researchCostMultiplier = 0.3f;
            size = 2;
            consumePower(1.5f / 3f);
        }};

        liquidPipe = new ArmoredConduit("liquid-pipe"){{
            requirements(Category.liquid, with(AZItems.fors, 1, AZItems.ferbium, 1));
            botColor = AZPal.aureliaOutline;
            leaks = true;
            liquidCapacity = 25f;
            liquidPressure = 1.1f;
            health = 300;
            researchCostMultiplier = 0.3f;
            underBullets = true;
        }};
        liquidPipeJunction = new LiquidJunction("liquid-pipe-junction"){{
            requirements(Category.liquid, with(AZItems.fors, 3, AZItems.ferbium, 5));
            buildCostMultiplier = 3f;
            health = 320;
            ((Conduit)liquidPipe).junctionReplacement = this;
            researchCostMultiplier = 0.3f;
            solid = false;
            underBullets = true;
        }};

        pipeBridgeConduit = new LiquidBridge("pipe-bridge-conduit"){{
            requirements(Category.liquid, with(AZItems.fors, 3, AZItems.ferbium, 8));
            range = 5;
            bridgeWidth = 8F;
            hasPower = false;
            researchCostMultiplier = 0.3f;
            underBullets = true;
            arrowSpacing = 6f;

            ((Conduit)liquidPipe).rotBridgeReplacement = this;
        }};

        liquidPipeRouter = new LiquidRouter("liquid-pipe-router"){{
            requirements(Category.liquid, with(AZItems.fors, 3, AZItems.ferbium, 5));
            liquidCapacity = 35f;
            liquidPadding = 1.0f;
            researchCostMultiplier = 0.3f;
            underBullets = true;
            solid = false;
        }};

        liquidCanister = new LiquidRouter("liquid-canister") {{
            requirements(Category.liquid, with(AZItems.fors, 15, AZItems.ferbium, 35));
            liquidCapacity = 3545f;
            size = 2;
            liquidPadding = 1.0f;
            researchCostMultiplier = 0.3f;
            underBullets = true;
        }};

        advancedPump = new Pump("advanced-pump"){{
            requirements(Category.liquid, with(AZItems.fors, 90, AZItems.arside, 70, AZItems.superdenseAlloy, 30));
            squareSprite = true;
            pumpAmount = 10f / 60f;
            liquidCapacity = 140f;
            researchCostMultiplier = 0.3f;
            size = 3;
            consumePower(1.5f / 3f);
        }};
    }
}
