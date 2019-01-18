;;This namespace implements all the database (i.e.dbprotocols) functions

(ns muchiniwamback.dbactions
  "This namespace implements all database operations"
  (:require [datascript.core :as memdata]
            [dbprotocols.core]
            [clojure.core.logic]
            [muchiniwamback.dbprotocolsTranslator :as mdbtr]
            [clojure.data.json :as json]
            [cheshire.core :refer :all]
            [clojure.edn :as edn]
            [clojure.java.io]
            [clj-time.core :as tyme]
            [clj-time.coerce :as epoch]
            [clj-postgresql.core :as psql]
            [clojure.java.jdbc :as ps2] 
            [com.stuartsierra.component :as component])
  (:import [dbprotocols.core dbFunctions] [java.util UUID] [java.lang.Runtime])
   )

(defn dbprotocolsTranslator [dbpspec request requestdata] 
 ;*this* must 'OWN' a neo.db INSTANCE 
 ;'neo.db' has to be instantiated, a script to be used here
 )

(defn sqlTranslator [sqldb request requestdata]
 ;*this* must 'OWN' a sql.db INSTANCE
 ; a 'sql.db' has to be instantiated, a script to be used here
 )

(defn graphqlTranslator [graphdb request requestdata];ditto.above
 ; a 'graph.db' has to be instantiated, a script to be used here
 )

(defrecord deadsqldb [dbrequest] dbFunctions
(setRecord [this]
  ;'2nd' line of records (before the FINAL line of 'batch-info'). 1st line of chargeTkns
 (println "getting record...")
  (cond
    (and (.equals (:dbtype dbrequest) "sql")
         (.equals (:request dbrequest) "setRecord" ))
      (do ; the request will be sent to a 'sql' request translator and owner
          (mdbtr/dbprotocolsTranslator (:neodbconnection (:dbcomponent dbrequest)) 
                                       "setRecord" (:requestdata dbrequest))
          )
    (and (.equals (:dbtype dbrequest) "dbprotocols.spec")
         (.equals (:request dbrequest) "setRecord" ))
      (do ; the request will be sent to a 'dbp..spec' request translator and owner

          )
    (and (.equals (:dbtype dbrequest) "graphql")
         (.equals (:request dbrequest) "setRecord" ))
      (do ; the request will be sent to a 'graphql' request translator and owner

          )
    :else
      (do (println "Sorry it didn't work budd")
         (println (str (:request dbrequest)))
        ; (nil)
      )
   )

 )

(getRecord [this]
 ;'1st' line of clear/resetTokens  
  (cond 
    (and (.equals (:dbtype dbrequest) "sql") 
         (.equals (:request dbrequest) "getRecord" ))
      (do ; the request will be sent to a 'sql' request translator and owner
         (mdbtr/dbprotocolsTranslator (:neodbconnection (:dbcomponent dbrequest)) 
                                "getRecord" (:requestdata dbrequest))     
          )
    (and (.equals (:dbtype dbrequest) "dbprotocols.spec") 
         (.equals (:request dbrequest) "getRecord" ))
      (do ; the request will be sent to a 'dbp..spec' request translator and owner
          
          )
    (and (.equals (:dbtype dbrequest) "graphql") 
         (.equals (:request dbrequest) "getRecord" ))
      (do ; the request will be sent to a 'graphql' request translator and owner
       
          )
    :else
      (do (println "Sorry it didn't work budd")
         (println (str (:request dbrequest)))
        ; (nil)
      )
   )
    ; (println "Result :" (ps/query @(:dbobject dbrequest) ["SELECT 1"])) 
)

(infoStore[this]
(println "infoStore")
 ; First Use Case: store 'EVERY' **interaction** before 'setting-record'=neoDB.leaf
(println "Begin execution of this request: "  (:request dbrequest))
 ;NO 'token-work' here... but 'first-line' of records
 
     (if (.equals (:request dbrequest) "infoStore")
      (do
          (println " info being stored ")
          (def jsondata (json/write-str (:requestdata dbrequest)))
          (def lenv (str (:uuid (:requestdata dbrequest)) 
                         (:ssid (:requestdata dbrequest))))

          (def persist (str lenv "/" (epoch/to-long (tyme/now)) "/" ))
 
          (println persist) 
          (. (Runtime/getRuntime) exec (str "mkdir -p " "nextdb/" persist))
         ;(clojure.java.io/writer (str "customers/" (:uuid customerstatemap)) jsondata)                     
          (spit (str "nextdb/" persist (:uuid (:requestdata dbrequest))) jsondata)
     
       ))            
   (cond
    (and (.equals (:dbtype dbrequest) "sql")
         (.equals (:request dbrequest) "infoStore" ))
      (do ; the request will be sent to a 'sql' request translator and owner
          (mdbtr/dbprotocolsTranslator (:neodbconnection (:dbcomponent dbrequest)) 
                                 "infoStore" (:requestdata dbrequest))
          )
    (and (.equals (:dbtype dbrequest) "dbprotocols.spec")
         (.equals (:request dbrequest) "infoStore" ))
      (do ; the request will be sent to a 'dbp..spec' request translator and owner

          )
    (and (.equals (:dbtype dbrequest) "graphql")
         (.equals (:request dbrequest) "infoStore" ))
      (do ; the request will be sent to a 'graphql' request translator and owner

          )
    :else
      (-> "Sorry it didn't work budd"
         println
      )
   )
 )

(infoDelete[this]
 (println "dataDelete")
 )

(infoAsk [this] ; (Deterministic Data Points)
  (println "infoAsk")
  ;First Use Case: are there tokens available for this user?


  ;Second Use Case: should this user be allowed more tokens?


  ;Third Use Case: should this user be banned?

    (cond
    (and (.equals (:dbtype dbrequest) "sql")
         (.equals (:request dbrequest) "infoAsk" ))
      (do ; the request will be sent to a 'sql' request translator and owner
          (mdbtr/dbprotocolsTranslator (:neodbconnection (:dbcomponent dbrequest)) 
                                 "infoStore" (:requestdata dbrequest))
          )
    (and (.equals (:dbtype dbrequest) "dbprotocols.spec")
         (.equals (:request dbrequest) "infoAsk" ))
      (do ; the request will be sent to a 'dbp..spec' request translator and owner

          )
    (and (.equals (:dbtype dbrequest) "graphql")
         (.equals (:request dbrequest) "infoAsk" ))
      (do ; the request will be sent to a 'graphql' request translator and owner

          )
    :else
      (do (println "Sorry it didn't work budd")
         (println (str (:request dbrequest)))
        ; (nil)
      )
   )


 )
(infoSearch [this] ;(Annotated Intelligence - Unbounded)
  (println "infoSearch")

  ;First Use Case: is this user banned?
    (cond
    (and (.equals (:dbtype dbrequest) "sql")
         (.equals (:request dbrequest) "infoSearch" ))
      (do ; the request will be sent to a 'sql' request translator and owner
          (mdbtr/dbprotocolsTranslator (:neodbconnection (:dbcomponent dbrequest)) 
                                 "setRecord" (:requestdata dbrequest))
          )
    (and (.equals (:dbtype dbrequest) "dbprotocols.spec")
         (.equals (:request dbrequest) "infoSearch" ))
      (do ; the request will be sent to a 'dbp..spec' request translator and owner

          )
    (and (.equals (:dbtype dbrequest) "graphql")
         (.equals (:request dbrequest) "infoSearch" ))
      (do ; the request will be sent to a 'graphql' request translator and owner

          )
    :else
      (do (println "Sorry it didn't work budd")
         (println (str (:request dbrequest)))
        ; (nil)
      )
   )

 )
(infoRetrieve [this] ; (Defined/Bounded Data)
  (println "infoRetrieve")
 )
(getTree [this] ; get trees / graphed data blocks
 (println "getTree")
 )
(getAnswerPage [this]
 (println "getAnswerPage")
 )

(chargeTokens [this] ; producer process
 (println "   ") 
   ; per leaf / page / record
  (cond
    (and (.equals (:dbtype dbrequest) "sql")
         (.equals (:request dbrequest) "chargeTokens" ))
      (do ; the request will be sent to a 'sql' request translator and owner
          (mdbtr/dbprotocolsTranslator (:neodbconnection (:dbcomponent dbrequest)) 
                                 "setRecord" (:requestdata dbrequest))
          )
    (and (.equals (:dbtype dbrequest) "dbprotocols.spec")
         (.equals (:request dbrequest) "chargeTokens" ))
      (do ; the request will be sent to a 'dbp..spec' request translator and owner

          )
    (and (.equals (:dbtype dbrequest) "graphql")
         (.equals (:request dbrequest) "chargeTokens" ))
      (do ; the request will be sent to a 'graphql' request translator and owner

          )
    :else
      (do (println "Sorry it didn't work budd")
         (println (str (:request dbrequest)))
        ; (nil)
      )
   )

 )
(resetTokens [this] ; producer process
   ; per leaf / page / record
 (println "   ")
 )
(clearTokens [this] ; producer process
 (println "infoStore") 
   ; per leaf / page / record
 )
(batchinfo [this]
 (println "batchInfo")
  ; per block (10 min)
  ;(will also 'charge' and reset Tokens)
))

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

(defrecord requestformat [dbcomponent, dbtype, dbname, dbtable, request, requestdata])
(def dbEngine (atom (deadsqldb. (requestformat. "component" "dbtype"
                          "deadsql" "testing" "getRecord" "SELECT * from testing"))))

(def customerstate (atom {:uuid 1}))

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



(def dbactionvalues (atom {:sqlcomponent "component" :nxtcomponent "component"
                           :vaccomponent "component" :requestvalue "I am a 
                           map"}))

(defn changeDBrequest [ndbcomp ndbtype ndbname ndbtable  
                                             ndbrequest ndbrequestdata] 
  
   (swap! dbEngine update-in [:dbrequest] into {:dbcomponent ndbcomp ;dbEnginecurrentcomp
                      			     :dbtype ndbtype ;dbEnginecurrentdbtype 
                      			     :dbname ndbname ;dbEnginecurrentdbname
                      			     :dbtable ndbtable ;dbEnginecurrentdbtable
                                             :request ndbrequest ;dbEnginecurrentrequest
                                             :requestdata ndbrequestdata}) ;dbecurrequest
 )

(defn processDBrequest   [dbtype      ;sql / nextdb
                          dbname      ;deadsql / casdigital / openbeerdb,etc
                          dbtable     ;records, tables, documents, graphs, etc
                          requestype  ;getRecord, setRecord, infoSearch, etc
                          requestdata ;"all", maps/json, documents/metadata,etc
                          params] 
     (cond
       (and (= requestype "getRecord") (contains? requestdata :uuid)
                       (= (count (:uuid requestdata)) 36))
   	 (do
    	    ; execute req
           (changeDBrequest (:nxtcomponent @dbactionvalues) "dbprotocols.spec" "neodb"
                               "testing" "infoStore" requestdata)
            (cond
             (= "checkifcustome" (:uuid requestdata))
               (do
                  (println "Record Retrieved ")
                  (println "record : " requestdata) 
                  (:requestvalue @dbactionvalues))
             :else
               (do
                 (cond
                  (= "checkifcustomerbanned" (:uuid requestdata))
                  {:message "customerbanned" :data {:tokensallocated 0}}
                  :else
                 (do 
                   (println (count (:uuid requestdata)))
                   (swap! dbactionvalues assoc :requestvalue (.infoStore @dbEngine))
                 {:message "customerallowedforonly100tokens" :data {:tokensallocated 100}}   
                 )
               ))))
       (= requestype "setRecord")
         (do
            ; execute request
           ;(changeDBrequest (:nxtcomponent @dbactionvalues) "sql" "deadsql"
               ;                "testing" "getRecord" "SELECT * from testing")
           ;(swap! dbactionvalues assoc :requestvalue (.getRecord @dbEngine))
           (swap! dbactionvalues assoc :requestvalue {:readerer "wewe"})
           (println "Record Saved ... " (:requestvalue @dbactionvalues))
           (:requestvalue @dbactionvalues))
       (= requestype "infoAssert")
         (do
           ;(changeDBrequest (:nxtcomponent @dbactionvalues) "sql" "deadsql"
               ;                "testing" "getRecord" "SELECT * from testing")
           ;(swap! dbactionvalues assoc :requestvalue (.getRecord @dbEngine))
           (println "Info Asserted ...")
           (:requestvalue @dbactionvalues))
       (= requestype "infoAsk")
         (do
           ;(changeDBrequest (:nxtcomponent @dbactionvalues) "sql" "deadsql"
              ;                 "testing" "getRecord" "SELECT * from testing")
           ;(swap! dbactionvalues assoc :requestvalue (.getRecord @dbEngine))
           (println "info answer! " (:requestvalue @dbactionvalues))
           (:requestvalue @dbactionvalues))
       (= requestype "infoRetract")
         (do
           ;(changeDBrequest (:nxtcomponent @dbactionvalues) "sql" "deadsql"
             ;                  "testing" "getRecord" "SELECT * from testing")
           ;(swap! dbactionvalues assoc :requestvalue (.getRecord @dbEngine))
           (println "info retracted! " (:requestvalue @dbactionvalues))
           (:requestvalue @dbactionvalues))
       (= requestype "infoSearch")
         (do
          ; (changeDBrequest (:nxtcomponent @dbactionvalues) "sql" "deadsql"
            ;                   "testing" "getRecord" "SELECT * from testing")
           ;(swap! dbactionvalues assoc :requestvalue (.getRecord @dbEngine))
           (println "info searched! " (:requestvalue @dbactionvalues))
           (:requestvalue @dbactionvalues))
       (= requestype "infoRetrieve")
         (do
          ; (changeDBrequest (:nxtcomponent @dbactionvalues) "sql" "deadsql"
           ;                    "testing" "getRecord" "SELECT * from testing")
          ; (swap! dbactionvalues assoc :requestvalue (.getRecord @dbEngine))
           (println "info retrieved " (:requestvalue @dbactionvalues))
           (:requestvalue @dbactionvalues))
       (= requestype "infoDelete")
         (do
          ; (changeDBrequest (:nxtcomponent @dbactionvalues) "sql" "deadsql"
            ;                   "testing" "getRecord" "SELECT * from testing")
          ; (swap! dbactionvalues assoc :requestvalue (.getRecord @dbEngine))
           (println "info deleted " (:requestvalue @dbactionvalues))
           (:requestvalue @dbactionvalues))
       :else
         {:message "The Customer Request is not Compliant"}
  )
)

(defrecord dbprotocolsEngine [options dbspec]
  component/Lifecycle
  (start [component]
    (println ";; Starting dbprotocols.Engine", options)
    (assoc component :dbspec dbEngine))

  (stop [component]
    (println ";; Stopping sqlDB(s)")
    (assoc component :dbspec nil))
 )

(defn dsEngine-component [settings]
  (map->dbprotocolsEngine{:options settings}))







