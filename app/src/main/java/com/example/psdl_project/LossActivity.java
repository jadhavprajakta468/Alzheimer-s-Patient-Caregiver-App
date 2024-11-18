package com.example.psdl_project;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class LossActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loss);

        TextView scoreTextView = findViewById(R.id.scoreTextView);
        TextView incorrectAnswersTextView = findViewById(R.id.incorrectAnswersTextView);

        int score = getIntent().getIntExtra("score", 0);
        int totalQuestions = getIntent().getIntExtra("totalQuestions", 0);
        List<Question> incorrectQuestions = (List<Question>) getIntent().getSerializableExtra("incorrectQuestions");

        if (incorrectQuestions == null) {
            Toast.makeText(this, "No incorrect questions data available.", Toast.LENGTH_SHORT).show();
            return;
        }

        scoreTextView.setText("You scored " + score + " out of " + totalQuestions + ".");

        StringBuilder missedQuestionsText = new StringBuilder("Incorrect Answers:\n");
        for (Question question : incorrectQuestions) {
            missedQuestionsText.append("\nQ: ").append(question.getQuestionText())
                    .append("\nCorrect Answer: ").append(question.getCorrectAnswer())
                    .append("\n");
        }
        incorrectAnswersTextView.setText(missedQuestionsText.toString());
    }
}
