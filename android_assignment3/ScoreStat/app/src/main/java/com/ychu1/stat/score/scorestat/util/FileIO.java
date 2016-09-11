package com.ychu1.stat.score.scorestat.util;

import com.ychu1.stat.score.scorestat.model.Student;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 */
public class FileIO {

    public static List<Student> readFile(InputStream ins) throws IOException {
        List<Student> result = new ArrayList<Student>();

        BufferedReader buff = new BufferedReader(new InputStreamReader(ins));
        String line = buff.readLine(); // the first line of titles

        line = buff.readLine(); // the first info line
        while (line != null) {
            StringTokenizer st = new StringTokenizer(line);
            // the 4-digit SID
            String id = st.nextToken();

            // read 5 quiz scores
            String[] tempScores = new String[5];
            int i = 0;
            while (st.hasMoreTokens()) {
                tempScores[i++] = st.nextToken();
            }

            Student stu = new Student(id,tempScores);
            result.add(stu);

            line = buff.readLine();
        }
        buff.close();

        return result;
    }


}
