(ns veletlenszeru.core
  (:gen-class)
  (:require [clj-http.client :as http]
            [clojure.string :as string]
            [clj-wrap-indent.core :as wrap]))

(defn -main
  "velétlenszerű - random quote fetching console utility"
  [& _]
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
             (wrap/wrap-indent quote-text 60 1)))
    (when (not= quote-text quote-author)
      (printf "\033[93m%s\033[0m\n"
              (string/trim
               (string/replace quote-author #"\)" ""))))
    (flush)))
