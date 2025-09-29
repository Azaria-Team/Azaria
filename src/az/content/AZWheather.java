package az.content;

import arc.graphics.Color;
import arc.graphics.g2d.TextureRegion;
import arc.util.Time;
import az.type.weather.Element;
import az.type.weather.StormWeather;
import az.type.weather.WeatherElement;
import mindustry.gen.Sounds;
import mindustry.type.Liquid;
import mindustry.type.Weather;
import mindustry.type.weather.RainWeather;

public class AZWheather {
    public static Weather
            smallRain, storm;

    public static void load() {
        smallRain = new RainWeather("small-rain") {{
            density = 100f;
            sizeMin = 5f;
            sizeMax = 15f;

            xspeed = 5;
            yspeed = 4f;
            stroke = 0.4f;
            soundVol = 0.8f;
            duration = 5 * Time.toMinutes;
            padding = 30f;
            liquid = AZLiquids.oxyliteLiq;
            splashes = new TextureRegion[12];
            color = Color.valueOf("50a9a8");
            sound = Sounds.rain;
        }};
        storm = new StormWeather("storm") {{
            density = 400f;
            sizeMin = 20f;
            sizeMax = 30f;
            sound = Sounds.rain;
            xspeed = -18;
            yspeed = -16f;
            stroke = 0.8f;
            soundVol = 0.8f;
            duration = 5 * Time.toMinutes;
            padding = 45f;
            force = 0.8f;
            element = new Element[]{new WeatherElement.Thunder("")};
        }};
    }
}
