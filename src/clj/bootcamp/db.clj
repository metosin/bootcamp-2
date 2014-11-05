(ns bootcamp.db
  (:require [schema.core :as sc]
            [bootcamp.books :as books]))

(require '[bootcamp.db.h2 :as db])

(defn init-db []
  (db/init-db))

(sc/defn ^:always-validate get-books :- [books/Book]
  []
  (db/get-books))

(sc/defn ^:always-validate get-book :- (sc/maybe books/Book)
  [book-id :- sc/Str]
  (db/get-book book-id))

(sc/defn ^:always-validate set-read :- (sc/maybe books/Book)
  [book-id  :- sc/Str
   read?    :- sc/Bool]
  (db/set-read book-id read?))
