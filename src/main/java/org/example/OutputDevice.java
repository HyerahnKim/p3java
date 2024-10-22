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

    public void printAvailableVersions(B productInfo, String name){
        if (productInfo.getMemberA().getName().equals(name)) {
            C[] versionArray = productInfo.getMemberCArray();
            // Print the name
            System.out.print("Available versions of " + name + ": ");

            // Print the versions
            for (int i = 0; i < versionArray.length; i++) {
                System.out.print(versionArray[i].gerVersion() + " ");
            }
        }
        else {
            System.out.print("Not found");
        }
    }
}
