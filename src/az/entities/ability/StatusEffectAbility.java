package az.entities.ability;

import arc.Core;
import arc.math.Angles;
import arc.scene.ui.layout.Table;
import arc.util.Strings;
import arc.util.Time;
import az.content.AZStatusEffects;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.Units;
import mindustry.entities.abilities.Ability;
import mindustry.gen.Unit;
import mindustry.type.StatusEffect;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

import static mindustry.Vars.tilesize;

public class StatusEffectAbility extends Ability {
    public StatusEffect effect;
    public float duration = 60, reload = 100, range = 20; //
    public boolean onShoot = false;
    public Effect applyEffect = Fx.none;
    public Effect activeEffect = Fx.overdriveWave;
    public float effectX, effectY;
    public boolean parentizeEffects, effectSizeParam = true;

    protected float timer;

    StatusEffectAbility(){}

    public StatusEffectAbility(StatusEffect effect, float duration, float reload, float range){
        this.duration = duration;
        this.reload = reload;
        this.range = range;
        this.effect = effect;
    }

    @Override
    public void addStats(Table t){
        t.add("[lightgray]" + Stat.reload.localized() + ": [white]" + Strings.autoFixed(60f / reload, 2) + " " + StatUnit.perSecond.localized());
        t.row();
        t.add("[lightgray]" + Stat.shootRange.localized() + ": [white]" +  Strings.autoFixed(range / tilesize, 2) + " " + StatUnit.blocks.localized());
        t.row();
        t.add(effect.emoji() + " " + effect.localizedName);
    }

    @Override
    public void update(Unit unit){
        timer += Time.delta;

        if(timer >= reload && (!onShoot || unit.isShooting)){
            Units.nearby(unit.team, unit.x, unit.y, range, other -> {
                other.apply(effect, duration);
                applyEffect.at(other, parentizeEffects);
            });

            float x = unit.x + Angles.trnsx(unit.rotation, effectY, effectX), y = unit.y + Angles.trnsy(unit.rotation, effectY, effectX);
            activeEffect.at(x, y, effectSizeParam ? range : unit.rotation, parentizeEffects ? unit : null);
/* /todo
            Units.nearbyEnemies(unit.team, unit.x - radius, unit.y - radius, radius * 2, radius * 2, enemy -> {
                if (enemy.within(unit, radius)) {
                    enemy.apply(enemyEffect, enemyDuration);
                }
            });

 */

            timer = 0f;
        }
    }
}

