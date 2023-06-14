(ns hello
  (:gen-class)
  (:require [clojure.java.io :as io]))

(defn list-files [dir]
  (let [files (io/file dir)]
    (filter #(.isFile %) (file-seq files))))

(defn current-directory-files []
  (let [files (list-files "./src")]
    (doseq [file files]
      (println (.getName file)))))

(defn show-file-contents [filename]
  (let [updated-filename (str "./src/" filename)]
    (with-open [reader (io/reader updated-filename)]
      (doseq [line (line-seq reader)]
        (println line)))
    )
  )

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


(defn save-string-to-file [content filename]
  (  let [updated-filename (str "./src/" filename)]
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

(declare option-1)
(declare option-2)

(defn main-menu []
  (println "*** Compression Menu ***")
  (println "------------------------")
  (println "Select an option:")
  (println "1. Display list of files")
  (println "2. Display file contents")
  (println "3. Compress a file")
  (println "4. Uncompress a file")
  (println "5. Exit")
  (println "Enter your choice:  ")
  (let [choice (read)]
    (case choice
      1 (option-1)
      2 (option-1)
      3 (option-1)
      4 (option-1)
      5 (println "Exiting...")
      (do (println "Invalid choice. Please try again.")
          (main-menu)))))

(defn option-1 []
  (println)
  (println "File List:")
  (current-directory-files)
  (println)
  (main-menu)
  )

(defn option-2 []
  (println)
  (println "File List:")
  (current-directory-files)
  (println)
  (main-menu)
  )


(defn -main []

  (main-menu)


  ;(print "hello")
  ;(current-directory-files)
  ;(let [filename "abc.txt"]
  ;  (show-file-contents filename))

  ;(let [word (read-line)
  ;      filename "./src/frequency.txt"
  ;      frequency (find-first-occurrence-frequency word filename)]
  ;  (println "Frequency of first occurrence:" frequency))


    ;(let [file-string (get-file-contents "frequency.txt") ]
    ;    (println file-string)
    ;
    ;  )

  ;save-string-to-file ((compress-string "The experienced man, named Oz, representing the 416 area code - and\nhis (principal) assistant - are not in the suggested @list\n[production, development]. Is that actually the correct information? ") "t1.txt.cx")
  ;(save-string-to-file (compress-string "The experienced man, named Oz, representing the 416 area code - and\nhis (principal) assistant - are not in the suggested @list\n[production, development]. Is that actually the correct information?") "t1.txt.cx")

  ;(println (find-word-by-frequency 41 "./src/frequency.txt")  )


  ;(println (let [processed-text (process-text-from-file "t1.txt.cx")]
  ;           processed-text))

  ;(println (let [processed-text (process-text-from-file "t1.txt.cx")]
  ;           (capitalize-first-letter processed-text)
  ;
  ;           ))

  ;(println
  ;  (let [processed-text (process-text-from-file "t1.txt.cx")]
  ;    (-> processed-text
  ;        (capitalize-first-letter)
  ;        (remove-at-symbols)
  ;        (remove-spaces-before-punctuation)
  ;        (remove-space-after-brackets)
  ;        (remove-space-before-brackets)
  ;        (remove-space-after-symbols)
  ;        )))


  ;(println
  ;  "A ( cat ) is [ there ]")))


  ;(println (find-first-occurrence-frequency-word "0" "./src/frequency.txt"))


  ;(print (find-first-occurrence-frequency "lkjdsfjsdjk" "./src/frequency.txt"))
             )


;(let [input (mainMenu)]
;  (println "Selected option was:" input ))





