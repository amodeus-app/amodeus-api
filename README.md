# amodeus-api

Middleware (API) for Alternative MODEUS app for UTMN Students.

**DISCLAIMER:** THIS APP IS WORK IN PROGRESS, EVERYTHING CAN CHANGE OR BREAK ANY TIME. USE IT AT
YOUR OWN RISK.

## Features

- Get timetable of any user without logging in
- Search for any user by their name or UUID
- Small and self-descriptive responses
- Built-in Swagger API docs
- Bugs

## Installation

### For self-hosting

- Make sure docker and docker-compose are installed
- Copy [compose](docker-compose.yaml) file to the same directory as the config
- Run `docker-compose up -d --build`
- Set up your client to use API at `http://<yourip>:8000`

### For development

- Clone this repo
- Make sure JDK is installed
- Modify sources as needed
- Build and run with `./gradlew run`
- Set up your client to use API at `http://<yourip>:8000`

## Roadmap

- [x] Get timetable for any user
- [x] Get lesson team
- [x] Search user by name
  - [ ] More search options...
- [x] Login
  - [x] Get timetable for self
  - [ ] Get marks for self
- [ ] Monitor timetable changes
- [ ] Write tests to make sure upstream didn't break API
- [ ] Microservice architecture
- [ ] Other features coming soon...
