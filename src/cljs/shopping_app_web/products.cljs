(ns shopping-app-web.products
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-event-db
  ::init-products
  (fn [db]
    (assoc db :products {1 {:id 1 :name "iPhone 12"}
                         2 {:id 2 :name "Samsung Galaxy S20"}
                         3 {:id 3 :name "Xiaomi Mi9T"}})))

(re-frame/dispatch-sync [::init-products])

(re-frame/reg-sub
  :products/all
  (fn [db]
    (:products db)))

(re-frame/reg-sub
  :shopping-cart/items
  (fn [db]
    (println db)
    (let [items (get-in db [:cart :items])]
      (mapv (fn [[id qty]]
              [((:products db) id) qty])
            items))))

(re-frame/reg-sub
  :shopping-cart/db
  (fn [db]
    (:cart db)))

(re-frame/reg-event-db
  :add-to-cart
  (fn [db [_ pid]]
    (update-in db [:cart :items pid] (fnil inc 0))))

(re-frame/reg-event-db
  :remove-from-cart
  (fn [db [_ pid]]
    (let [qty (get-in db [:cart :items pid])]
      (if (> qty 1)
        (update-in db [:cart :items pid] dec)
        (update-in db [:cart :items] dissoc pid)))))
