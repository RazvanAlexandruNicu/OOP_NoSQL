This program simulates a NoSQL Database, using Java Collections.
The program takes 2 params:
	- the input file path name
	- the output file path name
It supports commands such as:
	- CREATEDB [database]
	- CREATE [entity] [rf] [number_attr]
	- INSERT [entity] [valattr1] .. [valattrn]
	- DELETE [entity] [primary_key]
	- UPDATE [entity] [primary_key] [attr1] [newval_attr1] .. [attrn] [newval_attrn]
	- GET [entity] [primary_key]
	- SNAPSHOTDB
	- CLEANUP [database] [timestamp]
The database has also the property to scale itself, to increase the number of nodes if no
space is available.