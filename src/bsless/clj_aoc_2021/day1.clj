(ns bsless.clj-aoc-2021.day1
  (:require
   [bsless.clj-aoc-2021.utils :as u]))

(def increase-xf
  (comp
   (map u/parse-long)
   (u/sliding 2 1)
   (map (fn ^long [[^long prev ^long curr]] (if (> curr prev) 1 0)))))

(defn count-increases
  [red]
  (transduce increase-xf + 0 red))


(def windowed-increase-xf
  (comp
   (map u/parse-long)
   (u/sliding 3 1)
   (map (fn [[x y z]] (+ x y z)))
   (u/sliding 2 1)
   (map (fn ^long [[^long prev ^long curr]] (if (> curr prev) 1 0)))))

(defn count-windowed-increases
  [red]
  (transduce windowed-increase-xf + 0 red))

(count-increases (u/day-lines 1))
(count-windowed-increases (u/day-lines 1))

(count-increases [199 200 208 210 200 207 240 269 260 263])
(count-windowed-increases [199 200 208 210 200 207 240 269 260 263])
