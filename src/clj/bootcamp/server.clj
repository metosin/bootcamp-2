(ns bootcamp.server
  (:require [bootcamp.dev :refer [is-dev? browser-repl]]
            [bootcamp.db :as db]
            [bootcamp.ring :as ring]
            [bootcamp.books :as books]
            [clojure.java.io :as io]
            [compojure.api.sweet :refer :all]
            [compojure.route :refer [resources]]
            [environ.core :refer [env]]
            [hiccup.core :refer [html]]
            [hiccup.page :refer [html5 include-js include-css]]
            [garden.core :refer [css]]
            [garden.color :refer [rgb]]
            [org.httpkit.server :refer [run-server]]
            [ring.middleware.reload :as reload]
            [ring.util.http-response :refer [ok]]
            [schema.core :as sc])
  (:gen-class))

(defn index-page []
  (html5
    [:head
     [:title "Books - Metosin Clojure Bootcamp"]
     [:meta {:charset "utf-8"}]
     [:style (css  {:pretty-print? is-dev?}
                   [[:body {:background-color 'gray
                            :font-family "sans-serif"
                            :color 'white}]
                    [:.books
                     [:a {:color 'white}
                      [:&:hover {:color (rgb 128 16 16)}]]]])]]
    [:body
     [:div#app.app-wrapper]
     (if is-dev?
       (include-js "/react/react.js" "/js/out/goog/base.js"))
     (include-js "/js/bootcamp.js")
     (if is-dev?
       [:script {:type "text/javascript"} "goog.require('bootcamp.ui.dev');"])]))

;; The routes

(defapi api
  ; Automatic api documentation
  (swagger-ui "/docs")
  (swagger-docs
    :title "Bootcamp API"
    :description "Might have something to do with books")

  ; For frontend
  (resources "/")
  (resources "/react" {:root "react"})
  (GET* "/" req
    (ok (index-page)))

  ; Apis
  (context "/api/books" []
    (swaggered "books"
               :description "RESTful book api"
      (GET* "/" []
        :summary "Retrieve all books"
        :return [books/Book]
        (ok (db/get-books)))
      
      (GET* "/:book-id" []
        :summary "Return a book by ID"
        :path-params [book-id :- sc/Str]
        (ok (db/get-book book-id)))
    
      (POST* "/:book-id/set-read" []
        :summary "Mark book as read"
        :path-params [book-id :- sc/Str]
        :body-params [read :- sc/Bool]
        (ok (db/set-read book-id read))))))

;; Boring stuff

(def start (partial ring/start-server #'api))
(def stop ring/stop-server)

(defn -main [& _]
  (start))


