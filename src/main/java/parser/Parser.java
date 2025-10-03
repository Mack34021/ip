package parser;

import fileManager.FileManager;
import items.Task;
import taskList.TaskList;

import java.util.ArrayList;

import static ui.Ui.listSearchResult;
import static utils.Utils.*;

public class Parser {

    /**
     * Splits string into arrayList of strings, one word long each
     *
     * @param userInput Unformatted string input from scanner
     * @return Arraylist<String> of words (stings with no whitespaces)
     */
    public static ArrayList<String> getArguments(String userInput) {
        ArrayList<String> commandsList = new ArrayList<String>();
        getWordArrayFromString(userInput, commandsList);
        return commandsList;
    }

    /**
     * Main switch used for different command types.
     *
     * @param commandsList The list of commands passed by the user, one word per index
     * @param taskList     The program's main list of tasks (and events, deadline, etc.)
     * @param userInput    The unsplit user input string.
     * @return True if it needs to terminate early for program exit. False otherwise
     */
    public static boolean commandSwitch(ArrayList<String> commandsList, TaskList taskList, String userInput, java.io.File saveFile) {
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
            FileManager.writeFile(saveFile, taskList.getTaskList());
            break;
        case "unmark":    // Marks task as done
            if (checkCommandArguments(commandsList, 2)){
                String indexToMark = commandsList.get(1);
                unmarkTaskDone(indexToMark, taskList);
            }
            FileManager.writeFile(saveFile, taskList.getTaskList());
            break;
        case "deadline":    // Marks task as done
            addDeadlineToTaskList(commandsList, taskList);
            FileManager.writeFile(saveFile, taskList.getTaskList());
            break;
        case "event":    // Marks task as done
            addEventToTaskList(commandsList, taskList);
            FileManager.writeFile(saveFile, taskList.getTaskList());
            break;
        case "task":    // Requested new task added
            StringBuilder strBuil = new StringBuilder();
            for (int i = 1; i < commandsList.size(); i++) {
                strBuil.append(commandsList.get(i)).append(" ");
            }
            String taskToAdd = strBuil.toString().trim();
            printMessage("Added: '" + taskToAdd + "'", false);
            taskList.addTaskToList(taskToAdd);
            FileManager.writeFile(saveFile, taskList.getTaskList());
            break;
        case "delete":    // Requested to delete task
            if (checkCommandArguments(commandsList, 2)){
                String indexToMark = commandsList.get(1);
                removeTaskFromTaskList(indexToMark, taskList);
            }
            FileManager.writeFile(saveFile, taskList.getTaskList());
            break;
        case "find":    // Finds a string if possible
            ArrayList<Task> taskMatches =  findByString(commandsList, taskList);
            listSearchResult(taskMatches);
            break;
        default:        // No actual commmand
            printMessage("Need to add a command!", false);
            break;
        }
        return false;
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
     * Adds event to the taskList list
     *
     * @param commandsList The user's input command, split into words
     * @param taskList     The taskList to add the newly-created task to
     */
    private static void addEventToTaskList(ArrayList<String> commandsList, TaskList taskList) {
        // todo: add error checking, incl for if insufficient qualifiers
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
        printMessage("Deleting task: " + curTask.toString(), false);
        taskList.removeTaskFromList(taskIndex);
    }

    /**
     * Looks for the string in the names of all events
     *
     * @param stringToFind The user's input command, to be found
     * @param taskList     The taskList to remove from the task list
     */
    private static ArrayList<Task> findByString(ArrayList<String> stringToFind, TaskList taskList){
        ArrayList<Task> taskMatches = new ArrayList<>();

        StringBuilder strBuil = new StringBuilder();
        for(int i=1; i < stringToFind.size(); i++){
            strBuil.append(" ").append(stringToFind.get(i));
        }
        String strToFind = strBuil.toString().trim();

        for(int i=1; i < taskList.getTaskListLength(); i++){
            Task task = taskList.getTaskFromList(i);
            //System.out.println("Checking " + task.toString());
            if(task.getTaskName().contains(strToFind)){
                taskMatches.add(task);
            }
        }
        return taskMatches;
    }
}

