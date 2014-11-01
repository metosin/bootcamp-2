(ns bootcamp.server
  (:require [bootcamp.dev :refer [is-dev? browser-repl]]
            [bootcamp.mongo :as mongo]
            [bootcamp.ring :as ring]
            [bootcamp.schema :as schema]
            [clojure.java.io :as io]
            [compojure.api.sweet :refer :all]
            [compojure.route :refer [resources]]
            [environ.core :refer [env]]
            [hiccup.core :refer [html]]
            [hiccup.page :refer [html5 include-js include-css]]
            [org.httpkit.server :refer [run-server]]
            [ring.middleware.reload :as reload]
            [ring.util.http-response :refer [ok]]
            [schema.core :as s])
  (:gen-class))

(declare index-page)

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
  (swaggered "books"
    :description "RESTful book api"
    (GET* "/books" []
      :summary "Retrieve all books"
      (ok (mongo/get-books)))

    (POST* "/books" []
      :summary "Create a new book"
      :body [book schema/Book]
      (ok (mongo/insert-book book)))

    (GET* "/books/:id" []
      :summary "Return a book"
      :path-params [id :- s/Str]
      (ok (mongo/get-book id)))

    ; Implement book updating
    ; - Use mongo/update-book
    ; - Create a new schema as update-book only allows changing
    ;   name or pages
    ))

;; Boring stuff

(def http-handler
  (if is-dev?
    (reload/wrap-reload #'bootcamp.server/api)
    api))

(def start (partial ring/start-server http-handler))
(def stop ring/stop-server)

(defn -main [& [port]]
  (start))

(defn index-page []
  (html
    (html5
      [:head
       [:title "Hello World"]
       [:meta {:charset "utf-8"}]
       (include-css "css/style.css")]
      [:body
       [:div#app.app-wrapper]
       (if is-dev? (include-js "/react/react.js" "/js/out/goog/base.js"))
       (include-js "/js/bootcamp.js")
       (if is-dev? [:script {:type "text/javascript"} "goog.require('bootcamp.ui.dev');"])])))
