import java.util.Scanner;

// Kelas Node untuk merepresentasikan setiap pelanggan dalam Linked List
class Node {
    String customerName;
    Node next;

    public Node(String customerName) {
        this.customerName = customerName;
        this.next = null;
    }
}

// Kelas Queue berbasis Linked List untuk Customer Service
class LinkedListQueue {
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
        Node newNode = new Node(name);
        if (rear == null) {
            front = rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
        System.out.println("-> Pelanggan \"" + name + "\" berhasil ditambahkan ke antrean.");
    }

    // 2. Fitur: Layani pelanggan / menghapus dari antrean (Dequeue)
    public void dequeue() {
        if (front == null) {
            System.out.println("-> Antrean kosong! Tidak ada pelanggan untuk dilayani.");
            return;
        }
        
        String servedCustomer = front.customerName;
        front = front.next;
        
        if (front == null) {
            rear = null; // Reset rear jika antrean menjadi kosong
        }
        
        size--;
        System.out.println("-> Melayani pelanggan: " + servedCustomer);
    }

    // 3. Fitur: Menampilkan daftar pelanggan dalam antrean
    public void display() {
        if (front == null) {
            System.out.println("\nPelanggan dalam antrean: (Kosong)");
            return;
        }

        System.out.println("\nPelanggan dalam antrean:");
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
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LinkedListQueue queue = new LinkedListQueue();
        int choice;

        do {
            System.out.println("\n=== SIMULASI ANTREAN CUSTOMER SERVICE ===");
            System.out.println("1. Tambah Pelanggan Baru");
            System.out.println("2. Layani Pelanggan");
            System.out.println("3. Tampilkan Daftar Pelanggan");
            System.out.println("4. Keluar");
            System.out.print("Pilih menu (1-4): ");

            // Validasi input angka
            while (!scanner.hasNextInt()) {
                System.out.print("Input tidak valid! Masukkan angka 1-4: ");
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
                    System.out.println("Keluar dari program. Terima kasih!");
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan pilih antara 1-4.");
            }
        } while (choice != 4);

        scanner.close();
    }
}