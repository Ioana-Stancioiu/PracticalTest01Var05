package ro.pub.cs.systems.eim.practicaltest01var05;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest01Var05MainActivity extends AppCompatActivity {

    private Button topLeftButton, topRightButton, bottomLeftButton, bottomRightButton, centerButton;
    private TextView textView;
    private Button navigateToSecondaryActivityButton;

    private ButtonClickListener buttonClickListener = new ButtonClickListener();

    private int totalClicks = 0;

    private boolean isServiceStarted = false;

    private MessageBroadCastReceiver messageBroadCastReceiver = new MessageBroadCastReceiver();

    private class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            String textViewContent = textView.getText().toString();
            if (!textViewContent.isEmpty()) {
                textViewContent += ",";
            }
            switch (view.getId()) {
                case R.id.top_left_button:
                    totalClicks++;
                    textView.setText(textViewContent + "Top Left");
                    break;
                case R.id.top_right_button:
                    totalClicks++;
                    textView.setText(textViewContent + "Top Right");
                    break;
                case R.id.bottom_left_button:
                    totalClicks++;
                    textView.setText(textViewContent + "Bottom Left");
                    break;
                case R.id.bottom_right_button:
                    totalClicks++;
                    textView.setText(textViewContent + "Bottom Right");
                    break;
                case R.id.center_button:
                    totalClicks++;
                    textView.setText(textViewContent + "Center");
                    break;
                case R.id.navigate_to_secondary_activity_button:
                    Intent intent = new Intent(getApplicationContext(), PracticalTest01Var05SecondaryActivity.class);
                    intent.putExtra("sablon", textViewContent);
                    startActivityForResult(intent, 1);
                    break;
                default:
                    break;
            }

            if (totalClicks > Constants.numClickToStart && !isServiceStarted) {
                Log.d("colocviu", "onClick: Starting service...");
                Intent intent = new Intent(getApplicationContext(), PracticalTest01Var05Service.class);
                intent.putExtra("sablon", textViewContent);
                getApplicationContext().startService(intent);
                isServiceStarted = true;
            }

        }
    }

    private static class MessageBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String button_name = intent.getStringExtra("button");
            Log.d("ButtonPressed", "Received broadcast for button " + button_name);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("ro.pub.cs.systems.eim.practicaltest01var05.actionType");
        registerReceiver(messageBroadCastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(messageBroadCastReceiver);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("total_clicks", totalClicks);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        totalClicks = 0;
        textView.setText("");

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Result is OK", Toast.LENGTH_SHORT).show();
            }

            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Result is CANCELED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey("total_clicks")) {
            totalClicks = savedInstanceState.getInt("total_clicks");
            Toast.makeText(this, "Total clicks " + totalClicks, Toast.LENGTH_SHORT).show();
        }
        Log.d("colocviu", "onRestoreInstanceState: Total clicks: " + totalClicks);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var05_main);

        topLeftButton = findViewById(R.id.top_left_button);
        topRightButton = findViewById(R.id.top_right_button);
        bottomLeftButton = findViewById(R.id.bottom_left_button);
        bottomRightButton = findViewById(R.id.bottom_right_button);
        centerButton = findViewById(R.id.center_button);
        navigateToSecondaryActivityButton = findViewById(R.id.navigate_to_secondary_activity_button);
        textView = findViewById(R.id.text_view);

        topLeftButton.setOnClickListener(buttonClickListener);
        topRightButton.setOnClickListener(buttonClickListener);
        bottomLeftButton.setOnClickListener(buttonClickListener);
        bottomRightButton.setOnClickListener(buttonClickListener);
        centerButton.setOnClickListener(buttonClickListener);
        navigateToSecondaryActivityButton.setOnClickListener(buttonClickListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(getApplicationContext(), PracticalTest01Var05Service.class));
        isServiceStarted = false;
    }
}