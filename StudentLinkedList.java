public class StudentLinkedList {
    private static class Node {
        private final String nim;
        private final String nama;
        private double nilai;
        private Node next;

        Node(String nim, String nama, double nilai) {
            this.nim = nim;
            this.nama = nama;
            this.nilai = nilai;
        }

        String getNim() { return nim; }
        String getNama() { return nama; }
        double getNilai() { return nilai; }
        Node getNext() { return next; }
        void setNilai(double nilai) { this.nilai = nilai; }
        void setNext(Node next) { this.next = next; }
    }

    private Node head;
    private Node tail;

    // Mencari mahasiswa berdasarkan NIM.
    private Node findStudent(String nim) {
        Node current = head;
        while (current != null) {
            if (current.getNim().equals(nim)) return current;
            current = current.getNext();
        }
        return null;
    }

    private boolean isValidGrade(double nilai) {
        return Double.isFinite(nilai) && nilai >= 0 && nilai <= 100;
    }

    // Menambahkan mahasiswa di akhir list; NIM harus unik.
    public boolean addStudent(String nim, String nama, double nilai) {
        if (nim == null || nama == null || nim.trim().isEmpty()
                || nama.trim().isEmpty() || !isValidGrade(nilai)) return false;
        nim = nim.trim();
        if (findStudent(nim) != null) return false;

        Node baru = new Node(nim, nama.trim(), nilai);
        if (head == null) head = baru;
        else tail.setNext(baru);
        tail = baru;
        return true;
    }

    // Menghapus node dan memperbaiki hubungan antar-node.
    public boolean removeStudent(String nim) {
        Node current = head;
        Node previous = null;
        while (current != null) {
            if (current.getNim().equals(nim)) {
                if (previous == null) head = current.getNext();
                else previous.setNext(current.getNext());
                if (current == tail) tail = previous;
                return true;
            }
            previous = current;
            current = current.getNext();
        }
        return false;
    }

    // Mengubah nilai mahasiswa yang ditemukan berdasarkan NIM.
    public boolean updateGrade(String nim, double nilai) {
        if (!isValidGrade(nilai)) return false;
        Node mahasiswa = findStudent(nim);
        if (mahasiswa == null) return false;
        mahasiswa.setNilai(nilai);
        return true;
    }

    // Menelusuri list dari head sampai node terakhir.
    public void display() {
        if (head == null) {
            System.out.println("Daftar mahasiswa kosong.");
            return;
        }
        Node current = head;
        int nomor = 1;
        while (current != null) {
            System.out.println(nomor + ". NIM: " + current.getNim()
                    + ", Nama: " + current.getNama()
                    + ", Nilai: " + current.getNilai());
            current = current.getNext();
            nomor++;
        }
    }

    public static void demo() {
        StudentLinkedList daftar = new StudentLinkedList();
        daftar.addStudent("12345", "Andi", 85);
        daftar.addStudent("67890", "Budi", 90);
        System.out.println("Daftar mahasiswa:");
        daftar.display();

        System.out.println("\nUpdate nilai Budi menjadi 95:");
        daftar.updateGrade("67890", 95);
        daftar.display();

        System.out.println("\nHapus mahasiswa Andi:");
        daftar.removeStudent("12345");
        daftar.display();
    }
    public static void main(String[] args) {
        demo();
    }
}
