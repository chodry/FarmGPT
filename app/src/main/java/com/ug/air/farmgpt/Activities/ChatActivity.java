package com.ug.air.farmgpt.Activities;

import static com.ug.air.farmgpt.Activities.SplashActivity.SHARED_PREFS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.ug.air.farmgpt.Adapters.ChatAdapter;
import com.ug.air.farmgpt.Adapters.SpinnerAdapter;
import com.ug.air.farmgpt.Models.Chat;
import com.ug.air.farmgpt.Models.Gpt;
import com.ug.air.farmgpt.Models.GptResponse;
import com.ug.air.farmgpt.Models.Question;
import com.ug.air.farmgpt.R;
import com.ug.air.farmgpt.Utils.Functions;
import com.ug.air.farmgpt.Utils.Item;
import com.ug.air.farmgpt.Utils.SystemProgressDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {

    ImageView btnBack, btnSend, btnChange;
    Button btnContinue;
    RadioGroup radioGroup;
    RecyclerView responseRecyclerView;
    LinearLayout instructLinearLayout;
    EditText editText;
    String question, language, category, subCategory, topic, subTopic, location, answer;
    ChatAdapter chatAdapter;
    List<Item> itemList;
    LinearLayoutManager layoutManager;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    SystemProgressDialog progressDialog;
    public static final String LANGUAGE = "language";
    public static final String CATEGORY = "category";
    public static final String SUB_CATEGORY = "sub_category";
    public static final String OTHER = "other";
    ArrayAdapter<CharSequence> adapter;
    List<String> checkBoxList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the status bar background color to white
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.setStatusBarColor(ContextCompat.getColor(this, android.R.color.white));
        }

        // Set the status bar text and icons color to the primary color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        setContentView(R.layout.activity_chat);

        sharedPreferences = getApplicationContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        progressDialog = new SystemProgressDialog(ChatActivity.this);

        btnBack = findViewById(R.id.back);
        responseRecyclerView = findViewById(R.id.recyclerView);
        instructLinearLayout = findViewById(R.id.instruct);
        btnSend = findViewById(R.id.send);
        btnChange = findViewById(R.id.change);
        editText = findViewById(R.id.edit_question);

        btnBack.setOnClickListener(view -> {
            startActivity(new Intent(ChatActivity.this, HomeActivity.class));
        });
        
        btnSend.setOnClickListener(view -> {
            question = editText.getText().toString().trim();
            
            if (question.isEmpty()) {
                Functions.showToast(this, "Please provide a question");
            }
            else {
                selectCategory();
            }
            
        });

        btnChange.setOnClickListener(view -> {
            showDialog();
        });

        layoutManager = new LinearLayoutManager(this);
        responseRecyclerView.setLayoutManager(layoutManager);
        responseRecyclerView.setHasFixedSize(true);
        responseRecyclerView.setItemAnimator(new DefaultItemAnimator());

        itemList = new ArrayList<>();
        chatAdapter = new ChatAdapter(this, itemList);
        responseRecyclerView.setAdapter(chatAdapter);

        language = sharedPreferences.getString(LANGUAGE, "");
        if (language.isEmpty()){
            showDialog();
        }
    }

    private void showDialog() {
        Dialog dialog = new Dialog(ChatActivity.this);
        dialog.setContentView(R.layout.language);
        dialog.setCancelable(false);

        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        btnContinue = dialog.findViewById(R.id.btnContinue);
        radioGroup = dialog.findViewById(R.id.radioGroup);

        set_radio_button();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedRadioButton = dialog.findViewById(checkedId);
                language = selectedRadioButton.getText().toString();
                btnContinue.setEnabled(true);
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString(LANGUAGE, language);
                editor.apply();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void set_radio_button() {
        if (!language.isEmpty()){
            btnContinue.setEnabled(true);
            for (int i = 0; i < radioGroup.getChildCount(); i++){
                View view = radioGroup.getChildAt(i);
                if (view instanceof RadioButton){
                    RadioButton radioButton = (RadioButton) view;
                    if (radioButton.getText().toString().equals(language)){
                        radioButton.setChecked(true);
                        break;
                    }
                }
            }
        }
    }

    private void selectCategory(){
        Dialog dialog_x = new Dialog(ChatActivity.this);
        dialog_x.setContentView(R.layout.category);
        dialog_x.setCancelable(false);

        Window window = dialog_x.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        RadioGroup radioGroup1 = dialog_x.findViewById(R.id.category);
        Button btnCancel = dialog_x.findViewById(R.id.cancel);
        Button btnNext = dialog_x.findViewById(R.id.next);
        LinearLayout linearLayout2 = dialog_x.findViewById(R.id.linear_spinner);
        LinearLayout linearLayout3 = dialog_x.findViewById(R.id.selector1);
        TextView textView = dialog_x.findViewById(R.id.heading2);
        TextView textView_2 = dialog_x.findViewById(R.id.inst1);
        Spinner spinner =  dialog_x.findViewById(R.id.spinner);
        EditText editTextCrop = dialog_x.findViewById(R.id.crop_other);

        category = "";

//        category = sharedPreferences.getString(CATEGORY, "");
//        if (!category.isEmpty()) {
//            btnNext.setEnabled(true);
//            for (int i = 0; i < radioGroup1.getChildCount(); i++) {
//                View view = radioGroup1.getChildAt(i);
//                if (view instanceof RadioButton) {
//                    RadioButton radioButton = (RadioButton) view;
//                    String radioButtonText = radioButton.getText().toString();
//
//                    if (radioButtonText.equals(category)) {
//                        radioButton.setChecked(true); // Set the radio button as checked
//                        break; // Exit the loop since we found a match
//                    }
//                }
//            }
//        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_x.dismiss();
            }
        });

        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedRadioButton = dialog_x.findViewById(checkedId);
                if (selectedRadioButton != null) {
                    category = selectedRadioButton.getText().toString();
                    editTextCrop.setText("");
                    linearLayout3.setVisibility(View.GONE);
                    if (category.equals("Crop")){
                        textView.setText("Select crop category");
                        adapter = ArrayAdapter.createFromResource(ChatActivity.this, R.array.crops, android.R.layout.simple_spinner_item);
                    }
                    else {
                        textView.setText("Select animal category");
                        adapter = ArrayAdapter.createFromResource(ChatActivity.this, R.array.animal, android.R.layout.simple_spinner_item);

                    }
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                    linearLayout2.setVisibility(View.VISIBLE);
                }
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subCategory = (String) parent.getItemAtPosition(position);
                if (subCategory.equals("Select one")) {
                    btnNext.setEnabled(false);
                }
                else {
                    if (subCategory.equals("Others")){
                        btnNext.setEnabled(false);
                        linearLayout3.setVisibility(View.VISIBLE);
                        if (category.equals("Crop")){
                            textView_2.setText("Please enter your crop category");
                        }
                        else {
                            textView_2.setText("Please enter your animal category");
                        }
                    }
                    else {
                        btnNext.setEnabled(true);
                        linearLayout3.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        editTextCrop.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String value = s.toString().trim();
                if (value.length() >= 3){
                    btnNext.setEnabled(true);
                }
                else {
                    btnNext.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String other = editTextCrop.getText().toString().trim();
                editor.putString(CATEGORY, category);
                editor.putString(SUB_CATEGORY, subCategory);
                editor.putString(OTHER, other);
                editor.apply();
                dialog_x.dismiss();
                load_topic(category);
            }
        });

        dialog_x.show();
    }

    private void load_topic(String category) {
        Dialog dialog_x = new Dialog(ChatActivity.this);
        dialog_x.setContentView(R.layout.topic);
        dialog_x.setCancelable(false);

        Window window = dialog_x.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        Button btnBack = dialog_x.findViewById(R.id.cancel);
        Button btnSubmit = dialog_x.findViewById(R.id.submit);

        Spinner spinner_x = dialog_x.findViewById(R.id.spinner_x);
        TextView textView = dialog_x.findViewById(R.id.heading3);

        LinearLayout checkbox_layout = dialog_x.findViewById(R.id.linear_sub);

        LinearLayout location_layout = dialog_x.findViewById(R.id.linear_location);
        EditText editTextLocation = dialog_x.findViewById(R.id.location);


        String[] items;
        if (category.equals("Crop")){
            items = new String[]{"Select one", getString(R.string.soil), getString(R.string.planting),
                    getString(R.string.management), getString(R.string.harvesting), getString(R.string.market),
                    getString(R.string.crop), getString(R.string.genetically), getString(R.string.integrated),
                    getString(R.string.climate), getString(R.string.sustainable), getString(R.string.equipment),
                    getString(R.string.government)};
        }
        else {
            items = new String[]{"Select one", getString(R.string.livestock), getString(R.string.housing),
                    getString(R.string.feed), getString(R.string.health), getString(R.string.birthing),
                    getString(R.string.pasture), getString(R.string.harvesting), getString(R.string.sales),
                    getString(R.string.technology), getString(R.string.farming), getString(R.string.animal),
                    getString(R.string.business)};
        }

        SpinnerAdapter adapter1 = new SpinnerAdapter(ChatActivity.this, R.layout.custom_spinner_item, items);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_x.setAdapter(adapter1);

        spinner_x.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                topic = (String) parent.getItemAtPosition(position);
                if (topic.equals("Select one")) {
                    checkbox_layout.setVisibility(View.GONE);
                    editTextLocation.setText("");
                    location_layout.setVisibility(View.GONE);
                    textView.setText("");
                }
                else {
                    checkbox_layout.setVisibility(View.VISIBLE);
                    textView.setText("Select sub topics");
                    location_layout.setVisibility(View.GONE);
                    checkBoxList.clear();
                    editTextLocation.setText("");
                    create_checkboxes(checkbox_layout, location_layout, editTextLocation);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        editTextLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String value = s.toString().trim();
                if (value.length() >= 3) {
                    btnSubmit.setEnabled(true);
                }
                else {
                    btnSubmit.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_x.dismiss();
                selectCategory();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subTopic = Functions.convertListToString(checkBoxList);
                String other = sharedPreferences.getString(OTHER, "");
                location = editTextLocation.getText().toString().trim();
                if (!other.isEmpty()){
                    subCategory = other;
                }

                sendQuestion(dialog_x);
            }
        });

        dialog_x.show();
    }

    private void create_checkboxes(LinearLayout checkboxLayout, LinearLayout locationLayout, EditText editText) {
        String[] items = Functions.get_items(ChatActivity.this, topic);

        checkboxLayout.removeAllViews();
        for(String item : items){
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(item);
            checkBox.setChecked(false);

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    String checkboxText = checkBox.getText().toString();
                    if (isChecked){
                        checkBoxList.add(checkboxText);
                        locationLayout.setVisibility(View.VISIBLE);
                    }
                    else {
                        checkBoxList.remove(checkboxText);
                        if (checkBoxList.size() == 0){
                            editText.setText("");
                            locationLayout.setVisibility(View.GONE);
                        }
                    }
                }
            });

            checkboxLayout.addView(checkBox);
        }
    }

    private void sendQuestion(Dialog dialog) {
        dialog.dismiss();
        progressDialog.showProgressDialog(null);

        Question qn = new Question(question);
        Item item = new Item(0, qn);
        itemList.add(item);
        chatAdapter.notifyDataSetChanged();

        if (instructLinearLayout.getVisibility() == View.VISIBLE){
            instructLinearLayout.setVisibility(View.GONE);
            responseRecyclerView.setVisibility(View.VISIBLE);
        }

        Chat chat = new Chat(question, language, category, subCategory, topic, subTopic, location, true);
//        Chat chat = new Chat(question, "English", "crop", "beans", "planting", "planting", "kampala", true);
        Call<Gpt> call = Functions.jsonPlaceHolder("chat").sendQuery(chat);
        call.enqueue(new Callback<Gpt>() {
            @Override
            public void onResponse(Call<Gpt> call, Response<Gpt> response) {
                progressDialog.closeProgressDialog();
                if (response.isSuccessful()){
                    answer = response.body().getAnswer();
                    GptResponse response1 = new GptResponse(answer, "");
                    Item item = new Item(1, response1);
                    itemList.add(item);
                    chatAdapter.notifyDataSetChanged();

                    if (!isVisible()){
                        responseRecyclerView.smoothScrollToPosition(chatAdapter.getItemCount()-1);
                    }
                    editText.setText("");
                }
                else {
                    int code = response.code();
                    Functions.showToast(ChatActivity.this, "Error code: " + code);
                }

            }

            @Override
            public void onFailure(Call<Gpt> call, Throwable t) {
                progressDialog.closeProgressDialog();
                Functions.showToast(ChatActivity.this, "Something went wrong, Please try again later");
            }
        });

    }

    public boolean isVisible(){
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) responseRecyclerView.getLayoutManager();
        int positionOfLastVisibleItem = linearLayoutManager.findLastCompletelyVisibleItemPosition();
        int itemCount = responseRecyclerView.getAdapter().getItemCount();
        return (positionOfLastVisibleItem>=itemCount);
    }

}