(defproject clitutor "0.1.0-SNAPSHOT"
  :description "Small script for learning shell golf"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :main clitutor.core
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [midje "1.4.0-beta1"]
                 [com.stuartsierra/lazytest "1.2.3"]
                 [commons-io/commons-io "2.3"]
                 [lumiere "1.0.0-SNAPSHOT"]]
  :dev-dependencies [[lein-midje "1.0.9"]]
  :repositories {"stuart" "http://stuartsierra.com/maven2"})
