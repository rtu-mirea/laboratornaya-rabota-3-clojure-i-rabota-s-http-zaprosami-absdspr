(ns metrics-server.api.hardware
  (:require [metrics-server.core :refer [call-api check-required-params with-collection-format]])
  (:import (java.io File)))

(defn get-metrics-with-http-info
  "Get hardware metrics"
  []
  (call-api "/hardware" :get
            {:path-params   {}
             :header-params {}
             :query-params  {}
             :form-params   {}
             :content-types []
             :accepts       []
             :auth-names    []}))

(defn get-metrics
  "Get hardware metrics"
  []
  (:data (get-metrics-with-http-info)))


;; Метод суммирует по полю cpuTemp
(defn sumCpuTemp [hardwareMetrics]
  (reduce + (map (fn [x] (x :cpuTemp)) hardwareMetrics)))

;; Метод суммирует по полю cpuLoad
(defn sumCpuLoad [hardwareMetrics]
  (reduce + (map (fn [x] (x :cpuLoad)) hardwareMetrics)))

(defn task11 [hardwareMetrics]
  (filter (fn [x] (> (x :cpuTemp) 2)) hardwareMetrics)
  )

(defn task12 [hardwareMetrics]
  (/ (sumCpuTemp hardwareMetrics) (count hardwareMetrics)))

(defn task13 [hardwareMetrics]
  (/ (sumCpuLoad hardwareMetrics) (count hardwareMetrics)))

(defn -main []
  (println (task11 (get-metrics)))
  (println (task12 (get-metrics)))
  (println (task13 (get-metrics))))