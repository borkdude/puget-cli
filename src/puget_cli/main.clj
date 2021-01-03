(ns puget-cli.main
  (:gen-class)
  (:require [clojure.edn :as edn]
            [clojure.string :as str]
            [puget.printer :as puget])
  (:import [java.io PushbackReader]))

(def default-opts
  {:print-color true})

(defn parse-args [options]
  (let [opts (loop [options options
                    opts-map {}
                    current-opt nil]
               (if-let [opt (first options)]
                 (if (str/starts-with? opt "--")
                   (recur (rest options)
                          (assoc opts-map opt nil)
                          opt)
                   (recur (rest options)
                          (update opts-map current-opt (fnil conj []) opt)
                          current-opt))
                 opts-map))]
    {:opts (some-> (get opts "--opts")
                   first
                   edn/read-string)}))

(defn read-and-print
  [reader opts]
  (with-open [r (PushbackReader. reader)]
    (->> (repeatedly #(edn/read {:eof ::eof} r))
         (take-while #(not= % ::eof))
         (run! #(puget/pprint % opts)))))

(defn -main [& args]
  (let [arg-opts (parse-args args)
        opts (:opts arg-opts)
        opts (merge default-opts opts)]
    (read-and-print *in* opts)))
