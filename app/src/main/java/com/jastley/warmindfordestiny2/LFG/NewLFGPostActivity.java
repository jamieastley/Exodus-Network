package com.jastley.warmindfordestiny2.LFG;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jastley.warmindfordestiny2.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NewLFGPostActivity extends AppCompatActivity {

    @BindView(R.id.activity_name_input) EditText activityName;
//    private EditText activityName;
//    private EditText activityCheckpoint;
    @BindView(R.id.activity_checkpoint_input) EditText activityCheckpoint;
    private String key;
    private String lightLevel;
    private String membershipType;
//    @BindView(R.id.description_text_input) EditText description;
    private String displayName;
//    private String classType;
    private Long dateTime;
    private RadioGroup characterRadioGroup;
    private boolean hasMic;
    private static final FirebaseDatabase DATABASE = FirebaseDatabase.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_lfgpost);
        ButterKnife.bind(this);

        Toolbar myToolbar = findViewById(R.id.lfg_toolbar);
        myToolbar.setTitle(R.string.submitPost);
        setSupportActionBar(myToolbar);

/**       TODO: Store activityNames/checkpoints on Firebase and cache on device, save last update timestamp and
*         TODO sync from Firebase if expired
*/

//        activityName = findViewById(R.id.activity_name_input);
//        activityCheckpoint = findViewById(R.id.activity_checkpoint_input);
        characterRadioGroup = findViewById(R.id.radio_character_selection);

//        ActionBar actionBar = getSupportActionBar();  //to support lower version too
//        actionBar.setDisplayShowCustomEnabled(true);
//        View customView=getLayoutInflater().inflate(R.layout.yourlayout, null);
//        actionBar.setCustomView(customView);

//        TODO: remove hardcoded variables and replace with user account details
        lightLevel = "278";
        membershipType = "4";
        displayName = "Last player";
//        classType = "2";

        characterRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                View radioButton = characterRadioGroup.findViewById(i);
                int index = characterRadioGroup.indexOfChild(radioButton);

//                int count = characterRadioGroup.getChildCount();
                // Add logic here

                switch (index) {
                    case 0: // first button
                        characterRadioGroup.getChildAt(0).setAlpha(1f);
                        characterRadioGroup.getChildAt(1).setAlpha(0.3f);
                        characterRadioGroup.getChildAt(2).setAlpha(0.3f);
                        break;
                    case 1: // secondbutton
                        characterRadioGroup.getChildAt(0).setAlpha(0.3f);
                        characterRadioGroup.getChildAt(1).setAlpha(1f);
                        characterRadioGroup.getChildAt(2).setAlpha(0.3f);
                        break;
                    case 2:
                        characterRadioGroup.getChildAt(0).setAlpha(0.3f);
                        characterRadioGroup.getChildAt(1).setAlpha(0.3f);
                        characterRadioGroup.getChildAt(2).setAlpha(1f);
                        break;
                }
            }
        });

//        radioButton.getTag(); //TODO: store characterId here?

        hasMic = true;
        dateTime = System.currentTimeMillis();


//        submitBtn.setOnClickListener(this);

        key = DATABASE.getReference().push().getKey();
    }


//    @Override
//    public void onClick(final View view) {
//
//
//
//
//        LFGPost newPost = new LFGPost(activityName.getText().toString(),
//                                    activityCheckpoint.getText().toString(),
//                                    lightLevel, membershipType, displayName, selectedCharacter.getText().toString(), dateTime, hasMic);
//
//        DATABASE.getReference().child("lfg").child(displayName)
//                .setValue(newPost)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//                    if(task.isSuccessful()){
//                        Toast.makeText(getApplicationContext(), "Post submitted!", Toast.LENGTH_SHORT).show();
//    //                    Snackbar.make(view, "Post submitted!", Snackbar.LENGTH_SHORT)
//    //                        .show();
//    //                    Intent returnIntent = new Intent();
//    ////                    returnIntent.putExtra("result", result);
//    //                    setResult(RESULT_OK);
//                        finish();
//                    }
//                }
////            TODO: Network/submit error
//        });
////        finish();
//    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar_new_lfg, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.submit_lfg_post_button:

                int radioButtonID = characterRadioGroup.getCheckedRadioButtonId();
                View radioButton = characterRadioGroup.findViewById(radioButtonID);
                int idx = characterRadioGroup.indexOfChild(radioButton);
                RadioButton selectedCharacter = (RadioButton)  characterRadioGroup.getChildAt(idx);

                LFGPost newPost = new LFGPost(activityName.getText().toString(),
                        activityCheckpoint.getText().toString(),
                        lightLevel, membershipType, displayName, selectedCharacter.getText().toString(), dateTime, hasMic);

                DATABASE.getReference().child("lfg").child(displayName).setValue(newPost).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Post submitted!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
//            TODO: Network/submit error
                });


        }
        return super.onOptionsItemSelected(item);
    }
}
