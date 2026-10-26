# 🕹️ Minigame Tech

> Una piattaforma web cross-platform per arcade classici, ospitata su GitHub Pages e confezionata in un'app Android tramite WebView.

[![GitHub Pages](https://img.shields.io/badge/status-live-success.svg)](https://cipriano-salvatore.github.io/minigame-tech/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Platform](https://img.shields.io/badge/platform-Web%20|%20Android-blue.svg)](https://github.com/cipriano-salvatore/minigame-tech)
[![Android](https://img.shields.io/badge/Android-WebView%20Wrapper-green.svg)](https://github.com/cipriano-salvatore/minigame-tech/blob/main/app/)

---

## 📖 About The Project

**Minigame Tech** è un progetto open-source dedicato alla ricreazione di leggendari giochi arcade retrò. La piattaforma unisce:

- **Logic di gioco** scritta in Python (con `g2d`, Pygame) e C#
- **Interfaccia frontend** in HTML5/CSS3 moderna e responsiva
- **Wrapper Android** che incapsula il sito web in un WebView per la distribuzione su Google Play Store

Whether you're dodging traffic in Frogger, clearing aliens in Space Invaders, or navigating mazes in Pac-Man, Minigame Tech consegna un'esperienza nostalgica ottimizzata sia per desktop che per schermi touch mobile.

---

## 🎮 Features

- **Classic Arcade Remakes:** Meccaniche fedelmente ricreate di titoli senza tempo.
- **Cross-Platform Play:** Design fully responsive ottimizzato per browser desktop e touchscreen mobili.
- **Android App Support:** Pubblicato su Google Play Store come applicazione WebView-wrapper per una distribuzione mobile senza friction.
- **Zero-Dependency Web Core:** Il cuore web è costruito con script core efficienti e standard web moderni per tempi di caricaggio rapidi.

> **Nota:** L'app Android funge da contenitore WebView per la piattaforma web. Le funzionalità native sono attualmente limitate a modalità fullscreen e pull-to-refresh offline.

---

## 🚀 Built With

- **Core Logic & Game Loops:** Python (`g2d`, Pygame) / C#
- **Frontend:** HTML5, CSS3, Responsive Design
- **Hosting & CI/CD:** GitHub Pages
- **Mobile Wrapper:** Android Studio (Java, WebView API)

---

## 🌐 Live Demo & Access

Puoi giocare ai minigames direttamente attraverso la piattaforma web:

🔗 **[minigame-tech.github.io](https://cipriano-salvatore.github.io/minigame-tech/)**

**Stato Servizi:** Il sito GitHub Pages è attivamente mantenuto e il badge sopra riflette lo stato attuale. Se il collegamento non dovesse funzionare, apri un issue sul repository.

---

## 📱 Android Application

Minigame Tech è disponibile anche come applicazione Android, che porta l'esperienza completa arcade retrò su smartphone con layout touch ottimizzati e modalità fullscreen immersiva.

**Importante:** L'app è un wrapper WebView attorno alla piattaforma web. Non contiene codice gioco nativo: tutti i giochi vengono eseguiti all'interno della WebView caricando `https://minigame-tech.github.io/`.

**Permessi richiesti:**
- `INTERNET`: Per il caricamento della pagina web.
- `ACCESS_NETWORK_STATE`: Per rilevare la connessione e mostrare la pagina offline appropriata.

La sicurezza della rete è gestita tramite `network_security_config`, che impone HTTPS per il dominio `minigame-tech.github.io` e blocca il traffico in chiaro.

---

## 🛠️ Local Development & Setup

Per eseguire o modificare il progetto localmente:

1. **Clone the repository:**
   ```bash
   git clone https://github.com/cipriano-salvatore/minigame-tech.git
   ```

2. **Navigate to the project directory:**
   ```bash
   cd minigame-tech
   ```

3. **Open and test:** Open `index.html` in your preferred browser or serve it via a local development server (e.g., Python's HTTP server):
   ```bash
   python3 -m http.server 8000
   ```

Per testare l'app Android:
- Apri il progetto in Android Studio.
- Assicurati di avere una versione recente del JDK (Java 17+ consigliato).
- Esegui su un dispositivo o emulator con API level 24+.

---

## 🔒 Sicurezza & Privacy

- **Network Security:** L'app impone HTTPS per tutti i caricamenti web. Il traffico in chiaro (HTTP) è bloccato a livello di configurazione Android.
- **Dati locali:** Non vengono memorizzati dati sensibili localmente. L'accesso ai file locali sulla device è disabilitato nella WebView per motivi di sicurezza.
- **Privacy:** Non sono presenti tracker o analytics di terze parti nell'app Android. La pagina web potrebbe avere la propria policy sulla privacy.

---

## 🤝 Contributing

Contributi, segnalazioni di problemi e richieste di funzionalità sono benvenute! Sentiti libero di controllare la [pagina degli issues](https://github.com/cipriano-salvatore/minigame-tech/issues).

1. Fork the Project
2. Crea il tuo ramo di funzionalità (`git checkout -b feature/AmazingFeature`)
3. Commit dei tuoi cambiamenti (`git commit -m 'Add some AmazingFeature'`)
4. Push sul ramo (`git push origin feature/AmazingFeature`)
5. Apri una Pull Request

---

## 📄 License

Distribuito sotto la licenza MIT. Vedi [LICENSE](https://github.com/cipriano-salvatore/minigame-tech/blob/main/LICENSE.md) per maggiori informazioni.

---

## 🛠️ Stack Tecnico Interno (Developer Notes)

> Queste informazioni sono per i contributori tecnici.

- **Web Core:** Sito statico generato con HTML/CSS/JS puro, ospitato su GitHub Pages.
- **Android App:** Modulo `app/` su Android Studio, minimum SDK 24, target SDK 37.
- **Build System:** Gradle con Java 17 (compatibile con compileSdk 37).
- **Risorse aggiunte:** `res/xml/network_security_config.xml` per la sicurezza di rete configurata.