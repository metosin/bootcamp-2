(ns bootcamp.mongo-test
  (:require [midje.sweet :refer :all]
            [bootcamp.mongo :refer :all]
            [bootcamp.util.books :refer :all]))

(def valid-book {:name    "The Joy of Clojure"
                 :langs   #{:clojure}
                 :pages   328
                 :authors [{:fname "Michael"    :lname "Fogus"}
                           {:fname "Chris"      :lname "Houser"}]})

; db refers to db connection from mongo-ns
#_
(facts books
  (let [{:keys [_id] :as book}
        (insert-book (first books))]
    (dissoc book :_id) => valid-book

    (get-book _id) => book))