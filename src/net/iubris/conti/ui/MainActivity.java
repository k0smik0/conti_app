package net.iubris.conti.ui;

import com.parse.ParseAnalytics;

import net.iubris.conti.R;
import butterknife.ButterKnife;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

public class MainActivity extends /*Robo*/ActionBarActivity {

	private FormFragment formFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
		
		ParseAnalytics.trackAppOpenedInBackground(getIntent());
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		FragmentManager supportFragmentManager = getSupportFragmentManager();
		if (formFragment==null) {
			formFragment = (FormFragment) supportFragmentManager.findFragmentByTag(FormFragment.TAG);
//			Log.d("MainActivity","FormFragment: "+formFragment);
		}
		if (formFragment==null) {
			formFragment = new FormFragment();
//			Log.d("MainActivity","FormFragment: "+formFragment);
		}
		supportFragmentManager.beginTransaction().replace(R.id.container, formFragment).commit();
		unlockScreen();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
		return super.onOptionsItemSelected(item);
	}
	
	private void unlockScreen() {
        Window window = this.getWindow();
        window.addFlags(LayoutParams.FLAG_DISMISS_KEYGUARD);
        window.addFlags(LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        window.addFlags(LayoutParams.FLAG_TURN_SCREEN_ON);
	}
}
