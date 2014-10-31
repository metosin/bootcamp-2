(ns java-interop
  (:import [javax.swing JFrame JComponent SwingUtilities]))

;;
;; Java inter-op
;; -------------
;;
;; - Import
;; - Instantiate Java objects.
;; - Invoke methods on Java objects
;; - Clojure utils: class, instance?, doto
;; - exception handling
;; - Create objects that implement Java interfaces
;;

; Import:

(import '[java.util Random])

; rarely used like this, typically imports are done in ns declaration, see this ns declaration. 

; Instantiate Java objects:

(def rnd (new Random 42))  ; old way
(def rnd (Random. 42))     ; idiomatic

; Invoke method:

(. rnd nextInt 1337) ;=> 897  old way
(.nextInt rnd 1337)  ;=> 897  idiomatic

; class

(class rnd)          ;=> java.util.Random
(class (class rnd))  ;=> java.util.Class

; instance?

(instance? Random rnd)  ;=> true
(instance? Random 42)   ;=> false

; Static methods/fields:

(println "Java version:" (System/getProperty "java.version"))  ;=> "Java version: 1.8.0_20"
(println "AC/DC: Back in" java.awt.Color/BLACK)                ;=> AC/DC: Back in #<Color java.awt.Color[r=0,g=0,b=0]>

;;
;; Exceptions:
;;

(try
  (println "Here we go...")
  (throw (RuntimeException. "Oh no!"))
  (catch Exception e
    (println "Got exception:" (.getMessage e)))
  (finally
    (println "That's it for exceptions")))

; stdout
;   Here we go...
;   Got exception: Oh no!
;   That's it for exceptions

; doto
; - macro to help working with Java objects

; without doto:

(defn paint-large-x [g x-color width height]
  (.setColor  g  java.awt.Color/GRAY)
  (.fillRect  g  0 0 width height)
  (.setColor  g  x-color)
  (.drawLine  g  0 0 width height)
  (.drawLine  g  0 height width 0)
  g)

; with doto:

(defn paint-large-x [g x-color width height]
  (doto g
    (.setColor  java.awt.Color/GRAY)
    (.fillRect  0 0 width height)
    (.setColor  x-color)
    (.drawLine  0 0 width height)
    (.drawLine  0 height width 0)))

; proxy
; - create Java objects that extends abstract classes and implement interfaces 

(defn make-x-component [x-color]
  (proxy [JComponent] []
    (paint [g]
      (paint-large-x g x-color (.getWidth this) (.getHeight this)))))

; Put it all together:

(defn open-frame [title]
  (doto (JFrame. title)
    (.setContentPane (make-x-component java.awt.Color/BLACK))
    (.setBounds 32 32 700 900)
    (.setDefaultCloseOperation JFrame/DISPOSE_ON_CLOSE)
    (.setAlwaysOnTop true)
    (.setVisible true)))

; (def f (open-frame "Hello"))
; (.dispose f)

;;
;; Bonus: ===================
;; - update frame when paint-large-x changes
;;

(defn watch-paint-large-x [f]
  (add-watch #'paint-large-x
             :update-frame
             (fn [_ _ _ _]
               (SwingUtilities/invokeLater
                 (fn []
                   (.repaint f))))))

; (watch-paint-large-x f)

;;
;; Bonus material: type hints
;; ==========================
;;

; Clojure compiler does not know what type 'g' is in this context:

(defn clear-canvas [g width height]
  (doto g
    (.setColor java.awt.Color/GRAY)
    (.fillRect 0 0 width height)))

; Clojure compiler generates calls to 'setColor' and 'fillRect' using reflection.

; Warn when using reflection:

; remove the comment and evaluate
; (set! *warn-on-reflection* true)

(defn clear-canvas [g width height]
  (doto g
    (.setColor java.awt.Color/GRAY)
    (.fillRect 0 0 width height)))

; stderr:
;   Reflection warning, java_interop.clj:103:3 - call to method setColor can't be resolved (target class is unknown).
;   Reflection warning, java_interop.clj:103:3 - call to method fillRect can't be resolved (target class is unknown).

; Add metadata with type hint:

(defn clear-canvas [^{:tag java.awt.Graphics} g width height]
  (doto g
    (.setColor java.awt.Color/GRAY)
    (.fillRect 0 0 width height)))

; Since this is somewhat common, there is a shorter way:

(defn clear-canvas [^java.awt.Graphics g width height]
  (doto g
    (.setColor java.awt.Color/GRAY)
    (.fillRect 0 0 width height)))

;;
;; Excercise:
;;

; Create Swing application that shows system time.

; Advanced, make clock update automatically:
; Perhaps use something like this: 
;
; (defn keep-invalidating [component]
;   (future
;     (while true
;       (.invalidate component)
;       (Thread/sleep 100))))

;;
;; Things to study:
;;

;  http://clojure.org/java_interop
;  with-open
;  reify
