package ewolucja;

public class PoleZywieniowe implements Pole {

    private final int ile_rosnie_jedzenie;
    private int tury_do_wyrosniecia;

    public PoleZywieniowe(int ile_rosnie_jedzenie) {
        this.ile_rosnie_jedzenie = ile_rosnie_jedzenie;
        tury_do_wyrosniecia = 0;
    }

    @Override
    public boolean czy_pole_zywieniowe() {
        return true;
    }

    @Override
    public boolean czy_gotowe_jedzenie() {
        return (tury_do_wyrosniecia == 0);
    }

    @Override
    public void zjedz() {
        tury_do_wyrosniecia = ile_rosnie_jedzenie;
    }

    @Override
    public void symuluj_ture() {
        if (tury_do_wyrosniecia > 0)
            --tury_do_wyrosniecia;
    }

}
