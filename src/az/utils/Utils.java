package az.utils;

import arc.Events;
import arc.func.*;
import arc.math.Angles;
import arc.math.Mathf;
import arc.math.geom.*;
import arc.struct.IntSeq;
import arc.struct.IntSet;
import arc.struct.Seq;
import arc.util.Strings;
import arc.util.pooling.Pool;
import arc.util.pooling.Pools;
import mindustry.entities.bullet.BulletType;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.*;

import static mindustry.Vars.tilesize;
import static mindustry.Vars.world;


public class Utils {
    private static final BoolGrid collideLineCollided = new BoolGrid();
    private static final Vec2 tV = new Vec2(), tV2 = new Vec2();
    private static final IntSet collidedBlocks = new IntSet();
    private static final Rect rect = new Rect(),hitRect = new Rect();
    private static final IntSeq lineCast = new IntSeq(), lineCastNext = new IntSeq();
    private static final Seq<Hit> hitEffects = new Seq<>();
    private static boolean hit, hitB;
    public static void init(){
        Events.on(EventType.WorldLoadEvent.class, event -> {
            collideLineCollided.updateSize(world.width(), world.height());
        });
    }

    static class Hit implements Pool.Poolable {
        Healthc ent;
        float x, y;

        @Override
        public void reset(){
            ent = null;
            x = y = 0f;
        }
    }

    public static int getByIndex(IntSet intSet, int index) {
        if (index < 0 || index >= intSet.size) {
            throw new IndexOutOfBoundsException();
        }

        final int[] value = {0};
        final int[] counter = {0};
        intSet.each((item) -> {
            if (counter[0] == index) {
                value[0] = item;
            }
            counter[0]++;
        });

        if (counter[0] > index) {
            return value[0];
        } else {
            throw new IllegalArgumentException();
        }
    }

    public interface Targeting {
        default Vec2 targetPos(){
            return null;
        }
    }

    public static float getBulletDamage(BulletType type){
        return type.damage + type.splashDamage + (Math.max(type.lightningDamage / 2f, 0f) * type.lightning * type.lightningLength);
    }

    public static String stringsFixed(float value){
        return Strings.autoFixed(value, 2);
    }

    /** @author EyeOfDarkness */
    public static void collideLineRawEnemyRatio(Team team, float x, float y, float x2, float y2, float width, Boolf3<Building, Float, Boolean> buildingCons, Boolf2<Unit, Float> unitCons, Floatc2 effectHandler){
        float minRatio = 0.05f;
        collideLineRawEnemy(team, x, y, x2, y2, width, (building, direct) -> {
            float size = (building.block.size * tilesize / 2f);
            float dst = Mathf.clamp(1f - ((Intersector.distanceSegmentPoint(x, y, x2, y2, building.x, building.y) - width) / size), minRatio, 1f);
            return buildingCons.get(building, dst, direct);
        }, unit -> {
            float size = (unit.hitSize / 2f);
            float dst = Mathf.clamp(1f - ((Intersector.distanceSegmentPoint(x, y, x2, y2, unit.x, unit.y) - width) / size), minRatio, 1f);
            return unitCons.get(unit, dst);
        }, effectHandler, true);
    }

    /** @author EyeOfDarkness */
    public static void collideLineRawEnemy(Team team, float x, float y, float x2, float y2, float width, Boolf2<Building, Boolean> buildingCons, Boolf<Unit> unitCons, Floatc2 effectHandler, boolean stopSort){
        collideLineRaw(x, y, x2, y2, width, width, b -> b.team != team, u -> u.team != team, buildingCons, unitCons, healthc -> healthc.dst2(x, y), effectHandler, stopSort);
    }
    public static void collideLineRaw(float x, float y, float x2, float y2, float unitWidth, float tileWidth, Boolf<Building> buildingFilter, Boolf<Unit> unitFilter, Boolf2<Building, Boolean> buildingCons, Boolf<Unit> unitCons, Floatf<Healthc> sort, Floatc2 effectHandler, boolean stopSort){
        collideLineRaw(x, y, x2, y2, unitWidth, tileWidth,
                buildingFilter, unitFilter, buildingCons != null, unitCons != null,
                sort, (ex, ey, ent, direct) -> {
                    boolean hit = false;
                    if(unitCons != null && direct && ent instanceof Unit){
                        hit = unitCons.get((Unit)ent);
                    }else if(buildingCons != null && ent instanceof Building){
                        hit = buildingCons.get((Building)ent, direct);
                    }

                    if(effectHandler != null && direct) effectHandler.get(ex, ey);
                    return hit;
                }, stopSort
        );
    }

    /** @author EyeOfDarkness */
    public static void collideLineRaw(float x, float y, float x2, float y2, float unitWidth, float tileWidth, Boolf<Building> buildingFilter, Boolf<Unit> unitFilter, boolean hitTile, boolean hitUnit, Floatf<Healthc> sort, HitHandler hitHandler, boolean stopSort){
        hitEffects.clear();
        lineCast.clear();
        lineCastNext.clear();
        collidedBlocks.clear();

        tV.set(x2, y2);
        if(hitTile){
            collideLineCollided.clear();
            Runnable cast = () -> {
                hitB = false;
                lineCast.each(i -> {
                    int tx = Point2.x(i), ty = Point2.y(i);
                    Building build = world.build(tx, ty);

                    boolean hit = false;
                    if(build != null && (buildingFilter == null || buildingFilter.get(build)) && collidedBlocks.add(build.pos())){
                        if(sort == null){
                            hit = hitHandler.get(tx * tilesize, ty * tilesize, build, true);
                        }else{
                            hit = hitHandler.get(tx * tilesize, ty * tilesize, build, false);
                            Hit he = Pools.obtain(Hit.class, Hit::new);
                            he.ent = build;
                            he.x = tx * tilesize;
                            he.y = ty * tilesize;

                            hitEffects.add(he);
                        }

                        if(hit && !hitB){
                            tV.trns(Angles.angle(x, y, x2, y2), Mathf.dst(x, y, build.x, build.y)).add(x, y);
                            hitB = true;
                        }
                    }

                    Vec2 segment = Intersector.nearestSegmentPoint(x, y, tV.x, tV.y, tx * tilesize, ty * tilesize, tV2);
                    if(!hit && tileWidth > 0f){
                        for(Point2 p : Geometry.d8){
                            int newX = (p.x + tx);
                            int newY = (p.y + ty);
                            boolean within = !hitB || Mathf.within(x / tilesize, y / tilesize, newX, newY, tV.dst(x, y) / tilesize);
                            if(segment.within(newX * tilesize, newY * tilesize, tileWidth) && collideLineCollided.within(newX, newY) && !collideLineCollided.get(newX, newY) && within){
                                lineCastNext.add(Point2.pack(newX, newY));
                                collideLineCollided.set(newX, newY, true);
                            }
                        }
                    }
                });

                lineCast.clear();
                lineCast.addAll(lineCastNext);
                lineCastNext.clear();
            };

            world.raycastEachWorld(x, y, x2, y2, (cx, cy) -> {
                if(collideLineCollided.within(cx, cy) && !collideLineCollided.get(cx, cy)){
                    lineCast.add(Point2.pack(cx, cy));
                    collideLineCollided.set(cx, cy, true);
                }

                cast.run();
                return hitB;
            });

            while(!lineCast.isEmpty()) cast.run();
        }

        if(hitUnit){
            rect.setPosition(x, y).setSize(tV.x - x, tV.y - y);

            if(rect.width < 0){
                rect.x += rect.width;
                rect.width *= -1;
            }

            if(rect.height < 0){
                rect.y += rect.height;
                rect.height *= -1;
            }

            rect.grow(unitWidth * 2f);
            Groups.unit.intersect(rect.x, rect.y, rect.width, rect.height, unit -> {
                if(unitFilter == null || unitFilter.get(unit)){
                    unit.hitbox(hitRect);
                    hitRect.grow(unitWidth * 2);

                    Vec2 vec = Geometry.raycastRect(x, y, tV.x, tV.y, hitRect);

                    if(vec != null){
                        float scl = (unit.hitSize - unitWidth) / unit.hitSize;
                        vec.sub(unit).scl(scl).add(unit);
                        if(sort == null){
                            hitHandler.get(vec.x, vec.y, unit, true);
                        }else{
                            Hit he = Pools.obtain(Hit.class, Hit::new);
                            he.ent = unit;
                            he.x = vec.x;
                            he.y = vec.y;
                            hitEffects.add(he);
                        }
                    }
                }
            });
        }

        if(sort != null){
            hit = false;
            hitEffects.sort(he -> sort.get(he.ent)).each(he -> {
                if(!stopSort || !hit){
                    hit = hitHandler.get(he.x, he.y, he.ent, true);
                }

                Pools.free(he);
            });
        }

        hitEffects.clear();
    }

    public interface HitHandler{
        boolean get(float x, float y, Healthc ent, boolean direct);
    }
}
