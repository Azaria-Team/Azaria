package az.type.weather;

import arc.util.Time;
import mindustry.gen.Groups;
import mindustry.gen.Unit;
import mindustry.gen.WeatherState;
import mindustry.type.weather.ParticleWeather;

public class ElementWeather extends ParticleWeather {
    public Element[] element = {};

    public boolean update = false;
    public ElementWeather(String name){
        super(name);
    }

    @Override
    public WeatherState create(float intensity, float duration){
        WeatherState state = super.create(intensity, duration);

        init(state);

        return state;
    }

    @Override
    public void update(WeatherState state){
        super.update(state);
        if(update)updateWeather(state);
        float speed = force * state.intensity * Time.delta;
        if(speed > 0.001f){
            float windx = state.windVector.x * speed, windy = state.windVector.y * speed;

            for(Unit unit : Groups.unit){
                unit.impulse(windx, windy);
            }
        }
    }

    public void init(WeatherState state){
        for(Element e : element){
            e.init(state);
        }
    }


    public void updateWeather(WeatherState state) {
        for (Element e : element) {
            e.update(state);
        }
    }
}
