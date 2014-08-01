Wstęp
Program umożliwiający wprowadzanie danych(nazwy, ulicy, email, adres www, tel, NIP) firm do pliku CSV
wersja 1.21 z dnia 01.08.2014

Nowości (W porównaniu z wersją 1.1)

Należy zawsze podawać Nazwe oraz NIP
Wprowadzono wlidacje NIP; Musi być cyfrą z przedziału[0-9] oraz zawierać maksymalnie 10 znaków
Nie jest wymagany e-mail do wprowadzania danych

Instalacja
Należy mieć zainstalowane środowisko uruchomieniowe JAVY
oraz pobraną bibliotekę javacsv z tej strony
http://sourceforge.net/projects/javacsv/files/

Wykorzystanie
Aby zacząć obsługiwać należy wprowadzić w odpowiednie pola wymagane informacje
Pole "Nazwa" - jest to nazwa przedsiębiorstwa które dane chcemy umieścić w pliku csv (Wymagane pole)
Pole "Ulica" - adres wprowadzanej firmy
Pole "E-mail" - EMail do wprowadzanej firmy
Pole "Adres WWW" - Adres www firmy
Pole "tel" - tel do firmy
Pole "NIP" - NIP firmy cyfrą z przedziału[0-9] oraz zawierać maksymalnie 10 znaków(Wymagane pole)

Jeśli operacja pójdzie pomyślnie w dolej części ekranu zostaną wyświetlone wprowadzane dane oraz tekst 
w konsoli podświetli sie na zielono. Jednocześnie zostaną one dodane na następne wolne miejsce w pliku firmy.csv. 

W przypadku niewłaściwego adresu email zostanie wyświetlony czerwony komunikat o błędzie.

W przypadku niewłaściwego adresu WWW zostanie wyświetlony czerwony komunikat o błędzie.

Informacje dodatkowe
Kod stanowi zmodyfikowaną wersje kodu dostępnego na stronie:
http://www.csvreader.com/java_csv_samples.php

Walidacja e-mailu jest oparta o wyrażenie regularne zamieszczone na stronie
http://www.mkyong.com/regular-expressions/how-to-validate-email-address-with-regular-expression/

Walidacja adresu URL oparta o bilblioteke dostępną pod tym adresem
http://commons.apache.org/proper/commons-validator/download_validator.cgi
