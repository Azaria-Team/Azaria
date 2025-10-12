package azaria.gen;

import static mindustry.Vars.*;

import arc.math.*;
import arc.util.*;
import azaria.annotations.Annotations;
import azaria.entities.units.DroneUnitType;
import azaria.world.draw.Rotor;
import mindustry.content.Fx;
import mindustry.gen.*;
import mindustry.type.UnitType;
import azaria.annotations.Annotations;
import azaria.world.draw.Rotor;
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

@Annotations.EntityInterface
@SuppressWarnings({"all", "deprecation"})
public interface Dronec extends Builderc, Hitboxc, Entityc, Syncc, Drawc, Posc, Teamc, Physicsc, Rotc, Unitc, Healthc, Statusc, Velc, Itemsc, Weaponsc, Shieldc, Minerc {
    Rotor.RotorMount[] rotors();

    void rotors(Rotor.RotorMount[] rotors);

    float rotSpeedScl();

    void rotSpeedScl(float rotSpeedScl);
}
