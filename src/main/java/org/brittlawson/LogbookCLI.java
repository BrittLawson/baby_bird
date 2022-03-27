package org.brittlawson;

import org.apache.commons.dbcp2.BasicDataSource;
import org.brittlawson.dao.*;

import javax.sql.DataSource;
import java.util.Scanner;

public class LogbookCLI {

    private final Scanner userInput = new Scanner(System.in);
    private final CanopyDao canopyDao;
    private final DropzoneDao dropzoneDao;
    private final JumpDao jumpDao;


    public static void main(String[] args) {

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/SkydiveLogbook");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres1");
        
        LogbookCLI application = new LogbookCLI(dataSource);
        application.run();
    }
    
    public LogbookCLI(DataSource dataSource) {
        canopyDao = new JdbcCanopyDao(dataSource);
        dropzoneDao = new JdbcDropzoneDao(dataSource);
        jumpDao = new JdbcJumpDao(dataSource);
    }
    
    private void run() {
        displayBanner();
        boolean running = true;
        while (running) {
            displayMenu();
            int selection = promptForInt("What do you want to do?");
            if (selection == 1) {
                // call method
            } else if (selection == 2) {
                // call method
            } else if (selection == 99) {
                running = false;
            } else {
                displayError("Bummer. Invalid selection.");
            }
        }
    }

    private void displayError(String message) {
        System.out.println();
        System.out.println("***" + message + "***");
        System.out.println();
    }

    private int promptForInt(String prompt) {
        while (true) {
            System.out.println(prompt);
            String response = userInput.nextLine();
            try {
                return Integer.parseInt(response);
            } catch (NumberFormatException e) {
                if (response.isBlank()) {
                    return -1; //assumes negative numbers are never valid entries
                } else {
                    displayError("Numbers only, please.");
                }
            }
        }
    }

    private void displayMenu() {
        System.out.println("What do you want to get into today?");
        System.out.println("1. Check out past shreddy jumps");
        System.out.println("2. Log a new jump");
        System.out.println("3. Peep your canopies");
        System.out.println("4. How bout them DZs");
        System.out.println("4. Update info for a previously logged jump, canopy, or DZ");

    }

    private void displayBanner() {
        System.out.println(" _          _ _         _   _");
        System.out.println("| |__   ___| | | ___   | |_| |__   ___ _ __ ___");
        System.out.println("| '_ \\ / _ \\ | |/ _ \\  | __| '_ \\ / _ \\ '__/ _ \\  ");
        System.out.println("| | | |  __/ | | (_) | | |_| | | |  __/ | |  __/_");
        System.out.println("|_| |_|\\___|_|_|\\___/   \\__|_| |_|\\___|_|  \\___( )");
        System.out.println("| |__   __ _| |__  _   _  | |__ (_)_ __ __| |  |/ ");
        System.out.println("| '_ \\ / _` | '_ \\| | | | | '_ \\| | '__/ _` |  ");
        System.out.println("| |_) | (_| | |_) | |_| | | |_) | | | | (_| |   ");
        System.out.println("|_.__/ \\__,_|_.__/ \\__, | |_.__/|_|_|  \\__,_|  ");
        System.out.println("                   |___/");
        System.out.println();
        System.out.println();
    }


}
