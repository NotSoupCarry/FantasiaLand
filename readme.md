# FantasiaLand

**FantasiaLand** è una piattaforma di gestione di creature fantasy, dove gli utenti possono visualizzare, aggiungere, aggiornare e assegnare custodi alle creature. È progettato per offrire un'interfaccia di amministrazione separata per gli utenti con il ruolo di **Admin**, mentre gli utenti normali possono solo visualizzare le creature.

## Funzionalità

- **Amministrazione:**
  - Gli amministratori possono gestire tutte le creature, aggiungere nuove creature, aggiornare le informazioni e assegnare un custode.
  
- **Utenti normali:**
  - Gli utenti possono visualizzare tutte le creature e aggiornare solo il livello di pericolo delle creature.

- **Gestione del login:**
  - Gli utenti con ruolo **Admin** vengono reindirizzati alla sezione di amministrazione (`/admin/creature`) dopo il login, mentre gli utenti normali vengono reindirizzati alla sezione delle creature (`/creature`).

## Tecnologie utilizzate

- **Spring Boot**: Per la gestione del backend e della logica di business.
- **Thymeleaf**: Come motore di templating per il frontend.
- **Spring Security**: Per la gestione della sicurezza e dei ruoli utente (Admin e Normale).
- **JPA e Hibernate**: Per la gestione della persistenza dei dati nel database.

## Setup

### Prerequisiti

Per eseguire il progetto, avrai bisogno di:

- **Java 17** o superiore
- **Maven** o **Gradle** per la gestione delle dipendenze
- Un **database relazionale** (ad esempio MySQL o H2, configurabile nel file di configurazione)

### Installazione

1. **Clona il repository**

   ```bash
   git clone https://github.com/NotSoupCarry/FantasiaLand
   cd fantasiaLand
