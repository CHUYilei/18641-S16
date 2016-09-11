package driver;

import lab2.Student;
import model.QuizStatistics;
import utils.ScoreInputUtil;
import utils.Util;

/**
 * Driver class for student score analysis
 */
public class Driver {
    public static void main(String[] args) {
        Student[] lab2 = new Student[40];
        QuizStatistics statlab2 = new QuizStatistics(5);
        Util inputUtil = new ScoreInputUtil();

        // test case 1
        System.out.println("Test case 1: data1.txt");
        lab2 = inputUtil.readFile("data1.txt", lab2);
        statlab2.findlow(lab2);
        statlab2.findhigh(lab2);
        statlab2.findavg(lab2);

        statlab2.printStatistics();

        // test case 2
        System.out.println("Test case 2: data2.txt");
        lab2 = inputUtil.readFile("data2.txt", lab2);
        statlab2.findlow(lab2);
        statlab2.findhigh(lab2);
        statlab2.findavg(lab2);

        statlab2.printStatistics();

        // test case 3 (more than 40 records)
        System.out.println("Test case 3: data3.txt");
        lab2 = inputUtil.readFile("data3.txt", lab2);
        statlab2.findlow(lab2);
        statlab2.findhigh(lab2);
        statlab2.findavg(lab2);

        statlab2.printStatistics();
    }
}
