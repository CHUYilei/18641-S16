package model;

import lab2.Student;

/**
 * Statistics - abstract class define arrays for storing statistical results
 */
public abstract class Statistics {
    protected int[] lowscores;
    protected int[] highscores;
    protected float[] avgscores;

    /**
     * Find the lowest score and store it in an array named low scores.
     * 
     * @param students
     *            student array
     */
    public abstract void findlow(Student[] students);

    /**
     * Find the highest score and store it in an array named high scores
     * 
     * @param students
     *            student array
     */
    public abstract void findhigh(Student[] students);

    /**
     * Find average score for each quiz and store it in an array named avg
     * scores
     * 
     * @param students
     *            student array
     */
    public abstract void findavg(Student[] students);
}
