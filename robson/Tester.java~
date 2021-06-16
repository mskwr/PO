/**
 * Klasa testująca program. Tworzy pliki z programami w języku Robson
 * w formacie JSON, następnie za pomocą funkcji fromJSON, toJSON, wykonaj
 * je wykonuje i zwraca wynik, przy okazji usuwając powstałe pliki.
 */

public class Tester {

    Robson robson;

    Tester(Robson robson) {
        this.robson = robson;
    }

    private void testFibo() {
        try {
            robson.fromJSON("fibo.json");
        } catch (Robson.NieprawidlowyProgram nieprawidlowyProgram) {
            nieprawidlowyProgram.printStackTrace();
        }

        robson.toJSON("fibo_toJSON.json");
        robson.toJava("fibo_toJava.java");
        double wynik = 0;

        try {
            wynik = robson.wykonaj();
        } catch (Robson.BladWykonania bladWykonania) {
            bladWykonania.printStackTrace();
        }

        System.out.println("TEST1 - LICZBY FIBONACCIEGO");
        System.out.println("Dziesiata liczba Fibonacciego to: " + wynik);
        System.out.println("Plik JSON z przekonwertowanym programem: fibo_toJSON.json");
        System.out.println("Plik wykonywalny Java z programem: fibo_toJava.java");
        System.out.println();
    }

    private void testSilnia() {
        try {
            robson.fromJSON("silnia.json");
        } catch (Robson.NieprawidlowyProgram nieprawidlowyProgram) {
            nieprawidlowyProgram.printStackTrace();
        }

        robson.toJSON("silnia_toJSON.json");
        robson.toJava("silnia_toJava.java");
        double wynik = 0;

        try {
            wynik = robson.wykonaj();
        } catch (Robson.BladWykonania bladWykonania) {
            bladWykonania.printStackTrace();
        }

        System.out.println("TEST2 - SILNIA");
        System.out.println("Silnia z 10 to: " + wynik);
        System.out.println("Plik JSON z przekonwertowanym programem: silnia_toJSON.json");
        System.out.println("Plik wykonywalny Java z programem: silnia_toJava.java");
        System.out.println();
    }

    private void testDwumian() {
        try {
            robson.fromJSON("dwumian.json");
        } catch (Robson.NieprawidlowyProgram nieprawidlowyProgram) {
            nieprawidlowyProgram.printStackTrace();
        }

        robson.toJSON("dwumian_toJSON.json");
        robson.toJava("dwumian_toJava.java");
        double wynik = 0;

        try {
            wynik = robson.wykonaj();
        } catch (Robson.BladWykonania bladWykonania) {
            bladWykonania.printStackTrace();
        }

        System.out.println("TEST3 - DWUMIAN NEWTONA");
        System.out.println("Dwumian Newtona 10 nad 3 to: " + wynik);
        System.out.println("Plik JSON z przekonwertowanym programem: dwumian_toJSON.json");
        System.out.println("Plik wykonywalny Java z programem: dwumian_toJava.java");
        System.out.println();
    }

    public void testuj() {
        testFibo();
        testSilnia();
        testDwumian();
    }

}
