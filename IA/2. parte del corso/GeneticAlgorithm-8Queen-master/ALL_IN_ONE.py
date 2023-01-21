import numpy as np
import random

def countThreat (population):
    threat = 0

    #count threats for each queen
    for currentGene in range(0, len(population)):

        #queen[x,y]
        currentQueen = [currentGene, population[currentGene]]

        #check all queens before current queen for threats
        for previousGene in range (0, currentGene):
            previousQueen = [previousGene, population[previousGene]]

            slope = (currentQueen[1] - previousQueen[1]) / (currentQueen[0] - previousQueen[0])

            #check for horizental threat
            #two queens must form a line with slope 0
            if slope == 0:
                threat += 1
                break

            #check for diagonal threat
            #two queens must form a line with slop either 1 or -1
            elif slope == 1 or slope == -1:
                threat += 1
                break

    return threat


def initializeRandomPopulation (populationSize, chromosomeSize): # initializing population randomly
    population = []
    for i in range(populationSize):
        #population.append(np.random.random_integers(low=0, high=chromosomeSize-1, size=(chromosomeSize))) # using numpy library to initialize population with size of chromosome size with random values between 0 to 7
        population.append(np.random.randint(low=0, high=chromosomeSize-1, size=(chromosomeSize))) # using numpy library to initialize population with size of chromosome size with random values between 0 to 7
    return population


def sortPopulation (population):
    population.sort(key=countThreat) # python built in sort with preferred function as key
    return population


def crossover(population, crossoverCount):
    chromosomeLenght = len(population[0])
    for i in range(0, crossoverCount): # run cross over as many times as we want
        crossoverParent1 = random.choice(population) # selects first parent randomly
        crossoverParent2 = random.choice(population) # selects second parent randomly

        crossoverPoint = random.randint(1, chromosomeLenght-1) # selects point for cross over randomly between genes 1 to 6

        child1 = [] # initialize first child
        child1.extend(crossoverParent1[:crossoverPoint]) # add genes of first parent from first gene to cross over point gene
        child1.extend(crossoverParent2[crossoverPoint:]) # add genes of second parent from cross over gene to last gene
        child2 = [] # initialize second child
        child2.extend(crossoverParent2[:crossoverPoint]) # add genes of second parent from first gene to cross over point gene
        child2.extend(crossoverParent1[crossoverPoint:]) # add genes of first parent from cross over gene to last gene

        population.append(child1) # add first child to whole population
        population.append(child2) # add second child to whole population

    return population


def mutation(population, mutationCount):
    chromosomeLenght = len(population[0])
    for i in range(0, mutationCount):  # run mutation as many times as we want
        mutationParent = random.choice(population) # selects a random chromosome

        mutationPoint = random.randint(0, chromosomeLenght-1) # selects a random gene
        mutationGene = random.randint(0, chromosomeLenght-1) # selects a random value for selected gene

        child = mutationParent # create a child for mutation
        child[mutationPoint] = mutationGene # mutate

        population.append(child) # add child to whole population

    return population


#print chess board with queens as 'O' and empty positions as '-'
def printBoard(formation, chromosomeSize):
    board = []
    for row in range(0, chromosomeSize):
        array = []
        for column in range(0, chromosomeSize):
            if row == formation[column]:
                array.append('O')
            else:
                array.append('-')
        board.append(array)

    print("board with current formation is:")
    print(np.matrix(board))


# Application of genetic algorithm on 8queen problem

populationSize = 20 # Number of chromosomes
chromosomeSize = 9 # Number of queens (genes in each chromosome)
iterations = 1000 # Number of iterations that genetic algorithm runs
mutationCount = 5 # Number of mutations in each iteration
crossoverCount = 5 # Number of crossovers in each iteration

population = initializeRandomPopulation(populationSize, chromosomeSize) # Initialize population (chromosomes)

for iteration in range(0, iterations): # run from iteration 0 to total number of iterations defined above
    population = crossover(population, crossoverCount) # Apply genetic algorithm cross over, over the population
    population = mutation(population, mutationCount) # Apply genetic algorithm mutation, over the population
    population = sortPopulation(population) # sort the population by their value for selection part
    population = population[:populationSize] # select top chromosomes of population


print("position vector of queens: " + str(population[0]))
print("number of queens threats: " + str(countThreat(population[0])))

printBoard(population[0], chromosomeSize)