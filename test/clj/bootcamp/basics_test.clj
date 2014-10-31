(ns basics-test
  (:require [midje.sweet :refer :all]))

;;
;; Midje syntax:
;;

(fact
  (+ 1 2)   => 3
  (- 44 2)  => 42)

;;
;; Excercises for basics:
;;

;;
;; functions:
;; ----------
;;

(defn add-13 [some-value]
  ; put your code here
  )

;(fact
;  (add-13 10) => 23)

; arities:

(defn add
  ([]
    )
  ([value-a]
    )
  ([value-a value-b]
    ))

;(fact
;  (add)      => 0
;  (add 1)    => 1
;  (add 1 2)  => 3)

(defn get-greeting [your-name]
  ; hint: see http://clojure.github.io/clojure/clojure.core-api.html#clojure.core/str
  )

;(fact
;  (get-greeting "world")  => "Hello, world!"
;  (get-greeting "")       => "Hello, !"
;  (get-greeting nil)      => "Hello, !")

;;
;; if
;;

(defn user-is-root [username]
  )

;(fact
;  (user-is-root "root")  => truthy
;  (user-is-root "pena")  => falsey
;  (user-is-root nil)     => falsey)
