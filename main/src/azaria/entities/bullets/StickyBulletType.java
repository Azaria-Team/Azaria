package azaria.entities.bullets;

import arc.math.Mathf;
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
        if (b.data instanceof StickData)
            return;

        if (entity instanceof Unit u) {
            float dx = b.x - u.x;
            float dy = b.y - u.y;

            float cos = Mathf.cosDeg(-u.rotation);
            float sin = Mathf.sinDeg(-u.rotation);

            float localX = dx * cos - dy * sin;
            float localY = dx * sin + dy * cos;

            float relativeRotation = b.rotation() - u.rotation;

            StickData data = new StickData(u, localX, localY, relativeRotation);
            b.data = data;

            b.time = 0f;
            b.lifetime = stickTime;
            b.vel.setZero();
        } else {
            super.hitEntity(b, entity, initialHealth);
        }
    }

    @Override
    public void update(Bullet b) {
        if (b.data instanceof StickData data) {
            Unit u = data.unit;
            if (u.dead || !u.isAdded()) {
                b.remove();
                return;
            }

            float cos = Mathf.cosDeg(u.rotation);
            float sin = Mathf.sinDeg(u.rotation);

            float worldOffsetX = data.x * cos - data.y * sin;
            float worldOffsetY = data.x * sin + data.y * cos;

            b.set(u.x + worldOffsetX, u.y + worldOffsetY);
            b.rotation(u.rotation + data.rotation);
        }
        super.update(b);
    }

    @Override
    public void despawned(Bullet b) {
        if (b.data instanceof StickData data) {
            Unit u = data.unit;
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

    public static class StickData {
        public Unit unit;
        public float x, y, rotation;

        public StickData(Unit unit, float x, float y, float rotation) {
            this.unit = unit;
            this.x = x;
            this.y = y;
            this.rotation = rotation;
        }
    }
}
