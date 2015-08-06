(* Esercizio 1 - Pieraccini Francesco *)

open Printf

(* Cerca associazione tra nome ed oggetto nell'ambiente env - la utilizzerò nell'implementazione di alcuni costrutti *)

let bind (env, nome, ogg) = (nome,ogg) :: env;;

(* Ambiente vuoto *)

let emptyenv = [];;

(* Cerco il valore della variabile nell'ambiente *)

let rec lookup (var, env) =
    match env with
        (nome, valore) :: env1 -> if nome = var then valore else lookup (var,env1)
        | [] -> failwith "Valore non trovato" ;;

(* Sintassi astratta del linguaggio *)

(* Identificatori memorizzati come stringhe *)

type ide = string

(* Tipi di dati memorizzabili *)

and eval =
      Int of int
    | Bool of bool
    | Unbound
    | Funval of efun

and efun = ide * exp * env

(* Ambiente - lista di bindings (coppie <nome, valore>) *)

and env = ( ide * eval ) list

(*

Pattern per l'espressione "try...with...":
1. Composizione di pattern
2. Pattern elementare
3. Pattern di Default

*)

and pat =
      PatComp of exp * exp * pat
    | PatEl of exp * exp
    | PatDe of exp

and exp =

(* Identificatori *)

      Ide of ide

    | Val of eval

(* Operazioni su Interi *)

    | Prod of exp * exp | Somm of exp * exp | Diff of exp * exp | Ug of exp * exp
    | MinUg of exp * exp

(* Operazioni su Booleani *)

    | Or of exp * exp | And of exp * exp | Not of exp

(* Condizionale, Blocco Let, Astrazione di funzione, Applicazione Funzionale, Espressione "try...with..." *)

    | Ifthenelse of exp * exp * exp

    | Let of ide * exp * exp

    | Fun of ide * exp

    | Appl of exp * exp

    | TryWith of ide * exp * pat;;

(* Type Checker, controlla che un valore (semantico) sia di un dato tipo *)

let typecheck(tipo , valore) = match tipo with
      "int" -> (match valore with
    | Int(u) -> true
    | _ -> false)
    | "bool"-> (match valore with
    | Bool(u) -> true
    | _ -> false)
    | _ -> failwith ("Tipo non valido")

(* Operazioni primitive *)

let ug (x,y) =
    if typecheck("int",x) && typecheck("int",y) then (
        match (x,y) with
              (Int(u), Int(w)) -> Bool(u = w)
            | (_, _) -> failwith ("Tipo non valido") )
    else failwith ("Errore col tipo")

let minug (x,y) =
    if typecheck("int",x) && typecheck("int",y) then (
        match (x,y) with
              (Int(u), Int(w)) -> Bool(u <= w)
            | (_, _) -> failwith ("Tipo non valido") )
    else failwith ("Errore col tipo")

let somm (x,y) =
    if typecheck("int",x) && typecheck("int",y) then (
        match (x,y) with
              (Int(u), Int(w)) -> Int(u+w)
            | (_, _) -> failwith ("Tipo non valido") )
    else failwith ("Errore col tipo")

let diff (x,y) =
    if typecheck("int",x) && typecheck("int",y) then (
        match (x,y) with
              (Int(u), Int(w)) -> Int(u-w)
            | (_, _) -> failwith ("Tipo non valido") )
    else failwith ("Errore col tipo")

let prod (x,y) =
    if typecheck("int",x) && typecheck("int",y) then (
        match (x,y) with
              (Int(u), Int(w)) -> Int(u*w)
            | (_, _) -> failwith ("Tipo non valido") )
    else failwith ("Errore col tipo")

let et (x,y) =
    if typecheck("bool",x) && typecheck("bool",y) then (
        match (x,y) with
              (Bool(u), Bool(w)) -> Bool(u && w)
            | (_, _) -> failwith ("Tipo non valido") )
    else failwith ("Errore col tipo")

let vel (x,y) =
    if typecheck("bool",x) && typecheck("bool",y) then (
        match (x,y) with
              (Bool(u), Bool(w)) -> Bool(u || w)
            | (_, _) -> failwith ("Tipo non valido") )
    else failwith ("Errore col tipo")

let non x =
    if typecheck("bool",x) then (
        match x with
              Bool(y) -> Bool(not y)
            | (_) -> failwith ("Tipo non valido") )
    else failwith ("Errore col tipo");;

(* Funzione per il Pattern Matching *)

let rec funpat ((p: pat), (env)) =
    match p with

(* Pattern composto *)

          PatComp(g,e,p1) -> (
            match sem ( g, env ) with
                          Bool (true) -> e
                        | Bool (false) -> funpat (p1, env)
                        | _ -> failwith "La guardia deve essere booleana" )

(* Pattern elementare *)

        | PatEl(g,e) -> (
            match sem (g, env) with
                      Bool (true) -> e
                    | Bool (false) -> failwith "No pattern found"
                    | _ -> failwith "La guardia deve essere booleana" )

(* Pattern di default *)

        | PatDe(e) -> e

(* Interprete del linguaggio *)

and sem ((e: exp), (r: env)) =
    match e with

          Ide (ide) -> lookup(ide, r)

        | Val (v) -> v

        | Prod (a,b) -> prod (sem (a, r), sem (b, r))

        | Somm (a,b) -> somm (sem (a, r), sem (b, r))

        | Diff (a,b) -> diff (sem (a, r), sem (b, r))

        | Ug (a,b) -> ug (sem (a, r) ,sem (b, r) )

        | MinUg (a,b) -> minug (sem (a, r) ,sem (b, r) )

        | And (a,b) -> et (sem (a, r), sem (b, r))

        | Or (a,b) -> vel (sem (a, r), sem (b, r))

        | Not (a) -> non (sem (a, r))

        | Ifthenelse (a,b,c) -> let g = sem (a, r) in
                                    if typecheck ("bool", g) then(
                                        if g = Bool (true) then sem (b, r)
                                        else sem (c, r))
                                    else failwith ("La guardia deve essere booleana")

        | Let (i, e1, e2) -> sem (e2, bind(r, i, sem (e1, r)))

        | Fun (x, a) -> Funval (x, a, r)

        | Appl ( e1, e2 ) -> (match sem (e1, r) with
                                  Funval ( x, a, r1 ) ->
                                    sem (a, bind (r1, x, sem (e2,r)))
                                | _ -> failwith ("Il primo valore di Appl deve essere un Funval"))

(*

Prima mi creo un nuovo ambiente con l'associazione fra i (identificatore) ed e (espressione), valuto il pattern nella funzione funpat per vedere di che tipo (composto o elementare o di default) è (cioè faccio il pattern matching), infine valuto il pattern risultante nel nuovo ambiente ottenuto.

*)

        | TryWith (i, e, pattern) -> let r1 = bind(r, i, sem (e, r)) in
                                        let mypat = funpat (pattern, r1) in
                                            sem (mypat, r1);;

