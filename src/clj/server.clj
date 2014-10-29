(ns server
  (:require [clojure.java.io :as io]
            [dev :refer [is-dev? browser-repl start-figwheel]]
            [compojure.core :refer [GET defroutes]]
            [compojure.route :refer [resources]]
            [compojure.handler :refer [site]]
            [ring.middleware.reload :as reload]
            [ring.util.http-response :refer [ok]]
            [environ.core :refer [env]]
            [org.httpkit.server :refer [run-server]]
            [hiccup.core :refer [html]]
            [hiccup.page :refer [html5 include-js include-css]])
  (:gen-class))

(println "dev" is-dev?)

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
       (if is-dev? [:script {:type "text/javascript"} "goog.require('bootcamp_ui.dev');"])])))

(defroutes routes
  (resources "/")
  (resources "/react" {:root "react"})
  (GET "/*" req
    (ok (index-page))))

(def http-handler
  (if is-dev?
    (reload/wrap-reload (site #'routes))
    (site routes)))

(defn run [& [port]]
  (defonce ^:private server
    (do
      (if is-dev? (start-figwheel))
      (let [port (Integer. (or port (env :port) 10555))]
        (println (str "Starting web server on port " port))
        (run-server http-handler {:port port
                                  :join? false}))))
  server)

(defn -main [& [port]]
  (run port))
