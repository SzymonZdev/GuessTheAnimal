package animals;

import java.time.LocalTime;
import java.util.*;

public class UserInteraction {

    public static LocalTime time;

    public static ResourceBundle appResource = ResourceBundle.getBundle("App");
    static Scanner scanner = new Scanner(System.in);
    static AnimalTree tree = new AnimalTree();
    static String format;

    public static boolean english = true;

    public UserInteraction(String[] argument) {
        setFormatAndTime(argument);
        greet();
        tree.newGame();
        showMenu();
    }

    private void setFormatAndTime(String[] arguments) {
        for (String arg: arguments
             ) {
            if (arg.contains("eo")) {
                appResource = ResourceBundle.getBundle("App_eo");
            }

            if (arg.contains("xml")) {
                format = "xml";
            } else if (arg.contains("yaml")) {
                format = "yaml";
            } else {
                format = "json";
            }
        }
        if (String.valueOf(UserInteraction.appResource).contains("eo")) {
            english = false;
        }
        time = LocalTime.now();
    }

    public static void showMenu() {
        System.out.println(appResource.getString("menuOptions"));

        int choice = scanner.nextInt();
        switch (choice) {
            case 0 -> {
                tree.writeFile();
                sayFarewell();
            }
            case 1 -> startGame();
            case 2 -> {
                System.out.println(appResource.getString("yourChoice") + "\n2");
                AnimalTree.printAllAnimals();
            }
            case 3 -> {
                System.out.println(appResource.getString("yourChoice") + "\n3");
                AnimalTree.searchForAnimal();
            }
            case 4 -> AnimalTree.printTreeStatistics();

            case 5 -> AnimalTree.printTree();
        }
    }

    public static void askToPlayAgain() {
        System.out.println(appResource.getString("playAgain"));
        if (Animal.verifyAnswer(scanner.nextLine())) {
            startGame();
        } else {
            showMenu();
        }
    }

    private static void startGame() {
        System.out.println(appResource.getString("thinkAndGuess"));
        waitForEnter();
    }

    private static void playGame() {
        AnimalTree.traverse();
    }

    private static void waitForEnter() {
        System.out.println(appResource.getString("pressEnter"));
        scanner = new Scanner(System.in);
        if (!scanner.nextLine().equals("")) {
            waitForEnter();
        } else {
            playGame();
        }
    }

    public void greet() {
        int timeOfDay = time.getHour();

        if (timeOfDay > 5 && timeOfDay < 12) {
            System.out.println(appResource.getString("morningGreeting"));
        }
        else if (timeOfDay >= 12 && timeOfDay < 18) {
            System.out.println(appResource.getString("afternoonGreeting"));
        }
        else if (timeOfDay >= 18 || timeOfDay < 5) {
            System.out.println(appResource.getString("eveningGreeting"));
        } else {
            System.out.println(appResource.getString("classicGreeting"));
        }
    }

    public static void sayFarewell() {
        System.out.println(getRandomArrayElement(appResource.getStringArray("farewell")));
    }

    public static String getRandomArrayElement(String[] array) {
        return array[new Random().nextInt(array.length)];
    }
}
