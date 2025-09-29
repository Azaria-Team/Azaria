package az.entities.bullets;

import arc.graphics.Color;
import arc.math.Angles;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.entities.Units;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Bullet;
import mindustry.gen.Groups;
import mindustry.gen.Teamc;
import mindustry.gen.Unit;
import mindustry.type.unit.MissileUnitType;

public class AntiMissileBulletType extends BasicBulletType {


    public AntiMissileBulletType(float speed, float damage, float homingRange, String missileSprite){
        this.homingRange = homingRange;
        this.sprite = missileSprite;
        trailChance = 0.8f;
        trailColor = Color.valueOf("6f6f6f");
        absorbable = false;
        hittable = false;
        collides = false;
        collidesTiles = false;
        collidesAir = false;
        collidesGround = false;
    }

    public AntiMissileBulletType(float speed, float damage, float homingRange){
        this(speed, damage, homingRange, "bullet");
    }

    public AntiMissileBulletType(){
        this(1f, 1f, 30f, "bullet");
    }

    @Override
    protected float calculateRange() {
        return homingRange * 2.5f;
    }

    @Override
    public void update(Bullet b) {
        Teamc target;
        target = Units.closestEnemy(b.team, b.x, b.y, homingRange, unit -> unit.type instanceof MissileUnitType);
        if(target == null) target = Groups.bullet.intersect(b.x - homingRange, b.y - homingRange, homingRange*2, homingRange*2).min(bt -> bt.team != b.team && (bt.type() != null && (bt.type().homingPower > 0 || bt.type().spawnUnit != null)), bt -> bt.dst2(b.x, b.y));
        if(target instanceof Bullet bt){
            b.vel.setAngle(Angles.moveToward(b.rotation(), b.angleTo(target), 30 * Time.delta));
            if(target.within(b.x, b.y, bt.type().homingRange * Math.max(Time.delta, 1))){
                bt.vel.setAngle(Angles.moveToward(bt.rotation(), target.angleTo(b), bt.type().homingPower * Time.delta * 50));
            }
            if(target.within(b.x, b.y, splashDamageRadius * bt.type().speed * Math.max(Time.delta, 1))){
                bt.time += bt.lifetime;
                b.remove();
            }
        }

        if(target instanceof Unit ut && ut.type instanceof MissileUnitType){
            b.vel.setAngle(Angles.moveToward(b.rotation(), b.angleTo(target), 30 * Time.delta));

            if(target.within(b.x, b.y, splashDamageRadius * Math.max(Time.delta, 1))){
                ut.kill();
                b.remove();
            }
        }
        if(Mathf.chanceDelta(trailChance)){
            trailEffect.at(b.x, b.y, trailParam, trailColor);
        }
    }
}
