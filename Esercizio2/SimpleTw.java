/* Esercizio 2 - Pieraccini Francesco */

import java.util.*;
import java.lang.*;

interface SimpleTw {
    
    
    void addUser(User bob, String pass) throws UnauthorizedAccessException;
    /*
        MODIFIES: this.
        EFFECTS: inserisce l'utente bob all'insieme degli utenti. Solleva un'eccezione se pass non è corretta o (AGGIUNTA ALLE SPECIFICHE) se l'utente è già presente.
    */
    
    // AGGIUNTA: solleva un'eccezione se l'utente è già presente.Permette comunque che due utente
    // diversi abbiano lo stesso nick, per evitare quello bisognerebbe fare ulteriori controlli.
    
    void deleteUser(User bob, String pass) throws UnauthorizedAccessException;
    /*
        MODIFIES: this.
        EFFECTS: elimina l’utente bob dall’insieme degli utenti. Solleva una eccezione se pass non è corretta.
    */
    
    int tweet(String message, Tag t, User bob) throws UnauthorizedUserException, MsgException;
    /*
        MODIFIES: this.
        EFFECTS: inserisce un messaggio di bob con intestazione t. Restituisce un codice numerico del messaggio. 
        Lancia un’eccezione se bob non è un utente registrato o se il messaggio supera la dimensione massima.
    */
    
    String readLast(User bob) throws EmptyMsgException;
    /*
        EFFECTS: restituisce l’ultimo messaggio inserito da bob. Solleva un’eccezione se non ci sono messaggi di bob.
    */
    
    String readLast() throws EmptyMsgException;
    /*
        EFFECTS: restituisce l’ultimo messaggio inserito. Solleva un’eccezione se non sono presenti messaggi.
    */
    
    List<String> readAll(Tag t);
    /*
        EFFECTS: restituisce tutti i messaggi inseriti con Tag t, nell’ordine di inserimento.
    */
    
    List<String> readAll();
    /* 
        EFFECTS: restituisce tutti i messaggi inseriti, nell’ordine di inserimento.
    */
    
    void delete(int code) throws WrongCodeException;
    /*
        MODIFIES: this
        EFFECTS: Cancella il messaggio identificato da code.
        Solleva un’eccezione se non esiste un messaggio con quel codice.
    */
    
    boolean empty();
    /*
        EFFECTS: restituisce true se e solo se non sono presenti messaggi.
    */
}

class User {
    /*
        OVERVIEW: classe utente - contiene unicamente la variabile d'istanza nick (come da specifiche).
    */
    
    private String nick;
    
    public User(String nick) {
        /*
            MODIFIES: this.
            EFFECTS: inizializza this assegnando il valore passato al costruttore (nick) alla variabile di istanza nick.
        */
        
        this.nick = nick;
    }
    
    public boolean equals(User u) {
        // Funzione di uguaglianza fra due utenti (saranno uguali se avranno lo stesso nick).
        /*
            EFFECTS: restituisce true se il nick di this è uguale a quello di u.
        */
        
        if(nick.compareTo(u.getNick()) == 0)
            return true;
        return false;
    }
    
    public String getNick() {
        /*
            EFFECTS: restituisce una stringa contenente la variabile di istanza nick.
        */
        
        return nick;
    }
}


class Tag {
    /*
        OVERVIEW: classe tag - etichetta che identifica una categoria di messaggi (simile all'utilizzo degli hashtag sui social networks).
        Contiene una stringa nomeTag.
    */

    private String nomeTag;
    
    public Tag(String t) {
        /*
            MODIFIES: this.
            EFFECTS: inizializza this assegnando t alla variabile di istanza nomeTag.
        */
        
        nomeTag = t;
    }
    
    public boolean equals(Tag t) {
        // Funzione di uguaglianza fra due Tag (saranno uguali se avranno lo stesso nomeTag).
        /*
            EFFECTS: restituisce true se il nomeTag di this è uguale a quello di t.
        */
        
        if(nomeTag.compareTo(t.getNomeTag()) == 0)
            return true;
        return false;
    }
           
    public String getNomeTag() {
        /*
            EFFECTS: restituisce una stringa contenente la variabile di istanza nomeTag.
        */
        
        return nomeTag;
    }
    
}

// Definizioni delle classi eccezioni.

class UnauthorizedAccessException extends Exception {
    public UnauthorizedAccessException() {
        super();
    }
    public UnauthorizedAccessException(String s) {
        super(s);
    }
}

class UnauthorizedUserException extends Exception {
    public UnauthorizedUserException() {
        super();
    }
    public UnauthorizedUserException(String s) {
        super(s);
    }
}

class MsgException extends Exception {
    public MsgException() {
        super();
    }
    public MsgException(String s) {
        super(s);
    }
}

class EmptyMsgException extends Exception {
    public EmptyMsgException() {
        super();
    }
    public EmptyMsgException(String s) {
        super(s);
    }
}

class WrongCodeException extends Exception {
    public WrongCodeException() {
        super();
    }
    public WrongCodeException(String s) {
        super(s);
    }
}