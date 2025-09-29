package az.maps.generators;


import arc.graphics.Color;
import arc.math.Angles;
import arc.math.Mathf;
import arc.math.geom.Geometry;
import arc.math.geom.Point2;
import arc.math.geom.Vec2;
import arc.math.geom.Vec3;
import arc.struct.FloatSeq;
import arc.struct.ObjectSet;
import arc.struct.Seq;
import arc.util.Tmp;
import arc.util.noise.Ridged;
import arc.util.noise.Simplex;

import az.content.AZLiquids;
import az.content.blocks.AZEnvironment;
import az.world.blocks.environment.ModOverlayFloor;
import mindustry.ai.Astar;
import mindustry.content.Blocks;
import mindustry.game.Schematics;
import mindustry.maps.generators.PlanetGenerator;
import mindustry.type.Sector;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.TileGen;
import az.content.AZLoadouts;
import mindustry.world.meta.Attribute;

import static mindustry.Vars.*;
import static mindustry.Vars.state;

import static az.content.blocks.AZEnvironment.*;

public class AurionaPlanetGenerator extends PlanetGenerator {
    public static boolean alt = false;

    //BaseGenerator basegen = new BaseGenerator();
    float scl = 5f;
    float waterOffset = 0.07f;
    boolean genLakes = false;

    {
    defaultLoadout = AZLoadouts.basicLegion;
    }

    Block[][] arr =
            {
                    {deepOxylite, deepOxylite, deepOxylite, serridOxylite, serridOrange, serridOrange, serridOrange, crabStone, huitaRock, huitaRock, crabStone, serridicStone, forsite},
                    {deepOxylite, deepOxylite, deepOxylite, serridOxylite, serridOrange, serridOrange, serridOrange, crabStone, huitaRock, huitaRock, crabStone, serridicStone, forsite},
                    {deepOxylite, deepOxylite, oxylite, serridOxylite, serridOrange, huitaRock, huitaRock, huitaRock, crabStone, huitaRock, crabStone, crabStone, serridicStone},
                    {deepOxylite, deepOxylite, oxylite, serridOxylite, serridOrange, huitaRock, huitaRock, huitaRock, crabStone, huitaRock, huitaRock, crabStone, crabStone},
                    {deepOxylite, deepOxylite, oxylite, serridOxylite, serridOrange, huitaRock, serridicStone, huitaRock, serridicStone, huitaRock, crabStone, crabStone, crabStone},
                    {deepOxylite, deepOxylite, oxylite, oxylite, serridOrange, crabStone, crabStone, forenite, serridicStone, crabStone, forenite, forenite, forsite},
                    {deepOxylite, deepOxylite, oxylite, oxylite, serridOrange, crabStone, forsite, fir, fir, fir, forenite, fir, forsite},
                    {deepOxylite, deepOxylite, oxylite, oxylite, serridOrange, serridicStone, huitaRock, fir, forsite, forsite, forenite, fir, fir},
                    {deepOxylite, deepOxylite, oxylite, oxylite, serridOrange, serridicStone, forenite, forenite, forsite, forsite, forenite, darkSerrid, fir},
                    {deepOxylite, deepOxylite, oxylite, serridOxylite, darkSerrid, fir, fir, crystalIce, forenite, forenite, fir, forenite, lamprosMineral},
                    {deepOxylite, deepOxylite, deepOxylite, darkSerridOxylite, fir, fir, lamprosMineral, serridicStone, serridicStone, lamprosMineral, forsite, crystalIce, crystalIce},
                    {deepOxylite, deepOxylite, deepOxylite, darkSerridOxylite, darkSerrid, lamprosMineral, forsite, lamprosMineral, lamprosMineral, darkSerrid, lamprosMineral, crystalIce, crystalIce},
                    {deepOxylite, deepOxylite, deepOxylite, darkSerridOxylite, darkSerrid, darkSerrid, forsite, lamprosMineral, darkSerrid, crystalIce, crystalIce, crystalIce, crystalIce}
            };
            /*
            {
                    {HPLBlocks.deepOxylite, HPLBlocks.deepOxylite, HPLBlocks.oxylite, HPLBlocks.oxylite, HPLBlocks.oxylite, HPLBlocks.serridDust, HPLBlocks.serridicRock, HPLBlocks.serridicRock, HPLBlocks.crabStone, HPLBlocks.forsite, },
                    {HPLBlocks.deepOxylite, HPLBlocks.deepOxylite, HPLBlocks.oxylite, HPLBlocks.oxylite, HPLBlocks.oxylite, HPLBlocks.serridDust, HPLBlocks.serridicRock, HPLBlocks.huitaRock, HPLBlocks.crabStone, HPLBlocks.crabStone},
                    {HPLBlocks.deepOxylite, HPLBlocks.deepOxylite, HPLBlocks.deepOxylite, HPLBlocks.oxylite, HPLBlocks.serridOxylite, HPLBlocks.serridDust, HPLBlocks.huitaRock, HPLBlocks.crabStone, HPLBlocks.forsite, HPLBlocks.forsite},
                    {HPLBlocks.deepOxylite, HPLBlocks.deepOxylite, HPLBlocks.deepOxylite, HPLBlocks.oxylite, HPLBlocks.serridOxylite, HPLBlocks.serridDust, HPLBlocks.forenite, HPLBlocks.fir, HPLBlocks.forenite, HPLBlocks.forenite},
                    {HPLBlocks.deepOxylite, HPLBlocks.deepOxylite, HPLBlocks.deepOxylite, HPLBlocks.oxylite, HPLBlocks.serridOxylite, HPLBlocks.serridDust, HPLBlocks.forenite, HPLBlocks.forsite, HPLBlocks.forsite, HPLBlocks.fir},
                    {HPLBlocks.deepOxylite, HPLBlocks.deepOxylite, HPLBlocks.deepOxylite, HPLBlocks.oxylite, HPLBlocks.serridOxylite, HPLBlocks.serridDust, HPLBlocks.forenite, HPLBlocks.fir, HPLBlocks.fir, HPLBlocks.fir},
                    {HPLBlocks.deepOxylite, HPLBlocks.deepOxylite, HPLBlocks.deepOxylite, HPLBlocks.oxylite, HPLBlocks.serridOxylite, HPLBlocks.serridDust, HPLBlocks.forsite, HPLBlocks.forenite, HPLBlocks.forenite, HPLBlocks.forenite},
                    {HPLBlocks.deepOxylite, HPLBlocks.deepOxylite, HPLBlocks.oxylite, HPLBlocks.oxylite, HPLBlocks.darkSerridOxylite, HPLBlocks.darkSerrid, HPLBlocks.lamprosMineral, HPLBlocks.lamprosMineral, HPLBlocks.crystalIce, HPLBlocks.crystalIce},
                    {HPLBlocks.deepOxylite, HPLBlocks.deepOxylite, HPLBlocks.oxylite, HPLBlocks.oxylite, HPLBlocks.darkSerridOxylite, HPLBlocks.darkSerrid, HPLBlocks.lamprosMineral, HPLBlocks.forsite, HPLBlocks.lamprosMineral, HPLBlocks.crystalIce},
                    {HPLBlocks.deepOxylite, HPLBlocks.deepOxylite, HPLBlocks.oxylite, HPLBlocks.oxylite, HPLBlocks.darkSerridOxylite, HPLBlocks.darkSerrid, HPLBlocks.forsite, HPLBlocks.crystalIce, HPLBlocks.crystalIce, HPLBlocks.crystalIce},
            };

    Block[][] arr =
            {
                    {HPLBlocks.deepOxylite, HPLBlocks.oxylite, HPLBlocks.serridOxylite, HPLBlocks.serridDust, HPLBlocks.serridicRock, HPLBlocks.serridDust, HPLBlocks.crabStone, HPLBlocks.crabStone, HPLBlocks.crabStone},
                    {HPLBlocks.deepOxylite, HPLBlocks.oxylite, HPLBlocks.serridOxylite, HPLBlocks.serridDust, HPLBlocks.forenite, HPLBlocks.serridicRock, HPLBlocks.serridDust, HPLBlocks.crabStone, HPLBlocks.forenite},
                    {HPLBlocks.deepOxylite, HPLBlocks.oxylite, HPLBlocks.serridOxylite, HPLBlocks.huitaRock, HPLBlocks.forenite, HPLBlocks.fir, HPLBlocks.serridicRock, HPLBlocks.crabStone, HPLBlocks.forenite},
                    {HPLBlocks.deepOxylite, HPLBlocks.deepOxylite, HPLBlocks.serridOxylite, HPLBlocks.forenite, HPLBlocks.forenite, HPLBlocks.forenite, HPLBlocks.serridDust, HPLBlocks.crabStone, HPLBlocks.fir},
                    {HPLBlocks.deepOxylite, HPLBlocks.oxylite, HPLBlocks.oxylite, HPLBlocks.serridDust, HPLBlocks.fir, HPLBlocks.fir, HPLBlocks.forsite, HPLBlocks.serridDust, HPLBlocks.serridDust},
                    {HPLBlocks.deepOxylite, HPLBlocks.deepOxylite, HPLBlocks.serridOxylite, HPLBlocks.fir, HPLBlocks.fir, HPLBlocks.fir, HPLBlocks.serridicRock, HPLBlocks.serridicRock, HPLBlocks.serridicRock},
                    {HPLBlocks.deepOxylite, HPLBlocks.deepOxylite, HPLBlocks.oxylite, HPLBlocks.serridDust, HPLBlocks.darkSerrid, HPLBlocks.darkSerrid, HPLBlocks.fir, HPLBlocks.huitaRock, HPLBlocks.huitaRock},
                    {HPLBlocks.deepOxylite, HPLBlocks.deepOxylite, HPLBlocks.serridOxylite, HPLBlocks.serridDust, HPLBlocks.forsite, HPLBlocks.fir, HPLBlocks.huitaRock, HPLBlocks.fir},
                    {HPLBlocks.deepOxylite, HPLBlocks.oxylite, HPLBlocks.oxylite, HPLBlocks.darkSerrid, HPLBlocks.forsite, HPLBlocks.forsite, HPLBlocks.forenite, HPLBlocks.forenite, HPLBlocks.forenite},
                    {HPLBlocks.deepOxylite, HPLBlocks.deepOxylite, HPLBlocks.oxylite, HPLBlocks.darkSerrid, HPLBlocks.darkSerrid, HPLBlocks.forsite, HPLBlocks.forenite, HPLBlocks.fir},
                    {HPLBlocks.deepOxylite, HPLBlocks.oxylite, HPLBlocks.oxylite, HPLBlocks.serridDust, HPLBlocks.forsite, HPLBlocks.crystalIce, HPLBlocks.darkSerrid, HPLBlocks.darkSerrid, HPLBlocks.darkSerrid},
                    {HPLBlocks.deepOxylite, HPLBlocks.oxylite, HPLBlocks.oxylite, HPLBlocks.lamprosMineral, HPLBlocks.lamprosMineral, HPLBlocks.lamprosMineral, HPLBlocks.darkSerrid, HPLBlocks.darkSerrid, HPLBlocks.crystalIce},
                    {HPLBlocks.deepOxylite, HPLBlocks.oxylite, HPLBlocks.oxylite, HPLBlocks.lamprosMineral, HPLBlocks.crystalIce, HPLBlocks.crystalIce, HPLBlocks.crystalIce, HPLBlocks.crystalIce, HPLBlocks.crystalIce}
            };


     */
    float water = 4f / arr[0].length;

    float rawHeight(Vec3 position){
        position = Tmp.v33.set(position).scl(scl);
        return (Mathf.pow(Simplex.noise3d(seed, 7, 0.5f, 1f/3f, position.x, position.y, position.z), 2.3f) + waterOffset) / (1f + waterOffset);
    }

    @Override
    public void generateSector(Sector sector){
        //no bases right now
    }
    
    @Override
    public float getHeight(Vec3 position){
        float height = rawHeight(position);
        return Math.max(height, water);
    }
    @Override
    
    public Color getColor(Vec3 position) {
        Block block = getBlock(position);

        if(block == AZEnvironment.crystalIce) return Color.valueOf("f6e9ff");
        return Tmp.c1.set(block.mapColor).a(1f - block.albedo);
    }

    @Override
    public void genTile(Vec3 position, TileGen tile){
        tile.floor = getBlock(position);
        tile.block = tile.floor.asFloor().wall;

        if(Ridged.noise3d(seed + 1, position.x, position.y, position.z, 2, 22) > 0.31){
            tile.block = Blocks.air;
        }
    }

    Block getBlock(Vec3 position){
        float height = rawHeight(position);
        Tmp.v31.set(position);
        position = Tmp.v33.set(position).scl(scl);
        float rad = scl;
        float temp = Mathf.clamp(Math.abs(position.y * 2f) / (rad));
        float tnoise = Simplex.noise3d(seed, 7, 0.56, 1f/3f, position.x, position.y + 999f, position.z);
        temp = Mathf.lerp(temp, tnoise, 0.5f);
        height *= 1.2f;
        height = Mathf.clamp(height);

        //float tar = (float)noise.octaveNoise3D(4, 0.55f, 1f/2f, position.x, position.y + 999f, position.z) * 0.3f + Tmp.v31.dst(0, 0, 1f) * 0.2f;

        return arr[Mathf.clamp((int)(temp * arr.length), 0, arr[0].length - 1)][Mathf.clamp((int)(height * arr[0].length), 0, arr[0].length - 1)];
    }

    @Override
    protected float noise(float x, float y, double octaves, double falloff, double scl, double mag){
        Vec3 v = sector.rect.project(x, y).scl(5f);
        return Simplex.noise3d(seed, octaves, falloff, 1f / scl, v.x, v.y, v.z) * (float)mag;
    }


    @Override
    protected void generate(){

        class Room{
            int x, y, radius;
            ObjectSet<Room> connected = new ObjectSet<>();

            Room(int x, int y, int radius){
                this.x = x;
                this.y = y;
                this.radius = radius;
                connected.add(this);
            }

            void join(int x1, int y1, int x2, int y2){
                float nscl = rand.random(100f, 140f) * 6f;
                int stroke = rand.random(3, 9);
                brush(pathfind(x1, y1, x2, y2, tile -> (tile.solid() ? 50f : 0f) + noise(tile.x, tile.y, 2, 0.4f, 1f / nscl) * 500, Astar.manhattan), stroke);
            }

            void connect(Room to){
                if(!connected.add(to) || to == this) return;

                Vec2 midpoint = Tmp.v1.set(to.x, to.y).add(x, y).scl(0.5f);
                rand.nextFloat();

                if(alt){
                    midpoint.add(Tmp.v2.set(1, 0f).setAngle(Angles.angle(to.x, to.y, x, y) + 90f * (rand.chance(0.5) ? 1f : -1f)).scl(Tmp.v1.dst(x, y) * 2f));
                }else{
                    //add randomized offset to avoid straight lines
                    midpoint.add(Tmp.v2.setToRandomDirection(rand).scl(Tmp.v1.dst(x, y)));
                }

                midpoint.sub(width/2f, height/2f).limit(width / 2f / Mathf.sqrt3).add(width/2f, height/2f);

                int mx = (int)midpoint.x, my = (int)midpoint.y;

                join(x, y, mx, my);
                join(mx, my, to.x, to.y);
            }

            void joinLiquid(int x1, int y1, int x2, int y2){
                float nscl = rand.random(100f, 140f) * 6f;
                int rad = rand.random(7, 11);
                int avoid = 2 + rad;
                var path = pathfind(x1, y1, x2, y2, tile -> (tile.solid() || !tile.floor().isLiquid ? 70f : 0f) + noise(tile.x, tile.y, 2, 0.4f, 1f / nscl) * 500, Astar.manhattan);
                /*path.each(t -> {
                    //don't place liquid paths near the core
                    if(Mathf.dst2(t.x, t.y, x2, y2) <= avoid * avoid){
                        return;
                    }

                    for(int x = -rad; x <= rad; x++){
                        for(int y = -rad; y <= rad; y++){
                            int wx = t.x + x, wy = t.y + y;
                            if(Structs.inBounds(wx, wy, width, height) && Mathf.within(x, y, rad)){
                                Tile other = tiles.getn(wx, wy);
                                other.setBlock(Blocks.air);
                                if(Mathf.within(x, y, rad - 1) && !other.floor().isLiquid){
                                    Floor floor = other.floor();
                                    //TODO does not respect tainted floors
                                    other.setFloor((Floor)(floor == serridDust || floor == huitaRock ? serridOxylite : oxylite));
                                }
                            }
                        }
                    }

                });

                 */
            }

            void connectLiquid(Room to){
                if(to == this) return;

                Vec2 midpoint = Tmp.v1.set(to.x, to.y).add(x, y).scl(0.5f);
                rand.nextFloat();

                //add randomized offset to avoid straight lines
                midpoint.add(Tmp.v2.setToRandomDirection(rand).scl(Tmp.v1.dst(x, y)));
                midpoint.sub(width/2f, height/2f).limit(width / 2f / Mathf.sqrt3).add(width/2f, height/2f);

                int mx = (int)midpoint.x, my = (int)midpoint.y;

                joinLiquid(x, y, mx, my);
                joinLiquid(mx, my, to.x, to.y);
            }
        }

        cells(4);
        distort(10f, 12f);

        float constraint = 1.3f;
        float radius = width / 2f / Mathf.sqrt3;
        int rooms = rand.random(2, 5);
        Seq<Room> roomseq = new Seq<>();

        for(int i = 0; i < rooms; i++){
            Tmp.v1.trns(rand.random(360f), rand.random(radius / constraint));
            float rx = (width/2f + Tmp.v1.x);
            float ry = (height/2f + Tmp.v1.y);
            float maxrad = radius - Tmp.v1.len();
            float rrad = Math.min(rand.random(9f, maxrad / 2f), 30f);
            roomseq.add(new Room((int)rx, (int)ry, (int)rrad));
        }

        //check positions on the map to place the player spawn. this needs to be in the corner of the map
        Room spawn = null;
        Seq<Room> enemies = new Seq<>();
        int enemySpawns = rand.random(1, Math.max((int)(sector.threat * 4), 1));
        int offset = rand.nextInt(360);
        float length = width/2.55f - rand.random(13, 23);
        int angleStep = 5;
        int waterCheckRad = 5;
        for(int i = 0; i < 360; i+= angleStep){
            int angle = offset + i;
            int cx = (int)(width/2 + Angles.trnsx(angle, length));
            int cy = (int)(height/2 + Angles.trnsy(angle, length));

            int waterTiles = 0;

            //check for water presence
            for(int rx = -waterCheckRad; rx <= waterCheckRad; rx++){
                for(int ry = -waterCheckRad; ry <= waterCheckRad; ry++){
                    Tile tile = tiles.get(cx + rx, cy + ry);
                    if(tile == null || tile.floor().liquidDrop != null){
                        waterTiles ++;
                    }
                }
            }

            if(waterTiles <= 4 || (i + angleStep >= 360)){
                roomseq.add(spawn = new Room(cx, cy, rand.random(8, 15)));

                for(int j = 0; j < enemySpawns; j++){
                    float enemyOffset = rand.range(60f);
                    Tmp.v1.set(cx - width/2, cy - height/2).rotate(180f + enemyOffset).add(width/2, height/2);
                    Room espawn = new Room((int)Tmp.v1.x, (int)Tmp.v1.y, rand.random(8, 16));
                    roomseq.add(espawn);
                    enemies.add(espawn);
                }

                break;
            }
        }

        //clear radius around each room
        for(Room room : roomseq){
            erase(room.x, room.y, room.radius);
        }

        //randomly connect rooms together
        int connections = rand.random(Math.max(rooms - 1, 1), rooms + 3);
        for(int i = 0; i < connections; i++){
            roomseq.random(rand).connect(roomseq.random(rand));
        }

        for(Room room : roomseq){
            spawn.connect(room);
        }

        Room fspawn = spawn;

        cells(1);

        int tlen = tiles.width * tiles.height;
        int total = 0, waters = 0;

        for(int i = 0; i < tlen; i++){
            Tile tile = tiles.geti(i);
            if(tile.block() == Blocks.air){
                total ++;
                if(tile.floor().liquidDrop == AZLiquids.oxyliteLiq){
                    waters ++;
                }
            }
        }

        boolean naval = (float)waters / total >= 0.19f;

        //create water pathway if the map is flooded
        if(naval){
            for(Room room : enemies){
                room.connectLiquid(spawn);
            }
        }

        distort(10f, 6f);

        //rivers
        pass((x, y) -> {
            if(block.solid) return;

            Vec3 v = sector.rect.project(x, y);

            float rr = Simplex.noise2d(sector.id, (float)2, 0.6f, 1f / 7f, x, y) * 0.1f;
            float value = Ridged.noise3d(2, v.x, v.y, v.z, 1, 1f / 55f) + rr - rawHeight(v) * 0f;
            float rrscl = rr * 44 - 2;

            if(value > 0.17f && !Mathf.within(x, y, fspawn.x, fspawn.y, 12 + rrscl)){
                boolean deep = value > 0.17f + 0.1f && !Mathf.within(x, y, fspawn.x, fspawn.y, 15 + rrscl);
                boolean spore = floor != AZEnvironment.serridOrange && floor != crabStone;
                //do not place rivers on ice, they're frozen
                //ignore pre-existing liquids
                if(!(floor == AZEnvironment.crystalIce ||  floor.asFloor().isLiquid)){
                    floor = spore ?
                            (deep ? oxylite : darkSerridOxylite) :
                            (deep ? AZEnvironment.oxylite :
                                    (floor == AZEnvironment.serridOrange || floor == crabStone ? serridOxylite : darkSerridOxylite));
                }
            }
        });

        //shoreline setup
        pass((x, y) -> {
            int deepRadius = 3;

            if(floor.asFloor().isLiquid && floor.asFloor().shallow){

                for(int cx = -deepRadius; cx <= deepRadius; cx++){
                    for(int cy = -deepRadius; cy <= deepRadius; cy++){
                        if((cx) * (cx) + (cy) * (cy) <= deepRadius * deepRadius){
                            int wx = cx + x, wy = cy + y;

                            Tile tile = tiles.get(wx, wy);
                            if(tile != null && (!tile.floor().isLiquid || tile.block() != Blocks.air)){
                                //found something solid, skip replacing anything
                                return;
                            }
                        }
                    }
                }

                floor = floor == serridOxylite ? deepOxylite : AZEnvironment.oxylite;
            }
        });

        if(naval){
            int deepRadius = 2;

            //TODO code is very similar, but annoying to extract into a separate function
            pass((x, y) -> {
                if(floor.asFloor().isLiquid && !floor.asFloor().isDeep() && !floor.asFloor().shallow){

                    for(int cx = -deepRadius; cx <= deepRadius; cx++){
                        for(int cy = -deepRadius; cy <= deepRadius; cy++){
                            if((cx) * (cx) + (cy) * (cy) <= deepRadius * deepRadius){
                                int wx = cx + x, wy = cy + y;

                                Tile tile = tiles.get(wx, wy);
                                if(tile != null && (tile.floor().shallow || !tile.floor().isLiquid)){
                                    //found something shallow, skip replacing anything
                                    return;
                                }
                            }
                        }
                    }

                    floor = floor == darkSerridOxylite ? AZEnvironment.deepOxylite : oxylite;
                }
            });
        }

        Seq<Block> ores = Seq.with();
        float poles = Math.abs(sector.tile.v.y);
        float nmag = 0.5f;
        float scl = 1f;
        float addscl = 1.3f;

        if(Simplex.noise3d(seed, 2, 0.5, scl, sector.tile.v.x, sector.tile.v.y, sector.tile.v.z)*nmag + poles > 0.4f*addscl){
            ores.add(AZEnvironment.forsOre);
        }

        FloatSeq frequencies = new FloatSeq();
        for(int i = 0; i < ores.size; i++){
            frequencies.add(rand.random(-0.1f, 0.01f) - i * 0.01f + poles * 0.04f);
        }

        pass((x, y) -> {
            if(!floor.asFloor().hasSurface()) return;

            int offsetX = x - 4, offsetY = y + 23;
            for(int i = ores.size - 1; i >= 0; i--){
                Block entry = ores.get(i);
                float freq = frequencies.get(i);
                if(Math.abs(0.5f - noise(offsetX, offsetY + i*999, 2, 0.7, (40 + i * 2))) > 0.22f + i*0.01 &&
                        Math.abs(0.5f - noise(offsetX, offsetY - i*999, 1, 1, (30 + i * 4))) > 0.37f + freq){
                    ore = entry;
                    break;
                }
            }

        });

        trimDark();

        int minVents = rand.random(6, 9);
        int ventCount = 0;

        //vents
        outer:
        for(Tile tile : tiles){
            var floor = tile.floor();
            if((floor == forsite || floor == forenite) && rand.chance(0.002)){
                int rad1us = 2;
                for(int x = -rad1us; x <= rad1us; x++){
                    for(int y = -rad1us; y <= rad1us; y++){
                        Tile other = tiles.get(x + tile.x, y + tile.y);
                        if(other == null || (other.floor() != forsite && other.floor() != forenite) || other.block().solid){
                            continue outer;
                        }
                    }
                }

                ventCount ++;
                for(var pos : ModOverlayFloor.offsets){
                    Tile other = tiles.get(pos.x + tile.x + 1, pos.y + tile.y + 1);
                    other.setFloor(forsRock.asFloor());
                }
            }
        }

        int iterations = 0;
        int maxIterations = 5;

        Vec2 trns = Tmp.v1.trns(rand.random(360f), length);
        int
                spawnX = (int)(trns.x + width/2f), spawnY = (int)(trns.y + height/2f);

        //try to add additional vents, but only several times to prevent infinite loops in bad maps
        while(ventCount < minVents && iterations++ < maxIterations){
            outer:
            for(Tile tile : tiles){
                if(rand.chance(0.00018 * (1 + iterations)) && !Mathf.within(tile.x, tile.y, spawnX, spawnY, 5f)){
                    //skip crystals, but only when directly on them
                    if(tile.floor() == Blocks.crystallineStone || tile.floor() == Blocks.crystalFloor){
                        continue;
                    }

                    int rad1us = 1;
                    for(int x = -rad1us; x <= rad1us; x++){
                        for(int y = -rad1us; y <= rad1us; y++){
                            Tile other = tiles.get(x + tile.x, y + tile.y);
                            //skip solids / other vents / arkycite / slag
                            if(other == null || other.block().solid || other.floor().attributes.get(Attribute.steam) != 0 || other.floor() == Blocks.slag || other.floor() == Blocks.arkyciteFloor || other.floor() == AZEnvironment.oxylite){
                                continue outer;
                            }
                        }
                    }

                    Block
                            floor,
                            secondFloor,
                            vent = AZEnvironment.forsite;

                    int xDir = 1;
                    //set target material depending on what's encountered
                    if(tile.floor() == AZEnvironment.forsite){
                        floor = secondFloor = AZEnvironment.forsite;
                        vent = AZEnvironment.forsRock;
                    }


                    ventCount ++;
                    for(var pos : ModOverlayFloor.offsets){
                        Tile other = tiles.get(pos.x + tile.x + 1, pos.y + tile.y + 1);
                        other.setFloor(vent.asFloor());
                    }

                }
            }
        }

        median(2);

        inverseFloodFill(tiles.getn(spawn.x, spawn.y));

        tech();

        pass((x, y) -> {
            //random moss

            //tar
            if(floor == AZEnvironment.darkSerridOxylite){
                if(Math.abs(0.5f - noise(x - 40, y, 2, 0.7, 80)) > 0.25f &&
                        Math.abs(0.5f - noise(x, y + sector.id*10, 1, 1, 60)) > 0.41f && !(roomseq.contains(r -> Mathf.within(x, y, r.x, r.y, 30)))){
                    floor = AZEnvironment.darkSerridOxylite;
                }
            }

            if(genLakes && floor != AZEnvironment.huitaRock && floor != AZEnvironment.crystalIce && floor.asFloor().hasSurface()){
                float noise = noise(x + 782, y, 5, 0.75f, 260f, 1f);
                if(noise > 0.67f && !roomseq.contains(e -> Mathf.within(x, y, e.x, e.y, 14))){
                    if(noise > 0.72f){
                        floor = noise > 0.78f ? AZEnvironment.oxylite : (floor == AZEnvironment.serridOrange ? serridOxylite : AZEnvironment.darkSerridOxylite);
                    }else{
                        floor = (floor == AZEnvironment.serridOrange ? floor : AZEnvironment.darkSerrid);
                    }
                }
            }

            if(rand.chance(0.0075)){
                //random spore trees
                boolean any = false;
                boolean all = true;
                for(Point2 p : Geometry.d4){
                    Tile other = tiles.get(x + p.x, y + p.y);
                    if(other != null && other.block() == Blocks.air){
                        any = true;
                    }else{
                        all = false;
                    }
                }
                if(any && ((block == AZEnvironment.crystalIce) || (all && block == Blocks.air && floor == AZEnvironment.crystalIce && rand.chance(0.03)))){
                    block = rand.chance(0.5) ? AZEnvironment.crystalIceBoulder : AZEnvironment.lamprosBoulder;
                }
            }

            //random stuff
            dec: {
                for(int i = 0; i < 4; i++){
                    Tile near = world.tile(x + Geometry.d4[i].x, y + Geometry.d4[i].y);
                    if(near != null && near.block() != Blocks.air){
                        break dec;
                    }
                }
            }
        });

        float difficulty = sector.threat;
        ints.clear();
        ints.ensureCapacity(width * height / 4);

/*

        int ruinCount = rand.random(0, 0);
        if(ruinCount > 0){
            int padding = 25;

            //create list of potential positions
            for(int x = padding; x < width - padding; x++){
                for(int y = padding; y < height - padding; y++){
                    Tile tile = tiles.getn(x, y);
                    if(!tile.solid() && (tile.drop() != null || tile.floor().liquidDrop != null)){
                        ints.add(tile.pos());
                    }
                }
            }

            ints.shuffle(rand);

            int placed = 0;
            float diffRange = 0f;
            //try each position
            for(int i = 0; i < ints.size && placed < ruinCount; i++){
                int val = ints.items[i];
                int x = Point2.x(val), y = Point2.y(val);

                //do not overwrite player spawn
                if(Mathf.within(x, y, spawn.x, spawn.y, 18f)){
                    continue;
                }

                float range = difficulty + rand.random(diffRange);

                Tile tile = tiles.getn(x, y);
                BaseRegistry.BasePart part = null;
                if(tile.overlay().itemDrop != null){
                    part = bases.forResource(tile.drop()).getFrac(range);
                }else if(tile.floor().liquidDrop != null && rand.chance(0.05)){
                    part = bases.forResource(tile.floor().liquidDrop).getFrac(range);
                }else if(rand.chance(0.05)){ //ore-less parts are less likely to occur.
                    part = bases.parts.getFrac(range);
                }

                //actually place the part
                if(part != null && BaseGenerator.tryPlace(part, x, y, Team.derelict, (cx, cy) -> {
                    Tile other = tiles.getn(cx, cy);
                    if(other.floor().hasSurface()){
                        other.setOverlay(Blocks.oreScrap);
                        for(int j = 1; j <= 2; j++){
                            for(Point2 p : Geometry.d8){
                                Tile t = tiles.get(cx + p.x*j, cy + p.y*j);
                                if(t != null && t.floor().hasSurface() && rand.chance(j == 1 ? 0.4 : 0.2)){
                                    t.setOverlay(Blocks.oreScrap);
                                }
                            }
                        }
                    }
                })){
                    placed ++;

                    int debrisRadius = Math.max(part.schematic.width, part.schematic.height)/2 + 3;
                    Geometry.circle(x, y, tiles.width, tiles.height, debrisRadius, (cx, cy) -> {
                        float dst = Mathf.dst(cx, cy, x, y);
                        float removeChance = Mathf.lerp(0.05f, 0.5f, dst / debrisRadius);

                        Tile other = tiles.getn(cx, cy);
                        if(other.build != null && other.isCenter()){
                            if(other.team() == Team.derelict && rand.chance(removeChance)){
                                other.remove();
                            }else if(rand.chance(0.5)){
                                other.build.health = other.build.health - rand.random(other.build.health * 0.9f);
                            }
                        }
                    });
                }
            }
        }

 */

        //remove invalid ores
        for(Tile tile : tiles){
            if(tile.overlay().needsSurface && !tile.floor().hasSurface()){
                tile.setOverlay(Blocks.air);
            }
        }


        for(Room espawn : enemies){
            tiles.getn(espawn.x, espawn.y).setOverlay(Blocks.spawn);
        }


        for(Tile tile : tiles){
            if(tile.overlay().needsSurface && !tile.floor().hasSurface()){
                tile.setOverlay(Blocks.air);
            }
        }


        decoration(0.017f);
        state.rules.placeRangeCheck = true;
        Schematics.placeLaunchLoadout(spawnX, spawnY);

        state.rules.env = sector.planet.defaultEnv;
        //state.rules.enemyCoreBuildRadius = 600f;
    }

    /*
    @Override
    public void postGenerate(Tiles tiles){
        if(sector.hasEnemyBase()){
            basegen.postGenerate();

            //spawn air enemies
            if(spawner.countGroundSpawns() == 0){
                state.rules.spawns = Waves.generate(sector.threat, new Rand(sector.id), state.rules.attackMode, true, false);
            }
        }
    }

     */
}
