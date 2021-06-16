import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Klasa konwertująca - konwertuje pliki z i do JSON przy pomocy jednej
 * z polecanych bibliotek firmy Google - GSON.
 */

public class Konwerter {

    // Parametr w którym przechowywany jest plik z programem
    // przekonwertowany już z języka ROBSON
    private Instrukcja program;

    Konwerter(Instrukcja program) {
        this.program = program;
    }

    public Instrukcja program() {
        return program;
    }

    // Sprawdza czy wpisywany plik istnieje
    private void checkFile(String filename) {
        File plik = new File(filename);

        if (!plik.exists()) {
            System.err.println("Plik z JSON nie istnieje");
            System.exit(1);
        }
     }

    public void fromJson(String filename) {
        checkFile(filename);
        Path sciezka = Path.of(filename);
        String json = null;

        try {
            json = Files.readString(sciezka);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        program = gson.fromJson(json, Instrukcja.class);
    }

    public void toJson(String filename) throws IOException {
        // setPrettyPrinting() - wypisuje programy poprawnie stylistycznie, nie w jednej linijce
        // disableHtmlEscaping() - poprawnie wypisuje znaki "<", ">"
        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        String json = gson.toJson(program);
        File plik = new File(filename);
        Files.writeString(Path.of(filename), json);
    }

}
