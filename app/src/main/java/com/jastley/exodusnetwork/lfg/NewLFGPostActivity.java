package com.jastley.exodusnetwork.lfg;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

import butterknife.*;

import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.*;
import com.jastley.exodusnetwork.Dialogs.LoadingDialogFragment;
import com.jastley.exodusnetwork.lfg.models.LFGPost;
import com.jastley.exodusnetwork.MainActivity;
import com.jastley.exodusnetwork.R;
import com.jastley.exodusnetwork.Utils.NetworkUtil;
import com.jastley.exodusnetwork.api.models.Response_GetAllCharacters;
import com.jastley.exodusnetwork.database.AccountDAO;
import com.jastley.exodusnetwork.database.AppDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class NewLFGPostActivity extends AppCompatActivity {

    @BindView(R.id.radio_character_selection) RadioGroup characterRadioGroup;
    @BindView(R.id.activity_name_spinner) Spinner activityNameSpinner;
    @BindView(R.id.activity_checkpoint_spinner) Spinner activityCheckpointSpinner;
    @BindView(R.id.lfg_description_input) EditText description;
    @BindView(R.id.micCheckBox) CheckBox micCheckBox;
    @BindView(R.id.description_char_count) TextView charCounter;
    private String displayName;
    private boolean hasMic = false;
    private boolean onCreateFlag = true;
    List<Response_GetAllCharacters.CharacterData> mCharacterList = new ArrayList<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private static final FirebaseDatabase DATABASE = FirebaseDatabase.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_lfgpost);

        ButterKnife.bind(this);

        SharedPreferences savedPrefs = getSharedPreferences("saved_prefs", MODE_PRIVATE);
        String selectedPlatform = savedPrefs.getString("selectedPlatform", "");
        displayName = savedPrefs.getString("displayName"+selectedPlatform, "");

        if ((savedInstanceState != null)) {

            description.setText((String)savedInstanceState.getSerializable("description"));
        }

        Toolbar myToolbar = findViewById(R.id.lfg_toolbar);
        myToolbar.setTitle(R.string.submitPost);
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);

        description.setOnEditorActionListener(new DoneOnEditorActionListener());
        description.addTextChangedListener(new DescriptionCharCounter());

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.activities, R.layout.spinner_list_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> checkpointAdapter = ArrayAdapter.createFromResource(this, R.array.nightfall, R.layout.spinner_list_item);
        checkpointAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        activityNameSpinner.setAdapter(adapter);

        activityNameSpinner.setSelection(0, false);
        activityCheckpointSpinner.setAdapter(checkpointAdapter);

//
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

        getAccountCharacters();
    }

    private void getAccountCharacters(){

        AccountDAO mAccountDAO = AppDatabase.getAppDatabase(this).getAccountDAO();

        Disposable disposable = mAccountDAO.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(characters -> {

                    Drawable coloredPlaceholder = getApplicationContext().getResources().getDrawable(R.drawable.ic_account_circle_black_24dp);
                    coloredPlaceholder.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent), PorterDuff.Mode.SRC_IN);

                    SharedPreferences savedPrefs = getSharedPreferences("saved_prefs", MODE_PRIVATE);
                    String selectedPlatform = savedPrefs.getString("selectedPlatform", "");

                    for (int i = 0; i < characters.size(); i++) {

                        //Deserialize and bind data to model class
                        Gson gson = new GsonBuilder().create();
                        Response_GetAllCharacters.CharacterData character = gson.fromJson(characters.get(i).getValue(), Response_GetAllCharacters.CharacterData.class);

                        //only get characters for the selectedPlatform
                        if (character.getMembershipType().equals(selectedPlatform)) {
                            //required for post submit OnClick
                            mCharacterList.add(character);
                        }
                    }

                    for (int j = 0; j < mCharacterList.size(); j++) {

                        String classType = mCharacterList.get(j).getClassType();

                        RadioButton radioButton = new RadioButton(this);

                        switch(classType){

                            case "0": //Titan
                                radioButton.setText(R.string.titan);
                                break;
                            case "1": //Hunter
                                radioButton.setText(R.string.hunter);
                                break;
                            case "2": // Warlock
                                radioButton.setText(R.string.warlock);
                                break;
                        }

                        radioButton.setId(j);
                        radioButton.setGravity(Gravity.CENTER);

                        //disables default radiobutton
                        radioButton.setBackgroundDrawable(null);

                        radioButton.setButtonDrawable(new StateListDrawable());
                        radioButton.setTextColor(getResources().getColor(R.color.colorWhite));
                        radioButton.setLayoutParams(new RadioGroup.LayoutParams(
                                RadioGroup.LayoutParams.WRAP_CONTENT, //width
                                RadioGroup.LayoutParams.WRAP_CONTENT, //height
                                1.0f
                        ));

                        if(j == 0){ //always ensure an option is preselected onCreate
                            radioButton.setChecked(true);
                        }
                        else{
                            radioButton.setChecked(false);
                            radioButton.setAlpha(0.3f);
                        }

                        //set emblem icons
                        File dir = getDir("emblems", MODE_PRIVATE);
                        File path = new File(dir, j+".jpeg");
                        if(path.exists()){

                            RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(getResources(), path.getAbsolutePath());
                            roundedDrawable.setCircular(true);

                            //bounds necessary for compoundDrawables
                            roundedDrawable.setBounds(0,0, 150, 150);
                            radioButton.setCompoundDrawables(null, roundedDrawable, null, null);
                        }

                        characterRadioGroup.addView(radioButton);
                    }
                });
        compositeDisposable.add(disposable);
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

                String descriptionText = description.getText().toString().trim();

                if(!NetworkUtil.isOnline(getApplicationContext())){
                    Snackbar.make(findViewById(R.id.new_LFG_coordinator_layout), "No network detected!", Snackbar.LENGTH_LONG)
                            .show();
                }
                else{

                    if(description.getText().toString().matches("")) {
                        descriptionText = "No description provided.";
                    }
                        showLoadingDialog("Submitting...", "Please wait");
                        item.setEnabled(false);

                        int radioButtonID = characterRadioGroup.getCheckedRadioButtonId();
                        View radioButton = characterRadioGroup.findViewById(radioButtonID);
                        int idx = characterRadioGroup.indexOfChild(radioButton);
                        RadioButton selectedCharacter = (RadioButton)  characterRadioGroup.getChildAt(idx);

                        //Which object in characterArray to pass along to Firebase
                        int index = selectedCharacter.getId();
                        Response_GetAllCharacters.CharacterData selectedCharacterData = new Response_GetAllCharacters.CharacterData();

                        selectedCharacterData = mCharacterList.get(index);
    //                    JsonObject characterObject = characterArray.get(index).getAsJsonObject();

                        if(micCheckBox.isChecked()){
                            hasMic = true;
                        }

                        String light = selectedCharacterData.getLight();
                        String membershipType = selectedCharacterData.getMembershipType();
                        String membershipId = selectedCharacterData.getMembershipId();
                        String emblemIcon = selectedCharacterData.getEmblemPath();
                        String emblemBackground = selectedCharacterData.getEmblemBackgroundPath();
                        String characterId = selectedCharacterData.getCharacterId();
                        String classType = selectedCharacterData.getClassType();

                        Long dateTime = System.currentTimeMillis();

                        LFGPost newPost = new LFGPost(
                                activityNameSpinner.getSelectedItem().toString(),
                                activityCheckpointSpinner.getSelectedItem().toString(),
                                light,
                                membershipType, displayName, classType, descriptionText, dateTime, hasMic,
                                membershipId, emblemIcon, emblemBackground, characterId);

                                DATABASE.getReference().child("lfg").child(displayName).setValue(newPost).addOnCompleteListener(task -> {
                                    if(task.isSuccessful()){

                                        dismissLoadingFragment();

                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        intent.putExtra("lfgPost", true);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }
                                    else {
                                        dismissLoadingFragment();
                                        Snackbar.make(findViewById(R.id.new_LFG_coordinator_layout), "Couldn't submit post!", Snackbar.LENGTH_LONG)
                                                .show();
                                    }

                    });
                break;
}
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
    protected void onResume() {
        super.onResume();
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
                    adapter = ArrayAdapter.createFromResource(this, R.array.pvp, R.layout.spinner_list_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    activityCheckpointSpinner.setAdapter(adapter);
                    break;
                case "Nightfall":
                    adapter = ArrayAdapter.createFromResource(this, R.array.nightfall, R.layout.spinner_list_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    activityCheckpointSpinner.setAdapter(adapter);
                    break;
                case "Raid Lair":
                    adapter = ArrayAdapter.createFromResource(this, R.array.raidLair, R.layout.spinner_list_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    activityCheckpointSpinner.setAdapter(adapter);
                    break;
                case "Raid":
                    adapter = ArrayAdapter.createFromResource(this, R.array.raid, R.layout.spinner_list_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    activityCheckpointSpinner.setAdapter(adapter);
                    break;
                case "Raid - Misc":
                    adapter = ArrayAdapter.createFromResource(this, R.array.raidMisc, R.layout.spinner_list_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    activityCheckpointSpinner.setAdapter(adapter);
                    break;
                case "Public Events":
                    adapter = ArrayAdapter.createFromResource(this, R.array.publicEvents, R.layout.spinner_list_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    activityCheckpointSpinner.setAdapter(adapter);
                    break;
                case "Strikes":
                    adapter = ArrayAdapter.createFromResource(this, R.array.strikes, R.layout.spinner_list_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    activityCheckpointSpinner.setAdapter(adapter);
                    break;
                case "Campaign":
                    adapter = ArrayAdapter.createFromResource(this, R.array.empty, R.layout.spinner_list_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    activityCheckpointSpinner.setAdapter(adapter);
                    break;
                case "Achievement":
                    adapter = ArrayAdapter.createFromResource(this, R.array.achievement, R.layout.spinner_list_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    activityCheckpointSpinner.setAdapter(adapter);
                    break;
                case "Social":
                    adapter = ArrayAdapter.createFromResource(this, R.array.empty, R.layout.spinner_list_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    activityCheckpointSpinner.setAdapter(adapter);
                    break;
            }

            //TODO: populate and show checkpoint spinner here
        }
    }

    private void showLoadingDialog(String title, String message) {
        LoadingDialogFragment fragment = LoadingDialogFragment.newInstance(title, message);
        fragment.setCancelable(false);
        fragment.show(this.getFragmentManager(), "loadingDialog");
    }

    private void dismissLoadingFragment() {
        DialogFragment loadingFragment = (DialogFragment)getFragmentManager().findFragmentByTag("loadingDialog");
        if (loadingFragment != null){
            loadingFragment.dismiss();
        }
    }

    private class DoneOnEditorActionListener implements TextView.OnEditorActionListener {

        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if(i == EditorInfo.IME_ACTION_DONE) {
                InputMethodManager imm = (InputMethodManager)textView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
                return true;
            }
            return false;
        }
    }

    private class DescriptionCharCounter implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            String counterText = String.valueOf(charSequence.length()) + "/50";
            charCounter.setText(counterText);
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
}
