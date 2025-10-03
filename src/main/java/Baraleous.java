import fileManager.FileManager;
import taskList.TaskList;
import ui.Ui;

import java.io.File;
import java.util.Scanner;

import static utils.Utils.*;

public class Baraleous {
    static File saveFile;

    public static void main(String[] args) {
        printMessage(Ui.logo(), false);
        saveFile = FileManager.initialiseFile();
        Scanner scanner = new Scanner(System.in);
        TaskList taskList = new TaskList();
        FileManager.readFile(saveFile, taskList.getTaskList());
        while (!receiveInstruction(scanner, taskList, saveFile)) {
            ;
        }
    }
}
