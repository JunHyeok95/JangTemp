package com.example.question;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class QuestionListActivity extends AppCompatActivity implements ItemClickListener {

    private static final String PASSWORD = "0000";

    private ConstraintLayout frontLayout, backLayout;
    private EditText passwordEditText;

    private QuestionDBHelper dbHelper;
    private RecyclerView listView;
    private QuestionAdapter adapter;
    private ArrayList<QuestionBean> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);

        frontLayout = findViewById(R.id.frontLayout);   // 로그인
        backLayout = findViewById(R.id.backLayout);     // 리스트
        passwordEditText = findViewById(R.id.passwordEditText); // 비밀번호

        findViewById(R.id.addButton).setOnClickListener(listener);      // 리스트 추가 버튼
        findViewById(R.id.loginButton).setOnClickListener(listener);    // 로그인 버튼

    }

    private View.OnClickListener listener = v -> {
        // 로그인 버튼 클릭
        if(v.getId() == R.id.loginButton){
            String passwd = String.valueOf(passwordEditText.getText());
            if(passwd.equals(PASSWORD)){
                frontLayout.setVisibility(View.GONE);
                backLayout.setVisibility(View.VISIBLE);

                dbHelper = new QuestionDBHelper(this,"DB",null,1);
                data = dbHelper.get();

                adapter = new QuestionAdapter(data, this);
                LinearLayoutManager manager = new LinearLayoutManager(this);
                listView = findViewById(R.id.QuestionList);
                listView.setAdapter(adapter);
                listView.setLayoutManager(manager);

            } else {
                Toast.makeText(this,"비밀번호 오류",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        // 추가하기 버튼 클릭 Go !!
        else if(v.getId() == R.id.addButton){
            Intent intent = new Intent(this, QuestionActivity.class);
            startActivityForResult(intent, 1);
        }
    };

    @Override
    public void onItemClickListener(View v, int index) {
        Intent intent = new Intent(this, QuestionActivity.class);
        intent.putExtra("qid", data.get(index).getQid());
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){ // 성공적 -> 화면 최신화
            this.data = dbHelper.get();
            adapter.notifyDataSetChanged();
        }
    }
}
