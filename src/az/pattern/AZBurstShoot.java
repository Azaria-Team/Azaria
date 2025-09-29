package az.pattern;

import mindustry.entities.pattern.ShootPattern;
import arc.math.Rand;

public class AZBurstShoot extends ShootPattern {
    public int min;
    public int max;

    public AZBurstShoot(int min, int max, float shotDelay) {
        this.min = min;
        this.max = max;
        this.shotDelay = shotDelay;
    }

    @Override
    public void shoot(int totalShots, ShootPattern.BulletHandler handler) {
        int randNum = new Rand().random(min, max);

        for(int i = 0; i <= randNum; ++i) {
            //TODO fix the delay (MAYBE)
            handler.shoot(0, 0, 0, firstShotDelay + shotDelay * i);
        }
    }
}
