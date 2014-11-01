(ns bootcamp.destructuring)

;;
;; Vector destructuring:
;;

(let [data  [1 2 3]
      a     (nth data 0)
      b     (nth data 1)
      c     (nth data 2)]
  (+ a b c))
;=> 6

(let [[a b c]  [1 2 3]]
  (+ a b c))
;=> 6

;;
;; Map destructuring:
;;

(let [data    {:body {:message "hello"}}
      message (get-in data [:body :message])]
  message)
;=> "hello"

(let [{body :body}  {:body {:message "hello"}}]
  body)
;=> {:message "hello"}

(let [{{message :message} :body}  {:body {:message "hello"}}]
  message)
;=> "hello"

;
; :keys
;

(let [{status :status body :body}  {:status 404 :body "not found"}]
  [status body])
;=> [404 "not found"]

(let [{:keys [status body]}  {:status 404 :body "not found"}]
  [status body])
;=> [404 "not found"]

;
; Works with functions:
;

(defn long-book? [book]
  (> (:pages book) 200))

(defn long-book? [{pages :pages}]
  (> pages 200))
