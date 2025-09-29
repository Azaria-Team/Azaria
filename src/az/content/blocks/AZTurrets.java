package az.content.blocks;

import arc.math.Mathf;
import arc.util.Time;
import az.content.AZBullets;
import az.content.AZFx;
import az.content.AZItems;
import az.content.AZLiquids;
import az.graphics.AZPal;
import az.world.blocks.defense.turret.AirDefenceTurret;
import az.world.blocks.defense.turret.SpeedUpItemTurret;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.ExplosionBulletType;
import mindustry.entities.part.RegionPart;
import mindustry.entities.pattern.ShootBarrel;
import mindustry.entities.pattern.ShootSpread;
import mindustry.gen.Sounds;
import mindustry.gen.UnitEntity;
import mindustry.graphics.Layer;
import mindustry.type.Category;
import mindustry.type.Weapon;
import mindustry.type.unit.MissileUnitType;
import mindustry.world.Block;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.defense.turrets.PowerTurret;
import mindustry.world.consumers.ConsumeLiquid;
import mindustry.world.draw.DrawTurret;

import static mindustry.type.ItemStack.with;

public class AZTurrets {

    public static Block
            forceTurret, hornTurret, testTurret, tideTurret,
            razeTurret,
            complexShell, complexAvalon;

    public static void load() {
        forceTurret = new ItemTurret("force") {{
            requirements(Category.turret, with(AZItems.fors, 85, AZItems.lepera, 30));
            researchCost = with(AZItems.fors, 150, AZItems.lepera, 70);
            health = 700;
            shootEffect = AZFx.shootForce;
            smokeEffect = AZFx.shootSmokeForce;
            reload = 70f;
            inaccuracy = 2f;
            shake = 2f;
            shootY = -2;
            outlineColor = AZPal.aureliaOutline;
            size = 2;
            recoil = 2f;
            range = 24 * Vars.tilesize /*nerf*/ * 0.70f;
            shootCone = 10f;
            rotateSpeed = 4f;

            shootSound = Sounds.cannon;
            squareSprite = false;
            ammoPerShot = 1;
            maxAmmo = 10;
            itemCapacity = 10;
            coolantMultiplier = 2f;
            coolant = consume(new ConsumeLiquid(AZLiquids.oxyliteLiq, 10f / 60f));

            ammo(
                    AZItems.fors, AZBullets.forceBullet,
                    AZItems.ferbium, AZBullets.forceFerbiumBullet
            );


            drawer = new DrawTurret("fortified-") {{
                parts.add(
                        new RegionPart("-edge-r") {{
                            progress = PartProgress.warmup;
                            mirror = false;
                            under = false;
                            moveRot = -8f;
                        }},
                        new RegionPart("-edge-l") {{
                            progress = PartProgress.warmup;
                            mirror = false;
                            under = false;
                            moveRot = 8f;
                        }},
                        new RegionPart("-barrel") {{
                            progress = PartProgress.recoil;
                            mirror = false;
                            under = false;
                            moveX = 0f;
                            moveY = -1f;
                            moveRot = 0f;
                            x = 0;
                            y = 0;
                        }});
            }};
        }};

        hornTurret = new PowerTurret("horn") {{
            requirements(Category.turret, with(AZItems.fors, 70, AZItems.arside, 25));
            researchCost = with(AZItems.fors, 150, AZItems.lepera, 70);
            health = 900;
            shootEffect = Fx.none;
            smokeEffect = Fx.none;
            reload = 100f;
            inaccuracy = 0f;
            shake = 4f;
            shootY = -2;
            outlineColor = AZPal.aureliaOutline;
            size = 2;
            recoil = 4f;
            range = 16 * Vars.tilesize;
            shootCone = 35f;
            rotateSpeed = 3.4f;

            shootSound = Sounds.shockBlast;
            squareSprite = false;

            shoot = new ShootSpread(7, 6);

            shootType = AZBullets.hornBullet;
            consumePower(1.5f);
            coolantMultiplier = 2f;
            coolant = consume(new ConsumeLiquid(AZLiquids.oxyliteLiq, 12f / 60f));

            drawer = new DrawTurret("fortified-") {{
                parts.add(
                        new RegionPart("-blade-r") {{
                            progress = PartProgress.warmup;

                            mirror = false;
                            under = false;
                            moveY = -2;
                            moves.add(new PartMove(PartProgress.recoil, 0f, 6f, 0f));
                        }},

                        new RegionPart("-blade-l") {{
                            progress = PartProgress.warmup;
                            mirror = false;
                            under = false;
                            moveY = -2;
                            moves.add(new PartMove(PartProgress.recoil, 0f, 6f, 0f));
                        }},

                        new RegionPart("-piston") {{
                            progress = PartProgress.recoil;
                            mirror = false;
                            under = false;
                            moveX = 0f;
                            moveY = -3f;
                            moveRot = 0f;
                            x = 0;
                            y = 0;
                        }});
            }};
        }};

        testTurret = new SpeedUpItemTurret("zxc") {{
            requirements(Category.turret, with(AZItems.fors, 70, AZItems.lepera, 25));
            inaccuracy = 5f;
            shake = 1f;
            shootY = -2;
            outlineColor = AZPal.aureliaOutline;
            size = 2;
            recoil = 2f;
            range = 28 * Vars.tilesize;
            shootCone = 30f;
            rotateSpeed = 3f;


            maxSpeedupScl = 1f;
            speedupPerShoot = 0.09f;
            reload = 300f;

            shootSound = Sounds.shootAltLong;
            squareSprite = false;
            ammoPerShot = 1;
            maxAmmo = 10;
            itemCapacity = 10;
            shoot.shots = 12;
            shoot.shotDelay = 3;
            velocityRnd = 0.2f;
            minRange = 6 * 8f;

            ammo(
                    AZItems.ferbium, AZBullets.ferbiumBullet
            );


            drawer = new DrawTurret("fortified-") {{
            }};
        }};
        //todo change craft
        tideTurret = new PowerTurret("tide") {{
            requirements(Category.turret, with(AZItems.fors, 70, AZItems.lepera, 25));
            reload = 50f;
            inaccuracy = 4f;
            shake = 1f;
            shootY = 1;
            outlineColor = AZPal.aureliaOutline;
            size = 2;
            recoil = 2f;
            range = 23 * Vars.tilesize;
            shootCone = 10f;
            rotateSpeed = 3f;
            shoot.shots = 2;
            shoot.shotDelay = 10;
            consumePower(1f);

            shootSound = Sounds.bolt;
            squareSprite = false;
            coolantMultiplier = 2f;

            shootType = AZBullets.tideLaser;
            drawer = new DrawTurret("fortified-"){{
                parts.add(
                        new RegionPart("-spike") {{
                            progress = PartProgress.warmup;
                            mirror = false;
                            under = true;
                            moveX = 0f;
                            moveY = 1f;
                            recoil = 2;
                            moveRot = 0f;
                        }});
            }};
        }};

        razeTurret = new ItemTurret("raze") {{
            requirements(Category.turret, with(AZItems.fors, 230, AZItems.arside, 130, AZItems.superdenseAlloy, 70, AZItems.ferbium, 20));
            ammo(
                    AZItems.superdenseAlloy, AZBullets.razeBullet
            );
            outlineColor = AZPal.aureliaOutline;

            shoot = new ShootBarrel() {
                {
                    barrels = new float[]{
                            -3, -1f, 0,
                            -8, -4f, 0,
                            3, -1f, 0,
                            8, -4f, 0
                    };
                    shots = 1;
                    shotDelay = 1f;
                }};

            health = 1560;
            size = 3;
            reload = 13f;
            range = 37f * Vars.tilesize;
            recoil = 2;
            inaccuracy = 2f;
            rotateSpeed = 3f;
            shake = 3f;
            shootCone = 2f;
            //ammoPerShot = 1;
            shootSound = Sounds.shootBig;
            ammoUseEffect = Fx.casing2;
            targetAir = true;
            targetGround = false;

            recoils = 4;
            drawer = new DrawTurret("fortified-"){{
                parts.add(new RegionPart("-wing-r") {{
                    progress = PartProgress.warmup;
                    moveX = 0.6f;
                    moveRot = -15f;
                }}, new RegionPart("-wing-l") {{
                    progress = PartProgress.warmup;
                    moveX = -0.6f;
                    moveRot = 15f;
                }});
            /*    parts.add(new RegionPart("-radar") {{
                    layerOffset = 0.001f;
                    moveRot = 360;
                    //rotateSpeed = 2f;
                    y = -3f;
                    progress = p -> Mathf.sinDeg(Time.time) * 1f + 0.3f;
                   // moves.add(new PartMove(PartProgress.life, 0f, 0f, 360f));
                }});

             */
                for(int i = 4; i > 0; i--) {
                    int f = i;
                    parts.add(new RegionPart("-barrel-" + i){{
                        progress = PartProgress.recoil;
                        recoilIndex = f - 1;
                        under = true;
                        moveY = -3f;
                    }});
                }

                //
//                parts.add(new RegionPart("-side")  {{
//                        progress = PartProgress.warmup;
//                        moveX = 0.6f;
//                        moveRot = -15f;
//                        mirror = true;
//                        layerOffset = 0.001f;
//                    }};
                //
            }};
        }};

        //region h-tur
        complexShell = new AirDefenceTurret("complex-shell") {{
            size = 3;
            requirements(Category.turret, with(AZItems.superdenseAlloy, 300));
            health = 700;
            shootEffect = AZFx.shootForce;
            smokeEffect = AZFx.shootSmokeForce;
            reload = 70f;
            inaccuracy = 2f;
            shake = 2f;
            shootY = -2;
            outlineColor = AZPal.aureliaOutline;
            recoil = 2f;
            range = 24 * Vars.tilesize;
            shootCone = 10f;
            rotateSpeed = 3f;

            shootSound = Sounds.cannon;
            squareSprite = false;
            itemCapacity = 20;
            coolantMultiplier = 2f;
            consumePower(1f);
            ammo(AZItems.superdenseAlloy, AZBullets.antiMissileBullet);
        }};
/*
                complexAvalon = new PowerTurret("complex-avalon") {{
                    size = 3;
                    requirements(Category.turret, with(AZItems.superdenseAlloy, 300));
                }};

 */
        //endregion h-tur

    }
}