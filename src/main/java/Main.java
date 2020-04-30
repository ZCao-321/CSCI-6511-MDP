import java.io.*;

public class Main {
    private static int size;
    private static double gamma;
    private static double[] Noise = new double[4];
    private static GridNode[][] map;

    public static void main(String[] args) throws Exception{
        Initialization(args);
        File file = new File(args[0]);
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        readFile(in);
        runAlg();
    }

    private static void Initialization(String[] args) {
        if (args.length == 1) return;
        String alert = "Please invoke it as follows:" + System.lineSeparator() +
                "java -jar xxx.jar <File>" + System.lineSeparator() +
                "The content of file needs to be standardized.";
        System.out.println(alert);
        System.exit(0);
    }

    private static void readFile(BufferedReader in) throws IOException {
        String str = in.readLine();
        while (str.length() == 0 || str.charAt(0) == '#') str = in.readLine();
        size = Integer.parseInt(str.split(" ")[0]);
        str = in.readLine();
        while (str.length() == 0 || str.charAt(0) == '#') str = in.readLine();
        gamma = Double.parseDouble(str.split(" ")[0]);
        str = in.readLine();
        while (str.length() == 0 || str.charAt(0) == '#') str = in.readLine();
        String[] strs = str.split(",");
        for (int i = 0; i < strs.length; i++) {
            while (strs[i].indexOf(' ') == 0) strs[i] = strs[i].substring(1);
            if (strs[i].charAt(0) > '9' || strs[i].charAt(0) < '0') continue;
            Noise[3-i] = Double.parseDouble(strs[i].split("#")[0]);
        }
        str = in.readLine();
        while (str.length() == 0 || str.charAt(0) == '#') str = in.readLine();
        map = new GridNode[size][size];
        for (int i = 0; i < size; i++) {
            String[] get = str.split(",");
            for (int j = 0; j < size; j++) {
                map[i][j] = new GridNode();
                while (get[j].indexOf(' ') == 0) get[j] = get[j].substring(1);
                str = get[j].split("#")[0];
                if (str.charAt(0) != 'X') {
                    map[i][j].setNum(Double.parseDouble(str));
                    map[i][j].setCheck(true);
                } else map[i][j].setDirection(Noise);
            }
            str = in.readLine();
        }
    }

    private static void runAlg() throws CloneNotSupportedException {
        Algorithm alg1 = new ValueIteration(gamma, Noise, map);
        GridNode[][] map2 = new GridNode[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                map2[i][j] = map[i][j].clone();
            }
        }
        Algorithm alg2 = new PolicyIteration(gamma, Noise, map2);
        // Running value iteration algorithm
        long start1 = System.currentTimeMillis();
        alg1.execute();
        long end1 = System.currentTimeMillis();
        System.out.println("Time Taken: " + (end1 - start1) + " ms");
        System.out.println();
        // Running policy iteration algorithm
        long start2 = System.currentTimeMillis();
        alg2.execute();
        long end2 = System.currentTimeMillis();
        System.out.println("Time Taken: " + (end2 - start2) + " ms");
    }
}