import java.io.FileNotFoundException;
import java.io.*;
import java.util.Scanner;
public class Wordle {

    public static Scanner keyboard = new Scanner(System.in);
    static String answer; //global variable that can be applied to multiple methods

    public static void intro() { //made intro method to clean up the main and make it easier to call when a player wants to play again
        System.out.println("#########################################################");
        System.out.println("# Let’s play Wordle. #");
        System.out.println("# Your goal is to guess a secret word. #");
        System.out.println("# The word may have duplicated letters. #");
        System.out.println("# For each guess, you receive feedback. #");
        System.out.println("# ’H’ for hits, ’m’ for miss, and ’-’ for others. #");
        System.out.println("# All words are 6 letters and only 6 letter guesses will be accepted #");
        System.out.println("# Your commands are as follows #");
        System.out.println("# s for showing the secret, #");
        System.out.println("# h for showing the history, and #");
        System.out.println("# g for giving up and terminating the present puzzle. #");
        System.out.println("#########################################################");
    }

    public static String generateWord() throws FileNotFoundException { // Randomly generates the correct word
        Scanner sc = new Scanner(new File("words6.txt")); // scans the file
        String[] allWords = new String[6936]; //creates an empty array of the size of # of words on the words6.txt file
        for (int i = 1; i < 6936; i++) { //Starts at line one because the first line is the number of words available
            allWords[i] = sc.nextLine(); // goes line by line through the loop and makes each word a new spot in the array 'all words'
        }
        int x = (int) (Math.random() * 6936); //creates a random integer between 0 and 6936 (# of words)
        answer = allWords[x]; // sets the correct word to be the word at the random int above's position in the array of all words
        return answer;
    }


    public static String result(String realWord, String guess) { //Creates the resulting string of H, m, -
        char[] actual = realWord.toCharArray(); //header takes in the real answer and the guess, and this line makes the word into an array of chars
        char[] input = guess.toCharArray(); // Makes the guess into a char array
        char[] output = new char[6]; //makes a new array of length 6 that will become the string with H, m, -
        for (int i = 0; i < 6; i++) {
            if (actual[i] == input[i]) { //goes through and compares each letter to the one in the same spot
                output[i] = 'H';
            } else if (input[i] == actual[0] || input[i] == actual[1] || input[i] == actual[2] || input[i] == actual[3] || input[i] == actual[4] || input[i] == actual[5]) {
                output[i] = 'm'; //compares each letter of the guess to each individual letter in the correct word for a miss
            } else {
                output[i] = '-'; //if it's not in the word at all, it's neither a hit or a miss ("-")
            }
        }
        return new String(output); // returns the string version of the char array made
    }

    public static void main(String[] args) throws FileNotFoundException {
        intro(); //call to intro method
        generateWord(); //creates the random word
        System.out.println("Choose the length for the secret (Must be 6 in this version of Wordle). This length will be the same for all games.");
        String length = "";
        length = keyboard.next();
        while (!length.equals("6")) { //forces the user to put 6 for the length but still asks them to input it
            System.out.println("This is an invalid length. Please type 6 for the game to begin.");
            length = keyboard.next();
        }
            if (length.equals("6")) { //once the user types in 6, guesses begin
                System.out.println("You can now begin guessing!");
                String guess = "";
                String history = "";
                while (guess != answer) {
                    guess = keyboard.next();
                    if (guess.equals("s")) { // code to show the secret
                        guess = "s-----"; //user doesnt see this, but makes the length 6 to get around my other issue
                        System.out.println("The secret is " + answer);
                    } else if (guess.equals("h")) { // code to show the history
                        guess = "h-----"; // same as above
                        System.out.println("History:");
                        System.out.println(history);
                    } else if (guess.equals("g")) { // code to give up
                        guess = "g-----"; // same as above
                        System.out.println("You have given up! The word was " + answer);
                        System.out.println("Here was your round history:");
                        System.out.println(history);
                        System.out.println("Would you like to play again? (y or n)"); //option to play again
                        guess = keyboard.next();
                        if (guess.equals("y")) { // starts over if they want to play again
                            guess = "y-----";
                            intro();
                            generateWord();
                            history = ""; //Resets history once they play again and start over
                        } else { // code terminates if they say no
                            System.out.println("Bye!");
                            break;
                        }

                    } else if (guess.length() != 6) { //makes all guesses be 6 letters long
                        System.out.println("The guess must be 6 letters long. Try again.");
                    } else if (guess.equals(answer)) { //once they guess the word right
                        System.out.println(result(answer, guess));
                        String x = guess + ": " + result(answer, guess);
                        System.out.print("Good job, you got it! Would you like to play again? (y or n)");
                        guess = keyboard.next();
                        if (guess.equals("y")) { // play again code, same as above
                            guess = "y-----"; // user doesnt see this, but makes the length 6 to get around my other issue
                            intro();
                            generateWord();
                            history = ""; //Resets history once they play again and start over
                        } else {
                            System.out.println("Bye!");
                            break;
                        }
                    } else {
                        System.out.println(result(answer, guess));
                        String x = guess + ": " + result(answer, guess);
                        history += x + "\n"; //updates history
                    }
                }
            }
        }
    }

