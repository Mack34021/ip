import java.util.Scanner;

public class Baraleous {
    public static void main(String[] args) {
        String logo = "Hello! I'm Baraleous XIV!\n" +
                "  What can I do for you today?";
        printMessage(logo);
        Scanner scanner = new Scanner(System.in);
        while(true){
            String userInput = scanner.nextLine();
            if(userInput.equals("bye")){
                printMessage("Goodbye!");
                break;
            }else{
                printMessage(userInput);
            }
        }
    }

    /**
     * Prints to terminal in a nice formatted manner.
     * @param message String to be printed to the user
     * todo: manage messages with '/n' characters - replace with '/n  '.
     */
    private static void printMessage(String message){
        System.out.println("  _________Baraleous__________");
        System.out.println("  " + message);
        System.out.println("  ____________User____________");
    }
}


