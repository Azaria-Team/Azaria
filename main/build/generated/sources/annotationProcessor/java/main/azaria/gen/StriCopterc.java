package azaria.gen;

import arc.math.Angles;
import arc.math.Mathf;
import arc.util.Time;
import azaria.annotations.Annotations;
import azaria.entities.units.StriCopterUnitType;
import azaria.gen.StriCopterc;
import azaria.world.draw.Blade;
import mindustry.content.Fx;
import mindustry.gen.Unitc;
import mindustry.type.UnitType;
import azaria.annotations.Annotations;
import azaria.world.draw.Blade;
import mindustry.gen.Builderc;
import mindustry.gen.Drawc;
import mindustry.gen.Entityc;
import mindustry.gen.Healthc;
import mindustry.gen.Hitboxc;
import mindustry.gen.Itemsc;
import mindustry.gen.Minerc;
import mindustry.gen.Physicsc;
import mindustry.gen.Posc;
import mindustry.gen.Rotc;
import mindustry.gen.Shieldc;
import mindustry.gen.Statusc;
import mindustry.gen.Syncc;
import mindustry.gen.Teamc;
import mindustry.gen.Unitc;
import mindustry.gen.Velc;
import mindustry.gen.Weaponsc;
import mindustry.type.UnitType;

@Annotations.EntityInterface
@SuppressWarnings({"all", "deprecation"})
public interface StriCopterc extends Shieldc, Teamc, Entityc, Physicsc, Posc, Itemsc, Weaponsc, Hitboxc, Syncc, Unitc, Drawc, Statusc, Healthc, Velc, Builderc, Rotc, Minerc {
    void setBlades(UnitType type);

    Blade.BladeMount[] blades();

    void blades(Blade.BladeMount[] blades);

    float bladeMoveSpeedScl();

    void bladeMoveSpeedScl(float bladeMoveSpeedScl);

    long drawSeed();

    void drawSeed(long drawSeed);
}
