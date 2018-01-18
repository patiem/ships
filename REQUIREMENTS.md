# Requirements

Below we track our progress on requirements split into three categories.

## Features

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

### Code Quality & Team Setup

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
