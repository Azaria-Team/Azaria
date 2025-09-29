package az.content;

import mindustry.type.SectorPreset;

import static az.content.AZPlanets.auriona;

public class AZSectorPreset {
    public static SectorPreset
    //legacy
    /*abandonedShoreline, caveEntrance, seaOutpost, theOutskirts;*/
    testSector1, testSector2, testSector3, testSector4, testSector5, testSector6, testSector7;

    public static void load(){
        /*legacy
        abandonedShoreline = new SectorPreset("abandoned-shoreline", auriona, 15){{
            alwaysUnlocked = true;
            difficulty = 1;
            captureWave = 10;
            startWaveTimeMultiplier = 3f;
        }};

        caveEntrance = new SectorPreset("cave-entrance", auriona, 220) {{
            alwaysUnlocked = false;
            difficulty = 2;
            captureWave = 15;
            startWaveTimeMultiplier = 3.2f;
        }};

        seaOutpost = new SectorPreset("sea-outpost", auriona, 200) {{
            alwaysUnlocked = false;
            difficulty = 3;
        }};

        theOutskirts = new SectorPreset("the-outskirts", auriona, 68) {{
            alwaysUnlocked = false;
            difficulty = 3;
            captureWave = 20;
            startWaveTimeMultiplier = 2f;
        }};

         */

        testSector1 = new SectorPreset("test-sector-1", auriona, 15){{
            alwaysUnlocked = true;
            difficulty = 1;
            captureWave = 1;
            startWaveTimeMultiplier = 3f;
        }};
        testSector2 = new SectorPreset("test-sector-2", auriona, 16){{
            difficulty = 1;
            captureWave = 1;
            startWaveTimeMultiplier = 3f;
        }};
        testSector3 = new SectorPreset("test-sector-3", auriona, 17){{
            difficulty = 1;
            captureWave = 1;
            startWaveTimeMultiplier = 3f;
        }};
        testSector4 = new SectorPreset("test-sector-4", auriona, 18){{
            difficulty = 1;
            captureWave = 1;
            startWaveTimeMultiplier = 3f;
        }};
        testSector5 = new SectorPreset("test-sector-5", auriona, 19){{
            difficulty = 1;
            captureWave = 1;
            startWaveTimeMultiplier = 3f;
        }};
        testSector6 = new SectorPreset("test-sector-6", auriona, 20){{
            difficulty = 1;
            captureWave = 1;
            startWaveTimeMultiplier = 3f;
        }};
        testSector7 = new SectorPreset("test-sector-7", auriona, 21){{
            difficulty = 1;
            captureWave = 1;
            startWaveTimeMultiplier = 3f;
        }};
    }
}
