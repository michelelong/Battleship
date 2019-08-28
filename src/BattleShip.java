import java.util.Random;
import java.util.Scanner;

public class BattleShip {
    private static char[][] playerBoard = new char[10][10];
    private static char[][] npcBoard = new char[10][10];
    private static int playerShips = 5;
    private static int npcShips = 5;

    public static void main(String[] args) {
        intro();
        displayOcean();
        setupUserShips();
        setupNPCShips();

        while (playerShips > 0 && npcShips > 0){
            playerTurn();
            npcTurn();
            displayOcean();
            displayShipCount();
        }

        if(playerShips == 0){
            System.out.println("All of your ships are sunk! You lose!");
        } else if(npcShips == 0){
            System.out.println("You sank all of the computers ships! You win!");
        }
    }

    private static void intro(){
        System.out.println("*** Let's play Battle Ship! ***\n");
        System.out.println("Q to quit or ? for help, at any time.\n");
    }

    private static void displayOcean(){
        System.out.println();
        System.out.println("        You" + "                   Computer");
        System.out.println("     ----------" + "              ----------");
        for (int i = 0; i < playerBoard.length; i++){
                System.out.print("   " + i + "|");
            for (int j = 0; j < playerBoard[i].length; j++){
                if (playerBoard[i][j] == '\u0000') {
                    System.out.print(" ");
                } else {
                    System.out.print(playerBoard[i][j]);
                }
            }
            System.out.print("|" + i + "          " + i + "|");
            for (int k = 0; k < npcBoard[i].length; k++){
                if(npcBoard[i][k] == '@' || npcBoard[i][k] == '\u0000'){
                    System.out.print(" ");
                } else {
                    System.out.print(npcBoard[i][k]);
                }
            }
            System.out.println("|" + i);
        }
        System.out.println("     ----------" + "              ----------");
        System.out.println("     0123456789" + "              0123456789");
    }

    private static void displayHelp(){
        System.out.println("\n------HELP-------");
        System.out.println("Map Left: Your ships and the computer's attacks.\n");
        System.out.println("Map Right: Your attacks and the computer's ships.\n");
        System.out.println("Coordinates: X is left to right and Y is top to bottom.\n");
        System.out.println("Symbols: @ are your ships, - is a miss, and ! is a hit.");
        System.out.println("-----------------\n");
    }

    private static void displayShipCount(){
        System.out.println("\nYour ships: " + playerShips + " | Computer ships: " + npcShips + "\n");
    }

    private static boolean validateInput(String coord){
        return coord.matches("[0-9]");
    }

    private static void setupUserShips(){
        System.out.println("\nStart by placing your ships on the battlefield.");
        Scanner input = new Scanner(System.in);
        int shipCount = 1;

        while (shipCount <= 5){
            System.out.print("Enter X coordinate for ship #" + shipCount + ": ");
            String xStr = input.next();
            if (xStr.equalsIgnoreCase("Q")){
                System.exit(0);
            } else if (xStr.equals("?")){
                displayHelp();
                continue;
            }
            System.out.print("Enter Y coordinate for ship #" + shipCount + ": ");
            String yStr = input.next();
            if (yStr.equalsIgnoreCase("Q")){
                System.exit(0);
            } else if (yStr.equals("?")){
                displayHelp();
                continue;
            }

            if(validateInput(xStr) && validateInput(yStr)) {
                int x = Integer.parseInt(xStr);
                int y = Integer.parseInt(yStr);

                if (playerBoard[x][y] == '\u0000') {
                    playerBoard[x][y] = '@';
                    shipCount++;
                    displayOcean();
                } else if (playerBoard[x][y] == '@') {
                    System.out.println("That location is occupied. Try again.");
                } else {
                    System.out.println("Coordinates must be 0-9. Try again.");
                }
            } else {
                System.out.println("Invalid coordinates. Try again.");
            }
        }
    }

        private static void setupNPCShips(){
            System.out.println("\nPlease, wait while the computer deploys ships.");
            int shipCountNPC = 1;

            while (shipCountNPC <= 5){
                Random rand = new Random();
                int npcX = rand.nextInt(10);
                int npcY = rand.nextInt(10);
                if(npcBoard[npcX][npcY] == '\u0000'){
                    npcBoard[npcX][npcY] = '@';
                    System.out.println("Computer ship #" + shipCountNPC + " deployed.");
                    shipCountNPC++;
                }
            }
                System.out.println();
        }

        private static void playerTurn() {
            boolean launch = false;
            Scanner guess = new Scanner(System.in);
            String xStr;
            String yStr;

            while (!launch) {
                System.out.print("Enter X coordinate for attack: ");
                xStr = guess.next();
                if (xStr.equalsIgnoreCase("Q")){
                    System.exit(0);
                } else if (xStr.equals("?")){
                    displayHelp();
                    continue;
                }
                System.out.print("Enter Y coordinate for attack: ");
                yStr = guess.next();
                if (yStr.equalsIgnoreCase("Q")){
                    System.exit(0);
                } else if (yStr.equals("?")){
                    displayHelp();
                    continue;
                }

                if (validateInput(xStr) && validateInput(yStr)) {
                    int x = Integer.parseInt(xStr);
                    int y = Integer.parseInt(yStr);

                   if(npcBoard[x][y] == '-' || npcBoard[x][y] == '!') {
                        System.out.println("That location has already been entered. Try again.");
                    } else if (npcBoard[x][y] == '@') {
                        System.out.println("\nYOU SANK THE COMPUTER'S SHIP!");
                        npcBoard[x][y] = '!';
                        npcShips--;
                        launch = true;
                    } else {
                        System.out.println("\nYou MISSED!");
                        npcBoard[x][y] = '-';
                       launch = true;
                    }
                } else {
                    System.out.println("Invalid coordinates. Try again.");
                }
            }
        }

    private static void npcTurn(){
        Random rand = new Random();
        int x = rand.nextInt(10);
        int y = rand.nextInt(10);
        System.out.println("Computer is launching an attack on X: " + x + " | Y: " + y + ".");

        while (playerBoard[x][y] == '-' || playerBoard[x][y] == '!'){
            x = rand.nextInt(10);
            y = rand.nextInt(10);
        }

        if(playerBoard[x][y] == '@'){
            System.out.println("BOOM! YOUR SHIP SUNK!");
            playerBoard[x][y] = '!';
            playerShips--;
        } else {
            System.out.println("The computer MISSED!");
            playerBoard[x][y] = '-';
        }
    }
}
