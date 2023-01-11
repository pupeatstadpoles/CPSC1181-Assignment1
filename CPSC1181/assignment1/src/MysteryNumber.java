package src;

import javax.swing.*;
import java.util.Random;

/**
 * Name: Pup Abdulgapul
 * Student Number: 100362791
 * Course: CPSC 1181-003
 * Professor: Hengameh Hamavand
 * Program: MysteryNumber - A number guessing game with a GUI. There are 3 difficulty levels which modify the number of arithmetic operations performed during the game as well as the operands of those operations. Users are told to think of an integer, then given operations to perform on that initial number before inputting their final result. The program then guesses what the initial value was.
 */
public class MysteryNumber {
    public static void main(String[] args) {
        boolean repeat = true;
        String[] levels = {"Easy", "Medium", "Hard", "Cancel"};
        int rounds = 0, modifier = 0; //rounds specifies number of operations performed, modifier adds difficulty to operations
        JOptionPane.showMessageDialog(null, "Hello, this is a number guessing game!\nYou think of any whole number and perform some operations on it, then let me know your final answer and I'll guess what your original number was.");
        do {
            int difficulty = JOptionPane.showOptionDialog(null, "Please choose a difficulty ", "Number Guessing Game", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, levels, levels[3]);
            switch (difficulty) {
                case (0): //easy difficulty
                    rounds = 1;
                    modifier = 20;
                    break;
                case (1):
                    rounds = 3;
                    modifier = 45;
                    break;
                case (2):
                    rounds = 4;
                    modifier = 98;
                    break;
                default: //if cancel is clicked
                    return;
            }
            if (rounds != 0) {
                game(rounds, modifier);
            }

            //continuation dialogue prompt
            int cont = JOptionPane.showConfirmDialog(null, "Do you want to continue?", "Continue?", JOptionPane.YES_NO_OPTION);
            if (cont != 0) //if the second button (no) or the [X] button is clicked
                repeat = false;
        } while (repeat);

    }


    /**
     * Method to perform the actual game mechanics. Takes
     *
     * @param rounds is the number of operations to be carried out.
     * @param mod    is the modifier. This determines the range the operand can be for the operations during the game.
     */
    public static void game(int rounds, int mod) {
        //operations[] keeps track of the arithmetic operations used per round, results[] lists the user's inputs per round and randomMod[] tracks the randomized numbers used for each round's operation
        int[] operations = {-1, -1, -1, -1}, randomMod = {0, 0, 0, 0};
        int opsPerformed = 0; //tracks operations/rounds already carried out
        int userInput = 0;


        Random random = new Random();
        for (int i = 0; i < rounds; i++, opsPerformed++) {
            operations[i] = random.nextInt(2); //randomly chooses between addition/subtraction
            randomMod[i] = random.nextInt(mod) + 1; //ensures operand is in the range of 1-mod
            switch (operations[i]) {
                case (0):
                    JOptionPane.showMessageDialog(null, "Add " + randomMod[i] + " to your number.");
                    break;
                case (1):
                    JOptionPane.showMessageDialog(null, "Subtract " + randomMod[i] + " from your number.");
                    break;
            }
        }


        boolean invalid = false;
        String input = "";
        while (!invalid) {
            input = JOptionPane.showInputDialog(null, "What is your final number?", "Final Answer", JOptionPane.QUESTION_MESSAGE);
            if (input == null) //input can be null if cancel button was clicked. do not validate.
                break;
            invalid = validate(input);
        }


        if (input != null) {
            userInput = Integer.parseInt(input);
            //reversing the operations made
            for (; opsPerformed > 0; opsPerformed--) {
                switch (operations[opsPerformed - 1]) {
                    case (0):
                        //reversing the addition
                        userInput = userInput - randomMod[opsPerformed - 1];
                        break;
                    case (1):
                        //reversing the subtraction
                        userInput = userInput + randomMod[opsPerformed - 1];
                        break;
                }
            }
            JOptionPane.showMessageDialog(null, "My guess for your number is " + userInput);
        }
    }




    /**
     * Method to check if input is a number. Number must contain only digits and a maximum of 1 decimal place and 1 '-' in the case of negative numbers.
     *
     * @param in is a string passed through for validation
     * @return true if string is determined to be a valid number.
     */
    public static boolean validate(String in) {
        for (int i = 0, decimal = 0; i < in.length(); i++) {
            char ch = in.charAt(i);
            boolean result = Character.isDigit(ch);


            //if the very first character is '-', string may be a negative number
            if ((i == 0) && (ch == '-'))
                continue;

            if (ch == '.')
                decimal++;

            //not a number if character is not a digit and not '.'
            if (!result && ch != '.') {
                JOptionPane.showMessageDialog(null, "Error: please enter a whole number.");
                return false;
            }

            if (ch == '.' && (ch + 1) != 0) {
                JOptionPane.showMessageDialog(null, "Error, answer must be a whole number.");
                return false;
            }

            //not a number if more than 1 '.' in the string
            if (decimal > 1) {
                JOptionPane.showMessageDialog(null, "Error: too many decimal points.");
                return false;
            }
        }
        return true;
    }

}
