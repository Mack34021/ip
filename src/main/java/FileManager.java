import items.Task;
import items.Deadline;
import items.Event;

import java.io.File;       // Import the File class
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileManager {

    /**
     * Initialises the save file.
     *
     * @return the file if success, NULL if failure
     */
    public static File initialiseFile(){
        File file = new File("src/main/java/assets/saveFile.txt");
        try{
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("ERROR: FAILED TO INITIALISE FILE");
            return null;
        }

        return file;
    }

    /**
     * Formats the task to be printed in a human-readable way to the save file
     * @param task  The task to be added to save file
     * @return      The formatted string to be printed into the file
     */
    private static String formatForTestFile(Task task){
        String retVal;
        if (task instanceof Event){
            Event event = (Event) task;
            retVal = "E|" + (task.getIsTaskDone() ? "X|" : " |") + event.getStartsAt() + "|" + event.getEndsAt() + "|";
        }else if (task instanceof Deadline){
            Deadline deadline = (Deadline) task;
            retVal = "D|" + (task.getIsTaskDone() ? "X|" : " |") + deadline.getDueBy() + "|";
        }else{ // If just a task
            retVal = "T|" + (task.getIsTaskDone() ? "X|" : " |");
        }
        retVal = retVal + task.getTaskName() + '\n';

        return retVal;
    }

    /**
     * Writes the save file
     *
     * @param file      The file to write to
     * @param taskList  The list of task to write to the file
     */
    public static void writeFile(File file, ArrayList<Task> taskList){
        try{
            FileWriter fileWriter = new FileWriter("src/main/java/assets/saveFile.txt");
            for(int i=1; i < taskList.size(); i++){
                fileWriter.write(formatForTestFile(taskList.get(i)));
            }
            fileWriter.close();  // must close manually
        } catch (IOException e) {
            System.out.println("FILE WRITER FAILED");
        }
    }

    private static void createTaskFromString(String string, ArrayList<Task> taskList){
        boolean isTaskDone;
        String taskName;
        switch (string.charAt(0)){
        case 'T':
            taskName = string.substring(string.lastIndexOf('|') + 1);
            isTaskDone = string.charAt(2) == 'X';
            Task task = new Task(taskName);
            task.setTaskDone(isTaskDone);
            taskList.add(task);
            break;
        case 'D':
            taskName = string.substring(string.lastIndexOf('|') + 1);
            isTaskDone = string.charAt(2) == 'X';
            String dueBy = string.substring(4, string.lastIndexOf('|'));
            Deadline deadline = new Deadline(taskName, dueBy);
            deadline.setTaskDone(isTaskDone);
            taskList.add(deadline);
            break;
        case 'E':
            taskName = string.substring(string.lastIndexOf('|') + 1);
            isTaskDone = string.charAt(2) == 'X';       // at static place in line
            int pipeFrom = 3;       // non-static placement starts her
            // The penultimate | in the line, may be in a number of different positions
            int pipeTo = string.substring(pipeFrom+1).indexOf('|') + pipeFrom + 1;
            String from = string.substring(pipeFrom + 1, pipeTo);
            String to = string.substring(pipeTo + 1, string.lastIndexOf('|'));
            Event event = new Event(taskName, from, to);
            event.setTaskDone(isTaskDone);
            taskList.add(event);
            break;
        default:
            break;
        }

    }

    /**
     * Reads the save file, and brings into memory
     * @param file
     * @param taskList
     */
    public static void readFile(File file, ArrayList<Task> taskList){
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
            while (scanner.hasNextLine()){
                //System.out.println("Found Line: " + );
                createTaskFromString(scanner.nextLine(), taskList);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
