import com.google.gson.Gson;
import java.util.HashMap;
import java.util.List;

/**
 * Klasa, która przechowuje Instrukcje - prawie wszystko w programie jest
 * instrukcją z funkcją liczenia ich wartości.
 */

public class Instrukcja {

    // Wszystkie możliwe argumenty instrukcji
    private String typ = null;
    private String nazwa = null;
    private List<Instrukcja> instrukcje = null;
    private Instrukcja argument = null;
    private Instrukcja argument1 = null;
    private Instrukcja argument2 = null;
    private Instrukcja warunek = null;
    private Instrukcja blok_prawda = null;
    private Instrukcja blok_falsz = null;
    private Instrukcja blok = null;
    // Argument wartosc czasem występuje jako double, a czasem Instrukcja
    private Object wartosc = null;

    private double wykonajBlok(HashMap<String, Double> zmienne) {
        Instrukcja[] instr = instrukcje.toArray(new Instrukcja[0]);
        int i;

        for (i = 0; i < instr.length - 1; ++i)
            instr[i].wykonaj(zmienne);

        return instr[i].wykonaj(zmienne);
    }

    private double wykonajIf(HashMap<String, Double> zmienne) {
        if (warunek.wykonaj(zmienne) == 1)
            return blok_prawda.wykonaj(zmienne);
        else if (blok_falsz != null) {
            return blok_falsz.wykonaj(zmienne);
        }
        else {
            return 0;
        }
    }

    private double wykonajWhile(HashMap<String, Double> zmienne) {
        while (warunek.wykonaj(zmienne) == 1)
            blok.wykonaj(zmienne);

        return 0;
    }

    private double wykonajPrzypisanie(HashMap<String, Double> zmienne) {
        // Object wartosc jest typu Instrukcja, ale nie można łatwo
        // rzutować - dlatego jeszcze raz konwertuję na format JSON
        // i z powrotem, ale już do zmiennej Instrukcja
        Gson gson = new Gson();
        String json = new Gson().toJson(wartosc);
        Instrukcja i = gson.fromJson(json, Instrukcja.class);

        // Jeśli zmienna już istnieje, to ją nadpisuję,
        // a jak nie to ją tworzę
        if (zmienne.get(nazwa) == null)
            zmienne.put(nazwa, i.wykonaj(zmienne));
        else
            zmienne.replace(nazwa, i.wykonaj(zmienne));

        return zmienne.get(nazwa);
    }

    private double wykonajPlus(HashMap<String, Double> zmienne) {
        return (argument1.wykonaj(zmienne) + argument2.wykonaj(zmienne));
    }

    private double wykonajMinus(HashMap<String, Double> zmienne) {
        return (argument1.wykonaj(zmienne) - argument2.wykonaj(zmienne));
    }

    private double wykonajRazy(HashMap<String, Double> zmienne) {
        return (argument1.wykonaj(zmienne) * argument2.wykonaj(zmienne));
    }

    private double wykonajDzielenie(HashMap<String, Double> zmienne) {
        return (argument1.wykonaj(zmienne) / argument2.wykonaj(zmienne));
    }

    private double wykonajAnd(HashMap<String, Double> zmienne) {
        if (argument1.wykonaj(zmienne) != 0 && argument2.wykonaj(zmienne) != 0)
            return 1;
        else
            return 0;
    }

    private double wykonajOr(HashMap<String, Double> zmienne) {
        if (argument1.wykonaj(zmienne) == 0 && argument2.wykonaj(zmienne) == 0)
            return 0;
        else
            return 1;
    }

    private double wykonajL(HashMap<String, Double> zmienne) {
        if (argument1.wykonaj(zmienne) < argument2.wykonaj(zmienne))
            return 1;
        else
            return 0;
    }

    private double wykonajH(HashMap<String, Double> zmienne) {
        if (argument1.wykonaj(zmienne) > argument2.wykonaj(zmienne))
            return 1;
        else
            return 0;
    }

    private double wykonajLeq(HashMap<String, Double> zmienne) {
        if (argument1.wykonaj(zmienne) <= argument2.wykonaj(zmienne))
            return 1;
        else
            return 0;
    }

    private double wykonajHeq(HashMap<String, Double> zmienne) {
        if (argument1.wykonaj(zmienne) >= argument2.wykonaj(zmienne))
            return 1;
        else
            return 0;
    }

    private double wykonajEq(HashMap<String, Double> zmienne) {
        if (argument1.wykonaj(zmienne) == argument2.wykonaj(zmienne))
            return 1;
        else
            return 0;
    }

    private double wykonajNot(HashMap<String, Double> zmienne) {
        if (argument.wykonaj(zmienne) == 0)
            return 1;
        else
            return 0;
    }

    private double wykonajLiczba() {
        // Na pewno Object wartosc jest typu double,
        // więc rzutowanie jest bezpieczne
        return (double)wartosc;
    }

    private double wykonajTrue() {
        return 1;
    }

    private double wykonajFalse() {
        return 0;
    }

    private double wykonajZmienna(HashMap<String, Double> zmienne) {
        // W Javie niezainicjalizowane zmienne mają wartość 0
        if (zmienne.get(nazwa) == null)
            return 0;
        else
            return zmienne.get(nazwa);
    }

    // Sprawdzam typ, a potem liczę jego wartość odwołująć się do
    // odpowiedniej funkcji
    public double wykonaj(HashMap<String, Double> zmienne) {
        switch (typ) {
            case "Blok":
                return wykonajBlok(zmienne);
            case "If":
                return wykonajIf(zmienne);
            case "While":
                return wykonajWhile(zmienne);
            case "Przypisanie":
                return wykonajPrzypisanie(zmienne);
            case "Plus":
                return wykonajPlus(zmienne);
            case "Minus":
                return wykonajMinus(zmienne);
            case "Razy":
                return wykonajRazy(zmienne);
            case "Dzielenie":
                return wykonajDzielenie(zmienne);
            case "And":
                return wykonajAnd(zmienne);
            case "Or":
                return wykonajOr(zmienne);
            case "<":
                return wykonajL(zmienne);
            case ">":
                return wykonajH(zmienne);
            case "<=":
                return wykonajLeq(zmienne);
            case ">=":
                return wykonajHeq(zmienne);
            case "==":
                return wykonajEq(zmienne);
            case "Not":
                return wykonajNot(zmienne);
            case "Liczba":
                return wykonajLiczba();
            case "True":
                return wykonajTrue();
            case "False":
                return wykonajFalse();
            default:
                return wykonajZmienna(zmienne);
        }
    }

    private String wypiszBlok() {
        StringBuilder wynik = new StringBuilder();
        Instrukcja[] instr = instrukcje.toArray(new Instrukcja[0]);
        int i;

        // Po pętlach, instrukcjach warunkowych, pojedynczych zmiennych i liczbach
        // nie należy stawiać średnika
        for (i = 0; i < instr.length; ++i) {
            if (!instr[i].typ.equals("Zmienna") && !instr[i].typ.equals("Liczba"))
                wynik.append(instr[i]);

            if (!instr[i].typ.equals("While") && !instr[i].typ.equals("If")
                && !instr[i].typ.equals("Zmienna") && !instr[i].typ.equals("Liczba"))
                wynik.append(";");
        }


        return wynik.toString();
    }

    private String wypiszIf() {
        String wynik = ("if(" + warunek + "){" + blok_prawda + "}");

        if (blok_falsz != null)
            wynik = (wynik + "else{" + blok_falsz + "}");

        return wynik;
    }

    private String wypiszWhile() {
        return ("while(" + warunek + "){" + blok + "}");
    }

    private String wypiszPrzypisanie() {
        Gson gson = new Gson();
        String json = new Gson().toJson(wartosc);
        Instrukcja i = gson.fromJson(json, Instrukcja.class);

        return (nazwa + " = (" + i + ")");
    }

    private String wypiszPlus() {
        return ("((" + argument1 + ") + (" + argument2 + "))");
    }

    private String wypiszMinus() {
        return ("((" + argument1 + ") - (" + argument2 + "))");
    }

    private String wypiszRazy() {
        return ("((" + argument1 + ") * (" + argument2 + "))");
    }

    private String wypiszDzielenie() {
        return ("((" + argument1 + ") / (" + argument2 + "))");
    }

    private String wypiszAnd() {
        return ("((" + argument1 + ") && (" + argument2 + "))");
    }

    private String wypiszOr() {
        return ("((" + argument1 + ") || (" + argument2 + "))");
    }

    private String wypiszL() {
        return ("((" + argument1 + ") < (" + argument2 + "))");
    }

    private String wypiszH() {
        return ("((" + argument1 + ") > (" + argument2 + "))");
    }

    private String wypiszLeq() {
        return ("((" + argument1 + ") <= (" + argument2 + "))");
    }

    private String wypiszHeq() {
        return ("((" + argument1 + ") >= (" + argument2 + "))");
    }

    private String wypiszEq() {
        return ("((" + argument1 + ") == (" + argument2 + "))");
    }

    private String wypiszNot() {
        return ("!(" + argument + ")");
    }

    private String wypiszLiczba() {
        return ("" + (double)wartosc);
    }

    private String wypiszTrue() {
        return "1";
    }

    private String wypiszFalse() {
        return "0";
    }

    private String wypiszZmienna() {
        return nazwa;
    }

    @Override
    public String toString() {
        switch (typ) {
            case "Blok":
                return wypiszBlok();
            case "If":
                return wypiszIf();
            case "While":
                return wypiszWhile();
            case "Przypisanie":
                return wypiszPrzypisanie();
            case "Plus":
                return wypiszPlus();
            case "Minus":
                return wypiszMinus();
            case "Razy":
                return wypiszRazy();
            case "Dzielenie":
                return wypiszDzielenie();
            case "And":
                return wypiszAnd();
            case "Or":
                return wypiszOr();
            case "<":
                return wypiszL();
            case ">":
                return wypiszH();
            case "<=":
                return wypiszLeq();
            case ">=":
                return wypiszHeq();
            case "==":
                return wypiszEq();
            case "Not":
                return wypiszNot();
            case "Liczba":
                return wypiszLiczba();
            case "True":
                return wypiszTrue();
            case "False":
                return wypiszFalse();
            default:
                return wypiszZmienna();
        }
    }

}