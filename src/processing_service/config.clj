(ns processing-service.config
  (:gen-class)
  (:require [mount.core :refer [start defstate]]
            [aero.core :as aero]
            [clojure.java.io :as io]))

(defn get-config []
  (aero/read-config (io/resource "config.edn")))

(defstate env
  :start (get-config))

(defn get-mongo-host
  "Gets the mongodb host from config"
  []
  (get-in env [:mongo :host]))

(defn get-mongo-port
  "Gets the mongodb port from config"
  []
  (get-in env [:mongo :port]))

(defn get-mongo-db
  "Gets the mongodb db from config"
  []
  (get-in env [:mongo :db]))

(defn get-mongo-articles-collection
  "Gets the name of the collection holding articles"
  []
  (get-in env [:mongo :article-collection]))

(defn get-mongo-username
  "Gets the mongodb username (credentials) from config"
  []
  (get-in env [:mongo :credentials :user]))

(defn get-mongo-auth-db
  "Gets the mongodb auth-db (credentials) from config"
  []
  (get-in env [:mongo :credentials :auth-db]))

(defn get-mongo-password
  "Gets the mongodb password (credentials) from config"
  []
  (get-in env [:mongo :credentials :password]))

(defn get-server-port
  "Gets this services port"
  []
  (get-in env [:server :port]))

(comment
  (start)
  (get-mongo-port)
  (get-mongo-password)
  (get-mongo-articles-collection)
  (get-server-port))