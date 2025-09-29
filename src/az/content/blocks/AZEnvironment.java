package az.content.blocks;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.math.geom.Point2;
import az.content.AZAttribute;
import az.content.AZItems;
import az.content.AZLiquids;
import az.world.blocks.environment.ModBigOverlayFloor;
import az.world.blocks.environment.ModOverlayFloor;
import az.world.blocks.environment.ModSmallOverlayFloor;
import az.world.blocks.environment.ModTallBlock;
import mindustry.content.Blocks;
import mindustry.graphics.CacheLayer;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.*;

import static mindustry.Vars.tilesize;

public class AZEnvironment {
    public static Block
        // oxylite
            oxylite, deepOxylite, abyssOxylite,serridOxylite,

    serridOrange, serridOrangeWall, serridRed, serridRedWall, serridYellow, serridYellowWall, serridStones, oldShell,
    serridicStone, serridicStoneWall,

    balsitePlates, balsiteWall, balsitePlatesOxylite, balsiteHoles, balsiteHolesSmall, balsiteRocks, balsiteRocksLarge,
    buliteWall, fortileUwite, fortileUwiteWall,
    crabStone, crabStoneWall, crabStoneBoulder, crabStoneOvergrowth, crabStoneOvergrowthBoulder, overgrowthBlob, overgrowthFruit, overgrowthToot,

        // forest

        // swamp

        // crystal

        // frozen oxylite

        // thickets

        // infected

        //boulders
        serridOrangeBoulder, serridRedBoulder, serridYellowBoulder, serridicBoulder, balsiteBoulder,
    // region WIP
    leperaBlock, leperaBlockOxylite,
    corals,

    fir, firWall, firBoulder,
    forenite, foreniteWall, foreniteBoulder,
    darkSerrid, darkSerridWall, darkSerridBoulder, darkSerridOxylite,
    forsite, forsiteWall, forsiteBoulder, forsBoulder,
    bigKust,

    lamprosMineral, lamprosMineralWall, lamprosBoulder, lamprosCrystals,
    crystalIce, crystalIceWall, crystalIceBoulder,
    spectralite, spectraliteWall, spectraliteBoulder, spectraliaWall,
    nerephyte, nerephyteWall, nerephyteBoulder,
    spectralia,

    huitaRock, huitaRockWall, huitaBoulder,
    volcanicSerrid, volcanicSerridWall, volcanicSerridBoulder,


    //endregion WIP
    //prop
    ancientSus,

    //TODO
    //ores
    forsOre, ferbiumOre, forsRock,
            leperaGrowth;



    public static void load() {

        //region oxylite

        // OXYLITE
        oxylite = new Floor("oxylite-normal", 4) {{
            isLiquid = true;
            liquidDrop = AZLiquids.oxyliteLiq;
            cacheLayer = CacheLayer.water;
            liquidMultiplier = 1f;

            albedo = 0.7f;
            mapColor = Color.valueOf("50a9a8");
            attributes.set(AZAttribute.mainlheatattr, 0.25f);
        }};
        deepOxylite = new Floor("oxylite-deep", oxylite.variants) {{
            isLiquid = true;
            liquidDrop = AZLiquids.oxyliteLiq;
            cacheLayer = oxylite.cacheLayer;
            liquidMultiplier = 1.75f;

            albedo = oxylite.albedo;
            drownTime = 140f;
        }};
        abyssOxylite = new Floor("oxylite-abyss", oxylite.variants) {{
            isLiquid = true;
            cacheLayer = oxylite.cacheLayer;
            liquidDrop = AZLiquids.oxyliteLiq;
            albedo = oxylite.albedo;
        }};

        /// SERRID
        serridOrange = new Floor("serrid-orange") {{ variants = 5;
            attributes.set(AZAttribute.serridAttr, 1);
        }};
        serridOrangeWall = new StaticWall("serrid-orange-wall") {{ variants = 3;
            serridOrange.asFloor().wall = this;
        }};
        serridRed = new Floor("serrid-red", 5) {{
            attributes.set(AZAttribute.serridAttr, 1);
        }};
        serridRedWall = new StaticWall("serrid-red-wall") {{ variants = 3;
            serridOrange.asFloor().wall = this;
        }};;
        serridYellow = new Floor("serrid-yellow", 5) {{
            attributes.set(AZAttribute.serridAttr, 1);
        }};
        serridYellowWall = new StaticWall("serrid-yellow-wall") {{ variants = 3;

            serridOrange.asFloor().wall = this;
        }};
        serridOxylite = new ShallowLiquid("serrid-oxylite") {{ variants = 4;
            isLiquid = true;
            liquidDrop = AZLiquids.oxyliteLiq;
            cacheLayer = oxylite.cacheLayer;

            albedo = oxylite.albedo;
            supportsOverlay = true;
            mapColor = Color.valueOf("82827e");
        }};

        serridStones = new Prop("serrid-stones") {{
            variants = 2;
            solid = true;
            hasShadow = false;
        }};

        oldShell = new ModTallBlock("old-shell"){{
            variants = 2;
            clipSize = 112f;
        }};

        serridicStone = new Floor("serridic-stone", 4);
        serridicStoneWall = new StaticWall("serridic-stone-wall") {{
            variants = 3;
            serridicStone.asFloor().wall = this;
        }};

        // BALSITE
        balsitePlates = new Floor("balsite-plates", 8);
        balsiteWall = new StaticWall("balsite-wall") {{
            variants = 3;
            balsitePlates.asFloor().wall = this;
        }};
        balsitePlatesOxylite = new ShallowLiquid("balsite-plates-oxylite") {{ variants = 4;
            albedo = 0.9f;
            cacheLayer = oxylite.cacheLayer;
            liquidDrop = AZLiquids.oxyliteLiq;
            supportsOverlay = true;
            mapColor = Color.valueOf("4d7578");
        }};
        //TODO fix display
        balsiteHoles = new ModBigOverlayFloor("balsite-holes") {{
            variants = 2;
            parent = blendGroup = balsitePlates;
            drownTime = 999999999f;
        }};
        //TODO make indestructible and ddisable shadow
        balsiteHolesSmall = new ModSmallOverlayFloor("balsite-holes-small") {{
            variants = 4;
            parent = blendGroup = balsitePlates;
        }};
        //TODO player cannot build on this
        balsiteRocks = new Prop("balsite-rocks") {{
            variants = 2;
            solid = true;
            hasShadow = false;
        }};
        //TODO player cannot build on this
        balsiteRocksLarge = new ModTallBlock("balsite-rocks-large") {{
            variants = 3;
            clipSize = 96f;
            rotationRand = 0f;
        }};

        // CRAB STONE
        crabStone = new Floor("crab-stone", 8);
        crabStoneWall = new StaticWall("crab-stone-wall") {{ variants = 3;
            crabStone.asFloor().wall = this;
        }};
        crabStoneOvergrowth = new Floor("crab-stone-overgrowth") {{ variants = 12; }};

        // OXYLITE MINERALS
        buliteWall = new StaticWall("bulite-wall") {{ variants = 3; }};
        fortileUwite = new Floor("fortile-uwite", 4);
        fortileUwiteWall = new StaticWall("fortile-uwite-wall") {{ variants = 3; }};

        // BOULDERS
        serridOrangeBoulder = new Prop("serrid-orange-boulder") {{ variants = 3;
            serridOrange.asFloor().decoration = this;
        }};
        serridRedBoulder = new Prop("serrid-red-boulder") {{ variants = 3;
            serridRed.asFloor().decoration = this;
        }};
        serridYellowBoulder = new Prop("serrid-yellow-boulder") {{ variants = 3;
            serridYellow.asFloor().decoration = this;
        }};
        serridicBoulder = new Prop("serridic-boulder") {{ variants = 3;
            serridicStone.asFloor().decoration = this;
        }};
        crabStoneBoulder = new Prop("crab-stone-boulder") {{ variants = 3;
            crabStone.asFloor().decoration = this;
        }};
        crabStoneOvergrowthBoulder = new Prop("crab-stone-overgrowth-boulder") {{ variants = 2;
            crabStoneOvergrowth.asFloor().decoration = this;
        }};
        balsiteBoulder = new Prop("balsite-boulder") {{ variants = 3;
            balsitePlates.asFloor().decoration = this;
        }};
        overgrowthBlob = new TreeBlock("overgrowth-blob"){{
            variants = 2;
            clipSize = 96f;
        }};
        overgrowthFruit = new ModTallBlock("overgrowth-fruit"){{
            variants = 1;
            clipSize = 96f;
        }};

        overgrowthToot = new ModTallBlock("overgrowth-root"){{
            variants = 2;
            clipSize = 96f;
        }};



        //endregion oxylite













        //region Auriona
        //region sea-biome
        //oxylite














        //lepera blocks
        leperaBlock = new Floor("lepera-block") {{
            variants = 4;
        }};
        leperaBlockOxylite = new Floor("lepera-block-liquid") {{
            variants = 4;
            cacheLayer = CacheLayer.water;
            liquidDrop = AZLiquids.oxyliteLiq;
            isLiquid = true;
        }};





        //serridic rock
        //decorations
        corals = new ModBigOverlayFloor("coral") {{
            variants = 9;
            cacheLayer = CacheLayer.water;
            parent = blendGroup = oxylite;
            isLiquid = true;
        }};
        //endregion sea-biome

        //region forest-biome
        //fir
        fir = new Floor("fir") {{
            mapColor = Color.valueOf("222625");
            variants = 4;
        }};
        firWall = new StaticWall("fir-wall") {{
            variants = 3;
            fir.asFloor().wall = this;
            mapColor = Color.valueOf("54685e");
        }};
        firBoulder = new Prop("fir-boulder") {{
            variants = 3;
            fir.asFloor().decoration = this;
        }};
        //forenite
        forenite = new Floor("forenite") {{
            mapColor = Color.valueOf("313a3b");
            variants = 4;
        }};
        foreniteWall = new StaticWall("forenite-wall") {{
            variants = 3;
            forenite.asFloor().wall = this;
            mapColor = Color.valueOf("869985");
        }};
        foreniteBoulder = new Prop("forenite-boulder") {{
            variants = 3;
            forenite.asFloor().decoration = this;
        }};
        //forsite
        forsite = new Floor("forsite-plates") {{
            variants = 4;
        }};
        forsiteWall = new StaticWall("forsite-wall") {{
            variants = 3;
            forsite.asFloor().wall = this;
        }};
        forsiteBoulder = new Prop("forsite-boulder") {{
            variants = 2;
            forsite.asFloor().decoration = this;
        }};
        forsBoulder = new Prop("fors-boulder") {{
            variants = 2;
        }};
        //darkSerrid
        darkSerrid = new Floor("dark-serrid") {{
            variants = 4;
            attributes.set(AZAttribute.serridAttr, 1);
        }} ;
        darkSerridWall = new StaticWall("dark-serrid-wall") {{
            variants = 3;
            darkSerrid.asFloor().wall = this;
        }};
        darkSerridBoulder = new Prop("dark-serrid-boulder") {{
            variants = 3;
            darkSerrid.asFloor().decoration = this;
        }};
        darkSerridOxylite = new ShallowLiquid("dark-serrid-oxylite") {{
            variants = 4;
            liquidDrop = AZLiquids.oxyliteLiq;
            cacheLayer = CacheLayer.water;
            liquidMultiplier = 1f;
            isLiquid = true;
            albedo = 0.7f;
            mapColor = Color.valueOf("395f6a");
        }};
        //endregion forest-biome

        //region crystal-biome
        //lamprosMineral
        lamprosMineral = new Floor("lampros-mineral") {{
            variants = 4;
        }};
        lamprosCrystals = new Floor("lampros-crystals") {{
            variants = 4;
        }};
        lamprosMineralWall = new StaticWall("lampros-mineral-wall") {{
            variants = 3;
            lamprosMineral.asFloor().wall = this;
        }};
        lamprosBoulder = new Prop("lampros-boulder") {{
            variants = 3;
            lamprosMineral.asFloor().decoration = this;
        }};
        //crystalIce
        crystalIce = new Floor("crystal-ice") {{
            variants = 4;
        }};
        crystalIceWall = new StaticWall("crystal-ice-wall") {{
            variants = 2;
            crystalIce.asFloor().wall = this;
        }};
        crystalIceBoulder = new Prop("crystal-ice-boulder") {{
            variants = 3;
            crystalIce.asFloor().decoration = this;
        }};
        //spectralite
        spectralite = new Floor("spectralite") {{
            variants = 4;

        }};
        spectraliaWall = new StaticWall("spectralite-wall") {{
            variants = 2;
        }};
        spectraliteBoulder = new Prop("spectralite-boulder") {{
            variants = 3;
            spectralite.asFloor().decoration = this;
        }};
        //spectralia
        spectraliaWall = new StaticWall("spectralia-wall") {{
            variants = 2;
            spectralite.asFloor().wall = this;
        }};
        //nerephyte
        nerephyte = new Floor("nerephyte") {{
            variants = 5;
        }};
        nerephyteWall = new StaticWall("nerephyte-wall") {{
            variants = 3;
            nerephyte.asFloor().decoration = this;
        }};
        nerephyteBoulder  = new Prop("nerephyte-boulder") {{
            variants = 3;
            nerephyte.asFloor().decoration = this;
        }};
        //endregion crystal-biome

        //region volcanic-biome
        //huitaRock
        huitaRock = new Floor("huita-rock") {{
            //itemDrop = AZItems.volcanicSerrid;
            variants = 4;
        }};
        huitaRockWall = new StaticWall("huita-rock-wall") {{
            variants = 3;
            huitaRock.asFloor().wall = this;
        }};
        huitaBoulder = new Prop("huita-boulder") {{
            variants = 2;
            huitaRock.asFloor().decoration = this;
        }};
        //volcanicSerrid
        volcanicSerrid = new Floor("volcanic-serrid") {{
            variants = 4;
            attributes.set(AZAttribute.serridAttr, 1f);
        }};
        volcanicSerridWall = new StaticWall("volcanic-serrid-wall") {{
            variants = 3;
            volcanicSerrid.asFloor().decoration = this;
        }};
        volcanicSerridBoulder = new Prop("volcanic-serrid-boulder") {{
            variants = 3;
            volcanicSerrid.asFloor().decoration = this;
        }};
        //spectralia
        spectralia = new Floor("spectralia") {{
            variants = 4;
            isLiquid = true;
            liquidDrop = AZLiquids.spectralia;
            cacheLayer = CacheLayer.arkycite;
            attributes.set(AZAttribute.mainlheatattr, 0.25f);
        }};
        //endregion volcanic-biome




        //region decorations
        ancientSus = new Prop("ancient-sus") {{
            breakable = false;
            size = 1;
            solid = true;

        }};
        //endregion decorations

        //region ores
        forsOre = new OreBlock(AZItems.fors){{
            oreDefault = true;
            oreThreshold = 0.81f;
            oreScale = 23.47619f;
        }};
        ferbiumOre = new OreBlock(AZItems.ferbium){{
            //itemDrop = AZItems.ferbium;
            oreDefault = true;
            oreThreshold = 0.81f;
            oreScale = 23.47619f;
            variants = 2;
        }};
        forsRock = new ModOverlayFloor("fors-rock") {{
            parent = blendGroup = forsite;
            variants = 2;
            attributes.set(AZAttribute.forsattr, 1f);
        }};

        //todo smth with that ore
        leperaGrowth = new ModOverlayFloor("lepera-growth") {{
            parent = blendGroup = oxylite;
            variants = 2;
            cacheLayer = CacheLayer.water;
            attributes.set(AZAttribute.leperaattr, 1f);
            isLiquid = true;
        }};
        //endregion ores
        //endregion Auriona
        ((ShallowLiquid)balsitePlatesOxylite).set(oxylite, balsitePlates);
        ((ShallowLiquid)darkSerridOxylite).set(oxylite, darkSerrid);
    }
}
