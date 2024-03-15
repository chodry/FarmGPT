package com.ug.air.farmgpt.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ug.air.farmgpt.R;
import com.ug.air.farmgpt.Utils.Functions;

public class ChatActivity extends AppCompatActivity {

    ImageView btnBack, btnSend;
    RecyclerView responseRecyclerView;
    LinearLayout instructLinearLayout;
    EditText editText;
    String question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat);

        btnBack = findViewById(R.id.back);
        responseRecyclerView = findViewById(R.id.recyclerView);
        instructLinearLayout = findViewById(R.id.instruct);
        btnSend = findViewById(R.id.send);
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
                openDialog();
            }
            
        });
    }

    private void openDialog() {
    }


}