public class PolicyIteration implements Algorithm {
    private int size;
    private double gamma;
    private double[] Noise;
    private GridNode[][] map;

    public PolicyIteration(double gamma, double[] Noise, GridNode[][] map) {
        this.size = map.length;
        this.gamma = gamma;
        this.Noise = Noise;
        this.map = map;
    }

    public void execute() {
        double[][] new_num = new double[size][size];
        int[] row = {1, 0, -1, 0};
        int[] col = {0, 1, 0, -1};
        boolean loop = true;
        int times = 0;
        while (loop) {
            times++;
        double difference = 0;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (map[i][j].getCheck()) {
                        new_num[i][j] = map[i][j].getNum();
                        continue;
                    }
                    double[] value = new double[4];
                    for (int m = 0; m < 4; m++) {
                        int cc = i + col[m];
                        int rr = j + row[m];
                        if (cc >= 0 && cc < size && rr >= 0 && rr < size) {
                            value[m] = map[cc][rr].getNum();
                        } else value[m] = map[i][j].getNum();
                    }
                    new_num[i][j] = map[i][j].getValue(value, gamma);
                    difference = Math.max(Math.abs(new_num[i][j] - map[i][j].getNum()), difference);
                }
            }
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    map[i][j].setNum(new_num[i][j]);
                }
            }
            if (difference >= 0.0001 && !GridNode.getBest()) {
                GridNode.setBest();
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        if (map[i][j].getCheck()) continue;
                        double[] value = new double[4];
                        for (int m = 0; m < 4; m++) {
                            int cc = i + col[m];
                            int rr = j + row[m];
                            if (cc >= 0 && cc < size && rr >= 0 && rr < size) {
                                value[m] = map[cc][rr].getNum();
                            } else value[m] = map[i][j].getNum();
                        }
                        boolean change = map[i][j].rotate(value, gamma);
                        if (change) GridNode.setNBest();
                    }
                }
            } else if (difference < 0.0001 && GridNode.getBest()) loop = false;
        }
        printResult(map, times);
    }

    private void printResult(GridNode[][] map, int times) {
        System.out.println("The result of policy iteration will be showed as followings:");
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