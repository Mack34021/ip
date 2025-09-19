import items.Task;

import items.Deadline;
import items.Event;
import items.Task;

import java.io.File;       // Import the File class
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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
}
