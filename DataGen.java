import java.io.*;
import java.util.Random;

public class DataGen {
    public static void main(String[] args) throws IOException {
        int N = 10_000_000;
        System.out.println("Generating " + N + " integers...");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("input.txt"), 65536)) {
            Random rand = new Random(42);
            for (int i = 0; i < N; i++) {
                bw.write(Integer.toString(rand.nextInt(1_000_000)));
                bw.write(' ');
                if (i % 10 == 9) {
                    bw.write('\n');
                }
            }
        }

        System.out.println("Done! input.txt created.");
    }
}
