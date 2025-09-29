package az.world.blocks.legacy;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Time;
import az.world.meta.AZStat;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.logic.Ranged;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

import static mindustry.Vars.*;
import static mindustry.content.Blocks.air;
/**
 * Original code from FOS[<a href="https://github.com/TeamOct/fictional-octo-system/blob/master/src/fos/type/blocks/production/OreDetector.java">...</a>]
*/
public class OreRadarLegacy extends Block {
    public int range = 20 * 8;
    public float radarSpeed = 1f;
    public float radarCone = 15f;
    public float circleAlpha = 1f;
    public float coneAlpha = 0.4f;
    public float moveCircleAlpha = 0.4f;

    public OreRadarLegacy(String name) {
        super(name);
        solid = true;
        update = true;
    }

    @Override
    public void setStats() {
        super.setStats();

        stats.add(Stat.range, range / tilesize, StatUnit.blocks);
        stats.add(AZStat.radarSpeed.toStat(), radarSpeed, StatUnit.blocks);
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        super.drawPlace(x, y, rotation, valid);
        Drawf.dashCircle(x * tilesize + offset, y * tilesize + offset, range, Color.valueOf("4b95ff"));
    }

    public class OreRadarBuild extends Building implements Ranged {
        public float start;
        public Seq<Tile> detectedOres = new Seq<>();

        @Override
        public float range() {
            return range;
        }


        public float rot() {
            return (timeRad() * radarSpeed) % 360f;
        }

        public float timeRad() {
            return Time.time - start;
        }

        @Override
        public void draw() {
            super.draw();
            if (canConsume()) {
                Draw.z(Layer.light);
                //Draw.alpha(0.6f);
                Lines.stroke(2f, team.color);

                Draw.alpha(moveCircleAlpha - (timeRad() % 120f) / 120f);
                Lines.circle(x, y, (timeRad() % 120f) / 120f * range());

                Draw.alpha(coneAlpha);
                Fill.arc(x, y, range(), radarCone / 360f, rot());


                Draw.alpha(circleAlpha);
                Lines.circle(x, y, range());

                Draw.reset();
                locateOres(range());
            }
        }

        @Override
        public void drawSelect() {
            Drawf.dashCircle(x, y, range, team.color);
        }

        public void locateOres(float radius) {
            Tile hoverTile = world.tileWorld(Core.input.mouseWorld().x, Core.input.mouseWorld().y);

            tile.circle((int) (radius / tilesize), (ore) -> {
                if (ore != null && ore.overlay() != null && ore.overlay() instanceof UndergroundOreLegacy u) {
                    var angle = Mathf.angle(ore.x - tile.x, ore.y - tile.y);
                    var c1 = rot();
                    var c2 = rot() + radarCone;
                    if (c2 >= 360f && angle < 180f) {
                        angle += 360;
                    }

                    if (angle >= c1 && angle <= c2 && !detectedOres.contains(ore)) {
                        detectedOres.add(ore);
                    }
                }
            });

            for (var ore : detectedOres) {
                if (ore.block() != air) continue;

                UndergroundOreLegacy u = (UndergroundOreLegacy)ore.overlay();
                u.shouldDrawBase = true;
                u.drawBase(ore);
                u.shouldDrawBase = false;

                //show an item icon above the cursor/finger
                if (ore == hoverTile && ore.block() != null) {
                    Draw.z(Layer.max);
                    Draw.alpha(1f);
                    Draw.rect(u.drop.uiIcon, ore.x * 8, ore.y * 8 + 8);
                }
            }
        }

    }
}
