# GeneticAlgorithm-8Queen
Applying Genetic Algorithm on 8 Queen problem in Python


Genetic Algorithm
*****************

In this project genetic algorithm is used to solve 8queen problem. However it is not limited to only 8 queens and 
can be used to solve other instances of this problem.


Installation
============

You only need to install python and numpy to run this project. You can install them

On Linux:

	sudo apt-get update
	sudo apt-get install python3.6 python3-pip
	sudo pip3 install numpy

On OSX:

	brew install python
	sudo pip3 install numpy

On Windows you can download latest version of python from 'python.org <https://www.python.org/downloads/>'_, and run the installer.


Running
=======

Now that you have install python, you can run the project. To do so, you must be at directory AI, and enter command:

	python3 main.py

Doing so, you will receive the following output:

Position vector of queens - vector elements indicate the row and their index, indicate their column. Their values are between 0 to 7
as the bottom left square has values (0,0) and top right has values (7,7).

Number of threats - It indicate number of queens being threated howerver, here for simplification, we count threats themselves instead 
of queens being threated.

Board with current formation - Chess board with placed queens as the way the algorithm thinks is optimal.


Algorithm
=========

Genetic algorithm is used in this project. It has all genetic algorithm properties including:

Crossover (is used to generate new childs).
Mutation (is used to mutate chromosomes).
Selection (is used to select best individuals from population).

You can also see that iteration number iteration, population size, chromosome size (is directly related to problem which here is 8 as 
8queen problem), crossover count (number of cross overs done by algorithm in each iteration) and mutation count (number of mutation done
by algorithm).


Help
----
I tried to use meaningful funtions and variables as much as I could to make it easy to understand, but if you have any question, regard-
ing either it's algorithm or code-concept of this project, contact me at "sina.moqadam@gmail.com".
