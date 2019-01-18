;;This namespace implements all the database (i.e.dbprotocols) functions

(ns muchiniwamback.dbprotocolsTranslator
  "This namespace implements all database operations"
  (:require [datascript.core :as memdata]
            [clojure.core.logic]
            [clojure.data.json :as json]
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
  ;                 -dbleafbatch
  ;                    -ProjectID/ProducerGroup
  ;                   -dbpleafbatch <--  
  ;                   -sqlleafbatch     \ 
  ;                   -grhleafbatch      
  ;                   -colleafbatch     /
  ;                     -pID/pgIDleaf - 
  ;                      -file.locale
  ;                      -leafs | splitting @ 1024
  ;                        -currentbatchleaf
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

(def leafcreate)

(defn sRdbpspec [dbpspec request requestdata ]
        
     ;; analyse request + its "content"

      ;;while analyzing

       ;;create leaflets
  
      
      ;;when done analyzing
    
       ;;createleaf
         
        ;;storeleaf
        ;;check if leaf saved
          ;;confirm / disconfirm


           (println " info being stored ")
          (def jsondata (json/write-str requestdata))
          (def lenv (str (:uuid requestdata)
                         (:ssid requestdata)))

          (def persist (str lenv "/" (epoch/to-long (tyme/now)) "/" ))

          (println persist)
          (. (Runtime/getRuntime) exec (str "mkdir -p " "nextdb/" persist))
                              
          (spit (str "nextdb/" persist (:uuid requestdata)) jsondata)

 )

(defn gRdbpspec [dbpspec request requestdata ]
 
      ;;analyse request "producer.identity" / projectname / "content" / consumer

       ;;while analyzing

        ;;analyze db and its producers

           ;;while analysing

            ;;find project
     
            ;;find leaf.s

            ;;leafcreate - PULL 

           ;;when done analyzing

            ;;prepare for request-type

            ;;package and send to consumer

     (println  "ileaf :" (leafcreate "project" (parse-string
                            (json/read-str (slurp "/progs/muchiniwamback/settings.json"))true))
       )

 )

(defn iSdbpsec [dbpspec request requestdata ]


 )

(defn iDdbpsec [dbpspec request requestdata ]
 )

(defn cTdbpsec [dbpspec request requestdata ]
 )


(defn leafcreate [entity newvalue]
     (cond
       (= entity "lplace")
         (do
          (swap! ileaf assoc :lplace newvalue)
          (println entity " ... updated")
         )
       (= entity "lenv")
         (do
          (swap! ileaf assoc :lenv newvalue)
          (println entity " ... updated")
         )
       (= entity "ltokens")
         (do
          (swap! ileaf assoc :ltokens newvalue)
          (println entity " ... updated")
         )
       (= entity "project")
         (do
          (swap! ileaf assoc :ll3 newvalue)
          (println entity " ... updated")
         )
       (= entity "ll4")
         (do
          (swap! ileaf assoc :ll4 newvalue)
          (println entity " ... updated")
         )
       (= entity "lcontent")
         (do
          (swap! ileaf assoc :lcontent newvalue)
          (println entity " ... updated")
         )
       (= entity "ltime")
         (do
          (swap! ileaf assoc :ltime newvalue)
          (println entity " ... updated")
         )
       (= entity "ltype")
         (do
          (swap! ileaf assoc :ltype newvalue)
          (println entity " ... updated")
         )
       (= entity "lformat")
         (do
          (swap! ileaf assoc :lformat newvalue)
          (println entity " ... updated")
         )
       (= entity "lformatleaf")
         (do
          (swap! ileaf assoc :lformatleaf newvalue)
          (println entity " ... updated")
         )
       (= entity "ll5")
         (do
          (swap! ileaf assoc :ll5 newvalue)
          (println entity " ... updated")
         )
       (= entity "lmeaning")
         (do
          (swap! ileaf assoc :lmeaning newvalue)
          (println entity " ... updated")
         )
       (= entity "unlocks")
         (do
          (swap! ileaf assoc :unlocks newvalue)
          (println entity " ... updated")
         )
       (= entity "lsenses")
         (do
          (swap! ileaf assoc :lsenses newvalue)
          (println entity " ... updated")
         )
       (= entity "lsource")
         (do
          (swap! ileaf assoc :lsource newvalue)
          (println entity " ... updated")
         )
       (= entity "ltarget")
         (do
          (swap! ileaf assoc :ltarget newvalue)
          (println entity " ... updated")
         )
       (= entity "lgranurality")
         (do
          (swap! ileaf assoc :lgranurality newvalue)
          (println entity " ... updated")
         )
       (= entity "lmapreduce")
         (do
          (swap! ileaf assoc :lmapreduce newvalue)
          (println entity " ... updated")
         )
       (= entity "ll6")
         (do
          (swap! ileaf assoc :ll6 newvalue)
          (println entity " ... updated")
         )
      (= entity "ll6")
         (do
          (swap! ileaf assoc :ll6 newvalue)
          (println entity " ... updated")
         )
       (= entity "lidentity")
         (do
          (swap! ileaf assoc :lidentity newvalue)
          (println entity " ... updated")
         )
       (= entity "lrelative")
         (do
          (swap! ileaf assoc :lrelative newvalue)
          (println entity " ... updated")
         )
       (= entity "ll7")
         (do
          (swap! ileaf assoc :ll7 newvalue)
          (println entity " ... updated")
         )
       (= entity "ll8")
         (do
          (swap! ileaf assoc :ll8 newvalue)
          (println entity " ... updated")
         )
       (= entity "ll9")
         (do
          (swap! ileaf assoc :ll9 newvalue)
          (println entity " ... updated")
         )
       (= entity "ll10")
         (do
          (swap! ileaf assoc :ll10 newvalue)
          (println entity " ... updated")
         )
       :else
         (do
           (println "No updates")
         ;other updates    
         )
     ) ;lspace, lke, ltokens, ll3, ll4,
                      ; lcontent, ltime, ltype, lformat, lformatleaf, ll5,
                     ;  lmeaning, unlocks, lsenses, lsource, ltarget,
                    ;   lgranurality, ;default granularity = 0, 1 = standard for nanosecond intervals
                   ;    lmapreduce, ll6,
                  ;     lidentity, lrelative, ll7, ll8, ll9, ll10
 @ileaf
)

(defn dbprotocolsTranslator [dbpspec request requestdata]
 ;*this* must 'OWN' a neo.db INSTANCE 
 ;'neo.db' has to be instantiated, a script to be used here

(cond (.equals request "getRecord")
      (do
      (gRdbpspec "dbpspec" "getRecord" requestdata)
      (println "all data ...")
      
      )
     (.equals request "setRecord")
      (do
      (sRdbpspec "dbpspec" "setRecord" requestdata)
      (println "all data ...")
      
      )
     (.equals request "infoStore")
      (do
      ;(ps2/query dbpspec ["SELECT * from testing"])
      (println "all data ...") 
      )
     (.equals request "infoDelete")
      (do
      ;(ps2/query dbpspec ["SELECT * from testing"])
      (println "all data ...")
      
      )
     (.equals request "resetTokens")
      (do
      ;(ps2/query dbpspec ["SELECT * from testing"])
      (println "all data ...")
      
      )
     (.equals request "chargeTokens")
      (do
      ;(ps2/query dbpspec ["SELECT * from testing"])
      (println "all data ...")
      
      )
     (.equals request "checkTokens")
      (do
      ;(ps2/query dbpspec ["SELECT * from testing"])
      (println "all data ...")
      
      )
     :else
       nil

;(json energy.key.value.time.space)
  )
 )


