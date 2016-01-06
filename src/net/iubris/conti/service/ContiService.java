package net.iubris.conti.service;

import java.util.List;

import net.iubris.conti.R;
import net.iubris.conti.model.ContentCredit;
import net.iubris.conti.model.ContentExpense;
import net.iubris.conti.model.ContentList;
import net.iubris.conti.ui.MainActivity;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class ContiService extends Service {
	
	private static final String TAG = "ContiService";
	
	private static final String notificationTitle = "Conti";
//    private static final String notificationText = "sending data...";
    private static final int notificationId = 20160106;
    
    private static boolean started = false;
    
    @Override
    public void onCreate() {
    	super.onCreate();
    }
    
    @Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		if (!started) {
			Log.d(TAG,"start notification");
			Notification notification = buildNotification();
			startForeground(notificationId, notification);
			started = true;
		} else {
			Log.d(TAG,"already started *");
		}
		
		@SuppressWarnings("unchecked")
		List<ContentExpense> contentExpenses = ((ContentList<ContentExpense>) intent.getSerializableExtra(ContentList.EXTRA_CONTENT_EXPENSES)).getAll();
		@SuppressWarnings("unchecked")
		List<ContentCredit> contentCredits = ((ContentList<ContentCredit>) intent.getSerializableExtra(ContentList.EXTRA_CONTENT_CREDITS)).getAll();
		
		try {
			Log.d(TAG, "contentExpenses: "+contentExpenses.size());
			Log.d(TAG, "contentCredits: "+contentCredits.size());
		} catch(NullPointerException e) {
			Log.d(TAG,"null pointer!");
		}
		
		sendData();
		
		return START_STICKY;
	}
    
    private void sendData() {
    	
    }
    
    @Override
	public void onDestroy() {
		started = false;
		Log.d(TAG,"destroyed");
		super.onDestroy();
	}
	

	private Notification buildNotification() {
		Intent mainActivityIntent = new Intent(this, MainActivity.class);
		PendingIntent mainActivityPendingIntent = PendingIntent.getActivity(getBaseContext(), 0, mainActivityIntent, Intent.FLAG_ACTIVITY_NEW_TASK);

		Notification notification = new NotificationCompat.Builder(this)
				.setContentTitle(notificationTitle)
				.setContentText(getResources().getString(R.string.sending_data))
				.setSmallIcon(R.mipmap.ic_launcher)
				.setContentIntent(mainActivityPendingIntent).build();
		notification.flags = notification.flags | Notification.FLAG_FOREGROUND_SERVICE;

		return notification;
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
