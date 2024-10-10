package org.example;

public class Application {
    // Member
    InputDevice inputDevice;
    OutputDevice outputDevice;

    // Constructor
    public Application(InputDevice inputDevice, OutputDevice outputDevice) {
        this.inputDevice = inputDevice;
        this.outputDevice = outputDevice;
    }

    void run() {
        this.outputDevice.writeMessage("\n\n\nThe game has started.\n");
        playGame();
    }
    public void playGame () {
        int H = 0;  // Higher number
        int S = 0;  // Smaller number
        int i = 1; // Round Counter
        int ScoreS = 0;// Scores of S is initiated.
        int ScoreH = 0;// Scores of H is initiated.

        while (ScoreH < 5 && ScoreS < 5) {
            outputDevice.writeMessage("<< Round: " + i + " >>");

            int n1 = inputDevice.nextInt();  // Player 1's number
            int n2 = inputDevice.nextInt();  // Player 2's number

            // Compare the two numbers and assign H and S accordingly
            if (n1 > n2) {
                H = n1;
                S = n2;
            } else {
                H = n2;
                S = n1;
            }
            // Now H and S values are newly generated.

            // Print the current Values of H and S
            this.outputDevice.currentHValue(H);
            this.outputDevice.currentSValue(S);

            // If H is a multiple of S and S is greater than or equal to 10, increment S's score by 1
            if (H % S == 0 && S >= 10) {
                ScoreS++;
                outputDevice.writeMessage("S gets 1 point.");
            }
            // If H equals S (both players picked the same number), increment both H and S by 2 points
            else if (H == S) {
                ScoreH += 2;
                ScoreS += 2;
                outputDevice.writeMessage("H and S are the same. Both get 2 points.");
            }
            else {
                ScoreH += 1;
                outputDevice.writeMessage("H gets 1 point.");
            }
            this.outputDevice.currentScore(ScoreH, ScoreS);
            i++;
        }

        // Announce the winner
        outputDevice.writeMessage("\n<< The final announcement >>");
        if (ScoreH >= 5) {
            this.outputDevice.HWins();
        } else {
            this.outputDevice.SWins();
        }
    }
}
