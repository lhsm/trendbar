## Trend bar service
Implements storing and search for trend bars (candlesticks https://en.wikipedia.org/wiki/Candlestick_chart)
 
## Features
- asynchronous computing trend bars
- memory storage
- day, hour or minute trend bars only
- quotes provider (test-jar)

## Usage
- import `TrendBarConfig` for Spring application
- or call `new TrendBarServiceImpl(...).init()` directly

## TODO
 - add key indexing (partitioning) to improve search
 - put into separate modules (api, test, impl)
 - disk (remote) storage