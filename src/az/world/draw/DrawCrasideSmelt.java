package az.world.draw;

import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Angles;
import arc.math.Interp;
import arc.math.Mathf;
import arc.util.Time;
import az.graphics.AZPal;
import mindustry.gen.Building;
import mindustry.graphics.Layer;
import mindustry.world.draw.DrawBlock;

public class DrawCrasideSmelt extends DrawBlock {
        public Color flameColor = AZPal.craside, midColor = AZPal.craside2;
        public float circleStroke = 2f;

        public float alpha = 0.7f;
        public int particles = 30;
        public float particleLife = 45f, particleRad = 7f, particleStroke = 1.1f, particleL = 3f;
        //public Blending blending = Blending.additive;

        @Override
        public void draw(Building build){
            if(build.warmup() > 0f && flameColor.a > 0.001f){
                Lines.stroke(circleStroke * build.warmup());

                float a = alpha * build.warmup();
                Draw.color(flameColor, a);

                Lines.stroke(particleStroke * build.warmup());

                Draw.z(Layer.block + 0.01f);
                float base = (Time.time / particleLife);
                rand.setSeed(build.id);
                for(int i = 0; i < particles; i++){
                    float fin = (rand.random(1f) + base) % 1f, fout = 1f - fin;
                    float angle = rand.random(360f);
                    float rad = particleRad * Interp.pow2Out.apply(fin);
                    Fill.circle(build.x + Angles.trnsx(angle, rad), build.y + Angles.trnsy(angle, rad), particleL * fout * build.warmup());
                }

                Draw.z(Layer.block);
                Draw.color(midColor, a);
                rand.setSeed(build.id);
                for(int i = 0; i < particles; i++){
                    float fin = (rand.random(0.8f) + base) % 0.8f, fout = 0.8f - fin;
                    float angle = rand.random(400f);
                    float rad = particleRad * Interp.pow2Out.apply(fin);
                    Fill.circle(build.x + Angles.trnsx(angle, rad), build.y + Angles.trnsy(angle, rad), particleL / 2 * fout * build.warmup());
                }

                Draw.blend();
                Draw.reset();
            }
        }
    }
