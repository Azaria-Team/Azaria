package az.world.blocks.defense.wall;

import arc.util.Time;
import az.content.AZLiquids;
import az.type.content.AzItem;
import mindustry.content.Liquids;
import mindustry.gen.Building;
import mindustry.type.Liquid;
import mindustry.world.blocks.defense.Wall;

public class HealingWall extends Wall {
    public float healAmount = 5f;
    public Liquid healLiquid = AZLiquids.oxyliteLiq;
    public float liquidConsume = 0.1f;
    public HealingWall(String name) {
        super(name);
        update = true;
        hasLiquids = true;
        liquidCapacity = 40;
    }

    public class AZHealWallBuild extends WallBuild {

        @Override
        public boolean acceptLiquid(Building source, Liquid liquid) {
            return liquid == healLiquid;
        }

        @Override
        public void updateTile() {
            super.updateTile();

            if (health < maxHealth && liquids.get(healLiquid) > 0.01f) {
                // Calculate the required health
                float healNeeded = maxHealth - health;
                float healAmounts = healAmount * Time.delta;
                float maxHeal = Math.min(healAmounts, healNeeded);

                health += maxHeal;

                float consume = liquidConsume * (maxHeal / healAmounts) * Time.delta;
                liquids.remove(healLiquid, consume);
            }
            if(liquids.currentAmount() > 0.01f){
                dumpLiquid(liquids.current());
            }
        }
    }
}
