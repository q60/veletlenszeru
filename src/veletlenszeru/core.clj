(ns veletlenszeru.core
  (:gen-class)
  (:require [clj-http.client :as http]
            [clojure.string :as string]))

(defn wrap
  "text wrapper"
  ([str max-len]
   (wrap 0 (string/split str #"\s") "" max-len))
  ([acc [x & xs] res max-len]
   (cond (or x xs)
         (cond (>= acc max-len)
               (wrap (count x) xs
                     (string/join [res "\n " x])
                     max-len)
               :else
               (wrap (+ acc (count x)) xs
                     (string/join [res " " x])
                     max-len))
     :else res)))

(defn -main [& _]
  (let [uri "https://api.forismatic.com/api/1.0/"
        response (http/get uri {:as :string
                                :query-params {:method "getQuote"
                                               :format "text"
                                               :lang   "en"}})
        text-data (string/split
                   (:body response) #"\(")
        quote-text (first text-data)
        quote-author (last text-data)]
    (printf "\"\033[94m\033[1m%s\033[0m\"\n"
            (string/trim
             (wrap quote-text 40)))
    (when (not= quote-text quote-author)
      (printf "\033[93m%s\033[0m\n"
              (string/trim
               (string/replace quote-author #"\)" ""))))
    (flush)))
