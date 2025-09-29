package az.world.draw;

import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.Vars;
import mindustry.gen.Building;
import mindustry.graphics.Layer;
import mindustry.world.Block;
import mindustry.world.draw.DrawFlame;

public class MultiDrawFlame extends DrawFlame {
    public FlamePoint[] flamePoints = {};
    public boolean drawTopOnce=true;

    public void flamePoints(FlamePoint... flamePoints){
        this.flamePoints = flamePoints;
    }

    @Override
    public void draw(Building build){
        Block block = build.block;
        Draw.rect(block.region, build.x, build.y, block.rotate ? build.rotdeg() : 0);

        if(build.warmup() > 0f && flameColor.a > 0.001f){
            float g = 0.3f;
            float r = 0.06f;
            float cr = Mathf.random(0.1f);

            Draw.z(Layer.block + 0.01f);
            if (drawTopOnce){
                Draw.alpha(build.warmup());
                Draw.rect(top, build.x, build.y);
            }

            for(FlamePoint point : flamePoints){
                float x = build.x + (point.x - 0.5f) * block.size * Vars.tilesize;
                float y = build.y + (point.y - 0.5f) * block.size * Vars.tilesize;
                if (!drawTopOnce){
                    Draw.alpha(build.warmup());
                    Draw.rect(top, x, y);
                }

                Draw.alpha(((1f - g) + Mathf.absin(Time.time, 8f, g) + Mathf.random(r) - r) * build.warmup());

                Draw.tint(flameColor);
                float radius = flameRadius + Mathf.absin(Time.time, flameRadiusScl, flameRadiusMag) + cr;
                Fill.circle(x, y, radius*point.scale);
                Draw.color(1f, 1f, 1f, build.warmup());
                float radiusIn = flameRadiusIn + Mathf.absin(Time.time, flameRadiusScl, flameRadiusInMag) + cr;
                Fill.circle(x, y, radiusIn*point.scale);
            }


            Draw.color();
        }
    }
    public static class FlamePoint{

        /**Normalized coordinates from the lower left corner to the upper right corner of the building*/
        public float x,y;
        /**flame radius scale*/
        float scale;
        /**
         * @param x number from 0 to 1
         * @param y number from 0 to 1
         * @param scale number from 0 to 1
         * */
        public FlamePoint( float x, float y, float scale){
            this.x = x;
            this.y = y;
            this.scale = scale;
        }

        /**
         * @param x number from 0 to 1
         * @param y number from 0 to 1
         * */
        public FlamePoint(float x, float y){
            this(x,y,1f);
        }

        public FlamePoint(){
            this(0.5f,0.5f,1f);
        }
        /**
         * @param x number from 0 to 1
         * @param y number from 0 to 1
         * @param scale number from 0 to 1
         * */
        public void setX( float x, float y, float scale){
            this.x = x;
            this.y = y;
            this.scale = scale;
        }

        /**
         * @param x number from 0 to 1
         * @param y number from 0 to 1
         * */
        public void set(float x, float y){
            setX(x,y,1f);
        }
    }
}
