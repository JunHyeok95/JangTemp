package com.example.question;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

public class QuestionActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private ConstraintLayout imageRadioLayout, textRadioLayout;
    private ToggleButton modeToggleButton;
    private Button saveButton, deleteButton;
    private EditText questionEditText, scoreEditText;
    private int qid, answer, reqInt;

    private QuestionBean question;
    private QuestionDBHelper dbHelper;

    private RadioGroup textRadioGroup;
    private RadioButton textAnswerNumber1, textAnswerNumber2, textAnswerNumber3, textAnswerNumber4;
    private EditText textQuestion1, textQuestion2, textQuestion3, textQuestion4;

    private RadioGroup imageRadioGroup1, imageRadioGroup2;
    private RadioButton imageAnswerNumber1, imageAnswerNumber2, imageAnswerNumber3, imageAnswerNumber4;
    private ImageButton imageAnswerImage1, imageAnswerImage2, imageAnswerImage3, imageAnswerImage4;

    public static final int REQ_1 = 1;
    public static final int REQ_2 = 2;
    public static final int REQ_3 = 3;
    public static final int REQ_4 = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        imageRadioLayout = findViewById(R.id.imageRadioLayout); // 이미지 레이아웃
        textRadioLayout = findViewById(R.id.textRadioLayout);   // 텍스트 레이아웃
        modeToggleButton = findViewById(R.id.modeToggleButton); // 토글
        modeToggleButton.setOnCheckedChangeListener(this);
        saveButton = findViewById(R.id.saveButton);             // 저장
        saveButton.setOnClickListener(listener);
        deleteButton = findViewById(R.id.deleteButton);         // 삭제
        deleteButton.setOnClickListener(listener);
        questionEditText = findViewById(R.id.questionEditText);     // 문제
        scoreEditText = findViewById(R.id.scoreEditText);           // 점수
        // -------------------- TEXT MODE ---------------------------------------------
        textRadioGroup = findViewById(R.id.textRadioGroup);         // 텍스트 라디오 그룹
        textAnswerNumber1 = findViewById(R.id.textAnswerNumber1);   // 텍스트 정답 번호
        textAnswerNumber2 = findViewById(R.id.textAnswerNumber2);   // 텍스트 정답 번호
        textAnswerNumber3 = findViewById(R.id.textAnswerNumber3);   // 텍스트 정답 번호
        textAnswerNumber4 = findViewById(R.id.textAnswerNumber4);   // 텍스트 정답 번호
        textQuestion1 = findViewById(R.id.textQuestion1);   // 텍스트 문제
        textQuestion2 = findViewById(R.id.textQuestion2);   // 텍스트 문제
        textQuestion3 = findViewById(R.id.textQuestion3);   // 텍스트 문제
        textQuestion4 = findViewById(R.id.textQuestion4);   // 텍스트 문제
        // -------------------- IMAGE MODE --------------------------------------------
        imageRadioGroup1 = findViewById(R.id.imageRadioGroup1);     // 이미지 라디오 그룹
        imageRadioGroup2 = findViewById(R.id.imageRadioGroup2);     // 이미지 라디오 그룹
        imageAnswerNumber1 = findViewById(R.id.imageAnswerNumber1);
        imageAnswerNumber1.setOnClickListener(listener);
        imageAnswerNumber2 = findViewById(R.id.imageAnswerNumber2);
        imageAnswerNumber2.setOnClickListener(listener);
        imageAnswerNumber3 = findViewById(R.id.imageAnswerNumber3);
        imageAnswerNumber3.setOnClickListener(listener);
        imageAnswerNumber4 = findViewById(R.id.imageAnswerNumber4);
        imageAnswerNumber4.setOnClickListener(listener);
        imageAnswerImage1 = findViewById(R.id.imageAnswerImage1);
        imageAnswerImage1.setOnClickListener(listener);
        imageAnswerImage2 = findViewById(R.id.imageAnswerImage2);
        imageAnswerImage2.setOnClickListener(listener);
        imageAnswerImage3 = findViewById(R.id.imageAnswerImage3);
        imageAnswerImage3.setOnClickListener(listener);
        imageAnswerImage4 = findViewById(R.id.imageAnswerImage4);
        imageAnswerImage4.setOnClickListener(listener);
        // ----------------------------------------------------------------------

        dbHelper = new QuestionDBHelper(this, "DB", null, 1);
        qid = getIntent().getIntExtra("qid", -1);
        // 불러오기
        if(qid > -1){
            question = dbHelper.get(qid);
            try{
                if(question.getType().equals(QuestionBean.TYPE_TEXT)){
                imageRadioLayout.setVisibility(View.GONE);
                textRadioLayout.setVisibility(View.VISIBLE);
                textQuestion1.setText(question.getEx1());
                textQuestion2.setText(question.getEx2());
                textQuestion3.setText(question.getEx3());
                textQuestion4.setText(question.getEx4());
                } else if (question.getType().equals(QuestionBean.TYPE_IMAGE)){
                    imageRadioLayout.setVisibility(View.VISIBLE);
                    textRadioLayout.setVisibility(View.GONE);
                    imageAnswerImage1.setImageURI(Uri.parse(question.getEx1()));
                    imageAnswerImage2.setImageURI(Uri.parse(question.getEx2()));
                    imageAnswerImage3.setImageURI(Uri.parse(question.getEx3()));
                    imageAnswerImage4.setImageURI(Uri.parse(question.getEx4()));
                }
                questionEditText.setText(question.getQuestion());                   // 문제
                scoreEditText.setText(String.valueOf(question.getScore()));         // 배점
                answer = question.getAnswer();
                setSaveAnswer(answer);                                              // 적용
            }catch(Exception e){
                Toast.makeText(this,"불러오기 에러",Toast.LENGTH_SHORT);
                return;
            };
        } else {
            question = new QuestionBean();
            textRadioLayout.setVisibility(View.VISIBLE);
            question.setType(QuestionBean.TYPE_TEXT); // "text"
        }
    }

    private View.OnClickListener listener = v -> {
        if(v.getId() == R.id.saveButton){
            // Save
            try{
                if(question.getType().equals(QuestionBean.TYPE_TEXT)){
                question.setEx1(String.valueOf(textQuestion1.getText()));
                question.setEx2(String.valueOf(textQuestion2.getText()));
                question.setEx3(String.valueOf(textQuestion3.getText()));
                question.setEx4(String.valueOf(textQuestion4.getText())); }
                question.setQuestion(String.valueOf(questionEditText.getText()));               // 문제
                question.setScore(Integer.parseInt(String.valueOf(scoreEditText.getText())));   // 배점
            }catch(Exception e){
                if(String.valueOf(questionEditText.getText()).equals("")) {
                    Toast.makeText(this, "문제를 입력해 주세요", Toast.LENGTH_SHORT).show();
                } else if(String.valueOf(scoreEditText.getText()).equals("")) {
                    Toast.makeText(this, "배점을 입력해 주세요", Toast.LENGTH_SHORT).show();
                }
                return;
            };
            if(getSaveAnswer() == 0) {
                Toast.makeText(this, "정답을 선택해 주세요", Toast.LENGTH_SHORT).show();
                return;
            }
            question.setAnswer(getSaveAnswer());                                            // 답
            setSaveAnswer(question.getAnswer());
            if(modeToggleButton.isChecked() == true){
                if(textQuestion1.getText().toString().equals("") || textQuestion2.getText().toString().equals("")
                        || textQuestion3.getText().toString().equals("") || textQuestion4.getText().toString().equals("") ){
                    Toast.makeText(this,"정답을 입력해 주세요",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            if(modeToggleButton.isChecked() == false){
            }
            if(qid > -1) {
                dbHelper.update(question);
                Toast.makeText(this,"수정 성공",Toast.LENGTH_SHORT).show();
            } else {
                dbHelper.insert(question);
                Toast.makeText(this,"입력 성공",Toast.LENGTH_SHORT).show();
            }
            setResult(RESULT_OK);
            finish();
        } else if(v.getId() == R.id.deleteButton){
            // Delete
            if(qid != -1) { // getIntent().getIntExtra 디폴트 -1
                Toast.makeText(this,"삭제 성공",Toast.LENGTH_SHORT).show();
                dbHelper.delete(qid);
                setResult(RESULT_OK);
            }
            finish();
        }
        // ㅜㅜ 줄일 수 있는 방법을 생각해보자
        else if(v.getId() == R.id.imageAnswerImage1){
            reqInt = REQ_1;
            getImage();
        } else if(v.getId() == R.id.imageAnswerImage2){
            reqInt = REQ_2;
            getImage();
        } else if(v.getId() == R.id.imageAnswerImage3){
            reqInt = REQ_3;
            getImage();
        } else if(v.getId() == R.id.imageAnswerImage4){
            reqInt = REQ_4;
            getImage();
        } else if(v.getId() == R.id.imageAnswerNumber1){
            imageRadioGroup2.clearCheck();
        }else if(v.getId() == R.id.imageAnswerNumber2){
            imageRadioGroup2.clearCheck();
        }else if(v.getId() == R.id.imageAnswerNumber3){
            imageRadioGroup1.clearCheck();
        }else if(v.getId() == R.id.imageAnswerNumber4){
            imageRadioGroup1.clearCheck();
        }
    };

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(buttonView.getId() == R.id.modeToggleButton){
            if(isChecked){
                imageRadioLayout.setVisibility(View.GONE);
                textRadioLayout.setVisibility(View.VISIBLE);
                question = new QuestionBean();
                question.setType(QuestionBean.TYPE_TEXT);
            } else {
                imageRadioLayout.setVisibility(View.VISIBLE);
                textRadioLayout.setVisibility(View.GONE);
                question = new QuestionBean();
                question.setType(QuestionBean.TYPE_IMAGE);
            }
        }
    }

    public void setSaveAnswer(int answer){
        int visibility = textRadioLayout.getVisibility();
        if(visibility == View.VISIBLE){
            textRadioGroup.clearCheck();
            if(answer == 1)
                textAnswerNumber1.setChecked(true);
            else if(answer == 2)
                textAnswerNumber2.setChecked(true);
            else if(answer == 3)
                textAnswerNumber3.setChecked(true);
            else if(answer == 4)
                textAnswerNumber4.setChecked(true);
        }else {
            imageRadioGroup1.clearCheck();
            imageRadioGroup2.clearCheck();
            if(answer == 1)
                imageAnswerNumber1.setChecked(true);
            else if(answer == 2)
                imageAnswerNumber2.setChecked(true);
            else if(answer == 3)
                imageAnswerNumber3.setChecked(true);
            else if(answer == 4)
                imageAnswerNumber4.setChecked(true);
        }
    }

    public int getSaveAnswer(){
        int visibility = textRadioLayout.getVisibility();
        if(visibility == View.VISIBLE) {
            if (textAnswerNumber1.isChecked())
                answer = 1;
            else if (textAnswerNumber2.isChecked())
                answer = 2;
            else if (textAnswerNumber3.isChecked())
                answer = 3;
            else if (textAnswerNumber4.isChecked())
                answer = 4;
            textRadioGroup.clearCheck();
        }else {
            if (imageAnswerNumber1.isChecked())
                answer = 1;
            else if (imageAnswerNumber2.isChecked())
                answer = 2;
            else if (imageAnswerNumber3.isChecked())
                answer = 3;
            else if (imageAnswerNumber4.isChecked())
                answer = 4;
            imageRadioGroup1.clearCheck();
            imageRadioGroup2.clearCheck();
        }
        return answer;
    }

    public void getImage(){
        Intent image = new Intent();
//        image.setAction(Intent.ACTION_GET_CONTENT);
//        image.setType("image/*");
        image.setAction(Intent.ACTION_PICK);
        image.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
        startActivityForResult(image, reqInt);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ_1 || requestCode == REQ_2 || requestCode == REQ_3 || requestCode == REQ_4) {
            if (resultCode == RESULT_OK && data != null && data.getData() != null) {
                try {
                    Uri imgUri = data.getData();
                    if (reqInt == REQ_1) {
                        imageAnswerImage1.setImageURI(imgUri);
                        question.setEx1(imgUri.toString());
                    } else if (reqInt == REQ_2) {
                        imageAnswerImage2.setImageURI(imgUri);
                        question.setEx2(imgUri.toString());
                    } else if (reqInt == REQ_3) {
                        imageAnswerImage3.setImageURI(imgUri);
                        question.setEx3(imgUri.toString());
                    } else if (reqInt == REQ_4) {
                        imageAnswerImage4.setImageURI(imgUri);
                        question.setEx4(imgUri.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
