# dr-textile-and-mr-hiccup

This is a quick hack to convert textile markup to hiccup. Currently only *strong*, _em_, !imageurl an "link" are supported (since they are the only tags I want to allow in the project I'm working on currently, but it'd be fun to support the full textile language in the future.

## Usage

    (parse "foo *bar* baz")
    ("foo " [:strong "bar"] " baz)

## License

Copyright © 2012 Joel Westerberg

Distributed under the Eclipse Public License, the same as Clojure.
