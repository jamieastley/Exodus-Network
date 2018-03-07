package com.jastley.warmindfordestiny2.LFG;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.FirebaseDatabase;
import com.jastley.warmindfordestiny2.R;

import java.util.Calendar;

public class NewLFGPostActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText activityName;
    private EditText activityCheckpoint;
    private Button submitBtn;
    private String key;
    private String lightLevel;
    private String membershipType;
    private String displayName;
    private String classType;
    private String dateTime;
    private boolean hasMic;
    private static final FirebaseDatabase DATABASE = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_lfgpost);

        activityName = findViewById(R.id.activity_name_input);
        activityCheckpoint = findViewById(R.id.activity_checkpoint_input);
        submitBtn = findViewById(R.id.submit_lfg_post_button);

//        TODO: remove hardcoded variables and replace with user account details
        lightLevel = "280";
        membershipType = "2";
        displayName = "wheels00769";
        classType = "1";
        hasMic = true;
        dateTime = Calendar.getInstance().getTime().toString();

        submitBtn.setOnClickListener(this);

        key = DATABASE.getReference().push().getKey();
    }


    @Override
    public void onClick(View view) {
        LFGPost newPost = new LFGPost(activityName.getText().toString(),
                                    activityCheckpoint.getText().toString(),
                                    lightLevel, membershipType, displayName, classType, dateTime, hasMic);

        DATABASE.getReference().child("lfg").child(displayName).setValue(newPost);
        finish();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
