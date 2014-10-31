(ns bootcamp.hello
  (:gen-class))

(defn hello-world [your-name]
  (str "Hello, " your-name "!"))

(defn -main [& args]
  (println (hello-world "world")))
