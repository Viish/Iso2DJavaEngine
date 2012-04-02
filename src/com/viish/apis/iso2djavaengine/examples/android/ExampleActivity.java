package com.viish.apis.iso2djavaengine.examples.android;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

public class ExampleActivity extends Activity {

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
        final View v = new ExampleView(this);
        layout.addView(v);
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask()
		{
			public void run()
			{
				runOnUiThread(new Runnable() {
					public void run() {
						v.invalidate();
					}
				});
			}
		}, 0, 100); // Time for Sprites' animation speed
    }
}