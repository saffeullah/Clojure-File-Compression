(ns compress
  (:require [clojure.java.io :as io]))


(defn list-files [dir]
  (let [files (io/file dir)]
    (filter #(.isFile %) (file-seq files))))

(defn current-directory-files []
  (let [files (list-files "./src")]
    (doseq [file files]
      (println (.getName file)))))


(defn show-file-contents [filename]
  (let [updated-filename (str "./src/" filename)
        file (clojure.java.io/file updated-filename)]
    (if (.exists file)
      (with-open [reader (io/reader updated-filename)]
        (println "File Contents:")
        (doseq [line (line-seq reader)]
          (println line)))
      (println (str "File '" filename "' does not exist.")))))





(defn get-file-contents [filename]
  (let [updated-filename (str "./src/" filename)]
    (with-open [reader (io/reader updated-filename)]
      (let [content (slurp reader)]
        content))))


(defn find-first-occurrence-frequency [word filename]
  (with-open [reader (io/reader filename)]
    (let [content (slurp reader)
          words (clojure.string/split content #"\s+")
          index (.indexOf (map clojure.string/lower-case words) (clojure.string/lower-case word))
          unique-words (take-while #(not= (clojure.string/lower-case word) (clojure.string/lower-case %)) words)]
      (if (and index (>= index 0))
        (count (distinct unique-words))
        word)

      )
    )
  )





(defn compress-string [input-string]
  (let [words (clojure.string/split input-string #"\b")
        processed-words (map #(if (re-matches #"\d+" %)
                                (str "@" % "@") ; If the word is a number, surround it with @ symbols
                                (let [processed-word (find-first-occurrence-frequency (clojure.string/replace % #"\W" "") "./src/frequency.txt")]
                                  (str processed-word (clojure.string/join (re-seq #"\W" %))))) words)]
    (clojure.string/join " " processed-words)))


(defn save-string-to-file-after-compression [content filename]
  (  let [updated-filename (str "./src/" filename ".ct")]
    (spit updated-filename content)
    ))



(defn find-word-by-frequency [frequency filename]
  (with-open [reader (io/reader filename)]
    (let [content (slurp reader)
          words (clojure.string/split content #"\s+")
          unique-words (distinct words)
          word (nth unique-words frequency)]
      (if word
        word
        ""))))



(defn process-text-from-file [filename]
  (  let [updated-filename (str "./src/" filename)]
    (with-open [reader (io/reader updated-filename)]
      (let [content (slurp reader)
            words (clojure.string/split content #"\s+")]
        (clojure.string/join " " (map #(if (re-matches #"^\d+$" %)
                                         (if (re-matches #"^@\d+@" %)
                                           (str (subs % 1 (dec (count %))))
                                           (find-word-by-frequency (Integer/parseInt %) "./src/frequency.txt"))
                                         %) words))))))



(defn find-word-by-frequency [frequency filename]
  (with-open [reader (io/reader filename)]
    (let [content (slurp reader)
          words (clojure.string/split content #"\s+")
          unique-words (distinct words)
          word (nth unique-words frequency)]
      (if word
        word
        ""))))


;format string

(defn remove-at-symbols [s]
  (clojure.string/replace s #"@(.*?)@" "$1"))

(defn capitalize-first-letter [s]
  (if (empty? s)
    ""
    (let [words (clojure.string/split s #"\s+")]
      (clojure.string/join " "
                           (map-indexed
                             (fn [i word]
                               (if (or (zero? i)
                                       (and (= (subs (nth words (dec i)) (dec (count (nth words (dec i))))) ".")
                                            (not (empty? word))))
                                 (clojure.string/capitalize word)
                                 word))
                             words)))))

(defn remove-spaces-before-punctuation [text]
  (let [pattern #"\s+([.,!?])"]
    (clojure.string/replace text pattern "$1")))

(defn remove-space-after-brackets [text]
  (clojure.string/replace text #"\(\s|\[\s" #(str (first %))))

(defn remove-space-before-brackets [text]
  (clojure.string/replace text #"\s\)|\s\]" #(str (second %))))

(defn remove-space-after-symbols [s]
  (clojure.string/replace s #"(?<=@|\$)\s+" ""))


(defn uncompress [filename]
  (println
    (let [processed-text (process-text-from-file filename)]
      (-> processed-text
          (capitalize-first-letter)
          (remove-at-symbols)
          (remove-spaces-before-punctuation)
          (remove-space-after-brackets)
          (remove-space-before-brackets)
          (remove-space-after-symbols)
          )))

  )












