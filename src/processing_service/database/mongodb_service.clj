(ns processing-service.database.mongodb-service
  (:require
   [processing-service.database.mongodb-connection :refer [mongodb-connection]]
   [processing-service.config :refer :all :as config]
   [mount.core :refer [defstate]]
   [monger.core :as mg]
   [monger.collection :as mc]
   [monger.operators :refer [$in $set]]
   [clojure.tools.logging :as log]))

(def article-load-fields ["_id" "fetchTime" "day_fetched"
                          "provider" "author" "link"
                          "category" "headline" "paragraphs"
                          "summary" "link_id" "correlation_coefficient"])

(defstate db
  :start (mg/get-db mongodb-connection (config/get-mongo-db)))

(defn get-article-by-id
  "Gets an article from database by id"
  [^String id]
  (log/debug (format "Getting article with id %s from database" id))
  (mc/find-map-by-id db (config/get-mongo-articles-collection) id article-load-fields))

(defn get-articles-by-ids
  "Gets an article from database by id"
  [ids]
  (log/debug (format "Getting articles with ids %s from database" ids))
  (mc/find-maps db (config/get-mongo-articles-collection) {:_id {$in ids}} article-load-fields))

(defn update-article
  "Updates an article"
  [^String id update-map]
  (log/debug (format "Updating article %s with data %s" id update-map))
  (mc/update-by-id db (config/get-mongo-articles-collection) id {$set update-map}))

(comment
  (mount.core/start)
  (mc/count db "articles")
  (get-article-by-id "d1911471-687a-408b-9d44-c77ca4599c28")
  (get-articles-by-ids ["d1911471-687a-408b-9d44-c77ca4599c28" "b6a939c1-e62d-49fd-8ad3-82fed2a8ac35"])
  (update-article "b6a939c1-e62d-49fd-8ad3-82fed2a8ac35" {:summary "HELLO M8!"}))