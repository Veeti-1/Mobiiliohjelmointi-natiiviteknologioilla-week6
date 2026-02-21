# Mobiiliohjelmointi-natiiviteknologioilla-week6
### mitä room tekee?
  - UI kutsuu viewmodel luokkaa ja tarkkailee `` StateFlow<List<Task>> ``, `` viewModelScope.launch{} `` käynnistää korutiinit.
  - ViewModel kutsuu repositoryä, joka abstraktiokerron viewmodelin ja tietokanna välissä, kokoaa datan DAO:ssa ja tarjoaa selkeän API:n viewmodelille
  - Repository kutsuu ROOM-kirjastoa, jossa database: antaa DAO:t -> DAO: SQL-kyselyt -> Entity: SQL taulun rakenne. Lukee ja kirjoittaa SQLite tietokantaa.

### Projektin rakenne
  - /data/local: /AppDatabase määrittelee Room-tietokannan ja luo singleton instansin databasesta.
  - /data/local: /TaskDao Funktiot ja niitten SQL Queryt.
  - /data/model: /TaskEntity määrittelee tietokannan taulujen rakenteen.
  - /data/repository: Toimii välikerroksena viewmodelin ja Daon väillä ja sisältää viewmodelin ja Dao:n väliset funktiot.
  - /Navigation: Routes määrittää Routet.
  - /viewmodel: /TaskViewModel Toimii UI:n ja datakerroksen välissä, UI:ssa kutsutaan viewmodelin funktioita ja viewmodel välittää kutsut repositorylle.
  - /view: UI komponentit homescreen, DetailDialog jne.
  - /MainActivity: Luo tietokannan instanssin, luo repositoryn ja viewmodelin, määrittelee Routet ja välittää viewmodelin composable-komponenteille.

### Miten datavirta kulkee
  - UI: kutsuu viewmodel funktioita -> viewmodel kutsuu repositoryn funktioita ja palauttaa UI:lle datan -> repository palauttaa viewmodelille Dao funktioista saadun datan -> Dao tekee SQL Queryn tietokantaan ja  palauttaa tietokannasta saadun datan.
#### APK löytyy releasesta
#### Youtube-demo: 
