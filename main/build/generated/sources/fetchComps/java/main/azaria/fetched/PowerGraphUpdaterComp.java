package azaria.fetched;

import azaria.annotations.Annotations.*;
import mindustry.gen.*;
import mindustry.world.blocks.power.*;

@EntityComponent(write = false)
abstract class PowerGraphUpdaterComp implements Entityc{
    public transient PowerGraph graph;

    @Override
    public void update(){
        graph.update();
    }
}
