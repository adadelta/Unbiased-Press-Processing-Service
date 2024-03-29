(ns processing-service.config-test
  (:require [clojure.test :refer :all]
            [processing-service.config :refer :all :as config]
            [mount.core :refer :all]))

;; We run a fixture (:once) starting the configuration state only
(defn start-states [f]
  (mount.core/start #'processing-service.config/env)
  (f))
(clojure.test/use-fixtures :once start-states)

(deftest get-mongodb-host-test
  (testing "Should return mongodb host from test config"
    (is (= (config/get-mongo-host) "localhost"))))

(deftest get-mongo-port-test
  (testing "Should return mongodb port from test config"
    (is (= (config/get-mongo-port) 27017))))

(deftest get-mongodb-db-test
  (testing "Should return mongodb db from test config"
    (is (= (config/get-mongo-db) "test_unbiased"))))

(deftest get-mongodb-articles-collection-test
  (testing "Should return articles collection name from test config"
    (is (= (config/get-mongo-articles-collection) "articles"))))

(deftest get-mongodb-username-test
  (testing "Should return mongodb username from test config"
    (is (= (config/get-mongo-username) "test_user"))))

(deftest get-mongodb-auth-db-test
  (testing "Should return mongodb auth-db from test config"
    (is (= (config/get-mongo-auth-db) "test_unbiased"))))

(deftest get-mongodb-password-test
  (testing "Should return mongodb passowrd from test config"
    (is (= (config/get-mongo-password) "test_password"))))