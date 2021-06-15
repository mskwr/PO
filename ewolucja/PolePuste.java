package ewolucja;

public class PolePuste implements Pole {

    @Override
    public boolean czy_pole_zywieniowe() {
        return false;
    }

    @Override
    public boolean czy_gotowe_jedzenie() {
        return false;
    }

    @Override
    public void zjedz() {}

    @Override
    public void symuluj_ture() {}

}
