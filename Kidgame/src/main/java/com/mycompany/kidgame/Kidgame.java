/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.kidgame;

/**
 *
 * @author User
 */
import java.util.Scanner;
public class Kidgame{

 /*  public static void main(String[] args){
       Scanner input = new Scanner(System.in);
       Game game= new Game();
       System.out.println("How many questions?");
       int nQuestions= input.nextInt();
       long startTime = System.currentTimeMillis();
       for(int i=1; i <=nQuestions; i++){
         game.generateAddQuestions();
       }
       long endTime = System.currentTimeMillis();
       long totaltime = endTime - startTime;
       System.out.println("You took :"+totaltime/1000+"seconds");
       game.printSummary();
    }
}*/

public static void main(String[] args){
    Scanner input = new Scanner(System.in);
    Game game = new Game();
    
    System.out.println("====Math Learning Game====");
    System.out.println("1) Single Player");
    System.out.println("2) Multiplayer / Competition Mode");
    System.out.print("Choose mode: ");
    int mainChoice = input.nextInt();
    input.nextLine(); // consume newline
    
    if(mainChoice == 1){ // Single Player
        System.out.println("\n--- Single Player Mode ---");
        System.out.println("1) Make a Wish");
        System.out.println("2) No Mistakes");
        System.out.println("3) Take Chances (3 lives)");
        System.out.println("4) Time Trial");
        System.out.print("Choose a mode: ");
        int mode = input.nextInt();
    //System.out.println("====Math Learning Game====");
    /*System.out.println("(1) Make a Wish");
    System.out.println("(2) No Mistakes");
    System.out.println("(3) Take Chances [3 lives]");
    System.out.println("(4) Time Trial");
    System.out.print("Choose a mode: ");
    int mode = input.nextInt();*/

    switch(mode){// Make a Wish
        case 1: 
            System.out.print("How many questions? ");
            int n = input.nextInt();
            long startTime = System.currentTimeMillis();
            for(int i = 0; i < n; i++){
                game.askRandomQuestion();
            }
            long endTime =System.currentTimeMillis();
            long totalTime = (endTime - startTime) /1000;
            
            System.out.println("--Result:--");
            System.out.println("Time Taken:"+totalTime + "seconds");
            game.printSummary();
            break;


        case 2: // No Mistakes
            System.out.println("No mistakes Mode!!");
            System.out.println("!Warning:Game will stop when you get one answer wrong");
            long startTime2 = System.currentTimeMillis();
            
            boolean correct = true;
            while(correct){
                int prevScore = game.score;
                game.askRandomQuestion();
                if(game.score == prevScore){
                    correct = false; // user got one wrong, it cancels
                }
            }
            long endTime2 =System.currentTimeMillis();
            long totalTime2 = (endTime2 - startTime2) /1000;
            
            System.out.println("--Results:--");
            System.out.println("Time taken:"+totalTime2 + "seconds");
            game.printSummary();
                    
            break;

        case 3: // Take Chances (3 lives)
            System.out.println("Welcome to Chances Mode! You have 3 lives! use it wisely");
            int lives = 3;
            long startTime3 =System.currentTimeMillis();
                    
            while(lives > 0){
                System.out.println("\nlives left:"+ lives);
                
                int before = game.score;
                game.askRandomQuestion();
                if(game.score == before){
                    lives--; // wrong answer reduces life
                    System.out.println("Oops, Wrong! Life Remaining: " + lives);
                }
            }
            long endTime3 =System.currentTimeMillis();
            long totalTime3 =(endTime3 - startTime3) /1000;
            game.printSummary();
            break;

        case 4: // Time Trial (30 seconds)
            System.out.println("Welcome to the time limit and your time starts now!");
            System.out.print("Enter time limit in seconds: ");
            int limitSeconds = input.nextInt();

            long startTime4 = System.currentTimeMillis();
            long limitMillis = limitSeconds * 1000;
            int questionsAttempted = 0;
            //long start = System.currentTimeMillis();
            while(System.currentTimeMillis() - startTime4 < limitMillis){
                game.askRandomQuestion();
                questionsAttempted++;
            }
            long endTime4 = System.currentTimeMillis();
            long totalTime4 = (endTime4 - startTime4) / 1000;

            System.out.println("\n===== TIME'S UP! =====");
            System.out.println("Total time: " + totalTime4 + " seconds");
            System.out.println("Questions attempted: " + questionsAttempted);
            game.printSummary(); // score + summary
            break;

        default:
            System.out.println("Invalid mode.");
            break;
        }
    } else if (mainChoice ==2){
            System.out.println("\n=== Multiplayer / Competition Mode ===");
            System.out.print("Enter number of players: ");
            int numPlayers = input.nextInt();
            input.nextLine();
            
            String[] playerNames = new String[numPlayers];
            int[] playerScores = new int[numPlayers];
            String[] playerSummaries = new String[numPlayers];

            System.out.println("\nChoose mode for all players (1 - 4):");
            System.out.println("1) Make a Wish");
            System.out.println("2) No Mistakes");
            System.out.println("3) Take Chances (3 lives)");
            System.out.println("4) Time Trial");
            System.out.print("Mode: ");
            int chosenMode = input.nextInt();
            
            for(int i = 0; i < numPlayers; i++){
            input.nextLine();
            System.out.print("\nEnter name for Player " + (i+1) + ": ");
            playerNames[i] = input.nextLine();

            // Reset Game state
            game.score = 0;
            game.Summary = "";

            System.out.println("\n--- " + playerNames[i] + "'s Turn ---");

             switch(chosenMode){
                case 1:
                    System.out.print("How many questions would you like to play? ");
                    int n = input.nextInt();
                    long startTime1 = System.currentTimeMillis();
                    for(int q = 0; q < n; q++){
                        game.askRandomQuestion();
                    }
                    long endTime1 = System.currentTimeMillis();
                    System.out.println("Time taken: " + (endTime1 - startTime1)/1000 + " seconds");
                    break;
             case 2:
                    boolean stillCorrect = true;
                    long startTime2 = System.currentTimeMillis();
                    while(stillCorrect){
                        int beforeScore = game.score;
                        game.askRandomQuestion();
                        if(game.score == beforeScore){
                            stillCorrect = false;
                        }
                    }
                    long endTime2 = System.currentTimeMillis();
                    System.out.println("Time taken: " + (endTime2 - startTime2)/1000 + " seconds");
                    break;
              case 3:
                    int lives = 3;
                    long startTime3 = System.currentTimeMillis();
                    while(lives > 0){
                        System.out.println("\nLives left: " + lives);
                        int beforeScore = game.score;
                        game.askRandomQuestion();
                        if(game.score == beforeScore){
                            lives--;
                            System.out.println("Oops, wrong! Lives left = " + lives);
                        }
                    }
                    long endTime3 = System.currentTimeMillis();
                    System.out.println("Time taken: " + (endTime3 - startTime3)/1000 + " seconds");
                    break;
             case 4:
                    System.out.print("Enter time limit in seconds: ");
                    int limitSeconds = input.nextInt();
                    long startTime4 = System.currentTimeMillis();
                    long limitMillis = limitSeconds * 1000;
                    int questionsAttempted = 0;
                    while(System.currentTimeMillis() - startTime4 < limitMillis){
                        game.askRandomQuestion();
                        questionsAttempted++;
                    }
                    long endTime4 = System.currentTimeMillis();
                    System.out.println("Time taken: " + (endTime4 - startTime4)/1000 + " seconds");
                    System.out.println("Questions attempted: " + questionsAttempted);
                    break;

                default:
                    System.out.println("Invalid mode selected.");
                    break;
                    }
             
             // Save results for leaderboard
             playerScores[i] = game.score;
             playerSummaries[i] = game.Summary;

             // Display player's summary after their turn
             System.out.println("\n--- Results for " + playerNames[i] + " ---");
             game.printSummary();

             System.out.println("\n=====LEADERBOARD=====");
            // Simple bubble-sort style sort to maintain original order in case of ties
            for(int rank = 0; rank < numPlayers; rank++){
            int maxIndex = rank;
            for(int j = rank+1; j < numPlayers; j++){
                if(playerScores[j] > playerScores[maxIndex]){
                    maxIndex = j;
                }
            }
                // Swap scores
                int tempScore = playerScores[rank];
                playerScores[rank] = playerScores[maxIndex];
                playerScores[maxIndex] = tempScore;

                  // Swap names
                String tempName = playerNames[rank];
                playerNames[rank] = playerNames[maxIndex];
                playerNames[maxIndex] = tempName;

                  // (Summary is kept but not printed here)
        }
            for(int p = 0; p < numPlayers; p++){
            System.out.println((p+1) + ". " + playerNames[p] + " - Score: " + playerScores[p]);
            }
        }
    } else {
        System.out.println("Invalid choice.");

            }
        }
}

