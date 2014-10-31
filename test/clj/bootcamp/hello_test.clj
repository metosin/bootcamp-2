(ns bootcamp.hello-test
  (:require [midje.sweet :refer :all]
            [bootcamp.hello :as h]))

(fact
  (h/hello-world "foo") => "Hello, foo!"
  (h/hello-world "bar") => "Hello, bar!")


