package demo.n26.androidacademy.data.model;

/**
 Created by omkarpimple on 21.06.17.
 */

public class FloodProtectionStatus {

    private Login login;

    public class Login {

        private long delay;
        private int probability;
    }

    public long getDelay() {
        return login.delay;
    }

    public int getProbability() {
        return login.probability;
    }
}
