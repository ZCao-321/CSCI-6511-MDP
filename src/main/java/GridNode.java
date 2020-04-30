public class GridNode implements Cloneable{
    //right, down, left, up
    //0, 1, 2, 3
    private double num;
    private boolean check;
    private double[] direction = new double[4];
    private static boolean best = false;

    public GridNode(){
    }

    public static boolean getBest() { return best; }

    public static void setBest() { best = true; }

    public static void setNBest() { best = false; }

    public void setNum(double num) {
        this.num = num;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public double getNum() {
        return this.num;
    }

    public boolean getCheck() {
        return this.check;
    }

    public void setDirection(double[] Noise) {
        direction[0] = Noise[3];
        direction[2] = Noise[0];
        direction[1] = Noise[1];
        direction[3] = Noise[2];
    }

    public double getValue(double[] value, double gamma) {
        double ans = 0;
        for (int i = 0; i < 4; i++)
            ans += value[i] * gamma * direction[i];
        return ans;
    }

    public boolean rotate(double[] value, double gamma) {
        boolean change = false;
        double temp = Double.MIN_VALUE;
        int index = 0;
        for (int i = 0; i < 4; i++) {
            double res = this.getValue(value, gamma);
            if (res > temp) {
                temp = res;
                index = i;
            }
            this.rotate();
        }
        if (index != 0) change = true;
        while(index-- > 0) this.rotate();
        return change;
    }

    public void rotate() {
        double temp = direction[3];
        for (int i = 3; i > 0; i--)
            direction[i] = direction[i-1];
        direction[0] = temp;
    }

    protected GridNode clone() throws CloneNotSupportedException {
        GridNode clone = null;
        try {
            clone = (GridNode) super.clone();
        }
        catch (CloneNotSupportedException e){
            throw new RuntimeException(e);
        }
        return clone;
    }
}
