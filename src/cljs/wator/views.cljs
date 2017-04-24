(ns wator.views
  (:require [re-frame.core :as re-frame]
            [wator.map :as map]))


(defn render-map [map]
  [:div#map_grid
   (for [r map]
     [:div.map_row
      (for [c r]
        [:div.sea_cell
         (cond
               (= (:type c) :fish) [:div.fish_cell]
               (= (:type c) :shark) [:div.shark_cell])])])])

(defn dispatch-timer-event
  []
  (re-frame/dispatch [:chronon]))

(re-frame/reg-event-db
 :chronon
 (fn [db [_ _]]
   (let [new-map (map/simulate (:map db))]
     (assoc db :map new-map))))

(re-frame/reg-sub
 :chronon
 (fn [db _]
   (-> db
       :chronon)))

(defonce do-timer (js/setInterval dispatch-timer-event 1000))

(defn main-panel []
  (let [map (re-frame/subscribe [:map])]
    (render-map @map)))
