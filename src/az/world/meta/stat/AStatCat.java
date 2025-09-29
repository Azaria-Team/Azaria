package az.world.meta.stat;

import az.world.meta.stat.Offset;
import arc.struct.*;
import arc.util.*;
import mindustry.world.meta.*;

import java.lang.reflect.*;
import java.util.*;

public class AStatCat implements Comparable<AStatCat>{
    private static final ObjectMap<String, AStatCat> statCatMap = new ObjectMap<>();
    private static final Seq<AStatCat> allStatCats = new Seq<>();
    private static final StatCat[] rootValues;

    static{
        Seq<StatCat> values = new Seq<>();
        for(Field field : StatCat.class.getFields()){
            if(!Modifier.isPublic(field.getModifiers()) &&
            !Modifier.isStatic(field.getModifiers()) &&
            !Modifier.isFinal(field.getModifiers())) continue;
            if(field.getType() != StatCat.class) continue;
            values.add(Reflect.<StatCat>get(field));
        }
        rootValues = values.toArray(StatCat.class);
        for(StatCat value : rootValues){
            get(value);
        }
    }

    public final String name;
    public final StatCat statCat;
    public final boolean mindustryCopy;

    private AStatCat(String name, int index){
        this.name = name;
//        statCatMap.put(name, this);
        if(index < 0 || index > allStatCats.size){
            allStatCats.add(this);
        }else{
            allStatCats.insert(index, this);
        }
        StatCat statCat = StatCat.all.find(cat -> cat.name.equals(name));
        if (statCat==null){
            this.statCat=new StatCat(name);
        }else{
            this.statCat = statCat;
        }
        mindustryCopy = Structs.contains(rootValues, other -> other.name.equals(name()));
    }

    public static Seq<AStatCat> getAllStatCats(){
        return allStatCats.copy();
    }

    public static AStatCat get(StatCat category){
        return get(category.name);
    }

    public static AStatCat get(StatCat category, int index){
        return get(category.name, index);
    }

    public static AStatCat get(String category){
        return get(category, -1);
    }

    public static AStatCat get(String category, int index){
        return statCatMap.get(category.toLowerCase(Locale.ROOT), () -> new AStatCat(category, index));
    }

    public static AStatCat get(String category, StatCat nearby){
        return get(category, nearby, Offset.defaultOffset);
    }

    public static AStatCat get(String category, StatCat nearby, Offset offset){
        return get(category, get(nearby), offset);
    }

    public static AStatCat get(String category, AStatCat nearby){
        return get(category, nearby, Offset.defaultOffset);
    }

    public static AStatCat get(String category, AStatCat nearby, Offset offset){
        return get(category, offset.calculateIndex(nearby.index()));
    }


    public String name(){
        return name;
    }

    public int index(){
        return allStatCats.indexOf(this);
    }

    /**
     * @return statCat with same name in enum StatCat or null
     */
    public StatCat toStatCat(){
        return statCat;
    }

    @Override
    public String toString(){
        return name();
    }

    @Override
    public int compareTo(AStatCat o){
        return index() - o.index();
    }
}
