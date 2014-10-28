(ns functions
  (:require [clojure.string :as s]
            [util.books :refer [books]]))

;;
;; Functions:
;;

(defn multiplier [m]
  (fn [v]
    (* m v)))

(def doubler (multiplier 2))

(doubler 5)   ;=> 10

; partial:
; --------

(def tripler (partial * 3))

(tripler 5)   ;=> 15

; comp:
; -----

(defn replace-spaces [s]
  (s/replace s #"\s+" "-"))

(map replace-spaces (map s/upper-case (map :name books)))  ;=> ("THE-JOY-OF-CLOJURE" "ERLANG-PROGRAMMING"...

(map (comp replace-spaces s/upper-case :name) books)       ;=> ("THE-JOY-OF-CLOJURE" "ERLANG-PROGRAMMING"...

; apply:
; ------

(+ 1 2 3)          ;=> 6
;(+ [1 2 3])        ;=> ClassCastException Cannot cast clojure.lang.PersistentVector to java.lang.Number
(apply + [1 2 3])  ;=> 6
