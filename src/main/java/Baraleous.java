import items.Deadline;
import items.Event;
import items.Task;

import java.util.ArrayList;
import java.util.Scanner;

public class Baraleous {
    private static final String BARALEOUS_LOGO = "Hello! I'm Baraleous XIV!\n"
            + "What can I do for you today?";
    public static void main(String[] args) {
        printMessage(BARALEOUS_LOGO, false);
        Scanner scanner = new Scanner(System.in);
        TaskList taskList = new TaskList();
        while (true) {
            String userInput = scanner.nextLine();
            ArrayList<String> commandsList = getArguments(userInput);
            if (commandSwitch(commandsList, taskList, userInput)) {
                break;
            }
        }
    }

    /**
     * Main switch used for different command types.
     *
     * @param commandsList The list of commands passed by the user, one word per index
     * @param taskList     The program's main list of tasks (and events, deadline, etc.)
     * @param userInput    The unsplit user input string.
     * @return True if it needs to terminate early for program exit. False otherwise
     */
    private static boolean commandSwitch(ArrayList<String> commandsList, TaskList taskList, String userInput) {
        switch (commandsList.get(0)) {
        case "bye":     // close program
            printMessage("Goodbye!", true);
            return true;
        case "list":    // Requested list of all added tasks
            listAllTasks(taskList);
            break;
        case "mark":    // Marks task as done
            if (checkCommandArguments(commandsList, 2)){
                String indexToMark = commandsList.get(1);
                markTaskDone(indexToMark, taskList);
            }
            break;
        case "unmark":    // Marks task as done
            if (checkCommandArguments(commandsList, 2)){
                String indexToMark = commandsList.get(1);
                unmarkTaskDone(indexToMark, taskList);
            }
            break;
        case "deadline":    // Marks task as done
            addDeadlineToTaskList(commandsList, taskList);
            break;
        case "event":    // Marks task as done
            addEventToTaskList(commandsList, taskList);
            break;
        case "task":    // Requested new task added
            StringBuilder strBuil = new StringBuilder();
            for (int i = 1; i < commandsList.size(); i++) {
                strBuil.append(commandsList.get(i)).append(" ");
            }
            String taskToAdd = strBuil.toString().trim();
            printMessage("Added: '" + taskToAdd + "'", false);
            taskList.addTaskToList(taskToAdd);
            break;
        case "delete":    // Requested to delete task
            if (checkCommandArguments(commandsList, 2)){
                String indexToMark = commandsList.get(1);
                removeTaskFromTaskList(indexToMark, taskList);
            }
            break;
        default:        // No actual commmand
            printMessage("Need to add a command!", false);
            break;
        }
        return false;
    }

    private static boolean checkCommandArguments(ArrayList<String> commandsList, int desiredArguments){
        if (commandsList.size() == desiredArguments) {
            return true;
        } else if (commandsList.size() > desiredArguments) {
            printMessage("... That's too many arguments", false);
            return false;
        } else {
            printMessage("... That's too few arguments", false);
            return false;
        }
    }

    /**
     * Adds event to the taskList list
     *
     * @param commandsList The user's input command, split into words
     * @param taskList     The taskList to add the newly-created task to
     */
    private static void addEventToTaskList(ArrayList<String> commandsList, TaskList taskList) {
        // todo: add error checking
        // Loop through all words in command, looking for start and end times
        int startTimeIndex = 0;
        int endTimeIndex = 0;
        for (int i = 1; i < commandsList.size(); i++) {
            String str = commandsList.get(i);
            if (str.startsWith("/from")) {
                if (startTimeIndex == 0) {
                    startTimeIndex = i;
                } else {
                    System.out.println("ERROR: multiple start times for event.");
                }
            } else if (str.startsWith("/to")) {
                if (endTimeIndex == 0) {
                    endTimeIndex = i;
                } else {
                    System.out.println("ERROR: multiple end times for event.");
                }
            }
        }

        StringBuilder eventName = new StringBuilder();
        StringBuilder eventStartTime = new StringBuilder();
        StringBuilder eventEndTime = new StringBuilder();

        // Builds strings with the known start and endpoints of sections of the command string
        for (int i = 1; i < startTimeIndex; i++) {
            eventName.append(commandsList.get(i)).append(" ");
        }
        for (int i = startTimeIndex + 1; i < endTimeIndex; i++) {
            eventStartTime.append(commandsList.get(i)).append(" ");
        }
        for (int i = endTimeIndex + 1; i < commandsList.size(); i++) {
            eventEndTime.append(commandsList.get(i)).append(" ");
        }

        String eventNameTrimmed = eventName.toString().trim();
        String eventStartTimeTrimmed = eventStartTime.toString().trim();
        String eventEndTimeTrimmed = eventEndTime.toString().trim();

        // Trim all strings, then create and add deadline to the task list
        if (eventNameTrimmed.isEmpty()) {
            eventNameTrimmed = "UNSPECIFIED";   // In case user forgets to give due date
        }
        if (eventStartTimeTrimmed.isEmpty()) {
            eventStartTimeTrimmed = "UNSPECIFIED";   // In case user forgets to give due date
        }
        if (eventEndTimeTrimmed.isEmpty()) {
            eventEndTimeTrimmed = "UNSPECIFIED";   // In case user forgets to give due date
        }
        taskList.addEventToList(eventNameTrimmed, eventStartTimeTrimmed, eventEndTimeTrimmed);
        printMessage("Added Event: '" + eventNameTrimmed + "' starting at '" + eventStartTimeTrimmed
                + "' and ending at '" + eventEndTimeTrimmed + "'", false);
    }

    /**
     * Adds deadline to the taskList list
     *
     * @param commandsList The user's input command, split into words
     * @param taskList     The taskList to add the newly-created task to
     */
    private static void addDeadlineToTaskList(ArrayList<String> commandsList, TaskList taskList) {
        StringBuilder deadlineName = new StringBuilder();
        StringBuilder deadlineDueBy = new StringBuilder();
        // Loop through all words in command, looking for "/by" to find the due time
        for (int i = 1; i < commandsList.size(); i++) {
            String str = commandsList.get(i);
            if (str.startsWith("/by")) {
                for (int j = i + 1; j < commandsList.size(); j++) {
                    String str2 = commandsList.get(j);
                    if (str2.startsWith("/")) {
                        break;  // As another command found, is no longer part of '/by'
                    }
                    deadlineDueBy.append(str2).append(" ");
                }
                break;
            } else {
                // Then not the deadline, so add to the task text
                deadlineName.append(str).append(" ");
            }
        }
        // Trim all strings, then create and add deadline to the task list
        String deadlineNameTrimmed = deadlineName.toString().trim();
        String deadlineDueByTrimmed = deadlineDueBy.toString().trim();
        if (deadlineNameTrimmed.isEmpty()) {
            deadlineNameTrimmed = "UNSPECIFIED";   // In case user forgets to give due date
        }
        if (deadlineDueByTrimmed.isEmpty()) {
            deadlineDueByTrimmed = "UNSPECIFIED";   // In case user forgets to give due date
        }
        taskList.addDeadlineToList(deadlineNameTrimmed, deadlineDueByTrimmed);
        printMessage("Added Deadline: '" + deadlineNameTrimmed
                + "' due by '" + deadlineDueByTrimmed + "'", false);
    }

    /**
     * Removes a task form the taskList list
     *
     * @param taskToDelete The user's input command, for the task to delete
     * @param taskList     The taskList to remove from the task list
     */
    private static void removeTaskFromTaskList(String taskToDelete, TaskList taskList){
        int taskIndex = Integer.parseInt(taskToDelete);
        if (checkTaskIndexExists(taskList, taskIndex)) return;
        Task curTask = taskList.getTaskFromList(taskIndex);
        printMessage("Deleting task [X] " + curTask.toString(), false);
        taskList.removeTaskFromList(taskIndex);
    }

    /**
     * Marks a task as complete
     *
     * @param taskToMark The task index to mark as complete
     * @param taskList   The list of tasks
     */
    private static void markTaskDone(String taskToMark, TaskList taskList) {
        int taskIndex = Integer.parseInt(taskToMark);
        if (checkTaskIndexExists(taskList, taskIndex)) return;
        Task curTask = taskList.getTaskFromList(taskIndex);
        if (curTask.getIsTaskDone()) {
            printMessage("Hey! That's already complete\n[X] " + curTask.toString(), false);
        } else {
            curTask.setTaskDone(true);
            printMessage("OK! Task marked complete!\n[X] " + curTask.toString(), false);
        }
    }

    /**
     * Check if the listed task actually exists.
     *
     * @param taskList  The list of tasks to operate on
     * @param taskIndex the index to operate on
     * @return true if no such task exists, false if task does exist
     */
    private static boolean checkTaskIndexExists(TaskList taskList, int taskIndex) {
        if (taskIndex <= 0) {
            printMessage("Mate.... task "
                    + taskIndex + " ..... really? Indexing starts at task 1.", false);
            return true;
        }
        if (taskIndex >= taskList.getTaskListLength()) {
            printMessage("Brother you are not that busy, you don't have "
                    + taskIndex + " tasks, you only got " + (taskList.getTaskListLength() - 1), false);
            return true;
        }
        return false;
    }

    /**
     * Marks a task as not complete
     *
     * @param taskToMark The task index to mark as complete
     * @param taskList   The list of tasks to find taskToMark within
     */
    private static void unmarkTaskDone(String taskToMark, TaskList taskList) {
        int taskIndex = Integer.parseInt(taskToMark);
        if (checkTaskIndexExists(taskList, taskIndex)) return;
        Task curTask = taskList.getTaskFromList(taskIndex);
        if (!curTask.getIsTaskDone()) {
            printMessage("Hey! That's already not complete\n[ ] "
                    + curTask.toString(), false);
        } else {
            curTask.setTaskDone(false);
            printMessage("OK! Task marked as incomplete!\n[ ] "
                    + curTask.toString(), false);
        }
    }

    /**
     * Lists all currently-saved tasks in a user-friendly manner
     *
     * @param taskList The list to print
     */
    private static void listAllTasks(TaskList taskList) {
        StringBuilder taskListString = new StringBuilder();
        if (taskList.getTaskListLength() == 1){
            printMessage("No tasks exist.", false);
            return;
        }
        for (int i = 1; i < taskList.getTaskListLength(); i++) {
            Task curTask = taskList.getTaskFromList(i);
            String taskMarker = curTask.getIsTaskDone() ? "[X]" : "[ ]";
            String taskType;
            if (curTask instanceof Deadline) {
                taskType = "[D]";
            } else if (curTask instanceof Event) {
                taskType = "[E]";
            } else {
                taskType = "[T]";
            }
            taskListString.append(String.format("%d.%s%s %s", i, taskType, taskMarker, curTask.toString()));
            if (i < taskList.getTaskListLength() - 1) {
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
     * @param string       The string to be extracted from
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
        String firstWord = stringTrimmed.substring(0, firstSpace).trim();
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
