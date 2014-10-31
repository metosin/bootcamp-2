(ns compojure
  (:require [ring.util.http-response :refer [ok not-found]]
            [schema.core :as sc]
            [compojure.api.sweet :refer :all]
            [ring :refer [start-server]]))

(defonce storage (atom {}))

(defn set-message! [user message]
  (swap! storage assoc user message))

(defn get-message [user]
  (get @storage user))

(defapi app
  (context "/message" []
    (GET* "/:user" []
      :path-params [user :- sc/Keyword]
      (if-let [message (get-message user)]
        (ok {:message message})
        (not-found {})))
    (POST* "/:user" []
      :path-params [user :- sc/Keyword]
      :body-params [message :- String]
      (set-message! user message)
      (ok {:user user :message message}))))

(start-server #'app)
