package az.world.draw;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Angles;
import arc.math.Interp;
import arc.math.Mathf;
import mindustry.gen.Building;
import mindustry.graphics.Pal;
import mindustry.world.*;
import mindustry.world.draw.DrawBlock;

public class DrawDrillPart extends DrawBlock{
    public float angleOffset, baseOffset, maxOffset;
    public int sides = 4;
    public Interp offsetInterp = Interp.smooth2;
    /** Any number <=0 disables layer changes. */
    public float layer = -1;
    public boolean drawPlan = true;
    public float shadowOffset = -1f;
    public String suffix = "-dpart";
    public TextureRegion reg1, reg2, regt;

    public DrawDrillPart(float maxOffset){
        this.maxOffset = maxOffset;
    }

    public DrawDrillPart(String suffix, float maxOffset){
        this(maxOffset);
        this.suffix = suffix;
    }

    @Override
    public void draw(Building build){
        float z = Draw.z();
        if(layer > 0) Draw.z(layer);
        float len = baseOffset + offsetInterp.apply(build.progress()) * maxOffset;
        for(int i = 0; i < sides; i++){
            float angle = angleOffset + i * 360f / sides;
            TextureRegion reg =
                    regt.found() && (Mathf.equal(angle, 315) || Mathf.equal(angle, 135)) ? regt :
                            angle >= 135 && angle < 315 ? reg2 : reg1;

            if(Mathf.equal(angle, 315) || (angle % 180 > 45 && angle % 180 < 135)){
                Draw.yscl = -1f;
            }

            if(shadowOffset > 0){
                float sz = Draw.z();
                Draw.z(sz - 0.001f);
                Draw.color(Pal.shadow);
                Draw.rect(reg, build.x + Angles.trnsx(angle, len) - shadowOffset, build.y + Angles.trnsy(angle, len) - shadowOffset, angle);
                Draw.color();
                Draw.z(sz);
            }
            Draw.rect(reg, build.x + Angles.trnsx(angle, len), build.y + Angles.trnsy(angle, len), angle);

            Draw.yscl = 1f;
        }
        Draw.z(z);
    }
/*
    @Override
    public void drawPlan(Block block, BuildPlan plan, Eachable<BuildPlan> list){
        if(!drawPlan) return;
        Draw.rect(regPrev, plan.drawx(), plan.drawy());
    }

 */
/*
    @Override
    public TextureRegion[] icons(Block block){
        return new TextureRegion[]{regPrev};
    }


 */
    @Override
    public void load(Block block){
        super.load(block);

        reg1 = Core.atlas.find(block.name + suffix + "0", block.name + suffix);
        reg2 = Core.atlas.find(block.name + suffix + "1", block.name + suffix);
        regt = Core.atlas.find(block.name + suffix + "-t");
        //regPrev = Core.atlas.find(block.name + suffix + "-preview");
    }
}

