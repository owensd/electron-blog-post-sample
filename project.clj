(defproject
 blog-post "0.1.0"

 :description "Test configuration"
 :url "http://owensd.io"
 :license {:name "MIT"}
 :dependencies [[org.clojure/clojure "1.8.0"]
                [org.clojure/clojurescript "1.9.456"]]
 :plugins [[lein-cljsbuild "1.1.5"]
           [lein-shell "0.5.0"]]
 :aliases {"electron-main" ["do"
                            ["shell" "grunt" "generate-manifest"]
                            ["cljsbuild" "once" "main"]
                            ["shell" "grunt" "generate-mainjs"]
                            ["shell" "grunt" "symlink"]]
           "electron-ui" ["do"
                          ["cljsbuild" "once" "ui"]]
           "electron" ["do"
                       ["shell" "grunt" "generate-manifest"]
                       ["cljsbuild" "once" "main"]
                       ["shell" "grunt" "generate-mainjs"]
                       ["shell" "grunt" "symlink"]
                       ["cljsbuild" "once" "ui"]]
           "electron-package" ["shell" "./node_modules/electron-packager/cli.js" ".out/app" "--out=.dist"]
           "electron-clean" ["shell" "rm" "-rf" ".out" ".tmp" ".dist"]}

 :cljsbuild {:builds {:main {:source-paths ["app/src"]
                             :incremental true
                             :assert true
                             :compiler {:output-to ".out/app/electron-host.js"
                                        :output-dir ".tmp/app"
                                        :warnings true
                                        :elide-asserts true
                                        :target :nodejs
                                        :optimizations :simple
                                        :pretty-print true
                                        :output-wrapper true}}
                      :ui {:source-paths ["ui/src"]
                           :incremental true
                           :assert true
                           :compiler {:output-to ".out/app/ui.js"
                                      :output-dir ".out/app/lib/ui"
                                      :warnings true
                                      :elide-asserts true
                                      :optimizations :none
                                      :pretty-print true
                                      :output-wrapper true}}}})
