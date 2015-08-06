public class provaMyTw {
    public static void main (String[] args) {
        myTw Tw1 = new myTw("pass1");
        myTw Tw2 = new myTw("pass2");
        User Bob = new User("Bob");
        User Fra = new User("Fra");
        User Fra1 = new User("Fra");
        Tag t = new Tag("Ciao");
        Tag t1 = new Tag("Ciao1");
        try{
            // Corretta add.
            System.out.println("Corretta add()");
            Tw1.addUser(Bob, "pass1");
            System.out.println("Inserito: " + Bob.getNick() + "\n");
            
        }
        catch(UnauthorizedAccessException e){
            e.printStackTrace();
            System.out.println("Errore.");
        }
        try{
            // Eccezione add 1.
            Tw2.addUser(Fra, "pass1");
            System.out.println("Errore");
        }
        catch(UnauthorizedAccessException e){
            e.printStackTrace();
            System.out.println("Eccezione add - Test passed. \n");
        }
        try{
            // Eccezione add 2.
            Tw1.addUser(Bob, "pass1");
            System.out.println("Errore");
        }
        catch(UnauthorizedAccessException e){
            e.printStackTrace();
            System.out.println("Utente già Presente - Test passed. \n");
        }
        try{
            // Eccezione delete.
            Tw1.addUser(Fra, "pass1");
            System.out.println("Inserito: " + Fra.getNick());
            Tw1.deleteUser(Fra, "pass2");
            System.out.println("Errore");
        }
        catch(UnauthorizedAccessException e){
            e.printStackTrace();
            System.out.println("Eccezione delete - Test passed. \n");
        }
        try{
            // Corretta tweet, readLast(User) e readLast().
            System.out.println("Corretta tweet, readLast(User) e readLast()).");
            Tw1.tweet("Ciao mondo", t, Fra); //0
            Tw1.tweet("Ciao mondo2", t, Fra); //1
            System.out.println("readLast(User): " + Tw1.readLast(Fra));
            System.out.println("readLast(): " + Tw1.readLast() + "\n");
        }
        catch(UnauthorizedUserException e){
            e.printStackTrace();
            System.out.println("Errore.");
        }
        catch(MsgException e){
            e.printStackTrace();
            System.out.println("Errore.");
        }
        catch(EmptyMsgException e){
            e.printStackTrace();
            System.out.println("Errore.");
        }
        try{
            // Eccezione tweet.
            Tw1.deleteUser(Bob, "pass1");
            Tw1.tweet("Ciao mondo", t, Bob);
            System.out.println("Errore");
        }
        catch(UnauthorizedAccessException e){
            e.printStackTrace();
            System.out.println("Errore");
        }
        catch(UnauthorizedUserException e){
            e.printStackTrace();
            System.out.println("Eccezione tweet - Test passed. \n");
        }
        catch(MsgException e){
            e.printStackTrace();
            System.out.println("Errore.");
        }
        try{
            // Eccezione delete.
            Tw1.delete(99);
            System.out.println("Errore");
        }
        catch(WrongCodeException e){
            e.printStackTrace();
            System.out.println("Eccezione delete - Test passed. \n");
        }
        try{
            // Corretta delete.
            System.out.println("Corretta delete.");
            Tw1.delete(0);
            System.out.println("Cancellato: 0");
            Tw1.delete(1);
            System.out.println("Cancellato: 1 \n");
        }
        catch(WrongCodeException e){
            e.printStackTrace();
            System.out.println("Errore.");
        }
        
        try{
            // Eccezione readLast(User).
            System.out.println("Errore readLast(User): " + Tw1.readLast(Fra));
        }
        catch(EmptyMsgException e){
            e.printStackTrace();
            System.out.println("Eccezione readLast(User) - Test passed. \n");
        }
        
        try{
            // readAll(Tag).
            System.out.println("readAll(Tag)");
            Tw1.tweet("Ciao mondo", t, Fra);  //2
            Tw1.tweet("Ciao mondo2", t, Fra); //3
            Tw1.tweet("Ciao mondo3", t, Fra); //4
            System.out.println("Inserito: " + Fra.getNick());
            System.out.println("readAll(t): " + Tw1.readAll(t) + "\n");
        }
        catch(UnauthorizedUserException e){
            e.printStackTrace();
            System.out.println("Errore.");
        }
        catch(MsgException e){
            e.printStackTrace();
            System.out.println("Errore.");
        }
        // readAll(Tag) nuovo tag.
        System.out.println("readAll nuovo tag: ");
        System.out.println("readAll(Tag): " + Tw1.readAll(t1) + "\n");
        // readAll().
        System.out.println("readAll()");
        System.out.println("readAll(): " + Tw1.readAll() + "\n");
        try{
            // Eccezione readLast().
            Tw1.delete(2);
            Tw1.delete(3);
            Tw1.delete(4);
            System.out.println("Errore readLast(): " + Tw1.readLast() + "\n");
        }
        catch(EmptyMsgException e){
            e.printStackTrace();
            System.out.println("Eccezione readLast() - Test passed. \n");
        }
        catch(WrongCodeException e){
            e.printStackTrace();
            System.out.println("Errore.");
        }
    }
}