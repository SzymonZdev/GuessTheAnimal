import animals.UserInteraction;

import java.util.ListResourceBundle;
import java.util.function.UnaryOperator;

public class App extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"morningGreeting", "Good morning"},
                {"afternoonGreeting", "Good afternoon"},
                {"eveningGreeting", "Good evening"},
                {"classicGreeting", "Hello!"},
                {"farewell", new String[]{
                        "Bye!",
                        "Have a nice day!",
                        "See you soon!"
                }},
                {"welcomeText", "\nWelcome to the animal expert system!\n"},
                {"yourChoice", "Your choice:"},
                {"playAgain", "Would you like to play again?"},
                {"thinkAndGuess", "You think of an animal, and I guess it."},
                {"pressEnter", "Press enter when you're ready."},
                {"presentAnimals", "Here are the animals I know:"},
                {"gameOver", "I guessed right! Game over!"},
                {"wentWrong", "Something went wrong!"},
                {"treeStats", "The Knowledge Tree stats\n"},
                {"rootNode", "root node: "},
                {"totalNodes", "total number of nodes: "},
                {"totalAnimals", "total number of animals: "},
                {"totalStatements", "total number of statements: "},
                {"totalHeight", "height of the tree: "},
                {"minDepth", "minimum animal's depth: "},
                {"avgDepth", "average animal's depth: "},
                {"factsAbout", "Facts about the "},
                {"noFacts", "No facts about the  "},
                {"enterAnimal", "Enter the animal:"},
                {"it", "It"},
                {"giveUp", "I give up. What animal do you have in mind?"},
                {"isIt", "Is it "},
                {"askFavorite", "I want to learn about animals.\nWhich animal do you like most?"},
                {"specifyFact", "Specify a fact that distinguishes "},
                {"from", " from "},
                {"sentenceFormat", "The sentence should be of the format: 'It can/has/is ...'."},
                {"indefiniteArticle", "the"},
                {"indefiniteArticleListing", " - The "},
                {"isItCorrectFor", "Is the statement correct for "},
                {"learnedFollowing", "I learned the following facts about animals:"},
                {"canDistinguish", "I can distinguish these animals by asking the question:"},
                {"niceLearned", "Nice! I've learned so much about animals!\n"},
                {"cant", "can't"},
                {"doesntHave", "doesn't have"},
                {"isnt", "isn't"},
                {"examplesOfStatement", """
                The examples of a statement:
                         - It can fly
                         - It has a horn
                         - It is a mammal
                         """},
                {"menuOptions", """
                What do you want to do:
                
                1. Play the guessing game
                2. List of all animals
                3. Search for an animal
                4. Calculate statistics
                5. Print the Knowledge Tree
                0. Exit"""},
                {"confirmations", new String[]{
                        "y", "yes", "yeah", "yep", "sure", "right", "affirmative", "correct", "indeed", "you bet", "exactly", "you said it"
                }},
                {"negations", new String[]{
                        "n", "no", "no way", "nah", "nope", "negative", "i don't think so", "yeah no"
                }},
                {"reConfirm", new String[]{
                        "I'm not sure I caught you: was it yes or no?",
                        "Funny, I still don't understand, is it yes or no?",
                        "Oh, it's too complicated for me: just tell me yes or no.",
                        "Could you please simply say yes or no?",
                        "Oh, no, don't try to confuse me: say yes or no."
                }},


                {"animal.name", (UnaryOperator<String>) name -> {
                    if (name.matches("[aeiou].*")) {
                        return "an " + name;
                    } else {
                        return "a " + name;
                    }
                }},
                {"animal.question", (UnaryOperator<String>) animal -> "Is it " + animal + "?"}
        };
    }
}
