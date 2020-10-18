package com.example.question;

import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {

    private QuestionDBHelper dbHelper;
    private ArrayList<QuestionBean> data;
    private QuestionBean question;
    private String level;
    private int countStage, endStage, answer, total;

    private ImageView quizeCenterImageView;
    private TextView quizeCenterTextView;

    private TextView quizTextView2, quizScoreTextView2, countStageTextView, endStageTextView;
    private Button submit;
    private ConstraintLayout textEasyRadioLayout, textHardRadioLayout, imageRadioLayout;

    private RadioGroup textEasyRadioGroup, textHardRadioGroup, imageQiuzRadioGroup1, imageQiuzRadioGroup2;
    private RadioButton textEasyQuizNumber1, textEasyQuizNumber2, textEasyQuizNumber3, textEasyQuizNumber4;
    private TextView textEasyQuiz1, textEasyQuiz2, textEasyQuiz3, textEasyQuiz4;
    private RadioButton textHardQiuzNumber1;
    private EditText textHardQuiz1;
    private RadioButton imageQiuzAnswerNumber1, imageQiuzAnswerNumber2, imageQiuzAnswerNumber3, imageQiuzAnswerNumber4;
    private ImageButton imageQiuzAnswerImage1, imageQiuzAnswerImage2, imageQiuzAnswerImage3, imageQiuzAnswerImage4;
    private Button end;

    private TextView toastView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        end = findViewById(R.id.end);
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // -----------------------------------------------------------------------------------------
        quizTextView2 = findViewById(R.id.quizTextView2);   // 문제
        quizScoreTextView2 = findViewById(R.id.quizScoreTextView2);         // 배점
        countStageTextView = findViewById(R.id.countStageTextView);
        endStageTextView = findViewById(R.id.endStageTextView);
        submit = findViewById(R.id.submit);                         // 제출
        submit.setOnClickListener(listener);
        textEasyRadioLayout = findViewById(R.id.textEasyRadioLayout);   // 이지 레이아웃
        textHardRadioLayout = findViewById(R.id.textHardRadioLayout);   // 하드 레이아웃
        imageRadioLayout = findViewById(R.id.imageRadioLayout);         // 이미지 레이아웃
        quizeCenterImageView = findViewById(R.id.quizeCenterImageView);
        quizeCenterTextView = findViewById(R.id.quizeCenterTextView);
        // -------------------- easyText -----------------------------------------------------------
        textEasyRadioGroup = findViewById(R.id.textEasyRadioGroup);     // 그룹
        textEasyQuizNumber1 = findViewById(R.id.textEasyQuizNumber1);   // 문제 번호
        textEasyQuizNumber2 = findViewById(R.id.textEasyQuizNumber2);
        textEasyQuizNumber3 = findViewById(R.id.textEasyQuizNumber3);
        textEasyQuizNumber4 = findViewById(R.id.textEasyQuizNumber4);
        textEasyQuiz1 = findViewById(R.id.textEasyQuiz1);               // 문제 답
        textEasyQuiz2 = findViewById(R.id.textEasyQuiz2);
        textEasyQuiz3 = findViewById(R.id.textEasyQuiz3);
        textEasyQuiz4 = findViewById(R.id.textEasyQuiz4);
        // -------------------- hardText -----------------------------------------------------------
        textHardRadioGroup = findViewById(R.id.textHardRadioGroup);     // 그룹
        textHardQiuzNumber1 = findViewById(R.id.textHardQiuzNumber1);   // 문제 번호
        textHardQuiz1 = findViewById(R.id.textHardQuiz1);               // 문제 답
        // -------------------- image    -----------------------------------------------------------
        imageQiuzRadioGroup1 = findViewById(R.id.imageQiuzRadioGroup1);         // 그룹
        imageQiuzRadioGroup2 = findViewById(R.id.imageQiuzRadioGroup2);
        imageQiuzAnswerNumber1 = findViewById(R.id.imageQiuzAnswerNumber1);     // 문제 번호
        imageQiuzAnswerNumber2 = findViewById(R.id.imageQiuzAnswerNumber2);
        imageQiuzAnswerNumber3 = findViewById(R.id.imageQiuzAnswerNumber3);
        imageQiuzAnswerNumber4 = findViewById(R.id.imageQiuzAnswerNumber4);
        imageQiuzAnswerImage1 = findViewById(R.id.imageQiuzAnswerImage1);       // 문제 답
        imageQiuzAnswerImage2 = findViewById(R.id.imageQiuzAnswerImage2);
        imageQiuzAnswerImage3 = findViewById(R.id.imageQiuzAnswerImage3);
        imageQiuzAnswerImage4 = findViewById(R.id.imageQiuzAnswerImage4);
        // -----------------------------------------------------------------------------------------

        level = getIntent().getStringExtra("level");    // 이지, 하드
        total = countStage = 0;         // 시작
        dbHelper = new QuestionDBHelper(this, "DB", null, 1);
        data = dbHelper.get();
        endStage = data.size(); // 끝
        setLayout(countStage);
    }

    public void setLayout(int countStage) {
        if (endStage == 0) {
            quizeCenterImageView.setVisibility(View.VISIBLE);
            quizeCenterImageView.setImageResource(R.drawable.no);
            quizeCenterTextView.setVisibility(View.VISIBLE);
            quizeCenterTextView.setText("문제가 없습니다");
            submit.setVisibility(View.GONE);
            end.setVisibility(View.VISIBLE);
        }else if (level != null && endStage != 0 && countStage != endStage) {
            setStage(countStage, level);
            quizeCenterImageView.setVisibility(View.GONE);
            quizeCenterTextView.setVisibility(View.GONE);
        } else if (countStage != 0 && countStage == endStage){
            imageRadioLayout.setVisibility(View.GONE);
            textEasyRadioLayout.setVisibility(View.GONE);
            textHardRadioLayout.setVisibility(View.GONE);
            quizeCenterImageView.setVisibility(View.VISIBLE);
            quizeCenterImageView.setImageResource(R.drawable.good);
            quizeCenterTextView.setVisibility(View.VISIBLE);
            quizeCenterTextView.setText("축하합니다\n총점 : "+total);
            submit.setVisibility(View.GONE);
            end.setVisibility(View.VISIBLE);
        }
    }

    public void setStage(int countStage, String level) {
        question = data.get(this.countStage++);
        if (question.getType().equals(QuestionBean.TYPE_TEXT)) {
            imageRadioLayout.setVisibility(View.GONE);
            if (level.equals(MainActivity.START_EASY)) {
                textHardRadioLayout.setVisibility(View.GONE);
                textEasyRadioLayout.setVisibility(View.VISIBLE);
                textEasyQuiz1.setText(question.getEx1());
                textEasyQuiz2.setText(question.getEx1());
                textEasyQuiz3.setText(question.getEx1());
                textEasyQuiz4.setText(question.getEx1());
            } else if (level.equals(MainActivity.START_HARD)) {
                textEasyRadioLayout.setVisibility(View.GONE);
                textHardRadioLayout.setVisibility(View.VISIBLE);
            }
        } else if (question.getType().equals(QuestionBean.TYPE_IMAGE)) {
            textEasyRadioLayout.setVisibility(View.GONE);
            textHardRadioLayout.setVisibility(View.GONE);
            imageRadioLayout.setVisibility(View.VISIBLE);
            imageQiuzAnswerImage1.setImageURI(Uri.parse(question.getEx1()));
            imageQiuzAnswerImage2.setImageURI(Uri.parse(question.getEx2()));
            imageQiuzAnswerImage3.setImageURI(Uri.parse(question.getEx3()));
            imageQiuzAnswerImage4.setImageURI(Uri.parse(question.getEx4()));
        }
        // ------------------ 기본 데이터 -------------------------------------------------------------
        quizTextView2.setText(question.getQuestion());
        quizScoreTextView2.setText(String.valueOf(question.getScore()));
        countStageTextView.setText(String.valueOf(++countStage));
        endStageTextView.setText(String.valueOf(endStage));
        // -----------------------------------------------------------------------------------------
    }

    private View.OnClickListener listener = v -> {
        if(v.getId() == submit.getId()){
            if(checkAnswer()){
                setLayout(countStage);
                Toast toast = Toast.makeText(this," ",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,150);
                toast.setView(getLayoutInflater().inflate(R.layout.toast, null));
                toast.show();
            }
            else{
                setLayout(countStage);
                Toast toast = Toast.makeText(this," ",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,150);
                toast.setView(getLayoutInflater().inflate(R.layout.toast2, null));
                toast.show();
            }

        }
    };

    public boolean checkAnswer(){
        if(imageRadioLayout.getVisibility() == View.VISIBLE) {
            if (imageQiuzAnswerNumber1.isChecked())
                answer = 1;
            else if (imageQiuzAnswerNumber2.isChecked())
                answer = 2;
            else if (imageQiuzAnswerNumber3.isChecked())
                answer = 3;
            else if (imageQiuzAnswerNumber4.isChecked())
                answer = 4;
            imageQiuzRadioGroup1.clearCheck();
            imageQiuzRadioGroup2.clearCheck();
            if(answer == question.getAnswer()){
                total += question.getScore();
                return true;
            }
            else return false;
        }else if(textEasyRadioLayout.getVisibility() == View.VISIBLE) {
            if (textEasyQuizNumber1.isChecked())
                answer = 1;
            else if (textEasyQuizNumber2.isChecked())
                answer = 2;
            else if (textEasyQuizNumber3.isChecked())
                answer = 3;
            else if (textEasyQuizNumber4.isChecked())
                answer = 4;
            textEasyRadioGroup.clearCheck();
            if(answer == question.getAnswer()){
                total += question.getScore();
                return true;
            }
            else return false;
        }else if(textHardRadioLayout.getVisibility() == View.VISIBLE){
            String str = "";
            if(question.getAnswer() == 1){
                str = question.getEx1();
            }
            else if(question.getAnswer() == 2){
                str = question.getEx2();
            }
            else if(question.getAnswer() == 3){
                str = question.getEx3();
            }
            else if(question.getAnswer() == 4){
                str = question.getEx4();
            }

            if(String.valueOf(str).equals(String.valueOf(textHardQuiz1.getText()))){
                textHardQuiz1.setText("");
                total += question.getScore();
                return true;
            }
            else{
                textHardQuiz1.setText("");
                return false;
            }
        }
        return false;
    }

}
