package azaria.fetched;

import arc.math.*;
import arc.util.*;
import azaria.annotations.Annotations.*;
import mindustry.gen.*;

//basically just TimedComp but kills instead of removing.
@EntityComponent(write = false)
abstract class TimedKillComp implements Entityc, Healthc, Scaled{
    float time, lifetime;

    //called last so pooling and removal happens then.
    @MethodPriority(100)
    @Override
    public void update(){
        time = Math.min(time + Time.delta, lifetime);

        if(time >= lifetime){
            kill();
        }
    }

    @Override
    public float fin(){
        return time / lifetime;
    }
}
