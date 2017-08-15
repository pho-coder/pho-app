(ns rocks.pho.apps.{{name}}.config
  (:require [mount.core :refer [defstate]]
            [cprop.source :as source]))

(defstate env :start (source/from-env))
