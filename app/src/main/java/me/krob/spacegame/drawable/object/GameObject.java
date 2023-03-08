package me.krob.spacegame.drawable.object;

import me.krob.spacegame.drawable.Drawable;

public abstract class GameObject extends Drawable implements IGameObject {
    protected final GameObjectType type;

    public GameObject(GameObjectType type, float height, float width) {
        super(height, width);
        this.type = type;
    }

    public GameObjectType getType() {
        return type;
    }

    public float getLocX() {
        return locX;
    }

    public float getLocY() {
        return locY;
    }
}
