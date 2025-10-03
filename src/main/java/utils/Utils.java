package utils;

import items.Deadline;
import items.Event;
import items.Task;
import taskList.TaskList;

import java.util.ArrayList;
import java.util.Scanner;

import static parser.Parser.commandSwitch;
import static parser.Parser.getArguments;

public class Utils {

    /**
     * Takes a line from scanner input and executes
     *
     * @param scanner The scanner to be read from
     * @param taskList The tasklist to be added to if appropriate
     * @param saveFile The save file to be written to if modifications are made
     * @return true if must terminate the program, false if continuing
     */
    public static boolean receiveInstruction(Scanner scanner, TaskList taskList,
                                             java.io.File saveFile){
        String userInput = scanner.nextLine();
        ArrayList<String> commandsList = getArguments(userInput);
        return commandSwitch(commandsList, taskList, userInput, saveFile);
    }

    /**
     * Prints to terminal in a nice formatted manner.
     *
     * @param message String to be printed to the user
     */
    public static void printMessage(String message, boolean isFinalMessage) {
        String modMessage = message.replaceAll("\n", "\n  ");
        System.out.println("  _________Baraleous__________");
        System.out.println("  " + modMessage);
        if (!isFinalMessage) {
            System.out.println("  ____________User____________");
        }
    }

    /**
     * Lists all currently-saved tasks in a user-friendly manner
     *
     * @param taskList The list to print
     */
    public static void listAllTasks(TaskList taskList) {
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
     * Marks a task as complete
     *
     * @param taskToMark The task index to mark as complete
     * @param taskList   The list of tasks
     */
    public static void markTaskDone(String taskToMark, TaskList taskList) {
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
    public static boolean checkTaskIndexExists(TaskList taskList, int taskIndex) {
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
     * Checks the umber of arguments in a commmand
     *
     * @param commandsList The command to be checkkd
     * @param desiredArguments The correct number of arguments for this command.
     * @return true if valid, false if incvalid
     */
    public static boolean checkCommandArguments(ArrayList<String> commandsList, int desiredArguments){
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
     * Marks a task as not complete
     *
     * @param taskToMark The task index to mark as complete
     * @param taskList   The list of tasks to find taskToMark within
     */
    public static void unmarkTaskDone(String taskToMark, TaskList taskList) {
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
}
