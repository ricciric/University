import numpy as np
import random

# funzione che assegna il numero di minacce (threats) per ogni regina
# in ogni cromosoma, la posizione del gene indica la colonna, il valore indica la riga 
def countThreat(chromosome):
    threat = 0

    # Ciclo principale
    for currentGene in range(0, len(chromosome)-1):

        # queen[x,y]
        # In un diagramma cartesiamo, le x corrispondono alle colonne di una matrice e le y alle righe
        currentQueen = [currentGene, chromosome[currentGene]]

        # Questo ciclo è fatto in modo da non valutare due volte la stessa coppia di regine
        # per range(0,0) non viene eseguito il corpo del for.
        for previousGene in range (currentGene+1, len(chromosome)):
        # for previousGene in range (0, currentGene):    
            previousQueen = [previousGene, chromosome[previousGene]]

            # calcola il gradiente della retta che unisce le due regine
            slope = (currentQueen[1] - previousQueen[1]) / (currentQueen[0] - previousQueen[0])

            # Verifico una minaccia orizzontale
            # Due regine si minacciano se hanno la retta che le unisce a gradiente nullo
            if slope == 0:
                threat += 1
                

            # Verifico una minaccia diagonale
            # Due regine si minacciano se hanno la retta che le unisce con gradiente +1 o -1
            elif slope == 1 or slope == -1:
                threat += 1
                

    return threat

# inizializza la popolazione con valori tra 0 e 7
# in ogni cromosoma, la posizione del gene indica la colonna, il valore indica la riga
def initializeRandomPopulation (populationSize, chromosomeSize): # initializing population randomly
    population = []
    for i in range(populationSize):
        population.append(np.random.random_integers(low=0, high=chromosomeSize-1, size=(chromosomeSize))) 

    return population


def sortPopulation (population):
    # .sort ordina (dal più piccolo al più grande di default) un lista. Se indicata una funzione, ordina con i 
    # valori di quella funzione
    population.sort(key=countThreat) # python built in sort with preferred function as key
    return population


def crossover(population, crossoverCount):
    chromosomeLenght = len(population[0])
    for i in range(0, crossoverCount): # run cross over as many times as we want
        crossoverParent1 = random.choice(population) # selects first parent randomly
        crossoverParent2 = random.choice(population) # selects second parent randomly

        # L'incrocio viene fatto selezionando un punto di "frattura" e invertendo le due parti
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


