(ns bootcamp.ui.reagent
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

(go
  (let [{:keys [status body]} (<! (http/get "/api/books"))]
    (if (= status 200)
      (reset! books body))))

(defn one-book [{:keys [_id title]}]
  [:div.book
   [:a {:href (str "#/book/" _id)} title]])

(defn library []
  [:div
   [:h1 "Library"]
   [:div.books
    (for [{:keys [_id] :as book} @books]
      ^{:key _id} [one-book book])]])

(defn main []
  (reagent/render-component
    (fn []
      [library])
    (.-body js/document)))

(-> js/window .-onload (set! main))
