(defproject bootcamp "0.0.0-SNAPSHOT"
  :dependencies [[cljs-ajax "0.3.3"]
                 [clj-time "0.8.0"]
                 [com.fasterxml.jackson.core/jackson-core "2.3.2"]
                 [commons-codec "1.8"]
                 [compojure "1.2.1"]
                 [garden "1.2.5"]
                 [hiccup "1.0.5"]
                 [http-kit "2.1.19"]
                 [metosin/compojure-api "0.16.2"]
                 [metosin/ring-http-response "0.5.1"]
                 [org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2371"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [org.clojure/tools.reader "0.8.10"]
                 [potemkin "0.3.11"]
                 [prismatic/schema "0.3.1"]
                 [ring-middleware-format "0.4.0"]
                 [ring/ring-core "1.3.1"]

                 ; For clj-webdriver
                 [org.seleniumhq.selenium/selenium-server "2.42.2"]
                 [org.seleniumhq.selenium/selenium-java "2.42.2"]
                 [org.seleniumhq.selenium/selenium-remote-driver "2.42.0"]
                 ]
  :source-paths ["target/generated/clj" "src/clj"]
  :test-paths ["test/clj"]
  :profiles {:dev {:dependencies [[midje "1.6.3"]
                                  [clj-webdriver "0.6.1" :exclusions [org.clojure/core.cache]]]
                   :plugins [[com.keminglabs/cljx "0.4.0"]
                             [lein-cljsbuild "1.0.3"]
                             [lein-deps-tree "0.1.2"]
                             [lein-midje "3.1.3"]]
                   :cljx {:builds [{:rules :clj
                                    :source-paths ["src/cljx"]
                                    :output-path "target/generated/clj"}
                                   {:rules :cljs
                                    :source-paths ["src/cljx"]
                                    :output-path "target/generated/cljs"}]}
                   :pedantic? false}
             :uberjar {:main hello
                       :aot [hello]}}
  :cljsbuild {:builds {:dev {:source-paths ["target/generated/cljs" "src/cljs"]
                             :compiler {:output-to "resources/bootcamp.js"
                                        :output-dir "target/js/out-dev"
                                        :optimizations :none}}
                       :dist {:source-paths ["target/generated/cljs" "src/cljs"]
                              :compiler {:output-to "resources/bootcamp.js"
                                         :output-dir "target/js/out-prod"
                                         :optimizations :advanced}}}}
  :uberjar-name "bootcamp.jar"
  :min-lein-version "2.3.4"
  :aliases {"cljs"      ["do" ["cljsbuild" "clean"] ["cljsbuild" "auto" "dev"]]
            "dist"      ["do" ["cljx" "once"] ["cljsbuild" "clean"] ["cljsbuild" "once" "prod"] ["uberjar"]]})
