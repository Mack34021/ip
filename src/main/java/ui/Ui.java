package ui;

import items.Deadline;
import items.Event;
import items.Task;
import taskList.TaskList;

import java.util.ArrayList;

import static utils.Utils.printMessage;

public class Ui {
    private static final String BARALEOUS_LOGO = "Hello! I'm Baraleous XIV!\n"
            + "What can I do for you today?";

    public static String logo(){
        return BARALEOUS_LOGO;
    }

    public static void listSearchResult(TaskList taskList, ArrayList<Task> taskMatches) {
        StringBuilder strBuil2 = new StringBuilder();
        strBuil2.append("Search Results:\n");
        for (int i = 0; i < taskMatches.size(); i++){// Task taskMatch : taskMatches) {
            //System.out.println("Adding" + taskMatch);
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
            if (i < taskList.getTaskListLength() - 1) {
                strBuil2.append('\n');
            }
            //strBuil2.append(taskMatches.toString()).append("\n");
        }
        printMessage(strBuil2.toString(), false);
    }

}
