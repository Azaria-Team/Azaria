package az.world.blocks.legacy;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import az.world.blocks.production.DrawerBurstDrill;
import mindustry.content.Items;
import mindustry.type.Item;
import mindustry.world.Block;
import mindustry.world.Tile;

import static mindustry.Vars.*;

/**
 * Original code from FOS[<a href="https://github.com/TeamOct/FOS/blob/master/src/fos/type/blocks/production/UndergroundDrill.java">...</a>]
 */
public class UndergroundDrillLegacy extends DrawerBurstDrill {
    public UndergroundDrillLegacy(String name){
        super(name);
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        //super.drawPlace(x, y, rotation, valid);

        Tile tile = world.tile(x, y);
        if (tile == null) return;


        countOre(tile);

        if (returnItem != null) {
            float width = drawPlaceText(Core.bundle.formatFloat("bar.drillspeed", 60f / getDrillTime(returnItem) * returnCount, 2), x, y, valid);
            float dx = x * tilesize + offset - width/2f - 4f, dy = y * tilesize + offset + size * tilesize / 2f + 5, s = iconSmall / 4f;
            Draw.mixcol(Color.darkGray, 1f);
            Draw.rect(returnItem.fullIcon, dx, dy - 1, s, s);
            Draw.reset();
            Draw.rect(returnItem.fullIcon, dx, dy, s, s);

            if(drawMineItem) {
                Draw.color(returnItem.color);
                Draw.rect(itemRegion, tile.worldx() + offset, tile.worldy() + offset);
                Draw.color();
            }
        } else {
            Tile to = tile.getLinkedTilesAs(this, tempTiles).find(t -> getUnderDrop(t.overlay()) != null && (getUnderDrop(t.overlay()).hardness > tier || getUnderDrop(t.overlay()) == blockedItem));
            Item item = to == null ? null : to.drop();
            if(item != null) {
                drawPlaceText(Core.bundle.get("bar.drilltierreq"), x, y, valid);
            }
        }
    }


    @Override
    public boolean canMine(Tile tile) {
        if(tile == null || tile.block().isStatic()) return false;
        Item drops = getUnderDrop(tile.overlay());
        return drops != null && drops.hardness <= tier && drops != blockedItem;
    }

    @Override
    protected void countOre(Tile tile) {
        returnItem = null;
        returnCount = 0;

        oreCount.clear();
        itemArray.clear();

        for(Tile other : tile.getLinkedTilesAs(this, tempTiles)){
            if(canMine(other) && (other.overlay() instanceof UndergroundOreLegacy)) {
                oreCount.increment(getUnderDrop(other.overlay()), 0, 1);
            }
        }

        for(Item item : oreCount.keys()){
            itemArray.add(item);
        }

        itemArray.sort((item1, item2) -> {
            int type = Boolean.compare(!item1.lowPriority, !item2.lowPriority);
            if(type != 0) return type;
            int amounts = Integer.compare(oreCount.get(item1, 0), oreCount.get(item2, 0));
            if(amounts != 0) return amounts;
            return Integer.compare(item1.id, item2.id);
        });

        if(itemArray.size == 0){
            return;
        }

        returnItem = itemArray.peek();
        returnCount = oreCount.get(itemArray.peek(), 0);
    }

    public Item getOutput(Tile tile) {
        countOre(tile);

        //if nothing's under the drill, mine sand
        return returnItem != null ? returnItem : Items.sand;
    }

    protected Item getUnderDrop(Block b) {
        return b instanceof UndergroundOreLegacy u ? u.drop : null;
    }

    public class UndergroundDrillBuild extends DrillBuild {
        @Override
        public void onProximityUpdate() {
            super.onProximityUpdate();

            dominantItem = getOutput(tile);
            if (dominantItem == Items.sand) dominantItems = (int)Mathf.sqr(size);
        }
    }
}
