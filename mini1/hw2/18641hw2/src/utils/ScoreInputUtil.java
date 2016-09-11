package utils;

import exception.FileValidityException;
import lab2.Student;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.StringTokenizer;

public class ScoreInputUtil implements Util {
    /**
     * Reads the file and builds student array. Open the file using FileReader
     * Object. In a loop read a line using readLine method. Tokenize each line
     * using StringTokenizer Object Each token is converted from String to
     * Integer using parseInt method Value is then saved in the right property
     * of Student Object.
     * 
     * @param filename
     *            input file path
     * @param stu
     *            student array
     * @return filled student array
     */
    public Student[] readFile(String filename, Student[] stu) {
        try {
            // override the original students' data
            Arrays.fill(stu, null);

            FileReader file = new FileReader(filename);
            BufferedReader buff = new BufferedReader(file);
            int lineCnt = 0;
            String line = buff.readLine(); // the first line of titles

            line = buff.readLine(); // the first info line
            while (line != null && lineCnt < 40) {
                stu[lineCnt] = new Student();
                StringTokenizer st = new StringTokenizer(line);

                // read 4-digit SID
                stu[lineCnt].setSID(Integer.parseInt(st.nextToken()));

                // read 5 quiz scores
                int[] tempScores = new int[5];
                int i = 0;
                while (st.hasMoreTokens()) {
                    tempScores[i++] = Integer.parseInt(st.nextToken());
                }

                stu[lineCnt].setScores(tempScores);

                ++lineCnt;
                line = buff.readLine();
            }
            buff.close();

            // check whether exceeding 40 lines
            if (line != null) {
                throw new FileValidityException("Input file " + filename + " has more than 40 record!");
            }

        } catch (FileValidityException e) {
            e.printMessage();
        } catch (IOException e) {
            System.out.println("I/O error " + e.toString());
        }

        return stu;
    }
}
