package az.type.weapons;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Angles;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.scene.ui.layout.Table;
import arc.util.Log;
import arc.util.Time;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.core.World;
import mindustry.entities.Effect;
import mindustry.entities.Sized;
import mindustry.entities.Units;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.Building;
import mindustry.gen.Healthc;
import mindustry.gen.Teamc;
import mindustry.gen.Unit;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import mindustry.type.weapons.RepairBeamWeapon;
import mindustry.world.blocks.units.RepairTurret;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

import static mindustry.Vars.tilesize;

public class BeamWeapon extends Weapon {
    public boolean targetEnemy = true;
    public float damagePerSecond = 0.1f;
    public boolean targetBuildings = false, targetUnits = true;

    public float repairSpeed = 0.3f;
    public float fractionRepairSpeed = 0f;
    public float beamWidth = 1f;
    public float pulseRadius = 6f;
    public float pulseStroke = 2f;
    public float widthSinMag = 0f, widthSinScl = 4f;

    public TextureRegion laser, laserEnd, laserTop, laserTopEnd;

    public Color laserColor = Color.valueOf("98ffa9"), laserTopColor = Color.white.cpy();
    //only for blocks
    public Color healColor = Pal.heal;
    public Effect healEffect = Fx.healBlockFull;


    public BeamWeapon(String name){
        super(name);
    }

    public BeamWeapon(){
    }

    {
        //must be >0 to prevent various bugs
        reload = 1f;
        predictTarget = false;
        autoTarget = true;
        controllable = false;
        rotate = true;
        useAmmo = false;
        mountType = CustomBeamMount::new;
        recoil = 0f;
        noAttack = true;
        useAttackRange = false;
    }

    @Override
    public void addStats(UnitType u, Table w){
        w.row();
        w.add("[lightgray]" + Stat.repairSpeed.localized() + ": " + (mirror ? "2x " : "") + "[white]" + (int)(repairSpeed * 60) + " " + StatUnit.perSecond.localized());
        w.add("[lightgray]" + Stat.damage.localized() + ": " + (mirror ? "2x " : "") + "[white]" + (int)(damagePerSecond * 60) + " " + StatUnit.perSecond.localized());
    }

    @Override
    public float dps(){
        return 0f;
    }

    @Override
    public void load(){
        super.load();

        laser = Core.atlas.find("laser-white");
        laserEnd = Core.atlas.find("laser-white-end");
        laserTop = Core.atlas.find("laser-top");
        laserTopEnd = Core.atlas.find("laser-top-end");
    }

    @Override
    protected Teamc findTarget(Unit unit, float x, float y, float range, boolean air, boolean ground){
        Teamc ally = targetUnits ? Units.closest(unit.team, x, y, range, u -> u != unit && u.damaged()) : null;
        if(ally != null){
            return ally;
        }

        Teamc enemy = targetEnemy ? Units.closestEnemy(unit.team, x, y, range, u -> true) : null;
        if(enemy != null){
            return enemy;
        }

        if(targetBuildings){
            Teamc build = Units.findAllyTile(unit.team, x, y, range, Building::damaged);
            return build;
        }
        return null;
    }

    @Override
    protected boolean checkTarget(Unit unit, Teamc target, float x, float y, float range){
        if(!(target instanceof Healthc h) || !h.isValid()) return true;

        float realRange = range + unit.hitSize/2f + (target instanceof Sized s ? s.hitSize()/2f : 0f);
        boolean inRange = target.within(unit, realRange);

        if(target.team() != unit.team) return !inRange;

        return !(inRange && h.damaged());
    }


    @Override
    protected void shoot(Unit unit, WeaponMount mount, float shootX, float shootY, float rotation){
        //does nothing, shooting is handled in update()
    }


    @Override
    public void update(Unit unit, WeaponMount mount){
        super.update(unit, mount);

        float
                weaponRotation = unit.rotation - 90,
                wx = unit.x + Angles.trnsx(weaponRotation, x, y),
                wy = unit.y + Angles.trnsy(weaponRotation, x, y);

        CustomBeamMount heal = (CustomBeamMount)mount;
        boolean canShoot = mount.shoot;

        if(!autoTarget){
            heal.target = null;
            if(canShoot){
                heal.lastEnd.set(heal.aimX, heal.aimY);

                if(!rotate && !Angles.within(Angles.angle(wx, wy, heal.aimX, heal.aimY), unit.rotation, shootCone)){
                    canShoot = false;
                }
            }


            //limit range
            heal.lastEnd.sub(wx, wy).limit(range()).add(wx, wy);

            if(targetBuildings){
                //snap to closest building
                World.raycastEachWorld(wx, wy, heal.lastEnd.x, heal.lastEnd.y, (x, y) -> {
                    var build = Vars.world.build(x, y);
                    if(build != null && build.team == unit.team && build.damaged()){
                        heal.target = build;
                        heal.lastEnd.set(x * tilesize, y * tilesize);
                        return true;
                    }
                    return false;
                });
            }
            if(targetUnits){
                //TODO does not support healing units manually yet
            }
        }
        heal.strength = Mathf.lerpDelta(heal.strength,
                Mathf.num(autoTarget ? mount.target != null : canShoot), 0.2f);

        //create heal effect periodically
        if(canShoot && mount.target instanceof Building b && b.damaged() && (heal.effectTimer += Time.delta) >= reload && b.team() == unit.team()){
            healEffect.at(b.x, b.y, 0f, healColor, b.block);
            heal.effectTimer = 0f;
        }

        if(canShoot && mount.target instanceof Healthc h && h.isValid() && !h.dead()){
            if(h instanceof Teamc t && t.team() != unit.team()){
                float damage = damagePerSecond * Time.delta * heal.strength;
                h.damage(damage);
            } else if(h instanceof Teamc t && t.team() == unit.team()){
                if(h.damaged()){
                    float healAmount = repairSpeed * heal.strength * Time.delta * h.maxHealth()/100f;
                    h.heal(healAmount);
                }
            }
        }

    }

    @Override
    public void draw(Unit unit, WeaponMount mount){
        super.draw(unit, mount);

        CustomBeamMount heal = (CustomBeamMount)mount;

        if(unit.canShoot()){
            float
                    weaponRotation = unit.rotation - 90,
                    wx = unit.x + Angles.trnsx(weaponRotation, x, y),
                    wy = unit.y + Angles.trnsy(weaponRotation, x, y),
                    z = Draw.z();
            RepairTurret.drawBeam(wx, wy, unit.rotation + mount.rotation, shootY, unit.id, mount.target == null || controllable ? null : (Sized)mount.target, unit.team, heal.strength,
                    pulseStroke, pulseRadius, beamWidth + Mathf.absin(widthSinScl, widthSinMag), heal.lastEnd, heal.offset, laserColor, laserTopColor,
                    laser, laserEnd, laserTop, laserTopEnd);
            Draw.z(z);
        }
    }

    @Override
    public void init(){
        super.init();
        bullet.healPercent = fractionRepairSpeed;
    }

    public static class CustomBeamMount extends WeaponMount{
        public Vec2 offset = new Vec2(), lastEnd = new Vec2();
        public float strength, effectTimer;

        public CustomBeamMount(Weapon weapon){
            super(weapon);
        }
    }
}