package com.example.psdl_project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Quiz extends AppCompatActivity {

    private TextView questionTextView;
    private EditText answerEditText;
    private Button submitButton;

    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;

    private List<Question> incorrectQuestions = new ArrayList<>();

    private DBHelperForm dbHelperForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        dbHelperForm = new DBHelperForm(this);

        questionTextView = findViewById(R.id.questionTextView);
        answerEditText = findViewById(R.id.answerEditText);
        submitButton = findViewById(R.id.submitButton);

        loadQuestions();

        if (questions != null && !questions.isEmpty()) {
            displayQuestion(currentQuestionIndex);
        } else {
            Toast.makeText(this, "No questions available.", Toast.LENGTH_SHORT).show();
        }

        submitButton.setOnClickListener(v -> checkAnswerAndProceed());
    }

    @SuppressLint("Range")
    private void loadQuestions() {
        questions = new ArrayList<>();

        Cursor cursor = null;
        try {
            cursor = dbHelperForm.getReadableDatabase().query(
                    DBHelperForm.TABLE_USER,
                    new String[]{
                            DBHelperForm.COLUMN_NAME,
                            DBHelperForm.COLUMN_AGE,
                            DBHelperForm.COLUMN_BIRTH_DATE,
                            DBHelperForm.COLUMN_CITY,
                            DBHelperForm.COLUMN_SPOUSE,
                            DBHelperForm.COLUMN_FAVORITE_ACTIVITY
                    },
                    null, null, null, null, null
            );

            if (cursor != null && cursor.moveToFirst()) {
                questions.add(new Question("What is your name?", cursor.getString(cursor.getColumnIndex(DBHelperForm.COLUMN_NAME))));
                questions.add(new Question("How old are you?", cursor.getString(cursor.getColumnIndex(DBHelperForm.COLUMN_AGE))));
                questions.add(new Question("What is your birth date?", cursor.getString(cursor.getColumnIndex(DBHelperForm.COLUMN_BIRTH_DATE))));
                questions.add(new Question("What city do you live in?", cursor.getString(cursor.getColumnIndex(DBHelperForm.COLUMN_CITY))));
                questions.add(new Question("Do you have a spouse?", cursor.getString(cursor.getColumnIndex(DBHelperForm.COLUMN_SPOUSE))));
                questions.add(new Question("What is your favorite activity?", cursor.getString(cursor.getColumnIndex(DBHelperForm.COLUMN_FAVORITE_ACTIVITY))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error loading questions from database", Toast.LENGTH_SHORT).show();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private void displayQuestion(int questionIndex) {
        if (questionIndex < questions.size()) {
            Question question = questions.get(questionIndex);
            questionTextView.setText(question.getQuestionText());
            answerEditText.setText("");
        }
    }

    private void checkAnswerAndProceed() {
        String userAnswer = answerEditText.getText().toString().trim();

        if (userAnswer.isEmpty()) {
            Toast.makeText(this, "Please enter an answer.", Toast.LENGTH_SHORT).show();
            return;
        }

        Question currentQuestion = questions.get(currentQuestionIndex);
        if (!userAnswer.equalsIgnoreCase(currentQuestion.getCorrectAnswer())) {
            incorrectQuestions.add(currentQuestion);
        } else {
            score++;
        }

        currentQuestionIndex++;

        if (currentQuestionIndex < questions.size()) {
            displayQuestion(currentQuestionIndex);
        } else {
            finishQuiz();
        }
    }

    private void finishQuiz() {
        Intent intent;
        if (incorrectQuestions.isEmpty()) {
            intent = new Intent(Quiz.this, WinActivity.class);
        } else {
            intent = new Intent(Quiz.this, LossActivity.class);
            intent.putExtra("score", score);
            intent.putExtra("totalQuestions", questions.size());
            intent.putExtra("incorrectQuestions", new ArrayList<>(incorrectQuestions));
        }
        startActivity(intent);
        finish();
    }
}
