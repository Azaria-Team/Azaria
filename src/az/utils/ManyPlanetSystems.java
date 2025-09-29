package az.utils;

import arc.*;
import arc.struct.*;
import mindustry.*;
import mindustry.game.EventType.*;
import mindustry.graphics.g3d.*;
import mindustry.type.*;
import mindustry.ui.dialogs.*;

public class ManyPlanetSystems{
    private final static EventSender hasSolarSystemSender = new EventSender("has-many-planet-systems");
    private final static EventReceiver hasSolarSystemReceiver = new EventReceiver("has-many-planet-systems");

    public static void init(){
        boolean has[] = {false};
        hasSolarSystemSender.<Runnable>setParameter("callback", () -> has[0] = true);
        hasSolarSystemSender.fire(true);
        if(has[0]) return;
        hasSolarSystemReceiver.post(event -> {
            if(event.hasParameter("callback", Runnable.class)){
                event.<Runnable>getParameter("callback").run();
            }
        });
        Events.run(ClientLoadEvent.class, () -> {
            ObjectSet<Planet> solarSystemsSet = new ObjectSet<>();
            for(Planet planet : Vars.content.planets()){
                Planet solarSystem = planet.solarSystem;
                if(solarSystem == null) continue;
                solarSystemsSet.add(solarSystem);
            }
            Planet[] solarSystems = solarSystemsSet.toSeq().toArray(Planet.class);
            Events.run(Trigger.update, () -> {
                for(Planet planet : solarSystems){
                    updatePlanet(planet);
                }
                PlanetParams state = Vars.ui.planet.state;
                if(state.solarSystem != state.planet.solarSystem){
                    state.solarSystem = state.planet.solarSystem;
                }
            });
        });
    }

    private static void updateDialog(){

        PlanetDialog dialog = Vars.ui.planet;
        if(dialog.isShown()){

            dialog.show(dialog.getScene(), null);
        }
    }

    private static void updatePlanet(Planet planet){
        planet.position.setZero();
        planet.addParentOffset(planet.position);
        if(planet.parent != null){
            planet.position.add(planet.parent.position);
        }
        for(Planet child : planet.children){
            updatePlanet(child);
        }
    }
}