(ns wator.map)

(def MAX-ROW 25)
(def MAX-COL 25)
(def fish-breed 10)
(def shark-breed 10)
(def shark-death 20)

(def sea {:type :sea})

(defn make-world-row [size]
  (vec (for [x (range 0 size)]
           (assoc sea :c x))))


(defn random-fish []
  {
   :type :fish
   :age (rand-int fish-breed)})

(defn random-shark []
  {
   :type :shark
   :age (rand-int shark-death)})

(defn add-cell [world r c cell]
  (let [row (nth world r)
        new-row (assoc row c (-> cell
                                 (assoc :c c)
                                 (assoc :r r)))]
    (assoc world r new-row)))

(defn add-rand-creature [world type]
  (let [row (rand-int MAX-ROW)
        col (rand-int MAX-COL)
        creature (if (= type :fish)
                     (random-fish)
                     (random-shark))]
    (add-cell world row col creature)))

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
                           (->> (make-world-row columns)
                                (map #(assoc % :r x))
                                vec)))]
    (-> empty-world
        (add-fish start-fish)
        (add-sharks start-sharks))))

(defn get-cell [r c grid]
  "Gets the cell value at r,c"
  (-> grid
      (nth r)
      (nth c)))

(defn get-left [w r c]
  (let [new-c (dec c)]
    (if (< new-c 0)
      (get-cell r (dec MAX-COL) w)
      (get-cell r new-c w))))

(defn get-right [w r c]
  (let [new-c (inc c)]
    (if (>= new-c MAX-COL)
      (get-cell r 0 w)
      (get-cell r new-c w))))

(defn get-top [w r c]
  (let [new-r (dec r)]
    (if (< new-r 0)
      (get-cell (dec MAX-ROW) c w)
      (get-cell new-r c w))))

(defn get-bottom [w r c]
  (let [new-r (inc r)]
    (if (>= new-r MAX-ROW)
      (get-cell 0 c w)
      (get-cell new-r c w))))

(defn get-neighbors [world r c]
  [(get-left world r c)
   (get-right world r c)
   (get-top world r c)
   (get-bottom world r c)])

(defn swap-cells [map cell-1 cell-2]
  (let [{cell-1-r :r cell-1-c :c} cell-1
        {cell-2-r :r cell-2-c :c} cell-2]
    (-> map
        (add-cell cell-1-r cell-1-c cell-2)
        (add-cell cell-2-r cell-2-c cell-1))))

(defn extract-fish
  "Find all fish in the map so we can move/breed them."
  [map]
  (filter #(= (:type %) :fish)
          (mapcat identity map)))

(defn age-fish [{:keys [age] :as fish}]
  (assoc fish :age (inc age)))

(defn breed-fish [map breed? r c]
  (if breed?
    (add-cell map r c {:type :fish :age 0})
    map))

(defn fish-behavior [map
                     {:keys [age c r] :as fish}]
  (let [neighbors (get-neighbors map r c)
        empty-sea (filter #(= :sea (:type %)) neighbors)
        temp-fish (age-fish fish)
        breed? (if (< fish-breed (:age temp-fish))
                 true
                 false)
        ;; this is ugly but will fix later
        aged-fish (if breed?
                    (assoc temp-fish :age 0)
                    temp-fish)
        ]
    (if (empty? empty-sea)
      map
      (-> (swap-cells map aged-fish (rand-nth empty-sea))
          (breed-fish breed? r c)))))

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
        (recur (rest fs)
               (fish-behavior m (first fs)))))))

(defn shark-behavior [map shark]
  map)

(defn shark-move [map]
  (let [shark-locations (extract-sharks map)]
    (loop [shs shark-locations
           m map]
      (if (empty? shs)
        m
        (recur (rest shs)
               (shark-behavior m (first shs)))))))

(defn simulate [map]
 #_  (seed-world 25 25 10 10)
  (-> map
      fish-move
      #_shark-move))
