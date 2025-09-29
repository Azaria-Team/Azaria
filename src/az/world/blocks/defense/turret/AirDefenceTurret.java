package az.world.blocks.defense.turret;

import arc.util.Time;
import mindustry.entities.Units;
import mindustry.entities.bullet.MissileBulletType;
import mindustry.gen.Groups;
import mindustry.gen.Teamc;
import mindustry.logic.LAccess;
import mindustry.type.unit.MissileUnitType;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.meta.Stat;

public class AirDefenceTurret extends ItemTurret {
    public float loadSpeed = 0;
    public boolean controllable = false;

    public AirDefenceTurret(String name) {
        super(name);
        predictTarget = false;
        targetInterval = 2;
        playerControllable = false;
    }

    @Override
    public void setStats() {
        super.setStats();
        stats.remove(Stat.ammo);
        stats.remove(Stat.targetsAir);
        stats.remove(Stat.targetsGround);
    }

    public class AirDefenceTurretBuild extends ItemTurretBuild {
        public float reRot = 60;

        @Override
        protected void findTarget() {
            float range = range();

            Teamc ta;

            ta = Units.closestEnemy(team, x, y, range, u -> u.type instanceof MissileUnitType);

            if(ta == null)
                ta = Groups.bullet.intersect(x - range, y - range, range*2, range*2).min(b -> b.team != team && (b.type() != null && (b.type() instanceof MissileBulletType || b.type().homingPower > 0)), b -> b.dst2(x, y));


            target = ta;
        }

        @Override
        public void updateTile() {
            super.updateTile();
            if(power.status < 0.01f) return;
            if(target != null) reRot = 0;
            if(target == null && (reRot += Time.delta) > 60) rotation += Time.delta * loadSpeed * power.status;
        }

        @Override
        public void control(LAccess type, double p1, double p2, double p3, double p4) {
            if(controllable) super.control(type, p1, p2, p3, p4);
        }

        @Override
        public void control(LAccess type, Object p1, double p2, double p3, double p4) {
            if(controllable) super.control(type, p1, p2, p3, p4);
        }
    }
}
