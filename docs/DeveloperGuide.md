---
layout: default.md
title: "Developer Guide"
pageNav: 3
---

# EasyWeds Developer Guide

<!-- * Table of Contents -->
## **Table of Contents**

* [Acknowledgements](#acknowledgements)
* [Setting up, getting started](#setting-up-getting-started)
* [Design](#design)
  * [Architecture](#architecture)
  * [UI component](#ui-component)
  * [Logic component](#logic-component)
  * [Model component](#model-component)
  * [Storage component](#storage-component)
  * [Common classes](#common-classes)
* [Implementation](#implementation)
  * [[Proposed] Undo/redo feature](#proposed-undoredo-feature)
  * [[Proposed] Data archiving](#proposed-data-archiving)
  * [User Workflow Implementation](#user-workflow-implementation)
    * [Creating a contact](#1-creating-a-contact)
    * [Creating a wedding](#2-creating-a-wedding)
    * [Tagging the contact to the wedding](#3-tagging-a-contact-to-the-wedding)
    * [Adding a task to the wedding](#4-adding-a-task-to-a-wedding)
    * [Deleting a task from the wedding](#5-deleting-a-task-from-a-wedding)
    * [Untagging the contact from the wedding](#6-untagging-a-contact-from-a-wedding)
    * [Deleting the wedding](#7-deleting-a-wedding)
* [Documentation, logging, testing, configuration, dev-ops](#documentation-logging-testing-configuration-dev-ops)
* [Appendix: Requirements](#appendix-requirements)
  * [Product scope](#product-scope)
  * [User stories](#user-stories)
  * [Use cases](#use-cases)
  * [Non-Functional Requirements](#non-functional-requirements)
  * [Glossary](#glossary)
* [Appendix: Instructions for manual testing](#appendix-instructions-for-manual-testing)
  * [Launch and shutdown](#launch-and-shutdown)
  * [Deleting a person](#deleting-a-person)
  * [Saving data](#saving-data)
* [Appendix: Effort](#appendix-effort)
* [Appendix: Planned Enhancements](#appendix-planned-enhancements)



--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

EasyWeds is a brownfield software project based off [AddressBook Level-3](https://se-education.org/addressbook-level3/) ([UG](https://se-education.org/addressbook-level3/UserGuide.html), [DG](https://se-education.org/addressbook-level3/DeveloperGuide.html)),
taken under the CS2103T Software Engineering module held by the School of Computing at the National University of Singapore.

Java dependencies:

- JavaFX for GUI
- JUnit5 for testing

Documentation dependencies:

- Markbind for rendering the website
- PlantUML for creating UML diagrams

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document `docs/diagrams` folder. Refer to the [
_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create
and edit diagrams.

</div>

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object) and all `Wedding` objects (which are contained in a `UniqueWeddingList`).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores the currently 'selected' `Wedding` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Wedding>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</box>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />


<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</box>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

<puml src="diagrams/UndoSequenceDiagram-Logic.puml" alt="UndoSequenceDiagram-Logic" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

Similarly, how an undo operation goes through the `Model` component is shown below:

<puml src="diagrams/UndoSequenceDiagram-Model.puml" alt="UndoSequenceDiagram-Model" />

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

<puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4" />

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

<puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_

--------------------------------------------------------------------------------------------------------------------
## **User Workflow Implementation**

This section demonstrates how a user interacts with the application to perform essential tasks. In the following walkthrough, we cover:
1. Creating a contact
2. Creating a wedding
3. Tagging the contact to the wedding
4. Adding a task to the wedding
5. Deleting a task from the wedding
6. Untagging the contact from the wedding
7. Deleting the wedding

### 1. Creating a contact
The user begins by creating a new contact using the add command. For example, the user might input: `add n/John Doe p/98765432 e/johndoe@example.com a/123 Some Street r/Manager`
This command creates a contact with the specified name, phone number, email, address, and role.

Below is a sequence diagram that shows the steps involved when a user creates a contact.
This diagram illustrates how the command flows from the user through the `UI`, is parsed by the `LogicManager` (with help from the `AddressBookParser` and `AddCommandParser`), and then executed on the `Model`. Finally, the updated address book is saved via `Storage` and a success message is returned to the user.

<puml src="diagrams/createContact.puml" alt="createContact" />

### 2. Creating a wedding
The user can create a wedding using the addWedding command. For example, the user might input: `addWedding n/John Doe d/2025-12-25 l/Some Venue`
This command creates a wedding with the specified name, date, and location.

Below is a sequence diagram that shows the steps involved when a user creates a wedding. 
This diagram illustrates how the command flows from the user through the `UI`, is parsed by the `LogicManager` (with help from the `AddressBookParser` and `AddWeddingCommandParser`), and then executed on the `Model`. Finally, the updated address book is saved via `Storage` and a success message is returned to the user.

<puml src="diagrams/createWedding.puml" alt="createWedding" />

### 3. Tagging a contact to a wedding
The user can tag a contact to a wedding using the tag command. For example, the user might input: `tag 1 W1`
This command tags the contact with the specified name to the wedding with the specified wedding ID.

Below is a sequence diagram that shows the steps involved when a user tags a contact to a wedding.
This diagram illustrates how the command flows from the user through the `UI`, is parsed by the `LogicManager` (with help from the `AddressBookParser` and `TagCommandParser`), and then executed on the `Model`. Finally, the updated address book is saved via `Storage` and a success message is returned to the user.

<puml src="diagrams/tagContact.puml" alt="tagContact" />

### 4. Adding a task to a wedding
The user can add a task to a wedding using the addTask command. For example, the user might input: `addTask w/W1 desc/Book photographer`
This command adds a task to the wedding with the specified wedding ID.

Below is a sequence diagram that shows the steps involved when a user adds a task to a wedding.
This diagram illustrates how the command flows from the user through the `UI`, is parsed by the `LogicManager` (with help from the `AddressBookParser` and `AddTaskCommandParser`), and then executed on the `Model`. Finally, the updated address book is saved via `Storage` and a success message is returned to the user.

<puml src="diagrams/addTask.puml" alt="addTask" />

### 5. Deleting a task from a wedding
The user can delete a task from a wedding using the deleteTask command. For example, the user might input: `deleteTask w/W1 i/1`
This command deletes the task with the specified index from the wedding with the specified wedding ID.

Below is a sequence diagram that shows the steps involved when a user deletes a task from a wedding.
This diagram illustrates how the command flows from the user through the `UI`, is parsed by the `LogicManager` (with help from the `AddressBookParser` and `DeleteTaskCommandParser`), and then executed on the `Model`. Finally, the updated address book is saved via `Storage` and a success message is returned to the user.

<puml src="diagrams/deleteTask.puml" alt="deleteTask" />

### 6. Untagging a contact from a wedding
The user can untag a contact from a wedding using the untag command. For example, the user might input: `untag 1 W1`
This command untags the contact with the specified name from the wedding with the specified wedding ID.

Below is a sequence diagram that shows the steps involved when a user untags a contact from a wedding.
This diagram illustrates how the command flows from the user through the `UI`, is parsed by the `LogicManager` (with help from the `AddressBookParser` and `UntagCommandParser`), and then executed on the `Model`. Finally, the updated address book is saved via `Storage` and a success message is returned to the user.

<puml src="diagrams/untagContact.puml" alt="untagContact" />

### 7. Deleting a wedding
The user can delete a wedding using the deleteWedding command. For example, the user might input: `deleteWedding W1`
This command deletes the wedding with the specified wedding ID.

Below is a sequence diagram that shows the steps involved when a user deletes a wedding.
This diagram illustrates how the command flows from the user through the `UI`, is parsed by the `LogicManager` (with help from the `AddressBookParser` and `DeleteWeddingCommandParser`), and then executed on the `Model`. Finally, the updated address book is saved via `Storage` and a success message is returned to the user.

<puml src="diagrams/deleteWedding.puml" alt="deleteWedding" />
--------------------------------------------------------------------------------------------------------------------

## **Documentation, Logging, Testing, Configuration, Dev-Ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* has a need to manage a significant number of contacts and weddings
* freelance wedding planners who need to manage contacts for multiple weddings
* wishes to centralise the management of contacts and weddings into one spot
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: manage contacts and weddings faster than a typical mouse/GUI driven app


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​        | I want to …​                                                     | So that I can…​                                                |
|----------|----------------|------------------------------------------------------------------|----------------------------------------------------------------|
| `* * *`  | user           | add a client/vendor with their details                           | keep track of my contacts                                      |
| `* * *`  | user           | delete a client/vendor's record                                  | remove outdated or irrelevant clients/vendors                  |
| `* * *`  | user           | retrieve a client/vendor's record                                | view the details of my clients/vendors                         |
| `* * *`  | user           | update a client/vendor's detail                                  | have the most updated information for my contacts              |
| `* * *`  | user           | search for a client/vendor by name or wedding date               | quickly find the relevant personnel for a wedding              |
| `* * *`  | user           | add a wedding event with its details                             | keep track of the weddings that I am handling                  |
| `* * *`  | user           | view a list of wedding event with its details                    | view the details of the wedding                                |
| `* * *`  | user           | view a list of wedding events from the closest to latest date    | have a clear picture of the upcoming weddings                  |
| `* * *`  | user           | delete a wedding event                                           | remove outdated or irrelevant weddings                         |
| `* * *`  | user           | edit a wedding event                                             | update the details of the wedding with the correct information |
| `* * *`  | user           | add a task to a wedding event                                    | keep track of the tasks that need to be done for the wedding   |
| `* * *`  | user           | delete a task from a wedding event                               | remove outdated or irrelevant tasks                            |
| `* * *`  | user           | link vendors to specific weddings                                | know which vendors are handling which events                   |
| `* *`    | user           | view a summary of all my tasks related to a wedding              | have a clear picture of what needs to be done                  |
| `* *`    | user           | filter according to roles / event                                | have a clear picture of who I need to liaise with for          |
| `*`      | user           | mark and unmark my tasks related to a wedding                    | know which are the tasks that I have yet to complete           |
| `*`      | user           | have a confirmation message before I add very similar contacts   | avoid accidentally adding duplicate contacts                   |


### Use cases

(For all use cases below, the **System** is the `EasyWeds` and the **Actor** is the `User`, unless specified otherwise)

**Use case: UC01 - Add a Contact**

**MSS**

1.  User inputs the command to add contact
2.  EasyWeds adds the new contact

    Use case ends.

**Extensions**

* 2a. The field(s) is/are empty.

    * 2a1. An error message is shown.

        Use case ends.

* 2b. The name of the new contact matches an existing contact in the list.

    * 2b1. An error message is shown.

    * 2b2. User has to confirm whether to add this duplicate contact.

        Use case ends.


**Use case: UC02 - List Contacts**

**MSS**

1.  User inputs the command to list contacts
2.  EasyWeds shows the list of contacts

    Use case ends.

**Extensions**

* 2a. There are no contacts to show.

    Use case ends.


**Use case: UC03 - Delete a Contact**

**MSS**

1.  User requests to list contacts (UC02)
2.  EasyWeds shows a list of contacts
3.  User requests to delete a specific contact
4.  EasyWeds deletes the contact

    Use case ends.

**Extensions**

* 3a. The contact does not exist in the list.

    * 3a1. An error message is shown.

        Use case resumes at step 2.


**Use case: UC04 - Edit a Contact**

**MSS**

1.  User requests to list contacts (UC02)
2.  EasyWeds shows a list of contacts
3.  User requests to edit a specific contact in the list
4.  EasyWeds edits the contact

    Use case ends.

**Extensions**

* 3a. The contact does not exist in the list.

    * 3a1. An error message is shown.

        Use case resumes at step 2.

* 3b. The edited name is a duplicate of an existing name in the list.

    * 3b1. An error message is shown.

        Use case resumes at step 2.


**Use case: UC05 - Search Contacts by Name**

**MSS**

1.  User requests search for contacts that have a certain 'keyword' in their name
2.  EasyWeds shows a list of contacts that matches the 'keyword'

    Use case ends.

**Extensions**

* 2a. No matching contact is found.

    * 2a1. An error message is shown.

        Use case ends.


**Use case: UC06 - Search Contacts by Role**

**MSS**

1.  User requests search for contacts that have a certain 'keyword' in their role
2.  EasyWeds shows a list of contacts that matches the 'keyword'

    Use case ends.

**Extensions**

* 2a. No matching contact is found.

    * 2a1. An error message is shown.

        Use case ends.


**Use case: UC07 - Add a Wedding Event**

**MSS**

1.  User inputs the command to add wedding
2.  EasyWeds adds the new wedding

    Use case ends.

**Extensions**

* 2a. The field(s) is/are empty.

    * 2a1. An error message is shown.

        Use case ends.

* 2b. All the fields in the input match an existing wedding event in the list.

    * 2b1. An error message is shown.

        Use case ends.


**Use case: UC08 - View Wedding Events by Wedding Date**

**MSS**

1.  User inputs the command to list wedding events sorted from earliest to latest date
2.  EasyWeds shows the list of wedding events

    Use case ends.

**Extensions**

* 2a. There are no wedding events to show.

  Use case ends.

**Use case: UC09 - View Wedding Events by Wedding ID**

**MSS**
1.  User inputs the command to list wedding events sorted by wedding ID
2.  EasyWeds shows the list of wedding events

    Use case ends.

**Extensions**

* 2a. There are no wedding events to show.

  Use case ends.

**Use case: UC10 - Delete a Wedding Event**

**MSS**

1.  User requests to list wedding events (UC08)
2.  EasyWeds shows a list of wedding events
3.  User requests to delete a specific wedding event
4.  EasyWeds deletes the wedding event

    Use case ends.

**Extensions**

* 3a. The wedding event does not exist in the list.

    * 3a1. An error message is shown.

        Use case resumes at step 2.


**Use case: UC11 - Edit a Wedding Event**

**MSS**

1.  User requests to list wedding events (UC08)
2.  EasyWeds shows a list of wedding events
3.  User requests to edit a specific wedding event in the list
4.  EasyWeds edits the wedding event

    Use case ends.

**Extensions**

* 3a. The wedding event does not exist in the list.

    * 3a1. An error message is shown.

        Use case resumes at step 2.

* 3b. The edited wedding name, date and location is a duplicate of an existing wedding event in the list.

    * 3b1. An error message is shown.

        Use case resumes at step 2.


**Use case: UC12 - Tag Contacts to Wedding Events**

**MSS**

1.  User inputs the command to tag a contact to a wedding event
2.  EasyWeds adds the tag for the wedding event to the contact specified

    Use case ends.

**Extensions**

* 2a. The contact does not exist.

    * 2a1. An error message is shown.

        Use case ends.

* 2b. The wedding does not exist.

    * 2b1. An error message is shown.

        Use case ends.


**Use case: UC13 - Untag Contact from Wedding Event**

**MSS**

1.  User inputs the command to untag a contact from a wedding event
2.  EasyWeds removes the tag for the wedding event from the contact specified

    Use case ends.

**Extensions**

* 2a. The contact does not exist.

    * 2a1. An error message is shown.

        Use case ends

* 2b. The wedding does not exist.

    * 2b1. An error message is shown.

        Use case ends.

* 2c. The wedding specified is not tagged to this contact.

    * 2c1. An error message is shown.

        Use case ends.


**Use case: UC14 - Filter Contacts**

**MSS**

1.  User requests filtering contacts that are tagged to a certain wedding event
2.  EasyWeds shows a list of contacts that contain the tag

    Use case ends.

**Extensions**

* 2a. The wedding does not exist.

    * 2a1. An error message is shown.

        Use case ends.

* 2b. There are no contacts tagged to the wedding.

    * 2b1. An error message is shown.

        Use case ends.

**Use case: UC15 - Add a Task to a Wedding**

**MSS**

1.  User inputs the command to add a task to a specific wedding
2.  EasyWeds adds the task to the wedding

    Use case ends.

**Extensions**

* 2a. The wedding does not exist.

    * 2a1. An error message is shown.

      Use case ends.


**Use case: UC16 - Delete a Task from a Wedding**

**MSS**

1.  User inputs the command to delete a task from a specific wedding
2.  EasyWeds deletes the task from the wedding

    Use case ends.

**Extensions**

* 2a. The wedding does not exist.

    * 2a1. An error message is shown.

      Use case ends.

* 2b. The task index is invalid.

    * 2b1. An error message is shown.

      Use case ends.


**Use case: UC17 - Clear Contact Book**

**MSS**

1.  User inputs the command to clear the contact book
2.  EasyWeds asks for confirmation
3.  User confirms the action
4.  EasyWeds clears all contacts

    Use case ends.

**Extensions**

* 2a. The contact book is already empty.

    * 2a1. EasyWeds clears the contact book without asking for confirmation.

      Use case ends.

* 3a. User does not confirm the action.

    * 3a1. The clear operation is cancelled.

      Use case ends.


### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4. The system should respond to user commands within 2 seconds, even under peak load conditions.
5. The application should handle errors gracefully, providing clear and helpful error messages.
6. Adhere to a consistent coding style with comprehensive inline documentation and JavaDocs.
7. User interface should be intuitive and accessible
8. Candidate data should be stored securely, ensuring unauthorized users cannot access sensitive information.
9. Data should not be lost between each session and be persistently stored.


### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **MSS**: Main Success Scenario which describes the most straightforward interaction for a given use case, which assumes nothing goes wrong
* **Client**: A person who hires a wedding planner to organise their wedding. Clients in EasyWeds are individuals or couples whose event details are tracked within the system
* **Vendor**: A service provider involved in the wedding, such as florists, caterers, photographers. Vendors in EasyWeds are linked to events and are categorised based on their service types

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_

## **Appendix: Effort**


## **Appendix: Planned Enhancements**
