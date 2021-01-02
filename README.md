# Puget CLI

The `puget` CLI can be used to pprint EDN values with colors. It is based on the
[puget](https://github.com/greglook/puget) library.

## Installation

Grab the binary for your OS at [Github releases](https://github.com/borkdude/puget-cli/releases).

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
