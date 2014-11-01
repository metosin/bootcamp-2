(ns bootcamp.ring
  (:require [org.httpkit.server :as http-kit]
            [ring.util.http-response :refer [ok content-type bad-request!] :as resp]
            [compojure.core :refer [context]]
            [compojure.route :as route]
            [compojure.api.core :refer [defroutes* middlewares GET* POST*]]
            [compojure.api.middleware :refer [api-middleware]]
            [clojure.pprint :refer [pprint]]
            [ring.middleware.params :as params]
            [cheshire.core :as json]))

;;
;; Server life-cycle:
;;

(defonce server (atom nil))

(defn stop-server []
  (if-let [s (deref server)]
    (s))
  (reset! server nil))

(defn start-server [routes]
  (stop-server)
  (reset! server (http-kit/run-server routes {:port 8080})))

;;
;; Ring routing:
;;

(defn log-requests [handler]
  (fn [request]
    (pprint request)
    (handler request)))

(defn frame-options [handler]
  (fn [request]
    (assoc-in (handler request) [:headers "x-frame-options"] "SAMEORIGIN")))

(defn handle-request [request]
  {:status 200
   :body   "Hello, world!"})

(def app (frame-options (log-requests handle-request)))

(comment
  (start-server app))
