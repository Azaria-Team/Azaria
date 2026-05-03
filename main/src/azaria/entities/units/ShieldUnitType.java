package azaria.entities.units;

import arc.Core;
import arc.graphics.g2d.TextureRegion;
import azaria.graphics.AbilityTextures;
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
