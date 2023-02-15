package me.krob.spacegame;

import android.os.Bundle;
import android.graphics.Point;
import android.view.Display;

import androidx.appcompat.app.AppCompatActivity;

import me.krob.spacegame.view.SpaceGameView;

public class MainActivity extends AppCompatActivity {
    private SpaceGameView spaceGameView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);

        spaceGameView = new SpaceGameView(this, size.x, size.y);
        setContentView(spaceGameView);
    }

    protected void onResume() {
        super.onResume();
        spaceGameView.resume();
    }

    protected void onPause() {
        super.onPause();
        spaceGameView.pause();
    }
}

