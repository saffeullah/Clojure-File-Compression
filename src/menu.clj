(ns menu
  (:gen-class)
  (:require [clojure.java.io :as io])
  (:require [compress :as compress])
  )


(declare option-1)
(declare option-2)
(declare option-3)
(declare option-4)

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
      2 (option-2)
      3 (option-3)
      4 (option-4)
      5 (println "Exiting...")
      (do (println "Invalid choice. Please try again.")
          (main-menu)))))

(defn option-1 []
  (println)
  (println "File List:")
  (compress/current-directory-files)
  (println)
  (main-menu)
  )

(defn option-2 []
  (println)
  (println "Enter the filename:")
  (let [filename (read)]
    (compress/show-file-contents filename))
  (println)
  (main-menu)
  )

(defn option-3 []
  (println "Enter the filename:")
  (let [filename (read)]
    (let [file (java.io.File. (str "./src/" filename))]
      (if (.exists file)
        (do
          (compress/save-string-to-file-after-compression (compress/compress-string (compress/get-file-contents filename)) filename)
          (println "Compression complete.")
          (main-menu))
        (do
          (println "File not found.")
          (main-menu))))))
(defn option-4 []
  (println "Enter the filename:")
  (let [filename (read)]
    (let [file (java.io.File. (str "./src/" filename))]
      (if (.exists file)
        (do
          (compress/uncompress filename)
          (println "Compression complete.")
          (main-menu))
        ;)

        (do
          (println "File not found.")
          (main-menu))))))

;Main function to run program
(defn -main []
  (main-menu)
  )