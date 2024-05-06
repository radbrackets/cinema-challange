# High Way Cinema

## Overview

Our client operates a cinema in Wrocław, Poland, where currently all movie schedules are managed manually using pen and paper on a large board. This board displays the schedule for all movies at given times. Planner Jadwiga is responsible for efficiently scheduling each movie session (Show) to maximize the use of available space.

## Board overview

![Cinema - page 1 copy](https://user-images.githubusercontent.com/34231627/150541482-0b1e4a66-4298-4d3e-846f-c62ba1c8e37b.png)

## Dictionary

* **Show** - A movie embedded in the schedule, with a specific time and room assigned.
* **Planner** - An individual who manages the weekly schedule of shows at the cinema.
* **Movie Catalog** - A repository of movies that are available to be scheduled, although not all may necessarily be shown.
* **Cleaning slot** -  A designated time reserved for cleaning the room after each show.

## Domain requirements

We aim to assist Jadwiga in her weekly task of planning shows. The idea is to create a virtual board on which she can easily add shows.

User Stories:
- Planner Jadwiga will be able to schedule a show for a given movie at a specific time daily, from 8:00 AM to 10:00 PM.
- No two scheduled movies can occupy the same time and room. Overlapping schedules are also prohibited.
- Each show must include a maintenance slot to clean the room. Each room has a different cleaning requirement.
- Some movies may require 3D glasses.
- Not all movies are the same; for example, premieres should be scheduled after working hours, between 5:00 PM and 9:00 PM.
- There may be times when a particular room is unavailable for a certain time slot or even days.


## Assumption 
- A catalog of movies already exists, detailing requirements such as 3D glasses and duration.

### Challenge notes

* The Movie Catalog is not within the scope of this challenge, but a model will be required to address the given task.
* Consider how to manage concurrent modifications, such as when two planners try to add different movies to the same time and room.
* If you have questions about the requirements, please ask us.
* Please avoid working on a real database and UI during the assignment you will lose precious time.

#### What we care for:
- Solid domain model
- Quality of tests
- Clean code
- Proper module/context modeling

#### What we don’t care for:
- Implementing a UI
- Using database
- Covering all cases

#### What we expect from solution:
- Treat this as production code. Develop your software in the same way you would for any code intended for deployment.
- Describe your decisions clearly to aid future developers in understanding your reasoning.
- Test should be green
- Code should be hosted on a GitHub repository.
