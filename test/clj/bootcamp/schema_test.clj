(ns bootcamp.schema-test
  (:require [midje.sweet :refer :all]
            [schema.core :as s]
            [bootcamp.schema :refer :all]
            [bootcamp.util.books :refer :all]))

#_
(facts Author
  (s/check Author (:fogus authors)) => nil)

#_
(facts Book
  (s/check Book (first books)) => nil)

; Moar
#_
(facts coerce-book
  (coerce-book {:name    "The Joy of Clojure"
                :langs   ["clojure"]
                :pages   328
                :authors ["fogus" "houser"]})
  => (first books))