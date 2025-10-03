package ui;

import items.Deadline;
import items.Event;
import items.Task;

import java.util.ArrayList;

import static utils.Utils.printMessage;

public class Ui {
    private static final String BARALEOUS_LOGO = "Hello! I'm Baraleous XIV!\n"
            + "What can I do for you today?";

    public static String logo(){
        return BARALEOUS_LOGO;
    }

    /**
     * Prints list of search results in user-friendly way
     *
     * @param taskMatches The list fo matches to the search to be printed
     */
    public static void listSearchResult(ArrayList<Task> taskMatches) {
        StringBuilder strBuil2 = new StringBuilder();
        strBuil2.append("Search Results:\n");
        for (int i = 0; i < taskMatches.size(); i++){
            Task taskMatch = taskMatches.get(i);
            String taskMarker = taskMatch.getIsTaskDone() ? "[X]" : "[ ]";
            String taskType;
            if (taskMatch instanceof Deadline) {
                taskType = "[D]";
            } else if (taskMatch instanceof Event) {
                taskType = "[E]";
            } else {
                taskType = "[T]";
            }
            strBuil2.append(String.format("%d.%s%s %s", i+1, taskType, taskMarker, taskMatch.toString()));
            if (i < taskMatches.size()-1) {
                strBuil2.append('\n');
            }
        }
        printMessage(strBuil2.toString(), false);
    }

}
