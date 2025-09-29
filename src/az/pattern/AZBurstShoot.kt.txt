//slish add kotlin support pls 🥺
package az.pattern

import arc.math.Rand
import mindustry.entities.pattern.ShootPattern

open class AZBurstShoot(val min: Int, val max: Int, var shotDelay: Float) : ShootPattern() {
    override fun shoot(totalShots: Int, handler: BulletHandler?) {
        val randNum = Rand().random(min, max)
        for (i in 1..randNum) {
            handler!!.shoot(0F, 0F, 0F, firstShotDelay + this.shotDelay * i)
        }
    }
}