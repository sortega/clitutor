# CLI Tutor

Small script for learning shell golf, or in other words, to use some sortcuts
to write less at Bash or Zsh.

## Usage

Run from sources:

    lein run path/to/history_file

The history file is expected to be updated command-by-command (`setopt
INC_APPEND_HISTORY` for zsh). It can be execute in a side terminal or in the
same as a background task (with `&` at the end of the command).

## License

Copyleft © 2012

Distributed under the Eclipse Public License, the same as Clojure.
