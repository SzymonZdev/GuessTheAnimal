package animals;

public class Main {
    public static void main(String[] args) {
        try {
            new UserInteraction(args);
        } catch (ArrayIndexOutOfBoundsException e) {
            new UserInteraction(new String[]{"no argument"});
        }
    }
}
