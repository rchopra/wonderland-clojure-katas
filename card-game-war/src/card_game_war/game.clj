(ns card-game-war.game)

;; feel free to use these cards or use your own data structure
(def suits [:spade :club :diamond :heart])
(def ranks [2 3 4 5 6 7 8 9 10 :jack :queen :king :ace])
(def cards
  (for [suit suits
        rank ranks]
    [suit rank]))
(def rank-values (zipmap ranks (range (count ranks))))
(def suit-values (zipmap suits (range (count suits))))

(defn suit [card] (first card))

(defn rank [card] (second card))

(defn play-round [player1-card player2-card]
  (let [rank1 (rank-values (rank player1-card))
        rank2 (rank-values (rank player2-card))
        suit1 (suit-values (suit player1-card))
        suit2 (suit-values (suit player2-card))]
    (cond
      (> rank1 rank2) [(into () [player1-card player2-card]) ()]
      (< rank1 rank2) [() (into () [player1-card player2-card])]
      (> suit1 suit2) [(into () [player1-card player2-card]) ()]
      (< suit1 suit2) [() (into () [player1-card player2-card])]
      :else [(into () [player1-card]) (into () [player2-card])])))

(defn play-game [player1-cards player2-cards]
  (cond
    (empty? player1-cards) :player2
    (empty? player2-cards) :player1
    :else (let [[player1-winnings player2-winnings] (play-round
                                                     (first player1-cards)
                                                     (first player2-cards))]
            (play-game (concat (rest player1-cards) (shuffle player1-winnings))
                       (concat (rest player2-cards) (shuffle player2-winnings))))))
