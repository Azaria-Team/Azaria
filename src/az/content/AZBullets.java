package az.content;

import arc.graphics.Color;
import arc.graphics.g2d.Lines;
import arc.math.Interp;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import az.entities.bullets.AcceleratingLaserBulletType;
import az.entities.bullets.AntiMissileBulletType;
import az.graphics.AZPal;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.entities.Effect;
import mindustry.entities.bullet.*;
import mindustry.gen.Sounds;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.type.StatusEffect;

import static arc.graphics.g2d.Draw.color;
import static arc.graphics.g2d.Lines.stroke;

public class AZBullets {
    public static BulletType
            noneBullet, forceBullet, forceFerbiumBullet, hornBullet, antiMissileBullet, shrapnelBullet, ferbiumBullet, razeBullet, tideLaser;

    public static void load() {

        noneBullet = new BasicBulletType(0f, 0f){{
            shrinkX = shrinkY = 0f;
            width = 0f;
            height = 0f;
            despawnEffect = hitEffect = shootEffect = smokeEffect =  Fx.none;
            lifetime = 0f;
            speed = 0f;
            damage = 0f;
            sprite = "az-none";
            collidesAir = true;
            collidesGround = true;
            range = 17 * 8f;
            maxRange = 17 * 8f;
            despawnSound = hitSound = Sounds.none;
        }};
        forceBullet = new BasicBulletType(6f, 35f){{
            splashDamage = 30f;
            splashDamageRadius = 25f;
            sprite = "az-dagger-missile";
            trailInterval = 0.5f;
            trailEffect = AZFx.forceBulletTrail;
            hitEffect = AZFx.forceBulletHit;
            despawnEffect = AZFx.forceBulletDespawn;
            trailRotation = true;
            shrinkX = shrinkY = 0f;
            width = 8f;
            height = 12f;
            lifetime = 34 * 0.7f;
            collidesGround = true;
            collidesAir = true;
            hitSize = 3;
            homingPower = 0.3f;

            trailColor = AZPal.vogPinkBack;
            backColor = AZPal.vogPinkBack;
            frontColor = AZPal.vogPink;

            trailLength = 18;
            trailWidth = 1.7f;
        }};

        forceFerbiumBullet = new BasicBulletType(7f, 45f){{
            sprite = "az-dagger-missile";
            trailInterval = 0.4f;
            trailEffect = AZFx.forceFerbiumBulletTrail;
            hitEffect = AZFx.forceFerbiumBulletHit;
            despawnEffect = AZFx.forceFerbiumBulletDespawn;
            trailRotation = true;
            shrinkX = shrinkY = 0f;
            width = 8f;
            height = 14f;
            lifetime = 34 * 0.7f;
            collidesGround = true;
            collidesAir = true;
            hitSize = 3;
            homingPower = 0.3f;

            trailColor = AZPal.ferbiumBulletBack;
            backColor = AZPal.ferbiumBulletBack;
            frontColor = AZPal.ferbiumBullet;

            trailLength = 20;
            trailWidth = 1.7f;

            splashDamage = 45f;
            splashDamageRadius = 40f;
            fragBullets = 5;
            ammoMultiplier = 3f;
            fragBullet = new BasicBulletType(2f, 10) {{
                // sprite = "bullet";
                backColor = AZPal.ferbiumBulletBack;
                frontColor = AZPal.ferbiumBullet;
                width = 3f;
                height = 8f;
                shrinkX = 0;
                shrinkY = 0;
                hitSound = Sounds.explosion;
                hitEffect = Fx.none;
                despawnEffect = Fx.none;
                lifetime = 5;
            }};
        }};

        hornBullet = new BasicBulletType(5f, 35f){{
            hitEffect = AZFx.hornBulletHit;
            despawnEffect = AZFx.hornBulletDespawn;
            width = 52f;
            height = 5;
            shrinkY = -2f;
            shrinkX = 0.1f;
            hitSize = 9;
            knockback = 3.5f;
            ammoMultiplier = 0;

            shrinkInterp = Interp.reverse;
            lifetime = 26;
            collidesGround = true;
            collidesAir = true;
            impact = true;
            pierce = true;
            pierceCap = 2;
            trailLength = 5;
            trailWidth = 2;
            trailColor = AZPal.craside2;
            status = new StatusEffect("") {{
                disarm = true;
                speedMultiplier = 0.0f;
                statusDuration = 55f;
            }};
            backColor = AZPal.craside;
            frontColor = AZPal.craside2;
        }};

        ferbiumBullet = new ArtilleryBulletType(8f, 26f){{
            splashDamage = 10f;
            splashDamageRadius = 2f * 8;
            sprite = "az-vog";
            drag = 0.005f;
            trailInterval = 0.2f;
            trailEffect = AZFx.ferbiumBulletTrail;
            hitEffect = AZFx.ferbiumBulletHit;
            despawnEffect = AZFx.ferbiumBulletExplosion;
            trailRotation = true;
            shrinkX = shrinkY = 0f;
            width = 10f;
            height = 13f;
            lifetime = 76;
            collidesGround = true;
            collidesAir = true;
            hitSize = 2;
            shootEffect = Fx.shootSmall;
            hitSound = Sounds.none;
            despawnSound = Sounds.none;

            weaveScale = 8f;
            weaveMag = 0.8f;

            trailColor = AZPal.ferbiumBullet;
            backColor = AZPal.ferbiumBulletBack;
            frontColor = AZPal.ferbiumBullet;
        }};


        razeBullet = new BasicBulletType(7f, 40f){{
            sprite = "az-grenade";
            trailInterval = 0.5f;
            hitEffect = AZFx.superdanseBulletHit;
            despawnEffect = AZFx.superdanseBulletDespawn;
            trailRotation = false;
            shrinkX = shrinkY = 0f;
            width = 10f;
            height = 14f;
            lifetime = 38;
            collidesGround = false;
            collidesAir = true;
            hitSize = 5;
            //homingPower = 0.3f;

            backColor = AZPal.superdenseBulletBack;
            frontColor = AZPal.superdenseBullet;

            trailColor = AZPal.superdenseAlloy;
            trailEffect = Fx.none;
            trailLength = 10;
            trailWidth = 2f;
            ammoMultiplier = 3f;

            splashDamage = 50f;
            splashDamageRadius = 5.5f * 8f;
        }};

        antiMissileBullet = new AntiMissileBulletType(6f, 60f, 10 * Vars.tilesize){{
            sprite = "az-dagger-missile";
            shrinkX = shrinkY = 0f;
            speed = 6;
            damage = 60;
            width = 8f;
            height = 12f;
            lifetime = 35;
            collidesGround = true;
            collidesAir = true;
            hitSize = 3;
            homingPower = 0.3f;

            hitEffect = AZFx.hitExplosion;
            despawnEffect = AZFx.explosionSmall2;
            trailEffect = AZFx.vogTrail;
            trailRotation = true;
            trailInterval = 0.5f;

            splashDamage = 40f;
            splashDamageRadius = 25f;
        }};

        tideLaser = new RailBulletType(){{
            length = 210f;
            damage = 100f;
            hitColor = Color.valueOf("aaffe6");
            hitEffect = endEffect = Fx.hitBulletColor;
            pierceDamageFactor = 0.9f;

            smokeEffect = Fx.colorSpark;

            endEffect = new Effect(14f, e -> {
                color(e.color);
                Drawf.tri(e.x, e.y, e.fout() * 2.5f, 7f, e.rotation);
            });

            shootEffect = new Effect(20, e -> {
                color(e.color);
                float w = 1.2f + 7 * e.fout();

                Drawf.tri(e.x, e.y, w, 30f * e.fout(), e.rotation);
                color(e.color);

                for (int i : Mathf.signs) {
                    Drawf.tri(e.x, e.y, w * 2f, 10f * e.fout(), e.rotation + i * 90f);
                }

                Drawf.tri(e.x, e.y, w, 6f * e.fout(), e.rotation + 180f);
            });

            lineEffect = new Effect(30f, e -> {
                if (!(e.data instanceof Vec2 v)) return;

                color(e.color);
                stroke(e.fout() * 0.9f + 1.1f);

                Fx.rand.setSeed(e.id);
                for (int i = 0; i < 7; i++) {
                    Fx.v.trns(e.rotation, Fx.rand.random(8f, v.dst(e.x, e.y) - 8f));
                    Lines.lineAngleCenter(e.x + Fx.v.x, e.y + Fx.v.y, e.rotation + e.finpow(), e.foutpowdown() * 20f * Fx.rand.random(0.5f, 1f) + 0.3f);
                }

                e.scaled(24f, b -> {
                    stroke(b.fout() * 2.5f);
                    color(e.color);
                    Lines.line(e.x, e.y, v.x, v.y);
                });
            });
        }};
        shrapnelBullet = new ShrapnelBulletType(){{
            damage = 1f;
            length = 0f;
        }};
    }
}
