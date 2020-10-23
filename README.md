# Puget CLI

The `puget` CLI can be used to pprint EDN values with colors.

## Installation

Currently you can grab pre-built binaries for Linux and macOS from
[CircleCI](https://app.circleci.com/pipelines/github/borkdude/puget-cli?branch=main)
and for Windows from
[Appveyor](https://ci.appveyor.com/project/borkdude/puget-cli). Navigate to the
artifact tab and download the zip.

## Usage

Pipe EDN to stdin. Provide options to `puget.printer/pprint` using `--opts`.

E.g.:

``` shell
$ echo '[1 2 3]' | puget
```

This will invoke `puget.printer/pprint`. By default it uses colorized output which you can disable with:


``` shell
$ echo '[1 2 3]' | puget --opts '{:print-color false}'
```

## License

Copyright © 2020 Michiel Borkent

Distributed under the EPL License, same as Clojure. See LICENSE.
