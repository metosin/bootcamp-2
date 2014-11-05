(ns bootcamp.db.mem
  (:require [bootcamp.util.books :as data]))

(defonce book-id (atom 0))

(defn next-id! []
  (str (swap! book-id inc)))

(defn init-db []
  (->> data/books
       (map (fn [{:keys [authors] :as book}]
              (assoc book :authors (map data/authors authors))))
       (map (fn [book]
              (assoc book :read? false)))
       (map (fn [book]
              (assoc book :_id (next-id!))))
       (map (fn [{id :_id :as book}]
              [id book]))
       (into {})))

; Exercise: Refactor init-data function for simplicity
;  - perhaps reduce the number of mappings (see assoc documentation)
;  - or, reduce everything with reduce (pun intended)

(defonce db (atom (init-data)))

(defn get-books []
  (vals @db))

(defn get-book [book-id]
  (@db book-id))

(defn set-read [book-id read?]
  (swap! db update-in [book-id] assoc :read? read?)
  (get-book book-id))
