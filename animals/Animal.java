package animals;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Animal {
    static Scanner scanner = new Scanner(System.in);
    List<String> articles = Arrays.asList("a", "an");
    List<Character> vowels = Arrays.asList('a', 'e', 'i', 'o', 'u');
    @JsonProperty("name")
    String name;
    @JsonProperty("article")
    String article;
    @JsonProperty("fullName")
    String fullName;
    @JsonProperty("fact")
    String fact;
    static boolean firstAnimal = true;

    public Animal () {
        String[] animal = askForAnimal();
        this.article = animal[0];
        this.name = animal[1];
        this.fullName = this.article + this.name;
    }

    @JsonCreator
    public Animal (@JsonProperty("name") String name, @JsonProperty("article") String article, @JsonProperty("fullName") String fullName, @JsonProperty("fact") String fact) {
        this.name = name;
        this.article = article;
        this.fullName = fullName;
        this.fact = fact;
    }

    private String[] askForAnimal() {

        if (firstAnimal) {
            System.out.println(UserInteraction.appResource.getString("askFavorite"));
            firstAnimal = false;
        }

        String animal = scanner.nextLine().toLowerCase();
        String[] animalArr = animal.split(" ");

        ArrayList<String> animalWords = new ArrayList<>(List.of(animalArr));

        String firstWord = animalArr[0];

        if (!isArticle(firstWord)) {
            String article = findArticle(animal);
            if (article.equals(UserInteraction.appResource.getString("indefiniteArticle"))) {
                animalWords.remove(firstWord);
            }
            return new String[]{findArticle(animal), createAnimalName(animalWords)};
        } else {
            animalWords.remove(firstWord);
            return new String[]{firstWord, createAnimalName(animalWords)};
        }
    }

    public static String distinguishAnimal(Animal animal1, Animal animal2) {
        System.out.println(UserInteraction.appResource.getString("specifyFact") + animal1.fullName + UserInteraction.appResource.getString("from") + animal2.fullName + ".\n" +
                UserInteraction.appResource.getString("sentenceFormat"));

        return askForFact(animal1, animal2);
    }

    private static String askForFact(Animal animal1, Animal animal2) {
        String input = scanner.nextLine();
        Pattern pattern;
        if (UserInteraction.english) {
            pattern = Pattern.compile("it (is|has|can) ([a-zA-Z0-9\\s]+)\\W?", Pattern.CASE_INSENSITIVE);
        } else {
            pattern = Pattern.compile("ĝi (estas|havas|povas|loĝas) ([a-zA-Z0-9\\s]+)\\W?", Pattern.CASE_INSENSITIVE);
        }
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            String regexCaughtFact = matcher.group(2);
            String verb = matcher.group(1);
            return verifyFact(verb, regexCaughtFact, animal1, animal2);
        } else {
            System.out.println(UserInteraction.appResource.getString("examplesOfStatement") +
                    UserInteraction.appResource.getString("specifyFact") + animal1.fullName + UserInteraction.appResource.getString("from") + animal2.fullName + ".\n" +
                    UserInteraction.appResource.getString("sentenceFormat"));
            return askForFact(animal1, animal2);
        }
    }

    private static String verifyFact(String verb, String fact, Animal animal1, Animal animal2) {
        System.out.println(UserInteraction.appResource.getString("isItCorrectFor") + animal2.fullName + "?");
        boolean itIsCorrect = verifyAnswer(scanner.nextLine());

        System.out.println(UserInteraction.appResource.getString("learnedFollowing"));
        if (itIsCorrect) {
            System.out.println(UserInteraction.appResource.getString("indefiniteArticleListing") + animal1.name + " " + negateVerb(verb) + " " + fact + ".");
            System.out.println(UserInteraction.appResource.getString("indefiniteArticleListing") + animal2.name + " " + verb + " " + fact + ".");
            animal2.fact = verb + " " + fact;
            animal1.fact = negateVerb(verb) + " " + fact;
        } else {
            System.out.println(UserInteraction.appResource.getString("indefiniteArticleListing") + animal1.name + " " + verb + " " + fact + ".");
            System.out.println(UserInteraction.appResource.getString("indefiniteArticleListing") + animal2.name + " " + negateVerb(verb) + " " + fact + ".");
            animal1.fact = verb +  " " + fact;
            animal2.fact = negateVerb(verb) + " " +  fact;
        }

        System.out.println(UserInteraction.appResource.getString("canDistinguish"));
        System.out.println(askWithFact(verb, fact));
        System.out.println(UserInteraction.appResource.getString("niceLearned"));
        return askWithFact(verb, fact);
    }
    public static boolean verifyAnswer(String answer) {
        answer = answer.trim().toLowerCase().replaceFirst("[!.]","");
        if (Arrays.asList(UserInteraction.appResource.getStringArray("confirmations")).contains(answer)) {
            return true;
        } else if (Arrays.asList(UserInteraction.appResource.getStringArray("negations")).contains(answer)) {
            return false;
        } else {
            System.out.println(UserInteraction.getRandomArrayElement(UserInteraction.appResource.getStringArray("reConfirm")));
            String input = scanner.nextLine();
            return verifyAnswer(input);
        }
    }

    private static String askWithFact(String verb, String fact) {
        if (UserInteraction.english) {
            if (verb.equals("can")) {
                return " - Can it " + fact + "?";
            } else if (verb.equals("has")) {
                return " - Does it have " + fact + "?";
            } else {
                return " - Is it " + fact + "?";
            }
        } else {
            if (verb.equals("povas")) {
                return " - Ĉu ĝi povas " + fact + "?";
            } else if (verb.equals("havas")) {
                return " - Ĉu ĝi havas " + fact + "?";
            } else {
                return " - Ĉu ĝi estas " + fact + "?";
            }
        }
    }

    private static String negateVerb(String verb) {
        if (UserInteraction.english) {
            if (verb.equals("can")) {
                return "can't";
            } else if (verb.equals("has")) {
                return "doesn't have";
            } else {
                return "isn't";
            }
        } else {
            return "ne " + verb;
        }
    }

    private String createAnimalName(ArrayList<String> words) {
        StringBuilder wordString = new StringBuilder();
        for (String word: words
        ) {
            wordString.append(word).append(" ");
        }

        return wordString.toString().trim();
    }

    private boolean isArticle(String word) {
        return articles.contains(word);
    }

    private String findArticle(String word) {
        if (word.split(" ")[0].equals(UserInteraction.appResource.getString("indefiniteArticle"))) {
            return UserInteraction.appResource.getString("indefiniteArticle");
        }
        Character firstLetter = word.charAt(0);
        if (UserInteraction.english) {
            if (vowels.contains(firstLetter)) {
                return "an ";
            } else {
                return "a ";
            }
        } else {
            return "";
        }
    }

    public AnimalTree.AnimalNode guess(AnimalTree.AnimalNode animal) {
        System.out.println(UserInteraction.appResource.getString("isIt") + this.fullName + "?");
        boolean itIsCorrect = verifyAnswer(scanner.nextLine());
        if (itIsCorrect) {
            return animal;
        } else {
            System.out.println(UserInteraction.appResource.getString("giveUp"));
            Animal newAnimal = new Animal();
            String question = distinguishAnimal(this, newAnimal);
            if (this.fact.contains(UserInteraction.appResource.getString("cant")) ||
                    this.fact.contains(UserInteraction.appResource.getString("doesntHave")) ||
                    this.fact.contains(UserInteraction.appResource.getString("isnt")))
            {
                return new AnimalTree.AnimalNode(question,newAnimal, this);
            } else
                return new AnimalTree.AnimalNode(question,this, newAnimal);
        }
    }

    public String getName() {
        return name;
    }

    public String getArticle() {
        return article;
    }

    public String getFullName() {
        return fullName;
    }

    public String getFact() {
        return fact;
    }
}
