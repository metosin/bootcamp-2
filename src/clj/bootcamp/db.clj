(ns bootcamp.db
  (:require [schema.core :as sc]
            [bootcamp.books :as books]))

(require '[bootcamp.db.mem :as db])

(sc/defn ^:always-validate get-books :- [books/Book]
  []
  (db/get-books))

(sc/defn ^:always-validate get-book :- (sc/maybe books/Book)
  [book-id :- sc/Int]
  (db/get-book book-id))

(sc/defn ^:always-validate set-read :- (sc/maybe books/Book)
  [book-id  :- sc/Int
   read?    :- sc/Bool]
  (db/set-read book-id read?))
