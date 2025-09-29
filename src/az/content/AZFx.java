package az.content;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.math.Rand;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import arc.util.Time;
import arc.util.Tmp;
import az.graphics.AZPal;
import mindustry.core.Renderer;
import mindustry.entities.Effect;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;

import static arc.graphics.g2d.Draw.alpha;
import static arc.graphics.g2d.Draw.color;
import static arc.graphics.g2d.Lines.lineAngle;
import static arc.graphics.g2d.Lines.stroke;
import static arc.math.Angles.randLenVectors;
import static mindustry.graphics.Drawf.light;

public class AZFx {
    public static final Rand rand = new Rand();
    public static final Vec2 v = new Vec2();
    public static final Effect

            forsDrillEffect = new Effect(30, e -> {
        color(Color.white, AZPal.fors, e.fin());
        randLenVectors(e.id, 5, 2f + 14f * e.finpow(), (x, y) ->
                Fill.circle(e.x + x, e.y + y, e.fout() * 2.5f + 0.5f));
    }),

    smokeEvaporatorSmall = new Effect(50, e -> {
        color(Color.white, e.fin());
        randLenVectors(e.id, 3, 2f + 8f * e.finpow(), (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * 2f + 0.2f);
            Fill.circle(e.x + x / 2f, e.y + y / 2f, e.fout());
        });
    }),
            smokeEvaporatorBig = new Effect(30, e -> {
                color(Color.white, e.fin());
                randLenVectors(e.id, 5, 2f + 12f * e.finpow(), (x, y) -> {
                    Fill.circle(e.x + x, e.y + y, e.fout() * 2f + 0.2f);
                    Fill.circle(e.x + x / 2f, e.y + y / 2f, e.fout());
                });
            }),

    crasideBrewerSmoke = new Effect(120, e -> {
        color(AZPal.craside, AZPal.craside2, e.fin());
        randLenVectors(e.id, 10, 1f + 6f * e.finpow(), (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * 2 + 0.1f);
            Fill.circle(e.x + x / 0.9f, e.y + y / 3f, e.fout());
        });
        randLenVectors(e.id, 8, 1f + 10f * e.finpow(), (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * 4 + 0.1f);
            Fill.circle(e.x + x / 0.9f, e.y + y / 6f, e.fout());
        });
    }).layer(Layer.block + 0.05f),

    hitExplosion = new Effect(30, e -> {
        color(AZPal.vogPink);
        e.scaled(7, i -> {
            stroke(3f * i.fout());
            Lines.circle(e.x, e.y, 3f + i.fin() * 10f);
        });

        color(AZPal.vogPink);

        randLenVectors(e.id, 7, 2f + 19f * e.finpow(), (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * 3f + 0.5f);
            Fill.circle(e.x + x / 2f, e.y + y / 2f, e.fout());
        });

        color(AZPal.vogPink, AZPal.vogPinkBack, Color.pink, e.fin());
        stroke(1.5f * e.fout());

        randLenVectors(e.id + 1, 8, 1f + 23f * e.finpow(), (x, y) -> {
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 1f + e.fout() * 3f);
        });
    }),

    explosionSmall2 = new Effect(20, e -> {
        color(AZPal.vogPink);
        e.scaled(8, i -> {
            stroke(2f * i.fout());
            Lines.circle(e.x, e.y, 2f + i.fin() * 5f);
        });

        color(AZPal.vogPink, AZPal.vogPinkBack, Color.pink, e.fin());
        stroke(1.01f * e.fout());

        randLenVectors(e.id + 1, 4, 1f + 16 * e.finpow(), (x, y) -> {
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 1f + e.fout() * 2f);
        });
    }),

    massiveExplosionAurora = new Effect(30, e -> {
        color(AZPal.forsBack);

        e.scaled(7, i -> {
            stroke(3f * i.fout());
            Lines.circle(e.x, e.y, 4f + i.fin() * 30f);
        });

        color(AZPal.fors);
        randLenVectors(e.id, 14, 2f + 30f * e.finpow(), (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * 4f + 0.5f);
        });

        color(AZPal.forceBullet);
        stroke(e.fout());

        randLenVectors(e.id + 1, 6, 1f + 29f * e.finpow(), (x, y) -> {
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 1f + e.fout() * 4f);
        });

        Drawf.light(e.x, e.y, 50f, AZPal.forceBullet, 0.8f * e.fout());
    }),

    massiveExplosionAurora2 = new Effect(60, e -> {
        color(AZPal.forsBack);

        color(AZPal.fors);

        randLenVectors(e.id, 10, 2f + 30f * e.finpow(), (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * 6f + 0.5f);
        });

        color(AZPal.forceBullet);
        stroke(e.fout());

        randLenVectors(e.id + 1, 6, 1f + 29f * e.finpow(), (x, y) -> {
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 1f + e.fout() * 4f);
        });

        Drawf.light(e.x, e.y, 50f, AZPal.forceBullet, 0.8f * e.fout());
    }),

    scatheExplosionAurora = new Effect(60f, 160f, e -> {
        color(AZPal.forceBullet);
        stroke(e.fout() * 3f);
        float circleRad = 3f + e.finpow() * 30f;
        Lines.circle(e.x, e.y, circleRad);
    }),

    scatheLightAurora = new Effect(60f, 160f, e -> {
        float circleRad = 3f + e.finpow() * 30f;

        color(AZPal.forceBulletBack, e.foutpow());
        Fill.circle(e.x, e.y, circleRad);
    }).layer(Layer.bullet + 2f),

    smallestBlueExplosion= new Effect(20, e -> {
        color(AZPal.droneBullet);

        color(AZPal.droneBullet, AZPal.droneBulletBack, e.fin());
        stroke(0.50f * e.fout());

        randLenVectors(e.id + 1, 2, 1f + 5 * e.finpow(), (x, y) -> {
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 0.5f + e.fout() * 1f);
        });
    }),

    smallBlueExplosion = new Effect(20, e -> {
        color(AZPal.droneBullet);
        e.scaled(8, i -> {
            stroke(2f * i.fout());
            Lines.square(e.x, e.y, 2f + i.fin() * 5f, e.rotation * Mathf.random(20) * Time.delta);
        });

        color(AZPal.droneBullet, AZPal.droneBulletBack, e.fin());
        stroke(1.01f * e.fout());

        randLenVectors(e.id + 1, 4, 1f + 16 * e.finpow(), (x, y) -> {
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 1f + e.fout() * 2f);
        });
    }),

    smallBlueExplosion2 = new Effect(20, e -> {
        color(AZPal.droneBullet);
        e.scaled(8, i -> {
            stroke(2f * i.fout());
            Lines.square(e.x, e.y, 1f + i.fin() * 5f, e.rotation * Mathf.random(20) * Time.delta);
        });

        color(AZPal.droneBullet, AZPal.droneBulletBack, e.fin());
        stroke(1.01f * e.fout());

        randLenVectors(e.id + 1, 2, 1f + 5 * e.finpow(), (x, y) -> {
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 1f + e.fout() * 1.5f);
        });
    }),

    blueHitExplosionNormal = new Effect(30, e -> {
        color(AZPal.droneBullet);
        e.scaled(10, i -> {
            stroke(3f * i.fout());
            Lines.square(e.x, e.y, 5f + i.fin() * 15f, e.rotation * Mathf.random(30) * Time.delta);
        });

        color(AZPal.droneBullet);

        color(AZPal.droneBullet, AZPal.droneBulletBack, e.fin());
        stroke(1.5f * e.fout());

        randLenVectors(e.id + 3, 8, 3f + 25f * e.finpow(), (x, y) -> {
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 2f + e.fout() * 4f);
        });
    }),

    ferbiumBulletHit = new Effect(30, e -> {
        color(AZPal.droneBullet);
        e.scaled(10, i -> {
            stroke(3f * i.fout());
        });

        color(AZPal.droneBullet);

        color(AZPal.droneBullet, AZPal.droneBulletBack, e.fin());
        stroke(1.5f * e.fout());

        randLenVectors(e.id + 3, 8, 3f + 25f * e.finpow(), (x, y) -> {
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 2f + e.fout() * 4f);
        });
    }),


    smallblueHitExplosion = new Effect(30, e -> {
        color(AZPal.droneBullet);
        e.scaled(7, i -> {
            stroke(3f * i.fout());
            Lines.square(e.x, e.y, 3f + i.fin() * 10f, e.rotation * Mathf.random(20) * Time.delta);
        });

        color(AZPal.droneBullet);

        color(AZPal.droneBullet, AZPal.droneBulletBack, e.fin());
        stroke(1.5f * e.fout());

        randLenVectors(e.id + 1, 8, 1f + 23f * e.finpow(), (x, y) -> {
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 1f + e.fout() * 3f);
        });
    }),

    smallblueHitExplosion2 = new Effect(30, e -> {
        color(AZPal.droneBullet);
        e.scaled(7, i -> {
            stroke(3f * i.fout());
            Lines.square(e.x, e.y, 2f + i.fin() * 10f, e.rotation * Mathf.random(20) * Time.delta);
        });

        color(AZPal.droneBullet);

        color(AZPal.droneBullet, AZPal.droneBulletBack, e.fin());
        stroke(1.5f * e.fout());

        randLenVectors(e.id + 1, 3, 1f + 10f * e.finpow(), (x, y) -> {
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 1f + e.fout() * 2f);
        });
    }),

    blueExplosionNormal = new Effect(20, e -> {
        color(AZPal.droneBullet);
        e.scaled(10, i -> {
            stroke(2f * i.fout());
            Lines.square(e.x, e.y, 3f + i.fin() * 8f, e.rotation * Mathf.random(20) * Time.delta);
        });

        color(AZPal.droneBullet, AZPal.droneBulletBack, e.fin());
        stroke(2f * e.fout());

        randLenVectors(e.id + 1, 6, 2f + 19
                * e.finpow(), (x, y) -> {
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 2f + e.fout() * 2f);
        });
    }),

    ferbiumBulletExplosion = new Effect(20, e -> {
        color(AZPal.droneBullet);
        e.scaled(10, i -> {
            stroke(2f * i.fout());
        });

        color(AZPal.droneBullet, AZPal.droneBulletBack, e.fin());
        stroke(2f * e.fout());

        randLenVectors(e.id + 1, 6, 2f + 19
                * e.finpow(), (x, y) -> {
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 2f + e.fout() * 2f);
        });
    }),


    smallBlueExplosionEMI = new Effect(50, e -> {
        color(AZPal.droneEMIBullet);

        color(AZPal.droneEMIBullet, AZPal.droneEMIBulletBack, e.fin());
        stroke(e.fout());

        randLenVectors(e.id + 1, 3, 2f + 3 * e.finpow(), (x, y) -> {
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 1f + e.fout() * 2f);
        });
    }),

    blueExplosionEMI = new Effect(50, e -> {
        color(AZPal.droneEMIBullet);

        randLenVectors(e.id, 10, 2f + 30f * e.fin(), (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * 6f + 0.5f);
        });

        color(AZPal.droneEMIBullet, AZPal.droneEMIBulletBack, e.fin());
        stroke(3f * e.fout());

        randLenVectors(e.id + 1, 7, 2f + 19
                * e.finpow(), (x, y) -> {
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 4f + e.fout() * 2f);
        });
    }),

    smallblueExplosionEMI = new Effect(50, e -> {
        color(AZPal.droneEMIBullet);

        randLenVectors(e.id, 10, 2f + 10f * e.fin(), (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * 2f + 0.5f);
        });

        color(AZPal.droneEMIBullet, AZPal.droneEMIBulletBack, e.fin());
        stroke(3f * e.fout());

        randLenVectors(e.id + 1, 7, 2f + 6
                * e.finpow(), (x, y) -> {
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 4f + e.fout() * 2f);
        });
    }),

    smallGreenExplosion = new Effect(20, e -> {
        color(AZPal.unmakerColor);
        e.scaled(10, i -> {
            stroke(2f * i.fout());
        });

        color(AZPal.unmakerColor, Color.white, e.fin());
        stroke(1.01f * e.fout());

        randLenVectors(e.id + 1, 10, 1f + 15 * e.finpow(), (x, y) -> {
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 0.5f + e.fout() * 1.5f);
        });
    }),

    smallOrangeBulletExplosion = new Effect(20, e -> {
        color(AZPal.craside);
        e.scaled(8, i -> {
            stroke(2f * i.fout());
        });

        color(AZPal.craside, AZPal.craside2, e.fin());
        stroke(1.01f * e.fout());

        randLenVectors(e.id + 1, 6, 1f + 20 * e.finpow(), (x, y) -> {
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 1f + e.fout() * 1.5f);
        });
    }),

    orangeLightningExplosion = new Effect(50, e -> {
        color(AZPal.craside3);
        e.scaled(30, i -> {
            stroke(2f * i.fout());
            Lines.circle(e.x, e.y, 20f + i.fin() * 5f);
        });

        color(AZPal.craside, AZPal.craside2, e.fin());
        stroke(1.01f * e.fout());

        for (int i = 0; i < 2; i++) {
            Drawf.tri(e.x, e.y, 2.5f, 15f * e.finpow(), e.rotation * 90 * i + e.finpow());
        }
        randLenVectors(e.id + 1, 10, 2f + 33 * e.finpow(), (x, y) -> {
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 2f + e.fout() * 1.5f);
        });
    }),

    blueEMICharge = new Effect(40f, 40f, e -> {
        color(AZPal.droneEMIBullet, AZPal.droneEMIBulletBack, e.fin());
        stroke(e.fin() * 3f);

        randLenVectors(e.id, 5, 15f * e.fout(), (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fin() * 2f);
            Drawf.light(e.x + x, e.y + y, e.fin() * 4f, AZPal.droneEMIBulletBottom, 0.7f);
        });
    }).followParent(true).rotWithParent(true),

    custodianCharge = new Effect(20f, 20f, e -> {
        color(AZPal.craside2, AZPal.craside3, e.fin());
        stroke(e.fin() * 1.1f);

        randLenVectors(e.id, 8, 25f * e.fout(), (x, y) -> {
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 2f + e.fin() * 0.5f);
            Drawf.light(e.x + x, e.y + y, e.fin() * 4f, AZPal.craside3, 0.7f);
        });
    }),

    bulwarkCharge = new Effect(20f, 15f, e -> {
        color(AZPal.craside2, AZPal.craside3, e.fin());
        stroke(e.fin() * 1.1f);

        randLenVectors(e.id, 10, 30f * e.fout(), (x, y) -> {
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 5f + e.fin() * 0.5f);
            Drawf.light(e.x + x, e.y + y, e.fin() * 4f, AZPal.craside3, 0.7f);
        });
    }).followParent(true).rotWithParent(true),

    vogTrail = new Effect(15, e -> {
        color(AZPal.vogPink, AZPal.vogPinkBack, e.fin());
        stroke(0.3f + e.fout() * 0.7f);
        rand.setSeed(e.id);

        for(int i = 0; i < 1; i++){
            float rot = e.rotation + rand.range(10f) + 180f;
            v.trns(rot, rand.random(e.fin() * 18f));
            lineAngle(e.x + v.x, e.y + v.y, rot, e.fout() * rand.random(1f, 4f) + 1f);
        }
    }),

    aimMissileTrail = new Effect(20f, 50f, e -> {
        color(AZPal.vogPink, AZPal.vogPinkBack, Color.pink,  e.fin() * e.fin());

        randLenVectors(e.id, 4, 1f + e.finpow() * 15, e.rotation + 180, 7f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y, 0.30f + e.fout() * 1.1f);
        });
    }),

    unmakerBulletTrail = new Effect(50, e -> {
        color(AZPal.unmakerColor);
        Fill.circle(e.x, e.y, e.rotation * e.fout());
    }).layer(Layer.bullet - 0.001f), //below bullets

    shootForce = new Effect(10, e -> {
        color(AZPal.vogPink, e.fin());
        float w = 1.3f + 10 * e.fout();
        Drawf.tri(e.x, e.y, w, 20f * e.fout(), e.rotation);
        Drawf.tri(e.x, e.y, w, 5f * e.fout(), e.rotation + 180f);
    }),

    shootSmokeForce = new Effect(60f, e -> {
        rand.setSeed(e.id);
        for(int i = 0; i < 10; i++){
            v.trns(e.rotation + rand.range(10f), rand.random(e.finpow() * 20f));
            e.scaled(e.lifetime * rand.random(0.1f, 0.3f), b -> {
                color(e.color, AZPal.forceBullet, b.fin());
                Fill.circle(e.x + v.x, e.y + v.y, b.fout() * 2f + 0.3f);
            });
        }
    }),

    shootSmokeAuroraMissile = new Effect(130f, 300f, e -> {
        color(AZPal.forceBullet);
        alpha(0.5f);
        rand.setSeed(e.id);
        for(int i = 0; i < 35; i++){
            v.trns(e.rotation + 180f + rand.range(12f), rand.random(e.finpow() * 30f)).add(rand.range(0.8f), rand.range(0.6f));
            e.scaled(e.lifetime * rand.random(0.1f, 1f), b -> {
                Fill.circle(e.x + v.x, e.y + v.y, b.fout() * 2f + 0.2f);
            });
        }
    }),

    shootBigAurora = new Effect(9, e -> {
        color(AZPal.forceBullet, AZPal.forceBulletBack, e.fin());
        float w = 1.2f + 7 * e.fout();
        Drawf.tri(e.x, e.y, w, 10f * e.fout(), e.rotation);
        Drawf.tri(e.x, e.y, w, 2f * e.fout(), e.rotation + 180f);
    }),


    forceBulletTrail = new Effect(25, e -> {
        color(AZPal.forceBullet, AZPal.forceBulletBack, e.fin());

        stroke(0.6f + e.fout() * 0.9f);
        rand.setSeed(e.id);

        color(AZPal.forceBullet, AZPal.forceBulletBack, e.fin());
        for(int i = 0; i < 1; i++){
            float rot = e.rotation + rand.range(20f) + 180f;
            v.trns(rot, rand.random(e.fin() * 20f));
            lineAngle(e.x + v.x, e.y + v.y, rot, e.fout() * rand.random(2f, 3f) + 1f);
        }

    }),

    ferbiumBulletTrail = new Effect(25, e -> {
        color(AZPal.ferbiumBullet, AZPal.ferbiumBulletBack, e.fin());

        stroke(0.6f + e.fout() * 0.9f);
        rand.setSeed(e.id);

        color(AZPal.ferbiumBullet, AZPal.ferbiumBulletBack, e.fin());
        for(int i = 0; i < 1; i++){
            float rot = e.rotation + rand.range(5f) + 180f;
            v.trns(rot, rand.random(e.fin() * 10f));
            lineAngle(e.x + v.x, e.y + v.y, rot, e.fout() * rand.random(1f, 2f) + 1f);
        }
    }),


    forceFerbiumBulletTrail = new Effect(25, e -> {
        color(AZPal.ferbiumBullet, AZPal.ferbiumBulletBack, e.fin());

        stroke(0.6f + e.fout() * 0.9f);
        rand.setSeed(e.id);

        color(AZPal.ferbiumBullet, AZPal.ferbiumBulletBack, e.fin());
        for(int i = 0; i < 1; i++){
            float rot = e.rotation + rand.range(25f) + 180f;
            v.trns(rot, rand.random(e.fin() * 22f));
            lineAngle(e.x + v.x, e.y + v.y, rot, e.fout() * rand.random(1f, 3f) + 1f);
        }

    }),

    forceBulletHit = new Effect(30, e -> {
        color(AZPal.fors);
        e.scaled(10, i -> {
            stroke(4f * i.fout());
            Lines.circle(e.x, e.y, 7f + i.fin() * 15f);
        });

        color(AZPal.fors);

        randLenVectors(e.id, 10, 3f + 20f * e.finpow(), (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * 2f + 0.7f);
            Fill.circle(e.x + x / 2f, e.y + y / 2f, e.fout());
        });

        color(AZPal.vogPink, AZPal.vogPinkBack, Color.pink, e.fin());
        stroke(1.5f * e.fout());
    }),

    forceFerbiumBulletHit = new Effect(30, e -> {
        color(AZPal.ferbiumBullet);
        e.scaled(10, i -> {
            stroke(4f * i.fout());
            Lines.circle(e.x, e.y, 7f + i.fin() * 15f);
        });

        color(AZPal.ferbiumBulletBack);

        randLenVectors(e.id, 10, 3f + 20f * e.finpow(), (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * 2f + 0.7f);
            Fill.circle(e.x + x / 2f, e.y + y / 2f, e.fout());
        });

        color(AZPal.ferbiumBullet, AZPal.ferbiumBulletBack, e.fin());
        stroke(1.5f * e.fout());
    }),

    smallForceFerbiumBulletHit = new Effect(30, e -> {
        color(AZPal.ferbiumBullet);
        e.scaled(6, i -> {
            stroke(2f * i.fout());
            Lines.circle(e.x, e.y, 3f + i.fin() * 7f);
        });

        color(AZPal.ferbiumBulletBack);

        randLenVectors(e.id, 5, 1f + 10f * e.finpow(), (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * 1.1f + 0.7f);
            Fill.circle(e.x + x / 2f, e.y + y / 2f, e.fout());
        });

        color(AZPal.ferbiumBullet, AZPal.ferbiumBulletBack, Color.purple, e.fin());
        stroke(1.1f * e.fout());
    }),

    superdanseBulletHit = new Effect(30, e -> {
        color(AZPal.superdenseAlloy);
        e.scaled(10, i -> {
            stroke(4f * i.fout());
            Lines.circle(e.x, e.y, 3f + i.fin() * 10f);
        });

        color(AZPal.superdenseAlloy);
        randLenVectors(e.id, 7, 8f + 28f * e.finpow(), (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * 4f + 1f);
            Fill.circle(e.x + x / 3f, e.y + y / 3f, e.fout());
        });

        color(AZPal.superdenseBullet, AZPal.superdenseBulletBack, Color.gray, e.fin());
        randLenVectors(e.id + 1, 10, 1f + 25f * e.finpow(), (x, y) -> {
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 3f + e.fout() * 3f);
        });
    }),

    blueExplosionEMIHit = new Effect(50, e -> {
        color(AZPal.droneEMIBullet);

        color(AZPal.droneEMIBullet, AZPal.droneEMIBulletBack, e.fin());
        stroke(3f * e.fout());

        randLenVectors(e.id + 1, 6, 2f + 19
                * e.finpow(), (x, y) -> {
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 2f + e.fout() * 2f);
        });
    }),

    smallOrangeBulletHit = new Effect(20, e -> {
        color(AZPal.craside);
        e.scaled(8, i -> {
            stroke(2f * i.fout());
            Lines.circle(e.x, e.y, 3f + i.fin() * 5f);
        });

        color(AZPal.craside, AZPal.craside2, e.fin());
        stroke(1.01f * e.fout());

        randLenVectors(e.id + 1, 7, 1f + 23 * e.finpow(), (x, y) -> {
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 1f + e.fout() * 1.5f);
        });
    }),

    orangeLightningHit = new Effect(80, e -> {
        color(AZPal.craside3);
        e.scaled(30, i -> {
            stroke(2f * i.fout());
            Lines.circle(e.x, e.y, 20f + i.fin() * 5f);
        });

        color(AZPal.craside, AZPal.craside2, e.fin());
        stroke(1.01f * e.fout());

        for (int i = 0; i < 4; i++) {
            Drawf.tri(e.x, e.y, 2.5f, 15f * e.fout(), e.rotation * 90f * i + e.fout() * 360);
        }
        randLenVectors(e.id + 1, 10, 2f + 33 * e.finpow(), (x, y) -> {
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 2f + e.fout() * 1.5f);

        });
        randLenVectors(e.id + 1, 3, 2f + 25 * e.finpow(), (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * 3f * 1.1f);
        });
    }),

    forceBulletDespawn = new Effect(30, e -> {
        color(AZPal.fors);
        e.scaled(10, i -> {
            stroke(4f * i.fout());
            Lines.circle(e.x, e.y, 7f + i.fin() * 15f);
        });

        color(AZPal.vogPink, AZPal.vogPinkBack, Color.pink, e.fin());
        stroke(1.5f * e.fout());

        randLenVectors(e.id + 1, 10, 1f + 30f * e.finpow(), (x, y) -> {
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 2f + e.fout() * 3f);
        });
    }),

    superdanseBulletDespawn = new Effect(30, e -> {
        color(AZPal.superdenseAlloy);
        e.scaled(10, i -> {
            stroke(4f * i.fout());
            Lines.circle(e.x, e.y, 8f + i.fin() * 15f);
        });

        color(AZPal.superdenseBullet, AZPal.superdenseBulletBack, Color.gray, e.fin());
        stroke(2f * e.fout());

        randLenVectors(e.id + 1, 5, 1f + 20f * e.finpow(), (x, y) -> {
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 1f + e.fout() * 3f);
        });
    }),


    forceFerbiumBulletDespawn = new Effect(30, e -> {
        color(AZPal.ferbiumBullet);
        e.scaled(10, i -> {
            stroke(4f * i.fout());
            Lines.circle(e.x, e.y, 7f + i.fin() * 15f);
        });

        color(AZPal.ferbiumBullet, AZPal.ferbiumBulletBack, e.fin());
        stroke(1.5f * e.fout());

        randLenVectors(e.id + 1, 10, 1f + 30f * e.finpow(), (x, y) -> {
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 2f + e.fout() * 3f);
        });
    }),

    smallForceFerbiumBulletDespawn = new Effect(30, e -> {
        color(AZPal.ferbiumBullet);
        e.scaled(5, i -> {
            stroke(2f * i.fout());
            Lines.circle(e.x, e.y, 3f + i.fin() * 5f);
        });

        color(AZPal.ferbiumBullet, AZPal.ferbiumBulletBack, Color.purple, e.fin());
        stroke(1.1f * e.fout());

        randLenVectors(e.id + 1, 5, 1f + 15f * e.finpow(), (x, y) -> {
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 1.2f + e.fout() * 3f);
        });
    }),

    hornBulletHit = new Effect(30, e -> {
        color(AZPal.craside);
        e.scaled(10, i -> {
            stroke(4f * i.fout());
            //Lines.circle(e.x, e.y, 7f + i.fin() * 15f);
        });

        color(AZPal.craside, AZPal.craside2, Color.orange, e.fin());
/*
        randLenVectors(e.id, 10, 3f + 20f * e.finpow(), (x, y) -> {
            Fill.circle(e.x + x, e.y + y, e.fout() * 2f + 0.7f);
            Fill.circle(e.x + x / 2f, e.y + y / 2f, e.fout());
        });

 */

        color(AZPal.craside, AZPal.craside2, Color.orange, e.fin());
        stroke(1.5f * e.fout());
    }),

    hornBulletDespawn = new Effect(30, e -> {
        color(AZPal.craside);
        e.scaled(10, i -> {
            stroke(4f * i.fout());
            //Lines.circle(e.x, e.y, 7f + i.fin() * 15f);
        });

        color(AZPal.craside, AZPal.craside2, Color.orange, e.fin());
        stroke(1.5f * e.fout());

        randLenVectors(e.id + 1, 10, 1f + 30f * e.finpow(), (x, y) -> {
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), 2f + e.fout() * 3f);
        });
    }),

    dynamicWave2 = new Effect(30, e -> {
        color(e.color, 0.8f);
        stroke(e.fout() * 3f);
        Lines.circle(e.x, e.y, 6f + e.finpow() * e.rotation);
    }),

    lightning2 = new Effect(10f, 500f, e -> {
        if(!(e.data instanceof Seq)) return;
        Seq<Vec2> lines = e.data();

        stroke(3f * e.fout());
        color(e.color, Color.white, e.fin());
        Draw.alpha(Renderer.laserOpacity);

        for(int i = 0; i < lines.size - 1; i++){
            Vec2 cur = lines.get(i);
            Vec2 next = lines.get(i + 1);

            Lines.line(cur.x, cur.y, next.x, next.y, false);
        }

        for(Vec2 p : lines){
            Fill.circle(p.x, p.y, Lines.getStroke() / 2f);
        }
    }),

    lampParticle = new Effect(100f, e -> {
        color(AZPal.ferbiumBulletBack);

        Fill.square(e.x, e.y, e.fslope() * 1.5f + 0.14f, 45f);
    }),

    cursedFireTrailSmall = new Effect(11.3f, 50.0f, e -> {
        color(AZPal.unmakerColor, Color.valueOf("96d66a"), Color.valueOf("487364"), e.fin() * e.fin());
        randLenVectors(e.id, 4, 1.0f + e.finpow() * 30, e.rotation + 180, 7.0f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y, 0.9f + e.fout() * 1.1f);
        });
    }),

    gyurzaMissileTrail = new Effect(23.0f, 200.0f, e -> {
        color(Color.valueOf("b49c7f"), Color.valueOf("4f4f4f"), e.fin() * e.fin());
        randLenVectors(e.id, 3, 1.0f + e.finpow() * 30, e.rotation + 180, 6.5f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y, 1.4f + e.fout() * 1.1f);
        });
    }),

    cursedFireHit = new Effect(30, e -> {
        color(Color.valueOf("96d66a"));
        e.scaled(8, i -> {
            stroke(5.0f * i.fout());
            Lines.circle(e.x, e.y, 5.0f + i.fin() * 10.0f);
        });
    }),

    cursedFire = new Effect(35.0f, e -> {
        color(Color.valueOf("53dc54"), Color.valueOf("00401c"), e.fin());
        randLenVectors(e.id, 3, 4.0f + e.fin() * 7.0f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y, 0.57f + e.fout() * 1.4f);
        });
    }),

    opjozdyshHit = new Effect(14, e -> {
        color(AZPal.unmakerColor, Color.valueOf("487364"), e.fin());
        stroke(0.5f + e.fout());

        randLenVectors(e.id, 2, 1f + e.fin() * 15f, e.rotation, 50f, (x, y) -> {
            float ang = Mathf.angle(x, y);
            lineAngle(e.x + x, e.y + y, ang, e.fout() * 3 + 1f);
        });
    }),

    opjozdyhFlame = new Effect(32f, 80f, e -> {
        color(AZPal.unmakerColor, Color.valueOf("487364"), Color.gray, e.fin());

        randLenVectors(e.id, 8, e.finpow() * 60f, e.rotation, 10f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y, 0.65f + e.fout() * 1.5f);
        });
    });


    public static Effect explosionEffect(float explosionSize, float lifetime, Color frontColor, Color backColor, Color bottomColor) {
        return new Effect(lifetime, explosionSize, e -> {
            Draw.color(frontColor, backColor, e.fin());
            Lines.circle(e.x, e.y, explosionSize + e.fout());
            Lines.stroke(e.fout() * 2f);
            Draw.reset();
            Fill.light(e.x, e.y, Lines.circleVertices(explosionSize / 2), explosionSize, Color.white.cpy().a(0f), Tmp.c4.set(bottomColor).a(e.fout()));
            Draw.reset();
            light(e.x, e.y, explosionSize * 2f, backColor, e.fout());
        });
    }

    public static void lightning(float x1, float y1, float x2, float y2, Color c, int iterations, float rndScale, Effect e) {
        Seq<Vec2> lines = new Seq<>();
        boolean swap = Math.abs(y1 - y2) < Math.abs(x1 - x2);
        if(swap) {
            lines.add(new Vec2(y1, x1));
            lines.add(new Vec2(y2, x2));
        } else {
            lines.add(new Vec2(x1, y1));
            lines.add(new Vec2(x2, y2));
        }
        for (int i = 0; i < iterations; i++) {
            for (int j = 0; j < lines.size - 1; j += 2) {
                Vec2 v1 = lines.get(j), v2 = lines.get(j + 1);
                Vec2 v = new Vec2((v1.x + v2.x) / 2, ((v1.y + v2.y) / 2));
                float ang = (v2.angle(v1) + 90f) * Mathf.degRad;
                float sin = Mathf.sin(ang), cos = Mathf.cos(ang);
                float rnd = Mathf.random(rndScale);
                v.x += rnd * sin;
                v.y += rnd * cos;
                lines.insert(j + 1, v);
            }
        }
        if(swap) {
            for(int i = 0; i < lines.size; i++) {
                Vec2 v = lines.get(i);
                float px = v.x;
                v.x = v.y;
                v.y = px;
            }
        }
        e.at(x1, y1, 0f, c, lines);
    }
}
