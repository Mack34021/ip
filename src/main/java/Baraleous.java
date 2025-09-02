import java.util.Scanner;

public class Baraleous {
    public static void main(String[] args) {
        String logo = "Hello! I'm Baraleous XIV!\n" +
                "What can I do for you today?";
        printMessage(logo, false);
        Scanner scanner = new Scanner(System.in);
        TaskList taskList = new TaskList();
        commandLoop: while(true){
            String userInput = scanner.nextLine();
            String commandWord = getFirstWord(userInput);
            switch (commandWord){
            case "bye":     // close program
                printMessage("Goodbye!", true);
                break commandLoop;
            case "list":    // Requested list of all added tasks
                listAllTasks(taskList);
                break;
            default:        // Requested new task added
                taskList.addTaskToList(userInput.trim());
                printMessage("Added: " + userInput, false);
                break;
            }
        }
    }

    /**
     * Lists all currently-saved tasks in a user-friendly manner
     * @param taskList the list to print
     */
    private static void listAllTasks(TaskList taskList) {
        StringBuilder taskListString  = new StringBuilder();
        for (int i = 1; i < taskList.getTaskListLength(); i++){
            taskListString.append(i).append(". ").append(taskList.getTaskFromList(i));
            if (i < taskList.getTaskListLength()-1){
                taskListString.append('\n');
            }
        }
        printMessage(taskListString.toString(), false);
    }

    /**
     * Prints to terminal in a nice formatted manner.
     * @param message String to be printed to the user
     */
    private static void printMessage(String message, boolean isFinalMessage){
        String modMessage = message.replaceAll("\n", "\n  ");
        System.out.println("  _________Baraleous__________");
        System.out.println("  " + modMessage);
        if (!isFinalMessage){
            System.out.println("  ____________User____________");
        }
    }

    /**
     * Gets the first word from a string, defined as the text after first non-whitespace and before next whitespace
     * @param string the string to be extracted from
     * @return the first word in the given string
     */
    private static String getFirstWord(String string){
        String stringTrimmed = string.trim();
        int firstSpace = stringTrimmed.indexOf(" ");
        if (firstSpace == -1) {
            firstSpace = stringTrimmed.length();
        }
        return stringTrimmed.substring(0,firstSpace).trim();
    }
}
