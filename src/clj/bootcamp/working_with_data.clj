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

(filter (partial < 10)
        some-primes)         ;=> (11 13 17 19)

;; map:
;; ----

(inc 0)                        ;=> 1
(inc 41)                       ;=> 42

some-primes                    ;=> [2 3 5 7 11 13 17 19]
(map inc some-primes)          ;=> (3 4 6 8 12 14 18 20)
(map * some-primes [0 1 2 3])  ;=> (0 3 10 21)

;; reduce:
;; -------

(defn sum [values]
  (reduce
    (fn [acc value]
      (+ acc value))
    0
    values))

(sum some-primes)              ;=> 77

(reduce + some-primes)         ;=> 77
(apply + some-primes)          ;=> 77

; Performance shoot-out reduce vs. apply

(def lots-of-numbers (range 1e6))

(count lots-of-numbers)
;=> 1000000

(time
  (reduce + lots-of-numbers))
;=> 499999500000
; "Elapsed time: 15.210181 msecs"

(time
  (apply + lots-of-numbers))
;=> 499999500000
; "Elapsed time: 16.120721 msecs"
