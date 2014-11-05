(ns bootcamp.books
  (:require [schema.core :as sc :include-macros true]
            [schema.coerce :as scoerce]))

(sc/defschema Author {:fname  sc/Str
                      :lname  sc/Str})

(sc/defschema Book {:_id      sc/Str
                    :title    sc/Str
                    :langs    #{sc/Keyword}
                    :pages    sc/Int
                    :authors  [Author]
                    (sc/optional-key :read?) sc/Bool})

(def ->book (scoerce/coercer Book scoerce/json-coercion-matcher))
