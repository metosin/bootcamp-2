(defproject bootcamp "0.0.0-SNAPSHOT"
  :description "Metosin Bootcamp"
  :url "https://github.com/metosin/bootcamp-2"
  :license {:name "Copyrights Metosin Oy 2014, All rights reserved"
            :url "http://metosin.fi/"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [prismatic/schema "0.3.1"]
                 [http-kit "2.1.19"]
                 [ring/ring-core "1.3.1"]
                 [compojure "1.2.1"]
                 [hiccup "1.0.5"]
                 [ring-middleware-format "0.4.0"]
                 [garden "1.2.5"]
                 [metosin/compojure-api "0.16.2"]
                 [metosin/ring-http-response "0.5.1"]
                 [org.clojure/clojurescript "0.0-2371"]
                 [cljs-ajax "0.3.3"]
                 [org.clojure/tools.reader "0.8.9"]
                 [clj-time "0.8.0"]
                 [commons-codec "1.8"]
                 [com.fasterxml.jackson.core/jackson-core "2.3.2"]
                 [potemkin "0.3.9"]]
  :source-paths ["src/clj"]
  :test-paths ["test/clj"]
  :profiles {:dev {:source-paths ["generated/clj" "src/clj"]
                   :dependencies [[midje "1.6.3"]
                                  [clj-webdriver "0.6.1" :exclusions [org.clojure/core.cache]]]
                   :plugins [[lein-midje "3.1.3"]
                             [lein-cljsbuild "1.0.3"]
                             [com.keminglabs/cljx "0.4.0"]
                             [lein2-eclipse "2.0.0"]                             
                             [lein-deps-tree "0.1.2"]]
                   :cljx {:builds [{:rules :clj
                                    :source-paths ["src/cljx"]
                                    :output-path "generated/clj"}
                                   {:rules :cljs
                                    :source-paths ["src/cljx"]
                                    :output-path "generated/cljs"}]}
                   :pedantic? false}
             :uberjar {:source-paths ["generated/clj" "src/clj"]
                       :main hello
                       :aot [hello]}}
  :cljsbuild {:builds {:dev {:source-paths ["generated/cljs" "src/cljs"]
                             :compiler {:output-to "resources/bootcamp.js"
                                        :output-dir "target/js/out-dev"
                                        :optimizations :none}}
                       :dist {:source-paths ["generated/cljs" "src/cljs"]
                              :compiler {:output-to "resources/bootcamp.js"
                                         :output-dir "target/js/out-prod"
                                         :optimizations :advanced}}}}
  :uberjar-name "bootcamp.jar"
  :min-lein-version "2.3.4"
  :aliases {"cljs"      ["do" ["cljsbuild" "clean"] ["cljsbuild" "auto" "dev"]]
            "dist"      ["do" ["cljx" "once"] ["cljsbuild" "clean"] ["cljsbuild" "once" "prod"] ["uberjar"]]})
