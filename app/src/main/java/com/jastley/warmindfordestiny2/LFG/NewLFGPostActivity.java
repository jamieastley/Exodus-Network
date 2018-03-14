package com.jastley.warmindfordestiny2.LFG;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
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
    private Long dateTime;
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
        lightLevel = "318";
        membershipType = "4";
        displayName = "tanky_tim12";
        classType = "2";
        hasMic = true;
        dateTime = System.currentTimeMillis();

        submitBtn.setOnClickListener(this);

        key = DATABASE.getReference().push().getKey();
    }


    @Override
    public void onClick(final View view) {
        LFGPost newPost = new LFGPost(activityName.getText().toString(),
                                    activityCheckpoint.getText().toString(),
                                    lightLevel, membershipType, displayName, classType, dateTime, hasMic);

        DATABASE.getReference().child("lfg").child(displayName).setValue(newPost).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Post submitted!", Toast.LENGTH_SHORT).show();
//                    Snackbar.make(view, "Post submitted!", Snackbar.LENGTH_SHORT)
//                        .show();
//                    Intent returnIntent = new Intent();
////                    returnIntent.putExtra("result", result);
//                    setResult(RESULT_OK);
                    finish();
                }
            }

//            TODO: Network/submit error


        });
//        finish();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
