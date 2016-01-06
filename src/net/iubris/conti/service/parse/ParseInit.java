package net.iubris.conti.service.parse;

import android.content.Context;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

public enum ParseInit {

	INSTANCE;
	
	private boolean setup;
	public void init(Context context) {
		if (!setup) {
			// Enable Local Datastore.
		    Parse.enableLocalDatastore(context);
	
		    // Add your initialization code here
		    Parse.initialize(context);
	
		    ParseUser.enableAutomaticUser();
		    ParseACL defaultACL = new ParseACL();
		    // Optionally enable public read access.
		    // defaultACL.setPublicReadAccess(true);
		    ParseACL.setDefaultACL(defaultACL, true);
		    
		    setup = true;
		}
	}
}
