package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class StartGame extends AppCompatActivity {
    public static final String CHOOSEN_OPTION = "com.example.tictactoe.example.CHOOSEN_OPTION";
    private RadioGroup rdGroup;
    private RadioButton rdButton;
    private LinearLayout instructionLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);
        boolean playWithComputer = false;

        rdGroup = findViewById(R.id.radioGroup);
        instructionLayout = findViewById(R.id.instructionLayout);

    }

    public void StartGame(View view){
        int radioID = rdGroup.getCheckedRadioButtonId();
        //Log.d("Test Radio Check", "radioID="+radioID);
        if (radioID==-1){
            Toast.makeText(this, "Please select one option to start the game", Toast.LENGTH_SHORT).show();
        }else{
            rdButton = findViewById(radioID);
            int PlayOption = Integer.parseInt(rdButton.getTag().toString());

            Intent intent = new Intent(StartGame.this,MainActivity.class);
            intent.putExtra(CHOOSEN_OPTION,PlayOption);
            startActivity(intent);
            //Log.d("OptionSelected","PlayOption="+PlayOption);
        }


    }

    public void ShowInstruction(View view){
        Intent intent = new Intent(StartGame.this,InstructionsActivity.class);
        startActivity(intent);
    }

    public void ShowInstruction2 (View view){
        instructionLayout.setVisibility(View.VISIBLE);
    }


}