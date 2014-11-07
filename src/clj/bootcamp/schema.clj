(ns bootcamp.schema
  (:require [schema.core :as sc]
            [schema.coerce :as scoerce]))

;; Schemas - https://github.com/Prismatic/schema

; a schema can be

; a Class
(sc/check Long 42)
(sc/check java.util.Date "bar")

; Validate throws
(sc/validate Long 5)

; a collection or map containing Schemas
(sc/check [String] ["a" "5" "b"])

(sc/check {:a Long
           :b String
           :c (sc/maybe String)}
          {:a 42
           :b "foo"})

; schema.core ns has loads of helpers / aliases
(sc/check sc/Num 5)

; Those are especially important if developing for Clj+Cljs!
; (def Inst
;   "The local representation of #inst ..."
;   #+clj java.util.Date #+cljs js/Date

; predicates
(sc/check
  (sc/pred (fn [v] (= v "foobar")) 'not-foobar)
  "barfoo")

; Something which implements Schema-protocol.
; Schema.core provides multiple utilities:
(sc/check (sc/eq "foobar") "barfoo")
(sc/check (sc/maybe sc/Int) nil)
(sc/check (sc/either sc/Str sc/Int) "foo")
(sc/check (sc/both sc/Int (sc/pred even? 'not-even)) 5)
(sc/check (sc/enum :foo :bar) :bar)

;; EXCERCISES:

; Fix these
(sc/defschema Author {:fname sc/Str})

(sc/defschema Book {:title   sc/Str
                    :langs   #{String}
                    :pages   sc/Int
                    :authors sc/Keyword})

(require '[bootcamp.util.books :as books])

(sc/check Author (:fogus books/authors))
(sc/check Book (first books/books))

; More, coercions:
(defn str->keyword-matcher [schema]
  (if (= schema sc/Keyword)
    (fn [v]
      ; Fix this
      (if (string? v) (keyword v) v))))

(defn vec->set-matcher [schema]
  ; Implement this
  )

(defn book-coercien-matcher [schema]
  (or
    (str->keyword-matcher schema)
    (vec->set-matcher schema)))

(def coerce-book (scoerce/coercer Book book-coercien-matcher))

(coerce-book {:title "foo"
              :langs ["foo"]
              :pages 42
              :authors ["bar" "baz"]})

;; NOTE: e.g. schema.coerce has json-coercion-matcher which offers
;; existing implementation for e.g. keyword and sets matchers
