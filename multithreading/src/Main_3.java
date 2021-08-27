import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * exploring runtime class in java
 */
public class Main_3 {

    public static void main(String[] args) throws IOException {

        // get the number of cores int the cpu
        int coreCount = Runtime.getRuntime().availableProcessors();
        System.out.println(coreCount);

        Runtime rt = Runtime.getRuntime();
        String[] commands = {"ls"};
        Process proc = rt.exec(commands);
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        // Read the output from the command
        System.out.println("Here is the standard output of the command:\n");
        String s = null;
        while ((s = stdInput.readLine()) != null) {
            System.out.println(s);
        }
    }
}
