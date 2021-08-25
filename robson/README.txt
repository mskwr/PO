INSTRUKCJA OBSŁUGI PROGRAMU ROBSON

Robson został napisany przy użyciu programu Maven (zgodnie z poradą
prowadzących), aby móc łatwiej korzystać ze zdalnej biblioteki.
W moim przypadku była to (również polecana przez prowadzących) GSON,
która służy do konwersji z i do formatu JSON.

Praca zawiera pliki:
1. \src\main\java\ - folder z plikami programu
2. pom.xml - informacje o projekcie, używane przez Maven do kompilacji
3. *.json - testy w języku ROBSON wykorzystane przeze mnie w programie
4. README.txt - instrukcja obsługi programu.

Program można łatwo włączyć, wpisując w konsoli polecenia:
mvn compile
mvn exec:java

Usunąć pliki powstałe przy kompilacji można poleceniem:
mvn clean
