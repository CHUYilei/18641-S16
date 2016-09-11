package lab2;

/**
 * Student - contain identification and quiz scores
 */
public class Student {
    private int SID;
    private int scores[] = new int[5];

    /**
     * Getter
     * 
     * @return sid sid
     */
    public int getSID() {
        return SID;
    }

    /**
     * Setter
     * 
     * @param SID
     *            sid
     */
    public void setSID(int SID) {
        this.SID = SID;
    }

    /**
     * Getter
     * 
     * @return quiz scores in an array
     */
    public int[] getScores() {
        return scores;
    }

    /**
     * Setter
     * 
     * @param scores
     *            quiz scores in an array
     */
    public void setScores(int[] scores) {
        this.scores = scores;
    }

    /**
     * Combine all attributes into a string for print
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(SID).append(" ");

        for (int score : scores) {
            sb.append(score).append(" ");
        }

        return sb.toString();
    }

    /**
     * print student's all information
     */
    public void printStudent() {
        System.out.println(this.toString());
    }
}
