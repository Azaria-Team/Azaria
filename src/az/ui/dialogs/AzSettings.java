package az.ui.dialogs;

import arc.scene.ui.layout.Table;
import mindustry.ui.dialogs.SettingsMenuDialog;
import mindustry.ui.dialogs.SettingsMenuDialog.SettingsTable.*;

import static mindustry.Vars.ui;

public class AzSettings {
    public static boolean showLightning = true;

    public static void load(){
        ui.settings.addCategory("@settings.azaria","az-settings-icon", t -> {
            t.checkPref("@setting.azaria-show-disclaimer", true);
            t.checkPref("@setting.azaria-show-plasma-lightning", true, value -> {
                showLightning = value;
            });
        });
    }
    public static class Table extends Setting {
        public Table table;

        public Table(String name, Table table) {
            super(name);
            table = table;
        }

        @Override
        public void add(SettingsMenuDialog.SettingsTable table) {
            addDesc(table.add(table).growX().get());
            table.row();
        }
    }
}
