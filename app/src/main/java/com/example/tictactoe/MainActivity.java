package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    private Matrix cells;
    private int playerTurn;
    private TextView message;
    private boolean gameEnds;
    private int round;
    private boolean LCorientation = false;
    private int maxRound;
    private int choosenOption;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        choosenOption = intent.getIntExtra(StartGame.CHOOSEN_OPTION,0);

        cells = new Matrix();
        message = findViewById(R.id.message);
        round = 0;
        playerTurn = 1;
        gameEnds = false;
        if (choosenOption==2) message.setText("Your turn");
        else message.setText("Player 1 turns");

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            LCorientation = true;
            maxRound = 25;
            if (savedInstanceState != null){
                cells = (Matrix) savedInstanceState.getSerializable("CELLS");
                playerTurn = savedInstanceState.getInt("PLAYER_TURN");
                round = savedInstanceState.getInt("ROUND");
                gameEnds = savedInstanceState.getBoolean("GAME_ENDS");
                message.setText(savedInstanceState.getString("MESSAGE"));
                restoreButtonText();
            }
        }else {
            LCorientation = false;
            maxRound = 9;
        }



    }
    private void restoreButtonText(){
        Button b1 = findViewById(R.id.button7);
        int vb1 = cells.get(1,1);
        if(vb1!=0) b1.setText(vb1+"");

        Button b2 = findViewById(R.id.button8);
        int vb2 = cells.get(1,2);
        if(vb2!=0) b2.setText(vb2+"");

        Button b3 = findViewById(R.id.button9);
        int vb3=  cells.get(1,3);
        if(vb3!=0) b3.setText(vb3+"");

        Button b4 = findViewById(R.id.button12);
        int vb4 = cells.get(2,1);
        if(vb4!=0) b4.setText(vb4+"");

        Button b5 = findViewById(R.id.button13);
        int vb5 =cells.get(2,2);
        if(vb5!=0) b5.setText(vb5+"");

        Button b6 = findViewById(R.id.button14);
        int vb6 = cells.get(2,3);
        if(vb6!=0) b6.setText(vb6+"");

        Button b7 = findViewById(R.id.button17);
        int vb7 =  cells.get(3,1);
        if(vb7!=0) b7.setText(vb7+"");

        Button b8 = findViewById(R.id.button18);
        int vb8 = cells.get(3,2);
        if(vb8!=0) b8.setText(vb8+"");

        Button b9 = findViewById(R.id.button19);
        int vb9 = cells.get(3,3);
        if(vb9!=0) b9.setText(vb9+"");

    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable("CELLS",cells);
        savedInstanceState.putInt("PLAYER_TURN",playerTurn);
        savedInstanceState.putInt("ROUND",round);
        savedInstanceState.putBoolean("GAME_ENDS",gameEnds);
        savedInstanceState.putString("MESSAGE",(String)message.getText());
    }



    public void buttonClicked(View view) {
        if (!gameEnds) {
            if (choosenOption==2) executeGameWithComputer(view);
            else executeGame(view);
        }
    }

    private void executeGame(View view){
        round++;

            Button button = (Button) view;
            button.setText("" + playerTurn);
            int id = Integer.parseInt((String) view.getTag());
            int rowIndex = (id - 1) / 5;
            int colIndex = (id - 1) % 5;
            cells.set(rowIndex, colIndex, playerTurn);
            //Log.v("rowIndex", "rowIndex=" + rowIndex);
            //Log.v("colIndex", "colIndex=" + colIndex);


        int winner = checkWinner();


        if(winner==1){
            message.setText("Player 1 wins");
            gameEnds = true;
        } else if(winner==2){
            message.setText("Player 2 wins");
            gameEnds = true;
        }

        if(round==maxRound && winner==0){
            message.setText("Draw!");
            gameEnds = true;
        }

        if(!gameEnds){
            if(playerTurn==1){
                playerTurn=2;
                message.setText("Player 2 turns");
            }else{
                playerTurn=1;
                message.setText("Player 1 turns");
            }
        }
    }

    private void executeGameWithComputer(View view){


        if (playerTurn==1) {
            round++;
            Button button = (Button) view;
            button.setText("1");
            int id = Integer.parseInt((String) view.getTag());
            int rowIndex = (id - 1) / 5;
            int colIndex = (id - 1) % 5;
            cells.set(rowIndex, colIndex, 1);
        }

        int winner = checkWinner();


        if(winner==1){
            message.setText("You win");
            gameEnds = true;
        }

        if(round==maxRound && winner==0){
            message.setText("Draw!");
            gameEnds = true;
        }

        if(!gameEnds){
            if(playerTurn==1){
                playerTurn=2;
                message.setText("Computer turn");
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        RandomCell();
                    }
                }, 2000);


            }
        }
    }

    //0 - no winner, 1 - player 1 is the winner, 2 - player 2 is the winner
    private int checkWinner(){

        int winner = 0;
        if(checkIfWinner(1)){
            winner = 1;
        }else if(checkIfWinner(2)){
            winner = 2;
        }

        //check for player 2
        return winner;
    }


    private boolean checkIfWinner(int playerNumber){
        boolean win = false;
        int controlDiag1 = 0;
        int controlDiag2 = 0;
        int size, range1, range2, reverse, controller;

        if (LCorientation) {
            size = 5;
            reverse = 4;
            range1 = 0;
            range2 = 0;
            controller = 5;
        }else{
            size = 4;
            reverse = 3;
            range1 = 1;
            range2 = 1;
            controller = 3;

        }

        for (int outer=range1;outer<size;outer++) {
            int controlRow = 0;
            int controlCol = 0;
            for (int inner=range2;inner<size;inner++) {
                if (cells.get(outer, inner)==playerNumber) controlRow+=1;
                if (cells.get(inner,outer)==playerNumber) controlCol+=1;

            }
            if (cells.get(outer, outer)==playerNumber) controlDiag1+=1;
            if (cells.get(outer, reverse)==playerNumber) controlDiag2+=1;
            if (reverse>0) reverse=reverse-1;

            if (controlRow==controller||controlCol==controller||controlDiag1==controller||controlDiag2==controller) {
                win = true;
            }

        }
        return win;

    }

    private void RandomCell(){
        int range1, range2, total;
        int randomIndex;
        if (LCorientation){
            range1=0;
            range2=0;
            total=5;
        }else{
            range1=1;
            range2=1;
            total=4;
        }
        ArrayList<String> availableCells = new ArrayList<String>();
        for (int i=range1;i<total;i++){
            for (int z=range2;z<total;z++){
                int tempCell = cells.get(i,z);
                if (tempCell==0){
                    availableCells.add(""+i+","+z);
                }
            }
        }
        int maxRandom = availableCells.size();

            Random random = new Random();
            randomIndex = random.nextInt(maxRandom - 0) + 0;
            String randomCell = availableCells.get(randomIndex);
            String[] rowCol = randomCell.split(",");
            int row = Integer.parseInt(rowCol[0]);
            int col = Integer.parseInt(rowCol[1]);
            int tagValue = (row*5)+1+col;


        String buttonID = "button"+tagValue;
        Resources res = getResources();
        int id = res.getIdentifier(buttonID, "id", getPackageName());
        Button butt = findViewById(id);
        butt.setText("2");
        cells.set(row,col,2);
       // Log.d("TestRolCol", "Set row  "+row+" col "+col);

        round++;
        if (checkIfWinner(2)){
            message.setText("Computer wins");
            gameEnds = true;
        }else {
            playerTurn = 1;
            message.setText("Your turn");
        }

    }
}



