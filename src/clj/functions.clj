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

(map s/upper-case (map :name books))    ;=> ("THE JOY OF CLOJURE" "ERLANG PROGRAMMING" "CLOJURE DATA ...

(map (comp s/upper-case :name) books)   ;=> ("THE JOY OF CLOJURE" "ERLANG PROGRAMMING" "CLOJURE DATA ...

