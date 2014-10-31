(ns bootcamp.ring-test
  (:require [midje.sweet :refer :all]
            [ring :refer :all]))

(fact
  (let [handler (fn [req] {:status 200})
        app     (frame-options handler)
        resp    (app {})]
    resp => {:status 200
             :headers {"x-frame-options" "SAMEORIGIN"}}))

(fact
  (let [app (frame-options (constantly {:status 200}))]
    (app {}) => {:status 200
                 :headers {"x-frame-options" "SAMEORIGIN"}}))
