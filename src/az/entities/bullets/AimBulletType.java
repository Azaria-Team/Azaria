package az.entities.bullets;

import arc.util.Time;
import arc.util.Tmp;
import az.utils.Utils;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Bullet;
import mindustry.gen.Posc;
import mindustry.gen.Unitc;
import mindustry.logic.Ranged;
import mindustry.world.blocks.defense.turrets.Turret;

public class AimBulletType extends BasicBulletType {

    public AimBulletType(float speed, float damage){
        super(speed, damage);
    }

    @Override
    public void update(Bullet b){
        updateTrail(b);
        updateHoming(b);
        updateWeaving(b);
        updateTrailEffects(b);
        updateBulletInterval(b);

        if(!(b.owner instanceof Ranged)) return;
        Tmp.v1.set(b.x, b.y);

        if(b.owner instanceof Utils.Targeting){
            Tmp.v1.set(((Utils.Targeting) b.owner).targetPos());
        }
        else if(b.owner instanceof Turret.TurretBuild) {
            Tmp.v1.set(((Turret.TurretBuild) b.owner).targetPos.x, ((Turret.TurretBuild) b.owner).targetPos.y);
        }
        else if (b.owner instanceof Unitc){
            Tmp.v1.set(((Unitc) b.owner).aimX(), ((Unitc) b.owner).aimY());
        }
        Tmp.v3.set(((Posc) b.owner()).x(), ((Posc) b.owner()).y());
        Tmp.v1.sub(Tmp.v3).clamp(0, ((Ranged) b.owner).range()).add(Tmp.v3);
        b.vel.add(Tmp.v2.trns(b.angleTo(Tmp.v1), b.type.homingPower * Time.delta)).clamp(0, b.type.speed);
        if(b.dst(Tmp.v3.x, Tmp.v3.y) >= ((Ranged) b.owner).range() + b.type.speed + 3) b.time += b.lifetime/100 * Time.delta;
    }

}
