package ewolucja;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Parametry {

    // wszystkie mozliwe parametry
    private int ile_tur;
    private int koszt_tury;
    private int co_ile_wypisz;
    private int rozmiar_planszy_x;
    private int rozmiar_planszy_y;
    private String spis_instr;
    private String pocz_progr;
    private int pocz_ile_robow;
    private int pocz_energia;
    private int ile_daje_jedzenie;
    private int ile_rosnie_jedzenie;
    private int limit_powielania;
    private float pr_powielenia;
    private float ulamek_energii_rodzica;
    private float pr_usuniecia_instr;
    private float pr_dodania_instr;
    private float pr_zmiany_instr;

    private void sprawdz_parametr(boolean czy_ma_wartosc, boolean czy_juz_byl) {
        if (!czy_ma_wartosc) {
            System.err.println("Blad danych w pliku parametry.txt");
            System.exit(1);
        }
        if (czy_juz_byl) {
            System.err.println("Wielokrotne wystapienie parametru w parametry.txt");
            System.exit(1);
        }
    }

    private void czy_wszystkie_parametry(boolean[] parametr_wczytany) {
        // wszystkie parametry musza wystawpic dokladnie raz oprocz
        // rozmiar_planszy_x i rozmiar_planszy_y
        if (!parametr_wczytany[3]) {
            parametr_wczytany[3] = true;
            rozmiar_planszy_x = -1;
        }
        if (!parametr_wczytany[4]) {
            parametr_wczytany[4] = true;
            rozmiar_planszy_y = -1;
        }

        for (int i = 0; i < 17; ++i) {
            if (!parametr_wczytany[i]) {
                System.err.println("Zbyt malo parametrow w parametry.txt");
                System.exit(1);
            }
        }
    }

    // spis instrukcji moze zawierac tylko te zawarte w tresci: i, j, l, p, w
    private void sprawdz_spis_instr() {
        int i = 0;
        int j = 0;
        char[] pelny_spis = new char[] {'i', 'j', 'l', 'p', 'w'};
        char[] dany_spis = spis_instr.toCharArray();
        Arrays.sort(dany_spis);

        while (i < dany_spis.length && j < pelny_spis.length) {
            if (dany_spis[i] != pelny_spis[j]) {
                ++j;
            }
            else {
                ++j;
                ++i;
            }
        }

        if (j >= pelny_spis.length && i < dany_spis.length) {
            System.err.println("Bledny spis_instr w parametry.txt");
            System.exit(1);
        }
    }

    // pocz_progr musi byc podzbiorem spis_instr
    private void sprawdz_pocz_progr() {
        int i = 0;
        int j = 0;
        char[] dostepne_instrukcje = spis_instr.toCharArray();
        char[] dane_instrukcje = pocz_progr.toCharArray();
        Arrays.sort(dostepne_instrukcje);
        Arrays.sort(dane_instrukcje);

        while (i < dane_instrukcje.length && j < dostepne_instrukcje.length) {
            if (i != 0 && dane_instrukcje[i] == dane_instrukcje[i - 1]) {
                ++i;
            }
            else if (dane_instrukcje[i] != dostepne_instrukcje[j]) {
                ++i;
            }
            else {
                ++j;
                ++i;
            }

	    if (i != 0) {
		while (i < dane_instrukcje.length && dane_instrukcje[i] == dane_instrukcje[i - 1]) {
		    ++i;
		}
	    }
	    
            if (j >= dostepne_instrukcje.length && i < dane_instrukcje.length) {
                System.err.println("Bledny pocz_progr w parametry.txt");
                System.exit(1);
            }
        }
    }

    Parametry(File parametry) {
        String parametr;
        // tablicy sprawdzajacy czy dany parametr byl juz wczytywany
        boolean[] parametr_wczytany = new boolean[17];
        Scanner sc = null;

        try {
            sc = new Scanner(parametry);
        }
        catch (FileNotFoundException e) {
            System.err.println("Nie znaleziono pliku parametry.txt");
            System.exit(1);
        }

        while (sc.hasNext()) {
            parametr = sc.next();

            switch (parametr) {
                case "ile_tur":
                    sprawdz_parametr(sc.hasNextInt(), parametr_wczytany[0]);
                    ile_tur = sc.nextInt();
                    parametr_wczytany[0] = true;
                    break;
                case "koszt_tury":
                    sprawdz_parametr(sc.hasNextInt(), parametr_wczytany[1]);
                    koszt_tury = sc.nextInt();
                    parametr_wczytany[1] = true;
                    break;
                case "co_ile_wypisz":
                    sprawdz_parametr(sc.hasNextInt(), parametr_wczytany[2]);
                    co_ile_wypisz = sc.nextInt();
                    parametr_wczytany[2] = true;
                    break;
                case "rozmiar_planszy_x":
                    sprawdz_parametr(sc.hasNextInt(), parametr_wczytany[3]);
                    rozmiar_planszy_x = sc.nextInt();
                    parametr_wczytany[3] = true;
                    break;
                case "rozmiar_planszy_y":
                    sprawdz_parametr(sc.hasNextInt(), parametr_wczytany[4]);
                    rozmiar_planszy_y = sc.nextInt();
                    parametr_wczytany[4] = true;
                    break;
                case "spis_instr":
                    sprawdz_parametr(sc.hasNext(), parametr_wczytany[5]);
                    spis_instr = sc.next();
                    parametr_wczytany[5] = true;
                    break;
                case "pocz_progr":
                    sprawdz_parametr(sc.hasNext(), parametr_wczytany[6]);
                    pocz_progr = sc.next();
                    parametr_wczytany[6] = true;
                    break;
                case "pocz_ile_robów":
                    sprawdz_parametr(sc.hasNextInt(), parametr_wczytany[7]);
                    pocz_ile_robow = sc.nextInt();
                    parametr_wczytany[7] = true;
                    break;
                case "pocz_energia":
                    sprawdz_parametr(sc.hasNextInt(), parametr_wczytany[8]);
                    pocz_energia = sc.nextInt();
                    parametr_wczytany[8] = true;
                    break;
                case "ile_daje_jedzenie":
                    sprawdz_parametr(sc.hasNextInt(), parametr_wczytany[9]);
                    ile_daje_jedzenie = sc.nextInt();
                    parametr_wczytany[9] = true;
                    break;
                case "ile_rośnie_jedzenie":
                    sprawdz_parametr(sc.hasNextInt(), parametr_wczytany[10]);
                    ile_rosnie_jedzenie = sc.nextInt();
                    parametr_wczytany[10] = true;
                    break;
                case "limit_powielania":
                    sprawdz_parametr(sc.hasNextInt(), parametr_wczytany[11]);
                    limit_powielania = sc.nextInt();
                    parametr_wczytany[11] = true;
                    break;
                case "pr_powielenia":
                    sprawdz_parametr(sc.hasNextFloat(), parametr_wczytany[12]);
                    pr_powielenia = sc.nextFloat();
                    parametr_wczytany[12] = true;
                    break;
                case "ułamek_energii_rodzica":
                    sprawdz_parametr(sc.hasNextFloat(), parametr_wczytany[13]);
                    ulamek_energii_rodzica = sc.nextFloat();
                    parametr_wczytany[13] = true;
                    break;
                case "pr_usunięcia_instr":
                    sprawdz_parametr(sc.hasNextFloat(), parametr_wczytany[14]);
                    pr_usuniecia_instr = sc.nextFloat();
                    parametr_wczytany[14] = true;
                    break;
                case "pr_dodania_instr":
                    sprawdz_parametr(sc.hasNextFloat(), parametr_wczytany[15]);
                    pr_dodania_instr = sc.nextFloat();
                    parametr_wczytany[15] = true;
                    break;
                case "pr_zmiany_instr":
                    sprawdz_parametr(sc.hasNextFloat(), parametr_wczytany[16]);
                    pr_zmiany_instr = sc.nextFloat();
                    parametr_wczytany[16] = true;
                    break;
                default:
                    System.err.println("Bledna nazwa parametru w parametry.txt");
                    System.exit(1);
                    break;
            }
        }

        czy_wszystkie_parametry(parametr_wczytany);
        sprawdz_spis_instr();
        sprawdz_pocz_progr();
    }

    public void ustaw_rozmiar_planszy_x(int rozmiar_planszy_x) {
        this.rozmiar_planszy_x = rozmiar_planszy_x;
    }

    public void ustaw_rozmiar_planszy_y(int rozmiar_planszy_y) {
        this.rozmiar_planszy_y = rozmiar_planszy_y;
    }

    public int ile_tur() {
        return ile_tur;
    }

    public int koszt_tury() {
        return koszt_tury;
    }

    public int co_ile_wypisz() {
        return co_ile_wypisz;
    }

    public int rozmiar_planszy_x() {
        return rozmiar_planszy_x;
    }

    public int rozmiar_planszy_y() {
        return rozmiar_planszy_y;
    }

    public String spis_instr() {
        return spis_instr;
    }

    public String pocz_progr() {
        return pocz_progr;
    }

    public int pocz_ile_robow() {
        return pocz_ile_robow;
    }

    public int pocz_energia() {
        return pocz_energia;
    }

    public int ile_daje_jedzenie() {
        return ile_daje_jedzenie;
    }

    public int ile_rosnie_jedzenie() {
        return ile_rosnie_jedzenie;
    }

    public int limit_powielania() {
        return limit_powielania;
    }

    public float pr_powielania() {
        return pr_powielenia;
    }

    public float ulamek_energii_rodzica() {
        return ulamek_energii_rodzica;
    }

    public float pr_usuniecia_instr() {
        return pr_usuniecia_instr;
    }

    public float pr_dodania_instr() {
        return pr_dodania_instr;
    }

    public float pr_zmiany_instr() {
        return pr_zmiany_instr;
    }

}
