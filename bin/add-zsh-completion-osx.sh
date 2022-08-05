#!/usr/bin/env bash

set -euo pipefail

cp $1/$2 $1/_toolkit

ZSH_SHIM="
compopt() {
  complete \$@
}

_complete_toolkit
"

echo "$ZSH_SHIM" >> $1/_toolkit
