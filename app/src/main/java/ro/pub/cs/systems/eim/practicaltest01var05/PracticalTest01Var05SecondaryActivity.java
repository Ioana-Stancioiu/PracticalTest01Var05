package ro.pub.cs.systems.eim.practicaltest01var05;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class PracticalTest01Var05SecondaryActivity extends AppCompatActivity {

    private Button verifyButton, cancelButton;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var05_secondary);

        verifyButton = findViewById(R.id.verify_button);
        cancelButton = findViewById(R.id.cancel_button);
        textView = findViewById(R.id.text_view);

        Intent intent = getIntent();
        if (intent != null && intent.getExtras().containsKey("sablon")) {
            String sablon = intent.getStringExtra("sablon");
            textView.setText(sablon);
        }

        verifyButton.setOnClickListener(view -> {
            setResult(RESULT_OK);
            finish();
        });

        cancelButton.setOnClickListener(view -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }
}