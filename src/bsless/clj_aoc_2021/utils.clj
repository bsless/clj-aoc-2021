(ns bsless.clj-aoc-2021.utils
  (:require [clojure.java.io :as io])
  (:import (java.io BufferedReader)))

(defn day [n] (->> (str "day" n) io/resource))

(defn parse-long
  [s]
  (if (string? s) (Long/valueOf s) s))

(defn line-reducible
  [^BufferedReader rdr]
  (reify clojure.lang.IReduceInit
    (reduce [_this f start]
      (loop [acc start
             line (.readLine rdr)]
        (if line
          (let [res (f acc line)]
            (if (reduced? res)
              (do (.close rdr) @res)
              (recur res (.readLine rdr))))
          (do (.close rdr) acc))))))

(defn day-lines [n] (-> n day io/reader line-reducible))

(defn sliding
  ([^long n]
   (sliding n 1))
  ([^long n ^long step]
   (fn [rf]
     (let [a (java.util.ArrayDeque. n)]
       (fn
         ([] (rf))
         ([result] (rf result))
         ([result input]
          (.add a input)
          (if (= n (.size a))
            (let [v (vec (.toArray a))]
              (dotimes [_ step] (.removeFirst a))
              (rf result v))
            result)))))))
