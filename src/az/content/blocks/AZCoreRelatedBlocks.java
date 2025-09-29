package az.content.blocks;

import arc.graphics.Color;
import az.content.AZItems;
import az.content.AZUnits;
import az.graphics.AZPal;
import az.world.blocks.defense.ModRadar;
import az.world.blocks.defense.NavalMine;
import az.world.blocks.defense.turret.ModRepairTurret;
import az.world.blocks.defense.turret.TowerTurret;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.blocks.storage.StorageBlock;

import static mindustry.type.ItemStack.with;

public class AZCoreRelatedBlocks {
    public static Block
            coreLegion, caseI, lampRadar, repairTurret, navalMine, towerTurret;
    public static void load() {
        //region traps
        coreLegion = new CoreBlock("core-legion") {{
            requirements(Category.effect, with(AZItems.fors, 1400, AZItems.lepera, 900));

            isFirstTier = true;
            unitType = AZUnits.gyurza;
            health = 2500;
            itemCapacity = 2500;
            size = 3;
            armor = 2f;
            alwaysUnlocked = true;
            squareSprite = false;

            unitCapModifier = 12;
        }};

        caseI = new StorageBlock("case") {{
            requirements(Category.effect, with(AZItems.fors, 100));
            size = 2;
            itemCapacity = 100;
            scaledHealth = 80;
            squareSprite = false;
            researchCostMultiplier = 0.7f;
        }};

        lampRadar = new ModRadar("lamp-radar"){{
            requirements(Category.effect, with(AZItems.fors, 50, AZItems.lepera, 20));
            outlineColor = Color.valueOf("4a4b53");
            fogRadius = 15;
            size = 2;
            discoveryTime = 60f;
            researchCost = with(AZItems.fors, 40, AZItems.lepera, 20);

            consumePower(0.3f);
        }};

        repairTurret = new ModRepairTurret("repair-turret"){{
            requirements(Category.effect, with(AZItems.fors, 90, AZItems.lepera, 40));
            health = 320;
            repairSpeed = 0.75f;
            repairRadius = 90f;
            outlineColor = AZPal.aureliaOutline;
            beamWidth = 1f;
            powerUse = 0.7f;
            size= 2;
            researchCostMultiplier = 0.5f;
        }};

        navalMine = new NavalMine("naval-mine") {{
            size = 2;
            placeableLiquid = true;
            underBullets = true;
            outlineColor = AZPal.aureliaOutline;
            requirements(Category.effect, with(AZItems.seonium, 50, AZItems.ferbium, 35));
            hasShadow = false;
            health = 1850;
            damage = 790;
            tileDamage = 1850;
        }};

        towerTurret = new TowerTurret("tower-turret") {{
            requirements(Category.effect, with(AZItems.seonium, 50, AZItems.ferbium, 35));
            consumePower(0.3f);
            size = 2;
        }};
    }
}
