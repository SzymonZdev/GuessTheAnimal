package animals;


import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class AnimalTree {
static AnimalNode root;
static AnimalNode currentNode;

    public static void printAllAnimals() {
        System.out.println(UserInteraction.appResource.getString("presentAnimals"));
        for (String animal: getAllAnimals()
             ) {
            System.out.println("- " + animal);
        }
        System.out.println();
        UserInteraction.showMenu();
    }

    public static void searchForAnimal() {
        System.out.println(UserInteraction.appResource.getString("enterAnimal"));
        String userAnimal = Animal.scanner.nextLine();
        boolean animalExists = false;
        AnimalNode animalNode = null;
        List<AnimalNode> allAnimals = getListOfLeafs(root);
        for (AnimalNode animal: allAnimals
             ) {
            if (animal.animal.fullName.contains(userAnimal)) {
                animalNode = animal;
                animalExists = true;
                break;
            }
        }
        if (animalExists) {
            listQuestions(animalNode);
        } else {
            System.out.println(UserInteraction.appResource.getString("noFacts") + userAnimal + "\n");
            UserInteraction.showMenu();
        }
    }

    private static void listQuestions(AnimalNode animalNode) {
        AnimalNode node = animalNode;
        System.out.println(UserInteraction.appResource.getString("factsAbout") + animalNode.animal.name + ":");
        List<String> facts = new ArrayList<>();
        while (node.parentNode != null) {
            if (node.parentNode.yesNode.equals(node)) {
                facts.add("- " + UserInteraction.appResource.getString("it") + " " + node.parentNode.yesFact + ".");
            } else {
                facts.add("- " + UserInteraction.appResource.getString("it") + " " + node.parentNode.noFact + ".");
            }

            node = node.parentNode.parentNode;
        }
        Collections.reverse(facts);
        for (String q: facts
             ) {
            System.out.println(q);
        }
        System.out.println();
        UserInteraction.showMenu();
    }

    public static void printTree() {
        String pop = "├";
        String closer = "└";
        boolean isRoot = true;
        System.out.println();
        Stack<Integer> answerTracker = new Stack<>();
        answerTracker.push(1);
        List<AnimalNode> listOfNodes = getListOfAllNodes(root);
        for (AnimalNode node: listOfNodes
        ) {
            if (node.isLeaf()) {
                if (answerTracker.peek() == 1) {
                    answerTracker.pop();
                    if (answerTracker.peek() == 3) {
                        System.out.print("  " + repeat(answerTracker.size() - 2, "│") + " " + pop + " " + node.animal.fullName + "\n");
                    } else {
                        System.out.print("  " + repeat(answerTracker.size() - 1, "│") + pop + " " + node.animal.fullName + "\n");
                    }
                    answerTracker.push(2);
                } else {
                    answerTracker.pop();
                    if (answerTracker.peek() == 3) {
                        System.out.print("  " + repeat(answerTracker.size() - 2, "│") + " " + closer + " " + node.animal.fullName + "\n");
                        answerTracker.pop();
                    } else {
                        System.out.print("  " + repeat(answerTracker.size() - 1, "│") + closer + " " + node.animal.fullName + "\n");
                    }
                }
            } else {
                if (isRoot) {
                    System.out.print(" " + closer + node.question.substring(2) + "\n");
                    answerTracker.push(1);
                    isRoot = false;
                } else if (answerTracker.peek() == 1) {
                    System.out.print("  " + repeat(answerTracker.size() - 2, "│") + pop + node.question.substring(2) + "\n");
                    answerTracker.pop();
                    answerTracker.push(2);
                    answerTracker.push(1);
                } else {
                    System.out.print("  " + repeat(answerTracker.size() - 2, "│") + closer + node.question.substring(2) + "\n");
                    answerTracker.pop();
                    answerTracker.push(3);
                    answerTracker.push(1);
                }
            }
        }


        System.out.println();
        UserInteraction.showMenu();
    }

    public static String repeat(int count, String with) {
        if (count < 0) {
            return "";
        } else return new String(new char[count]).replace("\0", with);
    }

    public static void printTreeStatistics() {
        System.out.println(UserInteraction.appResource.getString("treeStats"));
        System.out.println(UserInteraction.appResource.getString("rootNode") + extractFact(root.question));
        System.out.println(UserInteraction.appResource.getString("totalNodes") + getListOfAllNodes(root).size());
        System.out.println(UserInteraction.appResource.getString("totalAnimals") + getListOfLeafs(root).size());
        System.out.println(UserInteraction.appResource.getString("totalStatements") + (getListOfAllNodes(root).size() - getListOfLeafs(root).size()));

        System.out.println(UserInteraction.appResource.getString("totalHeight") + getAnimalStatistic("height"));
        System.out.println(UserInteraction.appResource.getString("minDepth") + getAnimalStatistic("minimum")) ;
        System.out.println(UserInteraction.appResource.getString("avgDepth") + getAnimalStatistic("average")) ;



        System.out.println();
        UserInteraction.showMenu();
    }

    private static String extractFact(String question) {
        question = question.replaceAll("[-?]", "");
        String[] words = question.split(" ");
        StringBuilder stringBuilder = new StringBuilder();
        String firstWord = words[3];
        firstWord = firstWord.replace(firstWord.charAt(0), firstWord.substring(0, 1).toUpperCase().toCharArray()[0]);
        stringBuilder.append(firstWord).append(" ").append(words[2].toLowerCase()).append(" ");

        for (String word: words
             ) {
            if (!stringBuilder.toString().toLowerCase().contains(word.toLowerCase())) {
                stringBuilder.append(word);
                stringBuilder.append(" ");
            }
        }
        return stringBuilder.toString();
    }

    private static String getAnimalStatistic(String statType) {
        return switch (statType) {
            case "height" -> getTreeHeight("height");
            case "minimum" -> getTreeHeight("minimum");
            case "average" -> getTreeHeight("average");
            default -> UserInteraction.appResource.getString("wentWrong");
        };
    }

    private static String getTreeHeight(String type) {
        boolean isRoot = true;
        int maximum = 1;
        int minimum = Integer.MAX_VALUE;
        float count = 0;
        float levelCounter = 0;
        Stack<Integer> answerTracker = new Stack<>();
        answerTracker.push(1);
        List<AnimalNode> listOfNodes = getListOfAllNodes(root);
        for (AnimalNode node : listOfNodes
        ) {
            if (node.isLeaf()) {
                if (answerTracker.peek() == 1) {
                    answerTracker.pop();
                    if (answerTracker.size() > maximum) {
                        maximum = answerTracker.size();
                    }
                    if (answerTracker.size() - 2 < minimum) {
                        minimum = answerTracker.size() - 2;
                    }
                    count++;
                    levelCounter += answerTracker.size();
                    answerTracker.push(2);
                } else {
                    answerTracker.pop();
                    if (answerTracker.size() > maximum) {
                        maximum = answerTracker.size();
                    }
                    if (answerTracker.size() - 2 < minimum) {
                        minimum = answerTracker.size() - 2;
                    }
                    if (answerTracker.peek() == 3) {
                        count++;
                        levelCounter += answerTracker.size() - 1;
                        minimum = answerTracker.size() - 2;
                        continue;
                    }
                    count++;
                    levelCounter += answerTracker.size();
                }
            } else {
                if (isRoot) {
                    answerTracker.push(1);
                    isRoot = false;
                } else if (answerTracker.peek() == 1) {
                    answerTracker.pop();
                    answerTracker.push(2);
                    answerTracker.push(1);
                } else {
                    answerTracker.pop();
                    answerTracker.push(3);
                    answerTracker.push(1);
                }
            }
        }

        switch (type) {
            case "height" -> {
                return String.valueOf(maximum);
            }
            case "minimum" -> {
                return String.valueOf(minimum);
            }
            case "average" -> {
                return String.format("%.1f", levelCounter / count);
            }
        }

        return UserInteraction.appResource.getString("wentWrong");
    }

    public void newGame() {
    readFile();

    if (root == null) {
        root = new AnimalNode(new Animal());
        currentNode = root;
    }
    System.out.println(UserInteraction.appResource.getString("welcomeText"));
}

    private void readFile() {
        Path path = lookForDB();

        if (path != null) {
            ObjectMapper mapper;
            File file = new File(path.toUri());
            if (file.toString().matches(".*\\.xml")) {
                mapper = new XmlMapper();
            }else if (file.toString().matches(".*\\.yaml")) {
                mapper = new YAMLMapper();
            } else {
                mapper = new JsonMapper();
            }

            try {
                root = mapper.readValue(file, AnimalNode.class);
                Animal.firstAnimal = false;
                currentNode = root;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Path lookForDB() {
        File dir = new File(System.getProperty("user.dir"));
        File[] matches;
        if (UserInteraction.english) {
            matches = dir.listFiles((dir1, name) -> name.endsWith("animals.json") || name.endsWith("animals.xml") || name.endsWith("animals.yaml"));
        } else {
            matches = dir.listFiles((dir1, name) -> name.endsWith("animals_eo.json") || name.endsWith("animals_eo.xml") || name.endsWith("animals_eo.yaml"));
        }
        if (matches != null && matches.length > 0) {
            return matches[0].toPath();
        } else return null;
    }

    public void writeFile() {
    String myArg = UserInteraction.format;
    String fileName = "animals";
    ObjectMapper mapper;
    mapper = switch (myArg) {
        case "xml" -> new XmlMapper();
        case "yaml" -> new YAMLMapper();
        default -> new JsonMapper();
    };
    if (UserInteraction.english) {
        fileName = switch (myArg) {
            case "xml" -> fileName + ".xml";
            case "yaml" -> fileName + ".yaml";
            default -> fileName + ".json";
        };
    } else {
        fileName = switch (myArg) {
            case "xml" -> fileName + "_eo.xml";
            case "yaml" -> fileName + "_eo.yaml";
            default -> fileName + "_eo.json";
        };
    }

    try {
        mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(System.getProperty("user.dir") + "\\" + fileName), root);
        } catch (IOException e) {
            e.printStackTrace();
        }
}

    public static void traverse() {
        currentNode.check();
    }
    public static List<String> getAllAnimals() {
        List<AnimalNode> list = getListOfLeafs(root);
        List<String> listOfNames = new ArrayList<>();
        for (AnimalNode animal: list
             ) {
            listOfNames.add(animal.animal.name);
        }
        listOfNames = listOfNames.stream().sorted().toList();

        return listOfNames;
    }

    public static List<AnimalNode> getListOfLeafs(AnimalNode root) {
        List<AnimalNode> list = new ArrayList<>();
        walkTreeForLeafs(root, list);
        return list;
    }

    public static List<AnimalNode> getListOfAllNodes(AnimalNode root) {
        List<AnimalNode> list = new ArrayList<>();
        walkTreeForAllNodes(root, list);
        return list;
    }

    public static void walkTreeForLeafs(AnimalNode node, List<AnimalNode> list) {
        if (node == null) {
            return;
        }
        walkTreeForLeafs(node.yesNode, list);
        if (node.isLeaf()) {
            list.add(node);
        }
        walkTreeForLeafs(node.noNode, list);
    }

    public static void walkTreeForAllNodes(AnimalNode node, List<AnimalNode> list) {
        if (node == null) {
            return;
        }
        list.add(node);
        walkTreeForAllNodes(node.yesNode, list);
        walkTreeForAllNodes(node.noNode, list);
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="id")
    static class AnimalNode {

        public String getQuestion() {
            return question;
        }

        public Animal getAnimal() {
            return animal;
        }

        public AnimalNode getYesNode() {
            return yesNode;
        }

        public AnimalNode getNoNode() {
            return noNode;
        }

        public AnimalNode getParentNode() {
            return parentNode;
        }

        @JsonProperty("question")
        String question;
        @JsonProperty("animal")
        Animal animal;
        @JsonProperty("yesNode")
        AnimalNode yesNode;
        @JsonProperty("yesFact")
        String yesFact;
        @JsonProperty("noNode")
        AnimalNode noNode;
        @JsonProperty("noFact")
        String noFact;
        @JsonProperty("parentNode")
        AnimalNode parentNode;

        public AnimalNode(String question, Animal animal1, Animal animal2) {
            this.question = question;
            this.yesNode = new AnimalNode(animal1, this);
            this.yesFact = yesNode.animal.fact;
            this.noNode = new AnimalNode(animal2, this);
            this.noFact = noNode.animal.fact;
            this.animal = null;
            this.parentNode = currentNode;
        }


        public AnimalNode(Animal animal, AnimalNode parentNode) {
            this.animal = animal;
            this.yesNode = null;
            this.noNode = null;
            this.parentNode = parentNode;
            this.question = null;
        }

        public AnimalNode(Animal animal) {
            this.animal = animal;
            this.yesNode = null;
            this.noNode = null;
            this.parentNode = null;
            this.question = null;
        }

        public AnimalNode() {
        }

        @JsonIgnore
        public boolean isLeaf() {
            return this.yesNode == null && this.noNode == null;
        }

        public void check() {
            if (!isLeaf()) {
                currentNode.askQuestion();
                AnimalTree.traverse();
            } else {
                AnimalNode guess = currentNode;
                AnimalNode answer = guessAnimal(guess);
                if (answer.equals(guess)) {
                    System.out.println(UserInteraction.appResource.getString("gameOver"));
                } else {
                    if (root.equals(currentNode)) {
                        root.noNode = answer.noNode;
                        root.yesNode = answer.yesNode;
                        root.question = answer.question;
                        root.animal = null;
                    } else {
                        currentNode.noNode = answer.noNode;
                        currentNode.yesNode = answer.yesNode;
                        currentNode.question = answer.question;
                        currentNode.animal = null;
                    }
                }
                currentNode = root;
                UserInteraction.askToPlayAgain();
            }
        }

        private void askQuestion() {
            System.out.println(currentNode.question);
            if (Animal.verifyAnswer(Animal.scanner.nextLine())) {
                currentNode = currentNode.yesNode;
            } else {
                currentNode = currentNode.noNode;
            }
        }


        private AnimalNode guessAnimal(AnimalNode guess) {
            return currentNode.animal.guess(guess);
        }
    }
}
