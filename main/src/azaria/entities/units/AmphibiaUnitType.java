package azaria.entities.units;

import arc.func.Boolf;
import arc.func.Func;
import mindustry.gen.Unit;
import mindustry.gen.WaterMovec;
import mindustry.type.UnitType;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.blocks.environment.ShallowLiquid;

public class AmphibiaUnitType extends UnitType {
    public Func<Unit, UnitType> toTrans;

    public Boolf<Floor> exitFloor = f -> true;
    public Boolf<Floor> enterFloor = f -> true;

    public Boolf<Unit> transPred = unit -> {
        Floor floor = unit.floorOn();
        // return floor.isLiquid && !(floor instanceof ShallowLiquid) ^ unit instanceof WaterMovec;

        boolean isDeep = floor.isLiquid && !(floor instanceof ShallowLiquid);

        if (unit instanceof WaterMovec) {
            return !isDeep && exitFloor.get(floor);
        } else {
            return isDeep && enterFloor.get(floor);
        }
    };

    public float transformTime;

    public AmphibiaUnitType(String name) {
        super(name);

    }

}
