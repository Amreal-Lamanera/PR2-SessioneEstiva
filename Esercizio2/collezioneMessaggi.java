import java.util.*;
import java.lang.*;

public class collezioneMessaggi {
    /*
        OVERVIEW: classe collezioneMessaggi - una collezioneMessaggio è una collezione di elementi di tipo Messaggio. E' composta da un vettore di messaggi e da una variabile intera utilizzata come contatore per generare codici.
        Valore tipico: {conta, [messaggio_1, ..., messaggio_sizem-1]}} dove
        sizem = {collezione.getNumeroElem()}
        messaggio_i = {i, c, tw, t}.
    */
    
    private int conta;          // Per generare i codici.
    private Vector<Messaggio> collezione;
    
    public collezioneMessaggi() {
        /*
            MODIFIES: this.
            EFFECTS: inizializza this assegnando 0 a conta e assegnando a collezione un vettore vuoto.
        */
        
        conta = 0;
        collezione = new Vector<Messaggio>();
    }
    
    public int inserisciMessaggio(Tag t, String messaggio, User utente) {
        /*
            MODIFIES: this.
            EFFECTS: inserisce un nuovo Messaggio nel vettore collezione di this e ritorna un intero (il codice del messaggio -conta), poi incrementa il "generatore di codici" conta.
        */
        
        collezione.add(new Messaggio(conta, messaggio, utente, t));
        return ++conta;
    }
    
    public String ultimoMessaggioDi(User u) {
        /*
         EFFECTS: restituisce l’ultimo messaggio inserito da u.
        */
        
        String ultimo = null;
        for(int i = 0; i < collezione.size(); i ++) {
            if(collezione.get(i).getUtente().equals(u)) ultimo = collezione.get(i).getTweet();
        }
        return ultimo;
    }
    
    public int getNumeroElem() {
        /*
            EFFECTS: restituisce il numero degli elementi della collezione.
        */
        
        return collezione.size();
    }
    
    public String ultimoMessaggio() {
        /*
            EFFECTS: restituisce l'ultimo messaggio.
        */
        
        return collezione.get(collezione.size()-1).getTweet();
    }
    
    public List<String> getMessaggiConTag(Tag t) {
        /*
            EFFECTS: restituisce la lista dei messaggi con tag t.
        */
         
        List<String> lista = new Vector<String>();
        for(int i = 0; i < collezione.size(); i ++){
            if(collezione.get(i).getTag().equals(t))
                lista.add(collezione.get(i).getTweet());
        }
        return lista;
    }
    
    public List<String> getMessaggi() {
        /*
            EFFECTS: Restituisce la lista di tutti i messaggi.
        */
        
        List<String> lista = new Vector<String>();
        for(int i = 0; i < collezione.size(); i ++)
            lista.add(collezione.get(i).getTweet());
        return lista;
    }
    public boolean cancellaMessaggio(int codice){
        /*
            MODIFIES: this
            EFFECTS: Cancella il messaggio identificato da codice.
        */
        
        boolean cancellato = false;
        for(int i = 0; i < collezione.size(); i ++){
            if(collezione.get(i).getCodice() == codice) {
                collezione.removeElementAt(i);
                cancellato = true;
            }
        }
        return cancellato;
    }
}
