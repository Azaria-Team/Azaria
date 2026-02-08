package azaria.world.blocks.power;

import azaria.world.meta.AZStat;
import mindustry.game.Team;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.world.Tile;
import mindustry.world.blocks.power.ThermalGenerator;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import mindustry.world.meta.StatValues;

import arc.util.Time;

import static mindustry.Vars.*;

public class ThermalEvaporator extends ThermalGenerator {

    public int spacing = 4;

    public ThermalEvaporator(String name) {
        super(name);
        buildType = ThermalEvaporatorBuild::new;
    }

    @Override
    public void drawOverlay(float x, float y, int rotation) {
        if (spacing < 1)
            return;
        Drawf.dashSquare(Pal.remove, x, y, (size + spacing * 2) * tilesize);
    }

    @Override
    public void setStats() {
        super.setStats();

        stats.add(Stat.tiles, attribute, floating, size * size * displayEfficiencyScale, !displayEfficiency);
        stats.remove(generationType);
        stats.add(generationType, powerProduction * 60.0f / displayEfficiencyScale, StatUnit.powerSecond);

        if (outputLiquid != null) {
            stats.add(Stat.output,
                    StatValues.liquid(outputLiquid.liquid, outputLiquid.amount * size * size * 60f, true));
        }
        stats.add(AZStat.placeSpacing.toStat(), spacing, StatUnit.blocks);
    }

    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation) {
        if (spacing < 1)
            return true;

        int range = spacing + size;

        for (int x = tile.x - range; x <= tile.x + range; x++) {
            for (int y = tile.y - range; y <= tile.y + range; y++) {
                Tile other = world.tile(x, y);
                if (other != null && other.block() instanceof ThermalEvaporator) {
                    return false;
                }
            }
        }
        return tile.getLinkedTilesAs(this, tempTiles)
                .sumf(other -> other.floor().attributes.get(attribute)) > minEfficiency;
    }

    public class ThermalEvaporatorBuild extends ThermalGeneratorBuild {
        public float checkTimer = 0f;
        public boolean spacingClash = false;

        @Override
        public void onProximityUpdate() {
            super.onProximityUpdate();
            updateSpacingClash();
            if (spacingClash) {
                productionEfficiency = 0f;
            }
        }

        @Override
        public void updateTile() {
            super.updateTile();

            checkTimer += Time.delta;
            if (checkTimer >= 60f) {
                checkTimer = 0f;
                updateSpacingClash();
            }

            if (spacingClash) {
                productionEfficiency = 0f;
            }
        }

        public void updateSpacingClash() {
            if (spacing < 1) {
                spacingClash = false;
                return;
            }

            int range = spacing + size;
            spacingClash = false;

            for (int x = tile.x - range; x <= tile.x + range; x++) {
                for (int y = tile.y - range; y <= tile.y + range; y++) {
                    Tile other = world.tile(x, y);
                    if (other != null && other.block() instanceof ThermalEvaporator && other.build != this) {
                        spacingClash = true;
                        return;
                    }
                }
            }
        }
    }
}