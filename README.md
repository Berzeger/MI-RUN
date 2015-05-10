# MI-RUN

Semestrální práce z předmětu MI-RUN, vyučovaný na FIT ČVUT.

Autory práce jsou Filip Vondrášek a Václav Jirovský.

Cílem práce bylo implementovat Java VM. Pro otestování funkčnosti je ve VM naimplementované řešení SAT formulí pomocí hrubé síly.


Požadavky
---------------------
Pro zkompilování a spuštění VM je třeba mít nainstalované a správně nastavení JDK.

Kompilace
---------------------
Pro zkompilování projektu použijte buildovací program `ant`.


Použití
---------------------

VM načítá všechny vygenerované bytecode .class soubory, které jsou poté k dispozici při běhu programu. VM spustí první bytecode soubor .class (jako základní program), který obsahuje funkci `main`.

Pro spuštění testovacího naimplementovaného řešení SAT formulí, stačí mít pouze načtené bytecode soubory.
