/* Esercizio 2 - Pieraccini Francesco */

import java.util.*;
import java.lang.*;

public class myTw implements SimpleTw {
    /*
        OVERVIEW: un SimpleTw è un componente software di sviluppo alla gestione di un semplice sistema di microblogging.
        Da un punto di vista astratto un microblog può essere visto come una collezione di contenuti di testo con una lunghezza massima.
        Un microblog è utilizzabile solamente tramite una registrazione: solo gli utenti registrati possono scrivere e leggere i messaggi presenti nel microblog.
        Valore tipico di myTw: 
            {pass, [user_1, ..., user_sizeu-1], {conta, [messaggio_1, ..., messaggio_sizem-1]}} 
        dove:
            sizeu = {users.size()}
            sizem = {collezione.getNumeroElem()}
        messaggio_i = {i, c, tw, t}.
     */

    private final String pass;                  // Password dell'oggetto.
    private HashSet<User> users;                // Non voglio che uno stesso utente sia presente
                                                // due volte
    private collezioneMessaggi collezione;      // contenente tutti i messaggi.
    
    /*   
     
        FUNZIONE DI ASTRAZIONE
            a(c) =  {pass, [user_1, ..., user_sizeu-1], {conta, [messaggio_1, ..., messaggio_sizem-1]}} dove
            sizeu = {users.size()}
            sizem = {collezione.getNumeroElem()}
            messaggio_i = {i, c, tw, t}
     
        INVARIANTE DI RAPPRESENTAZIONE
            I(c) = (collezione != null) && (forall x, y in [0, users.size()] => users.get(x) != users.get(y)) && (forall x in [0, collezione.getMessaggi().size()] => collezione.getMessaggi().get(x).length() < 141)
     
            .) La collezione utenti non deve essere null;
            .) Gli elementi della collezione utenti devono essere diversi (assunzione aggiunta nell'interfaccia);
            .) I messaggi all'interno della collezione di messaggi non devono essere più di 140 caratteri.
     
    */
    
    public myTw() {
        /*
            MODIFIES: this.
            EFFECTS: inizializza this creando un set di utenti vuoto, una password di default e una collezioneMessaggi vuota.
        */
        
        users = new HashSet<User>();
        this.pass = "0000";
        collezione = new collezioneMessaggi();
    }
    
    public myTw(String pass) {
        /*
            MODIFIES: this.
            EFFECTS: inizializza this creando un set di utenti vuoto, assegnando a pass il valore passato al costruttore (pass) e creando una collezioneMessaggi vuota.
        */
        
        users = new HashSet<User>();
        this.pass = pass;
        collezione = new collezioneMessaggi();
    }
    
    public void addUser(User bob, String pass) throws UnauthorizedAccessException {
        /*
            MODIFIES: this
            EFFECTS: inserisce l'utente bob all'insieme degli utenti. Solleva un'eccezione se pass non è corretta o (AGGIUNTA ALLE SPECIFICHE) se l'utente è già presente.
        */
        
        if(this.pass.compareTo(pass) != 0) throw new UnauthorizedAccessException("Password sbagliata");
        if(!users.add(bob)) throw new UnauthorizedAccessException("Utente già presente");
    }
    
    public void deleteUser(User bob, String pass) throws UnauthorizedAccessException {
        /*
            MODIFIES: this
            EFFECTS: elimina l’utente bob dall’insieme degli utenti. Solleva una eccezione se pass non è corretta.
        */

        if(this.pass.compareTo(pass) != 0) throw new UnauthorizedAccessException("Password sbagliata");
        users.remove(bob);
    }
    
    public int tweet(String message, Tag t, User bob) throws UnauthorizedUserException, MsgException {
        /*
            MODIFIES: this.
            EFFECTS: inserisce un messaggio di bob con intestazione t. Restituisce un codice numerico del messaggio.
            Lancia un’eccezione se bob non è un utente registrato o se il messaggio supera la dimensione massima.
        */
        
        if(!users.contains(bob)) throw new UnauthorizedUserException("Utente non registrato");
        if(message.length() > 140) throw new MsgException("Superato il limite di caratteri (140)");
        return collezione.inserisciMessaggio(t, message, bob);
    }
    
    public String readLast(User bob) throws EmptyMsgException {
        /*
            EFFECTS: restituisce l’ultimo messaggio inserito da bob. Solleva un’eccezione se non ci sono messaggi di bob.
        */
        
        String last = collezione.ultimoMessaggioDi(bob);
        if(last == null) throw new EmptyMsgException("Non ci sono messagi di "+bob.getNick());
        return last;
    }
    
    public String readLast() throws EmptyMsgException {
        /*
         EFFECTS: restituisce l’ultimo messaggio inserito. Solleva un’eccezione se non sono presenti messaggi.
        */
        
        if(empty()) throw new EmptyMsgException("Non ci sono messaggi");
        return collezione.ultimoMessaggio();
    }
    
    public List<String> readAll(Tag t) {
        /*
            EFFECTS: restituisce tutti i messaggi inseriti con Tag t, nell’ordine di inserimento.
        */
        
        List<String> lista = collezione.getMessaggiConTag(t);
        return lista;
    }
    
    public List<String> readAll() {
        /*
            EFFECTS: restituisce tutti i messaggi inseriti, nell’ordine di inserimento.
        */
        
        List<String> lista = collezione.getMessaggi();
        return lista;
    }
    
    public void delete(int code) throws WrongCodeException {
        /*
            MODIFIES: this.
            EFFECTS: Cancella il messaggio identificato da code.
            Solleva un’eccezione se non esiste un messaggio con quel codice.
        */

        if(!collezione.cancellaMessaggio(code)) throw new WrongCodeException("Non ci sono messaggi con codice "+code);
    }
    
    public boolean empty() {
        /*
            EFFECTS: restituisce true se e solo se non sono presenti messaggi.
        */
        
        if(collezione.getNumeroElem() == 0) return true;
        return false;
    }
    
}