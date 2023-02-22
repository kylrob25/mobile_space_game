package me.krob.spacegame.drawable.object;

import me.krob.spacegame.drawable.Drawable;

public abstract class GameObject extends Drawable implements IGameObject {
    public GameObject(float height, float width) {
        super(height, width);
    }
}
