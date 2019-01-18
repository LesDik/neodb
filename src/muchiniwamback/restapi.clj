
(ns muchiniwamback.restapi
  "This namespace implements all dbprotocols.spec functions"
  (:require [datascript.core :as memd]
            [clojure.core.async :refer (go >! <! timeout)]
            [muchiniwamback.muchiniwam :as mcw]
            [clojure.data.json :as json]
            [cheshire.core :refer :all]
            [clojure.java.io]
            [clojure.string :as strimul]
            [bidi.ring :refer (make-handler)]
            [ring.util.response :refer (response redirect content-type)]
            [ring.middleware.json :refer (wrap-json-response
                                          wrap-json-params
                                          wrap-json-body)]
            [ring.middleware.cors :refer (wrap-cors)]
            [ring.middleware.defaults :refer (wrap-defaults	        
                                              api-defaults
       			                      site-defaults
   					      secure-api-defaults
   					      secure-site-defaults
                                             )]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [com.stuartsierra.component :as component]
            [immutant.web :as http-server]))

(defn muchiniwamback-handler
  [request]

 (cond
   (= (strimul/upper-case (:request-method request)) ":POST")
     (do
       (def requestparams  (parse-string (generate-string
                               (:body request)) true ))
       ;checkjson_body_validity (here!) 
      ; (def requestparams  {:app "db_app" :database_type "sql" 
       ;                    :database_name "deadsql" :database_table "testing"
        ;                   :requesttype "getRecord" :requestdata "all"             
         ;                  :params "enforce_timeout"}) 
      (cond
          (and (contains? requestparams :app) (contains? requestparams :database_type)
               (contains? requestparams :database_name) (contains? requestparams
               :database_table) (contains? requestparams :requesttype) (contains?
               requestparams :requestdata) (contains? requestparams :params))
          (do
           ; (response {:message "Checked dbprotocols.spec compliance"})
            (println ;(:database_name requestparams) (:database_table requestparams) 
                      requestparams (:requestdata requestparams))
          ; (def requestdatamap (json/read-str (parse-string 
           ;                    (:requestdata requestparams) true) :key-fn keyword))
           (def requestdata (mcw/processServerRequest (:database_type requestparams)
                                                  (:database_name requestparams)
                                                  (:database_table requestparams)
                                                  (:requesttype requestparams)
                                                  (:requestdata requestparams) 
                                                  (:params requestparams)))
                                           ; "testing" "getRecord" "all"))
           (response  {:message "Data_From_Muchiniwam_Server" :data requestdata})
          )  
      :else
        (response {:message "Request does not comply with the dbprotocols.spec"})  
      )                                               
     )
   :else
     (response (str "Request Method Not Supported", (:request-method request))))

 )

(defn api-cors [api]
 (-> api  (wrap-cors :access-control-allow-origin  [#".*"]
         ;            :headers ["Access-Control-Allow-Headers"
         ;                      "X-Requested-With,Content-Type,Cache-Control"]
                     :access-control-allow-methods [:get :put :post  
                                                    :delete :options])))

(defn api-handler [handler]
  (wrap-defaults handler secure-api-defaults))
(defn site-handler [handler]
  (wrap-defaults handler (update site-defaults into  {[:session] true
                                  [:responses :content-types] "application/json"
                                  [:security :anti-forgery] false})))
(def handler 
 (api-cors
 (wrap-json-response
 (wrap-keyword-params
 (wrap-json-body
; (site-handler
;(make-handler {"/sqldb" nextdb-handler}
 (make-handler ["/" {"sqldb" muchiniwamback-handler}]
             )))));)
 ) 

(defrecord restserver [host port restc]
  component/Lifecycle
  (start [component]
    (println "Starting HTTP Server")
    (println (str "Started web server on http://" host ":" port "/"))
    (assoc component
             :restc (http-server/run handler
                                     {:host host :port port
                                          :path "/"}))
                 )

  (stop [component]
    (println "Stopping HTTP Server")
    (http-server/stop restc)
    (assoc component :restc nil)
               ))

(defn restc-component [host port]
  (map->restserver {:host host :port port}))

