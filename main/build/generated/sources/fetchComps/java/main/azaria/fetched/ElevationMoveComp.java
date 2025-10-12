package azaria.fetched;

import azaria.annotations.Annotations.*;
import mindustry.entities.*;
import mindustry.entities.EntityCollisions.*;
import mindustry.gen.*;

@EntityComponent(write = false)
abstract class ElevationMoveComp implements Velc, Posc, Hitboxc, Unitc{
    @Import float x, y;

    @Replace
    @Override
    public SolidPred solidity(){
        return isFlying() || ignoreSolids() ? null : EntityCollisions::solid;
    }

}
