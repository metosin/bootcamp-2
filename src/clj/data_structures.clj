(ns data-structures)

;;
;; Data-structures:
;; ---------------
;; - Literals
;; - Persistent data-structures
;; - Sequences

; Vectors:

(def some-primes [2 3 5 7 11 13 17 19])

(nth some-primes 0)        ;=> 2
(peek some-primes)         ;=> 19
(pop some-primes)          ;=> [2 3 5 7 11 13 17]
(subvec some-primes 3)     ;=> [7 11 13 17 19]
(subvec some-primes 3 5)   ;=> [7 11]

(conj some-primes 23)      ;=> [2 3 5 7 11 13 17 19 23]

(vector? some-primes)      ;=> true
(vector 1 2 3)             ;=> [1 2 3]

; List:

(def some-facts '(1 1 2 6 24 120))

(nth some-facts 0)        ;=> 1
(peek some-facts)         ;=> 1
(pop some-facts)          ;=> (1 2 6 24 120)

(conj some-facts 0)       ;=> (0 1 1 2 6 24 120)

(list? some-facts)        ;=> true
(list 1 2 3)              ;=> (1 2 3)

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
;   Java:                Clojure:
;   java.util.List       list
;   java.util.Iterator   seq

(type [1 2 3])                 ;=> clojure.lang.PersistentVector
(type (seq [1 2 3]))           ;=> clojure.lang.PersistentVector$ChunkedSeq

(count [1 2 3])                ;=> 3 in O(1)
(count (seq [1 2 3]))          ;=> 3 in O(n)

(seq [1 2 3])                  ;=> (1 2 3)
(seq [])                       ;=> nil

; core functions that work with data-structures:
;   (func <data-structure> args)
; core functions that work with seq's:
;   (func args <seq>)

; For example:

(conj [1 2] 3)                 ;=> [1 2 3]
(cons 3 [1 2])                 ;=> (3 1 2)

; Common functions that work with seq:

(first (seq [1 2 3]))          ;=> 1
(rest (seq [1 2 3]))           ;=> (2 3)
(next (seq [1 2 3]))           ;=> (2 3)

(first (seq [1]))              ;=> 1
(rest (seq [1]))               ;=> ()
(next (seq [1]))               ;=> nil

; Sequences can be infinitely long:

(def natural-numbers (range))

; Don't try to evaluate this:
; (count natural-numbers)      ;=> core of the sun will run out of hydrogen before this returns
