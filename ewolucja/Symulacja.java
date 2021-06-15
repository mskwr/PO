/*
 * PO: Ewolucja, czyli niech programy pisza sie same
 * Autor: Michał Skwarek
 */

package ewolucja;

import java.io.File;

public class Symulacja {

    private static void sprawdz_poprawnosc_argumentow(String[] argumenty) {
        if (argumenty.length != 2) {
            System.err.println("Bledna ilosc argumentow programu");
            System.exit(1);
        }
        if (!argumenty[0].equals("plansza.txt")) {
            System.err.println("Bledna nazwa pliku z plansza");
            System.exit(1);
        }
        if (!argumenty[1].equals("parametry.txt")) {
            System.err.println("Bledna nazwa pliku z parametrami");
            System.exit(1);
        }
    }

    private static void wypisz_stan_roba(Rob rob, int nr) {
        System.out.println("Rob " + (nr + 1) + ": wiek - " + rob.wiek() + ", energia - " + rob.energia()
                           + ", program - " + rob.program() + ", wspolrzedne - (" + rob.wspolrzedna_x()
                           + ", " + rob.wspolrzedna_y() + "), kierunek - " + rob.kierunek() + ".");
    }

    // funkcja, ktora po kazdej turze przechodzi przez tablice wszystkich robow
    // i po calej planszy, gromadzi statystyki i je drukuje
    private static void wypisz_stan_tury(Rob[] roby, Plansza plansza, Parametry p, int dl) {
        int i, j;
        int zyw = 0;
        int min_prog = Integer.MAX_VALUE;
        int max_prog = 0;
        int min_wiek = Integer.MAX_VALUE;
        int max_wiek = 0;
        float min_energia = Integer.MAX_VALUE;
        float max_energia = 0;
        int suma_prog = 0;
        int suma_wiek = 0;
        float suma_energia = 0;
        float sr_prog, sr_wiek, sr_energia;

        // przejscie po planszy
        for (i = 0; i < p.rozmiar_planszy_y(); ++i) {
            for (j = 0; j < p.rozmiar_planszy_x(); ++j) {
                if (plansza.plansza(i, j).czy_gotowe_jedzenie()) {
                    ++zyw;
                }
            }
        }

        // przejscie po tablicy robow
        for (i = 0; i < dl; ++i) {
            if (roby[i].program().length() > max_prog)
                max_prog = roby[i].program().length();
            if (roby[i].program().length() < min_prog)
                min_prog = roby[i].program().length();
            if (roby[i].wiek() > max_wiek)
                max_wiek = roby[i].wiek();
            if (roby[i].wiek() < min_wiek)
                min_wiek = roby[i].wiek();
            if (roby[i].energia() > max_energia)
                max_energia = roby[i].energia();
            if (roby[i].energia() < min_energia)
                min_energia = roby[i].energia();

            suma_energia += roby[i].energia();
            suma_prog += roby[i].program().length();
            suma_wiek += roby[i].wiek();
        }

        sr_energia = suma_energia / dl;
        sr_prog = (float)suma_prog / dl;
        sr_wiek = (float)suma_wiek / dl;

        System.out.print("rob: " + dl + ", żyw: " + zyw + ", prg: " + min_prog + "/" + sr_prog
                         + "/" + max_prog + ", energ: " + min_energia + "/" + sr_energia + "/" + max_energia
                         + ", wiek: " + min_wiek + "/" + sr_wiek + "/" + max_wiek);

    }

    public static void main(String[] args) {
        sprawdz_poprawnosc_argumentow(args);
        File plik1 = new File(args[0]);
        File plik2 = new File(args[1]);
        Parametry p = new Parametry(plik2);
        Plansza plansza = new Plansza(plik1, p);
        Rob[] roby = new Rob[p.pocz_ile_robow()];
        int i, v, j, k, m, n;
        int dl = roby.length;
        int wypisz_stan = p.co_ile_wypisz();

        System.out.println("SYMULACJA ROZPOCZETA. STAN POCZATKOWY WSZYSTKICH ROBOW:");

        // wypisanie informacji o robach na poczatku symulacji
        for (i = 0; i < p.pocz_ile_robow(); ++i) {
            roby[i] = new Rob("losowy", p.pocz_energia(), p.pocz_progr(), -1, -1, p);
            wypisz_stan_roba(roby[i], i);
        }

        System.out.print("\n");

        for (i = 0; i < p.ile_tur(); ++i) {
            k = 0;
            m = 0;
            // tablice z nowymi robami, ktore wezma udzial w symulacji
            // od nastepnej tury
            Rob[] nowe_roby = new Rob[dl];
            // tablica z martwymi robami, ktore zostana usuniete
            int[] martwe_roby = new int[dl];

            // co wypisz_stan tur przedstawiam szczegolowe informacje o wszystkich robach
            if (i == wypisz_stan) {
                wypisz_stan += p.co_ile_wypisz();
                System.out.println();
                System.out.println("BIEZACY STAN SYMULACJI:");

                for (j = 0; j < dl; ++j) {
                    wypisz_stan_roba(roby[j], j);
                }

                System.out.print("\n");
            }

            // usuwanie martwych robow z symulacji
            for (j = 0; j < dl; ++j) {
                roby[j].wykonaj_program(plansza, p);

                if (roby[j].czy_powielaj(p)) {
                    nowe_roby[k] = roby[j].powielaj(p);
                    ++k;
                }

                roby[j].zakoncz_ture(p);

                if (roby[j].energia() < 0) {
                    martwe_roby[m] = j;
                    ++m;
                }
            }

            Rob[] roby_po_turze = new Rob[dl + k - m];
            n = 0;
            v = 0;

            // dodanie do symulacji robow, ktore powstaly w ubieglej turze
            for (j = 0; j < dl; ++j) {
                if (n < m && j < martwe_roby[n]) {
                    roby_po_turze[v] = roby[j];
                    ++v;
                }
                else if (n < m && j == martwe_roby[n]) {
                    ++n;
                }
                else {
                    roby_po_turze[v] = roby[j];
                    ++v;
                }
            }

            for (j = 0; j < k; ++j) {
                roby_po_turze[v] = nowe_roby[j];
                ++v;
            }

            dl = v;
            roby = roby_po_turze;
            System.out.print((i + 1) + ", ");
            wypisz_stan_tury(roby, plansza, p, dl);
            System.out.print("\n");

            // symulacja tury na wszystkich polach - rosniecie jedzenia
            for (j = 0; j < p.rozmiar_planszy_y(); ++j) {
                for (v = 0; v < p.rozmiar_planszy_x(); ++v) {
                    plansza.plansza(j, v).symuluj_ture();
                }
            }
        }

        System.out.println("\nSYMULACJA ZAKONCZONA. STAN KONCOWY WSZYSTKICH ROBOW:");

        for (j = 0; j < dl; ++j) {
            wypisz_stan_roba(roby[j], j);
        }
    }

}
