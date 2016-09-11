package coinToss;

/**
 * Coin -
 * Provide the representation of the side up
 * Allow tossing randomly with 50% probability
 */
public class Coin {
    private String sideUp;

    /**
     * Initialize the coin by tossing it for the first time
     */
    public Coin(){
        toss();
    }

    /**
     * Toss the coin randomly
     */
    public void toss(){
        // Math.random() creates result in [0,1]
        if(Math.random() < 0.5) {
            this.sideUp = "heads";
        }else{
            this.sideUp = "tails";
        }
    }

    /**
     * Getter for the sideUp
     * @return sideUp's content
     */
    public String getSideUp(){
        return this.sideUp;
    }
}

