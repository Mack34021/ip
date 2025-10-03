# Baraleous Personal Assistant

This is the project repository for Baraleous. Given below are instructions on how to use it.

## Using Baraleous

Baraleous is designed to be used to manage numerous commitments, both those with times attached, and those without.
Baraleous' memory persists between sessions, so you can set tasks, and restart Baraleous later and they'll remember.
All times are in natural language; Baraleous will not attempt to parse to any other format than the given string.
All indexes start from 1, not 0.
## User Interface
On startup Baraleous will greet the user with the following, promoting for a command.
```
  _________Baraleous__________
  Hello! I'm Baraleous XIV!
  What can I do for you today?
  ____________User____________
```
From there, the user can input any command under the ```____User____``` line, the result of which will be printed under a new ```___Baraleous___``` section.
This pattern will remain until program termination. 
## Commands
Below are the list of commands, and their syntax and effects.

### task
Syntax: "task &lt;name>"

Description: Adds a task with no start or end time. The name of the task is given as &lt;name>. Tha task is assumed to be incomplete (i.e. unmarked).

Example:
```
  ____________User____________
task read book
  _________Baraleous__________
  Added: 'read book'
  ____________User____________
```

### deadline
Syntax: "deadline &lt;name> /by &lt;time>"

Description: Adds a task &lt;name> with a time &lt;time> that the task must be complete by.

Example:
```
  ____________User____________
deadline return book /by 27th April
  _________Baraleous__________
  Added Deadline: 'return book' due by '27th April'
  ____________User____________
```

### event
Syntax: "event &lt;name> /from &lt;time1> /to &lt;time2>"

Description: Adds a task &lt;name> that has a distinct start time &lt;time1> and end time &lt;time2> time

Example:
```
  ____________User____________
event music festival /from 11th July /to 14th July
  _________Baraleous__________
  Added Event: 'music festival' starting at '11th July' and ending at '14th July'
  ____________User____________
```

### mark
Syntax: "mark &lt;index>"

Description: Marks task &lt;index> as done, where x is the index of the task. Index is given in the "list" command.

Example:
```
  ____________User____________
mark 1
  _________Baraleous__________
  OK! Task marked complete!
  [X] read book
  ____________User____________
```
### unmark
Syntax: "unmark &lt;index>"

Description: The opposite of mark, unmarks the task

Example:
```
  ____________User____________
unmark 1
  _________Baraleous__________
  OK! Task marked as incomplete!
  [ ] read book
  ____________User____________
```
### list
Syntax: "list"

Description: Generates and prints an indexed list of all current tasks tracked by Baraleous, and their current type and status.

Return value Syntax is: "&lt;index> [&lt;task type>] [&lt;task done>] &lt;name> &lt;timings>"

Where &lt;timings> will be empty for tasks, "(by: &lt;time1>)" for deadlines, and "(from: &lt;time1> to: &lt;time2>)" for events

Example:
```
  ____________User____________
list
  _________Baraleous__________
  1.[T][ ] read book
  2.[D][X] return book (by: 27th April)
  3.[E][ ] music festival (from: 11th July to: 14th July)
  ____________User____________
```
### delete
Syntax: "delete &lt;index>"

Description: Deletes the task with index as &lt;x>

Example:
```
  ____________User____________
delete 2
  _________Baraleous__________
  Deleting task [X] return book (by: 27th April)
  ____________User____________
```
### find
Syntax: "find &lt;string>"

Description: Searches through all tasks in system for any task with a &lt;name> that contains the string.
Returns a list of all such tasks to terminal

Example:
```
  ____________User____________
find book
  _________Baraleous__________
  Search Results:
  1.[T][ ] read book
  ____________User____________
```
### bye
Syntax: "bye"

Description: Closes the program
Example:
```
  ____________User____________
bye
  _________Baraleous__________
  Goodbye!
```