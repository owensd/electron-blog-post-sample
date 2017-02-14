(ns blog-post.landing)

(defn ^:export on-reload []
  (println "reloaded!"))

(let [elem (.getElementById js/document "app")]
  (set! (.-innerHTML elem) "Script LOADED!"))
