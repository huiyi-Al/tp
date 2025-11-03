---
  layout: default.md
  title: "InSight User Guide"
  pageNav: 3
---

# InSight User Guide

> **Version:** 1.6  
> **Built for:** Creative professionals (photographers, videographers, and media freelancers)  
> **Based on:** SE-EDU AddressBook Level 3

---

## 📚 Introduction

InSight is a lightweight Client & Booking manager designed for small **photography/videography** teams that sell
creative services directly to clients and businesses.

If you’ve ever juggled details across **email, WhatsApp,
spreadsheets, and sticky notes**, you know how easy it is to miss a change request, forget a payment update, or lose
track of which package a client actually chose.

InSight consolidates your **people, bookings, packages, notes, and tags** into one clean workspace.


> **Who this is for:** Solo creators, small studios, and boutique teams in the **photography/videography market**
> looking for speed, clarity, and control—without paying with time or sanity.


![Ui.png](images/Ui.png)
## 📖 Table of Contents
1. [Quick Start](#-quick-start)
2. [Command Reference](#-command-reference)
3. [Features](#-features)
    - [Adding a Client: `add`](#adding-a-client-add)
    - [Adding a Booking: `addbooking`](#adding-a-booking-addbooking)
    - [Listing all Clients: `list`](#listing-all-clients-list)
    - [Listing all Bookings: `listbooking`](#listing-all-bookings-listbooking)
    - [Marking a Booking as Paid: `markbooking`](#marking-a-booking-as-paid-markbooking)
    - [Marking a Booking as Not Paid: `unmarkbooking`](#marking-a-booking-as-not-paid-unmarkbooking)
    - [Editing a Client: `edit`](#editing-a-client-edit)
    - [Editing a Booking: `editbooking`](#editing-a-booking-editbooking)
    - [Deleting a Client: `delete`](#deleting-a-client-delete)
    - [Deleting a Booking: `deletebooking`](#deleting-a-booking-deletebooking)
    - [Finding Clients: `find`](#finding-clients-find)
    - [Viewing a Client’s Bookings: `viewbooking`](#viewing-a-clients-bookings-viewbooking)
    - [Sorting Clients: `sort`](#sorting-clients-sort)
    - [Sorting Bookings: `sortbooking`](#sorting-bookings-sortbooking)
    - [Clearing All Data: `clear`](#clearing-all-data-clear)
    - [Help: `help`](#help-help)
    - [Exit: `exit`](#exit-exit)
4. [Glossary](#-glossary)
5. [Saving the Data](#-saving-the-data)
6. [FAQ](#-faq)
7. [Known Issues](#-known-issues)

---

## ⚡ Quick-Start

1. Ensure **Java 17** or above is installed on your computer by running:<br/>
`java --version`<br/>
If no avaliable version shown, you can install Java [here](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html).
2. Download the latest `.jar` file from [InSight Releases](https://github.com/AY2526S1-CS2103T-T08-1/tp/releases).
3. Copy the file to your preferred folder.
4. Open a terminal and run:
   ```bash
   java -jar insight.jar
   ```
5. Use commands such as `help`, `list`, or `add` to begin.

> 💡 **Tip:** Refer to the [Features](#-features) section for detailed examples of each command.

---

## 💻 Command Reference

### Client Management Commands

| Command         | Description                                                                                                   | Example                                                                        |                                                                                
|-----------------|---------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------|
| `add`           | Adds a client to InSight. <br/> Parameters: `n/NAME p/PHONE e/EMAIL s/STATUS [a/ADDRESS] [t/TAG]`             | `add n/John Doe p/98765432 e/johnd@example.com s/PROSPECT a/311, Clementi Ave` |
| `delete`        | Deletes a client. <br/> Parameters: `INDEX`                                                                   | `delete 1`                                                                     |
| `edit`          | Edits client details. <br/> Parameters: `INDEX [n/NAME] [p/PHONE] [e/EMAIL] [s/STATUS] [a/ADDRESS] [t/TAG]`   | `edit 1 p/91234567 e/johndoe@example.com`                                      |
| `list`          | Lists all clients.                                                                                            | `list`                                                                         |
| `find`          | Finds clients by name or status. <br/> Parameters: `name + [name1 name2…]` or `status + [status1 status2…]. ` | `find name alice bob .`<br/> `find status active returning`                    |
| `sort`          | Sorts clients lexicographically.                                                                              |  `sort`                                                                        |

### Booking Management Commands
| Command         | Description                                                                                           | Example                                                                         |                                                                               
|-----------------|-------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------|
| `addbooking`    | Adds a booking to InSight. <br/> Parameters: `INDEX d/DESCRIPTION dt/dd/mm/yyyy HHmm p/PACKAGE t/TAG` | `addbooking 1 d/Wedding Shoot dt/14/10/2025 1200 p/PORTRAIT t/outdoor t/summer` |
| `editbooking`   | Edits booking details. <br/> Parameters: `INDEX [d/DESCRIPTION] [dt/DATETIME] [p/PACKAGE] [t/TAG]`    | `editbooking 1 d/Wedding Shoot p/WEDDING`                                       |
| `deletebooking` | Deletes a booking. <br/> Parameters: `INDEX`                                                          | `deletebooking 1`                                                               |
| `listbooking`   | Lists all bookings <br/> Lists all bookings within InSight.                                           | `listbooking`                                                                   |
| `viewbooking`   | Displays all bookings for a specific client.<br/> Parameters: `INDEX`                                 | `viewbooking 1`                                                                 |
| `sortbooking`   | Sorts bookings by date and time.                                                                      | `sortbooking`                                                                   |
| `markbooking`   | Marks a booking as 'Paid'. <br/> Parameters: `INDEX`                                                  | `markbooking 1`                                                                 |
| `unmarkbooking` | Unmarks a booking as 'Not Paid'. <br/> Parameters: `INDEX`                                            | `unmarkbooking 1`                                                               |

### General Commands
| Command | Description                                          |
|---------|------------------------------------------------------|
| `clear` | Clears all data from InSight. <br/> Example: `clear` |
| `help`  | Displays help information. <br/> Example: `help`     |
| `exit`  | Exits InSight. <br/>  Example: `exit`                |

---

## 🧭 Features
- See the [Glossary](#-glossary) if a term is unfamiliar.
- Valid client **status** values are listed [here](#1-client-status).
- Read about **INDEX** behavior [here](#5-indexing).
- Date & time formatting is defined [here](#4-date--time-format).
- Suggested **package types** are listed [here](#2-package-types).
- Learn how to **use tags powerfully** [here](#3-tags-clients--bookings).

### Adding a Client: add

Adds a Client to InSight.

**Format:**  
`add n/NAME p/PHONE e/EMAIL s/STATUS [a/ADDRESS] [t/TAG]…`

💡 **Tip:** A client can have any number of tags (including 0).

The status field (e.g., PROSPECT, ACTIVE, RETURNING) helps classify clients by engagement level.

**Examples:**  
`add n/John Doe p/98765432 e/johnd@example.com s/PROSPECT a/311, Clementi Ave t/wedding`  
`add n/Betsy Crowe t/friend e/betsycrowe@example.com s/ACTIVE a/Newgate Prison p/1234567 t/portrait`
![add.png](images/add.png)

### Adding a booking: addbooking

Adds a booking to a specific client in InSight.

**Format:**  
`addbooking INDEX d/DESCRIPTION dt/dd/mm/yyyy HHmm p/PACKAGE [t/TAG]…`

**Notes:**
- The INDEX refers to the client’s index in the displayed list.
- Each booking is tied to a client entry.
- Tags can represent the booking type, theme, or location.

💡 **Tip:** Since this is management of a single person, only one booking can exit at a single time regardless client.

**Examples:**  
`addbooking 1 d/Wedding Shoot dt/14/10/2025 1200 p/PORTRAIT t/outdoor t/summer`  
`addbooking 2 d/Product Photoshoot dt/10/09/2025 1600 p/CORPORATE t/studio`

![addBooking.png](images/addBooking.png)

### Listing all clients: list

Shows a list of all clients stored in InSight.

**Format:**  
`list`

**Example:**  
`list`

### Listing all bookings: listbooking

Displays all bookings stored in InSight.

**Format:**  
`listbooking`

**Example:**  
`listbooking`

### Marking a booking as paid: markbooking

Marks a booking as Paid.

**Format:**  
`markbooking INDEX`

**Notes:**  
The INDEX refers to the booking number displayed in the booking list. This helps users track completed or settled payments.

**Example:**  
`markbooking 1`
![markbooking.png](images/markBooking.png)

### Unmarking a booking as unpaid: unmarkbooking

Marks a booking as Not Paid.

**Format:**  
`unmarkbooking INDEX`

**Example:**  
`unmarkbooking 2`
![unmarkbooking.png](images/markBooking.png)

### Editing a client: edit

Edits details of a client in InSight.

**Format:**  
`edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [s/STATUS] [a/ADDRESS] [t/TAG]…`

**Notes:**
- The INDEX refers to the client’s position in the displayed list.
- At least one optional field must be provided.
- Editing tags will overwrite previous tags.
- To remove all tags, type `t/` without specifying any tag.

**Examples:**  
`edit 1 p/91234567 e/johndoe@example.com`  
`edit 2 n/Betsy Crower s/RETURNING t/`
![editPerson.png](images/editPerson.png)

### Editing a booking: editbooking

Edits details of an existing booking.

**Format:**  
`editbooking INDEX d/DESCRIPTION dt/DATETIME p/PACKAGE [t/TAG]…`

**Notes:**
- The INDEX refers to the booking index in the booking list.
- You can update multiple fields at once.
- To clear all tags, type `t/` without specifying any.

**Examples:**  
`editbooking 1 d/Wedding Shoot p/WEDDING`  
`editbooking 2 dt/21/09/2025 1600 t/sunset`

### Deleting a client: delete

Deletes a client from the InSight database.

**Format:**  
`delete INDEX`

**Notes:**
- Deletes the client at the specified index from the client list.
- All related bookings remain in the system unless deleted manually.

**Examples:**  
`list` followed by `delete 2` deletes the second client in the list.  
`find Betsy` followed by `delete 1` deletes the first client in the search results.

### Deleting a booking: deletebooking

Deletes a booking record from InSight.

**Format:**  
`deletebooking INDEX`

**Example:**  
`deletebooking 1`

### Finding clients: find

Finds clients whose names or statuses match the given keywords.

> **Note**: Type in the exact name or status to find the clients

**Format:**  
`find name KEYWORD [MORE_KEYWORDS]`  
or  
`find status STATUS [MORE_STATUSES]`

**Notes:**
- Search is case-insensitive.
- The order of keywords does not matter.
- Returns any client matching one or more keywords (OR search).

**Examples:**  
`find name alice bob charlie`  
`find status active returning`
![findStatus.png](images/findStatus.png)

### Viewing all bookings for a client: viewbooking

Displays all bookings associated with a selected client.

**Format:**  
`viewbooking INDEX`

**Notes:**  
The INDEX refers to the client’s index. Use this command to view all bookings tied to a specific Client.

**Example:**  
`viewbooking 1`
![viewBooking.png](images/viewBooking.png)

### Sorting clients: sort

Sorts clients alphabetically by name.

**Format:**  
`sort`

**Example:**  
`sort`
![sort.png](images/sort.png)

### Sorting bookings: sortbooking

Sorts all bookings by date and time.

**Format:**  
`sortbooking`

**Example:**  
`sortbooking`

### Clearing all data: clear

Clears all clients and bookings from InSight.

The command clears InSight immediately, there is no confirmation button.

> ⚠️ **Warning:** This permanently deletes **all clients and bookings** from InSight. It **cannot be undone**. Back up your data (copy the `.json` data file) before running `clear`.

**Format:**  
`clear`

**Example:**  
`clear`

### Viewing help: help

Shows usage instructions and available commands.

**Format:**  
`help`

**Example:**  
`help`
![help.png](images/help.png)

### Exiting the program: exit

Closes the InSight application.

**Format:**  
`exit`

**Example:**  
`exit`

---

## 📚 Glossary

### 1. Client Status


| Status    | Meaning                                                                        | Typical Use Case                                       |
|-----------|--------------------------------------------------------------------------------|--------------------------------------------------------|
| PROSPECT  | A potential client who has expressed interest but not confirmed a booking yet. | New lead from social media, email, or inquiry form.    |
| POTENCIAL | A                                                                              |                                                        |
| ACTIVE    | A current client with one or more ongoing or upcoming bookings.                | Client with a confirmed event or ongoing project.      |
| RETURNING | A past client who has returned for additional services.                        | Repeat customer booking a new shoot or event.          |
| COMPLETED | A client whose project(s) have been completed and delivered.                   | Finished work, awaiting feedback or testimonial.       |
| INACTIVE  | A client who has not engaged for a long period or has opted out.               | Dormant contacts for archiving or marketing reference. |

### 2. Package Types

| Package Type | Description                                                                         | Example Use Case                                |
|--------------|-------------------------------------------------------------------------------------|-------------------------------------------------|
| PORTRAIT     | Individual, couple, or family portraits conducted in studio or outdoor settings.    | `addbooking 1 d/Graduation Shoot p/PORTRAIT`    |
| FAMILY       |
| EVENT        | Coverage of public/private events (birthdays, conferences).                         | `addbooking 3 d/Company Dinner p/EVENT`         |
| WEDDING      | Full wedding photography or videography coverage, often spanning multiple sessions. | `editbooking 1 p/WEDDING`                       |
| CORPORATE    |                                                                                     |                                                 |
| PRODUCT      | Product, branding, or corporate-related shoots.                                     | `addbooking 2 d/Product Launch p/COMMERCIAL`    |
| MATERNITY    | Shoots for expecting mothers/families.                                              | `addbooking 2 d/Maternity Portrait p/MATERNITY` |
| BABY         |                                                                                     |                                                 |
| GRADUATION   |                                                                                     |                                                 |
| BIRTHDAY     |                                                                                     |                                                 |
| ANNIVERSARY  | Destination shoots involving travel logistics.                                      | `addbooking 1 d/Pre-Wedding in Bali p/TRAVEL`   |
| OTHER        | Flexible or hybrid requests not fitting standard categories.                        | `addbooking 2 d/Studio & Drone Shoot p/CUSTOM`  |


### 3. Tag Keywords
Short, user-defined labels you attach to Clients and Bookings to surface specific details at a glance. 
The app does not enforce meaning — they are up to the user to define as they see fit.
#### Good practices

Keep tags short and consistent (prefer lowercase; use hyphens for multi-word tags).

Use tags for quick flags;

### 4. Date & Time Format

All bookings use the datetime format:  
`dt/dd/mm/yyyy HHmm`

**Example:**  
`dt/14/10/2025 1200` → October 14, 2025, at 12:00 PM.

### 5. Indexing

The INDEX parameter refers to the numeric position of an entry (client or booking) in the currently displayed list.

**Example:**  
`edit 1` edits the first client.  
`editbooking 2` edits the second booking shown in the list.

---

## 💾 Saving the Data

InSight automatically saves all changes to disk after each command. No manual saving is required.


---

## ❔ FAQ

**Q:** How do I transfer data to another computer?  
**A:** Copy the data file from the source folder into the target folder and restart InSight.
> 💡 **Tip:** To migrate data, copy the `.json` file from your home directory to another device.

---

## ⚠️ Known Issues

- Moving the GUI to another monitor may cause positioning errors on reopen.
- If the Help Window is minimized, running `help` again will not reopen it automatically.

---

> © 2025 InSight Team. Built upon SE-EDU AddressBook Level 3.
