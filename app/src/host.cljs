(ns blog-post.electron
  (:require [cljs.nodejs :as nodejs]))

(def Electron (nodejs/require "electron"))
(def app (.-app Electron))
(def BrowserWindow (.-BrowserWindow Electron))
(def path (nodejs/require "path"))
(def url (nodejs/require "url"))

(def *win* (atom nil))

(def darwin? (= (.-platform nodejs/process) "darwin"))

(defn create-window []
  (reset! *win* (BrowserWindow. (clj->js {:width 800 :height 600})))

  (let [u (.format url (clj->js {:pathname (.join path
                                                  (js* "__dirname")
                                                  ".."
                                                  "index.html")
                                 :protocol "file:"
                                 :slashes true}))]
    (.loadURL @*win* u))

  (.openDevTools (.-webContents @*win*))

  (.on app "closed" (fn [] (reset! *win* nil))))

(defn -main []
  (.on app "ready" (fn [] (create-window)))

  (.on app "window-all-closed"
       (fn [] (when-not darwin? (.quit app))))

  (.on app "activate"
       (fn [] (when darwin? (create-window)))))

(nodejs/enable-util-print!)
(.log js/console "App has started!")

(set! *main-cli-fn* -main)
