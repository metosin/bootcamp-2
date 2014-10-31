(ns bootcamp.dev (:require [environ.core :refer [env]]
                  [cemerick.piggieback :as piggieback]
                  [weasel.repl.websocket :as weasel]
                  [leiningen.core.main :as lein]))

(def is-dev? (not= (env :is-dev) "false"))

(defn browser-repl []
  (piggieback/cljs-repl :repl-env (weasel/repl-env :ip "0.0.0.0" :port 9001)))

(defn start-figwheel []
  (future
    (print "Starting figwheel.\n")
    (lein/-main ["figwheel"])))
