(defproject {{namespace}} "0.1.0-SNAPSHOT"
  :description "Serverless ClojureScript app on Azure Functions"
  :url "https://github.com/cljcloud/azure-functions-template"
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [org.clojure/core.async "1.3.618"]

                 ;; ClojureScript
                 [org.clojure/clojurescript "1.10.879"]
                 [com.google.javascript/closure-compiler-unshaded "v20210505"]
                 [thheller/shadow-cljs "2.15.12"]

                 ;; used by cljcloud
                 [metosin/jsonista "0.3.3"]
                 [environ "1.2.0"]
                 ]

  :plugins [[lein-shadow "0.4.0"]
            [lein-environ "1.2.0"]
            [lein-shell "0.5.0"]]

  :target-path "target/%s"

  :shadow-cljs {:nrepl  {:port 7002}
                :builds {:azure {:target      :azure-app
                                 ;; the order of fn definition is always abc
                                 ;; important to have wildcard routes defined last
                                 :fn-map      {:utc-now {{namespace}}.api/get-utc-now}
                                 :app-dir     "target/azure"
                                 :build-hooks [({{namespace}}.cljcloud.azure/build-hook)]
                                 :js-options  {:js-provider          :shadow
                                               :keep-native-requires true
                                               :keep-as-require      #{}}}
                         :test  {:target    :node-test
                                 :output-to "target/tests/tests.js"
                                 :autorun   true}}}

  :npm-deps []
  :npm-dev-deps [[xmlhttprequest "1.8.0"]
                 [ws "7.5.0"]
                 [source-map-support "0.5.19"]]

  :shell {:dir "target/azure"}

  :aliases {"watch"        ["shadow" "watch" "azure"]
            "azure"        ["shell" "func" "start" "--cors" "*" "--port" "8021"]
            "release:prod" ["do"
                            ["clean"]
                            ["with-profile" "prod" "shadow" "release" "azure"]
                            ["shell" "cp" "-rf" "../../node_modules" "."]]
            "test"         ["do"
                            ["shadow" "compile" "test"]
                            ["shell" "node" "../tests/tests.js"]]}
  )
