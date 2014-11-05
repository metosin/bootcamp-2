(ns bootcamp.async-test
  (:require [midje.sweet :refer :all]
            [clojure.core.async :refer [go chan <! >! <!! >!! timeout close!] :as async]
            [clj-http.client :as http]
            [net.cgrand.enlive-html :as html]
            [cheshire.core :as json]))

;;;
;;; Advanced bonus exercise:
;;;

;
; Let's assume you have a functions that asynchronously fetch some data from internet
; and once ready, calls provided callback function with the data.
;

(defn fetch-some-data [callback]
  (future
    (Thread/sleep 100) ; simulate network
    (callback :data-from-internets)))

;
; Create a function that accepts a function that takes a callback function (cbf) as
; an argument (like the fetch-hn-news and fetch-reddit) and returns a channel where
; the results will be written.
;

(defn cbf->chan [cbf]
  ; Implement this
  )

;
; Test to check chan-lift:
;

#_
(fact cbf->chan
  (let [cf (cbf->chan fetch-some-data)
        c  (cf)]
    (<!! c) => :data-from-internets))
