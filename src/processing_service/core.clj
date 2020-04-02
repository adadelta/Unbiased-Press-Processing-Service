(ns processing-service.core
  (:gen-class)
  (:require 
   [mount.core :refer [start]]
   [processing-service.config :refer :all :as config]
   [processing-service.api :as api]
   [clojure.tools.logging :as log]))

(defn -main
  "Start service"
  [& _]
  (log/info "Starting states")
  (start)
  (log/info "Starting API service")
  (api/start (config/get-server-port)))