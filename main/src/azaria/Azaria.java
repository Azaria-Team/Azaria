package azaria;
//SEX!

import arc.Events;
import arc.util.Log;
import azaria.annotations.Annotations;
import azaria.content.*;
import azaria.gen.AZContentRegionRegistry;
import azaria.gen.AZEntityMapping;
import azaria.gen.Regions;
import azaria.ui.dialogs.AzSettings;
import azaria.ui.dialogs.DisclaimerDialog;
import azaria.utils.Utils;
import mindustry.ctype.Content;
import mindustry.ctype.MappableContent;
import mindustry.game.EventType;
import mindustry.mod.Mod;
import mindustry.mod.Mods;

import static arc.Core.settings;
import static mindustry.Vars.content;
import static mindustry.Vars.headless;

@Annotations.LoadRegs("error")// Need this temporarily, so the class gets generated.
@Annotations.EnsureLoad
public class Azaria extends Mod {

    public static boolean tools = false;
    protected static Mods.LoadedMod mod;

    public Azaria() {
        this(false);
    }

    public Azaria(boolean tools) {
        Azaria.tools = tools;

        Events.on(EventType.ClientLoadEvent.class, e -> {
            if (!settings.getBool("@setting.azaria-show-disclaimer")) {
                new DisclaimerDialog().show();
            }
        });
        Events.on(EventType.FileTreeInitEvent.class, e -> AZSounds.load());

        Log.info("Loaded ExampleJavaMod constructor.");
        if (!headless) {
            Utils.init();
        }
        Events.on(EventType.ClientLoadEvent.class, i -> {
            AzSettings.load();
        });
        Events.on(EventType.ContentInitEvent.class, e -> {
            if(!headless){
                Regions.load();
                content.each(content -> {
                    if(isAzaria(content) && content instanceof MappableContent mContent){
                        AZContentRegionRegistry.load(mContent);
                    }
                });
            }
        });
    }

    public static boolean isAzaria(Content content) {
        return content.minfo.mod != null && content.minfo.mod.name.equals("azaria");
    }

    public static Mods.LoadedMod mod() {
        return mod;
    }

    @Override
    public void init() {
    }

    @Override
    public void loadContent() {
        AZSounds.load();
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
        AZEntityMapping.init();
    }
}

/*
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

 */

