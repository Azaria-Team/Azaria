package az.entities.units;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.Angles;
import arc.math.Mathf;
import arc.struct.Seq;
import az.world.draw.Rotor;
import azaria.gen.Dronec;
import mindustry.content.Fx;
import mindustry.gen.Unit;
import mindustry.graphics.Layer;
import mindustry.type.UnitType;

public class DroneUnitType extends UnitType {

    public float rotorDeathSlowdown = 0.01f;
    public float fallSmokeX = 0f, fallSmokeY = -5f, fallSmokeChance = 0.1f;
    public boolean hover = false;

    // Copters.
    public final Seq<Rotor> rotors = new Seq<>(2);
    public float fallRotateSpeed = 2f;


    public DroneUnitType(String name) {
        super(name);
        engineSize = 0;
        fallSpeed = 0.006f;
    }

    @Override
    public void drawSoftShadow(Unit unit, float alpha) {
        float z = unit.elevation > 0.5f ? (lowAltitude ? Layer.flyingUnitLow : Layer.flyingUnit) : groundLayer + Mathf.clamp(hitSize / 4000f, 0, 0.01f);
        Draw.z(z - 3f);
        super.drawSoftShadow(unit, alpha);
    }
    @Override
    public void load(){
        super.load();

        for(Rotor rotor : rotors) rotor.load();
    }

    @Override
    public void init(){
        super.init();

        Seq<Rotor> mapped = new Seq<>();
        for(Rotor rotor : rotors){
            mapped.add(rotor);
            if(rotor.mirror){
                Rotor copy = rotor.copy();
                copy.x *= -1f;
                copy.speed *= -1f;
                copy.rotorGlowSpeedMultiplier *= -1f;
                copy.rotOffset += 360f / (copy.bladeCount * 2);

                mapped.add(copy);
            }
        }
        rotors.set(mapped);
    }

    @Override
    public void draw(Unit unit){
        float z = unit.elevation > 0.5f ? (lowAltitude ? Layer.flyingUnitLow : Layer.flyingUnit) : groundLayer + Mathf.clamp(hitSize / 4000f, 0, 0.01f);
        super.draw(unit);
        Draw.z(z);
        if(unit instanceof Dronec) drawRotors((Unit & Dronec)unit);
    }

    public <T extends Unit & Dronec> void drawRotors(T unit){
        float z = unit.elevation > 0.5f ? (lowAltitude ? Layer.flyingUnitLow : Layer.flyingUnit) : groundLayer + Mathf.clamp(hitSize / 4000f, 0, 0.01f);
        applyColor(unit);

        Rotor.RotorMount[] rotors = unit.rotors();
        for(Rotor.RotorMount mount : rotors){
            Rotor rotor = mount.rotor;
                float rx = unit.x + Angles.trnsx(unit.rotation - 90, rotor.x, rotor.y);
                float ry = unit.y + Angles.trnsy(unit.rotation - 90, rotor.x, rotor.y);

                float rotorScl = Draw.scl * rotor.rotorSizeScl;
                float rotorTopScl = Draw.scl * rotor.rotorTopSizeScl;

                for (int i = 0; i < rotor.bladeCount; i++) {
                    float angle = (i * 360f / rotor.bladeCount + mount.rotorRot) % 360;
                    float blurAngle = (i * 360f / rotor.bladeCount + (mount.rotorRot * rotor.rotorBlurSpeedMultiplier)) % 360;

                    // region Normal Rotor
                    Draw.z(z + rotor.rotorLayer);
                    Draw.alpha(rotor.rotorShadeRegion.found() ? 1 - (unit.rotSpeedScl() / 0.8f) : 1);
                    Draw.mixcol(Color.white, unit.hitTime);
                    Draw.rect(rotor.bladeRegion, rx, ry,
                            rotor.bladeRegion.width * rotorScl,
                            rotor.bladeRegion.height * rotorScl,
                            unit.rotation - angle
                    );
                    // endregion Normal Rotor
                    Draw.reset();

                    // Blur Rotor
                    if (rotor.rotorNotRadial) {
                        if (rotor.rotorShadeRegion.found()) {
                            Draw.z(z + rotor.rotorLayer);
                            Draw.alpha(unit.rotSpeedScl() * rotor.rotorBlurAlphaMultiplier * (unit.dead() ? unit.rotSpeedScl() * 0.5f : 1));
                            Draw.rect(
                                    rotor.rotorShadeRegion, rx, ry,
                                    rotor.rotorShadeRegion.width * rotorScl,
                                    rotor.rotorShadeRegion.height * rotorScl,
                                    unit.rotation - -blurAngle
                            );
                        }

                        Draw.reset();

                    }
                }
                // Blur Rotor
                float blurAngle = (mount.rotorRot * rotor.rotorBlurSpeedMultiplier) % 360;
                float glowAngle = (mount.rotorRot * rotor.rotorBlurSpeedMultiplier * rotor.rotorGlowSpeedMultiplier) % 360;
                if (rotor.rotorRadial) {
                    if (rotor.rotorShadeRegion.found()) {
                        Draw.z(z + rotor.rotorLayer);
                        Draw.alpha(unit.rotSpeedScl() * rotor.rotorBlurAlphaMultiplier * (unit.dead() ? unit.rotSpeedScl() * 0.5f : 1));
                        Draw.rect(
                                rotor.rotorShadeRegion, rx, ry,
                                rotor.rotorShadeRegion.width * rotorScl,
                                rotor.rotorShadeRegion.height * rotorScl,
                                -blurAngle
                        );
                    }

                    Draw.reset();

                    if (rotor.drawGlow) {
                        if (rotor.rotorGlowRegion.found()) {
                            Draw.z(z + rotor.rotorLayer + 0.01f);
                            Draw.alpha(unit.rotSpeedScl() * rotor.rotorGlowAlphaMultiplier * (unit.dead() ? unit.rotSpeedScl() * 0.5f : 1));
                            Draw.rect(
                                    rotor.rotorGlowRegion, rx, ry,
                                    rotor.rotorGlowRegion.width * rotorScl,
                                    rotor.rotorGlowRegion.height * rotorScl,
                                    -glowAngle
                            );
                        }

                        Draw.reset();
                    }
                    // Rotor Top
                    if (rotor.drawRotorTop) {
                        Draw.z(z + rotor.rotorLayer + 0.02f);
                        Draw.rect(
                                rotor.topRegion, rx, ry,
                                rotor.topRegion.width * rotorTopScl,
                                rotor.topRegion.height * rotorTopScl,
                                unit.rotation - 90
                        );
                    }
                    Draw.reset();
                }
            }
        }
}