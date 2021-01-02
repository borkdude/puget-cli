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

(defn in-seq
  [reader]
  (lazy-seq
    (if-let [obj (edn/read {:eof nil} reader)]
      (cons obj (in-seq reader))
      (.close ^PushbackReader reader))))

(defn -main [& args]
  (let [arg-opts (parse-args args)
        opts (:opts arg-opts)
        opts (merge default-opts opts)]
    (run! #(puget/pprint % opts) (in-seq (PushbackReader. *in*)))))
