package ro.pub.cs.systems.eim.practicaltest01var05;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ProcessingThread extends Thread{
    private String[] buttons;

    private Context context;

    private boolean isRunning = true;

    public ProcessingThread(Context context, String sablon) {
        this.context = context;
        this.buttons = sablon.split(",");
    }

    public void stopThread() {
        isRunning = false;
    }

    @Override
    public void run() {
        while (isRunning) {
            for (String button : buttons) {
                sendMessage(button);
                sleep();
            }
        }
    }

    private void sendMessage(String button) {
        Intent intent = new Intent();
        intent.setAction("ro.pub.cs.systems.eim.practicaltest01var05.actionType");
        intent.putExtra("button", button);
        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
