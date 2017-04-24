(ns wator.events
    (:require [re-frame.core :as re-frame]
              [wator.db :as db]
              [wator.map :as map]))

(def initial-num-fish 30)
(def initial-num-sharks 20)
(def initial-fish-breed 10)
(def initial-shark-breed 10)
(def initial-shark-death 10)

(re-frame/reg-event-db
 :initialize-db
 (let [m (map/seed-world map/MAX-ROW map/MAX-COL 20 20)]
   (fn  [_ _]
     db/default-db)))
