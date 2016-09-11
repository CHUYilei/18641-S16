package com.ychu1.stat.score.scorestat.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * QuizStatistics - subclass of Statistics find low,average,high in all students
 * for all quizzes
 */
public class QuizStatistics {
    private int quizNum = 5; // number of quizzes in total (5 default)
    private double lowscores[],highscores[],avgscores[];
    private static List<double[]> scoreList;

    /**
     * Constructor
     * @param quizNum
     *            number of quizzes in total (5 default)
     */
    public QuizStatistics(int quizNum) {
        lowscores = new double[quizNum];
        highscores = new double[quizNum];
        avgscores = new double[quizNum];
        this.quizNum = quizNum;

        if(scoreList == null) scoreList = new ArrayList<double[]>();
    }

    public void addStudentToList(Student student){
        double[] temp = student.getScores();
        scoreList.add(temp);
    }

    public void calculate(){
        if(scoreList == null || scoreList.isEmpty()){
            return;
        }

        findlow();
        findhigh();
        findavg();
    }

    /**
     * Find the lowest score and store it in an array named low scores.
     */
    private void findlow() {
        Arrays.fill(lowscores, Double.MAX_VALUE);

        for(double[] scores: scoreList){
            for(int i=0;i<quizNum;++i){
                if(scores[i] < lowscores[i]){
                    lowscores[i] = scores[i];
                }
            }
        }
    }

    /**
     * Find the highest score and store it in an array named high scores
     *
     */
    private void findhigh() {
        Arrays.fill(highscores,Double.MIN_VALUE);

        for(double[] scores: scoreList){
            for(int i=0;i<quizNum;++i){
                if(scores[i] > highscores[i]){
                    highscores[i] = scores[i];
                }
            }
        }
    }

    /**
     * Find average score for each quiz and store it in an array named avg
     * scores
     */
    private void findavg() {
        Arrays.fill(avgscores,0.0);
        for(double[] scores: scoreList){
            for(int i=0;i<quizNum;++i){
                avgscores[i] += scores[i];
            }
        }

        int cnt = scoreList.size();
        for(int i=0;i<quizNum;++i){
            avgscores[i] /= (double)cnt;
        }
    }

    public String[] getLowscores() {
        String[] result = new String[this.quizNum];
        for (int i = 0; i < 5; i++) {
            result[i] = String.format( "%.2f", this.lowscores[i]);
        }

        return result;
    }

    public String[] getHighscores() {
        String[] result = new String[this.quizNum];
        for (int i = 0; i < 5; i++) {
            result[i] = String.format( "%.2f", this.highscores[i]);
        }

        return result;
    }

    public String[] getAvgscores() {
        String[] result = new String[this.quizNum];
        for (int i = 0; i < 5; i++) {
            result[i] = String.format( "%.2f", this.avgscores[i]);
        }

        return result;
    }

    public void clearList(){
        scoreList.clear();
    }
}
