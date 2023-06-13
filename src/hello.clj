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








(defn -main []
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


  (println (let [processed-text (process-text-from-file "t1.txt.cx")]
             processed-text))


  ;(println (find-first-occurrence-frequency-word "0" "./src/frequency.txt"))


  ;(print (find-first-occurrence-frequency "lkjdsfjsdjk" "./src/frequency.txt"))
  )




;(let [input (mainMenu)]
;  (println "Selected option was:" input ))


;(defn mainMenu []
;  (println "Select an option:")
;  (println "1. Display list of files")
;  (println "2. Display file contents")
;  (println "3. Compress a file")
;  (println "4. Uncompress a file")
;  (println "5. Exit")
;  (println "Enter your choice:  ")
;  (let [choice (read)]
;    (case choice
;      1 (display-file-list)
;      2 (display-file-contents)
;      3 (compress-file)
;      4 (uncompress-file)
;      5 (println "Exiting...")
;      (do (println "Invalid choice. Please try again.")
;          (mainMenu)))))


