(ns dr-textile-and-mr-hiccup.core-test
  (:use clojure.test
        dr-textile-and-mr-hiccup.core))

(deftest just-text-test
  (testing "text"
    (let [source-string "Leiningen Versus the Ants"
          result '("Leiningen Versus the Ants")]
    (is (= result (parse source-string))))))

(deftest strong-test
  (testing "Strong text"
    (let [source-string "Leiningen *Versus* the Ants"
          result '("Leiningen " [:strong "Versus"] " the Ants")]
    (is (= result (parse source-string))))))

 (deftest strong-twice-test
   (testing "Strong text twice"
     (let [source-string "Leiningen *Versus* *the* Ants"
           result '("Leiningen " [:strong "Versus"] " " [:strong "the"] " Ants")]
     (is (= result (parse source-string))))))
 
 (deftest emphasis-test
   (testing "Emphasis text"
     (let [source-string "Leiningen _Versus_ the Ants"
           result '("Leiningen " [:em "Versus"] " the Ants")]
     (is (= result (parse source-string))))))

(deftest link-test
  (testing "link text"
    (let [source-string "leiningen \"versus\":http://example.com/foobar?true=false&bar=23 the ants"
          result '("leiningen " [:a {:href "http://example.com/foobar?true=false&bar=23"} "versus"] " the ants")]
    (is (= result (parse source-string))))))

 (deftest img-test
   (testing "image text"
     (let [source-string "leiningen \"!imageurl!\":http://example.com/example.gif the ants"
           result '("leiningen " [:img {:href "http://example.com/example.gif"}] " the ants")]
     (is (= result (parse source-string))))))

(deftest strong-test
  (testing "Strong text"
    (let [source-string "The *ants _ants_ ants* damn you!"
          result '("The " [:strong "ants " [:em "ants"] " ants"] " damn you!")]
    (is (= result (parse source-string))))))


