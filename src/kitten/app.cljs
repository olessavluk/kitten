(ns kitten.app
  (:require [reagent.core :as r]))

(defonce state (r/atom {:stats {:left 1
                                :pending 1
                                :done 0}
                        :tasks (sorted-map 0 {:id 0 :title "Minium" :status "left"}
                                           1 {:id 1 :title "asd" :status "pending"})}))

(defn stats-bar [{:keys [left pending done]}]
  [:section.stats
   [:div.stat "Left: " left]
   [:div.stat "Pending: " pending]
   [:div.stat "Done: " done]])

(defn item [{:keys [id title status]}]
  [:li.order.list-item
   [:div.order-image
    [:img.order-image
     {:alt title, :src (str "http://loremflickr.com/100/100?random=" id)}]]
   [:div.order-text title]
   (case status
     "left" [:button.btn "Take"]
     "pending" [:div.status "Pending..."])])

(defn app []
  (let [{:keys [stats tasks]} @state]
    [:div
     [stats-bar stats]
     [:section.orders-list
      (cond
        (empty? tasks) [:h3 "No kittens left!"]
        :else [:ul.list
               (for [[key task] tasks]
                 ^{:key key} [item task])])]]))
