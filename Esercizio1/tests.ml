(* Esercizio 1 - Pieraccini Francesco *)

open Printf

(* UN PAIO DI TEST SULLE OPERAZIONI *)

(*

Test Somma e Prodotto:

let e = 17 + (22 * 100)

*)

let e = Somm (Val (Int 17),
Prod (Val (Int 100), Val (Int 22)));;

print_string ("Risultato atteso 2217\n");;
sem(e, []);;

(*

Test Differenza:

let e = 2 - 7

*)

let e = Diff (Val (Int 2), Val (Int 7));;

print_string ("Risultato atteso -5\n");;
sem(e, []);;

(*

Test And, Or, Not:

let e = !true && (false || true)

*)

let e = And (Not (Val (Bool true)), Or (Val (Bool false), Val (Bool true)));;

print_string ("Risultato atteso false\n");;
sem(e, []);;


(* TEST COSTRUTTO TRY-WITH *)

(*

Test try-with con pattern composto (condizione true <= 0):

let z = 3 in
let y = -4 in
try x with +(z,y)	in
|(x	> 0)    ->	(x+1)
|(x <= 0)   ->  -x
|(_	->0)

*)

let x =
Let ("z", Val (Int (3)),
Let ("y", Val (Int (-4)),
TryWith ("x", (Somm (Ide "z",Ide "y")),
(PatComp( (Not (MinUg (Ide "x", Val (Int 0)))), Somm (Ide "x",Val (Int 1)),
(PatComp( (MinUg (Ide "x", Val (Int 0))), Diff (Val (Int 0), Ide ("x")),
PatDe (Val (Int 0)))))))));;

print_string ("Risultato atteso 1\n");;
sem (x,[]);;

(*

Test try-with con pattern composto (condizione true > 0):

let z = 3 in
let y = 4 in
try x with +(z,y)	in
|(x	> 0)    ->	(x+1)
|(x <= 0)   ->  -x
|(_	->0)

*)

let x =
Let ("z", Val (Int (3)),
Let ("y", Val (Int (4)),
TryWith ("x", (Somm (Ide "z",Ide "y")),
(PatComp( (Not (MinUg (Ide "x", Val (Int 0)))), Somm (Ide "x",Val (Int 1)),
(PatComp( (MinUg (Ide "x", Val (Int 0))), Diff (Val (Int 0), Ide ("x")),
PatDe (Val (Int 0)))))))));;

print_string ("Risultato atteso 8\n");;
sem (x,[]);;

(*

Test try-with con pattern elementare (condizione false)

let z = -1 in
let y = 0 in
try x	with +(z,y)	in
|(x	> 0)    ->	(x+1)

*)

(*

    COMMENTO IN QUANTO LANCIA ECCEZIONE:

let x =
try
Let ("z", Val (Int (-1)),
Let ("y", Val (Int (0)),
TryWith ("x", (Somm (Ide "z",Ide "y")),
(PatEl( (Not (MinUg (Ide "x", Val (Int 0)))), Somm (Ide "x",Val (Int 1)))))))
with Failure explanation -> Ide "print_endline explanation";;

sem (x,[]);;

Risultato: Exception: Failure "No pattern found".

*)

(*

Test try-with con pattern elementare (condizione true)

let z = 1 in
let y = 0 in
try x	with +(z,y)	in
|(x	> 0)    ->	(x+1)

*)

let x =
Let ("z", Val (Int (1)),
Let ("y", Val (Int (0)),
TryWith ("x", (Somm (Ide "z",Ide "y")),
(PatEl( (Not (MinUg (Ide "x", Val (Int 0)))), Somm (Ide "x",Val (Int 1)))))));;

print_string ("Risultato atteso 2\n");;
sem (x,[]);;