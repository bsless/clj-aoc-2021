(ns bsless.clj-aoc-2021.day2
  (:require [bsless.clj-aoc-2021.utils :as u]))

(defprotocol ISub
  (-up [sub n])
  (-down [sub n])
  (-forward [sub n])
  (-status [this]))

(defrecord Sub [^long depth ^long horizontal]
  ISub
  (-up [_ n] (Sub. (- depth n) horizontal))
  (-down [_ n] (Sub. (+ depth n) horizontal))
  (-forward [_ n] (Sub. depth (+ n horizontal)))
  (-status [_] (* depth horizontal)))

(def sample
  [["forward" 5]
   ["down" 5]
   ["forward" 8]
   ["up" 3]
   ["down" 8]
   ["forward" 2]])

(defn move
  [this ^String direction ^long n]
  (cond
    (.equals "forward" ^String direction) (-forward this n)
    (.equals "down" ^String direction) (-down this n)
    (.equals "up" ^String direction) (-up this n)))

(reduce (fn [sub [d n]] (move sub d (u/parse-long n))) (Sub. 0 0) sample)

(defn drive
  [sub commands]
  (reduce (fn [sub [d n]] (move sub d (u/parse-long n))) sub commands))

(defn result
  [{:keys [depth horizontal]}]
  (* depth horizontal))

(->> 2
     u/day-lines
     (->Eduction
      (map (fn [^String line] (.split line " "))))
     (drive (Sub. 0 0))
     -status);; => 1690020

(defrecord Sub2 [^long depth ^long horizontal ^long aim]
  ISub
  (-up [_ n] (Sub2. depth horizontal (- aim n)))
  (-down [_ n] (Sub2. depth horizontal (+ aim n)))
  (-forward [_ n] (Sub2. (+ (* aim n) depth) (+ n horizontal) aim))
  (-status [_] (* depth horizontal)))

(->> 2
     u/day-lines
     (->Eduction
      (map (fn [^String line] (.split line " "))))
     (drive (Sub2. 0 0 0))
     -status);; => 1408487760
