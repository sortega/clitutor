(ns clitutor.core-test
  (:use clojure.test
        midje.sweet
        clitutor.core))

(fact "alias can save you typing"
  (let [rule (alias-rule "foobar" "foo")]
    (rule ["foobar  "]) => ["foo"]
    (rule ["\nfoobar"]) => ["foo"]
    (rule ["ls -l"]) => empty?))

(fact "don't repeat a command, use !!"
  (embed-last-rule ["sudo foo" "foo"]) => ["sudo !!"])

(fact "replace bits from the last command"
  (replace-string-rule ["mv file2.txt report_2.md"
                        "mv file1.txt report_1.md"]) => ["^1^2"])
