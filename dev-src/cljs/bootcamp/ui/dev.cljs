(ns bootcamp.ui.dev
  (:require [bootcamp.ui.reagent :as bc]
            [figwheel.client :as figwheel :include-macros true]
            [cljs.core.async :refer [put!]]
            [weasel.repl :as weasel]))

(enable-console-print!)

(figwheel/watch-and-reload
  :websocket-url "ws://localhost:3449/figwheel-ws"
  :jsload-callback (fn [] (bc/main)))

(weasel/connect "ws://localhost:9001" :verbose true)
