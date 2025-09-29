package az.world.blocks.defense;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.struct.EnumSet;
import arc.util.Time;
import arc.util.io.Reads;
import arc.util.io.Writes;
import az.content.AZFx;
import az.graphics.AZPal;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.world.Block;
import mindustry.world.blocks.defense.Radar;
import mindustry.world.meta.BlockFlag;
import static mindustry.Vars.tilesize;

public class ModRadar extends Block {
    public float discoveryTime = 60f * 20f;
    public Color baseColor = AZPal.ferbiumBullet;
    public float effectChance = 0.003f;
    public Effect effect = AZFx.lampParticle;

    public ModRadar(String name){
        super(name);

        update = solid = true;
        flags = EnumSet.of(BlockFlag.hasFogRadius);
        fogRadius = 20;
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        super.drawPlace(x, y, rotation, valid);

        Drawf.dashCircle(x * tilesize + offset, y * tilesize + offset, fogRadius * tilesize, Pal.accent);
    }

    public class RadarBuild extends Building {
        public float progress;
        public float lastRadius = 0f;
        public float smoothEfficiency = 1f;
        public float totalProgress;

        @Override
        public float fogRadius(){
            return fogRadius * progress * smoothEfficiency;
        }

        @Override
        public void updateTile(){
            smoothEfficiency = Mathf.lerpDelta(smoothEfficiency, efficiency, 0.05f);

            if(Math.abs(fogRadius() - lastRadius) >= 0.5f){
                Vars.fogControl.forceUpdate(team, this);
                lastRadius = fogRadius();
            }

            progress += edelta() / discoveryTime;
            progress = Mathf.clamp(progress);

            totalProgress += efficiency * edelta();


                if(efficiency > 0.0001 && Mathf.chanceDelta(effectChance * size * size)){
                    effect.at(x + Mathf.range(size * tilesize/2f - 1f), y + Mathf.range(size * tilesize/2f - 1f));
            }
        }

        @Override
        public boolean canPickup(){
            return false;
        }

        @Override
        public void drawSelect(){
            Drawf.dashCircle(x, y, fogRadius() * tilesize, Pal.accent);
        }

        @Override
        public float progress(){
            return progress;
        }

        @Override
        public void write(Writes write){
            super.write(write);

            write.f(progress);
        }

        @Override
        public void draw(){
            super.draw();

            float f = 1f - (Time.time / 100f) % 1f;

            Draw.color(baseColor);
            Draw.alpha(1f);
            Lines.stroke(2f * f + 0.2f);
            Lines.square(x, y, Math.min(1f + (1f - f) * size * tilesize / 2f, size * tilesize/2f));

            Draw.reset();
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);

            progress = read.f();
        }
    }
}
