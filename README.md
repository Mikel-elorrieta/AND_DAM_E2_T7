# E2_T7_MOB_DAM

Android  aplikazioa - bilera aplikazioa

Lengoiaiak: Java, SQL

2025/01/13

Enuntziatua:

![image](https://github.com/user-attachments/assets/234a662d-5300-458a-a14b-99db38cfd37e)
![image](https://github.com/user-attachments/assets/4217c506-6ca0-48b2-923a-5d4144c4707c)
![image](https://github.com/user-attachments/assets/934c844c-2266-41be-8589-1c0c0f4aadac)
![image](https://github.com/user-attachments/assets/41406d6c-f301-4f8f-87ab-050adcc39b26)
![image](https://github.com/user-attachments/assets/d05059cd-df5a-4305-9266-7c2109f5b870)
![image](https://github.com/user-attachments/assets/4075b026-a99d-4089-a418-99deb288068b)
![image](https://github.com/user-attachments/assets/6e619251-aafe-4e6c-bcbd-ddc83a79bedf)
![image](https://github.com/user-attachments/assets/68c62a1a-dee8-4a22-8253-d38ef2068f60)

Denboralizazioa:

![image](https://github.com/user-attachments/assets/5587ac45-5e5d-4b97-a98c-129b8d0bc5ad)


Enuntziatua (Text Format):

App mugikorra:

Aplikazioa intuitiboa, erabiltzeko erraza eta atsegina izan behar da, erabiltzaile-esperientzia (UX) lehenetsiz. Une oro gertatzen ari denaren berri eman behar zaio erabiltzaileari.

Erabiltzaileak app mugikorra erabiltzen duen bitartean Interneterako sarbide izan behar du, datuak urruneko datu base batean gordeta daudelako. Horrela ez bada, erabiltzaileak ezingo ditu ekintzak burutu. Konexioa berreskuratzerakoan, aplikazioak berriz normaltasunez funtzionatu beharko du.

Hizkuntza anitzeko aplikazioa izan behar da. Defektuzko hizkuntza Euskara. Erabiltzaileak hizkuntza ezartzeko aukera izango du eta honakoa mugikorreko barneko datu basean gorde beharko da. Hortik aurrera, erabiltzaileak aukeratutako hizkuntzan aurkeztuko da app mugikorra.

Erabiltzaile mota bi daude eta honen arabera aukera desberdinak emango dira:

Irakasleak:
Ikasleen datuak eta beraien datuak kontsultatu.
Beraien ordutegiak kontsultatu.
Bilerak kontsultatu, sortu, onartu edota ezeztatu.
Ikasleak:
Beraien datuak kontsultatu.
Beraien eta irakasleen ordutegiak kontsultatu.
Bilerak kontsultatu eta sortu.
Login-a
Aplikazioa abiaraztean, CIFP Elorrieta-Errekamari LHII zentroaren eta enpresaren (taldeak aukeratua) logo animatua erakusten da. Jarraian, erabiltzailea urruneko datu basearen aurkako login/password bidez autentifikatzen da. Autentifikazioa enter tekla xakatzerakoan burutuko da.

Erabiltzaileak pasahitza ahaztu badu, hemendik berreskura dezake. Gakoa berreskuratzeko, ausazko gako berri bat sortuko da eta urruneko datu basean enkriptatuta gordeko da. Horren ondorioz, erabiltzaileari, email baten bidez gako berria ailegatuko zaio.

Profila
Erabiltzaileak ezin dira app mugikorrean erregistratu, bai ordea, datuak kontsultatu. Atal honetatik, erabiltzaileak gailu mugikorretik bertatik ateratako argazkia igo ahal izango du bere profilera, eta beraz, urruneko datu base-ra.

Erabiltzailearen arabera datu desberdinak aurkeztuko dira. Ikasleen kasuan, datu orokorretaz gain, burutzen ari diren zikloa, maila, DUAL-ean dauden, eta abar agertuko zaie.

Irakaslea
Irakasle batek asteko ordutegi bera du urte osoan. Ordutegia bost egunetan banatzen da, eta horietako bakoitzak 6 ordu ditu. Ordu bakoitzean, irakastordu, tutoretza edo zaintza bat konfiguratu daiteke. Irakastordua baldin bada, ikasturtea, zikloa eta dagokion moduloa bistaratuko da. Jarraian adibide bat ikus daiteke.

![image](https://github.com/user-attachments/assets/a1480116-c30f-4d4b-98b8-bc1e0617b4bc)

Irakasle batek bere ikasleen datuak kontsultatu ditzake. Hauek zerrenda batean agertuko zaizkio ikasle bakoitzaren izen-abizenak eta argazkia bistaratuz. Datu hauek kontsultatzeko taldea aukeratzeko iragazkiak erabili ahal izango ditu, hala nola zikloa eta ikasturtea.

Ikaslea
Ikasleen ordutegia irakasleen antzerakoa izango da baina zaintza eta tutoretza barik. Irakasleen ordutegietatik abiatuta sortuko dira ikasle ordutegiak eta ezin izango dira beste modu batean biltegiratu.

Ikasleek irakasleen ordutegiak kontsultatu ahalko dituzte eta hauek bilatzeko, izen-abizen iragazkia erabili ahal izango dute.

Bilerak
Bai ikasleek eta baita ere irakasleek, bilerak sor ditzakete. Bilera eskaerak buzoi (notifikazio, email edo antzerako funtzio) baten bidez jasotzen dira.

Bilera eskaera bakoitzari buruzko informazioa hurrengoa da:

Egoera: Onartzeke, Gatazka, Onartuta edo Ezeztatuta.
Izenburua: Bileraren helburua zein den.
Gaia: Aztertu beharreko gaien xehetasun laburra.
Eguna eta ordua.
Gela.
Kokapena: Bilerak Euskadiko edozein ikastetxetan egin daitezke, baina lehenetsitako balioa Elorrieta Errekamari LHII izango da.
Bilerako kideak: Irakaslea eta ikaslea.
Hasiera batean, bilera eskaerak Onartzeke (edo Gatazka) gisa markatuta agertzen dira. Bilera hauen egoera irakasleek bakarrik aldatu dezakete, onartuz edo ezeztatuz.

Irakasleen ordutegietan, bilera eskaera bat dagokion egunean eta orduan markatuta agertu beharko da. Irakasleak ordu hori okupatuta baldin badauka, egoera Gatazka moduan agertuko da, eta ordea libre badauka Onartzeke moduan. Jarraian adibide bat ikus daiteke.
![image](https://github.com/user-attachments/assets/e5d6060d-9d26-410b-beeb-eb87bd330f98)

Bilera xehetasunak
Irakaslearen ordutegian agertzen diren bilerei klik egin dakieke eta beraz, hauei loturiko informazio guztia xehetasunez aurkeztuko da. Testu informazioaz gain, bileraren kokapena mapa batean txintxeta batekin aurkeztuko da, eta bilera horretan parte hartuko duten ikasle eta irakaslearen izen-abizenak eta bakoitzaren argazkiak aurkeztuko dira.

Irakasleak bakarrik alda dezake bileraren egoera onartuz edo ezeztatuz, eta pantaila honetan egin ahal izango du.

Ikasleen kasuan, beraien bileren xehetasunak ikusteko aukera izango dute goiko paragrafoan deskribatzen den moduan.









