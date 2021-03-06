package com.example.sqa_pt.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {

    // The TextView used to display the message inside the Activity.
    private TextView mTextView;

    // The EditText where the user types the message.
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the listeners for the buttons.
        findViewById(R.id.changeTextBt).setOnClickListener(this);
        findViewById(R.id.activityChangeTextBtn).setOnClickListener(this);
        findViewById(R.id.btn_intent).setOnClickListener(this);
        findViewById(R.id.launchlistview).setOnClickListener(this);
        findViewById(R.id.btn_webview).setOnClickListener(this);

        mTextView = (TextView) findViewById(R.id.textToBeChanged);
        mEditText = (EditText) findViewById(R.id.editTextUserInput);
    }

    @Override
    public void onClick(View view) {
        // Get the text from the EditText view.
        final String text = mEditText.getText().toString();

        switch (view.getId()) {
            case R.id.changeTextBt:
                // First button's interaction: set a text in a text view.
                mTextView.setText(text);
                break;
            case R.id.activityChangeTextBtn:
                // Second button's interaction: start an activity and send a message to it.
                //Intent intent = ShowTextActivity.newStartIntent(this, text);
                //startActivity(intent);
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.example.sqa_pt.testapplication");
                startActivity(intent);
                break;
            case R.id.btn_intent:
                //Uri testdata=Uri.parse("tel:" + "Your Phone_number");

                intent = new Intent(this, ShowTextActivity.class);
                intent.putExtra("URL", "http://www.vogella.com");
                startActivity(intent);
                break;
            case R.id.launchlistview:
                intent = new Intent(this,demoliked.class);
                startActivity(intent);
                break;
            case R.id.btn_webview:
                intent = new Intent(this,webActivity.class);
                startActivity(intent);
                break;
        }
    }

}
