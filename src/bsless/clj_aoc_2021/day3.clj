(ns bsless.clj-aoc-2021.day3
  (:require [bsless.clj-aoc-2021.utils :as u]))

(defn parse-binary [^String s] (Long/valueOf s 2))

(def sample
  (mapv
   parse-binary
   [
    "00100"
    "11110"
    "10110"
    "10111"
    "10101"
    "01111"
    "00111"
    "11100"
    "10000"
    "11001"
    "00010"
    "01010"
    ]))

(definline -bit-test
  [x n]
  `(. clojure.lang.Numbers testBit ~x ~n))

(defmacro areverse
  [arr]
  `(let [arr# (aclone ~arr)
         n# (alength arr#)
         m# (unchecked-int (unchecked-dec n#))]
     (dotimes [i# n#]
       (aset arr# (unchecked-int i#) (aget ~arr (unchecked-subtract-int m# (unchecked-int i#)))))
     arr#))

(defn- count-occurances
  [sample ^long n]
  (let [arr (int-array n)]
    (reduce
     (fn [_ ^long x]
       (dotimes [i n]
         (when (-bit-test x i)
           (aset arr (unchecked-int i) (unchecked-inc-int (aget arr (unchecked-int i)))))))
     nil
     sample)
    (vec (areverse arr))))

(defn- gamma
  [^long size ^long n occurances]
  (reduce-kv
   (fn ^long [^long acc ^long idx ^long i]
     (if (< size i)
       (unchecked-add acc (bit-shift-left 1 (unchecked-subtract n (unchecked-inc idx))))
       acc))
   0
   occurances))

(defn go [sample n]
  (let [size (/ (count sample) 2)
        total (dec (bit-shift-left 1 n))
        occurances (count-occurances sample n)
        gamma (gamma size n occurances)
        epsilon (- total gamma)]
    [(* gamma epsilon) gamma epsilon]))

(comment
  (go sample 5)
  (go (into [] (map parse-binary) (u/day-lines 3)) 12));; => [841526 217 3878]


(defn ones-at
  [data i]
  (let [i (unchecked-int i)]
    (reduce
     (fn [acc x]
       (if (-bit-test x i)
         (unchecked-inc acc)
         acc))
     0
     data)))

(defn -keep [data  keeping i]
  (filterv (fn [x] (.equals keeping (-bit-test x i))) data))

(defn -rate
  [data i inv]
  (if (vector? data)
    (if (= (count data) 1)
      (nth data 0)
      (let [occ (ones-at data i)
            i (unchecked-int i)
            keeping (if (inv (<= (/ (count data) (unchecked-int 2)) occ)) true false)]
        (-keep data keeping i)))
    data))

(defn -rating
  [sample n]
   (loop [generator-data sample
          scrubber-data sample
          i n]
     (if (and (number? generator-data)
              (number? scrubber-data))
       [generator-data scrubber-data]
       (recur (-rate generator-data i identity)
              (-rate scrubber-data i not)
              (unchecked-dec i)))))

(comment
  (-rating sample 4)
  (-rating (into [] (map parse-binary) (u/day-lines 3)) 11)
  (apply * [1177 4070]) 4790390

  )
