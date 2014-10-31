(ns bootcamp.mongo
  (:require [monger.core :as mg]
            [monger.collection :as mc]
            [schema.core :as s]
            [util.books :refer :all]
            [schema :refer :all])
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
  [book :- Book]
  nil)

(s/defn ^:always-validate get-book
  ; Extra: Mongo looses some types (sets, keywords)
  ; remove following comment and use coercion to coerce data back to proper types
  ; :- MongoBook
  [id :- s/Str]
  nil)

(defn get-books
  []
  ;(mc/find-maps db :books)
  books)

(comment
  (get-author :fogus)
  (insert-book {})
  (def ^:private _id (:_id (insert-book (second books))))
  _id
  (get-book _id)

  (mc/drop db :books))