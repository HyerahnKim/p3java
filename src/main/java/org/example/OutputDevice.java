package org.example;

public class OutputDevice {
    public void writeMessage(String mess){
        System.out.println(mess);
    }

    public void currentScore(int ScoreH, int ScoreS) {
        System.out.println("score H:" + ScoreH + "\nScore S: " + ScoreS);
    }

    public void currentHValue(int H){
        System.out.println("Value of H: " + H);
    }

    public void currentSValue(int S){
        System.out.println("Value of S: " + S );
    }

    public void HWins(){
        System.out.println("H is the winner.");
    }
    public void SWins(){
        System.out.println("S is the winner.");
    }
}
