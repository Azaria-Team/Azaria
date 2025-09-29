package az.entities.comp;

import arc.math.*;
import arc.util.*;
import az.entities.units.DroneUnitType;
import az.world.draw.Rotor;
import ent.anno.Annotations;
import mindustry.content.Fx;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.type.UnitType;

import static mindustry.Vars.*;

@Annotations.EntityComponent
abstract class DroneComp implements Unitc {
        transient Rotor.RotorMount[] rotors;
        transient float rotSpeedScl = 1f;

        @Annotations.Import
        UnitType type;
        @Annotations.Import
        boolean dead;
        @Annotations.Import
        float health, rotation;
        @Annotations.Import
        int id;

        @Override
        public void add(){
            DroneUnitType type = (DroneUnitType)this.type;

            rotors = new Rotor.RotorMount[type.rotors.size];
            for(int i = 0; i < rotors.length; i++){
                Rotor rotor = type.rotors.get(i);
                rotors[i] = new Rotor.RotorMount(rotor);
                rotors[i].rotorRot = rotor.rotOffset;
                rotors[i].rotorShadeRot = rotor.rotOffset;
                if(dead || health < 0f) {
                    if (Mathf.chanceDelta(type.fallSmokeChance)) {
                        Fx.fallSmoke.at(rotor.x, rotor.y);
                        Fx.burning.at(rotor.x, rotor.y);
                    }
                }
            }
        }

        @Override
        public void update(){
            DroneUnitType type = (DroneUnitType)this.type;
            if(dead || health < 0f){
                if(!net.client() || isLocal()) rotation += type.fallRotateSpeed * Mathf.signs[id % 2] * Time.delta;

                rotSpeedScl = Mathf.lerpDelta(rotSpeedScl, 0f, type.rotorDeathSlowdown);
            }else{
                rotSpeedScl = Mathf.lerpDelta(rotSpeedScl, 1f, type.rotorDeathSlowdown);
            }

            for(Rotor.RotorMount rotor : rotors){
                rotor.rotorRot += ((rotor.rotor.rotorSpeed * rotSpeedScl) + rotor.rotor.minimumRotorSpeed) * Time.delta;
            }
        }
    }

