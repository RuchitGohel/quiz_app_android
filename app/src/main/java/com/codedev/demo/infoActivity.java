package com.codedev.demo;

import androidx.appcompat.app.AppCompatActivity;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



public class infoActivity extends AppCompatActivity {
    private TextView t1,t2,t3,t4,t5;
    private Button startTest;
    public static int setNo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        startTest=findViewById(R.id.startTest);
        com.google.android.material.slider.Slider slider = findViewById(R.id.questionCountSlider);
        final TextView countLabel = findViewById(R.id.questionCountLabel);

        setNo=getIntent().getIntExtra("SETNO",1);

        slider.addOnChangeListener(new com.google.android.material.slider.Slider.OnChangeListener() {
            @Override
            public void onValueChange(@androidx.annotation.NonNull com.google.android.material.slider.Slider slider, float value, boolean fromUser) {
                countLabel.setText("Select Number of Questions: " + (int)value);
            }
        });


        startTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(infoActivity.this,QuestionActivity.class);
                intent.putExtra("QUESTION_COUNT", (int) slider.getValue());
                startActivity(intent);
            }
        });

    }

}