(ns data-structures-test
  (:require [midje.sweet :refer :all]))

;;
;; Data-structures:
;; ----------------
;;

(defn get-name [person]
  )

;(fact
;  (get-name {:name "foo"}) => "foo"
;  (get-name {}             => "default"))


(def metosin {:name "Metosin"
              :address {:street "Kesksutori 7 E 37"
                        :city   "Tampere"}})

(defn get-street-address [company]
  ; hint: see http://clojure.github.io/clojure/clojure.core-api.html#clojure.core/get-in
  )

;(fact
;  (get-street-address metosin) => "Kesksutori 7 E 37")

;;
;; Persistent data-structures:
;; ---------------------------
;;

(defn set-home-page [company home-page]
  )

;(fact
;  (:url (set-home-page metosin "http://metosin.fi")) => "http://metosin.fi")

(defn set-zip [company zip]
  ; hint: http://clojure.github.io/clojure/clojure.core-api.html#clojure.core/assoc-in
  )

;(fact
;  (get-in (set-zip metosin "33100") [:address :zip]) => "33100")
