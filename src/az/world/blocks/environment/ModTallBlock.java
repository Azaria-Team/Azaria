package az.world.blocks.environment;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import mindustry.graphics.Layer;
import mindustry.world.Block;
import mindustry.world.Tile;

public class ModTallBlock extends Block {
    public float layer = Layer.power + 1;
    public float rotationRand = 20f;

    public ModTallBlock(String name){
        super(name);
        solid = true;
        clipSize = 90;
    }

    @Override
    public void drawBase(Tile tile){
        float rot = Mathf.randomSeedRange(tile.pos() + 1, rotationRand);
        Draw.color();

        Draw.z(Layer.power + 1);
        Draw.rect(variants > 0 ? variantRegions[Mathf.randomSeed(tile.pos(), 0, Math.max(0, variantRegions.length - 1))] : region,
                tile.worldx(), tile.worldy(), rot);
    }

    @Override
    public TextureRegion[] icons(){
        return variants == 0 ? super.icons() : new TextureRegion[]{Core.atlas.find(name + "1")};
    }
}
