/* Esercizio 2 - Pieraccini Francesco */

import java.util.*;
import java.lang.*;

public class Messaggio {
    /*
        OVERVIEW: classe messaggio - un messaggio è un nodo del microblog; contiene tutte le informazioni necessarie ai metodi del microblog (myTw).
        Valore tipico: {i, c, tw, t}
    */
    
    private User utente;        // Chi ha scritto il messaggio
    private int codice;         // Codice identificatore del messaggio
    private String tweet;       // Contenuto messaggio
    private Tag tag;            // Tag del messaggio
    
    public Messaggio(int c , String tw, User u, Tag t){
        /*
            MODIFIES: this.
            EFFECTS: inizializza this assegnando u alla variabile di istanza utente, c a codice, tw a tweet e t a tag.
        */
        
        utente = u;
        codice = c;
        tweet = tw;
        tag = t;
    }
    
    /*
        Funzioni che mi restituiscono i valori di un messaggio, utilizzati per implementare i metodi.
        Meglio che rendere le variabili di istanza pubbliche perché in questo modo possono essere lette, ma non modificate.
    */
    
    public User getUtente(){
        /*
            EFFECTS: restituisce un User contenente la variabile di istanza utente.
        */
        
        return utente;
    }
    
    public int getCodice(){
        /*
            EFFECTS: restituisce un intero contenente la variabile di istanza codice.
        */
        
        return codice;
    }
    
    public String getTweet(){
        /*
            EFFECTS: restituisce una stringa contenente la variabile di istanza tweet.
        */
        
        return tweet;
    }
    
    public Tag getTag(){
        /*
            EFFECTS: restituisce una stringa contenente la variabile di istanza tag.
        */
        
        return tag;
    }
}