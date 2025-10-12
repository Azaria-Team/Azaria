package azaria.fetched;

import arc.util.*;
import azaria.annotations.Annotations.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.world.blocks.storage.CoreBlock.*;

import static mindustry.Vars.*;

@EntityComponent(write = false)
abstract class TeamComp implements Posc{
    @Import float x, y;

    Team team = Team.derelict;

    public boolean cheating(){
        return team.rules().cheat;
    }

    /** @return whether the center of this entity is visible to the viewing team. */
    boolean inFogTo(Team viewer){
        return this.team != viewer && !fogControl.isVisible(viewer, x, y);
    }

    @Nullable
    public CoreBuild core(){
        return team.core();
    }

    @Nullable
    public CoreBuild closestCore(){
        return state.teams.closestCore(x, y, team);
    }

    @Nullable
    public CoreBuild closestEnemyCore(){
        return state.teams.closestEnemyCore(x, y, team);
    }
}
