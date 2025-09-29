package az.entities.bullets;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.util.Time;
import arc.util.Tmp;
import az.graphics.AZPal;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.entities.Damage;
import mindustry.entities.Effect;
import mindustry.entities.Units;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Bullet;
import mindustry.graphics.Layer;

import static az.content.AZFx.explosionEffect;
import static mindustry.Vars.tilesize;

public class ModEmpBulletType extends BasicBulletType {
    public float radius = 7f * tilesize;
    public float timeDuration = 5f * 60f;
    public float powerDamageScl = 1.3f, powerSclDecrease = 0.5f;
    public Effect hitPowerEffect = Fx.none, chainEffect = Fx.none/*chainEmp*/;
    public boolean hitUnits = true;
    public float unitDamageScl = 1f;
    public float effectLifetime = 5f * 60f;

    //effect
    public Color explosionBottomColor = AZPal.droneEMIBulletBottom;

    public ModEmpBulletType(float speed, float damage, String bulletSprite){
        super(speed, damage);
        this.sprite = bulletSprite;
        despawnHit = true;
        layer = Layer.bullet;
    }

    public ModEmpBulletType(float speed, float damage){
        this(speed, damage, "bullet");
    }


    @Override
    public void hit(Bullet b, float x, float y){
        super.hit(b, x, y);

        if(!b.absorbed){
            explosionEffect(radius, effectLifetime, frontColor, backColor, explosionBottomColor).at(b.x, b.y);
            Vars.indexer.allBuildings(x, y, radius, other -> {
                if(other.power != null){
                    var absorber = Damage.findAbsorber(b.team, x, y, other.x, other.y);
                    if(absorber != null){
                        other = absorber;
                    }

                    if(other.power != null && other.power.graph.getLastPowerProduced() > 0f){
                        other.applySlowdown(powerSclDecrease, timeDuration);
                        other.damage(damage * powerDamageScl);
                        hitPowerEffect.at(other.x, other.y, b.angleTo(other), hitColor);
                        chainEffect.at(x, y, 0, hitColor, other);
                    }
                }
            });

            if(hitUnits){
                Units.nearbyEnemies(b.team, x, y, radius, other -> {
                    if(other.team != b.team && other.hittable()){
                        var absorber = Damage.findAbsorber(b.team, x, y, other.x, other.y);
                        if(absorber != null){
                            return;
                        }

                        hitPowerEffect.at(other.x, other.y, b.angleTo(other), hitColor);
                        chainEffect.at(x, y, 0, hitColor, other);
                        other.damage(damage * unitDamageScl);
                        other.apply(status, statusDuration);
                    }
                });
            }
        }
    }
}
