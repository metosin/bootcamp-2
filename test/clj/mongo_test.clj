(ns mongo-test
  (:require [mongo :refer :all]
            [util.books :refer :all]
            [midje.sweet :refer :all]))

(def valid-book {:name    "The Joy of Clojure"
                 :langs   #{:clojure}
                 :pages   328
                 :authors [{:fname "Michael"    :lname "Fogus"}
                           {:fname "Chris"      :lname "Houser"}]})

; db refers to db connection from mongo-ns
#_
(facts books
  (let [{:keys [_id] :as book}
        (insert-book db (first books))]
    (dissoc book :_id) => valid-book

    (get-book db _id) => book))