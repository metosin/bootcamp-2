(ns async
  (:require [clojure.core.async :refer [go chan <! <!! >! put! alt! alts! timeout close!]]))

;; FIXME: Vim won't output prints if we don't block...?

;; Basics - Go block

(go 5)

;; Take a value from channel (two !'s -> blocking)
(<!! (go 5))

(let [c (chan)]
  (go
    (<! c 1)
    (println "Taking from a channel will block until a value is available.")))

(let [c (chan)]
  (go
    (>! c 1)
    (println "Putting to channel will block (park the thread) if no one is ready to take the value.")))

;; Reading from multiple channels
(let [c1 (chan)
      c2 (chan)]
  (go
    (while true
      (let [[v ch] (alts! [c1 c2])]
        (println "Read" v "from" ch))))
  (<!! (go
         (>! c1 "foo")
         (>! c2 "bar"))))

;; Alt! macro
(let [c1 (chan)
      c2 (chan)]
  (go
    (while true
      (println (alt!
                 c1 ([v] (+ v 10))
                 c2 ([v] (* v 10))))))
  (<!! (go
         (>! c1 5)
         (>! c2 5))))

;; Timeout
(<!! (go
       (println "Foo")
       (<! (timeout 1000))
       (println "Hello")))

;; Turning synchronous stuff to async
(let [c (chan)]
  )

;; Event-loop

(defn start-loop []
  (let [ctrl (chan)
        events (chan)]
    (go
      (loop []
        (alt!
          ctrl   ([] nil)
          events ([v] (when v
                        (println v)
                        (recur)))))
      (println "Loop closed"))
    [ctrl events]))

(let [[ctrl events] (start-loop)]
  (go (put! events 1))
  (go (put! events 2))
  (go (put! events 3))
  (go (put! events 4))
  (<!! (go
         (<! (timeout 200))
         (close! ctrl))))

;; API Docs: http://clojure.github.io/core.async/
