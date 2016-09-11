package model;

import lab2.Student;

import java.util.Arrays;

/**
 * QuizStatistics - subclass of Statistics find low,average,high in all students
 * for all quizzes
 */
public class QuizStatistics extends Statistics {
    private int quizNum = 5; // number of quizzes in total (5 default)

    /**
     * Constructor
     * 
     * @param quizNum
     *            number of quizzes in total (5 default)
     */
    public QuizStatistics(int quizNum) {
        lowscores = new int[quizNum];
        highscores = new int[quizNum];
        avgscores = new float[quizNum];
        this.quizNum = quizNum;
    }

    /**
     * Find the lowest score and store it in an array named low scores.
     * 
     * @param students
     *            student array
     */
    public void findlow(Student[] students) {
        Arrays.fill(lowscores, Integer.MAX_VALUE);

        for (Student stu : students) {
            if (stu != null) {
                int[] scores = stu.getScores();
                for (int i = 0; i < quizNum; i++) {
                    if (scores[i] < lowscores[i]) {
                        lowscores[i] = scores[i];
                    }
                }
            } else {
                break;
            }
        }

        // in case no student, recover low scores to 0
        for (int i = 0; i < quizNum; i++) {
            if (lowscores[i] == Integer.MAX_VALUE) {
                lowscores[i] = 0;
            }
        }
    }

    /**
     * Find the highest score and store it in an array named high scores
     * 
     * @param students
     *            student array
     */
    public void findhigh(Student[] students) {
        Arrays.fill(highscores, Integer.MIN_VALUE);

        for (Student stu : students) {
            if (stu != null) {
                int[] scores = stu.getScores();
                for (int i = 0; i < quizNum; i++) {
                    if (scores[i] > highscores[i]) {
                        highscores[i] = scores[i];
                    }
                }
            } else {
                break;
            }
        }

        // in case no student, recover high scores to 0
        for (int i = 0; i < quizNum; i++) {
            if (highscores[i] == Integer.MAX_VALUE) {
                highscores[i] = 0;
            }
        }
    }

    /**
     * Find average score for each quiz and store it in an array named avg
     * scores
     * 
     * @param students
     *            student array
     */
    public void findavg(Student[] students) {
        Arrays.fill(avgscores, 0f);

        int cnt = 0;
        for (Student stu : students) {
            if (stu != null) {
                int[] scores = stu.getScores();
                for (int i = 0; i < quizNum; i++) {
                    avgscores[i] += scores[i];
                }
            } else {
                break;
            }
            ++cnt;
        }

        for (int i = 0; i < quizNum; i++) {
            avgscores[i] = avgscores[i] / (float) cnt;
        }
    }

    /**
     * Print out to console the content of high score, low score, average arrays
     */
    public void printStatistics() {
        System.out.print(String.format("%-12s", "High Score"));

        for (int i = 0; i < quizNum; i++) {
            System.out.print(String.format("%-12d", highscores[i]));
        }
        System.out.println();

        System.out.print(String.format("%-12s", "Low Score"));

        for (int i = 0; i < quizNum; i++) {
            System.out.print(String.format("%-12d", lowscores[i]));
        }
        System.out.println();

        System.out.print(String.format("%-12s", "Average"));

        // round to 1 decimal places
        for (int i = 0; i < quizNum; i++) {
            System.out.print(String.format("%-12.1f", avgscores[i]));
        }
        System.out.println("\n\n");
    }

}
