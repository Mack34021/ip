import java.util.ArrayList;
import java.util.Scanner;

public class Baraleous {
    public static void main(String[] args) {
        String logo = "Hello! I'm Baraleous XIV!\n" +
                "What can I do for you today?";
        printMessage(logo, false);
        Scanner scanner = new Scanner(System.in);
        TaskList taskList = new TaskList();
        commandLoop: while(true) {
            String userInput = scanner.nextLine();
            ArrayList<String> commandsList = getArguments(userInput);
            switch (commandsList.get(0)) {
            case "bye":     // close program
                printMessage("Goodbye!", true);
                break commandLoop;
            case "list":    // Requested list of all added tasks
                listAllTasks(taskList);
                break;
            case "mark":    // Marks task as done
                if (commandsList.size() == 2) {
                    String indexToMark = commandsList.get(1);
                    markTaskDone(indexToMark, taskList);
                }else if (commandsList.size() > 2) {
                    printMessage("... Thats too many arguments", false);
                }else{
                    printMessage("... Which task should I mark????", false);
                }
                break;
            case "unmark":    // Marks task as done
                if (commandsList.size() == 2) {
                    String indexToMark = commandsList.get(1);
                    unmarkTaskDone(indexToMark, taskList);
                }else if (commandsList.size() > 2) {
                    printMessage("... Thats too many arguments", false);
                }else {
                    printMessage("... Which task should I to mark????", false);
                }
                break;
            default:        // Requested new task added
                taskList.addTaskToList(userInput.trim());
                printMessage("Added: " + userInput, false);
                break;
            }
        }
    }

    /**
     * Marks a task as complete
     *
     * @param taskToMark The task index to mark as complete
     * @param taskList The list of tasks
     */
    private static void markTaskDone(String taskToMark, TaskList taskList) {
        int taskIndex = Integer.parseInt(taskToMark);
        if (taskIndex <= 0) {
            printMessage("Mate.... task "
                    + taskIndex+ " ..... really? Indexing starts at task 1.", false);
            return;
        }
        if (taskIndex >= taskList.getTaskListLength()) {
            printMessage("Brother you are not that busy, you don't have "
                    + taskIndex + " tasks, you only got "+ (taskList.getTaskListLength()-1), false);
            return;
        }
        Task curTask = taskList.getTaskFromList(taskIndex);
        if (curTask.getIsTaskDone()) {
            printMessage("Hey! That's already complete\n[X] " + curTask.getTaskString(), false);
        }else{
            curTask.setTaskDone(true);
            printMessage("OK! Task marked complete!\n[X] " + curTask.getTaskString(), false);
        }
    }

    /**
     * Marks a task as not complete
     *
     * @param taskToMark The task index to mark as complete
     * @param taskList The list of tasks to find taskToMark within
     */
    private static void unmarkTaskDone(String taskToMark, TaskList taskList) {
        int taskIndex = Integer.parseInt(taskToMark);
        if (taskIndex <= 0) {
            printMessage("Mate.... task "
                    + taskIndex + " ..... really? Indexing starts at task 1.", false);
            return;
        }
        if (taskIndex >= taskList.getTaskListLength()) {
            printMessage("Brother you are not that busy, you don't have "
                    + taskIndex + " tasks, you only got " + (taskList.getTaskListLength()-1), false);
            return;
        }
        Task curTask = taskList.getTaskFromList(taskIndex);
        if (!curTask.getIsTaskDone()) {
            printMessage("Hey! That's already not complete\n[ ] "
                    + curTask.getTaskString(), false);
        }else{
            curTask.setTaskDone(false);
            printMessage("OK! Task marked as incomplete!\n[ ] "
                    + curTask.getTaskString(), false);
        }
    }

    /**
     * Lists all currently-saved tasks in a user-friendly manner
     *
     * @param taskList The list to print
     */
    private static void listAllTasks(TaskList taskList) {
        StringBuilder taskListString  = new StringBuilder();
        for (int i = 1; i < taskList.getTaskListLength(); i++) {
            Task curTask = taskList.getTaskFromList(i);
            String taskMarker = curTask.getIsTaskDone() ? "[X]" : "[ ]";
            taskListString.append(String.format("%d.%s %s", i, taskMarker, curTask.getTaskString()));
            if (i < taskList.getTaskListLength()-1) {
                taskListString.append('\n');
            }
        }
        printMessage(taskListString.toString(), false);
    }

    /**
     * Prints to terminal in a nice formatted manner.
     *
     * @param message String to be printed to the user
     */
    private static void printMessage(String message, boolean isFinalMessage) {
        String modMessage = message.replaceAll("\n", "\n  ");
        System.out.println("  _________Baraleous__________");
        System.out.println("  " + modMessage);
        if (!isFinalMessage) {
            System.out.println("  ____________User____________");
        }
    }

    /**
     * Splits the string into an array of words
     *
     * @param string The string to be extracted from
     * @param commandsList The arraylist to write identified commands into
     */
    private static void getWordArrayFromString(String string, ArrayList<String> commandsList) {
        // Trims whitespace, and finds the end of the word
        String stringTrimmed = string.trim();
        int firstSpace = stringTrimmed.indexOf(" ");
        if (firstSpace == -1) {
            firstSpace = stringTrimmed.length();
        }
        // Adds the first word to the commandsList ArrayList
        String firstWord = stringTrimmed.substring(0,firstSpace).trim();
        commandsList.add(firstWord);
        // Finds remaining string, and recursively passes. Base case then there is no remaining string.
        String remainder = stringTrimmed.substring(firstSpace);
        if (!remainder.isEmpty()) {
            getWordArrayFromString(remainder, commandsList);
        }
    }

    /**
     * Splits string into arrayList of strings, one word long each
     *
     * @param userInput Unformatted string input from scanner
     * @return Arraylist<String> of words (stings with no whitespaces)
     */
    private static ArrayList<String> getArguments(String userInput) {
        ArrayList<String> commandsList = new ArrayList<String>();
        getWordArrayFromString(userInput, commandsList);
        return commandsList;
    }
}
