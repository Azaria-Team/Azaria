package az.content;

import arc.struct.Seq;
import mindustry.game.Objectives;

import static az.content.AZLiquids.*;
import static az.content.blocks.AZCoreRelatedBlocks.*;
import static az.content.blocks.AZPower.*;
import static az.content.blocks.AZWalls.*;
import static az.content.blocks.AZDrills.*;
import static az.content.AZSectorPreset.*;
import static mindustry.content.TechTree.*;
import static az.content.AZItems.*;
import static az.content.blocks.AZDistribution.*;
import static az.content.blocks.AZProduction.*;
import static az.content.blocks.AZTurrets.*;
import static az.content.blocks.AZLiquidBlocks.*;
import static az.content.blocks.AZUnitRelatedBlocks.*;
import static az.content.AZUnits.*;


public class AZTechTree {

    public static void load(){
        AZPlanets.auriona.techTree = nodeRoot("Auriona", coreLegion, () -> {
            //region distribution
            node(magneticConveyor, () -> {
                node(magneticJunction, () -> {
                    node(magneticRouter, () -> {
                    });

                });
                node(magneticBridgeConveyor, () -> {
                });
                node(magneticSorter, Seq.with(new Objectives.OnSector(testSector2)), () -> {
                    node(magneticOverflorGate, Seq.with(new Objectives.OnSector(testSector2)), () -> {

                    });
                });
                node(caseI, Seq.with(new Objectives.SectorComplete(testSector3)), () -> {

                });
            });
            //endregion distribution
            //region crafting
            node(forsDrill, () -> {
                node(waveDrill, Seq.with(new Objectives.OnSector(testSector2)), () -> {
                });
                node(aquaticDrill, () -> {
                });
                node(dustCollector, () -> {
                });
            });
            node(crasideBrewer, Seq.with(new Objectives.OnSector(testSector2)), () -> {
                node(heavyDutyCrucible, () -> {

                });
            });
            //endregion crafting
            //region liquid
            node(liquidPipe, Seq.with(new Objectives.OnSector(testSector4)), () -> {
                node(impulsePump, Seq.with(new Objectives.OnSector(testSector4)), () -> {

                });
                node(liquidPipeJunction, Seq.with(new Objectives.OnSector(testSector4)), () -> {
                    node(liquidPipeRouter, Seq.with(new Objectives.OnSector(testSector4)), () -> {

                    });
                });
                node(pipeBridgeConduit, Seq.with(new Objectives.OnSector(testSector4)), () -> {

                });
            });

            //endregion liquid
            //region power
            node(plasmaNode, () -> {
                node(thermalEvaporator, () -> {
                    node(oxyliteTurbine, Seq.with(new Objectives.OnSector(testSector4), new Objectives.Research(impulsePump)), () -> {

                    });
                });
                node(plasmaDistributor, () -> {

                });
            });


            //endregion power
            //region defense
            node(forceTurret, () -> {
                node(forsWall, () -> {
                    node(forsWallLarge, () -> {
                        node(compositeWall, () -> {
                            node(compositeWallLarge, () -> {

                            });
                        });
                    });
                });
                node(testTurret, Seq.with(new Objectives.SectorComplete(testSector3)), () -> {
                    node(razeTurret, () -> {

                    });
                });
                node(hornTurret, Seq.with(new Objectives.SectorComplete(testSector3)), () -> {

                });
                node(repairTurret, Seq.with(new Objectives.SectorComplete(testSector2)), () -> {
                });
            });
            //endregion defense
            //endregion blocks
            //region units
            node(vectorFabricator, Seq.with(new Objectives.OnSector(testSector3)), () -> {
                node(vector);
                node(angelsharkFabricator, Seq.with(new Objectives.OnSector(testSector3)), () -> {
                    node(angelshark);
                });
            });
            //endregion units

            //region items and liquids
            nodeProduce(fors, () -> {
                nodeProduce(serrid, () -> {
                });
                nodeProduce(lepera, () -> {
                    nodeProduce(arside, () -> {
                        nodeProduce(ferbium, () -> {
                            nodeProduce(superdenseAlloy, () -> {
                            });
                        });
                    });
                });
            });
            nodeProduce(oxyliteLiq, () -> {
            });
            // node(HPLSectorPreset.testSector1, () -> {
            // });
            //endregion items and liquids
            //region sectors
            node(testSector1, () -> {
                node(testSector2, Seq.with(new Objectives.SectorComplete(testSector1)), () -> {
                    node(testSector3, Seq.with(new Objectives.SectorComplete(testSector2), new Objectives.Research(waveDrill), new Objectives.Research(crasideBrewer), new Objectives.Research(repairTurret)), () -> {
                        node(testSector4, Seq.with(new Objectives.SectorComplete(testSector3), new Objectives.Research(vectorFabricator), new Objectives.Research(caseI)), () -> {
                            node(testSector6, () -> {
                                node(testSector7, () -> {
                                });
                            });
                            node(testSector5, () -> {
                            });
                        });
                    });
                });
            });
            //endregion sectors
        });
    }
}
