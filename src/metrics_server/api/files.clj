(ns metrics-server.api.files
  (:require [metrics-server.core :refer [call-api check-required-params with-collection-format]])
  (:import (java.io File)))

(defn get-files-with-http-info
  "Get files in directory on server"
  []
  (call-api "/files" :get
            {:path-params   {}
             :header-params {}
             :query-params  {}
             :form-params   {}
             :content-types []
             :accepts       []
             :auth-names    []}))

(defn get-files
  "Get files in directory on server"
  []
  (:data (get-files-with-http-info)))

;; Метод убирает все директории
(defn task21 [files]
  (filter (fn [x] (false? (x :directory))) files)
  )

;; Метод убирает все неисполняемые файлы
(defn task22 [files]
  (filter (fn [x] (true? (x :executable))) files))


;; Метод изменяет расширение файла с .conf на .cfg
(defn changeExtension [filename]
  (clojure.string/replace filename #".conf" ".cfg" ))

;; Метод изменяет расширения файлов с .conf на .cfg
(defn task23 [files]
  (map (fn [file]
         {
          :name (changeExtension (file :name))
          :size (file :size)
          :changed (file :changed)
          :directory (file :directory)
          :executable (file :executable)
          })))

;; Метод считает средний размер файла не директории
(defn task24 [files]
  (/
    (reduce + (map (fn [file] (file :size)) (filter (fn [x] (false?(x :directory))) files)))
    (count (filter (fn [x] (false?(x :directory))) files))
    )
  )

(defn -main []
  (println (task21 (get-files)))
  (println (task22 (get-files)))
  (println (task23 (get-files)))
  (println (task24 (get-files))))