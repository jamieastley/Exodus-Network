package com.jastley.warmindfordestiny2.LFG;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jastley.warmindfordestiny2.LFG.models.LFGPost;
import com.jastley.warmindfordestiny2.R;
import com.jastley.warmindfordestiny2.database.DatabaseHelper;
import com.jastley.warmindfordestiny2.database.OldDatabaseModel;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;


public class NewLFGPostActivity extends AppCompatActivity {

    @BindView(R.id.radio_character_selection) RadioGroup characterRadioGroup;
    @BindView(R.id.activity_name_spinner) Spinner activityNameSpinner;
    @BindView(R.id.activity_checkpoint_spinner) Spinner activityCheckpointSpinner;
    @BindView(R.id.lfg_description_input) EditText description;
    @BindView(R.id.micCheckBox) CheckBox micCheckBox;
    private String key;
    private String displayName;
    private Long dateTime;
    private boolean hasMic = false;
    private boolean onCreateFlag = true;
    JsonArray characterArray = new JsonArray();

    private static final FirebaseDatabase DATABASE = FirebaseDatabase.getInstance();
    private DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_lfgpost);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        displayName = intent.getStringExtra("displayName");

        if ((savedInstanceState != null)) {

            description.setText((String)savedInstanceState.getSerializable("description"));
        }

        db = new DatabaseHelper(this);


        Toolbar myToolbar = findViewById(R.id.lfg_toolbar);
        myToolbar.setTitle(R.string.submitPost);
        setSupportActionBar(myToolbar);

/**       TODO: Store activityNames/checkpoints on Firebase and cache on device, save last update timestamp and
*         TODO sync from Firebase if expired
*/
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.activities, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> checkpointAdapter = ArrayAdapter.createFromResource(this, R.array.nightfall, android.R.layout.simple_spinner_item);
        checkpointAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        activityNameSpinner.setAdapter(adapter);
        activityNameSpinner.setSelection(0, false);
        activityCheckpointSpinner.setAdapter(checkpointAdapter);


//        activityName = findViewById(R.id.activity_name_input);
//        activityCheckpoint = findViewById(R.id.activity_checkpoint_input);

//        ActionBar actionBar = getSupportActionBar();  //to support lower version too
//        actionBar.setDisplayShowCustomEnabled(true);
//        View customView=getLayoutInflater().inflate(R.layout.yourlayout, null);
//        actionBar.setCustomView(customView);

//        final AccountDAO mAccountDAO = AppDatabase.getAppDatabase(this).getAccountDAO();

        final JsonParser parser = new JsonParser();

        final Drawable coloredPlaceholder = getApplicationContext().getResources().getDrawable(R.drawable.ic_account_circle_black_24dp);
        coloredPlaceholder.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent), PorterDuff.Mode.SRC_IN);

        for(int i = 0; i < 3; i++){

            OldDatabaseModel characters = db.getAccountData("account", "character"+i);
            String characterValue = characters.getValue();

            JsonObject json = (JsonObject) parser.parse(characterValue);
            characterArray.add(json);

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
            btn.setTextColor(getResources().getColor(R.color.colorWhite));
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

            File directory = getDir("playerEmblems", Context.MODE_PRIVATE);
            File path = new File(directory, i + ".jpeg");

            Picasso.with(this)
                    .load(Uri.fromFile(path))
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
                                  btn.setCompoundDrawables(null, coloredPlaceholder, null, null);
                              }
                          });
            characterRadioGroup.addView(btn);
        }

//        TODO: remove hardcoded variables and replace with user account details
//        lightLevel = "278";
//        membershipType = "4";
//        displayName = "Last player";
//        classType = "2";

        characterRadioGroup.setOnCheckedChangeListener((radioGroup, i) -> {

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
        });

//        radioButton.getTag(); //TODO: store characterId here?

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

                item.setEnabled(false);

                int radioButtonID = characterRadioGroup.getCheckedRadioButtonId();
                View radioButton = characterRadioGroup.findViewById(radioButtonID);
                int idx = characterRadioGroup.indexOfChild(radioButton);
                RadioButton selectedCharacter = (RadioButton)  characterRadioGroup.getChildAt(idx);

                //Which object in characterArray to pass along to Firebase
                int index = selectedCharacter.getId();
                JsonObject characterObject = characterArray.get(index).getAsJsonObject();

                if(micCheckBox.isChecked()){
                    hasMic = true;
                }

                String light = characterObject.get("light").getAsString();
                String membershipType = characterObject.get("membershipType").getAsString();
                String membershipId = characterObject.get("membershipId").getAsString();
                String emblemIcon = characterObject.get("emblemPath").getAsString();
                String emblemBackground = characterObject.get("emblemBackgroundPath").getAsString();
                String characterId = characterObject.get("characterId").getAsString();
                String classType = characterObject.get("classType").getAsString();

                //TODO: check for null values before writing to Firebase

                LFGPost newPost = new LFGPost(
                        activityNameSpinner.getSelectedItem().toString(),
                        activityCheckpointSpinner.getSelectedItem().toString(),
                        light,
                        membershipType, displayName, classType, description.getText().toString(), dateTime, hasMic,
                        membershipId, emblemIcon, emblemBackground, characterId);

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

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("description", description.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        description.setText((String)savedInstanceState.getSerializable("description"));
    }

    @OnItemSelected(R.id.activity_name_spinner)
    public void spinnerItemSelected(Spinner spinner, int position) {

        if(onCreateFlag){
            onCreateFlag = false;
        }
        else{
            ArrayAdapter<CharSequence> adapter;

            String result = spinner.getItemAtPosition(position).toString();
            System.out.println("result: "+ result);

            switch (result) {
                case "PvP":
                    adapter = ArrayAdapter.createFromResource(this, R.array.pvp, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    activityCheckpointSpinner.setAdapter(adapter);
                    break;
                case "Nightfall":
                    adapter = ArrayAdapter.createFromResource(this, R.array.nightfall, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    activityCheckpointSpinner.setAdapter(adapter);
                    break;
                case "Raid Lair":
                    adapter = ArrayAdapter.createFromResource(this, R.array.raidLair, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    activityCheckpointSpinner.setAdapter(adapter);
                    break;
                case "Raid":
                    adapter = ArrayAdapter.createFromResource(this, R.array.raid, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    activityCheckpointSpinner.setAdapter(adapter);
                    break;
                case "Raid - Misc":
                    adapter = ArrayAdapter.createFromResource(this, R.array.raidMisc, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    activityCheckpointSpinner.setAdapter(adapter);
                    break;
                case "Public Events":
                    adapter = ArrayAdapter.createFromResource(this, R.array.publicEvents, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    activityCheckpointSpinner.setAdapter(adapter);
                    break;
                case "Strikes":
                    adapter = ArrayAdapter.createFromResource(this, R.array.strikes, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    activityCheckpointSpinner.setAdapter(adapter);
                    break;
                case "Campaign":
                    adapter = ArrayAdapter.createFromResource(this, R.array.empty, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    activityCheckpointSpinner.setAdapter(adapter);
                    break;
                case "Achievement":
                    adapter = ArrayAdapter.createFromResource(this, R.array.achievement, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    activityCheckpointSpinner.setAdapter(adapter);
                    break;
                case "Social":
                    adapter = ArrayAdapter.createFromResource(this, R.array.empty, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    activityCheckpointSpinner.setAdapter(adapter);
                    break;
            }

            //TODO: populate and show checkpoint spinner here
        }


    }

}
