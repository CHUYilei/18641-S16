package utils;

import lab2.Student;

/**
 * Util - Interface which defines the function of a utility class like read
 * input from file
 */
public interface Util {
    /**
     * Construct student array from input file
     * 
     * @param filename
     *            input file path
     * @param stu
     *            student array
     * @return filled student array
     */
    Student[] readFile(String filename, Student[] stu);
}
