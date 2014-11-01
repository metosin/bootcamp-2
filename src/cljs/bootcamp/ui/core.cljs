(ns bootcamp.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [reagent.core :as reagent :refer [atom]]
            [cljs.core.async :refer [<!]]
            [cljs-http.client :as http]
            [bootcamp.schema2 :as schema]))

; Feature ideas:
; - filter books?
; - edit books inplace
; - use schemas to check if book being edited is valid

(def books (atom []))
(comment
  (reset! books []))

(go
  (let [res (<! (http/get "/books"))]
    (if (= (:status res) 200)
      (reset! books (:body res)))))

(defn one-book [i book]
  [:div
   [:h2 (:name book)]
   [:span (:pages book)]])

(defn library []
  [:div
   [:h1 "Library!"]
   (for [[i book] (zipmap (range) @books)]
     ^{:key (:_id book)} [one-book i book])])

(defn main []
  (reagent/render-component
    (fn []
      [library])
    (.-body js/document)))

(main)
