package com.example.location;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;


public class MainActivity extends Activity {
	
	SensorManager sm = null;
	Sensor accSensor = null;
	SensorEventListener sel = null;
	
	MView mView = null;

	// What's changed ??
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
				
		setContentView(R.layout.activity_main);

		mView = (MView)findViewById(R.id.mView1);
		
		sm = (SensorManager) getSystemService(SENSOR_SERVICE);
		accSensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sel = new SensorEventListener() {

			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {
			}

			@Override
			public void onSensorChanged(SensorEvent event)
			{
				mView.add(event.values[0], event.values[1], event.values[2], event.timestamp);
			}

		};
	}
	
	@Override
	public void onPause() {
		super.onPause();		
		sm.unregisterListener(sel);
	}
	
	@Override
	public void onResume() {
		super.onResume();		
		sm.registerListener(sel, accSensor, SensorManager.SENSOR_DELAY_GAME);
	}
}