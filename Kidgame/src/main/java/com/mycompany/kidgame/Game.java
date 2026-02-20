/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kidgame;

/**
 *
 * @author User
 */
import java.util.Scanner;
class Game {int score;
    String Summary ="";
    
    public void generateAddQuestions(){
        Scanner input =new Scanner(System.in);
        
        int num1 = (int)(Math.random() * 20);
        int num2 = (int)(Math.random() * 20);
        int actualAnswer = num1+num2;
        System.out.println("what is "+num1+"+"+num2+"?");
        int userAnswer =input.nextInt();
       // Summary +="\n"+num1"+"+num2+"="+userAnswer+":"+(userAnswer==actualAnswer);
        if(actualAnswer==userAnswer){
            System.out.println("True");
            ++score;
        }else{
           System.out.println("False");
        }
    }
    
    // SUBTRACTION QUESTION
    public void generateSubQuestion(){
        Scanner input = new Scanner(System.in);

        int num1 = (int)(Math.random() * 20);
        int num2 = (int)(Math.random() * 20);

        // Always subtract smaller from larger
        int larger = Math.max(num1, num2);
        int smaller = Math.min(num1, num2);

        int actualAnswer = larger - smaller;

        System.out.println("What is " + larger + " - " + smaller + "?");
        int userAnswer = input.nextInt();

        if(actualAnswer == userAnswer){
            System.out.println("yes");
            score++;
        }else{
            System.out.println("No");
        }
    }
    
    // MULTIPLICATION QUESTION
    public void generateMulQuestion(){
        Scanner input = new Scanner(System.in);

        int num1 = (int)(Math.random() * 20);
        int num2 = (int)(Math.random() * 20);
        int actualAnswer = num1 * num2;

        System.out.println("What is " + num1 + " * " + num2 + "?");
        int userAnswer = input.nextInt();

        if(actualAnswer == userAnswer){
            System.out.println("Exactly");
           score++;
        }else{
            System.out.println("False");
        }
    }

    // DIVISION QUESTION (2 decimal places, divisor 1–10)
    public void generateDivQuestion(){ // function name for div
        Scanner input = new Scanner(System.in);

        int num1 = (int)(Math.random() * 100);
        int num2 = (int)(Math.random() * 10) + 1; // ensures divisor is 1–10

        double actualAnswer = (double)num1 / num2;
        actualAnswer = Math.round(actualAnswer * 100.0) / 100.0; // round to 2 decimals

        System.out.println("What is " + num1 + " / " + num2 + "? (Answer to 2 decimal places)");
        double userAnswer = input.nextDouble();

        if(Math.abs(userAnswer - actualAnswer) < 0.01){ // accepts answer within 2 decimals
            System.out.println("Correct");
            score++;
        }else{
            System.out.println("False, answer is "+ actualAnswer);
            
        }
    }


    public void printSummary(){
        System.out.println(Summary);
        System.out.println("Your score is: " + score);
    }

    public void askRandomQuestion(){
        int choice = (int)(Math.random() * 4); // 0–3
      // generateSubQuestion(); so when i use this code line to make it only sub and just re edit this part to make it only the function i want.
       //generateDivQuestion(); //and use this to get only div question.

        switch(choice){
            case 0: generateAddQuestions(); break;
            case 1: generateSubQuestion(); break;
            case 2: generateMulQuestion(); break;
            case 3: generateDivQuestion(); break;
        }        
    }
}
       
    

   

    

