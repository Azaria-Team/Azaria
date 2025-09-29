package az.world.blocks.defense.turret;

import arc.audio.Sound;
import arc.graphics.Color;
import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Eachable;
import arc.util.Time;
import arc.util.Tmp;
import arc.util.io.Reads;
import arc.util.io.Writes;
import az.content.AZFx;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.entities.Effect;
import mindustry.entities.Units;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.logic.Ranged;
import mindustry.type.StatusEffect;
import mindustry.world.Block;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

import static mindustry.Vars.indexer;
import static mindustry.Vars.tilesize;

public class TowerTurret extends Block {
    public float damage = 20;
    public float range = 8f * 8f;
    public float reload = 3f * 60f;
    public StatusEffect buffEffect = StatusEffects.fast;
    public float buffDuration = 5f * 60f;
    public StatusEffect debufEffect = StatusEffects.slow;
    public float debuffDuration = 5f * 60f;
    public boolean targetAir = true;
    public boolean targetGround = true;
    public Color waveColor = newBuilding().team.color;
    public Effect fxEffect = AZFx.dynamicWave2;
    public Sound shootSound = Sounds.shootSmite;
    public float shake = 1;

    public TowerTurret(String name) {
        super(name);
        solid = true;
        update = true;
    }

    @Override
    public void setStats(){
        super.setStats();
        stats.add(Stat.targetsAir, targetAir);
        stats.add(Stat.targetsGround, targetGround);
        stats.add(Stat.range, range / tilesize, StatUnit.blocks);
        stats.add(Stat.reload, 60f / reload, StatUnit.perSecond);
        stats.add(Stat.damage, damage, StatUnit.none);
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        super.drawPlace(x, y, rotation, valid);

        Drawf.dashCircle(x * tilesize + offset, y * tilesize + offset, range, Pal.techBlue);
    }

    public class BlastTowerBuild extends Building{
        public float smoothProgress = 0f;
        public float shootLast = -reload;
        public float charge;
        public Seq<Teamc> targets = new Seq<>();
        @Override
        public void updateTile() {
            super.updateTile();

            targets.clear();
            Units.nearbyEnemies(team, x, y, range, units -> {
                if(units.checkTarget(targetAir, targetGround)) {
                    targets.add(units);
                }
            });

            Units.nearby(team, x, y, range, u -> {
                if(u.checkTarget(targetAir, targetGround)) {
                    targets.add(u);
                }
            });

            if (targets.size > 0 && canConsume()) {
                smoothProgress = Mathf.approach(smoothProgress, 1f, Time.delta / reload);

                if (efficiency > 0 && smoothProgress >= 0.99f) {
                    shootWave();
                }
            } else {
                smoothProgress = Mathf.approach(smoothProgress, 0f, Time.delta / reload);
            }
        }

        public void shootWave() {
            if (!canConsume()) return;

            consume();
            shootLast = Time.time;
            Effect.shake(shake, shake, x, y);
            fxEffect.at(x, y, range, waveColor);
            shootSound.at(x, y);
            for (Teamc target : targets) {
                if(target.team() != team) {
                    if(target instanceof Healthc){
                        ((Healthc)target).damage(damage);
                    }
                    if(target instanceof Statusc){
                        ((Statusc)target).apply(debufEffect, debuffDuration);
                    }
                } else {
                    if(target instanceof Statusc){
                        ((Statusc)target).apply(buffEffect, buffDuration);
                    }

                }
            }
            smoothProgress = 0f;
        }

        @Override
        public void drawSelect(){
            Drawf.dashCircle(x, y, range, team.color);
        }

        @Override
        public void write(Writes write) {
            super.write(write);
            write.f(shootLast);
            write.f(smoothProgress);
            write.f(charge);
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            shootLast = read.f();
            smoothProgress = read.f();
            charge = read.f();
        }
    }
}
