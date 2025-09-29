package az.world.blocks.defense;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.util.Time;
import arc.util.Timer;
import az.content.AZStatusEffects;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.entities.Effect;
import mindustry.entities.Units;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Posc;
import mindustry.gen.Unitc;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.logic.Ranged;
import mindustry.type.Liquid;
import mindustry.type.StatusEffect;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

import static mindustry.Vars.net;
import static mindustry.Vars.tilesize;

public class NavalMine extends Block{
    public float tileDamage = 80f;

    public float range = 60f;
    public float statusDuration = 0f;
    public StatusEffect status = StatusEffects.none;
    public Effect acceptEffect = Fx.none;

    public float damage = 130;
    public float teamAlpha = 0.3f;
    public int explosionTime = 5 * 60;

    public final int timerToggle = timers++;

    public float explosionDelay = 60;
    public float triggerDelay = 1f * 60;

    public NavalMine(String name){
        super(name);
        update = true;
        destructible = true;
        solid = false;
        targetable = false;
        outlinedIcon = 1;
    }

    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation){
        return tile.floor().isLiquid;
    }

    @Override
    public void setStats(){
        super.setStats();
        stats.add(Stat.range, range / tilesize, StatUnit.blocks);
        stats.add(Stat.damage, damage);
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        super.drawPlace(x, y, rotation, valid);
        Drawf.dashCircle(x * tilesize + offset, y * tilesize + offset, range, Pal.accent);
    }

    @Override
    public void drawOverlay(float x, float y, int rotation){
        Drawf.dashCircle(x * tilesize + offset, y * tilesize + offset, range, Pal.accent);
    }

    public class NavalMineBuild extends Building implements Ranged {

        public Posc target;
        protected boolean triggered = false; // Флаг активации мины
        protected float delayTimer = 0f;     // Таймер задержки перед взрывом
        protected float blinkTimer = 0f;     // Таймер для мерцания

        @Override
        public void drawTeam() {
            // no
        }

        public NavalMineBuild() {
            // make sure it is staggered
            timer.reset(timerToggle, Mathf.random(explosionTime));
        }

        @Override
        public float range() {
            return range;
        }

        protected void findTarget() {
            target = Units.closestTarget(team, x, y, range());
        }

        protected boolean validateTarget() {
            return !(Units.invalidateTarget(target, team, x, y) || !(target instanceof Unitc));
        }

        @Override
        public void updateTile() {
            if (!validateTarget()) target = null;

            // Если мина активирована, но еще не взорвалась
            if (triggered) {
                delayTimer -= Time.delta;
                blinkTimer += Time.delta;

                // Взрыв при завершении таймера
                if (delayTimer <= 0f) {
                    explosion();
                    triggered = false; // Сбрасываем флаг
                }
            }

            // Активация мины при обнаружении цели
            if (validateTarget() && !triggered) {
                triggered = true;
                delayTimer = triggerDelay; // Устанавливаем таймер задержки
            }

            // Поиск цели
            if (timer(0, explosionDelay)) {
                findTarget();
            }
        }

        protected void explosion() {
            Units.nearbyEnemies(team, x, y, range, unit -> {
                if (!unit.isFlying() && !unit.hovering) {
                    acceptEffect.at(unit);
                    unit.damage(damage);
                    damage(tileDamage);
                }
            });
        }

        @Override
        public void draw() {
            super.draw();
            Draw.color(team.color, teamAlpha);
            Draw.rect(teamRegion, x, y);
            Draw.color();
        }

        @Override
        public void drawCracks() {
            // no
        }
    }
}