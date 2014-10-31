(ns bootcamp.working-with-data-test
  (:require [midje.sweet :refer :all]
            [bootcamp.util.books :refer [books]]))


;; filter:
;; -------

; Filter books that have less than 300 pages

(defn short? [book]
  )

;(fact
;  (count (filter short? books)) => 2)

; Filter books that speak about Clojure:

(defn clojure-book? [book]
  )

;(fact
;  (count (filter clojure-book? books)) => 3
;  (filter clojure-book? books) => (contains
;                                    {:name   "The Joy of Clojure"
;                                     :langs  #{:clojure}
;                                     :pages  328}))

(defn short-clojure-books []
  )

;(fact
;  (count (short-clojure-books))         => 1
;  (:name (first (short-clojure-books))) => "Programming Concurrency on the JVM")

;; map:
;; ----

; Book names:

(defn book->name [book]
  )

;(fact
;  (map book->name books) => (contains ["The Joy of Clojure" "Erlang Programming"]))

(defn clojure-book-names []
  ; Return a seq of names of books that are about clojure
  )

;(fact
;  (clojure-book-names)
;  => (just
;       ["The Joy of Clojure"
;        "Programming Concurrency on the JVM"
;        "Clojure Data Analysis Cookbook"]
;       :in-any-order))

;; reduce:
;; -------

(defn pages-about [lang]
  ; Return the number of pages we have on given language
  )

;(facts
;  (pages-about :fortran) => 623
;  (pages-about :java)    => 893
;  (pages-about :clojure) => 924
;  (pages-about :c++)     => 0)

;;
;; Bonus:
;;

; Implement filter and map functions using reduce:

(defn my-filter [pred? elements]
  )

(defn my-map [mapper elements]
  )

;(fact
;  (my-filter odd? [1 2 3]) => [1 3]
;  (my-map inc [1 2 3])     => [2 3 4])
