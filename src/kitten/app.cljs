(ns kitten.app
  (:require [reagent.core :as r]))

(defn app []
  [:div
   [:section.stats
    [:div.stat "Left: 1"]
    [:div.stat "Pending: 1"]
    [:div.stat "Done: 0"]]
   [:section.orders-list
    [:ul.list
     [:li.order.list-item
      [:div.order-image
       [:img.order-image
        {:alt "", :src "http://loremflickr.com/100/100"}]]
      [:div.order-text "Mnium"]
      [:button.btn "Take"]]
     [:li.order.list-item
      [:div.order-image
       [:img.order-image
        {:alt "", :src "http://loremflickr.com/100/100"}]]
      [:div.order-text "Mnium"]
      [:div.status "Pending..."]]]]])
