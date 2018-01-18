# Requirements

Below we track our progress on requirements split into three categories.

## Non-functional

- [x] It must work: if one cannot play, it doesn't work.
- [ ] 60% unit test code coverage (lines).
- [ ] Code quality – non-OO code is tolerated in little amounts.
- [x] Project mantra followed (Git, Maven, test cases, etc.).
- [x] Java FX simple GUI
- [x] Network game, client-server architecture
- [x] Both players are human players

## Functional

- [ ] One game only
_Client: game hung-ups at the end seemingly (you can close, but no message)_
- [ ] 10x10 board
_Client: with some strange scaling problems_
- [x] Fleet consists of: 4-mast ship, 2 3-mast ships, 3 2-mast ships and 4 1-mast ships.
- [x] Winner has ships remaining while loser has none.
- [ ] Game messages have configurable target: console (System.out, System.err) or logs or external printer.
- [x] We are bi-lingual: Polish and English are fine. In future we want to add more languages: messages are to be read from a file for chosen language. Choosing the language depends on configuration variable.

## Code Quality & Team Setup

- [x] Holy master - everything on master is holy, this is what is being checked by customer
- [x] CI server - before anything gets pulled into master, it is integrated with master by CI server, it runs tests, checks, etc.
- [x] Reviewers - pull-requests to master that are handled by CI server are then reviewed internally by teammates
- [x] Outside reviewers - once team says yes, outsiders come in (each team chooses external reviewers). Two external reviewers need to say "code is OK" before it can be pulled to master.
- [x] Jenkins (or equivalent) is used as CI server 
_Client: equivalent_
- [x] Maven is used by Jenkins and team
- [x] Maven has Findbugs, JaCoCo and Checkstyle integration
- [ ] Dependency convergence must be 100%, verified with maven-enforcer plugin 
_Client: what honesty!_
- [ ] All dependencies that are NOT newest are recorded (along with reason why) in the dependencies
_Client: (log4j, lombok)_
- [ ] Sonar is used to keep quality gates (just internally is fine).
- [ ] Acceptance tests are welcome, one per feature is required
- [x] Documentation should be provided, explaining program architecture (diagram is necessary, CRC diagrams are most welcome)

## Features

- [x] Drawing the boards for a player (fleet board has player's fleet and where opponent shot, "seen" board has where player fired and what he has shot).
- [ ] Placing the fleet - diagonal placing is disallowed, only horizontal and vertical. Humans can place ships but they can also choose to randomize placement. Ships cannot touch (no adjacent field to a ship can have a ship). *! Dropped* Ships can be partially vertical and partially horizontal, if they have the length. _Client: ship-broken - no, BUG in random after placing (or during), doubles the fleet_
- [ ] Firing the shot - choose a place, shoot. If you hit, you repeat the shot. You can repeat as many times as you hit. _Client: no info_
- [ ] Hitting the ship - hit happens when place chosen has enemy ship. Mark this part of ship as hit, ask for another shot. One can repeat the shot into already hit (or even sunken) ship, but this doesn't give the right to another shot. _Client: no info on miss or repeated place_
- [ ] Missing the ship - misses are marked on "seen" board. One can shoot twice in the same place if it's a miss. _Client: no info_
- [ ] *! Dropped* Sinking the ship - if all masts of a ship are hit, ship sinks. Once the ship has sunk, mark all adjacent fields as "missed", since none of them can have a ship anyway.
- [x] Sinking last ship, that is, winning. _Client: V but scaling and sizing make it unseen what is being said (whether you won or lost)_
- [ ] Nuke - thrice per game player chooses a 3x3 area and "nukes" it, that is, all ships within take damage as if shot. This is done in addition to normal shot. Only 4-mast ship has nukes, so once they are sunk, nukes cannot be used.         X