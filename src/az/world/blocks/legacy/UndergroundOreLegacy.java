package az.world.blocks.legacy;

import arc.graphics.g2d.Draw;
import mindustry.Vars;
import mindustry.graphics.Layer;
import mindustry.type.Item;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.OverlayFloor;

/**
 * Original code from FOS[<a href="https://github.com/TeamOct/FOS/blob/master/src/fos/type/blocks/environment/UndergroundOreBlock.java">...</a>]
 */
public class UndergroundOreLegacy extends OverlayFloor {
    public boolean shouldDrawBase = false;
    public Item drop;

    public UndergroundOreLegacy(String name) {
        super(name);
        //hide an ore from the minimap
        useColor = false;
        playerUnmineable = true;
        variants = 1;
    }

    @Override
    public void load() {
        super.load();

        //just in case somebody decides to declare itemDrop
        if (itemDrop != null) {
            drop = itemDrop;
            itemDrop = null;
        }
    }
    @Override
    public void drawBase(Tile tile) {
        if (shouldDrawBase || Vars.state.isEditor()) {
            float l = Draw.z();
            Draw.z(Layer.floor);

            super.drawBase(tile);

            Draw.z(l);
        }
    }
}
