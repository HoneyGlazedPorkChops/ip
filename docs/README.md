# 🌸 Iris User Guide

Iris is a simple chatbot that helps you track personal tasks, with a bit of flair and personality.

![Chatbot UI](Ui.png)
---

# 🚀 Getting Started

1. Run the application.
2. You will see:

```
Hello! I'm Iris
What can I do for you?
```

3. Type "help" to see the list of commands available.

---

# 📋 Features

## 1️⃣ Adding a ToDo

Adds a simple task without a date.

```
todo <description>
```

Example:
```
todo read book
```

---

## 2️⃣ Adding a Deadline

Adds a task with a due date.

```
deadline <description> /by <date> [time]
```

Example:
```
deadline submit report /by 2026-02-20 17:30
```

---

## 3️⃣ Adding an Event

Adds a task that happens at a specific time.

```
event <description> /from <date> [time] /to <date> [time]
```

Example:
```
event team meeting /from 2026-02-20 12:00 /to 2026-02-20 16:00
```

---

## 4️⃣ Listing Tasks

Displays all tasks.

```
list
```

---

## 5️⃣ Marking a Task as Done

Marks a task using its number.

```
mark <task number>
```

Example:
```
mark 2
```

---

## 6️⃣ Unmarking a Task

Marks a task as not done.

```
unmark <task number>
```

Example:
```
mark 4
```

---

## 7️⃣ Deleting a Task

Removes a task from the list.

```
delete <task number>
```

---

## 8️⃣ Finding Tasks

Finds tasks containing a keyword.

```
find <keyword>
```

Example:
```
find report
```

---

## 9️⃣ Exiting the Application

Closes Iris.

```
bye
```

---

# 🎵 Music

- Chatbot comes with a default music player at the top which can be paused using a button
- Only music available is [Iris - Goo Goo Dolls](https://youtu.be/NdYWuo9OFAw?si=aXo5P5vTFT5EXkeJ)

---

# 💾 Data Storage

- Tasks are automatically saved to a file.
- When you restart Iris, your tasks will still be available.
- In the case of corrupted save file, a clean slate will be used

---

# ⚠️ Notes

- Task numbers start from **1**.
- Commands are case-sensitive.
- Ensure the correct format is used for deadlines and events.
- Timing for deadlines and events are optional, it will be 00:00 by default
