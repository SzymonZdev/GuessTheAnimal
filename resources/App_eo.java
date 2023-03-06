import java.util.ListResourceBundle;
import java.util.function.UnaryOperator;

public class App_eo extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"morningGreeting", "Bonan matenon"},
                {"afternoonGreeting", "Bonan posttagmezon"},
                {"eveningGreeting", "Bonan vesperon"},
                {"classicGreeting", "Saluton!"},
                {"farewell", new String[]{
                        "Ĝis!",
                        "Ĝis revido!",
                        "Estis agrable vidi vin!"
                }},
                {"welcomeText", "\nBonvenon al la sperta sistemo de la besto!\n"},
                {"yourChoice", "Via elekto:"},
                {"playAgain", "Ĉu vi ŝatus ludi denove?"},
                {"thinkAndGuess", "Vi pensu pri besto, kaj mi divenos ĝin."},
                {"pressEnter", "Premu enen kiam vi pretas."},
                {"presentAnimals", "Jen la bestoj, kiujn mi konas:"},
                {"gameOver", "Ĉu vi volas provi denove?"},
                {"wentWrong", "Io misfunkciis!"},
                {"treeStats", "La statistiko de la Scio-Arbo\n"},
                {"rootNode", "radika nodo: "},
                {"totalNodes", "totala nombro de nodoj: "},
                {"totalAnimals", "tuta nombro de bestoj: "},
                {"totalStatements", "totala nombro de deklaroj: "},
                {"totalHeight", "alteco de la arbo: "},
                {"minDepth", "minimuma profundo de besto: "},
                {"avgDepth", "averaĝa profundo de besto: "},
                {"factsAbout", "Faktoj pri la "},
                {"noFacts", "Neniuj faktoj pri la "},
                {"enterAnimal", "Enigu la beston:"},
                {"it", "Ĝi"},
                {"giveUp", "Mi rezignas. Kiun beston vi havas en la kapo?"},
                {"isIt", "Ĉu ĝi estas "},
                {"askFavorite", "Mi volas lerni pri bestoj.\nKiun beston vi plej ŝatas?"},
                {"specifyFact", "Indiku fakton, kiu distingas "},
                {"from", " de "},
                {"sentenceFormat", "La frazo devas esti de la formato: 'Ĝi ...'."},
                {"indefiniteArticle", "la"},
                {"indefiniteArticleListing", " - La "},
                {"isItCorrectFor", "Ĉu la aserto ĝustas por la "},
                {"learnedFollowing", "Mi lernis la jenajn faktojn pri bestoj:"},
                {"canDistinguish", "Mi povas distingi ĉi tiujn bestojn per la demando:"},
                {"niceLearned", "Bela! Mi multe lernis pri bestoj!\n"},
                {"cant", "ne povas"},
                {"doesntHave", "ne havas"},
                {"isnt", "ne estas"},
                {"examplesOfStatement", """
               La ekzemploj de deklaro:
                         - Ĝi povas flugi
                         - Ĝi havas kornon
                         - Ĝi estas mamulo
                """},
                {"menuOptions", """
                Kion vi volas fari:
                
                1. Ludi la divenludon
                2. Listo de ĉiuj bestoj
                3. Serĉi beston
                4. Kalkuli statistikon
                5. Printi la Sciarbon
                0. Eliri"""},
                {"confirmations", new String[]{
                        "j", "jes", "certe"
                }},
                {"negations", new String[]{
                        "n", "ne"
                }},
                {"reConfirm", new String[]{
                        "Bonvolu enigi jes aŭ ne.",
                        "Amuze, mi ankoraŭ ne komprenas, ĉu jes aŭ ne?",
                        "Pardonu, devas esti jes aŭ ne.",
                        "Ni provu denove: ĉu jes aŭ ne?",
                        "Pardonu, ĉu mi rajtas demandi vin denove: ĉu tio estis jes aŭ ne?"
                }},


        {"animal.name", (UnaryOperator<String>)name -> name},
        {"animal.question", (UnaryOperator<String>) animal ->
                "Ĉu ĝi estas " + animal + "?"}
       };
    }
}
