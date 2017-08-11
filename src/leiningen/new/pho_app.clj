(ns leiningen.new.pho-app
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files]]
            [leiningen.core.main :as main]))

(def render (renderer "pho-app"))

(defn pho-app
  "FIXME: write documentation"
  [name]
  (let [data {:name name
              :sanitized (name-to-path name)}]
    (main/info "Generating fresh 'lein new' pho-app project.")
    (->files data
             ["project.clj" (render "project.clj" data)]
             ["src/clj/{{sanitized}}/{{name}}.clj" (render "foo.clj" data)])))
