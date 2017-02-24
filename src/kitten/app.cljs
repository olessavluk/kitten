(ns kitten.app
  (:require [reagent.core :as r]
            [kitten.gen :as gen]))

(defonce state (r/atom {:stats {:left 0
                                :pending 0
                                :done 0}
                        :tasks (sorted-map)}))

(defonce counter (r/atom 0))

(declare kitten-tick)
(defonce kitten-timer (atom (js/setTimeout #(kitten-tick) 1000))) ;; why it doesn't work without wrap in #()?

(defn add-task [title]
  (let [id (swap! counter inc)]
    (swap! state assoc-in [:tasks id] {:id id :title title :status "left"})
    (swap! state update-in [:stats :left] inc)))

(defn done-task [id]
  (swap! state update-in [:tasks] dissoc id)
  (swap! state update-in [:stats :pending] dec)
  (swap! state update-in [:stats :done] inc))

(defn take-task [id]
  (swap! state assoc-in [:tasks id :status] "pending")
  (swap! state update-in [:stats :left] dec)
  (swap! state update-in [:stats :pending] inc)
  (js/setTimeout #(done-task id) (rand-int 5000)))

(defn kitten-tick []
  (add-task (gen/kitten))
  (let [pause (rand-int 10000)]
    (prn (str "Next kitten in: " pause "ms"))
    (swap! kitten-timer #(js/setTimeout kitten-tick pause))))

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
     "left" [:button.btn {:on-click #(take-task id)} "Take"]
     "pending" [:div.status "Pending..."])])

(defn app []
  (let [{:keys [stats tasks]} @state]
    [:div
     [stats-bar stats]
     [:section.orders-list
      (if (empty? tasks)
        [:h3 "No kittens left!"]
        [:ul.list
         (for [[key task] tasks]
           ^{:key key} [item task])])]]))
