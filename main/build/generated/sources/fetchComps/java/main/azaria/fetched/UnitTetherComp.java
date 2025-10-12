package azaria.fetched;

import arc.util.*;
import azaria.annotations.Annotations.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.type.*;

/** A unit that depends on a units's existence; if that unit is removed, it despawns. */
@EntityComponent(write = false)
abstract class UnitTetherComp implements Unitc{
    @Import UnitType type;
    @Import Team team;

    //spawner unit cannot be read directly for technical reasons.
    public transient @Nullable Unit spawner;
    public int spawnerUnitId = -1;

    @Override
    public void afterRead(){
        if(spawnerUnitId != -1) spawner = Groups.unit.getByID(spawnerUnitId);
        spawnerUnitId = -1;
    }

    @Override
    public void afterSync(){
        if(spawnerUnitId != -1) spawner = Groups.unit.getByID(spawnerUnitId);
        spawnerUnitId = -1;
    }

    @Override
    public void update(){
        if(spawner == null || !spawner.isValid() || spawner.team != team){
            Call.unitDespawn(self());
        }else{
            spawnerUnitId = spawner.id;
        }
    }
}
