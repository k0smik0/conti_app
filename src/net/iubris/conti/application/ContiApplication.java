package net.iubris.conti.application;

import java.util.Random;

import net.iubris.conti.service.parse.ParseInit;
import android.app.Application;
import android.util.Log;

public class ContiApplication extends Application {

	private final Random random = new Random();
	
	public void onCreate() {
//		RoboGuice.setUseAnnotationDatabases(false);
		Log.d("ContiApplication","version: "+random.nextInt());
		
		ParseInit.INSTANCE.init(this);
	}
}
