package com.example.question;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String START_EASY = "EASY";
    public static final String START_HARD = "HARD";

    private RadioGroup group;
    private RadioButton easy, hard;
    private TextView mode;
    private String level;

    private int permission;
    private static final int REQ_READ_PERMISSION_SET = 200;
    private static final int REQ_READ_PERMISSION_START = 201;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        easy = findViewById(R.id.easyRadioButton);
        easy.setOnClickListener(listener);  // setText !

        hard = findViewById(R.id.hardRadioButton);
        hard.setOnClickListener(listener);  // setText !

        group = findViewById(R.id.radioGroup);
        mode = findViewById(R.id.modeTextView);

        findViewById(R.id.startButton).setOnClickListener(listener);    // 시작
        findViewById(R.id.setButton).setOnClickListener(listener);      // 설정

    }

    private View.OnClickListener listener = v -> {
        // 모드 체크
        if(v.getId() == R.id.startButton){
            if(easy.isChecked())
                level = START_EASY;
            else if(hard.isChecked())
                level = START_HARD;
            else{
                Toast.makeText(MainActivity.this,"모드를 선택해주세요",Toast.LENGTH_SHORT).show();
                return;
            }
            if(Build.VERSION.SDK_INT >= 23){
                int permission = ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE);
                if(permission != PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQ_READ_PERMISSION_START);
                    return;
                }
            }
            Intent i = new Intent(this, QuizActivity.class);
            i.putExtra("level",level);
            level = "";
            group.clearCheck(); // 라디오 그룹 클리어
            startActivity(i);
        }
        // 설정
        else if (v.getId() == R.id.setButton){
            if(Build.VERSION.SDK_INT >= 23){
                int permission = ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE);
                if(permission != PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQ_READ_PERMISSION_SET);
                    return;
                }
            }
            startActivity(new Intent(this, QuestionListActivity.class));
        }
        // 버튼 클릭
        else if (v.getId() == R.id.easyRadioButton || v.getId() == R.id.hardRadioButton){
            if(v.getId() == R.id.easyRadioButton)
                mode.setText("모든 문제가 객관식으로 출제됩니다");
            else
                mode.setText("객관식과 주관식이 출제됩니다");
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults); // ??
        if(requestCode != REQ_READ_PERMISSION_START && requestCode != REQ_READ_PERMISSION_SET ) return;
        if(requestCode == REQ_READ_PERMISSION_START
                && permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE)
                && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Intent i = new Intent(this, QuizActivity.class);
            i.putExtra("level",level);
            level = "";
            group.clearCheck(); // 라디오 그룹 클리어
            startActivity(i);
        } else if(requestCode == REQ_READ_PERMISSION_SET
                && permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE)
                && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            startActivity(new Intent(this, QuestionListActivity.class));
        } else
            Toast.makeText(this,"앨범 권한이 없습니다.", Toast.LENGTH_SHORT).show();
    }
}
