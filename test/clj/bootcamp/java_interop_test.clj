(ns bootcamp.java-interop-test
  (:require [midje.sweet :refer :all]
            [bootcamp.java-interop :refer :all]))

(fact
  (+ 1 2) => 3)
