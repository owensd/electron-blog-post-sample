(ns blog-post.landing)

(let [elem (.getElementById js/document "app")]
  (set! (.-innerHTML elem) "Script LOADED!"))
