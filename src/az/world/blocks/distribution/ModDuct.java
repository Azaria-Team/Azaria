package az.world.blocks.distribution;

import arc.Core;
import arc.func.Boolf;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.math.geom.Geometry;
import arc.math.geom.Point2;
import arc.struct.Seq;
import arc.util.Nullable;
import az.content.blocks.AZDistribution;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.Sounds;
import mindustry.input.Placement;
import mindustry.world.Block;
import mindustry.world.blocks.distribution.*;

public class ModDuct extends Duct {
    public @Nullable
    Block junctionReplacement, bridgeReplacement;
    public String bottomName = "az-magnetic-conveyor-bottom";

    public ModDuct(String name) {
        super(name);
        ambientSound = Sounds.conveyor;
        ambientSoundVolume = 0.0022f;
    }

    @Override
    public void init(){
        super.init();

        if(junctionReplacement == null) junctionReplacement = AZDistribution.magneticJunction;
        if(bridgeReplacement == null || !(bridgeReplacement instanceof ItemBridge)) bridgeReplacement = AZDistribution.magneticBridgeConveyor;
    }

    @Override
    public TextureRegion[] icons(){
        return new TextureRegion[]{Core.atlas.find(bottomName), topRegions[0]};
    }

    @Override
    public void handlePlacementLine(Seq<BuildPlan> plans){
        if(bridgeReplacement == null) return;

        Placement.calculateBridges(plans, (ItemBridge) bridgeReplacement);
    }

    @Override
    public Block getReplacement(BuildPlan req, Seq<BuildPlan> plans){
        if(junctionReplacement == null) return this;

        Boolf<Point2> cont = p -> plans.contains(o -> o.x == req.x + p.x && o.y == req.y + p.y && (req.block instanceof Duct || req.block instanceof Junction));
        return cont.get(Geometry.d4(req.rotation)) &&
                cont.get(Geometry.d4(req.rotation - 2)) &&
                req.tile() != null &&
                req.tile().block() instanceof Duct &&
                Mathf.mod(req.tile().build.rotation - req.rotation, 2) == 1 ? junctionReplacement : this;
    }
}
