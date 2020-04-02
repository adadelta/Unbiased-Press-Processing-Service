(ns processing-service.database.mongodb-test-data)

(def test-article-1 {:_id "bbdf0d76-93a0-4ff5-aa64-821d56cfd7cc"
                     :fetchTime 1583064912
                     :day_fetched "2020-03-01"
                     :provider "The Space Gazet"
                     :author "Mary Maryson"
                     :link "https://www.somethingsomething123.com/articles/1234"
                     :category "politics"
                     :headline "Bridge does not collapse, everyone happy!"
                     :paragraphs [{:sentences
                                   ["First sentence" "Second sentence"]}
                                  {:sentences
                                   ["Third sentence" "Fourth sentence"]}]})

(def test-article-2 {:_id "36704f2c-4a14-4dd3-a5d4-1ef0d47b8c53"
                     :fetchTime 1582881212
                     :day_fetched "2020-02-28"
                     :provider "Giraffe daily"
                     :author "John Mungerleefson"
                     :link "https://www.jellyfish823479.com/articles/h7459"
                     :category "US"
                     :headline "People overcome with joy as the simluation seems to be resetting"
                     :paragraphs [{:sentences
                                   ["1. sentence" "2. sentence"]}]})