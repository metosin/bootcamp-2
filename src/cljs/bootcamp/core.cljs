(ns bootcamp.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [reagent.core :as reagent :refer [atom]]
            [cljs.core.async :refer [<!]]
            [cljs-http.client :as http]
            [schema2 :as schema]))

; Feature ideas:
; - filter books?
; - edit books inplace
; - use schemas to check if book being edited is valid

(def books (atom []))

(go
  (let [res (<! (http/get "/books"))]
    (if (= (:status res) 200)
      (reset! books (:body res)))))

(defn one-book [book]
  [:div
   [:h2 (:name book)]
   [:p "something"]])

(defn library []
  [:div
   [:h1 "Library!"]
   (for [book @books]
     ^{:key (:_id book)} [one-book book])])

(defn main []
  (reagent/render-component
    (fn []
      [library])
    (.-body js/document)))

(main)