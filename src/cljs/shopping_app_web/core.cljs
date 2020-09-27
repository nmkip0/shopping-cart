(ns shopping-app-web.core
  (:require
   [reagent.dom :as rdom]
   [re-frame.core :as re-frame]
   [shopping-app-web.events :as events]
   [shopping-app-web.routes :as routes]
   [shopping-app-web.views :as views]
   [shopping-app-web.config :as config]
   ))


(defn dev-setup []
  (when config/debug?
    (println "dev mode")))

(defn ^:dev/after-load mount-root []
  (re-frame/clear-subscription-cache!)
  (let [root-el (.getElementById js/document "app")]
    (rdom/unmount-component-at-node root-el)
    (rdom/render [views/main-panel] root-el)))

(defn init []
  (routes/app-routes)
  (re-frame/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (mount-root))
