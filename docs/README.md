# Tika User Guide

---

## Introduction

**Tika** is your personal task manager. Just like Tika the elephant, using this app will help you remember everything! Tap into Tika's **big memory** (and yes, that's not the only thing big about her 😉) to keep your tasks organized effortlessly.

![Product Screenshot](docs/Ui.png)

With Tika, you can manage **three types of tasks**: simple ToDos, Deadlines, and Events.

---
## Quick Start

Follow these steps to get Tika running on your computer:

1. **Ensure you have Java 17 or above installed.**
    - **Mac users:** Make sure you have the exact JDK version required.

2. **Download the latest `.jar` file** from [here](https://github.com/Jaepple/ip/releases/tag/A-Release).  
   *(Replace `#` with the actual download link.)*

3. **Copy the `.jar` file** to the folder you want to use as the home folder for Tika.

4. **Open a terminal** and navigate (`cd`) to the folder containing the `.jar` file.

5. **Run the application** using the following command:

```bash
java -jar tika.jar
```
---

## 1. Simple ToDo Tasks

A **ToDo** is a basic task you want to keep track of without any specific date or time.

**Format:**

`todo <description>`


**Example:**

todo walk my dog


**Outcome:**  
The task is added to your task list and stored in the following format:

T | 0 | walk my dog

- `T` → Task type (ToDo)
- `0` → Status (0 = not done, 1 = done)
- `walk my dog` → Task description

---

## 2. Adding Deadlines

A **Deadline** is a task that needs to be completed by a specific date and time.

**Format:**

`deadline <description> /by yyyy-MM-dd HHmm`


**Example:**

deadline submit report /by 2026-02-25 2359


**Outcome:**  
The deadline task is added to your task list with the specified due date:


D | 0 | submit report | 2026-02-25 23:59

And is displayed in the list as:

[ D ][ 0 ] submit report (by: Feb 25 2026 11:59pm)

- `D` → Task type (Deadline)
- `0` → Status (not done)
- `submit report` → Task description
- `2026-02-25 23:59` → Due date and time

---

## 3. Event Tasks

An **Event** is a task that occurs during a specific time range.

**Format:**

`event <description> /from <start> /to <end>`


**Example:**

event team meeting /from 4pm /to 8pm


**Outcome:**  
The event task is added to your list with its duration:

E | 0 | team meeting | 4pm | 8pm

And is displayed in the list as:

[E][ ] team meeting (from: 4pm to: 8pm)

- `E` → Task type (Event)
- `0` → Status (not done)
- `team meeting` → Event description
- `14:00 – 15:00` → Event duration

---

## 4. Marking and Unmarking Tasks

You can **mark** a task as done or **unmark** it if it’s incomplete.

**Format:**

`mark <task_index>`\
`unmark <task_index>`

**Example:**

mark 2\
unmark 2


**Outcome:**
- The task at the given index in your list is updated to reflect its completion status.
- Marked tasks will show a `1` in the status field, unmarked tasks show `0`.
- In the list marked tasks will show an `X` while unmarked tasks will have an empty space.

---
## 5. Deleting Tasks

You can **delete** tasks from your task list when they are no longer needed.

**Format:**

`delete <task_index>`

**Example:**

delete 2

**Outcome:**
- The task at the given index in your list is removed permanently.
- The remaining tasks are automatically re-indexed.
- The deleted task will no longer appear in your task list.
---
## 5. Exiting Tika

You can exit the Tika application when you are done managing your tasks.

**Format:**

`bye`


**Outcome:**
- The application will close gracefully.
- The GUI window will disappear, ending your Tika session.
---
## Command Summary
| Command | Format | Description | Example |
|---------|--------|-------------|---------|
| Add ToDo | `todo <description>` | Adds a simple task without a date or time | `todo walk my dog` |
| Add Deadline | `deadline <description> /by yyyy-MM-dd HHmm` | Adds a task that must be completed by a specific date and time | `deadline submit report /by 2026-02-25 2359` |
| Add Event | `event <description> /from <start> /to <end>` | Adds a task that occurs during a specific time range | `event team meeting /from 2026-02-26 1400 /to 2026-02-26 1500` |
| Mark Task | `mark <task_index>` | Marks a task as done | `mark 2` |
| Unmark Task | `unmark <task_index>` | Marks a task as not done | `unmark 2` |
| Delete Task | `delete <task_index>` | Deletes a task from the list | `delete 3` |
| Exit Tika | `bye` | Exits the application | `bye` |