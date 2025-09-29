package az.content;

import az.content.blocks.*;
import az.content.blocks.AZDistribution;
import mindustry.content.Fx;
import mindustry.gen.Sounds;
import mindustry.world.Block;
import mindustry.world.blocks.environment.Floor;

public class AZBlocks {
    public static Block
            //sandbox stuff
     plusFloor;

    public static void load() {
        AZCoreRelatedBlocks.load();
        AZDistribution.load();
        AZDrills.load();
        AZEnvironment.load();
        AZLiquidBlocks.load();
        AZPower.load();
        AZProduction.load();
        AZSandbox.load();
        AZTurrets.load();
        AZUnitRelatedBlocks.load();
        AZWalls.load();

        plusFloor = new Floor("plus-floor") {{
            variants = 0;
            isLiquid = true;
            walkSound = Sounds.none;
            walkEffect = Fx.none;
        }};
    }
}
