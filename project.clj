(defproject processing-service "0.1.0-SNAPSHOT"
  :description "Unbiased Press Processing Service"
  :url "unbiasedpress.org"
  :license {:name "Copyright - G. L. Finnbogason"
            :url ""}
  :profiles {:prod-profile  {:jvm-opts ["-Dconf=prod-config.edn"]
                             :resource-paths ["resources/prod"]}
             :dev-profile  {:jvm-opts ["-Dconf=dev-config.edn"]
                            :resource-paths ["resources/dev"]}
             :test-profile {:jvm-opts ["-Dconf=test-config.edn"]
                            :resource-paths ["resources/test"]}}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [com.novemberain/monger "3.5.0"]          ;; MongoDB client
                 [mount "0.1.16"]                          ;; State management
                 [com.novemberain/langohr "5.1.0"]         ;; RabbitMQ
                 [aero "1.1.6"]                            ;; Configuration
                 [ring/ring-json "0.5.0"]                  ;; Web-app framework
                 [ring/ring-jetty-adapter "1.8.0"]         ;; Web-app framework
                 [metosin/reitit "0.4.2"]                  ;; Router
                 [org.clojure/tools.logging "1.0.0"]       ;; Logging
                 [ch.qos.logback/logback-classic "1.2.3"]  ;; Logging
                 [com.gearswithingears/shrubbery "0.4.1"]] ;; Stub testing
  :test-selectors {:default (complement :integration)
                   :integration :integration}
  :repl-options {:init-ns processing-service.core}
  :main processing-service.core)