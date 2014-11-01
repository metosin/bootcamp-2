(defproject bootcamp "0.0.0-SNAPSHOT"
  :dependencies [[clj-http "1.0.1"]
                 [clj-time "0.8.0"]
                 [com.novemberain/monger "2.0.0"]
                 [environ "1.0.0"]
                 [garden "1.2.5"]
                 [hiccup "1.0.5"]
                 [http-kit "2.1.19"]
                 [metosin/compojure-api "0.16.2"]
                 [metosin/ring-http-response "0.5.1"]
                 [metosin/ring-swagger "0.14.0"]
                 [metosin/ring-swagger-ui "2.0.17"]
                 [org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2371" :scope "provided"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [org.clojure/java.jdbc "0.3.6"]
                 [potemkin "0.3.9"]
                 [prismatic/schema "0.3.1"]
                 [ring-middleware-format "0.4.0"]
                 [ring-mock "0.1.5"]
                 [ring/ring-core "1.3.1"]

                 ; Frontend
                 [cljs-http "0.1.18"]
                 [com.andrewmcveigh/cljs-time "0.2.3"]
                 [com.facebook/react "0.11.2"]
                 [reagent "0.4.3"]
                 [secretary "1.2.1"]

                 ; For Cljs workflow: https://github.com/plexus/chestnut
                 [com.cemerick/piggieback "0.1.3"]
                 [figwheel "0.1.5-SNAPSHOT"]
                 [weasel "0.4.2"]]

  :plugins [[lein-environ "1.0.0"]]

  :source-paths ["src/clj" "target/generated/clj"]
  :test-paths ["test/clj"]

  :cljsbuild {:builds {:app {:source-paths ["src/cljs" "target/generated/cljs"]
                             :compiler {:output-to     "resources/public/js/bootcamp.js"
                                        :output-dir    "resources/public/js/out"
                                        :source-map    "resources/public/js/out.js.map"
                                        :preamble      ["react/react.min.js"]
                                        :externs       ["react/externs/react.js"]
                                        :optimizations :none
                                        :pretty-print  true}}}}

  :profiles {:dev {:repl-options {:init-ns bootcamp.server
                                  :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl
                                                     cljx.repl-middleware/wrap-cljx]}

                   :dependencies [[midje "1.6.3"]]
                   :plugins [[lein-figwheel "0.1.5-SNAPSHOT"]
                             [lein-cljsbuild "1.0.3"]
                             [lein-deps-tree "0.1.2"]
                             [lein-midje "3.1.3"]
                             [com.keminglabs/cljx "0.4.0" :exclusions [org.clojure/clojure]]
                             [lein-pdo "0.1.1"]]

                   :figwheel {:http-server-root "public"
                              :port 3449
                              :css-dirs ["resources/public/css"]}

                   :env {:is-dev true}

                   :hooks [cljx.hooks]

                   :cljx {:builds [{:rules :clj
                                    :source-paths ["src/cljx"]
                                    :output-path "target/generated/clj"}
                                   {:rules :cljs
                                    :source-paths ["src/cljx"]
                                    :output-path "target/generated/cljs"}]}

                   :pedantic? false
                   :cljsbuild {:builds {:app {:source-paths ["dev-src/cljs"]}}}}
             :uberjar {:hooks [cljx.hooks
                               leiningen.cljsbuild]
                       :env {:is-dev false}
                       :omit-source :all
                       :main bootcamp.hello
                       :aot [bootcamp.hello bootcamp.server]
                       :cljsbuild {:builds {:app {:compiler {:optimizations :advanced
                                                             :pretty-print false}}}}}}

  :uberjar-name "bootcamp.jar"
  :min-lein-version "2.3.4")
