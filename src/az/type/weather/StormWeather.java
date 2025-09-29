package az.type.weather;

import arc.Core;
import arc.audio.Sound;
import arc.graphics.Color;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import az.content.AZLiquids;
import az.content.AZSounds;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.gen.WeatherState;
import mindustry.graphics.Pal;
import mindustry.type.Liquid;

public class StormWeather extends ElementWeather {

    public float yspeed = 5f, xspeed = 1.5f, padding = 16f, density = 1200f, stroke = 0.75f, sizeMin = 8f, sizeMax = 40f, splashTimeScale = 22f;
    public Liquid liquid = AZLiquids.oxyliteLiq;
    public TextureRegion[] splashes = new TextureRegion[12];
    public Color color = Color.valueOf("50a9a8");
    public int lightning = 1;
    public float lightningChance = 0.004f;
    public Sound lightningSound = AZSounds.thunder_blast;

    public StormWeather(String name) {
        super("thunder");
    }

    @Override
    public void update(WeatherState state){
        super.update(state);

        if(Mathf.chanceDelta(lightningChance * state.opacity * state.intensity / 2)){
            Fx.lightning.at(Core.camera.position.x, Core.camera.position.y, Pal.lancerLaser);

            if(Mathf.chanceDelta(1 - (lightningChance * 5))){
                float mag = (float) Math.random() * 5;

                if(mag > 1f && !Vars.headless) Vars.renderer.shake(mag, mag * 2);

                if(mag > 3.5f){
                    lightningSound.play(mag / 5, 0.6f + (float) Math.random() * 0.5f, Mathf.random(-0.5f, 0.5f));

                    if(Mathf.chanceDelta(0.5f)) updateWeather(state);
                }
            }
        }
    }

    @Override
    public void load(){
        super.load();

        for(int i = 0; i < splashes.length; i++){
            splashes[i] = Core.atlas.find("splash-" + i);
        }
    }

    @Override
    public void drawOver(WeatherState state){
        drawRain(sizeMin, sizeMax, xspeed, yspeed, density, state.intensity, stroke, color);
    }

    @Override
    public void drawUnder(WeatherState state){
        drawSplashes(splashes, sizeMax, density, state.intensity, state.opacity, splashTimeScale, stroke, color, liquid);
    }



}
