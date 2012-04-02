package com.viish.apis.iso2djavaengine.examples.android;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.ZoomControls;

public class ExampleActivity extends Activity {

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.layout);
        final ExampleView ev = new ExampleView(this);
        layout.addView(ev);
        
        ZoomControls zoomControls = new ZoomControls(this);
        layout.addView(zoomControls);
        zoomControls.setOnZoomInClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    ev.zoomIn();
            }
	    });
	    zoomControls.setOnZoomOutClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
                    ev.zoomOut();
	            }
	    });
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask()
		{
			public void run()
			{
				runOnUiThread(new Runnable() {
					public void run() {
						ev.invalidate();
					}
				});
			}
		}, 0, 100); // Time for Sprites' animation speed
    }
}