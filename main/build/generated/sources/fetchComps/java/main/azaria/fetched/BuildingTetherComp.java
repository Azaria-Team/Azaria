package azaria.fetched;

import arc.util.*;
import azaria.annotations.Annotations.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.type.*;

/** A unit that depends on a building's existence; if that building is removed, it despawns. */
@EntityComponent(write = false)
abstract class BuildingTetherComp implements Unitc{
    @Import UnitType type;
    @Import Team team;

    public @Nullable Building building;

    @Override
    public void update(){
        if(building == null || !building.isValid() || building.team != team){
            Call.unitDespawn(self());
        }
    }
}
