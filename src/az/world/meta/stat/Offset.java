package az.world.meta.stat;

import az.world.meta.stat.AStat;
import az.world.meta.stat.AStatCat;
import mindustry.world.meta.*;

public enum Offset{
    before, after;
    public static final Offset defaultOffset = after;

    public int calculateIndex(int index){
        return this == Offset.before ? index : index + 1;
    }

    public int calculateIndex(az.world.meta.stat.AStat stat){
        return calculateIndex(stat.index());
    }

    public int calculateIndex(Stat stat){
        return calculateIndex(AStat.get(stat).index());
    }
    public int calculateIndex(az.world.meta.stat.AStatCat statCat){
        return calculateIndex(statCat.index());
    }

    public int calculateIndex(StatCat statCat){
        return calculateIndex(AStatCat.get(statCat).index());
    }
}
