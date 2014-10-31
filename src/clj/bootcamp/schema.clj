(ns schema
  (:require [schema.core :as s]
            [schema.coerce :as sc]))

;; Schemas - https://github.com/Prismatic/schema

; a schema can be

; a Integer
(s/check Long "foo")
; Validate throws
(s/validate Long 5)

; a Class
(s/check java.util.Date "bar")

; a collection or map containing Schemas
(s/check [String] ["a" 5 "b"])
(s/check {:a Long
          :b String}
         {:a :error
          :b "foo"})

; schema.core ns has loads of helpers / aliases
(s/check s/Int 5)

; Those are especially important if developing for Clj+Cljs!
; (def Inst
;   "The local representation of #inst ..."
;   #+clj java.util.Date #+cljs js/Date)


; predicates
(s/check (s/pred (fn [v] (= v "foobar")) 'not-foobar) "barfoo")

; Something which implements Schema-protocol.
; Schema.core provides multiple utilities:
(s/check (s/eq "foobar") "barfoo")
(s/check (s/maybe s/Int) nil)
(s/check (s/either s/Str s/Int) "foo")
(s/check (s/both s/Int (s/pred even? 'not-even)) 5)
(s/check (s/enum :foo :bar) :asd)

;; EXCERCISES:

; Fix these
(s/defschema Author {:fname s/Str})

(s/defschema Book {:name s/Str
                   :langs s/Str
                   :pages s/Str})

; More, coercions:
(defn str->keyword-matcher[schema]
  (if (= schema s/Keyword)
    (fn [v]
      (if (string? v) v v))))

(defn book-coercien-matcher [schema]
  (or
    (str->keyword-matcher schema)
    ; [:foo] -> #{:foo}
    ))

(defn coerce-book [json-book]
  ((sc/coercer Book book-coercien-matcher) json-book))