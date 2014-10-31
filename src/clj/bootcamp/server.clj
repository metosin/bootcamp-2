(ns bootcamp.server
  (:require [clojure.java.io :as io]
            [bootcamp.dev :refer [is-dev? browser-repl start-figwheel]]
            [compojure.api.sweet :refer :all]
            [compojure.route :refer [resources]]
            [ring.middleware.reload :as reload]
            [ring.util.http-response :refer [ok]]
            [environ.core :refer [env]]
            [org.httpkit.server :refer [run-server]]
            [hiccup.core :refer [html]]
            [hiccup.page :refer [html5 include-js include-css]]

            bootcamp.schema
            bootcamp.mongo
            bootcamp.server)
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
      (ok (bootcamp.mongo/get-books)))

    (POST* "/books" []
      :summary "Create a new book"
      :body [book bootcamp.schema/Book]
      (ok (bootcamp.mongo/insert-book {})))

    ; Perhaps something else should be implemented also?
    ))

;; Boring stuff

(def http-handler
  (if is-dev?
    (reload/wrap-reload #'bootcamp.server/api)
    api))

(defn run [& [port]]
  (defonce ^:private server
    (do
      (if is-dev? (start-figwheel))
      (let [port (Integer. (or port (env :port) 3000))]
        (println (str "Starting web server on port " port))
        (run-server http-handler {:port port
                                  :join? false}))))
  server)

(defn -main [& [port]]
  (run port))

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
       (if is-dev? [:script {:type "text/javascript"} "goog.require('bootcamp.dev');"])])))
