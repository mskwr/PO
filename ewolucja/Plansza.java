package ewolucja;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Plansza {

    private final Pole[][] plansza;

    private int ustal_rozmiar_x(File plansza) {
        String wiersz;
        Scanner sc = null;

        try {
            sc = new Scanner(plansza);
        }
        catch (FileNotFoundException e) {
            System.err.println("Nie znaleziono pliku plansza.txt");
            System.exit(1);
        }

        if (sc.hasNextLine()) {
            wiersz = sc.nextLine();
            return wiersz.length();
        }
        else {
            return 0;
        }
    }

    private int ustal_rozmiar_y(File plansza) {
        String wiersz;
        int rozmiar_y = 0;
        Scanner sc = null;

        try {
            sc = new Scanner(plansza);
        }
        catch (FileNotFoundException e) {
            System.err.println("Nie znaleziono pliku plansza.txt");
            System.exit(1);
        }

        while (sc.hasNextLine()) {
            wiersz = sc.nextLine();
            ++rozmiar_y;
        }

        return rozmiar_y;
    }

    // sprawdzenie czy parametry rozmiaru wystepowaly w pliku z parametrami
    // jesli tak, to sprawdzenie czy sa one zgodne z rzeczywista wielkoscia
    // wynikajaca z pliku z plansza
    private void ustal_rozmiar(Parametry p, int rozmiar_x, int rozmiar_y) {
        if (rozmiar_x <= 0 || rozmiar_y <= 0) {
            System.err.println("Niedodatni rozmiar planszy");
            System.exit(1);
        }

        if (p.rozmiar_planszy_x() == -1) {
            p.ustaw_rozmiar_planszy_x(rozmiar_x);
        }
        else {
            if (p.rozmiar_planszy_x() != rozmiar_x) {
                System.err.println("Niekompatybilny rozmiar planszy z parametry.txt");
                System.exit(1);
            }
        }

        if (p.rozmiar_planszy_y() == -1) {
            p.ustaw_rozmiar_planszy_y(rozmiar_y);
        }
        else {
            if (p.rozmiar_planszy_y() != rozmiar_y) {
                System.err.println("Niekompatybilny rozmiar planszy z parametry.txt");
                System.exit(1);
            }
        }
    }

    Plansza(File plansza, Parametry p) {
        String wiersz;
        int rozmiar_planszy_x = 0;
        int rozmiar_planszy_y = 0;
        int i;
        Scanner sc = null;
        this.plansza = new Pole[ustal_rozmiar_y(plansza)][ustal_rozmiar_x(plansza)];

        try {
            sc = new Scanner(plansza);
        }
        catch (FileNotFoundException e) {
            System.err.println("Nie znaleziono pliku plansza.txt");
            System.exit(1);
        }

        while (sc.hasNextLine()) {
            wiersz = sc.nextLine();

            if (rozmiar_planszy_x == 0) {
                rozmiar_planszy_x = wiersz.length();
            }
            else if (wiersz.length() != rozmiar_planszy_x) {
                System.err.println("Rozne dlugosci wierszy w plansza.txt");
                System.exit(1);
            }

            char[] wiersz_tablicowo = wiersz.toCharArray();

            for (i = 0; i < wiersz.length(); ++i) {
                if (wiersz_tablicowo[i] == ' ') {
                    this.plansza[rozmiar_planszy_y][i] = new PolePuste();
                }
                else if (wiersz_tablicowo[i] == 'x') {
                    this.plansza[rozmiar_planszy_y][i] = new PoleZywieniowe(p.ile_rosnie_jedzenie());
                }
                else {
                    System.err.println("Niedozwolony znak w plansza.txt");
                    System.exit(1);
                }
            }

            ++rozmiar_planszy_y;
        }

        ustal_rozmiar(p, rozmiar_planszy_x, rozmiar_planszy_y);
    }

    public Pole plansza(int y, int x) {
        return plansza[y][x];
    }

}
