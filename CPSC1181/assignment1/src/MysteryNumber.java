package src;

import javax.swing.*;
import java.util.Random;

public class MysteryNumber {
    public static void main(String[] args) {
        boolean repeat = true;
        String[] levels = {"Easy", "Medium", "Hard", "Cancel"};
        int rounds = 0, modifier = 0; //rounds specifies number of operations performed, modifier adds difficulty to operations
        JOptionPane.showMessageDialog(null, "Hello, this is a number guessing game! You think of any integer and perform some operations on it, then let me know your final answer and I'll guess what your original number was.");
        do {
            int difficulty = JOptionPane.showOptionDialog(null, "Please choose a difficulty ", "Number Guessing Game", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, levels, levels[3]);
            switch (difficulty) {
                case (0):
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
                    break;
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


    public static void game(int count, int mod) {
        //operations[] keeps track of the arithmetic operations used per round, results[] lists the user's inputs per round and randomMod[] tracks the randomized numbers used for each round's operation
        int[] operations = {-1, -1, -1, -1}, randomMod = {0, 0, 0, 0};
        int opsPerformed = 0;
        int userInput = 0;
        Random random = new Random();
        for (int i = 0; i < count; i++, opsPerformed++) {
            operations[i] = random.nextInt(2);
            randomMod[i] = random.nextInt(mod) + 1;
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
            invalid = validate(input);
        }
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

    /**
     * Method to check if input is a number. Number must contain only digits and 1 decimal place
     *
     * @param in is a string passed through for validation
     * @return true if string is determined to be a number.
     */
    public static boolean validate(String in) {
        for (int i = 0, decimal = 0; i < in.length(); i++) {
            char ch = in.charAt(i);
            boolean result = Character.isDigit(ch);
            if (ch == '.')
                decimal++;
            //not a number if character is not a digit and not '.'
            if (!result && ch != '.') {
                JOptionPane.showMessageDialog(null, "Error: please enter a number.");
                return false;
            }
            if (ch=='.' && (ch+1)!= 0) {
                JOptionPane.showMessageDialog(null, "Error, answer must be an integer.");
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
