package ewolucja;

public interface Pole {

    boolean czy_pole_zywieniowe();
    // jesli jedzenie jeszcze rosnie, to nie jest gotowe
    boolean czy_gotowe_jedzenie();
    void zjedz();
    // symulacja tury - rosniecie jedzenia
    void symuluj_ture();

}
