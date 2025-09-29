package az.content;

import arc.graphics.Color;
import arc.math.Interp;
import arc.math.Mathf;
import arc.math.geom.Vec3;
import arc.struct.Seq;
import az.content.blocks.AZCoreRelatedBlocks;
import az.content.blocks.AZEnvironment;
import az.maps.ColorPass;
import az.maps.HeightPass;
import az.maps.generators.AurionaPlanetGenerator;
import az.maps.generators.TestGenerator;
import mindustry.content.*;
import mindustry.game.Team;
import mindustry.graphics.g3d.*;
import mindustry.type.*;
import mindustry.ui.dialogs.PlanetDialog;

import static az.content.AZItems.aurionaItems;
import static mindustry.content.Planets.erekir;
import static mindustry.content.Planets.serpulo;

public class AZPlanets {
    public static Planet aStar, bStar, auriona;

    public static void load() {
        PlanetDialog.debugSelect = true;
        aStar = new Planet("aStar", null, 6f){{
            bloom = true;
            accessible = false;
            rotateTime = 20f * 60f;

            meshLoader = () -> new SunMesh(
                    this, 4,
                    5, 0.3, 1.7, 1.2, 1,
                    1.1f,
                    Color.valueOf("e6d3ff"),
                    Color.valueOf("e6d3ff"),
                    Color.valueOf("bfa1ff"),
                    Color.valueOf("bfa1ff"),
                    Color.valueOf("8686ff"),
                    Color.valueOf("6e6eed")
            );
        }};

        bStar = new Planet("bStar", aStar, 4f){{
            bloom = true;
            accessible = false;
            orbitRadius = 130f;
            visible = true;
            drawOrbit = false;
            orbitTime = 20 * 60f * 60f;
            rotateTime = 20f * 60f;

            meshLoader = () -> new SunMesh(
                    this, 4,
                    5, 0.3, 1.7, 1.2, 1,
                    1.1f,
                    Color.valueOf("ffc87c"),
                    Color.valueOf("ffc87c"),
                    Color.valueOf("ffa853"),
                    Color.valueOf("ffa853"),
                    Color.valueOf("ff593d"),
                    Color.valueOf("ff3d40")
            );
        }};

        auriona = new Planet("auriona", aStar, 1.2f, 3){{
            meshLoader = () -> new HexMesh(this, 7);
            cloudMeshLoader = () -> new MultiMesh( // 81ffd7 old color > 5de7a3
                    new HexSkyMesh(this, 11, 0.25f, 0.14f, 6, Color.valueOf("5de7a3").a(0.75f), 2, 0.45f, 0.87f, 0.38f),
                    new HexSkyMesh(this, 2, 0.6f, 0.17f, 6, Color.valueOf("c1f4e4").a(0.75f), 2, 0.45f, 1f, 0.43f)
            );

            launchCapacityMultiplier = 0.5f;
            sectorSeed = 2;
            orbitTime = 2 * 60f * 60f;
            rotateTime = 34f * 60f;
            orbitSpacing = 10;

            allowLaunchToNumbered = false;
            //allowWaves = true;
            //allowWaveSimulation = true;
            //allowSectorInvasion = true;
            //allowLaunchSchematics = false;
            iconColor = Color.valueOf("24b95a");
            atmosphereColor = Color.valueOf("24b95a");
			defaultCore = AZCoreRelatedBlocks.coreLegion;
            atmosphereRadIn = 0.02f;
            atmosphereRadOut = 0.3f;
            startSector = 15;
            orbitRadius = 60f;
            clearSectorOnLose = true;
            alwaysUnlocked = true;
            landCloudColor = Color.valueOf("24b95a");
            serpulo.hiddenItems.addAll(aurionaItems).removeAll(Items.serpuloItems);
            erekir.hiddenItems.addAll(aurionaItems).removeAll(Items.erekirItems);
            itemWhitelist = aurionaItems;
           // hiddenItems.addAll(Items.erekirItems).removeAll(aurionaItems);
          //  hiddenItems.addAll(Items.serpuloItems).removeAll(aurionaItems);
            //hiddenItems.addAll(Items.serpuloItems).addAll(Items.erekirItems).addAll(Items.erekirOnlyItems).removeAll(aurionaItems);
            ruleSetter = r -> {
                r.waveTeam = Team.green;
                r.placeRangeCheck = false;
                r.showSpawns = true;
                r.fog = true;
                r.staticFog = true;
                r.lighting = true;
                r.coreDestroyClear = true;
                r.onlyDepositCore = false;
                unlockedOnLand.add(AZCoreRelatedBlocks.coreLegion);
            };
            generator = new TestGenerator() {{
                baseHeight = 0;
                baseColor = AZEnvironment.oxylite.mapColor;

                heights.add(new HeightPass.NoiseHeight() {{
                    offset.set(5000, 10000, -10000);
                    octaves = 8;
                    persistence = 0.55;
                    magnitude = 1.2f;
                    scale = 0.4;
                    heightOffset = -0.7f;
                }});


                Mathf.rand.setSeed(2);
                Seq<HeightPass> mountains = new Seq<>();
                for (int i = 0; i < 2; i++) {
                    mountains.add(new HeightPass.DotHeight() {{
                        dir.setToRandomDirection().y = Mathf.random(3f, 2f);
                        min = -1f;
                        magnitude = 0.03f;
                        interp = Interp.exp10In;
                    }});
                }

                for (int i = 0; i < 1; i++) {
                    mountains.add(new HeightPass.DotHeight() {{
                        dir.setToRandomDirection().y = Mathf.random(6f, 2f);
                        min = -1f;
                        magnitude = 0.2f;

                        interp = Interp.exp10In;
                    }});
                }

                for (int i = 0; i < 1; i++) {
                    mountains.add(new HeightPass.DotHeight() {{
                        dir.setToRandomDirection().y = Mathf.random(10f, 2f);
                        min = -1f;
                        magnitude = 1.1f;
                        interp = Interp.exp10In;
                    }});
                }
                heights.add(new HeightPass.MultiHeight(mountains, HeightPass.MultiHeight.MixType.max, HeightPass.MultiHeight.Operation.add));

                heights.add(new HeightPass.ClampHeight(0f, 0.9f));

                colors.addAll(
                        new ColorPass.NoiseColorPass() {{
                            seed = 5;
                            scale = 8;
                            persistence = 0.5;
                            octaves = 6;
                            magnitude = 1f;
                            min = 0.1f;
                            max = 0.5f;
                            out = AZEnvironment.serridRed.mapColor;
                            // offset.set(1500f, 300f, -500f);
                        }},

                        new ColorPass.NoiseColorPass() {{
                            seed = 5;
                            scale = 3;
                            persistence = 0.5;
                            octaves = 5;
                            magnitude = 1f;
                            min = 0.1f;
                            max = 0.5f;
                            out = AZEnvironment.serridYellow.mapColor;
                            // offset.set(1500f, 300f, -500f);
                        }},

                        new ColorPass.NoiseColorPass() {{
                            seed = 3;
                            scale = 4;
                            persistence = 0.56;
                            octaves = 10;
                            magnitude = 0.9f;
                            min = 0.2f;
                            max = 0.5f;
                            out = AZEnvironment.serridOrange.mapColor;
                            // offset.set(1500f, 300f, -500f);
                        }},

                        new ColorPass.NoiseColorPass() {{
                            seed = 2;
                            scale = 1;
                            persistence = 0.5;
                            octaves = 8;
                            magnitude = 1.2f;
                            min = 0.2f;
                            max = 0.5f;
                            out = AZEnvironment.forsite.mapColor;
                            // offset.set(1500f, 300f, -500f);
                        }},

                        new ColorPass.FlatColorPass() {{
                            min = max = 0f;
                            out = AZEnvironment.deepOxylite.mapColor;
                        }},
                        new ColorPass.FlatColorPass() {{
                            min = 0f;
                            max = 0f;
                            out = AZEnvironment.oxylite.mapColor;
                        }},

                        new ColorPass.FlatColorPass() {{
                            min = 0.3f;
                            max = 0.6f;
                            out = AZEnvironment.forenite.mapColor;
                        }},

                        new ColorPass.FlatColorPass() {{
                            min = 0.4f;
                            max = 0.7f;
                            out = AZEnvironment.lamprosMineral.mapColor;

                        }},

                        new ColorPass.FlatColorPass() {{
                            min = 0.5f;
                            max = 0.7f;
                            out = AZEnvironment.spectralite.mapColor;
                        }},

                        new ColorPass.FlatColorPass() {{
                            min = 0.4f;
                            max = 0.7f;
                            out = AZEnvironment.nerephyte.mapColor;
                        }},

                        new ColorPass.FlatColorPass() {{
                            min = 0.6f;
                            max = 0.8f;
                            out = AZEnvironment.crystalIce.mapColor;
                        }});

            }};
        }};

        /*
        auriona = new Planet("auriona", aStar, 1.2f, 3){{
            generator = new AurionaPlanetGenerator();
            meshLoader = () -> new HexMesh(this, 7);
            cloudMeshLoader = () -> new MultiMesh( // 81ffd7 old color > 5de7a3
                    new HexSkyMesh(this, 11, 0.25f, 0.14f, 6, Color.valueOf("5de7a3").a(0.75f), 2, 0.45f, 0.87f, 0.38f),
                    new HexSkyMesh(this, 2, 0.6f, 0.17f, 6, Color.valueOf("c1f4e4").a(0.75f), 2, 0.45f, 1f, 0.43f)
            );

            launchCapacityMultiplier = 0.5f;
            sectorSeed = 2;
            orbitTime = 2 * 60f * 60f;
            rotateTime = 34f * 60f;
            orbitSpacing = 10;

            allowLaunchToNumbered = false;
            //allowWaves = true;
            //allowWaveSimulation = true;
            //allowSectorInvasion = true;
            //allowLaunchSchematics = false;
            iconColor = Color.valueOf("24b95a");
            atmosphereColor = Color.valueOf("24b95a");
			defaultCore = AZCoreRelatedBlocks.coreLegion;
            atmosphereRadIn = 0.02f;
            atmosphereRadOut = 0.3f;
            startSector = 15;
            orbitRadius = 60f;
            clearSectorOnLose = true;
            alwaysUnlocked = true;
            landCloudColor = Color.valueOf("24b95a");
            serpulo.hiddenItems.addAll(aurionaItems).removeAll(Items.serpuloItems);
            erekir.hiddenItems.addAll(aurionaItems).removeAll(Items.erekirItems);
            itemWhitelist = aurionaItems;
           // hiddenItems.addAll(Items.erekirItems).removeAll(aurionaItems);
          //  hiddenItems.addAll(Items.serpuloItems).removeAll(aurionaItems);
            //hiddenItems.addAll(Items.serpuloItems).addAll(Items.erekirItems).addAll(Items.erekirOnlyItems).removeAll(aurionaItems);
            ruleSetter = r -> {
                r.waveTeam = Team.green;
                r.placeRangeCheck = false;
                r.showSpawns = true;
                r.fog = true;
                r.staticFog = true;
                r.lighting = true;
                r.coreDestroyClear = true;
                r.onlyDepositCore = false;
                unlockedOnLand.add(AZCoreRelatedBlocks.coreLegion);
            };
        }};
         */
    }
}
