package ro.pub.cs.systems.eim.practicaltest01var05;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class PracticalTest01Var05Service extends Service {
    public PracticalTest01Var05Service() {
    }

    private ProcessingThread processingThread = null;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("colocviu", "Service service...");
        String sablon = intent.getStringExtra("sablon");
        processingThread = new ProcessingThread(this, sablon);
        processingThread.start();
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        processingThread.stopThread();
    }
}