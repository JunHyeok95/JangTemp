package com.example.question;

public class QuestionBean {

    public static final String TYPE_TEXT = "TEXT";
    public static final String TYPE_IMAGE = "IMAGE";

    private int qid, score, answer;
    private long time;

    private String question, type, ex1, ex2, ex3, ex4;

    public int getQid() {
        return qid;
    }

    public int getScore() {
        return score;
    }

    public int getAnswer() {
        return answer;
    }

    public long getTime() {
        return time;
    }

    public String getQuestion() {
        return question;
    }

    public String getType() {
        return type;
    }

    public String getEx1() {
        return ex1;
    }

    public String getEx2() {
        return ex2;
    }

    public String getEx3() {
        return ex3;
    }

    public String getEx4() {
        return ex4;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setEx1(String ex1) {
        this.ex1 = ex1;
    }

    public void setEx2(String ex2) {
        this.ex2 = ex2;
    }

    public void setEx3(String ex3) {
        this.ex3 = ex3;
    }

    public void setEx4(String ex4) {
        this.ex4 = ex4;
    }


}
