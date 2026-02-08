package azaria.entities.bullets;

import mindustry.entities.Damage;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Bullet;
import mindustry.gen.Hitboxc;
import mindustry.gen.Unit;

public class StickyBulletType extends BasicBulletType {
    public float stickTime = 60f;

    public StickyBulletType(float speed, float damage) {
        super(speed, damage);
        pierce = true;
        collides = true;
    }

    @Override
    public void hitEntity(Bullet b, Hitboxc entity, float initialHealth) {
        if (b.data instanceof Unit)
            return;

        if (entity instanceof Unit u) {
            b.data = u;
            b.time = 0f;
            b.lifetime = stickTime;
            b.vel.setZero();
        } else {
            super.hitEntity(b, entity, initialHealth);
        }
    }

    @Override
    public void update(Bullet b) {
        if (b.data instanceof Unit u) {
            if (u.dead || !u.isAdded()) {
                b.remove();
                return;
            }
            b.set(u.x, u.y);
            b.rotation(u.rotation);
        }
        super.update(b);
    }

    @Override
    public void despawned(Bullet b) {
        if (b.data instanceof Unit u) {
            u.damage(damage * b.damageMultiplier());

            if (splashDamageRadius > 0) {
                Damage.damage(b.team, b.x, b.y, splashDamageRadius, splashDamage * b.damageMultiplier(), collidesAir,
                        collidesGround);
            }

            hitEffect.at(b.x, b.y, b.rotation(), hitColor);
            despawnEffect.at(b.x, b.y, b.rotation(), hitColor);
        } else {
            super.despawned(b);
        }
    }
}
