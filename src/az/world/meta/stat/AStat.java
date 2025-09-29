package az.world.meta.stat;

import az.world.meta.stat.AStatCat;
import az.world.meta.stat.Offset;
import arc.*;
import arc.struct.*;
import arc.util.*;
import mindustry.world.meta.*;

import java.lang.reflect.*;
import java.util.*;

public class AStat implements Comparable<AStat>{
    private static final ObjectMap<String, AStat> statMap = new ObjectMap<>();
    private static final Seq<AStat> allStats = new Seq<>();

    private static final Stat[] rootValues;

    static{
        Seq<Stat> values = new Seq<>();
        for(Field field : Stat.class.getFields()){
            if(!Modifier.isPublic(field.getModifiers()) &&
            !Modifier.isStatic(field.getModifiers()) &&
            !Modifier.isFinal(field.getModifiers())) continue;
            if(field.getType() != Stat.class) continue;
            values.add(Reflect.<Stat>get(field));
        }
        rootValues = values.toArray(Stat.class);
        for(Stat value : rootValues){
            get(value);
        }
    }

    public final AStatCat category;
    public final String name;
    public final Stat stat;
    public final boolean mindustryCopy;

    private AStat(String name, AStatCat category, int index){
        this.name = name;
        this.category = category;
        if(index < 0 || index > allStats.size){
            allStats.add(this);
        }else{
            allStats.insert(index, this);
        }
        Stat rootStat = Stat.all.find(cat -> cat.name.equals(name));
        if(rootStat == null){
            stat = new Stat(name, category.statCat);
        }else{
            stat = rootStat;
        }
        mindustryCopy = Structs.contains(rootValues, other -> other.name.equals(name()));
    }

    public static Seq<AStat> getAllStats(){
        return allStats.copy();
    }

    public static AStat get(String name, AStatCat category, int index){
        return statMap.get(name.toLowerCase(Locale.ROOT), () -> new AStat(name, category, index));
    }


    public static AStat get(String name, AStatCat category, Stat nearby){
        return get(name, category, nearby, Offset.defaultOffset);
    }

    public static AStat get(String name, AStatCat category, Stat nearby, Offset offset){
        return get(name, category, get(nearby), offset);
    }

    public static AStat get(String name, AStatCat category, AStat nearby, Offset offset){
        return get(name, category, offset.calculateIndex(nearby.index()));
    }

    public static AStat get(String name, AStatCat category){
        return get(name, category, -1);
    }

    public static AStat get(String name, String category){
        return get(name, AStatCat.get(category));
    }

    public static AStat get(String name, String category, int index){
        return get(name, AStatCat.get(category), index);
    }

    public static AStat get(Stat stat){
        return get(stat.name, stat.category, stat.id);
    }

    public static AStat get(String name, StatCat category){
        return get(name, category.name);
    }

    public static AStat get(String name, StatCat category, int index){
        return get(name, category.name, index);
    }

    public static AStat get(String name, StatCat category, Stat nearby){
        return get(name, category, get(nearby));
    }

    public static AStat get(String name, StatCat category, AStat nearby){
        return get(name, category, nearby, Offset.defaultOffset);
    }

    public static AStat get(String name, StatCat category, Stat nearby, Offset offset){
        return get(name, category, get(nearby), offset);
    }

    public static AStat get(String name, StatCat category, AStat nearby, Offset offset){
        return get(name, category, offset.calculateIndex(nearby.index()));
    }

    public String name(){
        return name;
    }

    public String localized(){
        return Core.bundle.get("stat." + name().toLowerCase(Locale.ROOT));
    }

    public int index(){
        return allStats.indexOf(this);
    }


    /**
     * @return stat with same name in enum Stat or null
     */
    public Stat toStat(){
        return stat;
    }

    @Override
    public String toString(){
        return name();
    }

    @Override
    public int compareTo(AStat o){
        return index() - o.index();
    }
}
