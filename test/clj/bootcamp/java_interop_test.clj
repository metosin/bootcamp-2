(ns bootcamp.java-interop-test
  (:require [midje.sweet :refer :all]
            [java-interop :refer :all]))

(fact
  (+ 1 2) => 3)
