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

### Non-functional