# amodeus-api

Middleware (API) for Alternative MODEUS app for UTMN Students.

> [!IMPORTANT]
> This app is no longer supported or developed as I'm no longer studying at University of Tyumen
> (UTMN). The repository may be deleted at any time without prior notice, but not earlier
> than 2027-01-01. If you need the code in this repo, consider forking, cloning and/or archiving
> the contents.

**DISCLAIMER:** THIS APP IS WORK IN PROGRESS, EVERYTHING CAN CHANGE OR BREAK ANY TIME. USE IT AT
YOUR OWN RISK.

## Alternative project:
[Calendar with Modeus, Netology and **LMS** integrations](https://github.com/depocoder/YetAnotherCalendar)

## Features

- Get timetable of any user without logging in
- Search for any user by their name or UUID
- Small and self-descriptive responses
- Built-in Swagger API docs
- Bugs

## Installation

### For self-hosting

- Make sure docker and docker-compose are installed
- Copy [example config](config.example.yaml), edit it and save as `config.yaml`
- Copy [compose](docker-compose.yaml) file to the same directory as the config
- Run `docker-compose up -d --build`
- Set up your client to use API at `http://<yourip>:8000`

### For development

- Clone this repo
- Make sure Python 3.10+ is installed
- Create virtualenv with `python -m venv .venv` and activate it
- Modify sources as needed
- Install an app with `pip install -e .[dev] .[server]`
- Copy [example config](config.example.yaml), edit it and save as `config.yaml`
- Run it with `uvicorn --reload amodeus.app:app`
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
- [ ] Other features coming soon...
