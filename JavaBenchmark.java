import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;

public class JavaBenchmark {
    private static final String FILE = "input.txt";
    private static final int N = 10_000_000;

    public static void main(String[] args) throws Exception {
        System.out.println("--- JAVA I/O BENCHMARK (N = " + N + ") ---");
        testCustomFastIO();
        testBufferedReader();
        testScanner();
    }

    static void testScanner() throws Exception {
        long start = System.nanoTime();
        long sum = 0;

        try (Scanner sc = new Scanner(new FileInputStream(FILE))) {
            while (sc.hasNextInt()) {
                sum += sc.nextInt();
            }
        }

        long ms = (System.nanoTime() - start) / 1_000_000;
        System.out.printf("1. java.util.Scanner       : %5d ms (checksum: %d)%n", ms, sum);
    }

    static void testBufferedReader() throws Exception {
        long start = System.nanoTime();
        long sum = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            StringTokenizer st;
            String line;
            while ((line = br.readLine()) != null) {
                st = new StringTokenizer(line);
                while (st.hasMoreTokens()) {
                    sum += Integer.parseInt(st.nextToken());
                }
            }
        }

        long ms = (System.nanoTime() - start) / 1_000_000;
        System.out.printf("2. BufferedReader + Tok    : %5d ms (checksum: %d)%n", ms, sum);
    }

    static void testCustomFastIO() throws Exception {
        long start = System.nanoTime();
        long sum = 0;

        try (FileInputStream fis = new FileInputStream(FILE)) {
            byte[] buf = new byte[1 << 16];
            int len = 0, ptr = 0;

            for (int i = 0; i < N; i++) {
                int c = 0;
                while (true) {
                    if (ptr >= len) {
                        len = fis.read(buf);
                        ptr = 0;
                        if (len <= 0) {
                            break;
                        }
                    }

                    c = buf[ptr++];
                    if (c > ' ') {
                        break;
                    }
                }

                int val = 0;
                while (c > ' ') {
                    val = val * 10 + (c - '0');
                    if (ptr >= len) {
                        len = fis.read(buf);
                        ptr = 0;
                        if (len <= 0) {
                            break;
                        }
                    }
                    c = buf[ptr++];
                }

                sum += val;
            }
        }

        long ms = (System.nanoTime() - start) / 1_000_000;
        System.out.printf("3. Custom 64KB Byte Parser : %5d ms (checksum: %d)%n", ms, sum);
    }
}
