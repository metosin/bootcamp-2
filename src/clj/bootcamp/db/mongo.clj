(ns bootcamp.db.mongo
  (:require [monger.core :as monger]
            [monger.collection :as mc]
            [monger.operators :refer :all]
            [schema.core :as sc]
            [bootcamp.util.books :as data]
            [bootcamp.books :as books])
  (:import [org.bson.types ObjectId]))

; Monger API reference: http://reference.clojuremongodb.info/

; Simple implementation: one connection per project
(let [m (monger/connect-via-uri (or (System/getenv "MONGO_URI") (System/getProperty "mongo") "mongodb://127.0.0.1/bootcamp-2"))]
  (def conn (:conn m))
  (def db   (:db m)))

(defn init-db []
  (mc/drop db :books)
  (mc/ensure-index db :books {:title 1})
  (doseq [book data/books]
    (let [db-book (assoc book
                         :_id     (str (ObjectId.))
                         :authors (map data/authors (:authors book)))]
      (sc/validate books/Book db-book)
      (mc/insert db :books db-book))))

;; EXCERCISES: Implement the following functions

(sc/defn ^:always-validate get-books :- [books/Book]
  []
  (map books/->book (mc/find-maps db :books)))

(sc/defn ^:always-validate get-book :- (sc/maybe books/Book)
  [book-id :- sc/Str]
  (some-> (mc/find-one-as-map db :books {:_id book-id})
          books/->book))

(sc/defn ^:always-validate set-read :- (sc/maybe books/Book)
  [book-id :- sc/Str
   read?   :- sc/Bool]
  (mc/update db :books {:_id book-id} {$set {:read? read?}})
  (get-book book-id))

