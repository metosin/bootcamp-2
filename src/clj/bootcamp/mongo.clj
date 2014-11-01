(ns bootcamp.mongo
  (:require [monger.core :as mg]
            [monger.collection :as mc]
            [schema.core :as s]
            [bootcamp.util.books :refer :all]
            [bootcamp.schema :refer :all])
  (:import [org.bson.types ObjectId]))

; Monger API reference: http://reference.clojuremongodb.info/

; Simple implementation: one connection per project
(let [m (mg/connect-via-uri (or (System/getenv "MONGO_URI") "mongodb://127.0.0.1/bootcamp-2"))]
  (def conn (:conn m))
  (def db (:db m)))

(defn create-id []
  (str (ObjectId.)))

; We can edit an existing schema with common map operations
; A mongo document has an id
; and we want to save the authors in denormalized format
(s/defschema MongoBook (assoc Book
                              :_id s/Str
                              :authors [Author]))

;; EXCERCISES: Implement the following functions
; Use this to retrieve authors before saving the book to mongo
(s/defn ^:always-validate get-author :- Author
  [key]
  (get authors key))

(s/defn ^:always-validate insert-book :- MongoBook
  "Insert a book into db.

   Takes a normal book in, which has authors
   as vector of keywords. Before book is saved to
   db, authors vector should be denormalized,
   that is, for each keyword a author should be
   fetched using get-author function."
  [book :- Book]
  nil)

(s/defn ^:always-validate get-book
  ; Extra: Mongo looses some types (sets, keywords)
  ; remove following comment and use coercion to coerce data back to proper types
  ; :- MongoBook
  "Retrieve a book from db"
  [id :- s/Str]
  nil)

(defn get-books
  "Get vector of all books."
  []
  ;(mc/find-maps db :books)
  books)

(defn update-book
  "Update books name and pages. Returns the updated book."
  [id book]
  nil)

(comment
  (get-author :fogus)
  (insert-book {})
  (def ^:private _id (:_id (insert-book (second books))))
  _id
  (get-book _id)
  (get-books)

  (mc/drop db :books))
