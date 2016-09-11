package coinToss;

/**
 * Driver class for coin tossing
 */
public class Driver {
    
    /**
     * The simulation of coin toss
     * @param args command line args
     */
    public static void main(String[] args) {
        Coin coin = new Coin();
        int headCnt = 0;    //count head
        int tailCnt = 0;    //count tail

        // test 1:
        System.out.println("Coin toss test 1: toss 20 times\n");
        
        // display initial face up
        System.out.println("Initial side up: "+coin.getSideUp());

        // toss 20 times
        for (int i = 0; i < 20; i++) {
            coin.toss();
            String sideUp = coin.getSideUp();
            System.out.println("Iteration "+(i+1)+" : "+sideUp);
            if(sideUp.equals("heads")){
                ++headCnt;
            }else{
                ++tailCnt;
            }
        }

        System.out.println("Totally "+headCnt+" heads, "+tailCnt+" tails\n");
        
        // test 2:
        System.out.println("Coin toss test 2: toss 7 times\n");
        
        headCnt = 0;    //count head
        tailCnt = 0;    //count tail

        for (int i = 0; i < 7; i++) {
            coin.toss();
            String sideUp = coin.getSideUp();
            System.out.println("Iteration "+(i+1)+" : "+sideUp);
            if(sideUp.equals("heads")){
                ++headCnt;
            }else{
                ++tailCnt;
            }
        }

        System.out.println("Totally "+headCnt+" heads, "+tailCnt+" tails\n");
        
        // test 3:
        System.out.println("Coint toss test 3: toss 4 times");
        
        headCnt = 0;    //count head
        tailCnt = 0;    //count tail

        for (int i = 0; i < 4; i++) {
            coin.toss();
            String sideUp = coin.getSideUp();
            System.out.println("Iteration "+(i+1)+" : "+sideUp);
            if(sideUp.equals("heads")){
                ++headCnt;
            }else{
                ++tailCnt;
            }
        }

        System.out.println("Totally "+headCnt+" heads, "+tailCnt+" tails\n");
    }
}
