package ewolucja;

import java.util.Random;

public class Rob {

    private int wiek;
    private String kierunek;
    private float energia;
    private String program;
    private int wspolrzedna_x;
    private int wspolrzedna_y;

    Rob(String k, float energia, String program, int x, int y, Parametry p) {
        this.wiek = 0;
        this.energia = energia;
        this.program = program;

        // dla parametru "losowy" losuje kierunek z czterech mozliwych
        if (k.equals("losowy")) {
            String[] kierunki = new String[] {"gora", "prawo", "dol", "lewo"};
            Random r = new Random();
            int losuj = r.nextInt(4);
            this.kierunek = kierunki[losuj];
        }
        else {
            this.kierunek = k;
        }

        // dla x == -1 lub y == -1 losuje polozenie na planszy dla powstajacego roba
        if (x == -1) {
            Random r_x = new Random();
            this.wspolrzedna_x = r_x.nextInt(p.rozmiar_planszy_x());
        }
        else {
            this.wspolrzedna_x = x;
        }

        if (y == -1) {
            Random r_y = new Random();
            this.wspolrzedna_y = r_y.nextInt(p.rozmiar_planszy_y());
        }
        else {
            this.wspolrzedna_y = y;
        }
    }

    public int wiek() {
        return wiek;
    }

    public String kierunek() {
        return kierunek;
    }

    public float energia() {
        return energia;
    }

    public String program() {
        return program;
    }

    public int wspolrzedna_x() {
        return wspolrzedna_x;
    }

    public int wspolrzedna_y() {
        return wspolrzedna_y;
    }

    public boolean czy_powielaj(Parametry p) {
        if (energia < p.limit_powielania()) {
            return false;
        }
        else {
            Random r = new Random();
            double losuj = r.nextDouble();
            return (losuj <= p.pr_powielania());
        }
    }

    private String odwroc_kierunek() {
        switch (kierunek) {
            case "gora":
                return "dol";
            case "dol":
                return "gora";
            case "lewo":
                return "prawo";
            default:
                return "lewo";
        }
    }

    private String mutuj_usuniecie(String program, Parametry p) {
        if (program.length() > 0) {
            Random r = new Random();
            double losuj = r.nextDouble();

            if (losuj <= p.pr_usuniecia_instr()) {
                return this.program.substring(0, program.length() - 1);
            }
            else {
                return program;
            }
        }
        else {
            return program;
        }
    }

    private String mutuj_dodanie(String program, Parametry p) {
        Random r = new Random();
        double losuj = r.nextDouble();

        if (losuj <= p.pr_dodania_instr()) {
            char[] instrukcje = p.spis_instr().toCharArray();
            Random rd = new Random();
            int los = rd.nextInt(instrukcje.length);
            String nowa_instrukcja = ("" + instrukcje[los]);
            return (program + nowa_instrukcja);
        }
        else {
            return program;
        }
    }

    private String mutuj_zmiane(String program, Parametry p) {
        if (program.length() > 0) {
            Random r = new Random();
            double losuj = r.nextDouble();

            if (losuj <= p.pr_zmiany_instr()) {
                char[] instrukcje_roba = program.toCharArray();
                Random r1 = new Random();
                int indeks_usuwanej = r1.nextInt(instrukcje_roba.length);
                char[] instrukcje = p.spis_instr().toCharArray();
                Random r2 = new Random();
                int indeks_dodawanej = r2.nextInt(instrukcje.length);
                return (program.substring(0, indeks_usuwanej) + instrukcje[indeks_dodawanej]
                        + program.substring(indeks_usuwanej + 1));
            }
            else {
                return program;
            }
        }
        else {
            return program;
        }
    }

    private String mutuj(String program, Parametry p) {
        program = mutuj_usuniecie(program, p);
        program = mutuj_dodanie(program, p);
        program = mutuj_zmiane(program, p);

        return program;
    }

    public Rob powielaj(Parametry p) {
        String kierunek_syna = odwroc_kierunek();
        float energia_syna = p.ulamek_energii_rodzica() * energia;
        String program_syna = program;
        program_syna = mutuj(program_syna, p);

        return new Rob(kierunek_syna, energia_syna, program_syna, wspolrzedna_x, wspolrzedna_y, p);
    }

    private void obroc_w_lewo() {
        switch (kierunek) {
            case "gora":
                kierunek = "lewo";
                break;
            case "prawo":
                kierunek = "gora";
                break;
            case "dol":
                kierunek = "prawo";
                break;
            default:
                kierunek = "dol";
                break;
        }
    }

    private void obroc_w_prawo() {
        switch (kierunek) {
            case "gora":
                kierunek = "prawo";
                break;
            case "prawo":
                kierunek = "dol";
                break;
            case "dol":
                kierunek = "lewo";
                break;
            default:
                kierunek = "gora";
                break;
        }
    }

    private void idz(Plansza plansza, Parametry p) {
        int lewo_x, prawo_x, gora_y, dol_y;

        if (wspolrzedna_x == 0)
            lewo_x = p.rozmiar_planszy_x() - 1;
        else
            lewo_x = wspolrzedna_x - 1;

        if (wspolrzedna_x == p.rozmiar_planszy_x() - 1)
            prawo_x = 0;
        else
            prawo_x = wspolrzedna_x + 1;

        if (wspolrzedna_y == 0)
            gora_y = p.rozmiar_planszy_y() - 1;
        else
            gora_y = wspolrzedna_y - 1;

        if (wspolrzedna_y == p.rozmiar_planszy_y() - 1)
            dol_y = 0;
        else
            dol_y = wspolrzedna_y + 1;

        switch (kierunek) {
            case "gora":
                wspolrzedna_y = gora_y;
                break;
            case "prawo":
                wspolrzedna_x = prawo_x;
                break;
            case "dol":
                wspolrzedna_y = dol_y;
                break;
            default:
                wspolrzedna_x = lewo_x;
                break;
        }

        if (plansza.plansza(wspolrzedna_y, wspolrzedna_x).czy_gotowe_jedzenie()) {
            energia += p.ile_daje_jedzenie();
            plansza.plansza(wspolrzedna_y, wspolrzedna_x).zjedz();
        }
    }

    private void wachaj(Plansza plansza, Parametry p) {
        int lewo_x, prawo_x, gora_y, dol_y;

        if (wspolrzedna_x == 0)
            lewo_x = p.rozmiar_planszy_x() - 1;
        else
            lewo_x = wspolrzedna_x - 1;

        if (wspolrzedna_x == p.rozmiar_planszy_x() - 1)
            prawo_x = 0;
        else
            prawo_x = wspolrzedna_x + 1;

        if (wspolrzedna_y == 0)
            gora_y = p.rozmiar_planszy_y() - 1;
        else
            gora_y = wspolrzedna_y - 1;

        if (wspolrzedna_y == p.rozmiar_planszy_y() - 1)
            dol_y = 0;
        else
            dol_y = wspolrzedna_y + 1;

        boolean gora = plansza.plansza(gora_y, wspolrzedna_x).czy_gotowe_jedzenie();
        boolean prawo = plansza.plansza(wspolrzedna_y, prawo_x).czy_gotowe_jedzenie();
        boolean dol = plansza.plansza(dol_y, wspolrzedna_x).czy_gotowe_jedzenie();
        boolean lewo = plansza.plansza(wspolrzedna_y, lewo_x).czy_gotowe_jedzenie();

        if (gora)
            kierunek = "gora";
        else if (prawo)
            kierunek = "prawo";
        else if (dol)
            kierunek = "dol";
        else if (lewo)
            kierunek = "lewo";
    }

    private void jedz(Plansza plansza, Parametry p) {
        int lewo_x, prawo_x, gora_y, dol_y;

        if (wspolrzedna_x == 0)
            lewo_x = p.rozmiar_planszy_x() - 1;
        else
            lewo_x = wspolrzedna_x - 1;

        if (wspolrzedna_x == p.rozmiar_planszy_x() - 1)
            prawo_x = 0;
        else
            prawo_x = wspolrzedna_x + 1;

        if (wspolrzedna_y == 0)
            gora_y = p.rozmiar_planszy_y() - 1;
        else
            gora_y = wspolrzedna_y - 1;

        if (wspolrzedna_y == p.rozmiar_planszy_y() - 1)
            dol_y = 0;
        else
            dol_y = wspolrzedna_y + 1;

        boolean gora = plansza.plansza(gora_y, wspolrzedna_x).czy_gotowe_jedzenie();
        boolean gora_prawo = plansza.plansza(gora_y, prawo_x).czy_gotowe_jedzenie();
        boolean prawo = plansza.plansza(wspolrzedna_y, prawo_x).czy_gotowe_jedzenie();
        boolean prawo_dol = plansza.plansza(dol_y, prawo_x).czy_gotowe_jedzenie();
        boolean dol = plansza.plansza(dol_y, wspolrzedna_x).czy_gotowe_jedzenie();
        boolean dol_lewo = plansza.plansza(dol_y, lewo_x).czy_gotowe_jedzenie();
        boolean lewo = plansza.plansza(wspolrzedna_y, lewo_x).czy_gotowe_jedzenie();
        boolean lewo_gora = plansza.plansza(gora_y, lewo_x).czy_gotowe_jedzenie();

        if (gora) {
            wspolrzedna_y = gora_y;

            if (plansza.plansza(wspolrzedna_y, wspolrzedna_x).czy_gotowe_jedzenie()) {
                energia += p.ile_daje_jedzenie();
                plansza.plansza(wspolrzedna_y, wspolrzedna_x).zjedz();
            }
        }
        else if (gora_prawo) {
            wspolrzedna_x = prawo_x;
            wspolrzedna_y = gora_y;

            if (plansza.plansza(wspolrzedna_y, wspolrzedna_x).czy_gotowe_jedzenie()) {
                energia += p.ile_daje_jedzenie();
                plansza.plansza(wspolrzedna_y, wspolrzedna_x).zjedz();
            }
        }
        else if (prawo) {
            wspolrzedna_x = prawo_x;

            if (plansza.plansza(wspolrzedna_y, wspolrzedna_x).czy_gotowe_jedzenie()) {
                energia += p.ile_daje_jedzenie();
                plansza.plansza(wspolrzedna_y, wspolrzedna_x).zjedz();
            }
        }
        else if (prawo_dol) {
            wspolrzedna_x = prawo_x;
            wspolrzedna_y = dol_y;

            if (plansza.plansza(wspolrzedna_y, wspolrzedna_x).czy_gotowe_jedzenie()) {
                energia += p.ile_daje_jedzenie();
                plansza.plansza(wspolrzedna_y, wspolrzedna_x).zjedz();
            }
        }
        else if (dol) {
            wspolrzedna_y = dol_y;

            if (plansza.plansza(wspolrzedna_y, wspolrzedna_x).czy_gotowe_jedzenie()) {
                energia += p.ile_daje_jedzenie();
                plansza.plansza(wspolrzedna_y, wspolrzedna_x).zjedz();
            }
        }
        else if (dol_lewo) {
            wspolrzedna_x = lewo_x;
            wspolrzedna_y = dol_y;

            if (plansza.plansza(wspolrzedna_y, wspolrzedna_x).czy_gotowe_jedzenie()) {
                energia += p.ile_daje_jedzenie();
                plansza.plansza(wspolrzedna_y, wspolrzedna_x).zjedz();
            }
        }
        else if (lewo) {
            wspolrzedna_x = lewo_x;

            if (plansza.plansza(wspolrzedna_y, wspolrzedna_x).czy_gotowe_jedzenie()) {
                energia += p.ile_daje_jedzenie();
                plansza.plansza(wspolrzedna_y, wspolrzedna_x).zjedz();
            }
        }
        else if (lewo_gora) {
            wspolrzedna_x = lewo_x;
            wspolrzedna_y = gora_y;

            if (plansza.plansza(wspolrzedna_y, wspolrzedna_x).czy_gotowe_jedzenie()) {
                energia += p.ile_daje_jedzenie();
                plansza.plansza(wspolrzedna_y, wspolrzedna_x).zjedz();
            }
        }
    }

    private void wykonaj_instrukcje(char instrukcja, Parametry p, Plansza plansza) {
        switch (instrukcja) {
            case 'l':
                obroc_w_lewo();
                break;
            case 'p':
                obroc_w_prawo();
                break;
            case 'i':
                idz(plansza, p);
                break;
            case 'w':
                wachaj(plansza, p);
                break;
            case 'j':
                jedz(plansza, p);
                break;
        }
    }

    public void wykonaj_program(Plansza plansza, Parametry p) {
        while (energia > 0 && program.length() > 0) {
            wykonaj_instrukcje(program.charAt(0), p, plansza);
            program = program.substring(1);
            --energia;
        }
    }

    public void zakoncz_ture(Parametry p) {
        energia -= p.koszt_tury();
        ++wiek;
    }

}
