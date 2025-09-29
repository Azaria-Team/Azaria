package az.ui.dialogs;

import arc.Core;
import arc.scene.actions.Actions;
import arc.scene.ui.TextButton;
import arc.util.Align;
import arc.util.Time;
import mindustry.ui.dialogs.BaseDialog;

public class DisclaimerDialog extends BaseDialog {
    private float leave = 3f * 60;

    private boolean canClose = false;

    public DisclaimerDialog() {
        super("@dialog.azaria-disclaimer.title", Core.scene.getStyle(DialogStyle.class));

        if(skip()) {
            return;
        }

        cont.add("@dialog.azaria-disclaimer")
                .width(500f)
                .wrap()
                .pad(4f)
                .get()
                .setAlignment(Align.center, Align.center);

        buttons.defaults().size(200f, 54f).pad(2f);
        setFillParent(false);

        TextButton button = buttons.button("@button.azaria-ok", this::hide).get();

        button.actions(
                Actions.alpha(0),
                Actions.moveBy(0f, 0f),
                Actions.delay(1f),
                Actions.fadeIn(0.5f),
                Actions.delay(0.5f)
        );

        TextButton button1 = buttons.button("@button.azaria-dont-show-again", () -> {
            hide();
            Core.settings.put("@setting.azaria-show-disclaimer", true);

        }).get();

        button1.actions(
                Actions.alpha(0),
                Actions.moveBy(0f, 0f),
                Actions.delay(2f),
                Actions.fadeIn(1f),
                Actions.delay(1f)
        );
/*
        update(() -> {
            leave -= Time.delta;
            if (leave < 0 && !canClose) {
                canClose = true;
            }
        });

        cont.image(Core.atlas.find("az-title")).pad(3f).height(70).width(700).row();
        cont.add(text("d-attention")).row();
        cont.add(Core.bundle.format("d.description")).row();
        buttons.button("", this::hide).update(b -> {
            b.setDisabled(!canClose);
            b.setText(canClose ? text("d-got-it") : "[accent]" + Math.floor(leave / 60) + text("d-seconds"));
        }).size(150f, 50f).center();


 */
    };

    boolean skip() {
        return Core.settings.getBool("@setting.azaria-show-disclaimer", false);
    }
}
