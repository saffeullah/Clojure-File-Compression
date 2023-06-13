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
  (let [words (clojure.string/split input-string #"\s+")]
    ;(apply str (map #(str (find-first-occurrence-frequency % "./src/frequency.txt")) words+ " "))))
    (clojure.string/join " " (map #(str (find-first-occurrence-frequency % "./src/frequency.txt")) words))))





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
    (println (compress-string "The pink elephant is absolutely groovy") )


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


