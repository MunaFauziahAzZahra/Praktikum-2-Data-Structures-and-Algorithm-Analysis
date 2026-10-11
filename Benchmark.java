import java.util.Stack;

/**
 * Kelas Benchmark untuk membandingkan efisiensi waktu eksekusi Queue, Stack, dan Linked List menggunakan System.nanoTime().
 */
public class Benchmark {

    // Struktur data penyimpan hasil pengujian untuk tiap N
    static class Result {
        int n;
        long queueEnqueueNs;
        long queueDequeueNs;
        long stackPushNs;
        long stackPopNs;
        long listInsertNs;
        long listSearchAvgNs; // Rata-rata 100x pencarian node terakhir
        long listDeleteHeadNs;

        Result(int n) {
            this.n = n;
        }
    }

    /**
     * Melakukan warm-up JVM JIT compiler agar hasil pengukuran stabil.
     */
    private static void performWarmUp() {
        System.out.println("-> Melakukan Warm-Up JVM...");
        int warmUpN = 5000;
        
        // Warm-up Queue
        LinkedListQueue q = new LinkedListQueue();
        for (int i = 0; i < warmUpN; i++) q.enqueue("Pelanggan_" + i, false);
        for (int i = 0; i < warmUpN; i++) q.dequeue(false);

        // Warm-up Stack
        Stack<String> s = new Stack<>();
        for (int i = 0; i < warmUpN; i++) s.push("Teks_" + i);
        for (int i = 0; i < warmUpN; i++) s.pop();

        // Warm-up Linked List
        StudentLinkedList list = new StudentLinkedList();
        for (int i = 0; i < warmUpN; i++) list.addLastDirect("NIM_" + i, "Nama_" + i, 80.0);
        for (int k = 0; k < 50; k++) list.contains("NIM_" + (warmUpN - 1));
        list.clear();
        System.out.println("-> Warm-Up selesai.\n");
    }

    /**
     * Mengukur performa untuk nilai N tertentu.
     */
    public static Result runBenchmark(int n) {
        Result res = new Result(n);
        long start, end;

        // 1. PENGUJIAN QUEUE (LinkedListQueue)
        LinkedListQueue queue = new LinkedListQueue();
        
        // Enqueue N elemen
        start = System.nanoTime();
        for (int i = 0; i < n; i++) {
            queue.enqueue("Cust_" + i, false);
        }
        end = System.nanoTime();
        res.queueEnqueueNs = end - start;

        // Dequeue N elemen
        start = System.nanoTime();
        for (int i = 0; i < n; i++) {
            queue.dequeue(false);
        }
        end = System.nanoTime();
        res.queueDequeueNs = end - start;

        // 2. PENGUJIAN STACK (java.util.Stack)
        Stack<String> stack = new Stack<>();

        // Push N elemen
        start = System.nanoTime();
        for (int i = 0; i < n; i++) {
            stack.push("Text_" + i);
        }
        end = System.nanoTime();
        res.stackPushNs = end - start;

        // Pop N elemen
        start = System.nanoTime();
        for (int i = 0; i < n; i++) {
            stack.pop();
        }
        end = System.nanoTime();
        res.stackPopNs = end - start;

        // 3. PENGUJIAN LINKED LIST (StudentLinkedList)
        StudentLinkedList list = new StudentLinkedList();

        // Insertion N data mahasiswa (O(1) per insert ke tail)
        start = System.nanoTime();
        for (int i = 0; i < n; i++) {
            list.addLastDirect(String.valueOf(1000000 + i), "Mhs_" + i, 85.0);
        }
        end = System.nanoTime();
        res.listInsertNs = end - start;

        // Pencarian node terakhir (Worst Case O(n)), diulang 100 kali dicari rata-ratanya
        String targetNim = String.valueOf(1000000 + (n - 1));
        long totalSearchNs = 0;
        int searchIterations = 100;
        for (int k = 0; k < searchIterations; k++) {
            start = System.nanoTime();
            list.contains(targetNim);
            end = System.nanoTime();
            totalSearchNs += (end - start);
        }
        res.listSearchAvgNs = totalSearchNs / searchIterations;

        // Penghapusan 1000 elemen pertama (head deletion)
        int deleteCount = Math.min(n, 1000);
        start = System.nanoTime();
        for (int i = 0; i < deleteCount; i++) {
            list.removeStudent(String.valueOf(1000000 + i));
        }
        end = System.nanoTime();
        res.listDeleteHeadNs = end - start;

        return res;
    }

    // Konversi nanodetik ke miliseconds dengan 3 desimal.
    private static String toMs(long ns) {
        return String.format("%.3f ms", ns / 1_000_000.0);
    }

    // Konversi nanodetik ke microseconds dengan 2 desimal.
    private static String toUs(long ns) {
        return String.format("%.2f µs", ns / 1_000.0);
    }

    /**
     * Menjalankan seluruh rangkaian benchmark dan menampilkan tabel hasil.
     */
    public static void run() {
        System.out.println("==========================================================================");
        System.out.println("               BENCHMARK EFISIENSI WAKTU (System.nanoTime())               ");
        System.out.println("==========================================================================");

        performWarmUp();

        int[] dataSizes = {1000, 10000, 100000};
        Result[] results = new Result[dataSizes.length];

        for (int i = 0; i < dataSizes.length; i++) {
            int n = dataSizes[i];
            System.out.println("Sedang menguji N = " + String.format("%,d", n) + "...");
            results[i] = runBenchmark(n);
        }

        System.out.println("\n==========================================================================");
        System.out.println("                       TABEL HASIL PENGUKURAN (WAKTU)                     ");
        System.out.println("==========================================================================");
        System.out.printf("%-10s | %-16s | %-16s | %-16s | %-16s%n",
                "N", "Queue Enqueue", "Queue Dequeue", "Stack Push", "Stack Pop");
        System.out.println("--------------------------------------------------------------------------");
        for (Result r : results) {
            System.out.printf("%-10s | %-16s | %-16s | %-16s | %-16s%n",
                    String.format("%,d", r.n),
                    toMs(r.queueEnqueueNs),
                    toMs(r.queueDequeueNs),
                    toMs(r.stackPushNs),
                    toMs(r.stackPopNs));
        }

        System.out.println("\n--------------------------------------------------------------------------");
        System.out.println("                 HASIL PENGUKURAN LINKED LIST                            ");
        System.out.println("--------------------------------------------------------------------------");
        System.out.printf("%-10s | %-20s | %-26s | %-20s%n",
                "N", "Insert N Mahasiswa", "Search Node Terakhir (Avg)", "Remove (1k Pertama)");
        System.out.println("--------------------------------------------------------------------------");
        for (Result r : results) {
            System.out.printf("%-10s | %-20s | %-26s | %-20s%n",
                    String.format("%,d", r.n),
                    toMs(r.listInsertNs),
                    toUs(r.listSearchAvgNs) + " (" + toMs(r.listSearchAvgNs) + ")",
                    toMs(r.listDeleteHeadNs));
        }
        System.out.println("==========================================================================\n");
    }

    public static void main(String[] args) {
        run();
    }
}
