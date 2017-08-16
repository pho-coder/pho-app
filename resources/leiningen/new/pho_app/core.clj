(ns rocks.pho.apps.{{name}} 
    (:require [mount.core :as mount] 
              [clojure.tools.logging :as log]
              [clojure.tools.cli :refer [parse-opts]]
              
              [rocks.pho.apps.{{name}}.config :refer [env]])
    (:gen-class))

(def cli-options
  [["-h" "--help"]])

(defn usage [options-summary]
  (->> ["This is my program. There are many like it, but this one is mine."
        ""
        "Usage: program-name [options] action"
        ""
        "Options:"
        options-summary
        ""
        "Actions:"
        "  start   Start app"
        ""
        "Please refer to the manual page for more information."]
       (clojure.string/join \newline)))

(defn error-msg [errors]
  (str "The following errors occurred while parsing your command:\n\n"
       (clojure.string/join \newline errors)))

(defn exit [status msg]
  (println msg)
  (System/exit status))

(defn stop-app []
  (doseq [component (:stopped (mount/stop))]
    (log/info component "stopped"))
  (shutdown-agents))

(defn start-app []
  (log/info "start app!"))

(defn -main
  [& args]
  (let [{:keys [options arguments errors summary]} (parse-opts args cli-options)]
    ;; Handle help and error conditions
    (log/debug options)
    (cond
      (:help options) (exit 0 (usage summary))
      (not= (count arguments) 1) (exit 1 (usage summary))
      errors (exit 1 (error-msg errors)))

    (.addShutdownHook (Runtime/getRuntime) (Thread. stop-app))
    (doseq [component (-> args
                          mount/start-with-args
                          :started)]
      (log/info component "started"))

    ;; Execute program with options
    (case (first arguments)
      "start" (start-app)
      (exit 1 (usage summary)))))


