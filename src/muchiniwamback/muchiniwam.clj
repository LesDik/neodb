;;This namespace implements all the database (i.e.dbprotocols) functions

(ns muchiniwamback.muchiniwam
  "This namespace implements all database operations"
  (:require [datascript.core :as memdata]
            [clojure.core.logic]
            [clojure.data.json :as json]
            [muchiniwamback.dbactions :as dba]
            [cheshire.core :refer :all]
            [clojure.edn :as edn]
            [clojure.java.io]
            [clj-time.core :as tyme]
            [clj-time.coerce :as epoch]
            [clj-postgresql.core :as psql]
            [clojure.java.jdbc :as ps2] 
            [com.stuartsierra.component :as component])
  (:import [java.util UUID] [java.lang.Runtime])
   )
;;; For 'ANY' data storage . retrieval . update . and delete:
  ;             a. indexing for data 'keys' e.g. columnID, table+rowID, graphID, collectionID
  ;             b. dictionary for the 'found' (data - via - keys)
  ;                a--> time.space / data : 'storing'.in-place (file.locale) 
  ;                b--> time.space / data : 'retriev'.in-place (location/index??)

;;; ACID: required for **ensuring** 'fidelity':
  ;             a. No 'production' w/th consumption . Atomicity
  ;             b. 'Consumption' (incl. timers) determines 'production' rate . Consistency
  ;             c. Producer - value.controller - Consumer . Isolation
  ;             d. Accretion >> . Durability / Immutability


(defrecord leafformat [^String lspace, lenv, ltokens, ll3, ll4,
                       lcontent, ltime, ltype, lformat, lformatleaf, ll5,
                       lmeaning, unlocks, lsenses, lsource, ltarget,
                       lgranurality, ;default granularity = 0, 1 = standard for nanosecond intervals
                       lmapreduce, ll6,
                       lidentity, lrelative, ll7, ll8, ll9, ll10])

(def ileaf (atom (leafformat. "imem" "server" 100 "testing" "begin" ;leaflocale 
               "test 123" "devinfo" "text" "nil" "nil"       ;leafinfo  
               "nil" "manuallyenteredtext" "me" "test" "nil" "wed 8am" ;leafclock
               "0" "0" "nil";leaflets
               "nil" "nil" "nil" "nil" "nil" "nil" ;leafidentity
              )))

;(json:energy.key.value.time.space)

(def customerstate (atom {:uuid 1}))

(defn checkifcustomerregistered [customerstatemap]
 ;starts from 'saveDcustomerdata
 false
 )

(defn registercustomer [customer]
 
 )

(defn checkifcustomerexists [customerstatemap]
 ;comes from 'saveDcustomerdata
 false
 )

(defn checkifcustomerbanned [customerstatemap]
 ;comes from 'saveDcustomerdata
 false
 )

(defn bancustomer [customer]

 )

(defn updatecustomerdata [entity newvalue]
     (cond
       (= entity "identity")
         (do
          (swap! customerstate assoc :uuid newvalue)
          (println entity " ... updated")
         )
       (= entity "idtype")
         (do
          (swap! customerstate assoc :idtype newvalue)
          (println entity " ... updated")
         )
       (= entity "tokens")
         (do
          (swap! customerstate assoc :tokens newvalue)
          (println entity " ... updated")
         )
       (= entity "timesaved")
         (do
          (swap! customerstate assoc :timesaved newvalue)
          (println entity " ...updated")
         )
       (= entity "customerstatus")
         (do
          (swap! customerstate assoc :customerstatus newvalue)
          (println entity " ...updated")
         )
       :else
         (do
           (println "No updates")
         ;other updates    
         )
     )
)

;define the vector that stores the 'customerdata'

(defn loadcustomerdata []
 ; (updatecustomerdata "settings" (parse-string
  ;                          (json/read-str (slurp "settings.json"))true))
 ;iterate through the stored file
  ;iterate through 'customerstate'

 )

(defn processServerRequest   [dbtype      ;sql / nextdb
                          dbname      ;deadsql / casdigital / openbeerdb,etc
                          dbtable     ;records, tables, documents, graphs, etc
                          requestype  ;getRecord, setRecord, infoSearch, etc
                          requestdata ;"all", maps/json, documents/metadata,etc
                          params] 
     (cond
       (and (= requestype "getRecord") (contains? requestdata :uuid)
                       (= (count (:uuid requestdata)) 36))
   	 (do
    	    ; store req.info FIRST
            ;(dba/processsDBrequest dbtype/dbprotocols.spec
            ;                     dbname/neodb 
             ;                    "testing"/leaf
              ;                   "infoStore" ;decided here!
               ;                  requestdata
                ;                 params)
            (cond
             (= true (checkifcustomerregistered (:uuid requestdata)))
               (do
                  (println "Record Retrieved ")
                  (println "record : " requestdata)
                  {:message "customerallowedforonly100tokens" :data {:tokensallocated 100}} 
               )
             :else
               (do
                 (cond
                  (= true (checkifcustomerbanned (:uuid requestdata)))
                  {:message "customerbanned" :data {:tokensallocated 0}}
                  :else
                 (do 
                   (println (count (:uuid requestdata)))
                   ; (pass it BACK!!! .infoStore @dbEngine))
                 {:message "customerallowedforonly100tokens" :data {:tokensallocated 100}}   
                 )
               ))
          ))
        :else
         {:message "The Customer Request is not Compliant"}
  )
)







