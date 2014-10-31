(ns bootcamp.data-structures)

;;
;; Data-structures:
;; ---------------
;; - Literals
;; - Persistent data-structures
;; - Sequences

; Vectors:

(def some-primes [2 3 5 7 11 13 17 19])

(count some-primes)        ;=> 8
(nth some-primes 0)        ;=> 2
(nth some-primes 1)        ;=> 3
(conj some-primes 23)      ;=> [2 3 5 7 11 13 17 19 23]
some-primes                ;=> [2 3 5 7 11 13 17 19]

(vector? some-primes)      ;=> true
(vector 1 2 3)             ;=> [1 2 3]

; See http://clojure.org/cheatsheet

; List:

(def some-happy-numbers '(1 7 10 13 19 23 28))  ; https://en.wikipedia.org/wiki/Happy_number

(nth some-happy-numbers 0)   ;=> 1
(nth some-happy-numbers 1)   ;=> 7
(peek some-happy-numbers)    ;=> 1
(pop some-happy-numbers)     ;=> (7 10 13 19 23 28)

(conj some-happy-numbers 0)  ;=> (0 10 13 19 23 28)

(list? some-happy-numbers)   ;=> true
(list 1 2 3)                 ;=> (1 2 3)

; Pay attention:

(conj [1 2 3]  0)   ;=> [1 2 3 0]
(conj '(1 2 3) 0)   ;=> (0 1 2 3)

; Maps:

(def person {:name  "<your name here>"
             :email "foo@bar.com"})

person                               ;=> {:email "foo@bar.com", :name "<your name here>"}

(get person :name)                   ;=> "<your name here>"
(get person :title)                  ;=> nil
(get person :title "programmer")     ;=> "programmer"

(keys person)                        ;=> (:email :name)
(vals person)                        ;=> ("foo@bar.com" "<your name here>")

(assoc person :title "programmer")   ;=> {:email "foo@bar.com", :name "<your name here>", :title "programmer"}
person                               ;=> {:email "foo@bar.com", :name "<your name here>"}

(dissoc person :email)               ;=> {:name "<your name here>"}
person                               ;=> {:email "foo@bar.com", :name "<your name here>"}

; Map is also a function of its keys:

(get person :name)                   ;=> "<your name here>"
(person :name)                       ;=> "<your name here>"

; You can test if something is a function:

(ifn? person)                        ;=> true
(ifn? "foo")                         ;=> false

; Keywords are functions too

(ifn? :name)                         ;=> true
(:name person)                       ;=> "<your name here>"

; Sets:

(def planets #{:mercury, :venus, :earth, :mars, :jupiter, :saturn, :uranus})

(get planets :mars)          ;=> :mars
(get planets :pluto)         ;=> nil :(

(conj planets :neptune)      ;=> #{:mercury :uranus :mars :neptune :jupiter :earth :venus :saturn}
(disj planets :earth)        ;=> #{:mercury :uranus :mars :jupiter :venus :saturn}

; Set is also a function for its content

(planets :mars)              ;=> :mars
(planets :pluto)             ;=> nil

;
; Sequences
;

; mental map:
;   Clojure: | Java:              
;   -------- |----------------
;   list     | java.util.List
;   seq      | java.util.Iterator

; Convert to seq

(seq [1 2 3])                    ;=> (1 2 3)       
(seq '(1 2 3))                   ;=> (1 2 3)
(seq {:name "foo" :role "bar"})  ;=> ([:role "bar"] [:name "foo"])
(seq #{\a \e \i \o \u \y})       ;=> (\a \e \i \o \u \y)
(seq "hello")                    ;=> (\h \e \l \l \o)
(seq [])                         ;=> nil
(seq '())                        ;=> nil
(seq nil)                        ;=> nil

; core functions that work with data-structures:
;   (func <data-structure> args)
; core functions that work with seq's:
;   (func args <seq>)

; For example:

(conj [] 1)                 ;=> [1]
(cons 1 '())                ;=> (1)

; Common functions that work with seq:

(def happy (seq some-happy-numbers))

(first happy)  ;=> 1
(rest happy)   ;=> (7 10 13 19 23 28)
(next happy)   ;=> (7 10 13 19 23 28)

(first (seq [1]))              ;=> 1
(rest (seq [1]))               ;=> ()
(next (seq [1]))               ;=> nil

; Sequences can be infinitely long:

(def natural-numbers (range))

; Don't try to evaluate this:
; (count natural-numbers)      ;=> core of the sun will run out of hydrogen before this returns
