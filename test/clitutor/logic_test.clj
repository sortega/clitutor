(ns clitutor.logic-test
  (:use clojure.test
        midje.sweet
        clitutor.logic))

(fact "minimal replacements"
      (minimal-replacement "hola1" "hola2") => ["1" "2"])

