
(ns muchiniwamback.core
  (:require [datascript.core :as memdata]
            [clojure.string :as s]
            [clojure.data.json :as json]
            [clojure.edn :as edn]
            [clojure.java.io]
            [clojure.pprint :as printy]
;            [muchiniwam.config :as config]
;            [muchiniwam.bus :as bus]
            [cheshire.core :refer :all]
;            [muchiniwam.sysecurity :as sys]
;            [muchiniwam.sysmonitorfns :as sysm]
;            [muchiniwam.dsconsole :as dsc]
            [muchiniwamback.dbactions :as dba]
            [muchiniwamback.restapi :as api]
            [clojure.tools.namespace.repl :refer :all]
            [com.stuartsierra.component :as component]
            )
  (:import (org.postgresql.util.PGobject)
            )
  (:gen-class))

(defn fine-system [settings]
   (component/system-map
      :maincomp   (dba/dsEngine-component settings)
      :restapi    (api/restc-component (get-in settings [:server :host])
                                       (get-in settings [:server :port]))
   )
 )

(def system nil)

(def applicationstate (atom {:identity 1}))

(defn updateapplication [entity newvalue]
     (cond
       (= entity "identity")
         (do
          (swap! applicationstate assoc :identity newvalue)
          (println entity " ... updated")
         )
       (= entity "settings")
         (do
          (swap! applicationstate assoc :settings newvalue)
          (println entity " ... updated")
         )
       (= entity "timer")
         (do
          (swap! applicationstate assoc :timer newvalue)
          (println entity " ... updated")
         )
       :else
         (do
           (println "No updates")
         ;other updates    
         )
     )
)

(defn init []
  ;;initialise system

  (updateapplication "settings" (parse-string
                            (json/read-str (slurp "/progs/muchiniwamback/settings.json"))true))

  (updateapplication "timer" 35000)

  (def all-records
                   (:settings @applicationstate))

  (println (:identity @applicationstate)
           (get-in all-records [:server :port]))

  (def system (fine-system all-records))
  ;;=> #'examples/system
 )
(defn start[]
  ;;start the system
  (alter-var-root #'system component/start)
  )

(defn stop []
  ;;stop the system
  (alter-var-root #'system component/stop)
 )
(defn go []
  (init)
  (start)
 )

(defn reset []
  ;;reset the system
  (stop)
  (refresh)
 )

(defn close []
  (print "Try closing me > ")
  ;(Thread/sleep (:timer @applicationstate))
  (def s "it's tym to park this")
  (if (= (str s) (str "it's tym to park this"))
   (do (stop)
       (reset)
   ))
 )

(defn -main
  "muchiniwamback"
  [& args]
  (println "muchiniwamback")

  ;;initialise system 
  (go)
  ;;stop the system
 ; (close)
)

