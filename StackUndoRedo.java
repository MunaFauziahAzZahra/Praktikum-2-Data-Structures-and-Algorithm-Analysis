import java.util.Stack;

public class StackUndoRedo {
    private String currentText = "Selamat";
    private Stack<String> undoStack = new Stack<>();
    private Stack<String> redoStack = new Stack<>();

    // Menambahkan atau memperbarui teks baru.
    public void addText(String newText) {
        undoStack.push(currentText);
        currentText = newText;
        redoStack.clear();
    }

    // Membatalkan perubahan terakhir (Undo).
    public void undo() {
        if (!undoStack.isEmpty()) {
            redoStack.push(currentText);
            currentText = undoStack.pop();
        } else {
            System.out.println("-> Tidak ada riwayat perubahan untuk di-undo.");
        }
    }

    // Mengembalikan perubahan yang telah di-undo sebelumnya (Redo).
    public void redo() {
        if (!redoStack.isEmpty()) {
            undoStack.push(currentText);
            currentText = redoStack.pop();
        } else {
            System.out.println("-> Tidak ada perubahan yang dapat di-redo.");
        }
    }

    public String getCurrentText() {
        return currentText;
    }

    public static void demo() {
        System.out.println("=== DEMO FITUR UNDO/REDO (STACK) ===");
        StackUndoRedo editor = new StackUndoRedo();

        editor.addText("Selamat datang");
        System.out.println("Teks saat ini: \"" + editor.getCurrentText() + "\"");

        editor.undo();
        System.out.println("Undo: \"" + editor.getCurrentText() + "\"");

        editor.redo();
        System.out.println("Redo: \"" + editor.getCurrentText() + "\"");
    }

    public static void main(String[] args) {
        demo();
    }
}