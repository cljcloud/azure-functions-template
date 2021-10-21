(ns leiningen.new.azure-functions
  (:require [leiningen.new.templates :as tmpl]
            [leiningen.core.main :as main]))

(def render (tmpl/renderer "azure_functions"))

(defn azure-functions
  "Scaffold new project."
  [name]
  (let [data {:name name
              :namespace (tmpl/sanitize-ns name)
              :path (tmpl/name-to-path name)}]
    (main/info "Generating fresh 'lein new' com.cljcloud/azure-functions project.")
    (tmpl/->files data
                  ["README.md" (render "README.md" data)]
                  [".gitignore" (render "gitignore" data)]
                  ["example.profiles.clj" (render "example.profiles.clj" data)]
                  ["externs/azure.txt" (render "externs/azure.txt" data)]

                  ["resources/host.tmpl.json" (render "resources/host.tmpl.json" data)]
                  ["resources/local.settings.tmpl.json" (render "resources/local.settings.tmpl.json" data)]
                  ["resources/proxies.tmpl.json" (render "resources/proxies.tmpl.json" data)]
                  
                  ["project.clj" (render "project.clj" data)]

                  ["src/{{path}}/api.cljs" (render "src/api.cljs" data)]
                  ["src/{{path}}/cljcloud/azure.cljc" (render "src/cljcloud/azure.cljc" data)]

                  ["test/{{path}}/example_test.cljs" (render "test/example_test.cljs" data)]
                  )
    (main/info "Done.")))
