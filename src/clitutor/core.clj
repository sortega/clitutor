(ns clitutor.core
  (:use [lumiere])
  (:use [clitutor.logic :only [minimal-replacement]])
  (:require [clojure.string :as s])
  (:import [org.apache.commons.io.input Tailer TailerListener])
  (:import [java.util.regex Pattern])
  (:import [java.io File]))

(defn mk-tailer
  "Watch a file and notify on every new line by calling line-handler.
   Call .stop on the returned value to stop tailing the file."
  [path line-handler]
  (Tailer. (File. path)
           (reify TailerListener
             (init [_ _])
             (^void handle [_ ^Exception line])
             (^void handle [_ ^String line]
               (line-handler line)))
           100 true))

(defn report-rules [rules history]
  (doseq [hint (sort-by count (mapcat #(% history) rules))]
    (println (str (green "Hint: ") hint))))

(defn history-appender [path rules]
  (let [history (agent ())]
    (add-watch history :rules (fn [_ _ _ history]
                                (report-rules rules history)))
    (mk-tailer path (fn [line]
                      (send history conj line)))))

(defn alias-rule [command shortcut]
  (fn [history]
    (when (= command (.trim (first history))) [shortcut])))

(defn embed-last-rule [history]
  (when (> (count history) 1)
    (let [[current prev] history
          prev-pattern   (Pattern/compile (Pattern/quote prev))
          embedded       (s/replace current prev-pattern "!!")]
      (when (< (count embedded) (count current))
      [embedded]))))

(defn replace-string-rule [history]
  (when (> (count history) 1) ; FIXME: duplication on current-prev pattern
    (let [[current prev] history
          [old new]      (minimal-replacement prev current)
          keystrokes     (+ 2 (count old) (count new))]
        (when (< keystrokes (count current))
          [(str "^" old "^" new)]))))

(defn -main
  "Give hints on a history file"
  [file]
  (println "Listening to the history...")
  (.run (history-appender file [(alias-rule "exit" "Ctrl+D")
                                (alias-rule "clear" "Ctrl-L")
                                embed-last-rule
                                replace-string-rule])))
