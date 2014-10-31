(ns bootcamp.macro-sweetness
  (:require [bootcamp.util.books :refer [books]]))

;; Macro sweetness:
;; ----------------
;; or: why macros are so cool?

; Your task, should you accept it:
;   From the books, find those that are about clojure, and are longer
;   than 300 pages, return their names.

; Step 1: filter books about clojure

(defn clojure-book? [book]
  (:clojure (:langs book)))

(filter clojure-book? books)                                  ;=> ({:name "The Joy of Clojure", :pages 328, :langs #{:clojure}} {:name "Clojure Data....

; Step 2: filter long books

(defn long-book? [book]
  (> (:pages book) 300))

(filter long-book? (filter clojure-book? books))             ;=> ({:name "The Joy of Clojure", :pages 328, :langs #{:clojure}} {:name "Clojure Data....

; Step 3: map book names

(map :name (filter long-book? (filter clojure-book? books))) ;=> ("The Joy of Clojure" "Clojure Data Analysis Cookbook")

; Problem: Joda talk is "logic difficult to see"

(->> books
     (filter clojure-book?)
     (filter long-book?)
     (map :name))
;=> ("The Joy of Clojure" "Clojure Data Analysis Cookbook")

; ->> is a macro that converts sequential steps to nested strcture.
; The result of macro is code

(->> [1 2 3]
     (filter odd?)
     (map inc))

; After macro expansion, clojure compiler sees:

(map inc (filter odd? [1 2 3]))

; There is no runtime penalty

(macroexpand '(->> [1 2 3]
                   (filter odd?)
                   (map inc)))
;=> (map inc (filter odd? [1 2 3]))

; ->> puts previous result to _last_ element in next expression
; ->  puts previous result to _first_ element in next expression

; ->> great when working with seqs
; ->  great when working with data-structures

(require '[clojure.string :as s])

(def text "Object-oriented design is the roman numerals of computing") ; by Rob Pike

(s/split (s/upper-case (s/replace text #"design" "programming")) #"\s+")
;=> ["OBJECT-ORIENTED" "PROGRAMMING" "IS" "THE" "ROMAN" "NUMERALS" "OF" "COMPUTING"]

(-> text
    (s/replace #"design" "programming")
    (s/upper-case)
    (s/split #"\s+"))
;=> ["OBJECT-ORIENTED" "PROGRAMMING" "IS" "THE" "ROMAN" "NUMERALS" "OF" "COMPUTING"]

;;
;; Java 8 has now the "try with resources":
;;
;;   try (BufferedReader br = new BufferedReader(new FileReader(path))) {
;;     return br.readLine();
;;   }
;;
;; It took 18 years to make it.
;;

;; Same idiom in Clojure:

; (let [resource ...make-resource...]
;   (try
;     ...use-resource...
;     (finally
;       (.close reource))))

(defmacro try-with-resource [resource make-resource & use-resource]
  `(let [~'resource ~make-resource]
     (try
       ~@use-resource
       (finally
         (.close ~'resource)))))

(macroexpand '(try-with-resource r (open-some-file) (do-some-stuff) (work-with-file) (heavy-stuff-here)))

;=> (let* [resource (open-some-file)]
;     (try
;       (do-some-stuff)
;       (work-with-file)
;       (heavy-stuff-here)
;       (finally
;         (.close resource))))

; Note:
;   Clojure already has with-open that does this (better). 
;   See http://clojure.github.io/clojure/clojure.core-api.html#clojure.core/with-open
