package com.example.question;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;

public class QuestionDBHelper extends SQLiteOpenHelper {

    private ArrayList<QuestionBean> data;

    public QuestionDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        data = new ArrayList<>();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 제약조건 생각하자
        String sql = "create table question(qid integer primary key autoincrement, score integer, answer integer," +
                "question text, type text, ex1 text, ex2 text, ex3 text, ex4 text, time integer)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 임시방편
        String sql = "drop table question";
        db.execSQL(sql);
        onCreate(db);
    }

    //  Bean_question table

    public long insert(QuestionBean question){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("score", question.getScore());
        values.put("answer", question.getAnswer());
        values.put("question", question.getQuestion());
        values.put("type", question.getType());
        values.put("ex1", question.getEx1());
        values.put("ex2", question.getEx2());
        values.put("ex3", question.getEx3());
        values.put("ex4", question.getEx4());
        values.put("time", System.currentTimeMillis());
        return db.insert("question", null, values);
    }

    public int update(QuestionBean question){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("score", question.getScore());
        values.put("answer", question.getAnswer());
        values.put("question", question.getQuestion());
        values.put("type", question.getType());
        values.put("ex1", question.getEx1());
        values.put("ex2", question.getEx2());
        values.put("ex3", question.getEx3());
        values.put("ex4", question.getEx4());
        values.put("time", System.currentTimeMillis());
        String idStr = String.valueOf(question.getQid());
        return db.update("question", values, "qid=?", new String[] {idStr});
    }

    public int delete(int qid){
        SQLiteDatabase db = getWritableDatabase();
        String idStr = String.valueOf(qid);
        return db.delete("question", "qid=?", new String[] {idStr});
    }

    public QuestionBean get(int qid){
        SQLiteDatabase db = getReadableDatabase();
        String idStr = String.valueOf(qid);
        Log.i("myTest",idStr);
        Cursor c = db.query("question", null, "qid=?", new String[] {idStr}, null, null, null);
        if(c.moveToNext()){
            QuestionBean question = new QuestionBean();
            question.setQid(c.getInt(c.getColumnIndex("qid")));
            question.setScore(c.getInt(c.getColumnIndex("score")));
            question.setAnswer(c.getInt(c.getColumnIndex("answer")));
            question.setQuestion(c.getString(c.getColumnIndex("question")));
            question.setType(c.getString(c.getColumnIndex("type")));
            question.setEx1(c.getString(c.getColumnIndex("ex1")));
            question.setEx2(c.getString(c.getColumnIndex("ex2")));
            question.setEx3(c.getString(c.getColumnIndex("ex3")));
            question.setEx4(c.getString(c.getColumnIndex("ex4")));
            question.setTime(c.getLong(c.getColumnIndex("time")));
            return question;
        } else {
            return null;
        }
    }

    public ArrayList<QuestionBean> get(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query("question", null, null, null, null, null, "time desc");
        data.clear();
        while (c.moveToNext()){
            QuestionBean question = new QuestionBean();
            question.setQid(c.getInt(c.getColumnIndex("qid")));
            question.setScore(c.getInt(c.getColumnIndex("score")));
            question.setAnswer(c.getInt(c.getColumnIndex("answer")));
            question.setQuestion(c.getString(c.getColumnIndex("question")));
            question.setType(c.getString(c.getColumnIndex("type")));
            question.setEx1(c.getString(c.getColumnIndex("ex1")));
            question.setEx2(c.getString(c.getColumnIndex("ex2")));
            question.setEx3(c.getString(c.getColumnIndex("ex3")));
            question.setEx4(c.getString(c.getColumnIndex("ex4")));
            question.setTime(c.getLong(c.getColumnIndex("time")));
            data.add(question);
        }
        return data;
    }
}
