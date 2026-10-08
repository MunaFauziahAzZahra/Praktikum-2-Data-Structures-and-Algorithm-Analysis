import java.util.Stack;

public class StackUndoRedo {

    private String currentText = "Selamat";
    private Stack<String> undoStack = new Stack<>();
    private Stack<String> redoStack = new Stack<>();

    public void addText(String newText) {
        undoStack.push(currentText);
        currentText = newText;
        redoStack.clear();
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            redoStack.push(currentText);
            currentText = undoStack.pop();
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            undoStack.push(currentText);
            currentText = redoStack.pop();
        }
    }

    public static void main(String[] args) {

        StackUndoRedo editor = new StackUndoRedo();

        editor.addText("Selamat datang");

        System.out.println("Teks saat ini: \"" + editor.currentText + "\"");

        editor.undo();
        System.out.println("Undo: \"" + editor.currentText + "\"");

        editor.redo();
        System.out.println("Redo: \"" + editor.currentText + "\"");
    }
}