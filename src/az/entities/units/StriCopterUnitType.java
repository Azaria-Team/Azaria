package az.entities.units;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.Angles;
import arc.math.Mathf;
import arc.struct.Seq;
import az.entities.entity.StriCopterUnitEntityLegacy;
import az.world.draw.Blade;
import azaria.gen.StriCopterUnit;
import azaria.gen.StriCopterc;
import mindustry.gen.Unit;
import mindustry.graphics.Layer;
import mindustry.type.UnitType;

public class StriCopterUnitType extends UnitType {
    public final Seq<Blade> blade = new Seq<>();

    public float spinningFallSpeed = 0.5f;
    public float bladeDeathMoveSlowdown = 0.01f;
    public float fallSmokeX = 0f, fallSmokeY = -5f, fallSmokeChance = 0.1f;

    public StriCopterUnitType(String name) {
        super(name);
        engineSize = 0f;
        constructor = StriCopterUnit::create;
    }

    // Drawing Rotors
    public void drawRotor(Unit unit) {
        float z = unit.elevation > 0.5f ? (lowAltitude ? Layer.flyingUnitLow : Layer.flyingUnit) : groundLayer + Mathf.clamp(hitSize / 4000f, 0, 0.01f);
        applyColor(unit);
        if(unit instanceof StriCopterc stri) {
            for (int sign : Mathf.signs) {
                long seedOffset = 0;
                for (Blade.BladeMount mount : stri.blades()) {
                    Blade blade = mount.blade;
                    float rx = unit.x + Angles.trnsx(unit.rotation - 90, blade.x * sign, blade.y);
                    float ry = unit.y + Angles.trnsy(unit.rotation - 90, blade.x * sign, blade.y);
                    float bladeScl = Draw.scl * blade.bladeSizeScl;
                    float shadeScl = Draw.scl * blade.shadeSizeScl;

                    if (blade.bladeRegion.found()) {
                        Draw.z(z + blade.bladeLayer);
                        Draw.alpha(blade.blurRegion.found() ? 1 - (stri.bladeMoveSpeedScl() / 0.8f) : 1);
                        Draw.mixcol(Color.white, unit.hitTime);
                        Draw.rect(blade.bladeRegion, rx, ry,
                                blade.bladeRegion.width * bladeScl * sign,
                                blade.bladeRegion.height * bladeScl,
                                unit.rotation - 90 + sign * Mathf.randomSeed(stri.drawSeed() + (seedOffset++), blade.bladeMoveSpeed, -blade.minimumBladeMoveSpeed)
                        );

                        if (blade.doubleBlade) {
                            Draw.mixcol(Color.white, unit.hitTime);
                            Draw.rect(blade.bladeRegion, rx, ry,
                                    blade.bladeRegion.width * bladeScl * sign,
                                    blade.bladeRegion.height * bladeScl,
                                    -unit.rotation - 90 + sign * Mathf.randomSeed(stri.drawSeed() + (seedOffset++), blade.bladeMoveSpeed, -blade.minimumBladeMoveSpeed)
                            );
                        }
                        Draw.reset();
                    }
                    Draw.reset();

                    if (blade.blurRegion.found()) {
                        Draw.z(z + blade.bladeLayer);
                        Draw.alpha(stri.bladeMoveSpeedScl() * blade.bladeBlurAlphaMultiplier * (stri.dead() ? stri.bladeMoveSpeedScl() * 0.5f : 1));
                        Draw.rect(
                                blade.blurRegion, rx, ry,
                                blade.blurRegion.width * bladeScl * sign,
                                blade.blurRegion.height * bladeScl,
                                unit.rotation - 90 + sign * Mathf.randomSeed(stri.drawSeed() + (seedOffset++), blade.bladeMoveSpeed, -blade.minimumBladeMoveSpeed)
                        );

                        // Double Rotor Blur
                        if (blade.doubleBlade) {
                            Draw.rect(
                                    blade.blurRegion, rx, ry,
                                    blade.blurRegion.width * bladeScl * sign,
                                    blade.blurRegion.height * bladeScl,
                                    unit.rotation - 90 + sign * Mathf.randomSeed(stri.drawSeed() + (seedOffset++), blade.bladeMoveSpeed, -blade.minimumBladeMoveSpeed)
                            );
                        }
                        Draw.reset();
                    }

                    Draw.reset();
                    if (blade.shadeRegion.found()) {
                        Draw.z(z + blade.bladeLayer + 0.001f);
                        Draw.alpha(stri.bladeMoveSpeedScl() * blade.bladeBlurAlphaMultiplier * (stri.dead() ? stri.bladeMoveSpeedScl() * 0.5f : 1));
                        Draw.rect(
                                blade.shadeRegion, rx, ry,
                                blade.shadeRegion.width * shadeScl * sign,
                                blade.shadeRegion.height * shadeScl,
                                unit.rotation - 90 + sign * Mathf.randomSeed(stri.drawSeed() + (seedOffset++), blade.bladeMoveSpeed, -blade.minimumBladeMoveSpeed)
                        );
                        Draw.mixcol(Color.white, unit.hitTime);
                        if (blade.dounbleBlur) {
                            Draw.alpha(stri.bladeMoveSpeedScl() * blade.bladeBlurAlphaMultiplier * (stri.dead() ? stri.bladeMoveSpeedScl() * 0.5f : 1));
                            Draw.rect(
                                    blade.shadeRegion, rx, ry,
                                    blade.shadeRegion.width * shadeScl * sign,
                                    blade.shadeRegion.height * shadeScl,
                                    unit.rotation - 90 + sign * Mathf.randomSeed(stri.drawSeed() + (seedOffset++), blade.bladeMoveSpeed, -blade.minimumBladeMoveSpeed)
                            );
                            Draw.reset();
                        }
                    }
                    Draw.reset();
                }
            }
        }
    }

    @Override
    public void draw(Unit unit) {
        float z = unit.elevation > 0.5f ? (lowAltitude ? Layer.flyingUnitLow : Layer.flyingUnit) : groundLayer + Mathf.clamp(hitSize / 4000f, 0, 0.01f);
        super.draw(unit);
        Draw.z(z);
        drawRotor(unit);
    }

    @Override
    public void load() {
        super.load();
        blade.each(Blade::load);
    }
}