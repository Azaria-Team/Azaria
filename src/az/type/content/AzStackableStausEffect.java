package az.type.content;

import mindustry.type.StatusEffect;

public class AzStackableStausEffect extends StatusEffect {
    /** Current status effect stack */
    protected int currentStack = 0;
    /** Max status effect stack can be applied */
    public int maxStack = 1;
    /** Stack lifetime */
    public float stackLifetime = 60f;
    /** Updates per stack */
    public AzStackableStausEffect.statsPerStack statsPerStack;

    public AzStackableStausEffect(String name) {
        super(name);
    }

    public class statsPerStack {
        public float damageMultiplierPerStack = 0f;
        public float healthMultiplierPerStack = 0f;
        public float speedMultiplierPerStack = 0f;
        public float reloadMultiplierPerStack = 0f;
        public float buildSpeedMultiplierPerStack = 0f;
        public float dragMultiplierPerStack = 0f;
        public float transitionDamagePerStack = 0f;
        public float damagePerStack = 0f;

        public statsPerStack() {

        }
    }

    public void stackIncrease() {
        if (currentStack < 0) {
            return;
        }
        this.damageMultiplier += statsPerStack.damageMultiplierPerStack;
        this.healthMultiplier += statsPerStack.healthMultiplierPerStack;
        this.speedMultiplier += statsPerStack.speedMultiplierPerStack;
        this.reloadMultiplier += statsPerStack.reloadMultiplierPerStack;
        this.buildSpeedMultiplier += statsPerStack.buildSpeedMultiplierPerStack;
        this.dragMultiplier += statsPerStack.dragMultiplierPerStack;
        this.transitionDamage += statsPerStack.transitionDamagePerStack;
        this.damage += statsPerStack.damagePerStack;
    }

    public void stackDecrease() {
        if (currentStack > maxStack) {
            return;
        }
        this.damageMultiplier -= statsPerStack.damageMultiplierPerStack;
        this.healthMultiplier -= statsPerStack.healthMultiplierPerStack;
        this.speedMultiplier -= statsPerStack.speedMultiplierPerStack;
        this.reloadMultiplier -= statsPerStack.reloadMultiplierPerStack;
        this.buildSpeedMultiplier -= statsPerStack.buildSpeedMultiplierPerStack;
        this.dragMultiplier -= statsPerStack.dragMultiplierPerStack;
        this.transitionDamage -= statsPerStack.transitionDamagePerStack;
        this.damage -= statsPerStack.damagePerStack;
    }

}
