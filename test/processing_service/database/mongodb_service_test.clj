(ns processing-service.database.mongodb-service-test
  (:require [clojure.test :refer :all]
            [processing-service.database.mongodb-test-data :refer [test-article-1 test-article-2]]
            [processing-service.config :refer :all :as config]
            [processing-service.database.mongodb-service :refer :all :as mongodb-service]
            [processing-service.database.mongodb-connection]
            [monger.collection :as mc]
            [mount.core :refer :all]))

(defn- is-test-database?
  "Checks if the database being used is the test database"
  [] 
  (and (= (config/get-mongo-db) "test_unbiased")
       (= (config/get-mongo-articles-collection) "articles")
       (<= (mc/count mongodb-service/db (config/get-mongo-db)) 3)))

(defn- ^:integration setupDB
  "Inserts test data to collection"
  []
  (println "Setting up DB")
  (mc/insert-batch mongodb-service/db (config/get-mongo-articles-collection) [test-article-1 test-article-2]))

(defn- ^:integration purgeDB
  "Purges test collection"
  []
  (println "Purging DB")
  (mc/purge-many mongodb-service/db [(config/get-mongo-articles-collection)]))

(defn- ^:integration setup-and-tear-down-db
  "For each test, we insert test data into collection, and after purge the collection"
  [f]
  (if (is-test-database?)
    (do
      (setupDB)
      (f)
      (purgeDB))
    (println "Not using test database - not running test!")))

(defn ^:integration start-states 
  "Starts the needed states"
  [f]
  (mount.core/start #'processing-service.config/env
                    #'processing-service.database.mongodb-connection/mongodb-connection
                    #'processing-service.database.mongodb-service/db)
  (f))

;; Test fixtures
(clojure.test/use-fixtures :once start-states)
(clojure.test/use-fixtures :each setup-and-tear-down-db)

(deftest ^:integration fixtures-test
  (testing "Testing fixtures"
    (is (= (mc/count mongodb-service/db (config/get-mongo-articles-collection)) 2))))

(deftest ^:integration get-article-by-id-test
  (testing "Getting Article by ID"
    (is (= (mongodb-service/get-article-by-id (:_id test-article-1)) test-article-1))
    (is (= (mongodb-service/get-article-by-id (:_id test-article-2)) test-article-2))
    (is (= (mongodb-service/get-article-by-id "asdf34029u34jsfd") nil))))

(deftest ^:integration get-articles-by-ids-test
  (testing "Getting Articles by IDs"
    (is (= (mongodb-service/get-articles-by-ids [(:_id test-article-1)]) (list test-article-1)))
    (is (= (mongodb-service/get-articles-by-ids [(:_id test-article-1) (:_id test-article-2)]) (list test-article-2 test-article-1)))
    (is (= (mongodb-service/get-articles-by-ids ["asdfuih23487hafs"]) ()))))

(deftest ^:integration update-article-test
  (testing "Updating article"
    (let [new-map {:summary "This is a summary"}
          updated-article (merge test-article-1 new-map)]
      (mongodb-service/update-article (:_id test-article-1) new-map)
      (is (= (mongodb-service/get-article-by-id (:_id test-article-1)) updated-article))
      (is (= (mongodb-service/get-article-by-id (:_id test-article-2)) test-article-2)))))

(comment
  (mount.core/start))