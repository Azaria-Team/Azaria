package az.content;

import arc.graphics.Color;
import arc.math.Interp;
import arc.math.geom.Rect;

import arc.util.Time;
import az.entities.ability.ModShieldArcAbility;
import az.entities.bullets.AimBulletType;
import az.entities.bullets.ArtLightningBulletType;
import az.entities.bullets.ModEmpBulletType;
import az.entities.units.DroneUnitType;
import az.entities.units.StriCopterUnitType;
import az.graphics.AZPal;
import az.pattern.AZBurstShoot;
import az.type.weapons.BeamWeapon;
import az.world.draw.Blade;
import az.world.draw.Rotor;

import azaria.gen.*;
import azaria.gen.TankUnit;
import azaria.gen.UnitEntity;
import ent.anno.Annotations;
import mindustry.Vars;
import mindustry.ai.types.BuilderAI;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.entities.abilities.StatusFieldAbility;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.part.RegionPart;
import mindustry.entities.pattern.ShootAlternate;
import mindustry.entities.pattern.ShootSpread;
import mindustry.gen.*;
import mindustry.graphics.Layer;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import mindustry.type.unit.MissileUnitType;
import mindustry.type.unit.TankUnitType;
import mindustry.type.weapons.RepairBeamWeapon;
import mindustry.world.meta.BlockFlag;

import static mindustry.Vars.tilesize;
public class AZUnits {
    //aurelia core units
    public static @Annotations.EntityDef({Unitc.class})
    UnitType gyurza, veresk, chaos;
    //angelshark unit tree
    public static @Annotations.EntityDef({Unitc.class, WaterMovec.class})
    UnitType angelshark, glaucus, aurora, piranha, megalodon;
    //vector tree
    public static @Annotations.EntityDef({Unitc.class, Dronec.class, ElevationMovec.class})
    UnitType vector;
    public static @Annotations.EntityDef({Unitc.class, Dronec.class})
    UnitType zephyr, vortex, altura, cataclysm;
    public static @Annotations.EntityDef({Unitc.class, StriCopterc.class})
    //unmaker tree
    UnitType unmaker, eliminator, exterminator, blighter, plague, opjozdysh;

    //support tree
    public static @Annotations.EntityDef({Unitc.class, Tankc.class})
    UnitType sentinel, custodian, bulwark, bulat, colossus;
    //fire support


    public static void load() {
        //region aureliaCoreUnits
        gyurza = new UnitType("gyurza") {{
            constructor = UnitEntity::create;

            aiController = BuilderAI::new;
            isEnemy = false;
            range = 230;

            lowAltitude = true;
            flying = true;

            speed = 3.3f;
            rotateSpeed = 13f;
            accel = 0.1f;
            drag = 0.05f;

            mineSpeed = 5.5f;
            mineTier = 1;
            buildSpeed = 0.6f;

            health = 300;
            hitSize = 10f;
            itemCapacity = 50;

            engineSize = 3f;
            engineOffset = 9.5f;
            alwaysUnlocked = true;
            outlineColor = AZPal.aureliaOutline;
            fogRadius = 0f;

            weapons.add(
                    new Weapon(){{
                        x = y = 0f;
                        mirror = false;
                        reload = 90f;
                        soundPitchMin = 1f;
                        shootSound = Sounds.missileSmall;
                        bullet = new BulletType(){{
                            shake = 2f;
                            speed = 0f;
                            keepVelocity = false;
                            inaccuracy = 2f;

                            spawnUnit = new MissileUnitType("gyurza-missile"){{
                                engineSize = 1.75f;
                                engineLayer = Layer.effect;
                                speed = 4.2f;
                                maxRange = 16f;
                                trailWidth = 1;
                                fogRadius = 0f;

                                lifetime = 55;
                                outlineColor = AZPal.aureliaOutline;
                                health = 25;
                                lowAltitude = true;
                                hittable = false;
                                targetable = false;
                                hitSize = 2f;

                                weapons.add(new Weapon(){{
                                    shootCone = 360f;
                                    mirror = false;
                                    reload = 1f;
                                    shootOnDeath = true;
                                    bullet = new ExplosionBulletType(100f, 3f * tilesize){{
                                        shootEffect = Fx.explosion;
                                        buildingDamageMultiplier = 0.3f;
                                    }};
                                }});
                            }};
                        }};
                    }});

            parts.add(
                    new RegionPart("-blade") {{
                        moveRot = -10;
                        moves.add(new PartMove(PartProgress.reload, 0f, 1.5f, -5f));
                        progress = PartProgress.warmup;
                        mirror = true;
                    }});
        }};
        //endregion aureliaCoreUnits
        //region angelsharkTree
        angelshark = new UnitType("angelshark") {{
            targetAir = true;
            speed = 0.9f;
            drag = 0.12f;
            hitSize = 13f;
            armor = 2;
            health = 700;
            accel = 0.2f;
            faceTarget = false;
            rotateSpeed = 4f;
            trailLength = 20;
            waveTrailX = 5.5f;
            trailScl = 1.3f;
            waveTrailY = -4f;
            range = 25 * Vars.tilesize;
            constructor = WaterMoveUnit::create;
            outlineColor = AZPal.aureliaOutline;

            weapons.add(new Weapon("az-vog-launcher") {{
                reload = 90f;
                shootY = 2f;
                rotate = true;
                x = 0;
                y = -6;
                mirror = false;
                rotateSpeed = 6;
                shootSound = Sounds.shootAlt;
                bullet = new MissileBulletType(5f, 10, "az-vog") {{
                    backColor = AZPal.vogPinkBack;
                    frontColor = AZPal.forceBullet;
                    width = 13f;
                    height = 15f;
                    shrinkX = 0;
                    shrinkY = 0;
                    hitSound = Sounds.explosion;
                    hitEffect = AZFx.hitExplosion;
                    despawnEffect = AZFx.explosionSmall2;
                    trailEffect = AZFx.vogTrail;
                    trailRotation = true;
                    trailInterval = 0.5f;
                    lifetime = 35;

                    splashDamage = 150;
                    splashDamageRadius = 45f;
                }};
            }});
        }};
        glaucus = new UnitType("glaucus") {{
            speed = 0.72f;
            hitSize = 25f;
            health = 1600;
            armor = 8;
            accel = 0.3f;
            drag = 0.04f;

            faceTarget = true;
            targetAir = true;
            rotateSpeed = 3f;
            trailLength = 20;
            waveTrailX = 7f;
            waveTrailY = -5f;
            trailScl = 1.9f;
            range = 30 * Vars.tilesize;
            constructor = WaterMoveUnit::create;
            outlineColor = AZPal.aureliaOutline;

            parts.add(
                    new RegionPart("-shaft") {{
                        progress = PartProgress.recoil;
                        mirror = false;
                        under = true;
                        moveX = 0f;
                        moveY = -3f;
                        moveRot = 0f;
                        //x = 0;
                        //y = -2f;
                    }});

            weapons.add(
                    new Weapon("az-plasma-pointer") {{
                        reload = 60f;
                        shootY = 2f;
                        rotate = false;
                        x = 6;
                        y = 1.3f;
                        mirror = true;
                        alternate = false;
                        layerOffset = -0.0001f;
                        baseRotation = -65f;
                        shootCone = 360f;
                        shootSound = Sounds.missileSmall;
                        shoot = new ShootSpread(1, 10f);
                        bullet = new AimBulletType(3f, 25) {{
                            backColor = AZPal.vogPinkBack;
                            frontColor = AZPal.vogPink;
                            sprite = "az-dagger-missile";
                            maxRange = 240;
                            homingPower = 0.07f;
                            homingRange = 0;
                            shrinkY = 0f;
                            shrinkX = 0f;
                            drag = 0.01f;
                            width = 9f;
                            height = 15f;
                            hitSound = Sounds.explosion;
                            hitEffect = AZFx.hitExplosion;
                            despawnEffect = AZFx.explosionSmall2;
                            trailEffect = AZFx.aimMissileTrail;
                            trailRotation = true;
                            trailInterval = 0.5f;
                            lifetime = 200f;

                            splashDamage = 85;
                            splashDamageRadius = 20;
                        }};
                    }},
                    new Weapon("az-plasma-pointer") {
                        {
                            reload = 60f;
                            shootY = 2f;
                            rotate = false;
                            x = 6;
                            y = -2.3f;
                            mirror = true;
                            alternate = false;
                            layerOffset = -0.0001f;
                            baseRotation = -115f;
                            shootCone = 360f;
                            shootSound = Sounds.missileSmall;
                            shoot = new ShootSpread(1, 10f);
                            bullet = new AimBulletType(3f, 25) {
                                {
                                    backColor = AZPal.vogPinkBack;
                                    frontColor = AZPal.vogPink;
                                    sprite = "az-dagger-missile";
                                    maxRange = 240;
                                    homingPower = 0.07f;
                                    homingRange = 0;
                                    shrinkY = 0f;
                                    shrinkX = 0f;
                                    drag = 0.01f;
                                    width = 9f;
                                    height = 15f;
                                    hitSound = Sounds.explosion;
                                    hitEffect = AZFx.hitExplosion;
                                    despawnEffect = AZFx.explosionSmall2;
                                    trailEffect = AZFx.aimMissileTrail;
                                    trailRotation = true;
                                    trailInterval = 0.5f;
                                    lifetime = 200f;

                                    splashDamage = 85;
                                    splashDamageRadius = 20;
                                }
                            };
                        }}
            );

            //TODO nerfc
            weapons.add(
                    new Weapon("az-vog-automatic-launcher") {{
                        reload = 100f;
                        shootY = 3f;
                        inaccuracy = 5.5f;
                        rotate = true;
                        x = 0;
                        y = -2.5f;
                        mirror = false;
                        rotateSpeed = 5;
                        shootSound = Sounds.shootAlt;

                        shoot = new AZBurstShoot(3, 6, 15f);
                        bullet = new MissileBulletType(6f, 10, "az-vog") {{
                            velocityRnd =  0.175f;
                            homingPower = 0f;
                            homingRange = 0f;
                            splashDamage = 50   ;
                            splashDamageRadius = 20;
                            backColor = AZPal.vogPinkBack;
                            frontColor = AZPal.vogPink;
                            width = 13f;
                            height = 15f;
                            shrinkX = 0;
                            shrinkY = 0;
                            hitSound = Sounds.explosion;
                            hitEffect = AZFx.hitExplosion;
                            despawnEffect = AZFx.explosionSmall2;
                            trailEffect = AZFx.vogTrail;
                            trailRotation = true;
                            trailInterval = 0.5f;
                            lifetime = 40f;
                        }};
                    }}
            );
        }};

        //TODO nerf at all
        aurora = new UnitType("aurora") {{
            speed = 0.67f;
            hitSize = 30f;
            armor = 14;
            health = 4050;
            accel = 0.35f;
            drag = 0.04f;

            faceTarget = false;
            targetAir = false;
            waveTrailY = -8f;
            rotateSpeed = 1.9f;
            trailLength = 40;
            waveTrailX = 11f;
            trailScl = 1.5f;
            range = 45 * Vars.tilesize;
            constructor = WaterMoveUnit::create;
            outlineColor = AZPal.aureliaOutline;

            weapons.add(
                    new Weapon("az-laser-launcher") {{
                        reload = 6f * Time.toSeconds;

                        x = 1f;
                        shootX = -6.7f;
                        shootY = -8f;
                        mirror = false;
                        rotate = true;
                        rotateSpeed = 1f;
                        layerOffset = 0.01f;
                        showStatSprite = false;
                        shootCone = 10f;
                        shootSound = Sounds.missileLaunch;
                        minWarmup = 0.9f;
                        smoothReloadSpeed = 0.15f;
                        shootWarmupSpeed = 0.02f;
                        parts.add(
                                new RegionPart("-missile") {{
                                    y = 0.5f;
                                    progress = PartProgress.reload.curve(Interp.pow2In);
                                    under = true;
                                    x = -7f;
                                    moves.add(new PartMove(PartProgress.reload,7f, 0f, 0f));
                                }},
                                /*new RegionPart("-beam") {{
                                    under = true;
                                  // colorTo = new Color(1f, 1f, 1f, 0f);
                                    //color = Color.red;
                                    //mixColorTo = new Color(1f, 1f, 1f, 1f);
                                    outline = false;
                                    x = -2;
                                    moves.add(new PartMove(PartProgress.warmup, 0f, 4f, 0f));
                                }},

                                 */
                                new RegionPart("-lens") {{
                                    under = true;
                                    moves.add(new PartMove(PartProgress.warmup, 0f, 4f, 0f));
                                }}
                        );
                        bullet = new BasicBulletType() {{
                            shootEffect = Fx.none;
                            smokeEffect = AZFx.shootSmokeAuroraMissile;
                            lifetime = 0f;
                            hitEffect = Fx.none;
                            despawnEffect = Fx.none;
                            keepVelocity = false;

                            spawnUnit = new MissileUnitType("aurora-missile") {{
                                speed = 5f;
                                //maxRange = 6f;
                                lifetime = 60f * 1.35f;
                                rotateSpeed = 0.7f;
                                outlineColor = AZPal.aureliaOutline;
                                engineColor = AZPal.vogPink;
                                engineLayer = Layer.effect;
                                engineOffset = 7.5f;
                                trailLength = 9;
                                trailColor = AZPal.vogPink;
                                health = 500;
                                hitSize = 10;
                                lowAltitude = true;
                                loopSound = Sounds.missileTrail;
                                loopSoundVolume = 0.6f;
                                deathSound = Sounds.largeExplosion;
                                hitEffect = Fx.none;
                                despawnEffect = Fx.none;
                                targetAir = false;

                                weapons.add(new Weapon(){{
                                    shootCone = 360f;
                                    mirror = false;
                                    reload = 1f;
                                    deathExplosionEffect = AZFx.massiveExplosionAurora2;
                                    shootOnDeath = true;
                                    shake = 5f;
                                    bullet = new ExplosionBulletType(790, 6f * tilesize){{
                                        hitColor = AZPal.forceBullet;
                                        hitEffect = Fx.none;
                                        collidesAir = false;
                                        despawnEffect = Fx.none;
                                        shootEffect = new MultiEffect(AZFx.massiveExplosionAurora, AZFx.scatheExplosionAurora, AZFx.scatheLightAurora);
                                        collidesAir = false;
                                    }};
                                }});
                            }};
                        }};
                    }}
            );
        }};
        //endregion angelsharkTree
        //region unmakerTree
        unmaker = new StriCopterUnitType("unmaker") {{
            flying = true;

            speed = 3f;
            rotateSpeed = 6f;
            accel = 0.1f;
            drag = 0.05f;
            strafePenalty = 0.4f;

            health = 340f;
            hitSize = 14;
            itemCapacity = 5;

            engineSize = 0f;
            outlineColor = AZPal.aureliaOutline;

            for(int i = -1; i < 2; i+=2) {
                int diffPos = i;
                blade.add(
                        new Blade(name + "-blade") {{
                            y = 1f * diffPos;
                            x = 1.3f;
                            bladeMoveSpeed = 40 * diffPos;
                            bladeBlurAlphaMultiplier = 0.5f;
                        }});
            }

            weapons.add(
                    new Weapon("az-unmaker-teeth") {{
                        x = 2f;
                        y = 8f;
                        reload = 27.5f;
                        layerOffset = -0.002f;
                        inaccuracy = 0.4f;
                        shootSound = Sounds.shootAltLong;
                        recoil = 3.5f;
                        shoot = new ShootSpread(3, 5f);
                        bullet = new BasicBulletType(5.5f, 7) {{
                            lifetime = 25f;

                            sprite = "az-dagbul";
                            statusDuration = 60 * 8f;
                            status = AZStatusEffects.weakness;

                            width = 12f;
                            height = 15f;
                            shrinkX = 0;
                            shrinkY = 0;
                            trailChance = 0.7f;
                            trailEffect = AZFx.unmakerBulletTrail;
                            hitEffect = AZFx.smallGreenExplosion;
                            despawnEffect = hitEffect;
                            frontColor = Color.valueOf("ffffff");
                            backColor = AZPal.unmakerColor;
                        }};
                    }});
        }};


        //TODO fix
        eliminator = new StriCopterUnitType("eliminator") {{
            flying = true;

            speed = 2.5f;
            rotateSpeed = 4.5f;
            accel = 0.2f;
            drag = 0.05f;
            strafePenalty = 0.3f;

            health = 970f;
            hitSize = 13f;
            itemCapacity = 10;
            range = 15 * tilesize;

            engineSize = 0f;

            outlineColor = AZPal.aureliaOutline;
            weapons.add(
                    new Weapon("eliminator-gun") {{
                        x = 3;
                        y = 5;
                        reload = 50;
                        layerOffset = -0.002f;
                        recoil = 3.5f;
                        inaccuracy = 6.35f;
                        shootSound = Sounds.missile;

                        shoot = new ShootAlternate() {{
                            shots = 2;
                            shotDelay = 7f;
                        }};

                        bullet = new BasicBulletType(4.5f, 5) {{
                            lifetime = 38f;
                            drag = 0.0012f;

                            shootEffect = Fx.shootLiquid;
                            frontColor = Color.white;
                            backColor = AZPal.unmakerColor;

                            height *= 2.5f;
                            width = height / 2;

                            trailEffect = AZFx.cursedFireTrailSmall;
                            trailRotation = true;
                            trailInterval = 0.5f;

                            hitEffect = AZFx.cursedFireHit;
                            status = AZStatusEffects.decomposition;
                            statusDuration = 140f;

                            splashDamage = 15;
                            splashDamageRadius = 8 * 2.2f;

                            range = 125.0f;
                        }};
                    }});

            blade.add(
                    new Blade(name + "-blade-big"){{
                        y = 1.5f; x = 1.3f;
                        bladeMoveSpeed = 40f;
                        bladeBlurAlphaMultiplier = 0.5f;
                    }},

                    new Blade(name + "-blade"){{
                        y = -0.2f; x = 1.3f;
                        bladeMoveSpeed = -40f;
                        bladeBlurAlphaMultiplier = 0.5f;
                    }});

        }};

        exterminator = new StriCopterUnitType("exterminator") {{
            flying = true;

            speed = 2f;
            rotateSpeed = 3f;
            accel = 0.08f;
            drag = 0.05f;

            health = 2300f;
            hitSize = 20f;
            itemCapacity = 5;

            outlineColor = AZPal.aureliaOutline;
            blade.add(
                    new Blade(name + "-blade-big"){{
                        y = 1.5f; x = 1.3f;
                        bladeMoveSpeed = 40f;
                        bladeBlurAlphaMultiplier = 0.5f;
                    }},

                    new Blade(name + "-blade"){{
                        y = -0.2f; x = 1.3f;
                        bladeMoveSpeed = -40f;
                        bladeBlurAlphaMultiplier = 0.5f;
                    }});


            weapons.add(
                    new Weapon("ex-bomb-bay") {{
                        reload = 150f;
                        rotate = false;
                        x = 6;
                        y = 7f;
                        mirror = true;
                        baseRotation = -50f;
                        rotationLimit = 20f;
                        layerOffset = -0.0001f;
                        shootCone = 90f;
                        shootSound = Sounds.missileSmall;
                        shoot.shots = 6;
                        shoot.shotDelay = 6f;
                        inaccuracy = 20;
                        bullet = new BasicBulletType(4.5f, 15) {{
                            lifetime = 55f;
                            //drag = 0.03f;

                            homingPower = 0.15f;
                            homingDelay = 3f;
                            homingRange = 60;
                            velocityRnd = 0.2f;
                            shootEffect = Fx.shootLiquid;
                            frontColor = Color.white;
                            backColor = AZPal.unmakerColor;

                            width = 5;
                            height = 8;
                            trailRotation = true;
                            trailWidth = 1;
                            trailLength = 20;
                            trailColor = AZPal.unmakerColor;

                            splashDamage = 20;
                            splashDamageRadius = 2 * 8f;

                            range = 125.0f;
                        }};
                    }},

                    new Weapon("ex-missile") {{
                        reload = 50f;
                        rotate = false;
                        x = 4;
                        y = 10f;
                        mirror = true;
                        layerOffset = -0.0001f;
                        shootCone = 30f;
                        shootSound = Sounds.shootAltLong;
                        shoot.shots = 2;
                        inaccuracy = 7;
                        bullet = new BasicBulletType(4f, 30) {{
                            lifetime = 40f;
                            //drag = 0.03f;

                            velocityRnd = 0.1f;
                            shootEffect = Fx.shootLiquid;
                            frontColor = Color.white;
                            backColor = AZPal.unmakerColor;

                            width = 10;
                            height = 14;

                            weaveScale = 5f;
                            weaveMag = 3f;

                            trailRotation = true;
                            trailWidth = 2;
                            trailLength = 10;
                            trailColor = AZPal.unmakerColor;

                        }};
                    }}
            );
        }};

        opjozdysh = new StriCopterUnitType("opjozdysh") {{
            flying = true;

            speed = 4.5f;
            rotateSpeed = 7f;
            accel = 0.1f;
            drag = 0.05f;

            health = 15000000000f; //150;
            armor = 15000000;
            hitSize = 5f;
            itemCapacity = 5;

            engineSize = 0f;
            outlineColor = AZPal.aureliaOutline;
            blade.add(

                    new Blade(name + "-blade"){{
                        y = 0; x = 0f;
                        bladeMoveSpeed = -40f;
                        bladeBlurAlphaMultiplier = 0.9f;
                    }});
            weapons.add(new Weapon(""){{
                shootSound = Sounds.flame;
                shootY = 2f;
                mirror = false;
                x = 0;
                reload = 11f;
                recoil = 1f;
                ejectEffect = Fx.none;
                bullet = new BulletType(4.2f, 37f){{
                    ammoMultiplier = 3f;
                    hitSize = 7f;
                    lifetime = 13f;
                    pierce = true;
                    pierceBuilding = true;
                    pierceCap = 2;
                    statusDuration = 60f * 4;
                    shootEffect = AZFx.opjozdyhFlame;
                    hitEffect = AZFx.opjozdyshHit;
                    despawnEffect = Fx.none;
                    status = StatusEffects.burning;
                    keepVelocity = false;
                    hittable = false;
                }};
            }});
        }};
        //endregion unmakerTree

        //region vectorTree
        vector = new DroneUnitType("vector") {{
            targetFlags = new BlockFlag[] {BlockFlag.turret, BlockFlag.drill, null};

            hovering = true;
            useEngineElevation = false;
            flying = false;
            drag = 0.07f;
            speed = 1.9f;
            rotateSpeed = 5f;
            accel = 0.1f;
            itemCapacity = 20;
            health = 500f;
            hitSize = 12f;
            outlineColor = AZPal.aureliaOutline;
            fallSpeed = 0.001f;
            fallRotateSpeed = 0;

            researchCostMultiplier = 0f;
            shadowElevation = 0.1f;
            hover = true;
            constructor = ElevationMoveDroneUnit::create;
            rotors.add(
                    new Rotor(name + "-rotor"){{
                        rotorLayer = -0.1f;
                        rotorSpeed = 30f;
                        x = 0f;
                        y = 0f;
                        rotorNotRadial = false;
                        rotorRadial = true;
                        bladeCount = 3;
                    }});
            weapons.add(new Weapon("az-vector-gun")
            {{
                reload = 27.5f; //10
                rotate = false;
                x = -4;
                y = -3;
                mirror = true;
                alternate = true;
                shootY = 1.7f;
                shootSound = Sounds.shootAlt;

                shoot = new AZBurstShoot(2, 5, 4.0f);
                inaccuracy = 5.65f;
                bullet = new BasicBulletType(6f, 10) {{
                    sprite = "az-grenade";
                    backColor = AZPal.droneBulletBack;
                    frontColor = AZPal.droneBullet;
                    width = 7f;
                    height = 10f;
                    shrinkX = 0;
                    shrinkY = 0;
                    hitSound = Sounds.explosion;
                    hitEffect = AZFx.smallblueHitExplosion;
                    despawnEffect = AZFx.smallBlueExplosion;
                    lifetime = 25;
                    velocityRnd =  0.2f;
                }};
            }});
        }};
        zephyr = new DroneUnitType("zephyr") {{

            lowAltitude = true;
            flying = true;
            drag = 0.05f;
            speed = 1.6f;
            rotateSpeed = 4f;
            accel = 0.1f;
            itemCapacity = 30;
            health = 1090f;
            hitSize = 14f;

            outlineColor = AZPal.aureliaOutline;
            constructor = DroneUnit::create;

            rotors.add(
                    new Rotor(name + "-rotor") {{
                        rotorSpeed = 30f;
                        x = 0f;
                        y = 0f;
                        bladeCount = 3;
                        rotorNotRadial = false;
                        rotorRadial = true;
                        drawRotorTop = true;
                        drawGlow = true;
                    }});
            weapons.add(new Weapon("az-zephyr-gun") {{
                reload = 25f;
                shootY = 2f;
                rotate = false;
                x = -5;
                y = -4;
                mirror = true;
                alternate = true;
                shootSound = Sounds.shootAlt;

                inaccuracy = 5.5f;
                bullet = new ArtilleryBulletType(5.5f, 10) {{
                    velocityRnd = 0.25f;
                    sprite = "az-grenade";
                    backColor = AZPal.droneBulletBack;
                    frontColor = AZPal.droneBullet;
                    width = 9f;
                    height = 11f;
                    hitSound = Sounds.explosion;
                    hitEffect = AZFx.blueHitExplosionNormal;
                    despawnEffect = AZFx.blueExplosionNormal;
                    lifetime = 30;
                    shrinkX = 0;
                    shrinkY = 0;
                    splashDamage = 10f;
                    splashDamageRadius = 2f * Vars.tilesize;
                    fragBullets = 6;
                    fragBullet = new BasicBulletType(4f, 20) {{
                        sprite = "az-grenade";
                        backColor = AZPal.droneBulletBack;
                        frontColor = AZPal.droneBullet;
                        width = 5f;
                        height = 8f;
                        shrinkX = 0;
                        shrinkY = 0;
                        hitSound = Sounds.explosion;
                        hitEffect = AZFx.smallblueHitExplosion;
                        despawnEffect = AZFx.smallBlueExplosion;
                        lifetime = 15; //10
                    }};
                }};
            }});
        }};

        vortex = new DroneUnitType("vortex") {{
            outlineColor = AZPal.aureliaOutline;
            flying = true;
            lowAltitude = true;

            speed = 1.4f;
            rotateSpeed = 3.0f;
            drag = 0.04f;
            accel = 0.07f;
            hitSize = 31.0f;

            health = 4200.0f;
            armor = 9.0f;
            range = 22 * tilesize;
            crashDamageMultiplier = 50;

            targetPriority = 3.0f;

            itemCapacity = 45;
            constructor = DroneUnit::create;
            engineSize = 0;

            setEnginesMirror(
                    new UnitEngine(23 / 4f, -65 / 4f, 3f, 290f),
                    new UnitEngine(-23 / 4f, -65 / 4f, 3f, 290f)
            );

            weapons.add(
                    new Weapon("az-vortex-emp") {{
                        rotate = true;
                        mirror = false;
                        rotateSpeed = 2.0f;
                        x = 0;
                        y = -10f;

                        reload = 335.0f;
                        inaccuracy = 1f;
                        shake = 1f;
                        shootSound = Sounds.pulseBlast;
                        shoot.firstShotDelay = 40;
                        parentizeEffects = true;
                        shootY = 4;

                        bullet = new ModEmpBulletType(4f, 50) {{
//                            velocityRnd = 0.25f;
                            chargeEffect = AZFx.blueEMICharge;
                            sprite = "circle-bullet";
                            backColor = AZPal.droneEMIBulletBack;
                            frontColor = AZPal.droneEMIBullet;
                            width = 16f;
                            height = 16f;
                            hitSound = Sounds.explosion;
                            hitEffect = AZFx.blueExplosionEMIHit;
                            despawnEffect = AZFx.blueExplosionEMI;
                            lifetime = 70;
                            shrinkX = 0;
                            shrinkY = 0;

                            collidesTiles = false;
                            collides = false;
                            collidesAir = true;
                            scaleLife = true;

                            //fragRandomSpread = 180f;
                            fragBullets = 4;
                            fragLifeMax = 1.5f;
                            fragBullet = new ModEmpBulletType(4f, 40) {{
                                sprite = "circle-bullet";
                                velocityRnd = 0.25f;
                                trailColor = AZPal.droneEMIBullet;
                                trailLength = 7;
                                trailWidth = 2;
                                powerDamageScl = 1.1f;
                                powerSclDecrease = 0.7f;
                                backColor = AZPal.droneEMIBulletBack;
                                frontColor = AZPal.droneEMIBullet;
                                width = height = 6.5f;
                                hitSound = Sounds.explosion;
                                lifetime = 20;
                                radius = 2 * 8;
                                shrinkX = 0;
                                shrinkY = 0;
                                hitEffect = AZFx.smallBlueExplosionEMI;
                                despawnEffect = AZFx.smallBlueExplosionEMI;
                            }};
                        }};
                    }},
                    new Weapon("az-vortex-gun") {{
                        rotate = true;
                        mirror = true;
                        alternate = true;
                        rotateSpeed = 3.0f;
                        x = 10;
                        y = -1;

                        reload = 15.0f;
                        inaccuracy = 5f;
                        shake = 0.4f;
                        shootSound = Sounds.shootAlt;

                        bullet = new BasicBulletType(5.7f, 15) {{
                            velocityRnd = 0.25f;
                            sprite = "az-grenade";
                            backColor = AZPal.droneBulletBack;
                            frontColor = AZPal.droneBullet;
                            width = 8f;
                            height = 11f;
                            hitSound = Sounds.explosion;
                            hitEffect = AZFx.blueHitExplosionNormal;
                            despawnEffect = AZFx.blueExplosionNormal;
                            lifetime = 30;
                            shrinkX = 0;
                            shrinkY = 0;
                            splashDamage = 20f;
                            splashDamageRadius = 2.5f * 8;
                            fragBullets = 3;
                            fragRandomSpread = 90;
                            fragLifeMax = 1.5f;
                            fragBullet = new BasicBulletType(5f, 5) {{
                                velocityRnd = 0.25f;
                                sprite = "az-grenade";
                                pierce = true;
                                pierceBuilding = true;
                                pierceCap = 1;
                                backColor = AZPal.droneBulletBack;
                                frontColor = AZPal.droneBullet;
                                width = 6f;
                                height = 9f;
                                hitEffect = AZFx.smallblueHitExplosion2;
                                despawnEffect = AZFx.smallBlueExplosion2;
                                lifetime = 10;
                                shrinkX = 0;
                                shrinkY = 0;
                            }};
                        }};
                    }}
            );
        }};
/*            weapons.add(new Weapon("") {{
            }});*/

        //endregion vectorTree

        //region sentinelTree
        sentinel = new TankUnitType("sentinel"){{
            drag = 0.3f;
            accel = 0.4f;
            hitSize = 8f;
            treadPullOffset = 3;
            speed = 1.3f;
            rotateSpeed = 4f;
            health = 350;
            armor = 2f;
            itemCapacity = 0;
            treadFrames = 12;
            treadRects = new Rect[]{new Rect(21 - 96f/2, 14 - 80f/2, 9, 24),new Rect(21 - 96f/2, 44 - 80f/2, 9, 20)};
            researchCostMultiplier = 0f;
            constructor = TankUnit::create;
            range = 17f * 8;
            targetAir = true;
            targetGround = true;
            abilities.add(new ModShieldArcAbility(){{
                radius = 32f;
                hitSize = 20;
                angle = 100;
                regen = 0.1675f;
                cooldown = 10f * 60f;
                shieldHealth = 600;
                drawWidth = 5f;

                whenShooting = true;
                width = 10f;
            }});
            weapons.add(new Weapon("az-flare-shield") {{
                reload = Integer.MAX_VALUE;
                shootSound = Sounds.none;
                recoil = 0f;
                rotateSpeed = 2f;
                rotate = true;
                x = y = 0;
                mirror = false;
                ejectEffect = Fx.none;

                bullet = AZBullets.noneBullet;
                targetAir = true;
                targetGround = true;
            }});
        }};

        custodian = new TankUnitType("custodian"){
            {
                drag = 0.3f;
                accel = 0.4f;
                hitSize = 12f;
                treadPullOffset = 3;
                speed = 1f;
                rotateSpeed = 3f;
                health = 1000;
                armor = 2f;
                itemCapacity = 0;
                researchCostMultiplier = 0f;
                constructor = TankUnit::create;
                range = 17f * 8;
                targetAir = true;
                targetGround = true;
                abilities.add(new StatusFieldAbility(AZStatusEffects.boost, 5 * 60, 10 * 60, 10f * 8));

                weapons.add(new Weapon("az-gun")
                {{
                    reload = 80f;
                    rotate = true;
                    x = 0;
                    y = 0;
                    mirror = true;
                    shootY = 1f;
                    rotateSpeed = 3.0f;
                    shootSound = Sounds.lasershoot;
                    shoot.shotDelay = 6;
                    shoot.firstShotDelay = 15;
                    parentizeEffects = true;
                    shoot.shots = 4;

                    inaccuracy = 3f;
                    bullet = new BasicBulletType(5.5f, 15) {{
                        sprite = "az-grenade";
                        chargeEffect = AZFx.custodianCharge;
                        backColor = AZPal.craside2;
                        frontColor = AZPal.craside;
                        hitEffect = AZFx.smallOrangeBulletHit;
                        despawnEffect = AZFx.smallOrangeBulletExplosion;
                        width = 8f;
                        height = 11f;
                        shrinkX = 0;
                        shrinkY = 0;
                        trailColor = AZPal.craside;
                        trailLength = 6;
                        trailWidth = 2;
                        hitSound = Sounds.explosion;

                        lifetime = 40;
                        velocityRnd =  0.2f;
                        healPercent = 1;
                        collidesTeam = true;
                    }};
                }});
            }};

        bulwark = new TankUnitType("bulwark"){
            {
                drag = 0.3f;
                accel = 0.4f;
                hitSize = 18f;
                treadPullOffset = 3;
                speed = 0.9f;
                rotateSpeed = 3f;
                health = 3000;
                armor = 4f;
                itemCapacity = 0;
                researchCostMultiplier = 0f;
                constructor = TankUnit::create;
                range = 17f * 8;
                targetAir = true;
                targetGround = true;
                weapons.add(new Weapon("az-lightning-weapon") {{
                    reload = 120f;
                    rotate = true;
                    x = 0;
                    y = 0;
                    mirror = false;
                    shootY = 1f;
                    rotateSpeed = 2.0f;
                    shootSound = Sounds.spark;
                    shoot.shotDelay = 6;
                    shoot.firstShotDelay = 15;
                    shoot.shots = 1;
                    bullet = new ArtLightningBulletType(100) {{
                        chargeEffect = AZFx.bulwarkCharge;
                        hitEffect = AZFx.orangeLightningHit;
                        lightnings = 2;
                        hitSound = Sounds.explosion;
                        lifetime = 48f;
                        maxRange = 36f * 8f;
                        lightningColor = AZPal.craside3;
                        lightColor = AZPal.craside3;
                    }};
                }});
                weapons.add(new BeamWeapon("bulwark-repair-weapon"){{
                    x = 5f;
                    y = -5f;
                    shootY = 5f;
                    beamWidth = 0.5f;
                    repairSpeed = 0.3f;
                    laserColor = AZPal.craside;
                    mirror = true;
                    rotate = true;
                    rotateSpeed = 3f;

                    bullet = new BulletType(){{
                        maxRange = 19f * 8f;
                    }};
                }});
            }};

        //endregion sentinelTree
        //region source
        //endregion source

        //region UNUSED
        //endregion UNUSED

        //region treeOff
        //endregion treeOff


    }
}
