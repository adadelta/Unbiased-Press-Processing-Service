(ns processing-service.database.mongodb-connection
  (:require
   [processing-service.config :refer :all :as config]
   [monger.core :as mg]
   [monger.credentials :as mcred]
   [mount.core :refer [defstate]]))

(defstate mongodb-connection
  :start (let [host (config/get-mongo-host)
               port (config/get-mongo-port)
               user (config/get-mongo-username)
               auth-db (config/get-mongo-auth-db)
               password (config/get-mongo-password)
               credentials (mcred/create user auth-db password)]
               (mg/connect-with-credentials host port credentials)))

(comment
  (mount.core/start)
  (mg/get-db-names mongodb-connection))