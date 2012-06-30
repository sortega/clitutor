(ns clitutor.logic
  (:refer-clojure :exclude [inc reify ==])
  (:use [clojure.core.logic]))

(defn not-emptyo
  "Non empty sequence"
  [x]
  (fresh [a b]
         (conso a b x)))

(defn replaceo
  "dst is src after replacing all appearances of old by new"
  [old new src dst]
  (all
    (not-emptyo old)
    (condu
      [(== src dst) succeed]
      [(fresh [src-after dst-after before a b]
              (appendo  old    src-after a)
              (appendo  before a         src)
              (appendo  before new       b)
              (appendo  b      dst-after dst)
              (replaceo old new src-after dst-after))])))

(defn minimal-replacement
  "Returns a vector [old, new] with the minimal replace operation to get
   dst from src"
  [src dst]
  (let [replacements
        (run* [q]
              (fresh [old new]
                     (replaceo old new (seq src) (seq dst))
                     (== [old new] q)))]
    (->> replacements
      (apply min-key (fn [old new] (+ (count old) (count new))))
      (map #(apply str %)))))
