(ns dr-textile-and-mr-hiccup.core)

(declare parse)

(defmulti tag :tag)
(defmethod tag :a [t]
  (let [href (.group (:m t) 2)
        linktext (:inner t)]
    [:a {:href href} linktext]))
(defmethod tag :img [t]
  (let [href (.group (:m t) 2)]
    [:img {:href href}]))
(defmethod tag :default [t] 
  (into [(:tag t)] (parse (:inner t))))

(def tags
  [{:tag :strong :rexp #"\*(.+?)\*"}
   {:tag :em     :rexp #"_(.+?)_"}
   {:tag :a      :rexp #"\"(.+?)\":((http|https)://[^\s\\]+)"}
   {:tag :img    :rexp #"\"\!(imageurl)\!\":((http|https)://[^\s\\]+)"}])

(defn- find-next-token
  [string hsh]
  (if-not (nil? string)
    (let [{tag :tag rexp :rexp} hsh
          m (re-matcher rexp string)
          found? (.find m)]
      (if found?
        (merge hsh
         {:offset (.start m)
         :before (.substring string 0 (.start m)) 
         :inner (.group m 1)
         :m m
         :after (.substring string (.end m) (.length string))})))))

(defn- next-match
  "searches for the next matching textile expression"
  [string]
  (let [hits (remove nil? (map #(find-next-token string %) tags))]
    (if-not (empty? hits)
      (apply min-key #(:offset %) hits))))
        
(defn parse
  "Converts source string to hiccup"
  ([string]
   (parse string []))
  ([string data] 
     (let [token-match (next-match string)]
       (if token-match 
         (parse (:after token-match) (conj data (:before token-match)) token-match)
       (apply list (conj data string)))))
  ([string data token-match]
   (parse string (conj data (tag token-match)))))
                       
