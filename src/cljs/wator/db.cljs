(ns wator.db
  (:require [cljs.pprint :as m]
            [wator.map :as map]))




(def default-db
  {:name "framedeven"
   :map (map/seed-world map/MAX-ROW map/MAX-COL 20 20)
   :fish []
   :sharks []
   :fish-breed 10
   :shark-breed 10
   :shark-death 10
   :chronon 0})
