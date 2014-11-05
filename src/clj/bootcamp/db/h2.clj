(ns bootcamp.db.h2
  (:require [clojure.string :as s]
            [clojure.java.jdbc :as jdbc]
            [bootcamp.util.books :as data]
            [schema.core :as sc]
            [bootcamp.books :as books]
            [yesql.core :refer [defqueries]])
  (:import [com.zaxxer.hikari HikariConfig HikariDataSource]))

;;;
;;; Implementing the DB using in-memory H2 database
;;;

; Connection pool

(def data-source (HikariDataSource. (doto (HikariConfig.)
                                      (.setMaximumPoolSize 10)
                                      (.setJdbcUrl "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1")
                                      (.setDriverClassName "org.h2.Driver")
                                      (.setUsername "sa")
                                      (.setPassword ""))))

(def db {:datasource data-source})

;;
;; Load queries:
;;

(defqueries "sql/queries.sql")

;;
;; Utilities:
;;

(defn next-id
  "Return next ID from DB sequence"
  ([]
    (next-id db))
  ([tx]
    (-> db query-next-id  first :id str)))

(defn foreign-key
  "Create alter table SQL statements that add foreign key constraints."
  [table & more]
  (s/join ";\n" (for [[col to-table to-col] (partition 3 more)]
                  (format "alter table %s add foreign key (%s) references %s (%s)"
                          (name table)
                          (name col)
                          (name to-table)
                          (name to-col)))))

(defn primary-key
  "Create alter table SQL statement for primary key constraint."
  [table & cols]
  (format "alter table %s add primary key (%s)"
          (name table)
          (s/join ", " (map name cols))))

(defn init-db
  "Initialize DB. First drops everything from DB, then creates tables and finally
   populates tables from bootcamp.util.books/books."
  []
  (jdbc/db-do-commands db
    "drop all objects"
    "create sequence ids"
    (jdbc/create-table-ddl :books
                           [:_id    "varchar(32) primary key"]
                           [:title  "varchar(64) not null"]
                           [:pages  "int not null"]
                           [:read   "boolean not null"])
    "create index title_ix on books (title)"
    (jdbc/create-table-ddl :langs
                           [:_id      "varchar(32) primary key"])
    (jdbc/create-table-ddl :book_langs
                           [:book_id  "varchar(32) not null"]
                           [:lang_id  "varchar(32) not null"])
    (primary-key :book_langs :book_id :lang_id)
    (foreign-key :book_langs
                 :book_id :books :_id
                 :lang_id :langs :_id)
    (jdbc/create-table-ddl :authors
                           [:_id    "varchar(32) primary key"]
                           [:fname  "varchar(32) not null"]
                           [:lname  "varchar(32) not null"])
    (jdbc/create-table-ddl :book_authors
                           [:book_id   "varchar(32) not null"]
                           [:author_id "varchar(32) not null"])
    (primary-key :book_authors :book_id :author_id)
    (foreign-key :book_authors
                 :book_id :books :_id
                 :author_id :authors :_id))
  
  ; Insert authors:
  (jdbc/with-db-transaction [tx db]
    (doseq [[_id {:keys [fname lname]}] data/authors]
      (jdbc/insert! tx :authors {:_id (name _id) :fname fname :lname lname})))
  
  ; Insert languages:
  (jdbc/with-db-transaction [tx db]
    (doseq [lang (->> data/books (mapcat :langs) set)]
      (jdbc/insert! tx :langs {:_id (name lang)})))
  
  ; Insert books:
  (jdbc/with-db-transaction [tx db]
    (doseq [{:keys [title langs pages authors] :as book} data/books]
      (let [id (next-id tx)]
        (jdbc/insert! tx :books {:_id   id
                                 :title title
                                 :pages pages
                                 :read  false})
        (doseq [lang langs]
          (jdbc/insert! tx :book_langs {:book_id id, :lang_id (name lang)}))
        (doseq [author authors]
          (jdbc/insert! tx :book_authors {:book_id id, :author_id (name author)}))))))

;;
;; Query utils:
;;

(defn denormalize-book
  "Denormalizes :langs and :authors of book"
  ([book]
    (denormalize-book db book))
  ([tx {:keys [_id] :as book}]
    (-> book
        (assoc :langs   (->> (query-book-langs tx _id) 
                             (map (comp keyword :lang))
                             set)
               :authors (->> (query-book-authors tx _id)
                             vec)
               :read? (:read book))
        (dissoc :read))))

;;
;; DB API:
;;

(defn get-books
  ([]
    (jdbc/with-db-transaction [tx db]
      (get-books tx)))
  ([tx]
    (->> (query-books tx)
         (map (partial denormalize-book tx))
         (doall))))

(defn get-book
  ([book-id]
    (jdbc/with-db-transaction [tx db]
      (get-book tx book-id)))
  ([tx book-id]
    (->> (query-book-by-id tx book-id) 
         first
         (denormalize-book tx)
         (doall))))

(defn set-read
  ([book-id read?]
    (jdbc/with-db-transaction [tx db]
      (set-read tx book-id read?)))
  ([tx book-id read?]
    (jdbc/update! tx :books {:read read?} ["_id = ?" book-id])
    (get-book tx book-id)))

;;
;; repl:
;;

(comment
  ; validate-all-the-data
  (let [books (get-books)]
    (doseq [book books]
      (sc/validate books/Book book))
    (println "Validated all" (count books) "books")))
