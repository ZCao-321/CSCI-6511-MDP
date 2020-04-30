public class ValueIteration implements Algorithm {
    private int size;
    private double gamma;
    private double[][] Noise;
    private GridNode[][] map;

    public ValueIteration(double gamma, double[] Noise, GridNode[][] map) {
        this.size = map.length;
        this.gamma = gamma;
        this.map = map;
        this.Noise = new double[4][4];
        this.Noise[0] = new double[]{Noise[3], Noise[2], Noise[0], Noise[1]};
        this.Noise[1] = new double[]{Noise[2], Noise[3], Noise[1], Noise[0]};
        this.Noise[2] = new double[]{Noise[0], Noise[2], Noise[3], Noise[1]};
        this.Noise[3] = new double[]{Noise[2], Noise[0], Noise[1], Noise[3]};
    }

    public void execute() {
        double[][] new_num = new double[size][size];
        int[] row = {1, 0, -1, 0};
        int[] col = {0, 1, 0, -1};
        boolean loop = true;
        int times = 0;
        while (loop) {
            times++;
            loop = false;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (map[i][j].getCheck()) {
                        new_num[i][j] = map[i][j].getNum();
                        continue;
                    }
                    new_num[i][j] = Double.MIN_VALUE;
                    for (int m = 0; m < 4; m++) {
                        double temp = 0;
                        for (int n = 0; n < 4; n++) {
                            int cc = i + col[n];
                            int rr = j + row[n];
                            if (cc >= 0 && cc < size && rr >= 0 && rr < size) {
                                temp += map[cc][rr].getNum() * gamma * Noise[m][n];
                            } else temp += map[i][j].getNum() * gamma * Noise[m][n];
                        }
                        new_num[i][j] = Math.max(temp, new_num[i][j]);
                    }
                    if (new_num[i][j] - map[i][j].getNum() > 0.0001) loop = true;
                }
            }
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    map[i][j].setNum(new_num[i][j]);
                }
            }
        }
        printResult(map, times);
    }

    private void printResult(GridNode[][] map, int times) {
        System.out.println("The result of value iteration will be showed as followings:");
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                System.out.printf("%-10.2f",map[i][j].getNum());
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println("Number of iterations Taken: " + times);
    }
}
