# Project main rules

This file defines all the rules that must be followed during the lifecycle of the project (will probably change during the lifecycle of the project).

## Commit message definition

Commit Message Format: *type*(*scope*): *short **summary*** *optional **detailed description*** --- Common Types:

• feat: New feature

• fix: Bug fix

• docs: Documentation update

• style: Code formatting (no logic change)

• refactor: Code restructuring (no behavior change)

• test: Adding/fixing tests

• chore: Maintenance tasks

 Example:
```
feat(auth): add JWT authentication middleware Implemented a new authentication middleware using JWT to handle user logins. Includes token verification and refresh logic.
```

## Usage of branches

Each member must use a new branch when starting to develop a new task. This also means that once the task is done, the member shall merge that branch into the main working branch and delete the task-specific branch.

Each task will have their Issue relate and must also mention the same issue number

Example:
```
IS01-Still-dont-now-what-to-do
```

### Branches to define work state of the project

Branches must also define what is the current state the project. The main branch must **only** have stable versions of the project.

- Example: Once we start the development stage (AKA: sprint), we must create a DEV branch and only merge it into main once the sprint has finished.

Here are the main branches that will exist in our repo

- main (master)

- dev

    - test

    - database

    - timelink-mobile

    - timelink-admin

- hot-fix

- qa

- business

### Merging branches

As for the moment only few people will be able to merge a branch into another, all the rules are still to be predefined but as for now everyone **must** create a merge request to André Martins.

