(ns wator.map)

(def MAX-ROW 25)
(def MAX-COL 25)
(def fish-breed 10)
(def shark-breed 10)
(def shark-death 20)

(defn make-world-row [size]
  (vec (for [x (range 0 size)]
           nil)))


(defn random-fish []
  {
   :type :fish
   :age (rand-int fish-breed)})

(defn random-shark []
  {
   :type :shark
   :age (rand-int shark-death)})

(defn add-creature [world r c creature]
  (let [row (nth world r)
        new-row (assoc row c (-> creature
                                 (assoc :c c)
                                 (assoc :r r)))]
    (assoc world r new-row)))

(defn add-rand-creature [world type]
  (let [row (rand-int MAX-ROW)
        col (rand-int MAX-COL)
        creature (if (= type :fish)
                     (random-fish)
                     (random-shark))]
    (add-creature world row col creature)))

(defn add-fish [world num-fish]
  "Create num-fish random fish.
   In case of collsion just overwrite what's there"
  (loop [w world
         x num-fish]
    (if (< x 1)
      w
      (recur (add-rand-creature w :fish)
             (dec x)))))

(defn add-sharks [world num-sharks]
  "Create num-sharks random sharks.
   In case of collsion just overwrite what's there"
  (loop [w world
         x num-sharks]
    (if (< x 1)
      w
      (recur (add-rand-creature w :shark)
             (dec x)))))

(defn seed-world [rows columns start-fish start-sharks]
  (let [empty-world (vec (for [x (range 0 rows)]
                           (make-world-row columns)))]
    (-> empty-world
        (add-fish start-fish)
        (add-sharks start-sharks))))

(defn get-cell [r c grid]
  "Gets the cell value at r,c"
  (-> grid
      (nth r)
      (nth c)))

(defn get-left [r c]
  (let [new-c (dec c)]
    (if (< new-c 0)
      [r MAX-COL]
      [r new-c])))

(defn get-right [r c]
  (let [new-c (inc c)]
    (if (> new-c MAX-COL)
      [r 0]
      [r new-c])))

(defn get-top [r c]
  (let [new-r (dec r)]
    (if (< new-r 0)
      [MAX-ROW c]
      [new-r c])))

(defn get-bottom [r c]
  (let [new-r (inc r)]
    (if (> new-r MAX-ROW)
      [0 c]
      [new-r c])))

(defn get-neighbors [r c]
  [(get-left r c)
   (get-right r c)
   (get-top r c)
   (get-bottom r c)])

(defn extract-fish
  "Find all fish in the map so we can move/breed them."
  [map]
  (filter #((= (:type %) :fish))
          (mapcat identity map)))

(defn fish-behavior [map
                     {:keys [age c r]}]
  (let [neighbors (get-neighbors r c)]
    map)) ;here's where I left off, lets use :type sea instead of nil for sea cells and keep the location in them so we can save a lookup 

(defn extract-sharks
  "Find all the sharks in the map so we can move/breed them"
  [map]
  (filter #((= (:type %) :fish))
          (mapcat identity map)))

(defn fish-move [map]
  "Moves and breeds all the fish in the map, returns the updated map"
  (let [fish-locations (extract-fish map)]
    (loop [fs fish-locations ;I think I'm going to shuffle these arrayss to start with a random fish every time
           m  map]
      (if (empty? fs)
        m
        (recur (rest fish-locations)
               (fish-behavior m (first fish-locations)))))))

(defn simulate [map]
  (-> map
      fish-move))
