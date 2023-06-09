(ns hello
  (:gen-class))

(defn displayFile [fileName]



  )

(defn mainMenu []
  (println "Select an option:")
  (println "1. Display list of files")
  (println "2. Display file contents")
  (println "3. Compress a file")
  (println "4. Uncompress a file")
  (println "5. Exit")
  (println "Enter your choice:  ")
  (read)
  )
(defn -main []
  ;(println "Hello, Worldss!")
    (let [input (mainMenu)]
      (println "Selected option was:" input )
      ))








