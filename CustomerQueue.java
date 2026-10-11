import java.util.Scanner;

// Kelas Queue berbasis Linked List untuk Customer Service
class LinkedListQueue {
    // Kelas Node untuk merepresentasikan setiap pelanggan dalam Linked List
    private class Node {
        String customerName;
        Node next;

        public Node(String customerName) {
            this.customerName = customerName;
            this.next = null;
        }
    }

    private Node front; // Menunjuk ke pelanggan di depan antrean
    private Node rear;  // Menunjuk ke pelanggan di belakang antrean
    private int size;   // Ukuran antrean

    public LinkedListQueue() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }

    // 1. Fitur: Tambah pelanggan baru ke antrean (Enqueue)
    public void enqueue(String name) {
        enqueue(name, true);
    }

    // Overload enqueue dengan opsi cetak pesan (berguna untuk benchmark tanpa overhead I/O)
    public void enqueue(String name, boolean verbose) {
        Node newNode = new Node(name);
        if (rear == null) {
            front = rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
        if (verbose) {
            System.out.println("-> Pelanggan \"" + name + "\" berhasil ditambahkan ke antrean.");
        }
    }

    // 2. Fitur: Layani pelanggan / menghapus dari antrean (Dequeue)
    public String dequeue() {
        return dequeue(true);
    }

    // Overload dequeue dengan opsi cetak pesan
    public String dequeue(boolean verbose) {
        if (front == null) {
            if (verbose) {
                System.out.println("-> Antrean kosong! Tidak ada pelanggan untuk dilayani.");
            }
            return null;
        }
        
        String servedCustomer = front.customerName;
        front = front.next;
        
        if (front == null) {
            rear = null; // Reset rear jika antrean menjadi kosong
        }
        
        size--;
        if (verbose) {
            System.out.println("-> Melayani pelanggan: " + servedCustomer);
        }
        return servedCustomer;
    }

    // Mengambil nama pelanggan di posisi terdepan tanpa menghapusnya (Peek)
    public String peek() {
        if (front == null) return null;
        return front.customerName;
    }

    // Memeriksa apakah antrean kosong
    public boolean isEmpty() {
        return front == null;
    }

    // 3. Fitur: Menampilkan daftar pelanggan dalam antrean
    public void display() {
        if (front == null) {
            System.out.println("\nPelanggan dalam antrean: (Kosong)");
            return;
        }

        System.out.println("\nPelanggan dalam antrean (Total: " + size + " orang):");
        Node current = front;
        int index = 1;
        while (current != null) {
            System.out.println(index + ". " + current.customerName);
            current = current.next;
            index++;
        }
    }
}

// Kelas Utama dengan Menu Interaktif
public class CustomerQueue {
    // Metode demo otomatis sesuai dengan contoh output di spesifikasi PDF
    public static void demo() {
        System.out.println("=== DEMO SISTEM ANTREAN (QUEUE) ===");
        LinkedListQueue queue = new LinkedListQueue();
        
        // 1. Menambahkan pelanggan baru ke antrean (Enqueue)
        queue.enqueue("Andi");
        queue.enqueue("Budi");
        queue.enqueue("Siti");
        
        // Menampilkan antrean awal
        queue.display();
        
        System.out.println();
        // 2. Melayani pelanggan terdepan (Dequeue)
        queue.dequeue();
        
        // Menampilkan antrean setelah 1 pelanggan dilayani
        queue.display();
    }

    public static void main(String[] args) {
        // Jika dijalankan dengan argumen "demo", langsung jalankan demo tanpa interaksi
        if (args.length > 0 && args[0].equalsIgnoreCase("demo")) {
            demo();
            return;
        }

        Scanner scanner = new Scanner(System.in);
        LinkedListQueue queue = new LinkedListQueue();
        int choice;

        do {
            System.out.println("\n=== SIMULASI ANTREAN CUSTOMER SERVICE ===");
            System.out.println("1. Tambah Pelanggan Baru");
            System.out.println("2. Layani Pelanggan");
            System.out.println("3. Tampilkan Daftar Pelanggan");
            System.out.println("4. Jalankan Demo Otomatis");
            System.out.println("5. Keluar");
            System.out.print("Pilih menu (1-5): ");

            // Validasi input angka
            while (!scanner.hasNextInt()) {
                System.out.print("Input tidak valid! Masukkan angka 1-5: ");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // Membersihkan buffer *newline*

            switch (choice) {
                case 1:
                    System.out.print("Masukkan nama pelanggan: ");
                    String name = scanner.nextLine();
                    if (!name.trim().isEmpty()) {
                        queue.enqueue(name);
                    } else {
                        System.out.println("-> Nama pelanggan tidak boleh kosong!");
                    }
                    break;
                case 2:
                    queue.dequeue();
                    break;
                case 3:
                    queue.display();
                    break;
                case 4:
                    demo();
                    break;
                case 5:
                    System.out.println("Keluar dari program. Terima kasih!");
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan pilih antara 1-5.");
            }
        } while (choice != 5);

        scanner.close();
    }
}