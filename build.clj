(ns build
  (:require [clojure.tools.build.api :as b] [clojure.string :as s]))

(def build-folder "build/libs")
(def jar-content "build/classes")
(def library-name 'com.dvoraksw/clojurehelloworld)
(def version "1.0.0")
(def jar-file-name (format "%s/%s-%s.jar" build-folder (name library-name) version))
(def uber-file-name (format "%s/%s-%s-standalone.jar" build-folder (name library-name) version))

(def basis (b/create-basis {:project "deps.edn"}))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defn clean [_]
      ;start info
  (println "Executing clean...")

  (b/delete {:path build-folder})

      ;finish info
  (println "Executiion finished clean"))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defn jar [_]
      ;start info
  (println "Executing build jar...")

      ;clean leftovers
  (b/delete {:path build-folder})

      ;copy sources and resources
  (b/copy-dir {:src-dirs   ["resources"]
               :target-dir jar-content})

      ;compile sources
  (b/compile-clj {:basis     basis
                  :src-dirs  ["src"]
                  :class-dir jar-content})

      ;create pom.xml
  (b/write-pom {:class-dir jar-content
                :lib       library-name
                :version   version
                :basis     basis
                :src-dirs  ["src"]})

      ;pack a jar
  (b/jar {:class-dir jar-content
          :jar-file  jar-file-name
          :manifest  {"Built-By" (System/getProperty "user.name"), "Build-Jdk" (str (System/getProperty "java.version") " " (s/replace (System/getProperty "java.vendor") #"N/A" "Arch Linux") " " (System/getProperty "java.vm.version")), "Build-OS" (str (System/getProperty "os.name") " " (System/getProperty "os.arch") " " (System/getProperty "os.version"))}})

      ;finish info
  (println "Executiion finished build jar"))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defn uber [_]
      ;start info
  (println "Executing build uber...")

      ;clean leftovers
  (b/delete {:path build-folder})

      ;copy sources and resources
  (b/copy-dir {:src-dirs   ["resources"]
               :target-dir jar-content})

      ;compile sources
  (b/compile-clj {:basis     basis
                  :src-dirs  ["src"]
                  :class-dir jar-content})

      ;pack a uberjar
  (b/uber {:class-dir jar-content
           :uber-file uber-file-name
           :basis     basis
           :main      'clojurehelloworld.main
           :manifest  {"Built-By" (System/getProperty "user.name"), "Build-Jdk" (str (System/getProperty "java.version") " " (s/replace (System/getProperty "java.vendor") #"N/A" "Arch Linux") " " (System/getProperty "java.vm.version")), "Build-OS" (str (System/getProperty "os.name") " " (System/getProperty "os.arch") " " (System/getProperty "os.version"))}})

      ;finish info
  (println "Executiion finished build uber"))
