(ns kitten.core
  (:require [reagent.core :as r]
            [kitten.app :refer [app]]))

(enable-console-print!)

(r/render-component [app] (. js/document (getElementById "app")))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
