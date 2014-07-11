(ns test.tixi.mutators.delete
  (:require-macros [cemerick.cljs.test :refer (is deftest use-fixtures)]
                   [tixi.utils :refer (b)])
  (:require [cemerick.cljs.test :as test]
            [tixi.geometry :as g :refer [Rect Point Size]]
            [tixi.mutators :as m]
            [tixi.mutators.selection :as ms]
            [tixi.mutators.delete :as md]
            [test.tixi.utils :refer [create-layer! create-sample-layer!]]
            [tixi.utils :refer [p]]
            [tixi.data :as d]))

(defn- setup [f]
  (m/reset-data!)
  (f))

(use-fixtures :each setup)

(deftest delete-selected!
  (let [id1 (create-sample-layer!)
        id2 (create-layer! (g/build-rect (Point. 5 6) (Point. 7 8)))
        id3 (create-layer! (g/build-rect (Point. 9 10) (Point. 11 12)))]
    (ms/select-layer! id1 (Point. 2 3))
    (ms/select-layer! id2 (Point. 5 6) true)
    (md/delete-selected!)
    (is (= (d/selected-ids) []))
    (is (= (d/selection-rect) nil))
    (is (= (d/current-selection) nil))
    (is (= (vec (keys (d/completed))) [id3]))))