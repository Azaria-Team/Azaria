package az.type.content;

import arc.graphics.Color;
import mindustry.type.Item;

public class AzItem extends Item {
    /** how overcharged the item is. */
    public float overcharged = 0f;
    /** how much damage does it make */
    public float itemDamage = 0f;

    public AzItem(String name) {
        super(name, new Color(Color.black));
    }
    public AzItem(String name, Color color) {
        super(name, color);
    }

}
