(defproject veletlenszeru "0.1.1"
  :description "Random quote fetching console utility."
  :url "https://github.com/q60/veletlenszeru"
  :license {:name "MIT"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [clj-http "3.12.3"]]
  :plugins [[lein-cljfmt "0.8.0"]]
  :main veletlenszeru.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
