# � accessibility - Mini_Game_Tech

Guida all'accessibilità di Mini_Game_Tech: come l'app rende fruibile la piattaforma di arcade retrò a tutti gli utenti.

## 📖 Introduzione

**Mini_Game_Tech** è un'app Android che funge da **WebView wrapper** per il sito web `https://minigame-tech.github.io/`. L'app non contiene codice gioco nativo: tutti i giochi vengono eseguiti all'interno di una WebView che carica pagine web.

Questa architettura influisce sull'approccio all'accessibilità: le funzionalità possono operare su due livelli:
1. **Livello Android**: configurazioni di sistema, WebView settings, accessibilità nativa
2. **Livello Web**: contenuto HTML/CSS/JavaScript del sito ospitato

L'app si concentra su un insieme di funzionalità di accessibilità implementabili attraverso WebView e CSS, senza richiedere modifiche al codice gioco nativo (che non esiste).

## 🎛️ Funzionalità di Accessibilità Implementate

L'app include le seguenti caratteristiche di accessibilità, attive fin dal primo caricamento:

### 1. **Ridotto Movimento (`prefers-reduced-motion`)**

L'app rispetta le preferenze di sistema per ridurre le animazioni:

- **Come funziona**: Viene rilevato `prefers-reduced-motion` sia nelle impostazioni Android che nel browser
- **Effetto**: Le transizioni CSS sono ridotte a 0.01ms, animazioni rimosse dai giochi e interfaccia più statica
- **Quando viene attivato**: Ideale per utenti con vertigini, epilessia fotosensibile o disturbi dell'elaborazione sensoriale

**Impostazione manuale nell'app**:
- Non è presente un toggle UI dedicato (è automatico dalle impostazioni sistema)
- L'app rispetta la scelta dell'utente al primo caricamento

### 2. **Tema Personalizzabile** (Parziale)

L'app supporta la selezione del tema colore, ma con limitazioni architetturali:

- **Temi disponibili**: Scuro (predefinito), Chiaro, Retro anni '80
- **Salvataggio**: Le preferenze sono salvate in `localStorage` e persistono tra le sessioni
- **Rilevamento automatico**: L'app rileva le preferenze `prefers-color-scheme` del sistema

**Limiti**: Il toggle del tema funziona solo se il sito web incorpora gli elementi HTML necessari (pulsante con `id="theme-toggle"`). Senza questi elementi nel sito caricato, il tema rimane quello di default (scuro).

**Per attivare/ disattivare manualmente** (se il sito lo supporta):
- Cerca il pulsante 🌙 nell'intestazione dell'app
- Clicca per passare da scuro a chiaro e viceversa

### 3. **Focus Visibile** (Standard Web)

L'app rispetta e migliora gli outline di focus standard dei browser:

- **Outline**: `3px solid var(--accent)` arancione `#5dcaa5`
- **Offset**: `2px` di spazio esterno
- **Quando appare**: Quando si naviga con la tastiera (Tab/Shift+Tab)

**Elementi interessati**:
- Link all'interno dei giochi e del menu
- Pulsanti della navbar
- Campi di eventuali form

**Nota**: Questo funziona automaticamente su tutti i browser Android senza configurazione aggiuntiva.

### 4. **Rilevamento Dispositivo Touch/PC**

L'applicazione adatta l'interfaccia in base al tipo di dispositivo:

```javascript
function isTouchDevice() {
  return 'ontouchstart' in window || navigator.maxTouchPoints > 0;
}
```

**Adattamenti automatici**:
- **Dispositivi touch**: Controlli minimum 44x44px, touch-action: manipulation
- **PC/Mouse**: Layout standard, puntamento preciso
- **Rilevamento**: Avviene al primo caricamento (`DOMContentLoaded`)

**Impatto**: I giochi o l'interfaccia si adattano automaticamente al dispositivo in uso.

### 5. **Alto Contrasto** (Condizionato)

L'app dispone di una modalità alto contrasto, ma è **condizionata** alla presenza di elementi nel sito web:

- **Come attivarla**: Se il sito include il pulsante `<button id="accessibility-toggle" aria-label="Modalità accessibilità">♿</button>`, cliccandolo si attiva/si disattiva
- **Effetto**: Inverte gli schema colori verso un contrasto maggiore (solitamente bianco su nero o nero su bianco)
- **Senza il pulsante**: La modalità non è attivabile dall'app

**Elemento richiesto**: Il sito `minigame-tech.github.io` deve includere il pulsante accessibility per rendere questa funzionalità attiva.

## 🛠️ Implementazione Tecnica

### MainActivity.java - Accessibilità WebView

Il file `MainActivity.java` include configurazioni e codice JavaScript per l'accessibilità:

**Impostazioni WebView (righe 44-47)**:
```java
webSettings.setJavaScriptEnabled(true);
webSettings.setDomStorageEnabled(true); // Necessario per salvare preferenze tema/accessibilità
webSettings.setAllowFileAccess(false); // Solo contenuti remoti, sicurezza
```

**JavaScript iniettato** (nel metodo `onPageFinished`):
Il codice rileva automaticamente le preferenze di sistema e applica le regole CSS appropriate.

**CSS Personalizzato** (variabile `CUSTOM_CSS` nel codice):
```css
/* Focus visibile - sempre attivo */
.focus-visible {
  outline: 3px solid var(--accent);
  outline-offset: 2px;
}

/* Ridotto movimento - rilevato automaticamente */
@media (prefers-reduced-motion: reduce) {
  html { scroll-behavior: instant; }
  *, *::before, *::after {
    animation-duration: 0.01ms !important;
    transition-duration: 0.01ms !important;
  }
}
```

### Meta Tag Consigliati nel Sito

Per sfruttare appieno le funzionalità di accessibilità, il sito dovrebbe includere nel `<head>`:

```html
<!-- Rilevamento preferenze sistema -->
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="color-scheme" content="light dark">
<meta name="movement" content="reduce">

<!-- Impostazioni theme (opzionale) -->
<link rel="stylesheet" href="styles.css">
```

## 📱 Impostazioni Android Raccomandate

Per un'esperienza ottimale, si consiglia di configurare le impostazioni di Android:

### Impostazioni di Sistema

1. **Impostazioni > Accessibilità**:
   - "Ridotto movimento" → Attiva per disabilitare animazioni
   - "Alto contrasto" → Attiva per invertire i colori (se il sito lo supporta)
   - "Dimensioni tocco" → Regola la dimensione degli elementi interattivi

2. **Impostazioni > Visualizzazione**:
   - "Dimensioni schermo" → Adatta la scala degli elementi
   - "Font" → Aumenta la dimensione del testo (minimo 16px consigliato)

3. **Impostazioni > Interazione**:
   - "Feedback tocco" → Vibrazione quando si tocca (può essere disabilitata per ridurre disturbo)
   - "Cursorio di inclusione" → Evidenziazione focus visiva

### WebView Specifiche

Nel `build.gradle.kts` sono state configurate:
- `compileSdk = 37` (Android 13) - Migliore supporto accessibility API
- `compileOptions` Java 17 - Miglior compatibilità con le features accessibility
- `setAllowFileAccess(false)` - Sicurezza: nessun accesso file locale

## 📋 Checklist di Test

Per verificare l'accessibilità dell'app, controlla quanto segue:

### Su Dispositivo Android

| Funzionalità | Verifica | Risultato Atteso |
|-------------|----------|-----------------|
| **Focus visibile** | Premi Tab ripetutamente | Appare outline arancione `#5dcaa5` intorno agli elementi focusabili |
| **Ridotto movimento** | Imposta "Riduci movimento" nelle impostazioni Android | Le animazioni si riducono o spariscono |
| **Navigazione** | Usa Tab e Shift+Tab | Si naviga tra link e pulsanti in ordine logico |
| **Touch** | Tocca gli elementi schermo piccolo | I controlli sono abbastanza grandi (almeno 44x44px) |

### Nel Browser del Computer

1. **Apri** `https://minigame-tech.github.io/` nel browser
2. **Premi F12** per aprire gli sviluppatore tools
3. **Verifica CSS**:
   - `@media (prefers-reduced-motion: reduce)` è presente
   - `.focus-visible` style è applicato agli elementi focusabili
4. **Testa ridotto movimento**:
   - Nei developer tools, simula `prefers-reduced-motion: reduce`
   - Verifica che le animazioni si fermino

## � recommended Practices per i Contributori

### Quando si aggiunge nuovo contenuto al sito:

**Colori e Contrasto**:
- Rapporto minimo 4.5:1 per testo normale
- Rapporto minimo 3:1 per testo grande (18pt+ o 14pt in grassetto)
- Usa [WebAIM Contrast Checker](https://webaim.org/resources/contrastchecker/) per verificare

**Font**:
- Font sans-serif per migliore leggibilità
- Dimensione testo minima 16px
- Line-height almeno 1.5
- Non usare testo tutto maiuscolo per periodi prolessi

**Interattività**:
- Tutti i componenti interattivi devono essere raggiungibili con Tab (`tabindex="0"` solo su elementi naturalmente interattivi)
- Fornire `aria-label` sui pulsanti icona (es. `<button aria-label="Gioca a Pac-Man">`)
- Non fare affidamento solo sul colore per trasmettere informazioni (es. non usare solo rosso per indicare "errore")

**Layout**:
- Il layout deve essere responsive (funzionare sia in portrait che landscape)
- Non fare affidamento solo sulla posizione visiva per indicare la direzione
- Fornire skip links per saltare la navigazione ripetuta

**Media**:
- Fornire transcript per file audio
- Fornire caption per video
- Descrivere immagini con alt text significativo

### Anti-Patterni da Evitare

- ❌ Usare `display: none` per nascondere contenuti importanti (usare invece `visibility` o posizionamento off-screen)
- ❌ Impostare `overflow: hidden` che tagli il contenuto accessibile
- ❌ Ordine di tab illogico (`tabindex` saltanti o positivi su elementi non interattivi)
- ❌ Colori con contrasto insufficiente (soprattutto rosso-verde per difetti daltonismo)
- ❌ Animazioni senza opzione per disabilitarle

## 🆘 Risoluzione Problemi

### Se il focus non è visibile:
- Verifica che gli elementi abbiano `tabindex` naturale o positivo
- Controlla che CSS personalizzato non disabiliti gli outline
- Testa con la tastiera esterna (non solo touch)

### Se il tema non cambia:
- Verifica che il sito includa `<button id="theme-toggle" aria-label="Cambia tema">`
- Cancella `localStorage` se le preferenze sono corrotte
- Riavvia l'app completamente

### Se l'alto contrasto non si attiva:
- Verifica che il sito includa `<button id="accessibility-toggle" aria-label="Modalità accessibilità">`
- Controlla che `.high-contrast` classe non sia sovrascritta nel CSS custom del sito
- Riavvia l'app dopo aver attivato la modalità

### Se le animazioni non si riducono:
- Verifica che `prefers-reduced-motion` sia attivo nelle impostazioni Android
- Controlla che non ci siano animazioni `!important` nel CSS del sito che sovrascrivano le regole

## 📞 Segnalazione Problemi

Per segnalare problemi di accessibilità o suggerimenti:

1. **Apri un issue** sul repository GitHub
2. **Etichetta** con `accessibility` o `a11y`
3. **Descrivi**:
   - Dispositivo e modello
   - Versione Android
   - Browser/WebView usato
   - Descrizione del problema
   - Passaggi per riprodurre
   - Schermate se possibile

**Template consigliato per l'issue**:
```
## Problem accessibilità
Breve descrizione del problema

Dispositivo: [es. Pixel 6a, Samsung Galaxy S23]
Versione Android: [es. 13, 14]
Browser/WebView: [es. Chrome 120, WebView system]

Passaggi per riprodurre:
1. ...
2. ...

Comportamento atteso:
...

Schermate/video:
```

## 📜 Storia delle Modifiche

| Data | Versione | Modifiche |
|------|----------|-----------|
| 2026 | 1.0 | Lanz iniziale con funzionalità base: focus visibile, rilevamento touch, rilevamento reduced motion |
| 2026 | 1.1 | Aggiunto supporto theme parziale, migliorato CSS personalizzato, aggiunte best practices |
| 2026 | 1.2 | Documentazione estesa, best practices per contributori, guida al test, template segnalazioni problemi |

---
*Ultimo aggiornamento: 26 ottobre 2026*
*Questo documento fa parte del progetto Mini_Game_Tech e viene mantenuto insieme al codice sorgente.*
*L'app è una WebView wrapper: le funzionalità di accessibilità dipendono sia dalle configurazioni Android sia dalla struttura HTML del sito caricato.*