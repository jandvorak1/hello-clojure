(ns clojurehelloworld.main-test
  #_{:clj-kondo/ignore [:unused-namespace]}
  (:require [clojure.test :as t :refer [deftest is testing]]
            [clojurehelloworld.main :as sut]))

((testing "Main"
   (deftest main-test
     (is (= 1 1)))

   (deftest ^:integration main-test-intergration
     (is (= 1 1)))))
