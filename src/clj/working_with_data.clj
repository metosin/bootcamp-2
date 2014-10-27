(ns working-with-data
  (:require [data-structures :refer [some-primes planets]]))

;;
;; filter, map, reduce
;;


;; filter:
;; -------

(odd? 0)                   ;=> false
(odd? 1)                   ;=> true

some-primes                ;=> [2 3 5 7 11 13 17 19]
(filter odd? some-primes)  ;=> (3 5 7 11 13 17 19)

(defn large? [v]
  (if (< 10 v)
    true
    false))

(filter large? some-primes)  ;=> (11 13 17 19)

(filter (fn [v]
          (if (< 10 v)
            true
            false))
        some-primes)         ;=> (11 13 17 19)

(filter (fn [v]
          (< 10 v))
        some-primes)         ;=> (11 13 17 19)

(filter #(< 10 %)
        some-primes)         ;=> (11 13 17 19)

;; map:
;; ----

(inc 0)                        ;=> 1
(inc 41)                       ;=> 42

(map inc some-primes)          ;=> (3 4 6 8 12 14 18 20)

some-primes                    ;=> [2 3 5 7 11 13 17 19]
(map * [0 1 2 3] some-primes)  ;=> (0 3 10 21)

;; reduce:
;; -------

(defn sum [values]
  (reduce
    (fn [acc value]
      (+ acc value))
    0
    values))

(sum some-primes)              ;=> 77
