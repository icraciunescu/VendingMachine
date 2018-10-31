public class Coin {

    private int codCoin;
    private String nameCoin;
    private int priceCoin;


    public Coin(int codCoin, String nameCoin, int priceCoin) {
        this.codCoin = codCoin;
        this.nameCoin = nameCoin;
        this.priceCoin = priceCoin;

    }

    public int getCodCoin() {
        return codCoin;
    }

    public String getNameCoin() {
        return nameCoin;
    }

    public int getPriceCoin() {
        return priceCoin;
    }

}
