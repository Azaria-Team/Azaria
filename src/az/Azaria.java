package az;
//SEX!
import arc.Core;
import arc.Events;
import arc.scene.Group;
import arc.scene.actions.Actions;
import arc.scene.event.Touchable;
import arc.scene.ui.layout.Table;
import arc.scene.ui.layout.WidgetGroup;
import arc.util.Log;
import az.ui.dialogs.AzSettings;
import az.ui.dialogs.DisclaimerDialog;
import az.utils.ManyPlanetSystems;
import az.utils.Utils;
import azaria.gen.EntityRegistry;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.mod.*;
import az.content.*;

import static arc.Core.settings;
import static mindustry.Vars.*;

public class Azaria extends Mod{

    @Override
    public void init() {
        super.init();
        ManyPlanetSystems.init();
    }

    public Azaria(){
        super();
        Events.on(EventType.ClientLoadEvent.class, e -> {
            if (!settings.getBool("@setting.azaria-show-disclaimer")) {
                new DisclaimerDialog().show();
            }
        });
        Events.on(EventType.FileTreeInitEvent.class, e -> AZSounds.load());

        Log.info("Loaded ExampleJavaMod constructor.");
        if(!headless){
            Utils.init();
        }
        Events.on(EventType.ClientLoadEvent.class, i-> { AzSettings.load();
        });
    }

    @Override
    public void loadContent(){
        EntityRegistry.register();
        AZItems.load();
        AZLiquids.load();
        AZStatusEffects.load();
        AZBullets.load();
        AZUnits.load();
        AZBlocks.load();
        AZLoadouts.load();
        AZPlanets.load();
        AZSectorPreset.load();
        AZWheather.load();
        AZTechTree.load();
    }
}
