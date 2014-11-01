(ns bootcamp.basics)

;;
;; Clojure basics:
;; ---------------
;; - Literals
;; - Keywords and symbols
;; - Functions

; Literals:
; ---------

;
; Scalar literals:
;

(type 1337)              ;=> java.lang.Long
(type 3.14159)           ;=> java.lang.Double
(type true)              ;=> java.lang.Boolean
(type "Hello, world")    ;=> java.lang.String
(type \m)                ;=> java.lang.Character
(type #"foo\s+bar")      ;=> java.util.regex.Pattern

; nil: Same as 'null' in Java

nil                      ;=> nil

;
; Keywords:
;

; - Evaluate to themselves
; - Fast equality test
; - Are functions

:foo                        ;=> :foo
(= :foo :foo)               ;=> true
(keyword? :foo)             ;=> true
(keyword? "foo")            ;=> false
(keyword? (keyword "foo"))  ;=> true

; = function uses Object.equals
(= "foobar" (str "foo" "bar"))                     ;=> true

; identical? uses Java == operator:
(identical? "foobar" (str "foo" "bar"))            ;=> false

; Equal keywords are always identical:
(identical? :foobar (keyword (str "foo" "bar")))   ;=> true

;
; Symbols:
;

; - Used to refer to something else
; - Evaluate to that 'something'

; uncomment and evaluate this:
; answer                      ;=> CompilerException java.lang.RuntimeException: Unable to resolve symbol: bar in this context

(quote answer)              ;=> answer
'answer                     ;=> answer

; Bind "something" to a namespace so that symbol can refer to it

(def answer 42)

answer                      ;=> 42

; Functions:

(def say-hello (fn [your-name]
                 (println "Hello," your-name)))

(say-hello "world")    ; stdout: "Hello, world"

; Conveniency macro for (def func-name (fn [args] body))

(defn say-hello [your-name]
  (println "Hello," your-name))

; Multi-arity

(defn say-hello
  ([]
    (say-hello "world!"))
  ([your-name]
    (println "Hello," your-name)))

(say-hello)          ; stdout: "Hello, world!"
(say-hello "foo")    ; stdout: "Hello, foo"

; Last thing evaluated in function is the return value:

(defn multiply-by-2 [some-value]
  (* some-value 2))

(multiply-by-2 21)  ;=> 42

; Closures:

(defn make-multiplier [multiplier]
  (fn [some-value]
    (* some-value multiplier)))

(def doubler (make-multiplier 2))
(def tripler (make-multiplier 3))

(doubler 42)  ;=> 84
(tripler 42)  ;=> 126

;;
;; if - conditional evaluation:
;;

(println (if true
           "seems ok"))
; stdout:  seems ok

(println (if false
           "seems weird"
           "still ok"))
; stdout: still ok

;;
;; let - create lexical bindings:
;;

(let [message "Hello,"
      user    "<your name here>"]
  (println message user))
; stdout: Hello, <your name here>

(let [a "a"
      b "b"]
  (println "1:" a b)
  (let [b "updated"]
    (println "2:" a b))
  (println "3:" a b))
; stdout:
;   1: a b
;   2: a updated
;   3: a b
