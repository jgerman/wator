# wator

> *Somewhere, in a direction that can only be called recreational at a distance limited only by one's programming prowess, the planet Wa-Tor swimgs among
the stars. It is shaped like a torus, or doughnut, and is entirely covered with water. The two dominant denizens of Wa-Tor are sharks and fish, so called because these are the terrestrial creatures the most closely resemble. The sharks of Wa-Tor eat the fish and the fish of Wa-Tor seem always to be in plentiful supply.*

-- A.K Dewdney, The Armchair Universe

## How does Wa-Tor work?

Wa-Tor work pretty simply. It operates on a unit of time called a chronon, every chronon the simulation moves one step forward in the following ways:

* Fish move phase: fish move to a random empty space, their age is increased by 1. If their age == fish-breed their age becomes 0 and a new fish (age 0) is 
placed in their previous spot.
* Shark move phase: Sharks move to a random empty space, their age is increased by 1. If there is a fish in the postition they move to they eat it, setting starve to 0. If their age is == shark-death they die. If their starve is == shark-starve they die.

Wa-Tor is a toroidal world. Moving off of a northmost squares wraps around to the south and vice versa. Same with east and west. 

## Your Challenge

This project is a simulator of planet Wa-Tor. There are sharks, there are fish, they move, breed, and eat each other according to the rules laid out by A.K. Dewdney in his book The Armchair Universe.

Your challenge is to extend this simulation, add to it, maybe fix a few bugs along the way. 


There is no right answer here, we just want to see a little of what you're capable of. Here are some suggestions to get you started:

* can you add a new creature type? complete with adjustable parameters from the ui? what do the fish eat?
* green and black squares are boring, maybe there should be actual fish and sharks, perhaps even pointing in the direction they last moved
* could sharks be smarter? 
* could fish be smarter?
* where are the tests??
* it seems limited to have a pre-set map size
* rather than random starting locations, how about a point and click interface to add creatures to a blank map
* real oceans aren't featureless, can you add reefs? how do the creatures handle them?
* is the simulation itself efficient?
* different species of sharks with differnt behaviors? of fish?
* are there bugs? I'm sure there are bugs...

Whatever you decide to add, we should be able to build and run your submission just like below. We're looking forward to discussing your choices with you.

## Where do I start?

This project was developed with Re-Frame (https://github.com/Day8/re-frame). 

You can find a lot of documentation there, but in general, if you drill down into the source directories (src/cljs/wator) you'll find what you're looking
for. The other directory of interest is resources/public where you'll find the base html file and the css for the project.
## Development Mode

### You'll need leiningen

https://leiningen.org/

### Run application:

```
lein clean
lein figwheel dev
```

Figwheel will automatically push cljs changes to the browser.

Wait a bit, then browse to [http://localhost:3449](http://localhost:3449).

