Test task for Real View

The task:

A closed 2D shape is represented as an array of connected 2D points.
Each point have x and y coordinates.
For 1m square it can be like this [{0,0}, {1,0}, {1,1} {0,1},{0,0}]
A valid 2D shape must have no sub-contours or interior points.
Any points should be used only once, with the exception of the first/last point of the figure.
A valid shape can be a convex polygon or a non-convex polygon
(https://en.wikipedia.org/wiki/Convex_polygon)
Invalid 2D shape in real practice occurs when a new shape is created as a direct concatenation of two or
more simple valid shapes.
The Task is as follows: ( Also please an additional clarification below)
Implement (in Java) TheShapeFixer class,
that implements shape validation and correction with two required methods:
isValid(Shape2D): boolean - returns true if a shape is valid
repair(Shape2D): Shape2D - takes an invalid shape and creates a valid version of it
