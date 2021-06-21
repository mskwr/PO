import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

/**
  Zadanie 2 - interpreter pewnego prostego języka programowania o nazwie ROBSON
  Autor: Michał Skwarek
 */

public class Robson {

    // Parametr, w którym trzymam program w języku ROBSON już
    // przekonwertowany z pliku JSON i wykonywalny funkcją wykonaj
    private static Instrukcja program;

    // Wyjątki, które muszą być w funkcjach fromJSON i wykonaj - zgodnie z treścią
    static class NieprawidlowyProgram extends Exception {}
    static class BladWykonania extends Exception {}

    public static void fromJSON(String filename) throws NieprawidlowyProgram {
        Konwerter k = new Konwerter(null);
        k.fromJson(filename);
        // Parametrowi przydzielam objekt klasy Instrukcja, który
        // jest przekonwertowanym programem w języku ROBSON
        program = k.program();
    }

    public static void toJSON(String filename) {
        Konwerter k = new Konwerter(program);

        // Tworzę plik o podanej nazwie, w którym będzie program w języku
        // ROBSON i formacie JSON wygenerowany na podstawie obiektu klasy Instrukcja
        try {
            k.toJson(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static double wykonaj() throws BladWykonania {
        // Zmienne użyte w programie trzymam w Hashmapie w formie:
        // <nazwa zmiennej, wartość zmiennej>
        HashMap<String, Double> zmienne = new HashMap<>();
        return program.wykonaj(zmienne);
    }

    public static void toJava(String filename) {
        HashMap<String, Double> zmienne = new HashMap<>();
        double wynik = program.wykonaj(zmienne);
        String nazwa = filename.substring(0, filename.length() - 5);
        StringBuilder tojava = new StringBuilder(("public class " + nazwa + "{ public static void main(String[] args) {"));

        // Inicjalizuje wszystkie występujące w programie zmienne na 0
        for (String i: zmienne.keySet()) {
            tojava.append("double ").append(i).append(" = 0;");
        }

        String s = (tojava.toString() + program + "System.out.println(" + wynik + ");}}");
        File plik = new File(filename);

        try {
            Files.writeString(Path.of(filename), s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Test za pomocą specjalnej klasy testującej Tester.
    // Sprawdza poprawność wszystkich zaimplementowanych funkcji na przykładzie
    // liczenia n-tej liczby Fibonacciego, silni i dwumianu Newtona.
    public static void main(String[] args) throws NieprawidlowyProgram, BladWykonania {
        Robson robson = new Robson();
        Tester t = new Tester(robson);
        t.testuj();
    }

}