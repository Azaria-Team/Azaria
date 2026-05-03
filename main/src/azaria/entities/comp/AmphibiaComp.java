package azaria.entities.comp;

import arc.math.geom.*;
import arc.util.*;
import azaria.entities.units.AmphibiaUnitType;
import mindustry.gen.*;
import azaria.annotations.Annotations;
import mindustry.game.Team;
import mindustry.type.UnitType;

@Annotations.EntityComponent
abstract class AmphibiaComp implements Unitc{
    @Annotations.SyncLocal
    float transformTime;

    @Annotations.Import
    UnitType type;
    @Annotations.Import
    Team team;

    @Annotations.Import
    float x, y, rotation;
    @Annotations.Import
    Vec2 vel;

    @Override
    public void setType(UnitType type){
        if(type instanceof AmphibiaUnitType def){
            transformTime = def.transformTime;
        }
    }

    @Override
    public void update(){
        AmphibiaUnitType type = (AmphibiaUnitType)this.type;
        if(type.transPred.get(self())){
            if(transformTime < 0f || transformTime > type.transformTime){
                Unit next = type.toTrans.get(self()).spawn(team, x, y);
                next.rotation = rotation;
                next.add();
                next.vel.set(vel);

                if(isPlayer()){
                    next.controller(controller());
                }

                remove();
            }else{
                transformTime -= Time.delta;
            }
        }
    }
}