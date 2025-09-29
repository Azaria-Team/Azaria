package az.world.blocks.defense;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.util.Nullable;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Building;
import mindustry.gen.Unit;
import mindustry.world.Block;

public class ModMine extends Block {
    public final int timerDamage = timers++;

    public float cooldown = 80f;
    public float tileDamage = 5f;
    public float damage = 13;
    public int length = 10;
    public int shots = 6;
    public float inaccuracy = 0f;
    public @Nullable BulletType bullet;
    public float teamAlpha = 0.3f;

        public ModMine(String name){
        super(name);
        update = false;
        destructible = true;
        solid = false;
        targetable = false;
    }

    @Override
    public void load() {
        super.load();
        teamRegion = Core.atlas.find("-team-top");
    }

    public class ModMineBuild extends Building {

        @Override
        public void drawTeam(){
            //no
        }

        @Override
        public void draw(){
            super.draw();
            Draw.color(team.color, teamAlpha);
            Draw.rect(teamRegion, x, y);
            Draw.color();
        }

        @Override
        public void drawCracks(){
            //no
        }

        @Override
        public void unitOn(Unit unit){
            if(enabled && unit.team != team && timer(timerDamage, cooldown)){
                triggered();
                damage(tileDamage);
            }
        }

        public void triggered(){
            if(bullet != null){
                for(int i = 0; i < shots; i++){
                    bullet.create(this, x, y, (360f / shots) * i + Mathf.random(inaccuracy));
                }
            }
        }
    }
}
