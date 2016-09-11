package com.ychu1.stat.score.scorestat.model;

/**
 *
 */
public class Student {
    private String id;
    private String[] scores;

    public Student(String id, String[] scores) {
        this.id = id;
        this.scores = scores;
    }


    public String getId() {
        return id;
    }

    public double[] getScores(){
        double[] temp = new double[5];
        for (int i = 0; i < 5; i++) {
            temp[i] = Double.parseDouble(this.scores[i]);
        }
        return temp;
    }
}
