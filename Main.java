import java.util.Scanner;

/**
 * Program Utama Praktikum 2 - Data Structures and Algorithm Analysis
 * Kompilasi : javac *.java
 * Jalankan  : java Main
 */
public class Main {
    public static void runAllDemos() {
        System.out.println("==========================================================================");
        System.out.println("      PRAKTIKUM 2 - STRUKTUR DATA & ANALISIS ALGORITMA (KELOMPOK)         ");
        System.out.println("==========================================================================\n");

        // 1. Demo Queue
        CustomerQueue.demo();
        System.out.println("\n--------------------------------------------------------------------------\n");

        // 2. Demo Stack
        StackUndoRedo.demo();
        System.out.println("\n--------------------------------------------------------------------------\n");

        // 3. Demo Linked List
        StudentLinkedList.demo();
        System.out.println("\n--------------------------------------------------------------------------\n");

        // 4. Benchmark Waktu
        Benchmark.run();
    }

    public static void main(String[] args) {
        // Dijalankan dengan argumen "menu", tampilkan menu untuk memilih
        if (args.length > 0 && args[0].equalsIgnoreCase("menu")) {
            showInteractiveMenu();
        } else {
            runAllDemos();
        }
    }

    /**
     * Menu untuk memilih demo.
     */
    private static void showInteractiveMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n=== MENU UTAMA PRAKTIKUM 2 ===");
            System.out.println("1. Jalankan Semua Demo & Benchmark");
            System.out.println("2. Demo Queue (Customer Service)");
            System.out.println("3. Demo Stack (Undo/Redo Editor)");
            System.out.println("4. Demo Linked List (Data Mahasiswa)");
            System.out.println("5. Jalankan Benchmark Waktu (System.nanoTime)");
            System.out.println("6. Keluar");
            System.out.print("Pilih opsi (1-6): ");

            while (!scanner.hasNextInt()) {
                System.out.print("Input tidak valid! Masukkan angka 1-6: ");
                scanner.next();
            }
            choice = scanner.nextInt();
            System.out.println();

            switch (choice) {
                case 1:
                    runAllDemos();
                    break;
                case 2:
                    CustomerQueue.demo();
                    break;
                case 3:
                    StackUndoRedo.demo();
                    break;
                case 4:
                    StudentLinkedList.demo();
                    break;
                case 5:
                    Benchmark.run();
                    break;
                case 6:
                    System.out.println("Selesai. Terima kasih!");
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan pilih 1-6.");
            }
        } while (choice != 6);
        scanner.close();
    }
}
