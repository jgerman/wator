(ns wator.runner
    (:require [doo.runner :refer-macros [doo-tests]]
              [wator.core-test]))

(doo-tests 'wator.core-test)
