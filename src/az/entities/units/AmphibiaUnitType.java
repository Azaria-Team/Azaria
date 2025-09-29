package az.entities.units;

import arc.func.Boolf;
import arc.func.Func;
import mindustry.gen.Unit;
import mindustry.gen.WaterMovec;
import mindustry.type.UnitType;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.blocks.environment.ShallowLiquid;

public class AmphibiaUnitType extends UnitType {
    public Func<Unit, UnitType> toTrans;
    public Boolf<Unit> transPred = unit -> {
        Floor floor = unit.floorOn();
        return floor.isLiquid && !(floor instanceof ShallowLiquid) ^ unit instanceof WaterMovec;
    };
    public float transformTime;

    public AmphibiaUnitType(String name) {
        super(name);
    }

}
