import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class VendingMachine {

    int insertSum = 0;
    int valProdus = 0;
    int rest = 0;
    int codMoneda;
    int valoareMoneda;
    int valMoneda1 = 1;
    int valMoneda2 = 5;
    int valMoneda3 = 10;
    String produs;
    private VMType vmType;
    private Currency currency;
    private LinkedHashMap<Product, Integer> productStock;
    private HashMap<Coin, Integer> coinStock;

    public VendingMachine(String s) {
        initialize( s );
    }

    public void initialize(String filePath) {
        Path path = Paths.get( filePath );
        List<String> lines = null;

        try {
            lines = Files.readAllLines( path );
            System.out.println( lines );
        } catch (IOException e) {
            e.printStackTrace();
        }

        vmType = VMType.valueOf( lines.get( 0 ) );
        currency = Currency.valueOf( lines.get( 1 ) );
        productStock = new LinkedHashMap<>();
        coinStock = new LinkedHashMap<>();

        for (int i = 2; i < lines.size(); i++) {
            String line = lines.get( i );
            String[] parts = line.split( " " );
            if (Integer.valueOf( parts[0] ) < 100) {
                Product product = new Product( Integer.valueOf( parts[0] ), parts[1], Integer.valueOf( parts[2] ), Integer.valueOf( parts[3] ) );
                productStock.put( product, Integer.valueOf( parts[4] ) );
            } else if (Integer.valueOf( parts[0] ) == 100) {
                for (int j = i; j < lines.size(); j++) {
                    String line2 = lines.get( j );
                    String[] parts2 = line2.split( " " );
                    Coin coin = new Coin( Integer.valueOf( parts2[0] ), parts2[1], Integer.valueOf( parts2[2] ) );
                    coinStock.put( coin, Integer.valueOf( parts2[3] ) );
                }
            } else {
                break;
            }
        }
    }

    public void displayMenu() {
        System.out.println( "this is a " + vmType + " vending machine" );
        System.out.println();
        System.out.println( "cod " + " produs " + " pret " + " gramaj" );
        System.out.println();
        for (Product product : productStock.keySet()) {
            if (product.getCod() < 100) {
                System.out.println( product.getCod() + "\t" + product.getName() + "\t" + product.getPrice() + " " + currency + "\t" + product.getSize() );
            } else {
                break;
            }
        }
        System.out.println();
    }

    public void buyProduct() {
        System.out.println( productStock.values() );
        System.out.println( "alegeti un produs:" );
        Scanner scanner = new Scanner( System.in );
        int option = scanner.nextInt();

        if (option == 0) {
            System.exit( 0 );
        }

        for (Product p : productStock.keySet()) {
            if (p.getCod() == option) {
                System.out.println( "ati selectat produsul: " + p.getName() );
                produs = p.getName();
                valProdus = p.getPrice();
                Integer quantity = productStock.get( p );
                if (quantity > 0) {
                    productStock.put( p, quantity - 1 );
                    System.out.println( productStock.values() );
                    System.out.println( "introduceti monezi:" );
                    this.insertCoins();
                    System.out.println();
                } else {
                    System.out.println( "nu sunt produse suficiente" );
                    System.out.println();
                }
            }
        }
    }

    public void insertCoins() {
        System.out.println( "cod " + " tip moneda " + " valoare " );
        for (Coin coin : coinStock.keySet()) {
            System.out.println( coin.getCodCoin() + "\t" + "\t" + coin.getNameCoin() + "\t" + "\t" + "\t" + coin.getPriceCoin() );
        }

        while (true) {

            Scanner scanner = new Scanner( System.in );
            int option = scanner.nextInt();

            if (option == 100) {
                restCoin();
                gata();
            }

            for (Coin c : coinStock.keySet()) {
                if (c.getCodCoin() == option) {
                    codMoneda = c.getCodCoin();
                    valoareMoneda = c.getPriceCoin();
                    Integer value = coinStock.get( c );
                    coinStock.put( c, value + 1 );
                    insertSum = insertSum + c.getPriceCoin();
                    System.out.println( "valoare introdusa" + "\t" + insertSum + "\t" + currency );
                    System.out.println( "tastati codul monedei daca vreti sa mai adaugati bani !!!" );
                    System.out.println( "tastati codul 100 daca nu mai adaugati bani !!!" );
                }
            }
            System.out.println( coinStock.values() );
        }
    }

    public void gata() {
        System.exit( 0 );
    }

    public void restCoin() {
        System.out.println( "ati achizitionat produsul: " + produs );
        rest = insertSum - valProdus;
        System.out.println( "rest: " + rest + currency );

        if (insertSum > valProdus) {
            if (rest > valMoneda3) {
                rest = rest - valMoneda3;
                for (Coin coinRest3 : coinStock.keySet()) {
                    if (coinRest3.getCodCoin() == 100 + 3) {
                        Integer valueRest3 = coinStock.get( coinRest3 );
                        coinStock.put( coinRest3, valueRest3 - 1 );
                        while (rest > 0) {
                            if (rest >= valMoneda2) {
                                for (Coin coinRest2 : coinStock.keySet()) {
                                    if (coinRest2.getCodCoin() == 100 + 2) {
                                        Integer valueRest2 = coinStock.get( coinRest2 );
                                        coinStock.put( coinRest2, valueRest2 - 1 );
                                        rest = rest - valMoneda2;
                                    }
                                }
                            } else {
                                for (Coin coinRest1 : coinStock.keySet()) {
                                    if (coinRest1.getCodCoin() == 100 + 1) {
                                        Integer valueRest1 = coinStock.get( coinRest1 );
                                        coinStock.put( coinRest1, valueRest1 - 1 );
                                        rest = rest - valMoneda1;
                                    }
                                }
                            }
                        }
                    }
                }

            } else {
                while (rest > 0) {
                    if (rest >= valMoneda2) {
                        for (Coin coinRest2 : coinStock.keySet()) {
                            if (coinRest2.getCodCoin() == 100 + 2) {
                                Integer valueRest2 = coinStock.get( coinRest2 );
                                coinStock.put( coinRest2, valueRest2 - 1 );
                                rest = rest - valMoneda2;
                            }
                        }
                    } else {
                        for (Coin coinRest1 : coinStock.keySet()) {
                            if (coinRest1.getCodCoin() == 100 + 1) {
                                Integer valueRest1 = coinStock.get( coinRest1 );
                                coinStock.put( coinRest1, valueRest1 - 1 );
                                rest = rest - valMoneda1;
                            }
                        }
                    }
                }
            }
        }
        System.out.println( coinStock.values() );
    }

    public void start() {
        while (true) {
            this.displayMenu();
            this.buyProduct();
        }
    }
}
