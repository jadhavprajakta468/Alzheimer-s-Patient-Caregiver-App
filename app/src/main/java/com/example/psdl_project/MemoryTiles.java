package com.example.psdl_project;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.Collections;

public class MemoryTiles extends AppCompatActivity {

    TextView movesText;
    ImageView iv_11, iv_12, iv_13, iv_14, iv_21, iv_22, iv_23, iv_24, iv_31, iv_32, iv_33, iv_34;
    Integer[] cardsArray = {101, 102, 103, 104, 105, 106, 201, 202, 203, 204, 205, 206};
    int images101, images102, images103, images104, images105, images106,
            images201, images202, images203, images204, images205, images206;
    int firstCard, secondCard;
    int clickedFirst, clickedSecond;
    int cardNumber = 1;
    int totalMoves = 0;
    int matchedPairs = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_tiles);

        movesText = findViewById(R.id.p2);

        iv_11 = findViewById(R.id.iv_11);
        iv_12 = findViewById(R.id.iv_12);
        iv_13 = findViewById(R.id.iv_13);
        iv_14 = findViewById(R.id.iv_14);
        iv_21 = findViewById(R.id.iv_21);
        iv_22 = findViewById(R.id.iv_22);
        iv_23 = findViewById(R.id.iv_23);
        iv_24 = findViewById(R.id.iv_24);
        iv_31 = findViewById(R.id.iv_31);
        iv_32 = findViewById(R.id.iv_32);
        iv_33 = findViewById(R.id.iv_33);
        iv_34 = findViewById(R.id.iv_34);

        iv_11.setTag("0");
        iv_12.setTag("1");
        iv_13.setTag("2");
        iv_14.setTag("3");
        iv_21.setTag("4");
        iv_22.setTag("5");
        iv_23.setTag("6");
        iv_24.setTag("7");
        iv_31.setTag("8");
        iv_32.setTag("9");
        iv_33.setTag("10");
        iv_34.setTag("11");

        frontOfCardResources();
        Collections.shuffle(Arrays.asList(cardsArray));
        movesText.setText("Moves: 0");

        setTileListeners();
    }

    private void setTileListeners() {
        ImageView[] tiles = {iv_11, iv_12, iv_13, iv_14, iv_21, iv_22, iv_23, iv_24, iv_31, iv_32, iv_33, iv_34};
        for (ImageView tile : tiles) {
            tile.setOnClickListener(v -> {
                int theCard = Integer.parseInt((String) v.getTag());
                doStuff(tile, theCard);
            });
        }
    }

    private void doStuff(ImageView iv, int card) {
        if (cardsArray[card] == 101) iv.setImageResource(images101);
        else if (cardsArray[card] == 102) iv.setImageResource(images102);
        else if (cardsArray[card] == 103) iv.setImageResource(images103);
        else if (cardsArray[card] == 104) iv.setImageResource(images104);
        else if (cardsArray[card] == 105) iv.setImageResource(images105);
        else if (cardsArray[card] == 106) iv.setImageResource(images106);
        else if (cardsArray[card] == 201) iv.setImageResource(images201);
        else if (cardsArray[card] == 202) iv.setImageResource(images202);
        else if (cardsArray[card] == 203) iv.setImageResource(images203);
        else if (cardsArray[card] == 204) iv.setImageResource(images204);
        else if (cardsArray[card] == 205) iv.setImageResource(images205);
        else if (cardsArray[card] == 206) iv.setImageResource(images206);

        if (cardNumber == 1) {
            firstCard = cardsArray[card] % 100; // Normalize IDs
            cardNumber = 2;
            clickedFirst = card;
            iv.setEnabled(false);
        } else if (cardNumber == 2) {
            secondCard = cardsArray[card] % 100;
            cardNumber = 1;
            clickedSecond = card;

            disableAllTiles();
            new Handler().postDelayed(this::calculate, 1000);
        }
    }

    private void calculate() {
        if (firstCard == secondCard) {
            makeInvisible(clickedFirst);
            makeInvisible(clickedSecond);
            matchedPairs++;
        } else {
            resetTile(clickedFirst);
            resetTile(clickedSecond);
        }
        enableAllTiles();
        totalMoves++;
        movesText.setText("Moves: " + totalMoves);

        if (matchedPairs == cardsArray.length / 2) {
            movesText.setText("Game Over! Total Moves: " + totalMoves);
        }
    }

    private void makeInvisible(int index) {
        ImageView tile = getTileByIndex(index);
        if (tile != null) tile.setVisibility(View.INVISIBLE);
    }

    private void resetTile(int index) {
        ImageView tile = getTileByIndex(index);
        if (tile != null) {
            tile.setImageResource(R.drawable.back);
            tile.setEnabled(true);
        }
    }

    private void disableAllTiles() {
        ImageView[] tiles = {iv_11, iv_12, iv_13, iv_14, iv_21, iv_22, iv_23, iv_24, iv_31, iv_32, iv_33, iv_34};
        for (ImageView tile : tiles) tile.setEnabled(false);
    }

    private void enableAllTiles() {
        ImageView[] tiles = {iv_11, iv_12, iv_13, iv_14, iv_21, iv_22, iv_23, iv_24, iv_31, iv_32, iv_33, iv_34};
        for (ImageView tile : tiles) {
            if (tile.getVisibility() == View.VISIBLE) tile.setEnabled(true);
        }
    }

    private ImageView getTileByIndex(int index) {
        switch (index) {
            case 0: return iv_11;
            case 1: return iv_12;
            case 2: return iv_13;
            case 3: return iv_14;
            case 4: return iv_21;
            case 5: return iv_22;
            case 6: return iv_23;
            case 7: return iv_24;
            case 8: return iv_31;
            case 9: return iv_32;
            case 10: return iv_33;
            case 11: return iv_34;
            default: return null;
        }
    }

    private void frontOfCardResources() {
        images101 = R.drawable.img1;
        images102 = R.drawable.img2;
        images103 = R.drawable.img3;
        images104 = R.drawable.img4;
        images105 = R.drawable.img5;
        images106 = R.drawable.img6;
        images201 = R.drawable.img1;
        images202 = R.drawable.img2;
        images203 = R.drawable.img3;
        images204 = R.drawable.img4;
        images205 = R.drawable.img5;
        images206 = R.drawable.img6;
    }
}
