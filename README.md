# Weather App 🌤️

Una applicazione web completa per il monitoraggio meteo in tempo reale che fornisce informazioni meteorologiche dettagliate per qualsiasi località.

## 📋 Descrizione

L'applicazione Weather App permette di:
- Visualizzare i dati meteo attuali (temperatura, umidità, velocità del vento)
- Consultare lo storico delle rilevazioni meteorologiche
- Visualizzare grafici interattivi dei dati meteo
- Cercare informazioni meteo per città specifiche

## 🛠️ Tecnologie utilizzate

- **Backend**: Java 17 + Spring Boot + API REST
- **Frontend**: HTML5, CSS3, JavaScript + Chart.js per i grafici
- **Database**: MySQL per lo storage dei dati
- **API Esterna**: OpenMeteo API per i dati meteorologici
- **Containerizzazione**: Docker e Docker Compose

## 📦 Prerequisiti

Per eseguire questa applicazione hai bisogno di:

- [Docker](https://www.docker.com/get-started) (versione 20.10 o superiore)
- [Docker Compose](https://docs.docker.com/compose/install/) (versione 2.0 o superiore)

> **Nota**: Non è necessario installare Java o Maven se usi Docker!

## 🚀 Installazione e Avvio

### Metodo Rapido (Consigliato)

1. **Clona il repository**:
   ```bash
   git clone <url-del-repository>
   cd Meteo
   ```

2. **Avvia l'applicazione**:
   ```bash
   docker-compose up --build
   ```

3. **Accedi all'applicazione**:
   - Frontend: http://localhost:8080
   - Backend API: http://localhost:8081

### Metodo Manuale (per sviluppatori)

Se preferisci compilare manualmente il backend:

1. **Compila il backend**:
   ```bash
   cd backend
   mvn clean package -DskipTests
   ```

2. **Avvia con Docker Compose**:
   ```bash
   cd ..
   docker-compose up --build
   ```

## 🎯 Come usare l'applicazione

1. Apri il browser e vai su http://localhost:3000
2. Inserisci il nome di una città nel campo di ricerca
3. Visualizza i dati meteo attuali e lo storico
4. Esplora i grafici interattivi per analizzare i trend

## 🔧 Configurazione

L'applicazione è configurata per funzionare immediatamente con Docker. Le configurazioni principali si trovano in:

- `docker-compose.yml`: Configurazione dei servizi
- `backend/src/main/resources/application.properties`: Configurazione del backend
- `db/init.sql`: Schema iniziale del database

## 📱 Struttura del progetto

```
Meteo/
├── backend/           # API REST Spring Boot
├── frontend/          # Interfaccia web
├── db/               # Script database MySQL
├── docker-compose.yml # Configurazione Docker
└── README.md         # Questa documentazione
```

## 🛑 Risoluzione problemi

**L'applicazione non si avvia?**
- Verifica che Docker sia in esecuzione
- Assicurati che le porte 8080 e 8081 siano libere
- Prova a riavviare con: `docker-compose down && docker-compose up --build`

**Errori di connessione al database?**
- Attendi qualche secondo in più per l'inizializzazione di MySQL
- Controlla i log con: `docker-compose logs`

## 📄 Licenza

Questo progetto è distribuito sotto licenza MIT.


