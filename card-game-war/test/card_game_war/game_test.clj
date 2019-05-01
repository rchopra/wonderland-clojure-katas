(ns card-game-war.game-test
  (:require [clojure.test :refer :all]
            [card-game-war.game :refer :all]))


;; fill in  tests for your game
(deftest test-play-round
  (testing "the highest rank wins the cards in the round"
    (let [card1 [:spade 8]
          card2 [:heart 3]]
    (is (= [(into () [card1 card2]) ()]
           (play-round card1 card2)))))

  (testing "queens are higher rank than jacks"
    (let [card1 [:club :jack]
          card2 [:club :queen]]
    (is (= [() (into () [card1 card2])]
           (play-round card1 card2)))))

  (testing "kings are higher rank than queens"
    (let [card1 [:club :king]
          card2 [:club :queen]]
    (is (= [(into () [card1 card2]) ()]
           (play-round card1 card2)))))

  (testing "aces are higher rank than kings"
    (let [card1 [:club :jack]
          card2 [:diamond :ace]]
    (is (= [() (into () [card1 card2])]
           (play-round card1 card2)))))

  (testing "if the ranks are equal, clubs beat spades"
    (let [card1 [:spade 3]
          card2 [:club 3]]
    (is (= [() (into () [card1 card2])]
           (play-round card1 card2)))))

  (testing "if the ranks are equal, diamonds beat clubs"
    (let [card1 [:diamond :king]
          card2 [:club :king]]
    (is (= [(into () [card1 card2]) ()]
           (play-round card1 card2)))))

  (testing "if the ranks are equal, hearts beat diamonds"
    (let [card1 [:diamond 8]
          card2 [:heart 8]]
    (is (= [() (into () [card1 card2])]
           (play-round card1 card2))))))

(deftest test-play-game
  (testing "the player loses when they run out of cards"
    (let [player1-cards cards
          player2-cards ()]
    (is (= :player1
           (play-game player1-cards player2-cards)))))

  (testing "the player who has the ace of hearts wins"
    (let [player1-cards (take 51 cards)
          player2-cards (take-last 1 cards)]
    (is (= :player2
           (play-game player1-cards player2-cards))))))
