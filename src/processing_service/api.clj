(ns processing-service.api
  (:require
   [reitit.ring :as ring]
   [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
   [reitit.dev.pretty :as pretty]
   [ring.adapter.jetty :as jetty]
   [clojure.string :as str]
   [clojure.edn]
   [clojure.tools.logging :as log]))

(def ^:const OK 200)

(defmacro project-version []
  (some->> (slurp "project.clj") clojure.edn/read-string (drop 1) (take 2) (str/join " ")))

(defn ping-services
  "Pings all relevant external services and returns a status map"
  []
  (log/debug "Pinging all external services")
  {:aggregator "DOWN"
   :baas "DOWN"
   :ml-service "DOWN"
   :database "DOWN"})

(def routes
  [["/" {:get (fn [_] {:status OK :body (project-version)})}]
   ["/ping" {:name "Returns status of all relevant services"
             :get {:parameters {:path {:date inst?}}
                   :responses {OK {:body ::ping-services}}
                   :handler (fn [_]
                              {:status OK
                               :headers {"Content-Type" "application/json"}
                               :body (ping-services)})}}]])

(def router
  (ring/router routes {:exception pretty/exception}))

(def app
  (-> router
      ring/ring-handler
      wrap-json-response
      (wrap-json-body {:keywords? true})))

(defn start [port]
  (log/debug (str "Starting server on port " port))
  (jetty/run-jetty #'app {:port port :join? false})
  (log/debug (str "Server running on port " port)))

(comment
  (app {:request-method :get :uri "/"})
  (app {:request-method :get :uri "/ping"})
  (start 3000))