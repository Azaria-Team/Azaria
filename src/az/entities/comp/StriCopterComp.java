package az.entities.comp;

import arc.math.Angles;
import arc.math.Mathf;
import arc.util.Time;
import az.entities.units.StriCopterUnitType;
import az.world.draw.Blade;
import azaria.gen.StriCopterc;
import ent.anno.Annotations;
import mindustry.content.Fx;
import mindustry.gen.Unitc;
import mindustry.type.UnitType;

/**
 * Original code from Omaloon[<a href="https://github.com/xstabux/Omaloon/blob/master/src/omaloon/entities/comp/OrnitopterComp.java">...</a>]
 */
@Annotations.EntityComponent
public abstract class StriCopterComp implements Unitc, StriCopterc {
    @Annotations.Import
    float x,y,rotation;
    @Annotations.Import
    boolean dead;
    @Annotations.Import
    UnitType type;
    public Blade.BladeMount[] blades;
    public float bladeMoveSpeedScl = 1f;

    @Override
    public void afterRead(){
        setBlades(type);
    }

    @Override
    public void setType(UnitType type) {
        setBlades(type);
    }

    public void setBlades(UnitType type){
        if (type instanceof StriCopterUnitType stricopter) {
            blades = new Blade.BladeMount[stricopter.blade.size];
            for (int i = 0; i < blades.length; i++) {
                Blade bladeType = stricopter.blade.get(i);
                blades[i] = new Blade.BladeMount(bladeType);
            }
        }
    }

    public long drawSeed = 0;

    @Override
    public void update() {
        drawSeed++;

        StriCopterUnitType type = (StriCopterUnitType) this.type;
        float rX = x + Angles.trnsx(rotation - 90, type.fallSmokeX, type.fallSmokeY);
        float rY = y + Angles.trnsy(rotation - 90, type.fallSmokeX, type.fallSmokeY);

        // Slows down rotor when dying
        if (dead || health() <= 0) {
            rotation += Time.delta * (type.spinningFallSpeed * vel().len()) * Mathf.signs[type.id % 2];
            if (Mathf.chanceDelta(type.fallSmokeChance)) {
                Fx.fallSmoke.at(rX, rY);
                Fx.burning.at(rX, rY);
            }
            bladeMoveSpeedScl = Mathf.lerpDelta(bladeMoveSpeedScl, 0f, type.bladeDeathMoveSlowdown);
        } else {
            bladeMoveSpeedScl = Mathf.lerpDelta(bladeMoveSpeedScl, 1f, type.bladeDeathMoveSlowdown);
        }

        for (Blade.BladeMount blade : blades) {
            blade.bladeRotation += ((blade.blade.bladeMoveSpeed * bladeMoveSpeedScl) + blade.blade.minimumBladeMoveSpeed) * Time.delta;
        }
        type.fallSpeed = 0.006f;
    }
}