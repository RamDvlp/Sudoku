package com.example.sudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class SudokuActivity extends AppCompatActivity {

    private TableLayout grid;
    private static final int SIZE = 9;
    private Sudoku sudoku = null;

    private TextView game_grid[][];

    private int focusedField[];
    private boolean selected = false;
    private Button selectionNumbers[];

    private Timer timer;
    private Clock clock;

    private TextView time_LBL;

    private String userNickName, userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku);

        ScreenUtils.hideSystemUI(this);

        initSudokuBoard();
        Log.d("ASD", sudoku.toString());

        initViews();
        int dif = getIntent().getIntExtra("Difficulty", 0);


        /*
        debug
         */
        //sudoku.prepGameBoard(dif);
        Log.d("ASD", sudoku.toString());

        sudoku.solvedBoard();

        grid = findViewById(R.id.sudoku_grid);

        game_grid = new TextView[SIZE][SIZE];

        for(int i =0; i<SIZE; i++){
            TableRow tr = new TableRow(grid.getContext());

            for(int j= 0; j<SIZE;j++){
                game_grid[i][j] = new TextView(this);
                game_grid[i][j].setTextColor(getColor(R.color.white));
                game_grid[i][j].setTextSize(25);
                game_grid[i][j].setBackground(getDrawable(R.drawable.grid_cell_background));

                if(sudoku.getEllement(i,j) == 0){
                    game_grid[i][j].setText(" "+"  "+ " ");
                    int finalI = i;
                    int finalJ = j;
                    game_grid[i][j].setOnClickListener(v -> fieldSelected(finalI, finalJ));
                } else {
                    game_grid[i][j].setText(" "+sudoku.getEllement(i,j)+ " ");

                }

                game_grid[i][j].setTransitionName(""+ i+ j);
                //game_grid[i][j].setText(" "+sudoku.getEllement(i,j)+ " ");
                game_grid[i][j].setPadding(10,0,10,0);

                tr.addView(game_grid[i][j]);
            }

            grid.addView(tr);
        }

        focusedField = new int[] {
            -1,-1
        };

        time_LBL = findViewById(R.id.sudoku_LBL_clock);
        clock = new Clock(0,0);
        timer = new Timer();


    }


    @Override
    protected void onResume() {
        super.onResume();
        startTimer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopTimer();
    }

    private void stopTimer() {
        if(timer != null){
            timer.cancel();
            timer.purge();
            timer = null;
        }

    }

    private void startTimer() {

            if(timer == null){
                timer = new Timer();

            }



            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            time_LBL.setText(clock.getCurrentCount());
                            clock.tick();
                        }
                    });
                }
            },0, 1000);

        }


    private void initViews() {
        selectionNumbers = new Button[]{findViewById(R.id.button_1),
                findViewById(R.id.button_2),
                findViewById(R.id.button_3),
                findViewById(R.id.button_4),
                findViewById(R.id.button_5),
                findViewById(R.id.button_6),
                findViewById(R.id.button_7),
                findViewById(R.id.button_8),
                findViewById(R.id.button_9)};


        for(int i=0;i<SIZE;i++){
            int finalI = i;
            selectionNumbers[i].setOnClickListener(v -> writeNumberOnBoard(finalI +1));
        }
    }

    private void writeNumberOnBoard(int i) {
        if(selected){
            //initial case
            if(focusedField[0]!= -1){
                unPaintReleventNumbers(i);
            }
            selected = false;

            //if(sudoku.getRemainingEmptyFiels()!=0){
                if(sudoku.checkLawsWithRequestedValue(i,focusedField[0],focusedField[1])){
                    //sudoku.setItem(i,focusedField[0],focusedField[1]);
                    game_grid[focusedField[0]][focusedField[1]].setText(" "+ i + " ");
                    game_grid[focusedField[0]][focusedField[1]].setTextColor(getColor(R.color.white));
                    game_grid[focusedField[0]][focusedField[1]].setBackground(getDrawable(R.drawable.grid_cell_background));

                    focusedField[0] = -1;
                    focusedField[1] = -1;

                    if(sudoku.getRemainingEmptyFiels()==0)
                        gameOver();

                } else {
                    Toast.makeText(this, "Incorrect Value By Sudoku Laws", Toast.LENGTH_SHORT).show();
                    game_grid[focusedField[0]][focusedField[1]].setBackground(getDrawable(R.drawable.grid_cell_background));

                }
            //} else {
              //  gameOver();
            //}


        } else {
            unPaintReleventNumbers(i);
            paintReleventNumbers(i);
        }

    }


    private void gameOver() {
        stopTimer();


        userID = mySP.getSP().readID();
        if(userID.equals("")){
            userID = String.valueOf(UUID.randomUUID());
            mySP.getSP().writeID(userID);
        }

        //Toast.makeText(this, ""+userID, Toast.LENGTH_SHORT).show();
        Log.d("ASD", userID);
        userNickName = mySP.getSP().readNickName();

        Log.d("ASD", userNickName);
        //Toast.makeText(this, ""+userNickName, Toast.LENGTH_SHORT).show();
        String userTime = time_LBL.getText().toString();

        LocalUser lc = new LocalUser(userID,userNickName);
        lc.setUserTime(userTime);

        FireBaseModul.getFireBaseModul().uploadUser(lc);





       //FireBaseModul.getFireBaseModul().readAllUsers();

        Toast.makeText(this, "Game Over!", Toast.LENGTH_SHORT).show();
        //TODO - ask user if new game or go to leaderboard
        //for now, just go leaderboard
        finish();

    }

    private void paintReleventNumbers(int pressedValue) {
        for(int i=0; i<SIZE ; i++){
            for(int j=0; j<SIZE;j++){
                if(sudoku.getEllement(i,j) == pressedValue){
                    game_grid[i][j].setTextColor(getColor(R.color.yellow));
                }
            }
        }
    }


    private void unPaintReleventNumbers(int pressedValue) {
        for(int i=0; i<SIZE ; i++){
            for(int j=0; j<SIZE;j++){
                //if(sudoku.getEllement(i,j) == pressedValue){
                    game_grid[i][j].setTextColor(getColor(R.color.white));
                //}
            }
        }
    }

    private void fieldSelected(int finalI, int finalJ) {
        if(selected){
            //game_grid[focusedField[0]][focusedField[1]].setText("     ");
            game_grid[focusedField[0]][focusedField[1]].setBackground(getDrawable(R.drawable.grid_cell_background));

            //game_grid[focusedField[0]][focusedField[1]].setTextColor(getColor(R.color.white));

        }
        focusedField[0] = finalI;
        focusedField[1] = finalJ;
        selected = true;
        //game_grid[finalI][finalJ].setText(" O ");
        game_grid[finalI][finalJ].setBackground(getDrawable(R.drawable.sub_mat_seperation));
        //game_grid[finalI][finalJ].setTextColor(getColor(R.color.yellow));
        //Toast.makeText(this, game_grid[finalI][finalJ].getText()+"", Toast.LENGTH_SHORT).show();
    }

    public void initSudokuBoard(){
        boolean sucsses = false;

        do {
            try {
                sudoku = new Sudoku(SIZE);
                sucsses = true;
            } catch (Exception e) {

            }
        } while(sucsses==false);


    }
}