package az.entities.units;

import arc.Core;
import arc.graphics.g2d.TextureRegion;
import az.graphics.AbilityTextures;
import mindustry.type.UnitType;

public class ShieldUnitType extends UnitType {
    public TextureRegion[] abilityRegions = new TextureRegion[AbilityTextures.values().length];

    public ShieldUnitType(String name){
        super(name);
    }

    @Override
    public void load() {
        super.load();
        //abilities
        for (AbilityTextures type : AbilityTextures.values()) {
            abilityRegions[type.ordinal()] = Core.atlas.find(name + "-" + type.name());
        }
    }
}
