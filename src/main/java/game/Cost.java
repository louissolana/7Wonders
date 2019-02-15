package game;

public class Cost {
    private Resources res;
    private int quant;

    public Cost(Resources r, int qty) {
        this.res = r;
        this.quant = qty;
    }

    public Resources getRes() {
        return res;
    }

    public int getQuant() {
        return quant;
    }
}
