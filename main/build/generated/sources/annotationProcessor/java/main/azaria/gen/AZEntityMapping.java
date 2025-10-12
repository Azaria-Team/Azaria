package azaria.gen;

import arc.func.Prov;
import arc.struct.ObjectIntMap;
import arc.util.Strings;
import azaria.content.AZUnits;
import mindustry.gen.EntityMapping;
import mindustry.gen.Entityc;
import mindustry.gen.Unit;
import mindustry.type.UnitType;

public class AZEntityMapping {
    private static final ObjectIntMap<Class<? extends Entityc>> ids = new ObjectIntMap<>();

    private static volatile int last = 0;

    public static <T extends Entityc> void register(Class<T> type, Prov<T> prov) {
        synchronized(AZEntityMapping.class) {
            if(ids.containsKey(type) || EntityMapping.nameMap.containsKey(type.getSimpleName())) return;

            for(; last < EntityMapping.idMap.length; last++) {
                if(EntityMapping.idMap[last] == null) {
                    EntityMapping.idMap[last] = prov;
                    ids.put(type, last);

                    EntityMapping.nameMap.put(type.getSimpleName(), prov);
                    EntityMapping.nameMap.put(Strings.camelToKebab(type.getSimpleName()), prov);

                    break;
                }
            }
        }
    }

    public static <T extends Entityc> void register(String name, Class<T> type, Prov<T> prov) {
        register(type, prov);
        EntityMapping.nameMap.put(name, prov);

        int id = classId(type);
        if(id != -1) {
            EntityMapping.customIdMap.put(classId(type), name);
        }
    }

    public static <T extends Unit> void register(UnitType unit, Class<T> type, Prov<T> prov) {
        register(unit.name, type, prov);
        unit.constructor = prov;
    }

    public static <T extends Entityc> int classId(Class<T> type) {
        return ids.get(type, -1);
    }

    public static void init() {
        register(AZUnits.gyurza, azaria.gen.UnitEntity.class, azaria.gen.UnitEntity::create);
        register(AZUnits.veresk, azaria.gen.UnitEntity.class, azaria.gen.UnitEntity::create);
        register(AZUnits.chaos, azaria.gen.UnitEntity.class, azaria.gen.UnitEntity::create);
        register(AZUnits.angelshark, azaria.gen.WaterMoveUnit.class, azaria.gen.WaterMoveUnit::create);
        register(AZUnits.glaucus, azaria.gen.WaterMoveUnit.class, azaria.gen.WaterMoveUnit::create);
        register(AZUnits.aurora, azaria.gen.WaterMoveUnit.class, azaria.gen.WaterMoveUnit::create);
        register(AZUnits.piranha, azaria.gen.WaterMoveUnit.class, azaria.gen.WaterMoveUnit::create);
        register(AZUnits.megalodon, azaria.gen.WaterMoveUnit.class, azaria.gen.WaterMoveUnit::create);
        register(AZUnits.vector, azaria.gen.ElevationMoveDroneUnit.class, azaria.gen.ElevationMoveDroneUnit::create);
        register(AZUnits.zephyr, azaria.gen.DroneUnit.class, azaria.gen.DroneUnit::create);
        register(AZUnits.vortex, azaria.gen.DroneUnit.class, azaria.gen.DroneUnit::create);
        register(AZUnits.altura, azaria.gen.DroneUnit.class, azaria.gen.DroneUnit::create);
        register(AZUnits.cataclysm, azaria.gen.DroneUnit.class, azaria.gen.DroneUnit::create);
        register(AZUnits.unmaker, azaria.gen.StriCopterUnit.class, azaria.gen.StriCopterUnit::create);
        register(AZUnits.eliminator, azaria.gen.StriCopterUnit.class, azaria.gen.StriCopterUnit::create);
        register(AZUnits.exterminator, azaria.gen.StriCopterUnit.class, azaria.gen.StriCopterUnit::create);
        register(AZUnits.blighter, azaria.gen.StriCopterUnit.class, azaria.gen.StriCopterUnit::create);
        register(AZUnits.plague, azaria.gen.StriCopterUnit.class, azaria.gen.StriCopterUnit::create);
        register(AZUnits.opjozdysh, azaria.gen.StriCopterUnit.class, azaria.gen.StriCopterUnit::create);
        register(AZUnits.sentinel, azaria.gen.TankUnit.class, azaria.gen.TankUnit::create);
        register(AZUnits.custodian, azaria.gen.TankUnit.class, azaria.gen.TankUnit::create);
        register(AZUnits.bulwark, azaria.gen.TankUnit.class, azaria.gen.TankUnit::create);
        register(AZUnits.bulat, azaria.gen.TankUnit.class, azaria.gen.TankUnit::create);
        register(AZUnits.colossus, azaria.gen.TankUnit.class, azaria.gen.TankUnit::create);
    }
}
