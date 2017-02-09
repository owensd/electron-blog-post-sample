(defproject
 blog-post "0.1.0"

 :description "Test configuration"
 :url "http://owensd.io"
 :license {:name "MIT"}
 :dependencies [[org.clojure/clojure "1.8.0"]
                [org.clojure/clojurescript "1.9.456"]]
 :plugins [[lein-cljsbuild "1.1.5"]
           [lein-shell "0.5.0"]]

 ;; Configuration variables used by our own build processes.
 :electron-version "1.5.0"
 :electron-packager-version "^8.5.1"

 :aliases
 {;; Each of the aliases expose the commands available for working with the
  ;; project is an easier manner as each of the build steps has many prereqs.
  "electron-init" ["do"
                   ["shell" "scripts/setup.sh"
                    :project/name :project/description
                    :project/version :project/url
                    :project/electron-version
                    :project/electron-packager-version]
                   ["shell" "npm" "install"]]

  "electron-dev" ["do"
                  ["cljsbuild" "once" "main-dev"]
                  ["cljsbuild" "once" "ui-dev"]
                  ["shell" "grunt" "generate-manifest" "--target=.out/dev/app"]
                  ["shell" "grunt" "copy-file" "--source=./app/hoist/main-dev.js" "--target=.out/dev/app/main.js"]
                  ["shell" "grunt" "copy-file" "--source=./ui/hoist/index-dev.html" "--target=.out/dev/app/index.html"]
                  ["shell" "grunt" "symlink" "--source=ui/public" "--target=.out/dev/app/public"]]
  "electron-prod" ["do"
                   ["cljsbuild" "once" "main-prod"]
                   ["cljsbuild" "once" "ui-prod"]
                   ["shell" "grunt" "generate-manifest" "--target=.out/prod/app"]
                   ["shell" "grunt" "copy-file" "--source=./app/hoist/main-prod.js" "--target=.out/prod/app/main.js"]
                   ["shell" "grunt" "copy-file" "--source=./ui/hoist/index-prod.html" "--target=.out/prod/app/index.html"]
                   ["shell" "grunt" "symlink" "--source=ui/public" "--target=.out/prod/app/public"]]
  "electron-dist" ["shell" "scripts/package.sh"]}

 :hooks [leiningen.cljsbuild]
 :clean-targets [".out" ".tmp" ".dist"]

 :cljsbuild
 {:builds
  {;; The developement profiles contain no optimizations.
   ;; NOTE!! When optimizations are set to :none, then the :output-dir *must*
   ;; also be point to the `.out` location as these files will be referrenced
   ;; by the output file.
   :main-dev
   {:source-paths ["app/src"]
    :compiler {:output-to ".out/dev/app/goog/electron-deps.js"
               :output-dir ".out/dev/app"

               :target :nodejs
               :optimizations :none}}
   :ui-dev
   {:source-paths ["ui/src"]
    :compiler {:output-to ".out/dev/app/ui.js"
               :output-dir ".out/dev/app/lib-ui"
               :optimizations :none}}

   ;; The production profiles contain full optimizations.
   ;; NOTE!! When optimizations are set to something other than :none, then it
   ;; is safe to output the build collateral outside of the target location.
   :main-prod
   {:source-paths ["app/src"]
    :compiler {:output-to ".out/prod/app/electron/host.js"
               :output-dir ".tmp/prod/app"
               :target :nodejs
               ;; :simple is preferred so any externs do not need to be created.
               ;; Also, there is no real performance difference here.
               :optimizations :simple}}
   :ui-prod
   {:source-paths ["ui/src"]
    :compiler {:output-to ".out/prod/app/ui.js"
               :output-dir ".tmp/prod/ui"
               :optimizations :simple}}}})
