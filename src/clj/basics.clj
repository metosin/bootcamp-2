(ns basics)

;;
;; Clojure basics:
;; ---------------
;; - Literals
;; - Keywords and symbols
;; - Functions

; Literals:
; ---------

; Scalar literals:

(type 1337)              ;=> java.lang.Long
(type 3.14159)           ;=> java.lang.Double
(type true)              ;=> java.lang.Boolean
(type "Hello, world")    ;=> java.lang.String
(type \m)                ;=> java.lang.Character

; Collection literals:

[1 2 3]

'(3.14159 2.71828 1.41421)

{"greeting"   "Hello"
 "object"     "world"}

#{\newline \return \space}

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

;
; Symbols:
;

; - Used to refer to something else
; - Evaluate to that 'something'

; uncomment and evaluate this:
; bar                         ;=> CompilerException java.lang.RuntimeException: Unable to resolve symbol: bar in this context

(quote bar)                 ;=> bar
'bar                        ;=> bar
(symbol? 'bar)              ;=> true

; Bind "something" to a namespace so that symbol can refer to it

(def answer 42)

'answer                     ;=> answer
(symbol? 'answer)           ;=> true
answer                      ;=> 42

; Regular expressions:

(type #"foo\s+bar")         ;=> java.util.regex.Pattern

; nil: Same as 'null' in Java


nil                         ;=> nil

; Functions:

(def say-hello (fn [your-name]
                 (println "Hello," your-name)))

(say-hello "world")
;=> stdout: "Hello, world"

; Conveniency macro for (def func-name (fn [args] body))

(defn say-hello [your-name]
  (println "Hello," your-name))

; Multi-arity

(defn say-hello
  ([]
    (say-hello "world!"))
  ([your-name]
    (println "Hello," your-name)))

(say-hello)
;=> stdout: "Hello, world!"

(say-hello "foo")
;=> stdout: "Hello, foo"

; Last thing evaluated in funcvtion body is the return value:

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
