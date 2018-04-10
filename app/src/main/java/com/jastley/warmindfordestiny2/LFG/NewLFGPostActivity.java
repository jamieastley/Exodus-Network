package com.jastley.warmindfordestiny2.LFG;

import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jastley.warmindfordestiny2.R;
import com.jastley.warmindfordestiny2.database.DatabaseHelper;
import com.jastley.warmindfordestiny2.database.OldDatabaseModel;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;


public class NewLFGPostActivity extends AppCompatActivity {

    @BindView(R.id.activity_name_input) EditText activityName;
    @BindView(R.id.activity_checkpoint_input) EditText activityCheckpoint;
    @BindView(R.id.radio_character_selection) RadioGroup characterRadioGroup;
    @BindView(R.id.activity_name_spinner) Spinner activityNameSpinner;
    private String key;
    private String lightLevel;
    private String membershipType;
    private String displayName;
    private Long dateTime;
    private boolean hasMic;
    private static final FirebaseDatabase DATABASE = FirebaseDatabase.getInstance();
    private DatabaseHelper db;
    private boolean onCreateFlag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_lfgpost);
        ButterKnife.bind(this);

        db = new DatabaseHelper(this);


        Toolbar myToolbar = findViewById(R.id.lfg_toolbar);
        myToolbar.setTitle(R.string.submitPost);
        setSupportActionBar(myToolbar);

/**       TODO: Store activityNames/checkpoints on Firebase and cache on device, save last update timestamp and
*         TODO sync from Firebase if expired
*/
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.activities, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        activityNameSpinner.setAdapter(adapter);
        activityNameSpinner.setSelection(0, false);

//        activityName = findViewById(R.id.activity_name_input);
//        activityCheckpoint = findViewById(R.id.activity_checkpoint_input);

//        ActionBar actionBar = getSupportActionBar();  //to support lower version too
//        actionBar.setDisplayShowCustomEnabled(true);
//        View customView=getLayoutInflater().inflate(R.layout.yourlayout, null);
//        actionBar.setCustomView(customView);

//        final AccountDAO mAccountDAO = AppDatabase.getAppDatabase(this).getAccountDAO();

        final JsonParser parser = new JsonParser();

        Drawable coloredPlaceholder = getApplicationContext().getResources().getDrawable(R.drawable.ic_account_circle_black_24dp);
        coloredPlaceholder.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent), PorterDuff.Mode.SRC_IN);

        for(int i = 0; i < 3; i++){

            OldDatabaseModel characters = db.getAccountData("account", "character"+i);
            String characterValue = characters.getValue();

            JsonObject json = (JsonObject) parser.parse(characterValue);

            String characterId = json.get("characterId").getAsString();
            int characterType = json.get("classType").getAsInt();
            String emblem = json.get("emblemPath").getAsString();

            final RadioButton btn = new RadioButton(this);

            //get class name
            switch(characterType) {
                case 0: //Titan
                    btn.setText(R.string.titan);
                    break;
                case 1: //Hunter
                    btn.setText(R.string.hunter);
                    break;
                case 2: //Warlock
                    btn.setText(R.string.warlock);
                break;
            }

            btn.setId(i);
            btn.setGravity(Gravity.CENTER);
            btn.setButtonDrawable(new StateListDrawable());
            btn.setCompoundDrawables(null, coloredPlaceholder, null, null);
            btn.setLayoutParams(new RadioGroup.LayoutParams(
                    RadioGroup.LayoutParams.WRAP_CONTENT, //width
                    RadioGroup.LayoutParams.WRAP_CONTENT, //height
                    1.0f
            ));

            if(i == 0){ //always set first button as checked to prevent null selection
                btn.setChecked(true);
            }
            else {
                btn.setChecked(false);
                btn.setAlpha(0.3f);
            }

            String baseURL = "https://www.bungie.net";
            Picasso.with(this)
                    .load(baseURL+emblem)
                    .placeholder(coloredPlaceholder)
                    .transform(new CropCircleTransformation())
                    .into(new Target() {
                            //Load image into target so the bitmap can be converted to a drawable
                              @Override
                              public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                  Drawable draw = new BitmapDrawable(getResources(), bitmap);

                                  //setBounds required for setCompoundDrawables
                                  draw.setBounds(0,0, 150, 150);
                                  btn.setCompoundDrawables(null, draw, null, null);
                              }

                              @Override
                              public void onBitmapFailed(Drawable errorDrawable) {
                                //TODO: set emblemIcon error placeholders if all else fails
                              }

                              @Override
                              public void onPrepareLoad(Drawable placeHolderDrawable) {

                              }
                          });
            characterRadioGroup.addView(btn);
        }

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

                //get button count for alpha modification
                int childCount = characterRadioGroup.getChildCount();

                switch (index) {
                    case 0: // first button
                        characterRadioGroup.getChildAt(0).setAlpha(1f);
                        if(childCount > 1){
                            characterRadioGroup.getChildAt(1).setAlpha(0.3f);
                            if(childCount > 2){
                                characterRadioGroup.getChildAt(2).setAlpha(0.3f);
                            }
                        }
                        break;
                    case 1: // secondbutton
                        characterRadioGroup.getChildAt(0).setAlpha(0.3f);
                        if(childCount > 1){
                            characterRadioGroup.getChildAt(1).setAlpha(1f);
                            if(childCount > 2){
                                characterRadioGroup.getChildAt(2).setAlpha(0.3f);
                            }
                        }
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


    @Override
    protected void onStart() {
        super.onStart();

    }

    @OnItemSelected(R.id.activity_name_spinner)
    public void spinnerItemSelected(Spinner spinner, int position) {

        if(onCreateFlag){
            onCreateFlag = false;
        }
        else{
            spinner.getItemAtPosition(position);
            //TODO: populate and show checkpoint spinner here
        }

    }

}
