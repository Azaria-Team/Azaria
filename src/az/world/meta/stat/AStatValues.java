package az.world.meta.stat;

import arc.util.Strings;
import mindustry.world.meta.StatUnit;
import mindustry.world.meta.StatValue;
import mindustry.world.meta.StatValues;

public class AStatValues extends StatValues {
/*
    public static StatValue number(float value, StatUnit statUnit){
        AStatUnit unit=AStatUnit.get(statUnit);
        return number(value,unit);
    }

    public static StatValue number(float value, AStatUnit unit) {
        return table -> {
            int precision = Math.abs((int)value - value) <= 0.001f ? 0 : Math.abs((int)(value * 10) - value * 10) <= 0.001f ? 1 : 2;

            table.add(Strings.fixed(value, precision));
            table.add((unit.space ? " " : "") + unit.localized());
        };
    }*/
}
