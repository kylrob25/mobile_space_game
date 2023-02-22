package me.krob.spacegame.drawable.object;

public interface IGameObject {
    void update(long framesPerSecond);
    void handleCollisions();
}
