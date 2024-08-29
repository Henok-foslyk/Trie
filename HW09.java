import java.util.*;

class HW09 extends App {
    
    static class Node  {
        // This node's children.
        // children[0] corresponds to 'a', children[1] corresponds to 'b', ...
        // Initially, all children are null (empty slots).
        Node[] children;
        
        // Whether this node corresponds to the last letter in a word.
        boolean isTerminal;
        
        // Construct a new Node object.
        // Initially, all its children are null.
        Node() {
            this.children = new Node[26]; // [ null, null, ..., null ]
            this.isTerminal = false; // NOTE: This line is optional (new Object's are zero-initialized--cleared to 0/false/null)
        }
    }
    
    class Trie {
        // The root node of the trie.
        Node root;
        
        // Construct a new trie.
        // NOTE: Feel free to write your own tests in this constructor.
        // NOTE: Uncomment runAutograder() to run a simple auto-grading function.
        //       The autograder builds a trie by calling the functions you will implement.
        //       +'s are passing tests; -'s are failing tests.
        //       (A crash means one of more of your functions is broken.)
        Trie() {
            root = new Node();
            //addWord("plane");
            runAutograder();
        }
        
        // Add this word to the trie if the trie does not already contain it.
        // Function returns true if it added the word.
        //          returns false if the trie already contained the word.
        // NOTE: Do NOT call containsWord() inside this function.
        //       Doing so would be inefficient (slow).
        boolean addWord(String word) {
            Node curr = root;
            for (char letter: word.toCharArray()){
                int index = letter - 'a';
                if (curr.children[index] != null) {
                    curr = curr.children[index];
                } 
                else {
                    Node child = new Node();
                    curr.children[index] = child;
                    curr = child;
                }
            }
            if (curr.isTerminal == false){
                curr.isTerminal = true;
                return true;
            }
            return false;
        }
        
        // Get whether a word is in the trie.
        boolean containsWord(String word) {
            Node curr = root;
            for (char letter: word.toCharArray()){
                int index = letter - 'a';
                if(curr.children[index] == null) {return false;}
                curr = curr.children[index];
            }
            if(curr.isTerminal == false){ return false; }
            return true;
        }
        
        // Remove this word from the trie if the trie contains it.
        // Function returns true if if removed the world.
        //          returns false if the trie did not contain the word.
        // NOTE: You can "remove" a word by setting the last node in the word's isTerminal to false.
        //       This looks odd in the visualizer, but is A OK.
        //       You do NOT have to actually remove nodes / "prune the tree."
        //       (You certainly can if you want!--It's kinda tricky!)
        // NOTE: Do NOT call containsWord() inside this function.
        //       Doing so would be inefficient (slow).
        boolean removeWord(String word) {
            Node curr = root;
            Node latest = new Node();
            int tocut = -1;
            for (char ltr: word.toCharArray()){
                int index = ltr - 'a';
                Node letter = curr.children[index];
                if(letter == null) {return false;}
                if(curr.isTerminal == true){ latest = curr; tocut = index; }
                else {
                    int count = 0;
                    for (Node child: curr.children){ if (child != null) { count++;}}
                    if (count > 1) { latest = curr; tocut = index;}}
                curr = letter;
            }
// just set the node false if it has other word that continues from it
            int count = 0;
            for (Node child: curr.children){ if (child != null) { count++;}}
            if(curr.isTerminal == true && count > 0){
                curr.isTerminal = false;
                return true;
            }
            else if (curr.isTerminal == true && count == 0){
                latest.children[tocut] = null;
                return true;
            }
/////////////////////////////////////////////           
            return false;
        }
        
        
        // Get all the words in the trie in alphabetical order.
        // 
        // NOTE: Don't worry about the list being alphabetical.
        //       Our trie gives us this "for free."
        // 
        // NOTE: This problem has a cute recursive solution in Java.
        //       I've set things up assuming you will use recursion (optional).
        ArrayList<String> getAllWords() {
            ArrayList<String> result = new ArrayList<>();
            _getAllWordsHelper(root, "", result);
            return result;
        }
        
        // Recursive helper function (calls itself).
        void _getAllWordsHelper(Node node, String prefix, ArrayList<String> result) {
            for (int i = 0; i < node.children.length; i++){
                if (node.children[i] != null){
                    char letter = (char)('a' + i);
                    prefix += letter;
                    if(node.children[i].isTerminal == true) { result.add(prefix);}
                    _getAllWordsHelper(node.children[i], prefix, result);
                    prefix= prefix.substring(0, prefix.length()-1);
                }
            }
        }
        
        
        ////////////////////////////////////////////////////////////////////////
        // A CODE STOPS HERE ///////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////
        
        
        void runAutograder() {
            root = new Node();
            System.out.print("[runAutograder] ");
            _grade(addWord("app"), true);
            _grade(addWord("a"), true);
            _grade(addWord("a"), false);
            _grade(containsWord("a"), true);
            _grade(containsWord("app"), true);
            _grade(containsWord("apple"), false);
            _grade(removeWord("app"), true);
            _grade(removeWord("app"), false);
            _grade(containsWord("a"), true);
            _grade(containsWord("app"), false);
            _grade(containsWord("apple"), false);
            _grade(addWord("at"), true);
            _grade(addWord("cow"), true);
            _grade(addWord("atom"), true);
            _gradeGetAllWords(new String[]{ "a", "at", "atom", "cow" });
            _grade(removeWord("atom"), true);
            _grade(removeWord("atom"), false);
            _gradeGetAllWords(new String[]{ "a", "at", "cow" });
            System.out.println(" // +'s are passing tests; -'s are failing tests\n");
        }
        
        void _gradeGetAllWords(String[] correctAllWords) {
            ArrayList<String> studentAllWords = getAllWords();
            boolean match = (studentAllWords.size() == correctAllWords.length);
            if (match) {
                for (int i = 0; i < correctAllWords.length; ++i) {
                    if (!studentAllWords.get(i).equals(correctAllWords[i])) {
                        match = false;
                        break;
                    }
                }
            }
            _grade(true, match);
        }
        
        void _grade(boolean studentAnswer, boolean correctAnswer) {
            boolean isCorrect = (studentAnswer == correctAnswer);
            System.out.print(isCorrect ? "+" : "-");
        }
        
        
        void draw() {
            class NodeWrapper {
                Node node;
                int level;
                Vector2 parentPosition;
                Vector2 position;
                char letter;
                NodeWrapper(Node node, int level, Vector2 parentPosition, Vector2 position, char letter) {
                    this.node = node;
                    this.level = level;
                    this.parentPosition = parentPosition;
                    this.position = position;
                    this.letter = letter;
                }
            }
            ArrayDeque<NodeWrapper> queue = new ArrayDeque<>();
            queue.add(new NodeWrapper(root, 0, new Vector2(), new Vector2(), ' '));
            int spawnLevel = 0;
            int drawIndexOfNodeInLevel = 0;
            while(queue.size() > 0) {
                NodeWrapper nodeWrapper = queue.remove();
                { // draw
                    double nodeRadius = 0.25;
                    { // line
                        Vector2 a = nodeWrapper.parentPosition;
                        Vector2 b = nodeWrapper.position;
                        Vector2 nudge = Vector2.directionVectorFrom(a, b).times(1.2 * nodeRadius);
                        a = a.plus(nudge);
                        b = b.minus(nudge);
                        drawLine(a, b, Vector3.black);
                    }
                    drawCircle(nodeWrapper.position, nodeRadius, (!nodeWrapper.node.isTerminal) ? new Vector3(0.9) : Vector3.cyan);
                    drawString("" + nodeWrapper.letter, nodeWrapper.position, Vector3.black, 16, true);
                }
                for (int letterIndex = 0; letterIndex < 26; ++letterIndex) {
                    Node child = nodeWrapper.node.children[letterIndex];
                    char childLetter = (char)('a' + letterIndex);
                    if (child == null) continue;
                    
                    int childLevel = nodeWrapper.level + 1;
                    if (spawnLevel != childLevel) {
                        spawnLevel = childLevel;
                        drawIndexOfNodeInLevel = 0;
                    } else {
                        ++drawIndexOfNodeInLevel;
                    }
                    Vector2 childParentPosition = nodeWrapper.position;
                    Vector2 childPosition = new Vector2(drawIndexOfNodeInLevel * 1.0, -1.0 * childLevel);
                    queue.add(new NodeWrapper(child, childLevel, childParentPosition, childPosition, childLetter));
                }
            }
        }
    }
    
    
    ////////////////////////////////////////////////////////////////////////
    // S CODE STOPS HERE ///////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    
    
    Trie _trie;
    String _consoleBuffer;
    final int CONSOLE_STATE_ADDING = 0;
    final int CONSOLE_STATE_REMOVING = 1;
    final int CONSOLE_STATE_QUERYING = 2;
    int consoleState;
    void setup() {
        _trie = new Trie();
        _consoleBuffer = "";
        
        System.out.println("Press + start adding words.");
        System.out.println("Press - start removing words.");
        System.out.println("Press ? start querying (asking) whether words are in the _trie.");
        System.out.println("Type a word like you normally do.");
        System.out.println("Press Enter to add/remove/query a word.");
        System.out.println("Press Space to print all the words in the _trie.");
        System.out.println("Press Escape to quit.");
        System.out.println();
    }
    
    void loop() {
        _trie.draw();
        { // _consoleBuffer
            if (keyPressed(27)) { // FORNOW
                System.exit(0);
            } else if (keyPressed('=')) {
                consoleState = CONSOLE_STATE_ADDING;
            } else if (keyPressed('-')) {
                consoleState = CONSOLE_STATE_REMOVING;
            } else if (keyPressed('/')) {
                consoleState = CONSOLE_STATE_QUERYING;
            } else if (keyPressed(' ')) {
                System.out.println("[getAllWords] " + _trie.getAllWords());
            } else if (keyPressed('\b')) {
                if (_consoleBuffer.length() > 0) {
                    _consoleBuffer = _consoleBuffer.substring(0, _consoleBuffer.length() - 1);
                }
            } else if (keyPressed('\n') || keyPressed('\r')) {
                String word = _consoleBuffer.trim();
                boolean valid = true;
                if (word.length() > 0) {
                    if (consoleState == CONSOLE_STATE_ADDING) {
                        boolean added = _trie.addWord(word);
                        if (added) {
                            System.out.println("[addWord] added \"" + word + "\"");
                        } else {
                            System.err.println("[addWord] did NOT add \"" + word + "\"; trie already contained it");
                        }
                    } else if (consoleState == CONSOLE_STATE_REMOVING) {
                        boolean removed = _trie.removeWord(word);
                        if (removed) {
                            System.out.println("[removeWord] removed \"" + word + "\"");
                        } else {
                            System.err.println("[removeWord] did NOT remove \"" + word + "\"; trie did NOT contain it");
                        }
                    } else if (consoleState == CONSOLE_STATE_QUERYING) {
                        boolean containsWord = _trie.containsWord(word);
                        if (containsWord) {
                            System.out.println("[containsWord] contains \"" + word + "\"");
                        } else {
                            System.err.println("[containsWord] does NOT contain \"" + word + "\"");
                        }
                    } else {
                        assert false;
                    }
                    _consoleBuffer = "";
                }
            } else if (keyAnyPressed) {
                if ('a' <= keyLastPressed && keyLastPressed <= 'z') {
                    _consoleBuffer += String.valueOf(keyLastPressed);
                }
            }
            
            { // draw
                String prefix = null;
                Vector3 color = null;
                if (consoleState == CONSOLE_STATE_ADDING) {
                    prefix = "+";
                    color = new Vector3(0.0, 0.6, 0.0);
                } else if (consoleState == CONSOLE_STATE_REMOVING) {
                    prefix = "-";
                    color = new Vector3(0.8, 0.0, 0.0);
                } else if (consoleState == CONSOLE_STATE_QUERYING) {
                    prefix = "?";
                    color = new Vector3(0.0, 0.0, 0.8);
                } else {
                    assert false;
                }
                drawString(
                           prefix + ' ' + _consoleBuffer,
                           new Vector2(1.5, 0.0),
                           color,
                           24,
                           false);
            }
        }
    }
    
    public static void main(String[] arguments) {
        App app = new HW09();
        double S = 10.0;
        app.setWindowSizeInWorldUnits(S, S);
        app.setWindowCenterInWorldUnits(S / 2 - 1.0, -S / 2 + 1.0);
        app.hotkeysEnabled = false;
        app.run();
    }
    
}