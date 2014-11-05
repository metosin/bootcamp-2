(ns bootcamp.db.h2
  (:require [clojure.java.jdbc :as jdbc]
            [bootcamp.util.books :as data]
            [schema.core :as sc]
            [bootcamp.books :as books])
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

(defn next-id
  ([]
    (next-id db))
  ([tx]
    (-> (jdbc/query tx ["select ids.nextval as id from dual"]) first :id str)))

(defn init-db []
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
    "alter table book_langs add primary key (book_id, lang_id)"
    "alter table book_langs add foreign key (book_id) references books (_id)"
    "alter table book_langs add foreign key (lang_id) references langs (_id)"
    (jdbc/create-table-ddl :authors
                           [:_id    "varchar(32) primary key"]
                           [:fname  "varchar(32) not null"]
                           [:lname  "varchar(32) not null"])
    (jdbc/create-table-ddl :book_authors
                           [:book_id   "varchar(32) not null"]
                           [:author_id "varchar(32) not null"])
    "alter table book_authors add primary key (book_id, author_id)"
    "alter table book_authors add foreign key (book_id) references books (_id)"
    "alter table book_authors add foreign key (author_id) references authors (_id)")
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

(defn denormalize-book
  ([book]
    (denormalize-book db book))
  ([tx {:keys [_id] :as book}]
    (assoc book
           :langs   (->> (jdbc/query tx ["select lang_id as lang from book_langs where book_id = ?" _id])
                         (map (comp keyword :lang))
                         set)
           :authors (->> (jdbc/query tx [(str "select a.fname, a.lname "
                                                "from authors a "
                                                "inner join book_authors ba "
                                                  "on a._id = ba.author_id "
                                                "where "
                                                  "ba.book_id = ?") _id])
                         vec))))

(defn get-books
  ([]
    (jdbc/with-db-transaction [tx db]
      (get-books tx)))
  ([tx]
    (->> (jdbc/query tx "select _id, title, pages, read as `read?` from books")
         (map (partial denormalize-book tx))
         (doall))))

(defn get-book
  ([book-id]
    (jdbc/with-db-transaction [tx db]
      (get-book tx book-id)))
  ([tx book-id]
    (->> (jdbc/query tx ["select _id, title, pages, read as `read?` from books where _id = ?" book-id])
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

(comment
  ; validate-all-the-data
  (doseq [book (get-books)]
    (sc/validate books/Book book)))
