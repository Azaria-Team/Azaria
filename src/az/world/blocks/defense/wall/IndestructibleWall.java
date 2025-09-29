package az.world.blocks.defense.wall;

import arc.util.Nullable;
import mindustry.content.UnitTypes;
import mindustry.gen.BlockUnitc;
import mindustry.gen.Bullet;
import mindustry.gen.Unit;
import mindustry.type.Category;
import mindustry.world.blocks.ControlBlock;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.meta.BuildVisibility;

public class IndestructibleWall extends Wall {

    public IndestructibleWall(String name) {
        super(name);
        health = 1;
        absorbLasers = placeableLiquid = instantDeconstruct = true;
        chanceDeflect = 1;
        buildVisibility = BuildVisibility.sandboxOnly;
        category = Category.defense;
        hideDetails = false;
    }
    public class IndestructibleBuild extends WallBuild implements ControlBlock {
        public @Nullable BlockUnitc unit;

        @Override
        public float handleDamage(float amount) {
            return 0;
        }

        @Override
        public boolean collision(Bullet bullet) {
            super.collision(bullet);
            return true;
        }

        @Override
        public Unit unit(){
            if(unit == null){
                unit = (BlockUnitc) UnitTypes.block.create(team);
                unit.tile(this);
            }
            return (Unit)unit;
        }

        @Override
        public boolean canControl() {
            return true;
        }
    }
}
