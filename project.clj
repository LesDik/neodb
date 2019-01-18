(defproject muchiniwamback "0.1.2"
  :description "muchiniwam back-end server"
  :url "http://muchiniwam.com/"
  :license {:name "Proprietary License"
            :url " "}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/java.jdbc "0.6.1"]
                 [org.postgresql/postgresql "9.4.1211"]
                 [clj-postgresql "0.7.0"]
                 [org.clojure/core.logic "0.8.11"]

                 [dbprotocols "0.1.21"]

                 [com.stuartsierra/component "0.3.2"]

                 [org.clojure/tools.namespace "0.2.11"]

                 [datascript "0.16.2"]

                 [ring/ring-core "1.6.2"]
                 [ring/ring-json "0.4.0"]
                 [ring/ring-json "0.4.0"]
                 [ring-cors "0.1.11"]
                 [ring/ring-defaults "0.3.1"]

                 [org.immutant/web "2.1.5"]
                 [org.immutant/scheduling "2.1.5"]
                 [org.immutant/immutant "2.1.8"]
                 [compojure "1.6.0"]
                 [bidi "2.1.2"]
                 [liberator "0.15.1"]
                 [clj-time "0.14.0"]
                 [cheshire "5.8.0"]
                 [environ "1.1.0"]

                 [org.clojure/core.async "0.3.443"]
        ]
  :main ^:skip-aot muchiniwamback.core
  :dev-dependencies [[uk.org.alienscience/leiningen-war "0.0.13"]]
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
