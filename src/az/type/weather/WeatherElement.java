package az.type.weather;

import arc.Core;
import arc.audio.Sound;
import arc.graphics.Color;
import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Tmp;
import az.content.AZSounds;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.entities.Damage;
import mindustry.entities.Effect;
import mindustry.entities.Lightning;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Groups;
import mindustry.gen.WeatherState;
import mindustry.graphics.Pal;
import mindustry.world.blocks.storage.CoreBlock;

public class WeatherElement{
    public static class Thunder extends Element {
        public float lightningDamage = 30f;
        public float lightningDamageRadius = 20f;


        public int lightning = 1;
        public float lightningShake = 15f;
        public Effect lightningEffect = Fx.lightning;
        public Sound lightningSound = AZSounds.thunder_blast;
        public Color lightningColor = Pal.lancerLaser;

        public Thunder(String name){
            super(name);
        }

        public void effects(float x, float y) {
            Effect.shake(lightningShake, lightningShake, x, y);
            lightningEffect.at(x, y);
            lightningSound.at(x, y);

            if (Tmp.r1.setCentered(x, y, Core.camera.width, Core.camera.height).overlaps(Core.camera.bounds(Tmp.r2))) {
                Fx.lightning.at(x, y, 0, Color.white);
            }
        }

        @Override
        public void update(WeatherState state) {
            Seq<Building> builds = new Seq<>();

            Groups.build.each(e -> {
                if (!(e.block instanceof CoreBlock)) builds.add(e);
            });

            for (int i = 0; i < Math.max(1, Math.round(Math.random() * lightning)); i++) {
                Building build = builds.select(e -> e != null && e.isValid()).random();
                Vars.indexer.allBuildings(build.x, build.y, lightningDamageRadius, other -> {
                    if (other.power != null && other.power.graph.getLastPowerProduced() > 0f) {
                        effects(build.x, build.y);
                        Damage.damage(build.x, build.y, lightningDamageRadius, lightningDamage);

                        for (int e = 0; e < Math.random() * 20; e++) {
                            Lightning.create(Team.derelict, lightningColor, lightningDamage, build.x, build.y, Mathf.random() * 360, 35);
                        }
                    }
                });
                if (build == null) {
                    return;
                }
            }
        }
    }
}
