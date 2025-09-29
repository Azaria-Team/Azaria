package az.world.blocks.defense.turret;

import arc.*;
import arc.math.*;
import arc.util.*;
import az.utils.Utils;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.Mover;
import mindustry.entities.bullet.*;
import mindustry.graphics.Pal;
import mindustry.ui.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
 /*
 * author Yuria
 */
public class SpeedUpItemTurret extends ItemTurret{

    public float overheatTime = -1f;
    public float overheatCoolAmount = 1.25f;
    public boolean isOverheat = false;

    public float maxSpeedupScl = 0.3f;
    public float speedupPerShoot = 0.03f;

    public float slowDownReloadTime = 150f;
    public float inaccuracyUp = 0f;

    public float maxHeatEffectChance = 0.3f;
    public Effect heatEffect = Fx.smoke;

    public SpeedUpItemTurret(String name){
        super(name);
    }

    @Override
    public void setBars(){
        super.setBars();
        addBar("az-speedup", (SpeedUpItemTurretBuild entity) -> new Bar(
                () -> Core.bundle.format("bar.az-speedup", Utils.stringsFixed(Mathf.clamp(entity.speedupScl / maxSpeedupScl) * 100f)),
                () -> entity.team.color,
                () -> entity.speedupScl / maxSpeedupScl
        ));
        if(isOverheat) {
            addBar("az-overheat",
                    (SpeedUpItemTurretBuild entity) -> new Bar(
                            () -> "bar.az-overheat",
                            () -> entity.requireCompleteCooling ? Pal.redDust : Pal.powerLight,
                            () -> entity.overheat / overheatTime
                    )
            );
        }
    }

    @Override
    public void setStats(){
        super.setStats();
        stats.add(Stat.inaccuracy, inaccuracyUp, StatUnit.degrees);
        if(isOverheat) {
            stats.add(Stat.heatCapacity, overheatTime / Time.toSeconds, StatUnit.seconds);
        }
    }

    @Override
    public void init(){
        super.init();
    }

    public class SpeedUpItemTurretBuild extends ItemTurretBuild{
        public float speedupScl = 0f;
        public float slowDownReload = 0f;
        public float overheat = 0;
        public boolean requireCompleteCooling = false;


        @Override
        public void updateTile(){
            if(!isOverheat) {
                super.updateTile();
            }
            if(slowDownReload >= 1f){
                slowDownReload -= Time.delta;
            }else{
                speedupScl = Mathf.lerpDelta(speedupScl, 0f, 0.05f);
                if(!requireCompleteCooling)coolDown();
            }
            if(isOverheat) {
                if (overheat > overheatTime * 0.3f) {
                    if (Mathf.chanceDelta(maxHeatEffectChance * (requireCompleteCooling ? 1 : overheat / overheatTime))) {
                        heatEffect.at(x + Mathf.range(Vars.tilesize * size / 2), y + Mathf.range(Vars.tilesize * size / 2), rotation, heatColor);
                    }
                }
                if(overheat < overheatTime && !requireCompleteCooling){
                    super.updateTile();
                }else {
                    slowDownReload = 0;
                    coolDown();
                    if (linearWarmup) {
                        shootWarmup = Mathf.approachDelta(shootWarmup, 0, shootWarmupSpeed);
                    } else {
                        shootWarmup = Mathf.lerpDelta(shootWarmup, 0, shootWarmupSpeed);
                    }

                    unit.tile(this);
                    unit.rotation(rotation);
                    unit.team(team);
                    curRecoil = Mathf.approachDelta(curRecoil, 0, 1 / recoilTime);
                    recoilOffset.trns(rotation, -Mathf.pow(curRecoil, recoilPow) * recoil);

                    if (logicControlTime > 0) {
                        logicControlTime -= Time.delta;
                    }

                    if (overheat < 1) {
                        overheat = 0;
                        requireCompleteCooling = false;
                    }
                }
            }
        }

        public void coolDown(){
            if(isOverheat) {
                overheat = Mathf.approachDelta(overheat, 0, overheatCoolAmount * (1 + (liquids().current() == null ? 0 : liquids().current().heatCapacity)));
            }
        }

        @Override
        protected void updateShooting(){
            if(reloadCounter >= reload){
                BulletType type = peekAmmo();

                shoot(type);

                reloadCounter = 0f;
            }else{
                reloadCounter += (1 + speedupScl) * delta() * peekAmmo().reloadMultiplier * baseReloadSpeed();
                if(isOverheat) {
                    overheat = Mathf.approachDelta(overheat, overheatTime + 0.05f, efficiency * timeScale * ((speedupScl / maxSpeedupScl) * 1) / (1 + (liquids().current() == null ? 0 : liquids().current().heatCapacity)));
                    if (overheat > overheatTime) requireCompleteCooling = true;
                }
            }
        }

        @Override
        protected void shoot(BulletType type){
            super.shoot(type);

            slowDownReload = slowDownReloadTime;
            if(speedupScl < maxSpeedupScl){
                speedupScl += speedupPerShoot;
            }else speedupScl = maxSpeedupScl;
        }

        @Override
        protected void bullet(BulletType type, float xOffset, float yOffset, float angleOffset, Mover mover){
            super.bullet(type, xOffset, yOffset, angleOffset + Mathf.range(speedupScl * inaccuracyUp), mover);
        }
    }
}
