(ns alphabet-cipher.coder
  (:require [clojure.string :as s]))

(def alphabet "abcdefghijklmnopqrstuvwxyz")

(def encode-decode-map (hash-map :encode 1 :decode -1))

(defn get-letter [method k m]
  (let [i (mod (+ (* (get encode-decode-map method)
                     (s/index-of alphabet k))
                  (s/index-of alphabet m))
               (count alphabet))]
    (subs alphabet i (inc i))))

(defn encode-letter [k m]
  (get-letter :encode k m))

(defn decode-letter [k m]
  (get-letter :decode k m))

(defn expand [keyword message]
  (let [times (inc (quot (count message) (count keyword)))]
    (subs (s/join (repeat times keyword)) 0 (count message))))

(defn encode [keyword message]
  (s/join (map encode-letter (expand keyword message) message)))

(defn decode [keyword message]
  (s/join (map decode-letter (expand keyword message) message)))

(defn smallest-keyword [keyword message cipher n]
  (let [small-keyword (subs keyword 0 n)]
    (if (= (encode small-keyword message) cipher)
      small-keyword
      (smallest-keyword keyword message cipher (inc n)))))

(defn decipher [cipher message]
  (let [repeated-keyword (s/join (map decode-letter message cipher))]
    (smallest-keyword repeated-keyword message cipher 1)))
