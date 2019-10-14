# Notey

Lotus Notes Java 8 Utility wrapper for Notes.jar Resources.

## Why?

The IBM provided java-api for Lotus Notes is very old and not compatible with many
new approaches introduced with java 5 and up.

A few examples of downsides:

- NotesException as a CheckedException

  This leads to programs where ``` throws NotesException ``` is basically a mandatory method suffix.
	  
- Untyped Generic Types

  Notes uses mixed type collections, but they are not properly typed to Object. 
	
- Lots of Boilerplate

  Tasks that are often repeated require heaps of Boilerplate code, which invites copy/pasteing and is error-prone.
  E.g.: Accessing Views, processing DocumentCollections and ViewCollections

This lead to workarounds and wrappers which were reinvented in every other notes project.

Goal: 

    This library aims to simplify working with notes in Java8+ by abstracting the old JavaAPI provided by Notes.jar away.


## Core Concepts

### Exceptions

Notey does never throw a lotus.domino.NotesException.

Unrecoverable errors like an Unopened Database will instead cause a NoteyRuntimeException.

Recoverable errors like lotus.domino.Base#recycle() will be ignored and silenced.

TBD configurables??


### Views are now Projections

In Notey you will not be working directly with Views. Instead Notey treats a viewEntry as a Projection of a Document.

No more recycling entry, then entrycollection, then... We recycle that for you.

Projection results are mapped java Pojos.

See Cookbook#Basic Projection





## Features / Cookbook

### Basic Projection

Create a Pojo extending DocumentProjection and annotate it with @NoteyProjection:

    @NoteyProjection("example-view")
    class Example extends DocumentProjection {
	     private String name;
	     private String street;
	     private String deletionMark;
	
		  public String getName(){
		    	return this.name;
		  }
		
		 /* Setter is optional */
	 }

This will map your ViewEntries in the View "example-view" to this class.

Now you need only a supply a Database to interact with the View in Projections:

	 Database db = loadDbFromSomewhere();
	 
Load All:

    List<Example> examples = Projections.all(db, Example.class);

One Key:

    List<Example> examples = Projections.byKey(db, Example.class, "Peter Fox");

Multiple Keys:

	Vector<Object> keys = loadKeyVectorFromSomewhere();
    List<Example> examples = Projections.byKeys(db, Example.class, keys);


Currently the mapping implementation is simple: columns are mapped left->right to field up->down.

In this example this would mean, the first 3 viewcolumns are mapped as such:

| Field | ColumnIndex |
| ----- | ----------- |
| name  | 0 |
| street| 1 |
| deletionMark| 2 |

With this setup, here is a simple example:

ViewEntry.ColumnValues ["Peter Stark", "MainStr.", "Yes"]

results in 

{
	name = "Peter Stark"
	street = "MainStr."
	deletionMark = "Yes"
}

